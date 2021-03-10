package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Class to manage conversions of the view's questions
 * delimitation of a question's zone for the model
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterQuestionZone {
  /**
   * Presenter for the creation view
   */
  private EditorPresenter presenter;
  
  private ExamEditionService service;
  
  public PresenterQuestionZone(final ExamEditionService s, final EditorPresenter p) {
    Objects.<ExamEditionService>requireNonNull(s);
    Objects.<EditorPresenter>requireNonNull(p);
    this.service = s;
    this.presenter = p;
  }
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public EditorPresenter setPresenterVueCreation(final EditorPresenter pres) {
    EditorPresenter _xblockexpression = null;
    {
      Objects.<EditorPresenter>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public EditorPresenter getPresenterVueCreation() {
    return this.presenter;
  }
  
  public int createQuestion(final double x, final double y, final double height, final double width) {
    return this.service.createQuestion(((float) x), ((float) y), ((float) height), ((float) width));
  }
  
  public void removeQuestion(final int ID) {
    this.service.removeQuestion(ID);
  }
  
  public void renameQuestion(final int ID, final String name) {
    this.service.renameQuestion(ID, name);
  }
  
  public void resizeQuestion(final int ID, final double height, final double width) {
    this.service.rescaleQuestion(ID, ((float) height), ((float) height));
  }
  
  /**
   * changes the x and y coordinates of the {@link Question} identified by the id
   * @param int id : the unique ID of question
   * @param float x : new x position
   * @param float y : new y position
   * @author : Benjamin Danlos
   */
  public void moveQuestion(final int id, final double x, final double y) {
    this.service.moveQuestion(id, ((float) x), ((float) y));
  }
  
  public void changeQuestionWorth(final int id, final int worth) {
  }
  
  /**
   * --LOADING NEW TEMPLATE--
   */
  public LinkedList<Integer> initLoading() {
    LinkedList<Integer> _xblockexpression = null;
    {
      LinkedList<Integer> _linkedList = new LinkedList<Integer>();
      this.pageNumbers = _linkedList;
      LinkedList<Question> _linkedList_1 = new LinkedList<Question>();
      this.questions = _linkedList_1;
      LinkedList<Integer> ids = new LinkedList<Integer>();
      for (final Question q : this.questions) {
        ids.add(Integer.valueOf(q.getId()));
      }
      _xblockexpression = ids;
    }
    return _xblockexpression;
  }
  
  private LinkedList<Integer> pageNumbers;
  
  private LinkedList<Question> questions;
  
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
  
  public int questionPage(final int id) {
    int _xblockexpression = (int) 0;
    {
      int result = (-1);
      for (final Question q : this.questions) {
        int _id = q.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          result = (this.pageNumbers.get(this.questions.indexOf(q))).intValue();
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public int questionWorth(final int id) {
    return 0;
  }
}
