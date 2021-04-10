/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.StudentSheet;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correction Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getEncodedDocument <em>Encoded Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getExam <em>Exam</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentListPath <em>Student List Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentListShift <em>Student List Shift</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentsheets <em>Studentsheets</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate()
 * @model
 * @generated
 */
public interface CorrectionTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Encoded Document</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encoded Document</em>' attribute.
	 * @see #setEncodedDocument(String)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_EncodedDocument()
	 * @model unique="false"
	 * @generated
	 */
	String getEncodedDocument();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getEncodedDocument <em>Encoded Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encoded Document</em>' attribute.
	 * @see #getEncodedDocument()
	 * @generated
	 */
	void setEncodedDocument(String value);

	/**
	 * Returns the value of the '<em><b>Exam</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exam</em>' containment reference.
	 * @see #setExam(Exam)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_Exam()
	 * @model containment="true"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getExam <em>Exam</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' containment reference.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

	/**
	 * Returns the value of the '<em><b>Student List Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Student List Path</em>' attribute.
	 * @see #setStudentListPath(String)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_StudentListPath()
	 * @model unique="false"
	 * @generated
	 */
	String getStudentListPath();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentListPath <em>Student List Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Student List Path</em>' attribute.
	 * @see #getStudentListPath()
	 * @generated
	 */
	void setStudentListPath(String value);

	/**
	 * Returns the value of the '<em><b>Student List Shift</b></em>' attribute.
	 * The default value is <code>"A1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Student List Shift</em>' attribute.
	 * @see #setStudentListShift(String)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_StudentListShift()
	 * @model default="A1" unique="false"
	 * @generated
	 */
	String getStudentListShift();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentListShift <em>Student List Shift</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Student List Shift</em>' attribute.
	 * @see #getStudentListShift()
	 * @generated
	 */
	void setStudentListShift(String value);

	/**
	 * Returns the value of the '<em><b>Studentsheets</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.StudentSheet}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Studentsheets</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_Studentsheets()
	 * @model containment="true"
	 * @generated
	 */
	EList<StudentSheet> getStudentsheets();

} // CorrectionTemplate
