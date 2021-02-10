package fr.istic.tools.scanexam.sessions;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.sessions.Session;
import java.io.File;
import java.util.Set;

@SuppressWarnings("all")
public class CorrectionSession extends Session {
  private int currentSheetIndex;
  
  private int currentQuestionIndex;
  
  private Set<StudentSheet> studentSheets;
  
  private Set<StudentSheet> visibleSheets;
  
  @Override
  public void save(final String path) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void open(final File xmiFile) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
