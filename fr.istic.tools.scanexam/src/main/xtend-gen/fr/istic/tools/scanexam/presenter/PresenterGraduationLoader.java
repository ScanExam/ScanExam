package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.io.ByteArrayInputStream;

@SuppressWarnings("all")
public class PresenterGraduationLoader {
  private final ServiceGraduation service;
  
  public PresenterGraduationLoader(final ServiceGraduation graduation) {
    this.service = graduation;
  }
  
  public boolean hasTemplateLoaded() {
    return this.service.hasExamLoaded();
  }
  
  public ByteArrayInputStream getPdfInputStream() {
    return this.service.getEditionPdfInputStream();
  }
  
  public boolean loadTemplate(final String path) {
    return this.service.openCreationTemplate(path);
  }
  
  public boolean loadStudentSheets(final String path) {
    return true;
  }
}
