/**
 */
package fr.istic.tools.scanexam;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Question Grade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.QuestionGrade#getQuestion <em>Question</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.QuestionGrade#isValidated <em>Validated</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.QuestionGrade#getFilename <em>Filename</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.QuestionGrade#getGrade <em>Grade</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestionGrade()
 * @model
 * @generated
 */
public interface QuestionGrade extends EObject {
	/**
	 * Returns the value of the '<em><b>Question</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Question</em>' reference.
	 * @see #setQuestion(Question)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestionGrade_Question()
	 * @model
	 * @generated
	 */
	Question getQuestion();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.QuestionGrade#getQuestion <em>Question</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Question</em>' reference.
	 * @see #getQuestion()
	 * @generated
	 */
	void setQuestion(Question value);

	/**
	 * Returns the value of the '<em><b>Validated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validated</em>' attribute.
	 * @see #setValidated(boolean)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestionGrade_Validated()
	 * @model unique="false"
	 * @generated
	 */
	boolean isValidated();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.QuestionGrade#isValidated <em>Validated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validated</em>' attribute.
	 * @see #isValidated()
	 * @generated
	 */
	void setValidated(boolean value);

	/**
	 * Returns the value of the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename</em>' attribute.
	 * @see #setFilename(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestionGrade_Filename()
	 * @model unique="false"
	 * @generated
	 */
	String getFilename();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.QuestionGrade#getFilename <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filename</em>' attribute.
	 * @see #getFilename()
	 * @generated
	 */
	void setFilename(String value);

	/**
	 * Returns the value of the '<em><b>Grade</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grade</em>' attribute.
	 * @see #setGrade(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestionGrade_Grade()
	 * @model unique="false"
	 * @generated
	 */
	String getGrade();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.QuestionGrade#getGrade <em>Grade</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grade</em>' attribute.
	 * @see #getGrade()
	 * @generated
	 */
	void setGrade(String value);

} // QuestionGrade
