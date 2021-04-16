package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import java.io.File;

@SuppressWarnings("all")
public class PresenterStudentSheetExport {
  private final ServiceEdition service;
  
  private final PresenterPdf presPdf;
  
  public PresenterStudentSheetExport(final ServiceEdition edition, final PresenterPdf presPdf) {
    this.service = edition;
    this.presPdf = presPdf;
  }
  
  public boolean export(final File file, final int number) {
    boolean _xblockexpression = false;
    {
      final QRCodeGenerator generator = new QRCodeGeneratorImpl();
      generator.createAllExamCopies(this.presPdf.getPdfInputStream(), file, this.service.getExamName(), number);
      _xblockexpression = true;
    }
    return _xblockexpression;
  }
}
