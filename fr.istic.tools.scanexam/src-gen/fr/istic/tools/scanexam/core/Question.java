/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Question</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Question#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Question#getName <em>Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Question#getGradeScale <em>Grade Scale</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Question#getZone <em>Zone</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getQuestion()
 * @model
 * @generated
 */
public interface Question extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getQuestion_Id()
	 * @model unique="false"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Question#getId <em>Id</em>}' attribute.
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
	 * @see fr.istic.tools.scanexam.core.CorePackage#getQuestion_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Question#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Grade Scale</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grade Scale</em>' containment reference.
	 * @see #setGradeScale(GradeScale)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getQuestion_GradeScale()
	 * @model containment="true"
	 * @generated
	 */
	GradeScale getGradeScale();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Question#getGradeScale <em>Grade Scale</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grade Scale</em>' containment reference.
	 * @see #getGradeScale()
	 * @generated
	 */
	void setGradeScale(GradeScale value);

	/**
	 * Returns the value of the '<em><b>Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zone</em>' containment reference.
	 * @see #setZone(QuestionZone)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getQuestion_Zone()
	 * @model containment="true"
	 * @generated
	 */
	QuestionZone getZone();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Question#getZone <em>Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zone</em>' containment reference.
	 * @see #getZone()
	 * @generated
	 */
	void setZone(QuestionZone value);

} // Question
