/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.Exam;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getDocument <em>Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate()
 * @model
 * @generated
 */
public interface CreationTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Document</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Byte}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document</em>' attribute list.
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate_Document()
	 * @model unique="false"
	 * @generated
	 */
	EList<Byte> getDocument();

	/**
	 * Returns the value of the '<em><b>Exam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exam</em>' attribute.
	 * @see #setExam(Exam)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getCreationTemplate_Exam()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.templates.Exam"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' attribute.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

} // CreationTemplate
