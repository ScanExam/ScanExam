package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.Service;
import java.util.Set;

@SuppressWarnings("all")
public class ExamGraduationService extends Service {
  private int currentSheetIndex;
  
  private int currentQuestionIndex;
  
  private Set<StudentSheet> studentSheets;
  
  private Set<StudentSheet> visibleSheets;
  
  @Override
  public void save(final String path) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void open(final String xmiFile) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void nextPage() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void previousPage() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
