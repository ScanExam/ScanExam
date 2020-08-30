package fr.istic.tools.scanexam.utils

import java.io.IOException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import fr.istic.tools.scanexam.Exam
import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.ScanexamPackage
import java.io.File
import fr.istic.tools.scanexam.StudentGrade
import fr.istic.tools.scanexam.impl.GradingDataImpl
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.swing.JOptionPane

class ScanExamXtendUtils {


	def static convertPdfToPng(String filename) {
		try {
			val File file = new File(filename);
			if (!file.exists()) throw new IOException()
			// Run a shell command
			val parent = file.getAbsoluteFile().getParentFile()
			val tmpDir = new File(parent.path+File.separator+"png")
			
			if (!tmpDir.exists)
				tmpDir.mkdir
			else 
				tmpDir.listFiles.filter[isFile].forEach[it.delete]
			
			val command ='''gs -dBATCH -dNOPAUSE -sDEVICE=pnggray -r300 -dUseCropBox  -sOutputFile="«tmpDir.path+File.separator»«filename»-%03d.png" «filename»''' 
			println(command);
			val process = Runtime.getRuntime().
			exec(command);

			val StringBuilder output = new StringBuilder();

			val BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			var String line;
			while ((line = reader.readLine()) !==null) {
				//output.append(line + "\n");
				System.out.println(line);
			}

			val exitVal = process.waitFor();
			if (exitVal == 0) {
				JOptionPane.showMessageDialog(null,'''Conversion successfull''',"Format error",JOptionPane.DEFAULT_OPTION);
				System.out.println("Success!");
				val files = tmpDir.listFiles
				return files
			} else {
				System.err.println(output);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	def static GradingData load(File f) {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.getResource(URI.createFileURI(f.absolutePath), true)
		return (resource.getContents().get(0) as GradingData)
	}

	def static void save(File f, GradingData data) throws IOException {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.createResource(URI.createFileURI(f.absolutePath))
		resource.getContents().add(data)
		resource.save(null)
	}
	
	val static gradeMap = newHashMap(#[
			"A"->5,
			"B"->4,
			"C"->3,
			"D"->2,
			"E"->1,
			"F"->0
		]);
	
	def static computeGrade(StudentGrade studentGrade) {
		val exam = (studentGrade.eContainer as GradingData).exam
		//val scale = exam.questions.map[weight].reduce[p1, p2|p1+p2]
		
		var grade = 0.0;  
		for (questionGrade : studentGrade.questionGrades) {
			val qgrade =gradeMap.get(questionGrade.grade)
			if (qgrade!==null && questionGrade.validated) {
				grade += gradeMap.get(questionGrade.grade)*questionGrade.question.weight
			} 
		}
		Math.ceil(grade/exam.scale*16)/4;
	}
	
}
