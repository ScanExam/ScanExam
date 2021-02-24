package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.view.fX.ControllerFX;
import fr.istic.tools.scanexam.view.fX.CorrectorAdapterFX;
import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("all")
public class MockFXAdapter extends CorrectorAdapterFX {
  private ArrayList<Question> questions = new ArrayList<Question>();
  
  private Question currentQuestion;
  
  private int index;
  
  public ControllerFX controller;
  
  public LinkedList<String> questionNames() {
    LinkedList<String> _xblockexpression = null;
    {
      LinkedList<String> stringList = new LinkedList<String>();
      for (final Question q : this.questions) {
        stringList.add(q.getName());
      }
      _xblockexpression = stringList;
    }
    return _xblockexpression;
  }
  
  @Override
  public void nextQuestion() {
    this.index++;
    int _size = this.questions.size();
    boolean _greaterEqualsThan = (this.index >= _size);
    if (_greaterEqualsThan) {
      this.index = 0;
    }
    this.currentQuestion = this.questions.get(this.index);
    this.controller.showQuestion(this.currentQuestion);
  }
  
  @Override
  public void previousQuestion() {
    this.index--;
    if ((this.index < 0)) {
      int _size = this.questions.size();
      int _minus = (_size - 1);
      this.index = _minus;
    }
    this.currentQuestion = this.questions.get(this.index);
    this.controller.showQuestion(this.currentQuestion);
  }
  
  public void selectQuestion(final String name) {
  }
  
  public String selectedName() {
    return this.currentQuestion.getName();
  }
  
  public void setQuestions() {
    DataFactory df = new DataFactory();
    Question Q1 = df.createQuestion(0);
    Q1.setZone(df.createRectangle(0, 0, 210, 100));
    Q1.setName("Question 1");
    Question Q2 = df.createQuestion(2);
    Q2.setZone(df.createRectangle(30, 200, 520, 50));
    Q2.setName("Question 2");
    Question Q3 = df.createQuestion(3);
    Q3.setZone(df.createRectangle(100, 300, 400, 100));
    Q3.setName("Question 3");
    Question Q4 = df.createQuestion(4);
    Q4.setZone(df.createRectangle(130, 500, 170, 100));
    Q4.setName("Question 4");
    this.questions.add(Q1);
    this.questions.add(Q2);
    this.questions.add(Q3);
    this.questions.add(Q4);
    this.currentQuestion = this.questions.get(0);
    LinkedList<String> stringList = new LinkedList<String>();
    for (final Question q : this.questions) {
      stringList.add(q.getName());
    }
    this.controller.initQuestionNames(stringList);
    this.index = 0;
    this.controller.showQuestion(this.questions.get(0));
  }
}
