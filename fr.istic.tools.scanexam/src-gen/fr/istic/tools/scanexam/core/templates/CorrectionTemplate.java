/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.Exam;

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
	 * Returns the value of the '<em><b>Exam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exam</em>' attribute.
	 * @see #setExam(Exam)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_Exam()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.templates.Exam"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getExam <em>Exam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' attribute.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

	/**
	 * Returns the value of the '<em><b>Studentsheets</b></em>' attribute list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Exam}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Studentsheets</em>' attribute list.
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_Studentsheets()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.templates.StudentSheet"
	 * @generated
	 */
	EList<Exam> getStudentsheets();

} // CorrectionTemplate
