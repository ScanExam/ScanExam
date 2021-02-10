/**
 */
package fr.istic.tools.scanexam.core.templates;

import fr.istic.tools.scanexam.core.Exam;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exam Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.ExamTemplate#getPdfPath <em>Pdf Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.ExamTemplate#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getExamTemplate()
 * @model
 * @generated
 */
public interface ExamTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Pdf Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pdf Path</em>' attribute.
	 * @see #setPdfPath(String)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getExamTemplate_PdfPath()
	 * @model unique="false"
	 * @generated
	 */
	String getPdfPath();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.ExamTemplate#getPdfPath <em>Pdf Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pdf Path</em>' attribute.
	 * @see #getPdfPath()
	 * @generated
	 */
	void setPdfPath(String value);

	/**
	 * Returns the value of the '<em><b>Exam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exam</em>' attribute.
	 * @see #setExam(Exam)
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#getExamTemplate_Exam()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.templates.Exam"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.templates.ExamTemplate#getExam <em>Exam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' attribute.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

} // ExamTemplate