/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Grade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Grade#getValue <em>Value</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Grade#getGradeLabel <em>Grade Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Grade#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade()
 * @model
 * @generated
 */
public interface Grade extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade_Value()
	 * @model unique="false"
	 * @generated
	 */
	float getValue();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Grade#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(float value);

	/**
	 * Returns the value of the '<em><b>Grade Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grade Label</em>' attribute.
	 * @see #setGradeLabel(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade_GradeLabel()
	 * @model unique="false"
	 * @generated
	 */
	String getGradeLabel();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Grade#getGradeLabel <em>Grade Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grade Label</em>' attribute.
	 * @see #getGradeLabel()
	 * @generated
	 */
	void setGradeLabel(String value);

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Comment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade_Comments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Comment> getComments();

} // Grade
