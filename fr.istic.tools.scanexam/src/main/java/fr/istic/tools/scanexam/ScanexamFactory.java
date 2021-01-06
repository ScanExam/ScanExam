/**
 */
package fr.istic.tools.scanexam;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.istic.tools.scanexam.ScanexamPackage
 * @generated
 */
public interface ScanexamFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScanexamFactory eINSTANCE = fr.istic.tools.scanexam.impl.ScanexamFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Exam</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exam</em>'.
	 * @generated
	 */
	Exam createExam();

	/**
	 * Returns a new object of class '<em>Info Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Info Field</em>'.
	 * @generated
	 */
	InfoField createInfoField();

	/**
	 * Returns a new object of class '<em>Question</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Question</em>'.
	 * @generated
	 */
	Question createQuestion();

	/**
	 * Returns a new object of class '<em>Scan Zone</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scan Zone</em>'.
	 * @generated
	 */
	ScanZone createScanZone();

	/**
	 * Returns a new object of class '<em>Grading Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Grading Data</em>'.
	 * @generated
	 */
	GradingData createGradingData();

	/**
	 * Returns a new object of class '<em>Student Grade</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Student Grade</em>'.
	 * @generated
	 */
	StudentGrade createStudentGrade();

	/**
	 * Returns a new object of class '<em>Question Grade</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Question Grade</em>'.
	 * @generated
	 */
	QuestionGrade createQuestionGrade();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ScanexamPackage getScanexamPackage();

} //ScanexamFactory
