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

} // Config
