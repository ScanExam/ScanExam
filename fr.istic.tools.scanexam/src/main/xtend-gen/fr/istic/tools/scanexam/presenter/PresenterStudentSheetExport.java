package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.ServiceEdition;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PresenterStudentSheetExport {
  private final ServiceEdition service;
  
  public PresenterStudentSheetExport(final ServiceEdition edition) {
    this.service = edition;
  }
  
  public boolean export(final File file, final int number) {
    try {
      boolean _xblockexpression = false;
      {
        final QRCodeGenerator generator = new QRCodeGeneratorImpl();
        ByteArrayInputStream _editionPdfInputStream = this.service.getEditionPdfInputStream();
        FileOutputStream _fileOutputStream = new FileOutputStream(file);
        generator.createAllExamCopies(_editionPdfInputStream, _fileOutputStream, this.service.getExamName(), number);
        _xblockexpression = true;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
