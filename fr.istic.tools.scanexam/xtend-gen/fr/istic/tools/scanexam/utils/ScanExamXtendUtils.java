package fr.istic.tools.scanexam.utils;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.ScanexamPackage;
import fr.istic.tools.scanexam.StudentGrade;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ScanExamXtendUtils {
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
