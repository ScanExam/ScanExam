/**
 */
package fr.istic.tools.scanexam.core.config.impl;

import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.core.config.ConfigPackage;

import java.util.Locale;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.config.impl.ConfigImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.impl.ConfigImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.impl.ConfigImpl#getEmailPassword <em>Email Password</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.impl.ConfigImpl#getMailHost <em>Mail Host</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.impl.ConfigImpl#getMailPort <em>Mail Port</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigImpl extends MinimalEObjectImpl.Container implements Config {
	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final Locale LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected Locale language = LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmailPassword() <em>Email Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmailPassword() <em>Email Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailPassword()
	 * @generated
	 * @ordered
	 */
	protected String emailPassword = EMAIL_PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getMailHost() <em>Mail Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMailHost()
	 * @generated
	 * @ordered
	 */
	protected static final String MAIL_HOST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMailHost() <em>Mail Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMailHost()
	 * @generated
	 * @ordered
	 */
	protected String mailHost = MAIL_HOST_EDEFAULT;

	/**
	 * The default value of the '{@link #getMailPort() <em>Mail Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMailPort()
	 * @generated
	 * @ordered
	 */
	protected static final String MAIL_PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMailPort() <em>Mail Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMailPort()
	 * @generated
	 * @ordered
	 */
	protected String mailPort = MAIL_PORT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Locale getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLanguage(Locale newLanguage) {
		Locale oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.CONFIG__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.CONFIG__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEmailPassword() {
		return emailPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmailPassword(String newEmailPassword) {
		String oldEmailPassword = emailPassword;
		emailPassword = newEmailPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.CONFIG__EMAIL_PASSWORD, oldEmailPassword, emailPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMailHost() {
		return mailHost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMailHost(String newMailHost) {
		String oldMailHost = mailHost;
		mailHost = newMailHost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.CONFIG__MAIL_HOST, oldMailHost, mailHost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMailPort() {
		return mailPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMailPort(String newMailPort) {
		String oldMailPort = mailPort;
		mailPort = newMailPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.CONFIG__MAIL_PORT, oldMailPort, mailPort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigPackage.CONFIG__LANGUAGE:
				return getLanguage();
			case ConfigPackage.CONFIG__EMAIL:
				return getEmail();
			case ConfigPackage.CONFIG__EMAIL_PASSWORD:
				return getEmailPassword();
			case ConfigPackage.CONFIG__MAIL_HOST:
				return getMailHost();
			case ConfigPackage.CONFIG__MAIL_PORT:
				return getMailPort();
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
			case ConfigPackage.CONFIG__LANGUAGE:
				setLanguage((Locale)newValue);
				return;
			case ConfigPackage.CONFIG__EMAIL:
				setEmail((String)newValue);
				return;
			case ConfigPackage.CONFIG__EMAIL_PASSWORD:
				setEmailPassword((String)newValue);
				return;
			case ConfigPackage.CONFIG__MAIL_HOST:
				setMailHost((String)newValue);
				return;
			case ConfigPackage.CONFIG__MAIL_PORT:
				setMailPort((String)newValue);
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
			case ConfigPackage.CONFIG__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case ConfigPackage.CONFIG__EMAIL:
				setEmail(EMAIL_EDEFAULT);
				return;
			case ConfigPackage.CONFIG__EMAIL_PASSWORD:
				setEmailPassword(EMAIL_PASSWORD_EDEFAULT);
				return;
			case ConfigPackage.CONFIG__MAIL_HOST:
				setMailHost(MAIL_HOST_EDEFAULT);
				return;
			case ConfigPackage.CONFIG__MAIL_PORT:
				setMailPort(MAIL_PORT_EDEFAULT);
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
			case ConfigPackage.CONFIG__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case ConfigPackage.CONFIG__EMAIL:
				return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
			case ConfigPackage.CONFIG__EMAIL_PASSWORD:
				return EMAIL_PASSWORD_EDEFAULT == null ? emailPassword != null : !EMAIL_PASSWORD_EDEFAULT.equals(emailPassword);
			case ConfigPackage.CONFIG__MAIL_HOST:
				return MAIL_HOST_EDEFAULT == null ? mailHost != null : !MAIL_HOST_EDEFAULT.equals(mailHost);
			case ConfigPackage.CONFIG__MAIL_PORT:
				return MAIL_PORT_EDEFAULT == null ? mailPort != null : !MAIL_PORT_EDEFAULT.equals(mailPort);
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
		result.append(" (language: ");
		result.append(language);
		result.append(", email: ");
		result.append(email);
		result.append(", emailPassword: ");
		result.append(emailPassword);
		result.append(", mailHost: ");
		result.append(mailHost);
		result.append(", mailPort: ");
		result.append(mailPort);
		result.append(')');
		return result.toString();
	}

} //ConfigImpl
