package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import java.io.File;
import java.io.FileInputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PresenterStudentSheetExport {
  public boolean export(final File file, final int number) {
    try {
      boolean _xblockexpression = false;
      {
        final QRCodeGenerator generator = new QRCodeGeneratorImpl();
        FileInputStream _fileInputStream = new FileInputStream(file);
        _xblockexpression = generator.createAllExamCopies(_fileInputStream, number);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
