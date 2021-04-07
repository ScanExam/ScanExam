/**
 */
package fr.istic.tools.scanexam.core.templates;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage
 * @generated
 */
public interface TemplatesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TemplatesFactory eINSTANCE = fr.istic.tools.scanexam.core.templates.impl.TemplatesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Correction Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Correction Template</em>'.
	 * @generated
	 */
	CorrectionTemplate createCorrectionTemplate();

	/**
	 * Returns a new object of class '<em>Creation Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Creation Template</em>'.
	 * @generated
	 */
	CreationTemplate createCreationTemplate();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TemplatesPackage getTemplatesPackage();

} //TemplatesFactory
