package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterCopy;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQuestion;
import fr.istic.tools.scanexam.services.ExamGraduationService;
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
  
  /**
   * @return next question
   */
  public int getNextQuestion(final int question) {
    return this.presQuestion.getNextQuestion(question);
  }
  
  /**
   * @param question is the actual question
   * @return previous question
   */
  public int getPreviousQuestion(final int question) {
    return this.presQuestion.getPreviousQuestion(question);
  }
  
  public void openEditionTemplate(final String path) {
    this.service.openCreationTemplate(path);
  }
  
  public void openCorrectionPdf(final String path) {
    this.service.openCorrectionPdf(path);
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
}
