package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.mailing.GradesExportMail;
import fr.istic.tools.scanexam.mailing.SendMailXtend;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GradesExportMailImpl implements GradesExportMail {
  private ExamGraduationService service;
  
  public GradesExportMailImpl(final ExamGraduationService serv) {
    this.service = serv;
  }
  
  public void exportGradesMail(final String user, final String password, final String path) {
    try {
      final SendMailXtend mailSender = new SendMailXtend();
      mailSender.setSender(user);
      mailSender.setSenderPassword(password);
      final LinkedHashMap<String, String> myMap = CollectionLiterals.<String, String>newLinkedHashMap();
      final InputStream file = ResourcesUtils.getInputStreamResource("mailing/mailMap.csv");
      InputStreamReader _inputStreamReader = new InputStreamReader(file);
      final BufferedReader reader = new BufferedReader(_inputStreamReader);
      while (reader.ready()) {
        {
          final String line = reader.readLine();
          myMap.put(line.split(",")[0], line.split(",")[1]);
        }
      }
      reader.close();
      int _size = this.service.getStudentSheets().size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final String name = (((StudentSheet[])Conversions.unwrapArray(this.service.getStudentSheets(), StudentSheet.class))[(i).intValue()]).getStudentName();
          final String adresse = myMap.get(name);
          mailSender.setRecipent(adresse);
          mailSender.setTitle("Controle");
          float _computeGrade = (((StudentSheet[])Conversions.unwrapArray(this.service.getStudentSheets(), StudentSheet.class))[(i).intValue()]).computeGrade();
          String _plus = ("Note :" + Float.valueOf(_computeGrade));
          mailSender.setMessage(_plus);
          mailSender.setPieceJointe("");
          mailSender.sendMailXtend();
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
