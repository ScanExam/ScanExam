package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ExamGraduationService extends Service {
  private int currentSheetIndex;
  
  private int currentQuestionIndex;
  
  private Set<StudentSheet> studentSheets;
  
  private CorrectionTemplate template;
  
  @Override
  public void save(final String path) {
    try {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      this.document.save(outputStream);
      final byte[] encodedDoc = Base64.getEncoder().encode(outputStream.toByteArray());
      String _string = new String(encodedDoc);
      this.template.setEncodedDocument(_string);
      outputStream.close();
      this.template.setExam(ExamSingleton.instance);
      this.template.getStudentsheets().addAll(this.studentSheets);
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
      final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
      resourceSet.getPackageRegistry().put(CorePackage.eNS_URI, CorePackage.eINSTANCE);
      resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
      final Resource resource = resourceSet.createResource(URI.createFileURI(path));
      resource.getContents().add(this.template);
      resource.save(null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean open(final String xmiFile) {
    try {
      final Optional<CorrectionTemplate> correctionTemplate = ExamGraduationService.loadTemplate(xmiFile);
      boolean _isPresent = correctionTemplate.isPresent();
      if (_isPresent) {
        this.template = correctionTemplate.get();
        ExamSingleton.instance = correctionTemplate.get().getExam();
        this.template.getStudentsheets().addAll(this.studentSheets);
        final byte[] decoded = Base64.getDecoder().decode(correctionTemplate.get().getEncodedDocument());
        this.document = PDDocument.load(decoded);
        return true;
      }
      return false;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static Optional<CorrectionTemplate> loadTemplate(final String path) {
    final ResourceSetImpl resourceSet = new ResourceSetImpl();
    final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
    Resource resource = null;
    try {
      resource = resourceSet.getResource(URI.createFileURI(path), true);
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        return Optional.<CorrectionTemplate>empty();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final EObject template = resource.getContents().get(0);
    if ((!(template instanceof CorrectionTemplate))) {
      return Optional.<CorrectionTemplate>empty();
    }
    return Optional.<CorrectionTemplate>ofNullable(((CorrectionTemplate) template));
  }
  
  /**
   * Liste des identifiants des etudiants
   */
  public ArrayList<Integer> studentsList() {
    ArrayList<Integer> _xblockexpression = null;
    {
      ArrayList<Integer> tab = new ArrayList<Integer>();
      for (int i = 0; (i < (this.studentSheets.size() - 1)); i++) {
        tab.add(Integer.valueOf((((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[i]).getId()));
      }
      _xblockexpression = tab;
    }
    return _xblockexpression;
  }
  
  public int numberOfQuestions() {
    int _xblockexpression = (int) 0;
    {
      int nbQuestion = 0;
      for (int i = 0; (i < (IterableExtensions.size(this.document.getPages()) - 1)); i++) {
        int _nbQuestion = nbQuestion;
        int _size = this.template.getExam().getPages().get(i).getQuestions().size();
        nbQuestion = (_nbQuestion + _size);
      }
      _xblockexpression = nbQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute d'un etudiant
   */
  public boolean addStudents(final int id) {
    boolean _xblockexpression = false;
    {
      final StudentSheet newStudent = CoreFactory.eINSTANCE.createStudentSheet();
      newStudent.setId(id);
      _xblockexpression = this.studentSheets.add(newStudent);
    }
    return _xblockexpression;
  }
  
  /**
   * Passe au prochaine etudiant dans les StudentSheet
   */
  public int nextStudent() {
    int _xifexpression = (int) 0;
    int _size = this.studentSheets.size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentSheetIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Passe au etudiant précédent dans les StudentSheet
   */
  public int previousStudent() {
    int _xifexpression = (int) 0;
    if ((this.currentSheetIndex > 0)) {
      _xifexpression = this.currentSheetIndex--;
    }
    return _xifexpression;
  }
  
  public int nextQuestion() {
    int _xifexpression = (int) 0;
    int _size = this.getCurrentPage().getQuestions().size();
    boolean _lessThan = ((this.currentQuestionIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.currentQuestionIndex++;
    }
    return _xifexpression;
  }
  
  public int previousQuestion() {
    int _xifexpression = (int) 0;
    if ((this.currentQuestionIndex > 0)) {
      _xifexpression = this.currentQuestionIndex--;
    }
    return _xifexpression;
  }
  
  public int indexOfQuestions(final int indexpage, final int indexquestion) {
    int _xblockexpression = (int) 0;
    {
      int indexQuestion = 0;
      for (int i = 0; (i < (indexpage - 1)); i++) {
        int _indexQuestion = indexQuestion;
        int _size = this.template.getExam().getPages().get(i).getQuestions().size();
        indexQuestion = (_indexQuestion + _size);
      }
      int _indexQuestion = indexQuestion;
      indexQuestion = (_indexQuestion + indexquestion);
      _xblockexpression = indexQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute la note a la question courante
   */
  public Grade setGrade(final Grade note) {
    return (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().set(this.indexOfQuestions(this.pageIndex, this.currentQuestionIndex), note);
  }
  
  @Override
  public void create(final File file) {
    try {
      this.document = PDDocument.load(file);
      ExamSingleton.instance = CoreFactory.eINSTANCE.createExam();
      int _size = IterableExtensions.size(this.document.getPages());
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        ExamSingleton.instance.getPages().add(CoreFactory.eINSTANCE.createPage());
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
