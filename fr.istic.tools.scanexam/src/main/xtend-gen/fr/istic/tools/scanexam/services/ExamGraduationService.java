package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.io.TemplateIO;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ExamGraduationService extends Service {
  private int currentSheetIndex;
  
  private int currentQuestionIndex;
  
  @Accessors
  private Collection<StudentSheet> studentSheets;
  
  private CreationTemplate creationTemplate;
  
  private CorrectionTemplate correctionTemplate;
  
  @Override
  public void save(final String path) {
  }
  
  public boolean openCorrectionTemplate(final String xmiFile) {
    final Optional<CorrectionTemplate> correctionTemplate = TemplateIO.loadCorrectionTemplate(xmiFile);
    boolean _isPresent = correctionTemplate.isPresent();
    if (_isPresent) {
      this.correctionTemplate = correctionTemplate.get();
      ExamSingleton.instance = correctionTemplate.get().getExam();
      return true;
    }
    return false;
  }
  
  public boolean openCreationTemplate(final String xmiFile) {
    final Optional<CreationTemplate> editionTemplate = TemplateIO.loadCreationTemplate(xmiFile);
    boolean _isPresent = editionTemplate.isPresent();
    if (_isPresent) {
      this.creationTemplate = editionTemplate.get();
      ExamSingleton.instance = editionTemplate.get().getExam();
      return true;
    }
    return false;
  }
  
  public boolean openCorrectionPdf(final String path) {
    try {
      File _file = new File(path);
      this.document = PDDocument.load(_file);
      int _size = ExamSingleton.instance.getPages().size();
      final PdfReaderWithoutQrCodeImpl pdfReader = new PdfReaderWithoutQrCodeImpl(this.document, _size, 3);
      pdfReader.readPDf();
      this.studentSheets = pdfReader.getCompleteStudentSheets();
      return true;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public int numberOfQuestions() {
    int _xblockexpression = (int) 0;
    {
      int nbQuestion = 0;
      for (int i = 0; (i < (IterableExtensions.size(this.document.getPages()) - 1)); i++) {
        int _nbQuestion = nbQuestion;
        int _size = this.creationTemplate.getExam().getPages().get(i).getQuestions().size();
        nbQuestion = (_nbQuestion + _size);
      }
      _xblockexpression = nbQuestion;
    }
    return _xblockexpression;
  }
  
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == studentId));
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.studentSheets, _function).getPosPage().get(0);
    return ((pageId).intValue() + offset);
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
  
  /**
   * Renomme l'étudiant
   * @param name le nouveau nom de l'étudiant
   */
  public void renameStudent(final String name) {
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentName(name);
  }
  
  public Question getCurrentQuestion() {
    final Function1<Question, Boolean> _function = (Question x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == this.currentQuestionIndex));
    };
    return IterableExtensions.<Question>findFirst(this.getCurrentPage().getQuestions(), _function);
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
        int _size = this.creationTemplate.getExam().getPages().get(i).getQuestions().size();
        indexQuestion = (_indexQuestion + _size);
      }
      int _indexQuestion = indexQuestion;
      indexQuestion = (_indexQuestion + indexquestion);
      _xblockexpression = indexQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute une entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle ajouter l'entrée
   * @param l'ID de l'entrée dans l'Examen
   */
  public boolean addGradeEntry(final int questionId, final int gradeEntryId) {
    boolean _xblockexpression = false;
    {
      final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
        int _id = entry.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      };
      final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
      _xblockexpression = (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries().add(gradeEntry);
    }
    return _xblockexpression;
  }
  
  /**
   * Retire une entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle retirer l'entrée
   * @param l'ID de l'entrée dans l'Examen
   */
  public boolean removeGradeEntry(final int questionId, final int gradeEntryId) {
    boolean _xblockexpression = false;
    {
      final EList<GradeEntry> entries = (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries();
      final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
        int _id = entry.getId();
        return Boolean.valueOf((_id == gradeEntryId));
      };
      final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(entries, _function);
      _xblockexpression = entries.remove(gradeEntry);
    }
    return _xblockexpression;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées
   * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
   */
  public List<Integer> getQuestionSelectedGradeEntries(final int questionId) {
    final Function1<GradeEntry, Integer> _function = (GradeEntry entry) -> {
      return Integer.valueOf(entry.getId());
    };
    return ListExtensions.<GradeEntry, Integer>map((((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries(), _function);
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
  public List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(final int questionId) {
    final Function1<GradeEntry, Tuple3<Integer, String, Float>> _function = (GradeEntry entry) -> {
      return Tuple3.<Integer, String, Float>of(Integer.valueOf(entry.getId()), entry.getHeader(), Float.valueOf(entry.getStep()));
    };
    return ListExtensions.<GradeEntry, Tuple3<Integer, String, Float>>map(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
  }
  
  /**
   * Ajoute la note a la question courante
   */
  public Grade setGrade(final Grade note) {
    return (((StudentSheet[])Conversions.unwrapArray(this.studentSheets, StudentSheet.class))[this.currentSheetIndex]).getGrades().set(this.indexOfQuestions(this.pageIndex, this.currentQuestionIndex), note);
  }
  
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
  
  @Pure
  public Collection<StudentSheet> getStudentSheets() {
    return this.studentSheets;
  }
  
  public void setStudentSheets(final Collection<StudentSheet> studentSheets) {
    this.studentSheets = studentSheets;
  }
}
