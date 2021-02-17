package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.view.ControllerVueCorrection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("all")
public class FXMockBackend extends ControllerVueCorrection {
  public static class Question {
    private String name;
    
    private double x;
    
    private double y;
    
    private double h;
    
    private double w;
    
    private int page;
    
    private List<Integer> bareme;
    
    public Question(final String name) {
      this.name = name;
      this.x = 0;
      this.y = 0;
      this.h = 0;
      this.w = 0;
    }
  }
  
  private ArrayList<FXMockBackend.Question> questions = new ArrayList<FXMockBackend.Question>();
  
  private FXMockBackend.Question currentQuestion;
  
  private int index;
  
  public void showCurrentQuestion() {
    this.getControllerFX().setSelectedQuestion();
  }
  
  public void nextQuestion() {
    this.index++;
    int _size = this.questions.size();
    boolean _greaterEqualsThan = (this.index >= _size);
    if (_greaterEqualsThan) {
      this.index = 0;
    }
    this.currentQuestion = this.questions.get(this.index);
    this.getControllerFX().setSelectedQuestion();
  }
  
  public void previousQuestion() {
    this.index--;
    if ((this.index < 0)) {
      int _size = this.questions.size();
      int _minus = (_size - 1);
      this.index = _minus;
    }
    this.currentQuestion = this.questions.get(this.index);
    this.getControllerFX().setSelectedQuestion();
  }
  
  public void selectQuestion(final String name) {
  }
  
  public String selectedName() {
    return this.currentQuestion.name;
  }
  
  public double selectedX() {
    return this.currentQuestion.x;
  }
  
  public double selectedY() {
    return this.currentQuestion.y;
  }
  
  public double selectedHeight() {
    return this.currentQuestion.h;
  }
  
  public double selectedWidth() {
    return this.currentQuestion.w;
  }
  
  public void setQuestions() {
    FXMockBackend.Question Q1 = new FXMockBackend.Question("Q1");
    Q1.x = 0;
    Q1.y = 0;
    Q1.h = 200;
    Q1.w = 100;
    FXMockBackend.Question Q2 = new FXMockBackend.Question("Q2");
    Q2.x = 30;
    Q2.y = 200;
    Q2.h = 520;
    Q2.w = 50;
    FXMockBackend.Question Q3 = new FXMockBackend.Question("Q3");
    Q3.x = 100;
    Q3.y = 300;
    Q3.h = 400;
    Q3.w = 100;
    FXMockBackend.Question Q4 = new FXMockBackend.Question("Q4");
    Q4.x = 130;
    Q4.y = 500;
    Q4.h = 170;
    Q4.w = 100;
    this.questions.add(Q1);
    this.questions.add(Q2);
    this.questions.add(Q3);
    this.questions.add(Q4);
    this.currentQuestion = this.questions.get(0);
    LinkedList<String> stringList = new LinkedList<String>();
    for (final FXMockBackend.Question q : this.questions) {
      stringList.add(q.name);
    }
    this.getControllerFX().initQuestions(stringList);
    this.index = 0;
    this.showCurrentQuestion();
  }
}
