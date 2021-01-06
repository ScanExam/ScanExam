/**
 */
package fr.istic.tools.scanexam;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Student Grade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.StudentGrade#getStudentID <em>Student ID</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.StudentGrade#getNumAnonymat <em>Num Anonymat</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.StudentGrade#getQuestionGrades <em>Question Grades</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.ScanexamPackage#getStudentGrade()
 * @model
 * @generated
 */
public interface StudentGrade extends EObject {
	/**
	 * Returns the value of the '<em><b>Student ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Student ID</em>' attribute.
	 * @see #setStudentID(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getStudentGrade_StudentID()
	 * @model unique="false"
	 * @generated
	 */
	String getStudentID();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.StudentGrade#getStudentID <em>Student ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Student ID</em>' attribute.
	 * @see #getStudentID()
	 * @generated
	 */
	void setStudentID(String value);

	/**
	 * Returns the value of the '<em><b>Num Anonymat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num Anonymat</em>' attribute.
	 * @see #setNumAnonymat(long)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getStudentGrade_NumAnonymat()
	 * @model unique="false"
	 * @generated
	 */
	long getNumAnonymat();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.StudentGrade#getNumAnonymat <em>Num Anonymat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num Anonymat</em>' attribute.
	 * @see #getNumAnonymat()
	 * @generated
	 */
	void setNumAnonymat(long value);

	/**
	 * Returns the value of the '<em><b>Question Grades</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.QuestionGrade}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Question Grades</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getStudentGrade_QuestionGrades()
	 * @model containment="true"
	 * @generated
	 */
	EList<QuestionGrade> getQuestionGrades();

} // StudentGrade
