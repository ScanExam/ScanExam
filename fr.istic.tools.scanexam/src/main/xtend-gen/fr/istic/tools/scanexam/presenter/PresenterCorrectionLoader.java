package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.services.ServiceGraduation;

@SuppressWarnings("all")
public class PresenterCorrectionLoader {
  private final ServiceGraduation service;
  
  public PresenterCorrectionLoader(final ServiceGraduation graduation) {
    this.service = graduation;
  }
  
  public boolean hasTemplateLoaded() {
    return this.service.hasExamLoaded();
  }
  
  public boolean loadTemplate(final String path) {
    return this.service.openCreationTemplate(path);
  }
  
  public boolean loadCorrection(final String path) {
    return true;
  }
}
