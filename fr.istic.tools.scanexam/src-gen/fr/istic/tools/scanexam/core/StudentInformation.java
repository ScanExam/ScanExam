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
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getUserId <em>User Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getLastName <em>Last Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getFirstName <em>First Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.StudentInformation#getEmailAddress <em>Email Address</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation()
 * @model
 * @generated
 */
public interface StudentInformation extends EObject {
	/**
	 * Returns the value of the '<em><b>User Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Id</em>' attribute.
	 * @see #setUserId(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation_UserId()
	 * @model unique="false"
	 * @generated
	 */
	String getUserId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentInformation#getUserId <em>User Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Id</em>' attribute.
	 * @see #getUserId()
	 * @generated
	 */
	void setUserId(String value);

	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation_LastName()
	 * @model unique="false"
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentInformation#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Name</em>' attribute.
	 * @see #setFirstName(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getStudentInformation_FirstName()
	 * @model unique="false"
	 * @generated
	 */
	String getFirstName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.StudentInformation#getFirstName <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Name</em>' attribute.
	 * @see #getFirstName()
	 * @generated
	 */
	void setFirstName(String value);

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
