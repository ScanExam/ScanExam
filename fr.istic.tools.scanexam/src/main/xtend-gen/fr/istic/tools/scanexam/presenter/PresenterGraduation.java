package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.GradesExportImpl;
import fr.istic.tools.scanexam.mailing.StudentDataManager;
import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterGraduationLoader;
import fr.istic.tools.scanexam.presenter.PresenterImportExportXMI;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQuestion;
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterGraduation implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQuestion presQuestion;
  
  private PresenterGraduation graduationPresenter;
  
  private PresenterPdf presPdf;
  
  private PresenterStudentListLoader studentListPresenter;
  
  private PresenterGraduationLoader graduationLoaderPresenter;
  
  private PresenterImportExportXMI importExportPresenter;
  
  private ServiceGraduation service;
  
  private static final Logger logger = LogManager.getLogger();
  
  public PresenterGraduation(final ServiceGraduation service) {
    Objects.<ServiceGraduation>requireNonNull(service);
    this.service = service;
    PresenterImportExportXMI _presenterImportExportXMI = new PresenterImportExportXMI(service);
    this.importExportPresenter = _presenterImportExportXMI;
    PresenterGraduationLoader _presenterGraduationLoader = new PresenterGraduationLoader(this.importExportPresenter, service);
    this.graduationLoaderPresenter = _presenterGraduationLoader;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
    PresenterQuestion _presenterQuestion = new PresenterQuestion(service);
    this.presQuestion = _presenterQuestion;
    PresenterStudentListLoader _presenterStudentListLoader = new PresenterStudentListLoader(service);
    this.studentListPresenter = _presenterStudentListLoader;
  }
  
  /**
   * @return current {@link PresenterQuestion}
   */
  public PresenterQuestion getPresenterQuestion() {
    return this.presQuestion;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public PresenterGraduation getControllerVueCorrection() {
    return this.graduationPresenter;
  }
  
  @Override
  public PresenterPdf getPresenterPdf() {
    return this.presPdf;
  }
  
  /**
   * @return current {@link PresenterStudentListLoader}
   */
  public PresenterStudentListLoader getPresenterStudentList() {
    return this.studentListPresenter;
  }
  
  /**
   * @return current {@link PresenterStudentSheetLoader}
   */
  public PresenterGraduationLoader getPresenterGraduationLoader() {
    return this.graduationLoaderPresenter;
  }
  
  public void openEditionTemplate(final String path) {
    this.service.openCreationTemplate(path);
  }
  
  public void exportGrades() {
    new GradesExportImpl(this.service).exportGrades();
  }
  
  public int getPageAmount() {
    return this.service.getPageAmount();
  }
  
  public boolean openCorrectionPdf(final File file) {
    try {
      final PDDocument document = PDDocument.load(file);
      final ByteArrayOutputStream stream = new ByteArrayOutputStream();
      document.save(stream);
      this.presPdf.create("", file);
      int _pageAmount = this.getPageAmount();
      final PdfReaderWithoutQrCodeImpl pdfReader = new PdfReaderWithoutQrCodeImpl(document, _pageAmount, 3);
      pdfReader.readPDf();
      final Collection<StudentSheet> studentSheets = pdfReader.getCompleteStudentSheets();
      return this.service.initializeCorrection(studentSheets);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean applyGrade(final int questionId, final int gradeId) {
    return this.service.assignGradeEntry(questionId, gradeId);
  }
  
  public void removeGrade(final int questionId, final int gradeId) {
    this.service.retractGradeEntry(questionId, gradeId);
  }
  
  public List<Integer> getEntryIds(final int questionId) {
    LinkedList<Integer> _xblockexpression = null;
    {
      List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
      int _size = l.size();
      String _plus = ("presenter size :" + Integer.valueOf(_size));
      InputOutput.<String>print(_plus);
      LinkedList<Integer> result = new LinkedList<Integer>();
      for (final Tuple3<Integer, String, Float> t : l) {
        result.add(t._1);
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public List<Integer> getSelectedEntryIds(final int questionId) {
    return this.service.getQuestionSelectedGradeEntries(questionId);
  }
  
  public String getEntryText(final int entryId, final int questionId) {
    String _xblockexpression = null;
    {
      List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
      for (final Tuple3<Integer, String, Float> t : l) {
        if ((entryId == (t._1).intValue())) {
          return t._2;
        }
      }
      _xblockexpression = "Entry not found";
    }
    return _xblockexpression;
  }
  
  public float getEntryWorth(final int entryId, final int questionId) {
    List<Tuple3<Integer, String, Float>> l = this.service.getQuestionGradeEntries(questionId);
    for (final Tuple3<Integer, String, Float> t : l) {
      if ((entryId == (t._1).intValue())) {
        return (t._3).floatValue();
      }
    }
    return (-1);
  }
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée
   */
  public int addEntry(final int questionId, final String desc, final float point) {
    return this.service.addEntry(questionId, desc, point);
  }
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  public void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point) {
    this.service.modifyEntry(questionId, gradeEntryId, desc, point);
  }
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  public void removeEntry(final int questionId, final int gradeEntryId) {
    this.service.removeEntry(questionId, gradeEntryId);
  }
  
  /**
   * Retourne la note globale de la copie
   * @return Note globale de la copie
   * //FIXME doit être lié au service
   */
  public float getGlobalGrade() {
    return 0.0f;
  }
  
  /**
   * Retourne le barème total de l'examen
   * @return Barème total de l'examen
   * //FIXME doit être lié au service
   */
  public float getGlobalScale() {
    return 0.0f;
  }
  
  /**
   * SAVING
   */
  public void saveTemplate(final String path) {
    this.service.saveCorrectionTemplate(path, this.presPdf.getPdfOutputStream());
  }
  
  /**
   * STUDENTS
   */
  public List<String> getStudentsSuggestedNames(final String start) {
    final Function<List<String>, List<String>> _function = (List<String> l) -> {
      final Function1<String, Boolean> _function_1 = (String n) -> {
        return Boolean.valueOf(n.toLowerCase().contains(start.toLowerCase()));
      };
      return IterableExtensions.<String>toList(IterableExtensions.<String>filter(l, _function_1));
    };
    return StudentDataManager.getAllNames().<List<String>>map(_function).orElse(List.<String>of());
  }
  
  public LinkedList<Integer> getStudentIds() {
    LinkedList<Integer> _xblockexpression = null;
    {
      Collection<StudentSheet> list = this.service.getStudentSheets();
      LinkedList<Integer> result = new LinkedList<Integer>();
      if ((list != null)) {
        for (final StudentSheet s : list) {
          result.add(Integer.valueOf(s.getId()));
        }
      } else {
        PresenterGraduation.logger.warn("Service returned null studentId list");
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public void renameStudent(final int studentId, final String newname) {
    this.service.assignStudentId(newname);
  }
  
  public int getAbsolutePage(final int studentId, final int pageNumber) {
    return this.service.getAbsolutePageNumber(studentId, pageNumber);
  }
  
  /**
   * --LOADING TEMPLATE--
   */
  public LinkedList<Integer> initLoading(final int pageNumber) {
    LinkedList<Integer> _xblockexpression = null;
    {
      this.questions = this.service.getQuestionAtPage(pageNumber);
      LinkedList<Integer> ids = new LinkedList<Integer>();
      for (final Question q : this.questions) {
        ids.add(Integer.valueOf(q.getId()));
      }
      _xblockexpression = ids;
    }
    return _xblockexpression;
  }
  
  private List<Question> questions;
  
  /**
   * Loads the next question into questionToLoad
   * if there is a new question, return true,
   * else return false
   */
  public double questionX(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getX();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionY(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getY();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionHeight(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getHeigth();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public double questionWidth(final int id) {
    double _xblockexpression = (double) 0;
    {
      double result = (-1.0);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getZone().getWidth();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public String questionName(final int id) {
    String _xblockexpression = null;
    {
      String result = "";
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = q.getName();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public float questionWorth(final int id) {
    float _xblockexpression = (float) 0;
    {
      float result = (-1f);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          GradeScale _gradeScale = q.getGradeScale();
          boolean _tripleNotEquals = (_gradeScale != null);
          if (_tripleNotEquals) {
            result = q.getGradeScale().getMaxPoint();
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
}
