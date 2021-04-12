package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.ServiceEdition;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.io.File;
import java.io.FileOutputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PresenterGraduationLoader {
  private final ServiceGraduation service;
  
  private final ServiceEdition edition;
  
  public PresenterGraduationLoader(final ServiceGraduation graduation, final ServiceEdition edition) {
    this.service = graduation;
    this.edition = edition;
  }
  
  public boolean hasTemplateLoaded() {
    return this.service.hasExamLoaded();
  }
  
  public boolean loadTemplate(final String path) {
    return this.service.openCreationTemplate(path);
  }
  
  public boolean loadStudentSheets(final String path, final int quantity) {
    try {
      boolean _xblockexpression = false;
      {
        final QRCodeGenerator generator = new QRCodeGeneratorImpl();
        File _file = new File(path);
        FileOutputStream _fileOutputStream = new FileOutputStream(_file);
        generator.createAllExamCopies(null, _fileOutputStream, this.service.getExamName(), quantity);
        _xblockexpression = true;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
