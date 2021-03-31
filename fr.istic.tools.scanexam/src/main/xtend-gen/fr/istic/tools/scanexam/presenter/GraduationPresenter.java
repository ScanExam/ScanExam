package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.GradesExportImpl;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.utils.Tuple3;
import fr.istic.tools.scanexam.view.Adapter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Class defining the presenter for the exam correction view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class GraduationPresenter implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQuestion presQuestion;
  
  private PresenterCopy presCopy;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private GraduationPresenter graduationPresenter;
  
  private PresenterPdf presPdf;
  
  private ExamGraduationService service;
  
  private Adapter<GraduationPresenter> adapter;
  
  public GraduationPresenter(final Adapter<GraduationPresenter> adapter, final ExamGraduationService service) {
    Objects.<ExamGraduationService>requireNonNull(service);
    this.service = service;
    Objects.<Adapter<GraduationPresenter>>requireNonNull(adapter);
    this.adapter = adapter;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
    PresenterQuestion _presenterQuestion = new PresenterQuestion(service);
    this.presQuestion = _presenterQuestion;
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
  @Override
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  /**
   * @return current {@link ControllerVueCorrection}
   */
  public GraduationPresenter getControllerVueCorrection() {
    return this.graduationPresenter;
  }
  
  @Override
  public PresenterPdf getPresenterPdf() {
    return this.presPdf;
  }
  
  public void openEditionTemplate(final String path) {
    this.service.openCreationTemplate(path);
  }
  
  public void exportGrades() {
    new GradesExportImpl(this.service).exportGrades();
  }
  
  public void openCorrectionPdf(final String path) {
    this.service.openCorrectionPdf(path);
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
    return this.service.getTemplatePageAmount();
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
    int _xblockexpression = (int) 0;
    {
      int result = (-1);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = (-1);
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
}
