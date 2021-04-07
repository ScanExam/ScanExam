/**
 */
package fr.istic.tools.scanexam.core.config;

import java.util.Locale;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.config.Config#getLanguage <em>Language</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.Config#getEmail <em>Email</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.Config#getEmailPassword <em>Email Password</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.Config#getMailHost <em>Mail Host</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.config.Config#getMailPort <em>Mail Port</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig()
 * @model
 * @generated
 */
public interface Config extends EObject {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(Locale)
	 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig_Language()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.config.Locale"
	 * @generated
	 */
	Locale getLanguage();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.config.Config#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(Locale value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig_Email()
	 * @model unique="false"
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.config.Config#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

	/**
	 * Returns the value of the '<em><b>Email Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email Password</em>' attribute.
	 * @see #setEmailPassword(String)
	 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig_EmailPassword()
	 * @model unique="false"
	 * @generated
	 */
	String getEmailPassword();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.config.Config#getEmailPassword <em>Email Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email Password</em>' attribute.
	 * @see #getEmailPassword()
	 * @generated
	 */
	void setEmailPassword(String value);

	/**
	 * Returns the value of the '<em><b>Mail Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mail Host</em>' attribute.
	 * @see #setMailHost(String)
	 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig_MailHost()
	 * @model unique="false"
	 * @generated
	 */
	String getMailHost();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.config.Config#getMailHost <em>Mail Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mail Host</em>' attribute.
	 * @see #getMailHost()
	 * @generated
	 */
	void setMailHost(String value);

	/**
	 * Returns the value of the '<em><b>Mail Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mail Port</em>' attribute.
	 * @see #setMailPort(String)
	 * @see fr.istic.tools.scanexam.core.config.ConfigPackage#getConfig_MailPort()
	 * @model unique="false"
	 * @generated
	 */
	String getMailPort();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.config.Config#getMailPort <em>Mail Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mail Port</em>' attribute.
	 * @see #getMailPort()
	 * @generated
	 */
	void setMailPort(String value);

} // Config
