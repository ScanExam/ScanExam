/**
 */
package fr.istic.tools.scanexam.core;

import fr.istic.tools.scanexam.utils.Rectangle;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exam Marker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.ExamMarker#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.ExamMarker#getName <em>Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.ExamMarker#getQuestions <em>Questions</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getExamMarker()
 * @model
 * @generated
 */
public interface ExamMarker extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getExamMarker_Id()
	 * @model unique="false"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.ExamMarker#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getExamMarker_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.ExamMarker#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Questions</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Question}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Questions</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getExamMarker_Questions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Question> getQuestions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="fr.istic.tools.scanexam.core.Rectangle" unique="false" indexUnique="false"
	 * @generated
	 */
	Rectangle getQuestionZone(int index);

} // ExamMarker
