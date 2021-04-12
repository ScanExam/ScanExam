package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.GradesExportImpl;
import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterCopy;
import fr.istic.tools.scanexam.presenter.PresenterCorrectionLoader;
import fr.istic.tools.scanexam.presenter.PresenterGradeScale;
import fr.istic.tools.scanexam.presenter.PresenterGraduationLoader;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQuestion;
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import fr.istic.tools.scanexam.utils.Tuple3;
import fr.istic.tools.scanexam.view.Adapter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
  
  private PresenterCopy presCopy;
  
  private PresenterGradeScale presMarkingScheme;
  
  private PresenterGraduation graduationPresenter;
  
  private PresenterPdf presPdf;
  
  private PresenterStudentListLoader studentListPresenter;
  
  private PresenterGraduationLoader studentSheetPresenter;
  
  private PresenterCorrectionLoader correctionLoaderPresenter;
  
  private ServiceGraduation service;
  
  private Adapter<PresenterGraduation> adapter;
  
  public PresenterGraduation(final ServiceGraduation service) {
    Objects.<ServiceGraduation>requireNonNull(service);
    this.service = service;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
    PresenterQuestion _presenterQuestion = new PresenterQuestion(service);
    this.presQuestion = _presenterQuestion;
    PresenterStudentListLoader _presenterStudentListLoader = new PresenterStudentListLoader(service);
    this.studentListPresenter = _presenterStudentListLoader;
    PresenterGraduationLoader _presenterGraduationLoader = new PresenterGraduationLoader(service, null);
    this.studentSheetPresenter = _presenterGraduationLoader;
    PresenterCorrectionLoader _presenterCorrectionLoader = new PresenterCorrectionLoader(service);
    this.correctionLoaderPresenter = _presenterCorrectionLoader;
  }
  
  public PresenterGraduation(final Adapter<PresenterGraduation> adapter, final ServiceGraduation service) {
    this(service);
    Objects.<Adapter<PresenterGraduation>>requireNonNull(adapter);
    this.adapter = adapter;
  }
  
  /**
   * @return current {@link PresenterQuestion}
   */
  public PresenterQuestion getPresenterQuestion() {
    return this.presQuestion;
  }
  
  /**
   * @return current {@link PresenterCopy}
   */
  public PresenterCopy getPresenterCopy() {
    return this.presCopy;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterGradeScale getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public PresenterGraduation getControllerVueCorrection() {
    return this.graduationPresenter;
  }
  
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
  public PresenterGraduationLoader getPresenterStudentSheet() {
    return this.studentSheetPresenter;
  }
  
  /**
   * @return current {@link PresenterCorrectionLoader}
   */
  public PresenterCorrectionLoader getPresenterCorrectionLoader() {
    return this.correctionLoaderPresenter;
  }
  
  public void openEditionTemplate(final String path) {
    this.service.openCreationTemplate(path);
  }
  
  public void exportGrades() {
    new GradesExportImpl(this.service).exportGrades();
  }
  
  public boolean openCorrectionPdf(final File file) {
    try {
      final PDDocument document = PDDocument.load(file);
      final ByteArrayOutputStream stream = new ByteArrayOutputStream();
      document.save(stream);
      this.presPdf.create(file);
      int _size = ExamSingleton.instance.getPages().size();
      final PdfReaderWithoutQrCodeImpl pdfReader = new PdfReaderWithoutQrCodeImpl(document, _size, 3);
      pdfReader.readPDf();
      final Collection<StudentSheet> studentSheets = pdfReader.getCompleteStudentSheets();
      return this.service.initializeCorrection(studentSheets);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean applyGrade(final int questionId, final int gradeId) {
    return this.service.addGradeEntry(questionId, gradeId);
  }
  
  public boolean removeGrade(final int questionId, final int gradeId) {
    return this.service.removeGradeEntry(questionId, gradeId);
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
  public boolean removeEntry(final int questionId, final int gradeEntryId) {
    return this.service.removeEntry(questionId, gradeEntryId);
  }
  
  public LinkedList<Integer> getStudentIds() {
    LinkedList<Integer> _xblockexpression = null;
    {
      Collection<StudentSheet> list = this.service.getStudentSheets();
      InputOutput.<Integer>print(Integer.valueOf(list.size()));
      LinkedList<Integer> result = new LinkedList<Integer>();
      for (final StudentSheet s : list) {
        result.add(Integer.valueOf(s.getId()));
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public int getAbsolutePage(final int studentId, final int pageNumber) {
    return this.service.getAbsolutePageNumber(studentId, pageNumber);
  }
  
  /**
   * --LOADING NEW TEMPLATE--
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
  
  public int getTemplatePageAmount() {
    return ExamSingleton.instance.getPages().size();
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
          InputOutput.<String>print(("h = " + Double.valueOf(result)));
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
          InputOutput.<String>print(("w = " + Double.valueOf(result)));
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
