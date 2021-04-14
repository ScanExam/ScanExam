package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.ServiceEdition;
import java.io.ByteArrayInputStream;
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
      ByteArrayInputStream _pdfInputStream = this.presPdf.getPdfInputStream();
      String _xifexpression = null;
      String _examName = this.service.getExamName();
      boolean _tripleEquals = (_examName == null);
      if (_tripleEquals) {
        _xifexpression = "foo";
      } else {
        _xifexpression = this.service.getExamName();
      }
      generator.createAllExamCopies(_pdfInputStream, file, _xifexpression, number);
      _xblockexpression = true;
    }
    return _xblockexpression;
  }
}
