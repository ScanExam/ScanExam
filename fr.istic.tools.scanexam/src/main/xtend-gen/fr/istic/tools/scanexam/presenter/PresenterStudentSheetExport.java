package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import java.io.File;

@SuppressWarnings("all")
public class PresenterStudentSheetExport {
  public void export(final File file, final int number) {
    final QRCodeGenerator generator = new QRCodeGeneratorImpl();
  }
}
