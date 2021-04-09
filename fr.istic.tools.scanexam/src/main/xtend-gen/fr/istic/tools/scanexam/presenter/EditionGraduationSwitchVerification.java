package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Exam;

@SuppressWarnings("all")
public class EditionGraduationSwitchVerification {
  private static Exam instanceOfExam;
  
  public static Exam saveExamInstance(final Exam exam) {
    return EditionGraduationSwitchVerification.instanceOfExam = exam;
  }
  
  public static Exam loadExamInstance() {
    return EditionGraduationSwitchVerification.instanceOfExam;
  }
}
