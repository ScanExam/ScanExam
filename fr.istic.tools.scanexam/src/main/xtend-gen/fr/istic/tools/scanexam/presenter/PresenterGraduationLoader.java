package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterImportExportXMI;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.ServiceGraduation;

@SuppressWarnings("all")
public class PresenterGraduationLoader {
  private final ServiceGraduation service;
  
  private final PresenterImportExportXMI presenterXMI;
  
  public PresenterGraduationLoader(final PresenterImportExportXMI presenterXMI, final ServiceGraduation graduation) {
    this.presenterXMI = presenterXMI;
    this.service = graduation;
  }
  
  public boolean hasTemplateLoaded() {
    return this.service.hasExamLoaded();
  }
  
  public boolean loadTemplate(final String path) {
    return this.presenterXMI.loadExamCreationTemplate(path);
  }
  
  public boolean loadStudentSheets(final String path) {
    boolean _xblockexpression = false;
    {
      final QRCodeGenerator generator = new QRCodeGeneratorImpl();
      _xblockexpression = true;
    }
    return _xblockexpression;
  }
}
