/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.StudentInformation;
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
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getInformations <em>Informations</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentsheets <em>Studentsheets</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getEncodedDocument <em>Encoded Document</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate()
 * @model
 * @generated
 */
public interface CorrectionTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Informations</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.StudentInformation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Informations</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCorrectionTemplate_Informations()
	 * @model containment="true"
	 * @generated
	 */
	EList<StudentInformation> getInformations();

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

} // CorrectionTemplate
