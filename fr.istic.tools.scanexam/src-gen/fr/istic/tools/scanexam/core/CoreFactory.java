/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.istic.tools.scanexam.core.CorePackage
 * @generated
 */
public interface CoreFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoreFactory eINSTANCE = fr.istic.tools.scanexam.core.impl.CoreFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Question Zone</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Question Zone</em>'.
	 * @generated
	 */
	QuestionZone createQuestionZone();

	/**
	 * Returns a new object of class '<em>Question</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Question</em>'.
	 * @generated
	 */
	Question createQuestion();

	/**
	 * Returns a new object of class '<em>Grade Scale</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Grade Scale</em>'.
	 * @generated
	 */
	GradeScale createGradeScale();

	/**
	 * Returns a new object of class '<em>Grade Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Grade Entry</em>'.
	 * @generated
	 */
	GradeEntry createGradeEntry();

	/**
	 * Returns a new object of class '<em>Exam</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exam</em>'.
	 * @generated
	 */
	Exam createExam();

	/**
	 * Returns a new object of class '<em>Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Page</em>'.
	 * @generated
	 */
	Page createPage();

	/**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Text Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Comment</em>'.
	 * @generated
	 */
	TextComment createTextComment();

	/**
	 * Returns a new object of class '<em>Handwriting Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Handwriting Comment</em>'.
	 * @generated
	 */
	HandwritingComment createHandwritingComment();

	/**
	 * Returns a new object of class '<em>Grade</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Grade</em>'.
	 * @generated
	 */
	Grade createGrade();

	/**
	 * Returns a new object of class '<em>Student Sheet</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Student Sheet</em>'.
	 * @generated
	 */
	StudentSheet createStudentSheet();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorePackage getCorePackage();

} //CoreFactory
