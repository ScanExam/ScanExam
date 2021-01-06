/**
 */
package fr.istic.tools.scanexam.core;

import fr.istic.tools.scanexam.utils.Rectangle;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exam Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getExamManager()
 * @model
 * @generated
 */
public interface ExamManager extends ExamMarker {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model questionUnique="false" rDataType="fr.istic.tools.scanexam.core.Rectangle" rUnique="false"
	 * @generated
	 */
	void addQuestion(Question question, Rectangle r);

} // ExamManager
