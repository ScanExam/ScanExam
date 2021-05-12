/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.StudentInformation;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Student Information</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentInformationImpl#getName <em>Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentInformationImpl#getEmailAddress <em>Email Address</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StudentInformationImpl extends MinimalEObjectImpl.Container implements StudentInformation {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmailAddress() <em>Email Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmailAddress() <em>Email Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailAddress()
	 * @generated
	 * @ordered
	 */
	protected String emailAddress = EMAIL_ADDRESS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StudentInformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.STUDENT_INFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_INFORMATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmailAddress(String newEmailAddress) {
		String oldEmailAddress = emailAddress;
		emailAddress = newEmailAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_INFORMATION__EMAIL_ADDRESS, oldEmailAddress, emailAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.STUDENT_INFORMATION__NAME:
				return getName();
			case CorePackage.STUDENT_INFORMATION__EMAIL_ADDRESS:
				return getEmailAddress();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.STUDENT_INFORMATION__NAME:
				setName((String)newValue);
				return;
			case CorePackage.STUDENT_INFORMATION__EMAIL_ADDRESS:
				setEmailAddress((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CorePackage.STUDENT_INFORMATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CorePackage.STUDENT_INFORMATION__EMAIL_ADDRESS:
				setEmailAddress(EMAIL_ADDRESS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorePackage.STUDENT_INFORMATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CorePackage.STUDENT_INFORMATION__EMAIL_ADDRESS:
				return EMAIL_ADDRESS_EDEFAULT == null ? emailAddress != null : !EMAIL_ADDRESS_EDEFAULT.equals(emailAddress);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", emailAddress: ");
		result.append(emailAddress);
		result.append(')');
		return result.toString();
	}

} //StudentInformationImpl
