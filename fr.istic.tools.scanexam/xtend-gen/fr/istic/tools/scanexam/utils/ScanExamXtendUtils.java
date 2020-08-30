package fr.istic.tools.scanexam.utils;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.ScanexamPackage;
import fr.istic.tools.scanexam.StudentGrade;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ScanExamXtendUtils {
  public static File[] convertPdfToPng(final String filename) {
    try {
      final File file = new File(filename);
      boolean _exists = file.exists();
      boolean _not = (!_exists);
      if (_not) {
        throw new IOException();
      }
      final File parent = file.getAbsoluteFile().getParentFile();
      String _path = parent.getPath();
      String _plus = (_path + File.separator);
      String _plus_1 = (_plus + "png");
      final File tmpDir = new File(_plus_1);
      boolean _exists_1 = tmpDir.exists();
      boolean _not_1 = (!_exists_1);
      if (_not_1) {
        tmpDir.mkdir();
      } else {
        final Function1<File, Boolean> _function = (File it) -> {
          return Boolean.valueOf(it.isFile());
        };
        final Consumer<File> _function_1 = (File it) -> {
          it.delete();
        };
        IterableExtensions.<File>filter(((Iterable<File>)Conversions.doWrapArray(tmpDir.listFiles())), _function).forEach(_function_1);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("gs -dBATCH -dNOPAUSE -sDEVICE=pnggray -r300 -dUseCropBox  -sOutputFile=\"");
      String _path_1 = tmpDir.getPath();
      String _plus_2 = (_path_1 + File.separator);
      _builder.append(_plus_2);
      _builder.append(filename);
      _builder.append("-%03d.png\" ");
      _builder.append(filename);
      final String command = _builder.toString();
      InputOutput.<String>println(command);
      final Process process = Runtime.getRuntime().exec(command);
      final StringBuilder output = new StringBuilder();
      InputStream _inputStream = process.getInputStream();
      InputStreamReader _inputStreamReader = new InputStreamReader(_inputStream);
      final BufferedReader reader = new BufferedReader(_inputStreamReader);
      String line = null;
      while (((line = reader.readLine()) != null)) {
        System.out.println(line);
      }
      final int exitVal = process.waitFor();
      if ((exitVal == 0)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Conversion successfull");
        JOptionPane.showMessageDialog(null, _builder_1, "Format error", JOptionPane.DEFAULT_OPTION);
        System.out.println("Success!");
        final File[] files = tmpDir.listFiles();
        return files;
      } else {
        System.err.println(output);
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else if (_t instanceof InterruptedException) {
        final InterruptedException e_1 = (InterruptedException)_t;
        e_1.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  public static GradingData load(final File f) {
    ResourceSet resourceSet = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(
      Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE);
    Resource resource = resourceSet.getResource(URI.createFileURI(f.getAbsolutePath()), true);
    EObject _get = resource.getContents().get(0);
    return ((GradingData) _get);
  }
  
  public static void save(final File f, final GradingData data) throws IOException {
    ResourceSet resourceSet = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(
      Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE);
    Resource resource = resourceSet.createResource(URI.createFileURI(f.getAbsolutePath()));
    resource.getContents().add(data);
    resource.save(null);
  }
  
  private static final HashMap<String, Integer> gradeMap = CollectionLiterals.<String, Integer>newHashMap(
    new Pair[] { Pair.<String, Integer>of("A", Integer.valueOf(5)), Pair.<String, Integer>of("B", Integer.valueOf(4)), Pair.<String, Integer>of("C", Integer.valueOf(3)), Pair.<String, Integer>of("D", Integer.valueOf(2)), Pair.<String, Integer>of("E", Integer.valueOf(1)), Pair.<String, Integer>of("F", Integer.valueOf(0)) });
  
  public static double computeGrade(final StudentGrade studentGrade) {
    double _xblockexpression = (double) 0;
    {
      EObject _eContainer = studentGrade.eContainer();
      final Exam exam = ((GradingData) _eContainer).getExam();
      double grade = 0.0;
      EList<QuestionGrade> _questionGrades = studentGrade.getQuestionGrades();
      for (final QuestionGrade questionGrade : _questionGrades) {
        {
          final Integer qgrade = ScanExamXtendUtils.gradeMap.get(questionGrade.getGrade());
          if (((qgrade != null) && questionGrade.isValidated())) {
            double _grade = grade;
            Integer _get = ScanExamXtendUtils.gradeMap.get(questionGrade.getGrade());
            double _weight = questionGrade.getQuestion().getWeight();
            double _multiply = ((_get).intValue() * _weight);
            grade = (_grade + _multiply);
          }
        }
      }
      int _scale = exam.getScale();
      double _divide = (grade / _scale);
      double _multiply = (_divide * 16);
      double _ceil = Math.ceil(_multiply);
      _xblockexpression = (_ceil / 4);
    }
    return _xblockexpression;
  }
}
