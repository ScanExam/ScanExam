package fr.istic.tools.scanexam.mailing;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.mailing.GradesExportMail;
import fr.istic.tools.scanexam.mailing.SendMailXtend;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GradesExportMailImpl implements GradesExportMail {
  private static ServiceGraduation service;
  
  public GradesExportMailImpl(final ServiceGraduation serv) {
    GradesExportMailImpl.service = serv;
  }
  
  private static Config instance;
  
  @Override
  public void exportGradesMail(final File pdf) {
    this.exportGradesMail1(pdf, GradesExportMailImpl.instance.getEmail(), GradesExportMailImpl.instance.getEmailPassword(), GradesExportMailImpl.service.getStudentSheets().size(), GradesExportMailImpl.service.getExamName(), GradesExportMailImpl.service.getStudentSheets());
  }
  
  @Override
  public void exportGradesMail1(final File pdf, final String mail, final String mdp, final int taille, final String nameExam, final Collection<StudentSheet> studentSheetss) {
    final SendMailXtend mailSender = new SendMailXtend();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, taille, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        mailSender.setRecipent((((StudentSheet[])Conversions.unwrapArray(studentSheetss, StudentSheet.class))[(i).intValue()]).getStudentName());
        mailSender.setTitle(nameExam);
        mailSender.setMessage("Note :");
        File _absoluteFile = pdf.getAbsoluteFile();
        String _plus = (_absoluteFile + ".pdf");
        mailSender.setPieceJointe(_plus);
        mailSender.sendMailXtend();
      }
    }
    Logger.getGlobal().info("Les mails ont été envoyés");
  }
}
