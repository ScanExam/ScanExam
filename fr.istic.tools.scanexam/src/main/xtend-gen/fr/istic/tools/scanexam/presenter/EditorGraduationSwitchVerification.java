package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.core.Exam;

@SuppressWarnings("all")
public class EditorGraduationSwitchVerification {
  private static Exam instanceOfExam;
  
  public static Exam saveExamInstance(final Exam exam) {
    return EditorGraduationSwitchVerification.instanceOfExam = exam;
  }
  
  public static Exam loadExamInstance() {
    return EditorGraduationSwitchVerification.instanceOfExam;
  }
}
