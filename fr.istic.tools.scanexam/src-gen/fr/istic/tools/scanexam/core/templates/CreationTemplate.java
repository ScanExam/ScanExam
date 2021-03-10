/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.Exam;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Creation Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getEncodedDocument <em>Encoded Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate()
 * @model
 * @generated
 */
public interface CreationTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Encoded Document</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encoded Document</em>' attribute.
	 * @see #setEncodedDocument(String)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate_EncodedDocument()
	 * @model unique="false"
	 * @generated
	 */
	String getEncodedDocument();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getEncodedDocument <em>Encoded Document</em>}' attribute.
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
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate_Exam()
	 * @model containment="true"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' containment reference.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

} // CreationTemplate
