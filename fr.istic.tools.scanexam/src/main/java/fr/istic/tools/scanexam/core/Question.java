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
 *   <li>{@link fr.istic.tools.scanexam.core.Question#getGradeScale <em>Grade Scale</em>}</li>
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

} // Question
