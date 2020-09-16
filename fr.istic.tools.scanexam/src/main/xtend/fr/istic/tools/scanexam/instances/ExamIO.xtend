package fr.istic.tools.scanexam.instances

import java.io.IOException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import fr.istic.tools.scanexam.Exam
import fr.istic.tools.scanexam.ScanexamPackage

class ExamIO {
	def static void save(Exam datapath, String modelfilename) throws IOException {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.createResource(URI.createFileURI(modelfilename))
		resource.getContents().add(datapath)
		resource.save(null)
	}

	def static Exam load(String modelfilename) {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.getResource(URI.createFileURI(modelfilename), true)
		return (resource.getContents().get(0) as Exam)
	}
}
