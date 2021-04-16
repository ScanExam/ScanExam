package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
  private PresenterEdition presenter;
  
  private ServiceEdition service;
  
  public PresenterQuestionZone(final ServiceEdition s, final PresenterEdition p) {
    Objects.<ServiceEdition>requireNonNull(s);
    Objects.<PresenterEdition>requireNonNull(p);
    this.service = s;
    this.presenter = p;
  }
  
  /**
   * setter for the PresenterVueCreation attribute
   * @param {@link PresenterVueCreation} pres instance of the presenter (not null)
   */
  public PresenterEdition setPresenterVueCreation(final PresenterEdition pres) {
    PresenterEdition _xblockexpression = null;
    {
      Objects.<PresenterEdition>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link PresenterVueCreation}
   */
  public PresenterEdition getPresenterVueCreation() {
    return this.presenter;
  }
  
  public int createQuestion(final double x, final double y, final double height, final double width) {
    return this.service.createQuestion(this.presenter.getPresenterPdf().pdfPageIndex, ((float) x), ((float) y), ((float) height), ((float) width));
  }
  
  public void removeQuestion(final int ID) {
    this.service.removeQuestion(ID);
  }
  
  public void renameQuestion(final int ID, final String name) {
    this.service.renameQuestion(ID, name);
  }
  
  public void resizeQuestion(final int ID, final double height, final double width) {
    this.service.rescaleQuestion(ID, ((float) height), ((float) width));
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
  
  public void changeQuestionWorth(final int id, final float worth) {
    this.service.modifyMaxPoint(id, worth);
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
  
  public float questionWorth(final int id) {
    float _xblockexpression = (float) 0;
    {
      float result = 0f;
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
