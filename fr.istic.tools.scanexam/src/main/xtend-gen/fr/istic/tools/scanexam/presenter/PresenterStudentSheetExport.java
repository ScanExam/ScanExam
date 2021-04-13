package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.ServiceEdition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PresenterStudentSheetExport {
  private final ServiceEdition service;
  
  private final PresenterPdf presPdf;
  
  public PresenterStudentSheetExport(final ServiceEdition edition, final PresenterPdf presPdf) {
    this.service = edition;
    this.presPdf = presPdf;
  }
  
  public boolean export(final File file, final int number) {
    try {
      boolean _xblockexpression = false;
      {
        final QRCodeGenerator generator = new QRCodeGeneratorImpl();
        InputStream _pdfInputStream = this.presPdf.getPdfInputStream();
        FileOutputStream _fileOutputStream = new FileOutputStream(file);
        generator.createAllExamCopies(_pdfInputStream, _fileOutputStream, this.service.getExamName(), number);
        _xblockexpression = true;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
