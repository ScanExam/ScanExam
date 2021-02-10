/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Student Sheet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentSheet#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentSheet#getPosPage <em>Pos Page</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentSheet#getGrades <em>Grades</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentSheet()
 * @model
 * @generated
 */
public interface StudentSheet extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentSheet_Id()
	 * @model unique="false"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentSheet#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Pos Page</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pos Page</em>' attribute list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentSheet_PosPage()
	 * @model unique="false"
	 * @generated
	 */
	EList<Integer> getPosPage();

	/**
	 * Returns the value of the '<em><b>Grades</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Grade}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grades</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentSheet_Grades()
	 * @model containment="true"
	 * @generated
	 */
	EList<Grade> getGrades();

} // StudentSheet
