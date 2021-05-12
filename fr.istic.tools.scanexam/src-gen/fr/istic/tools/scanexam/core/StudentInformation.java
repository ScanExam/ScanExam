/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Student Information</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getName <em>Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getEmailAddress <em>Email Address</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation()
 * @model
 * @generated
 */
public interface StudentInformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentInformation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Email Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email Address</em>' attribute.
	 * @see #setEmailAddress(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation_EmailAddress()
	 * @model unique="false"
	 * @generated
	 */
	String getEmailAddress();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentInformation#getEmailAddress <em>Email Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email Address</em>' attribute.
	 * @see #getEmailAddress()
	 * @generated
	 */
	void setEmailAddress(String value);

} // StudentInformation
