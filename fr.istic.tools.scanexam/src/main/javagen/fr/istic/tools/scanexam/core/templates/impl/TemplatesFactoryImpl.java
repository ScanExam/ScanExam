/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.Exam;

import fr.istic.tools.scanexam.core.templates.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TemplatesFactoryImpl extends EFactoryImpl implements TemplatesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TemplatesFactory init() {
		try {
			TemplatesFactory theTemplatesFactory = (TemplatesFactory)EPackage.Registry.INSTANCE.getEFactory(TemplatesPackage.eNS_URI);
			if (theTemplatesFactory != null) {
				return theTemplatesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TemplatesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplatesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TemplatesPackage.CORRECTION_TEMPLATE: return createCorrectionTemplate();
			case TemplatesPackage.CREATION_TEMPLATE: return createCreationTemplate();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TemplatesPackage.EXAM:
				return createExamFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TemplatesPackage.EXAM:
				return convertExamToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrectionTemplate createCorrectionTemplate() {
		CorrectionTemplateImpl correctionTemplate = new CorrectionTemplateImpl();
		return correctionTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreationTemplate createCreationTemplate() {
		CreationTemplateImpl creationTemplate = new CreationTemplateImpl();
		return creationTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Exam createExamFromString(EDataType eDataType, String initialValue) {
		return (Exam)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertExamToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplatesPackage getTemplatesPackage() {
		return (TemplatesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TemplatesPackage getPackage() {
		return TemplatesPackage.eINSTANCE;
	}

} //TemplatesFactoryImpl
