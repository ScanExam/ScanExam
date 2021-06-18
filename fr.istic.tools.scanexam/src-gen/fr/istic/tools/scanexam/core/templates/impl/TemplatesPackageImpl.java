/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.CorePackage;

import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesFactory;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TemplatesPackageImpl extends EPackageImpl implements TemplatesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correctionTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass creationTemplateEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see fr.istic.tools.scanexam.core.templates.TemplatesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TemplatesPackageImpl() {
		super(eNS_URI, TemplatesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TemplatesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TemplatesPackage init() {
		if (isInited) return (TemplatesPackage)EPackage.Registry.INSTANCE.getEPackage(TemplatesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTemplatesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TemplatesPackageImpl theTemplatesPackage = registeredTemplatesPackage instanceof TemplatesPackageImpl ? (TemplatesPackageImpl)registeredTemplatesPackage : new TemplatesPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		CorePackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTemplatesPackage.createPackageContents();

		// Initialize created meta-data
		theTemplatesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTemplatesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TemplatesPackage.eNS_URI, theTemplatesPackage);
		return theTemplatesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorrectionTemplate() {
		return correctionTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrectionTemplate_Informations() {
		return (EReference)correctionTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrectionTemplate_Studentsheets() {
		return (EReference)correctionTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorrectionTemplate_FailedPages() {
		return (EAttribute)correctionTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorrectionTemplate_EncodedDocument() {
		return (EAttribute)correctionTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreationTemplate() {
		return creationTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreationTemplate_Exam() {
		return (EReference)creationTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCreationTemplate_EncodedDocument() {
		return (EAttribute)creationTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplatesFactory getTemplatesFactory() {
		return (TemplatesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		correctionTemplateEClass = createEClass(CORRECTION_TEMPLATE);
		createEReference(correctionTemplateEClass, CORRECTION_TEMPLATE__INFORMATIONS);
		createEReference(correctionTemplateEClass, CORRECTION_TEMPLATE__STUDENTSHEETS);
		createEAttribute(correctionTemplateEClass, CORRECTION_TEMPLATE__FAILED_PAGES);
		createEAttribute(correctionTemplateEClass, CORRECTION_TEMPLATE__ENCODED_DOCUMENT);

		creationTemplateEClass = createEClass(CREATION_TEMPLATE);
		createEReference(creationTemplateEClass, CREATION_TEMPLATE__EXAM);
		createEAttribute(creationTemplateEClass, CREATION_TEMPLATE__ENCODED_DOCUMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CorePackage theCorePackage = (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(correctionTemplateEClass, CorrectionTemplate.class, "CorrectionTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrectionTemplate_Informations(), theCorePackage.getStudentInformation(), null, "informations", null, 0, -1, CorrectionTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorrectionTemplate_Studentsheets(), theCorePackage.getStudentSheet(), null, "studentsheets", null, 0, -1, CorrectionTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorrectionTemplate_FailedPages(), theEcorePackage.getEIntegerObject(), "failedPages", null, 0, -1, CorrectionTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorrectionTemplate_EncodedDocument(), theEcorePackage.getEString(), "encodedDocument", null, 0, 1, CorrectionTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(creationTemplateEClass, CreationTemplate.class, "CreationTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCreationTemplate_Exam(), theCorePackage.getExam(), null, "exam", null, 0, 1, CreationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreationTemplate_EncodedDocument(), theEcorePackage.getEString(), "encodedDocument", null, 0, 1, CreationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TemplatesPackageImpl
