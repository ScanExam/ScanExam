/**
 */
package fr.istic.tools.scanexam.core.templates;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.istic.tools.scanexam.core.templates.TemplatesFactory
 * @model kind="package"
 * @generated
 */
public interface TemplatesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "templates";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "fr.istic.tools.scanexam.core.templates";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "templates";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TemplatesPackage eINSTANCE = fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl <em>Correction Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl
	 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getCorrectionTemplate()
	 * @generated
	 */
	int CORRECTION_TEMPLATE = 0;

	/**
	 * The feature id for the '<em><b>Informations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__INFORMATIONS = 0;

	/**
	 * The feature id for the '<em><b>Studentsheets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__STUDENTSHEETS = 1;

	/**
	 * The feature id for the '<em><b>Failed Pages</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__FAILED_PAGES = 2;

	/**
	 * The feature id for the '<em><b>Uncomplete Student Sheets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__UNCOMPLETE_STUDENT_SHEETS = 3;

	/**
	 * The feature id for the '<em><b>Encoded Document</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__ENCODED_DOCUMENT = 4;

	/**
	 * The number of structural features of the '<em>Correction Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Correction Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl <em>Creation Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl
	 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getCreationTemplate()
	 * @generated
	 */
	int CREATION_TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Exam</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE__EXAM = 0;

	/**
	 * The feature id for the '<em><b>Encoded Document</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE__ENCODED_DOCUMENT = 1;

	/**
	 * The number of structural features of the '<em>Creation Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Creation Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate <em>Correction Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correction Template</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate
	 * @generated
	 */
	EClass getCorrectionTemplate();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getInformations <em>Informations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Informations</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getInformations()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EReference getCorrectionTemplate_Informations();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentsheets <em>Studentsheets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Studentsheets</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getStudentsheets()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EReference getCorrectionTemplate_Studentsheets();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getFailedPages <em>Failed Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Failed Pages</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getFailedPages()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EAttribute getCorrectionTemplate_FailedPages();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getUncompleteStudentSheets <em>Uncomplete Student Sheets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncomplete Student Sheets</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getUncompleteStudentSheets()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EReference getCorrectionTemplate_UncompleteStudentSheets();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getEncodedDocument <em>Encoded Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoded Document</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getEncodedDocument()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EAttribute getCorrectionTemplate_EncodedDocument();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate <em>Creation Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Creation Template</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CreationTemplate
	 * @generated
	 */
	EClass getCreationTemplate();

	/**
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam()
	 * @see #getCreationTemplate()
	 * @generated
	 */
	EReference getCreationTemplate_Exam();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getEncodedDocument <em>Encoded Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoded Document</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CreationTemplate#getEncodedDocument()
	 * @see #getCreationTemplate()
	 * @generated
	 */
	EAttribute getCreationTemplate_EncodedDocument();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TemplatesFactory getTemplatesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl <em>Correction Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl
		 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getCorrectionTemplate()
		 * @generated
		 */
		EClass CORRECTION_TEMPLATE = eINSTANCE.getCorrectionTemplate();

		/**
		 * The meta object literal for the '<em><b>Informations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRECTION_TEMPLATE__INFORMATIONS = eINSTANCE.getCorrectionTemplate_Informations();

		/**
		 * The meta object literal for the '<em><b>Studentsheets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRECTION_TEMPLATE__STUDENTSHEETS = eINSTANCE.getCorrectionTemplate_Studentsheets();

		/**
		 * The meta object literal for the '<em><b>Failed Pages</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRECTION_TEMPLATE__FAILED_PAGES = eINSTANCE.getCorrectionTemplate_FailedPages();

		/**
		 * The meta object literal for the '<em><b>Uncomplete Student Sheets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRECTION_TEMPLATE__UNCOMPLETE_STUDENT_SHEETS = eINSTANCE.getCorrectionTemplate_UncompleteStudentSheets();

		/**
		 * The meta object literal for the '<em><b>Encoded Document</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRECTION_TEMPLATE__ENCODED_DOCUMENT = eINSTANCE.getCorrectionTemplate_EncodedDocument();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl <em>Creation Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl
		 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getCreationTemplate()
		 * @generated
		 */
		EClass CREATION_TEMPLATE = eINSTANCE.getCreationTemplate();

		/**
		 * The meta object literal for the '<em><b>Exam</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATION_TEMPLATE__EXAM = eINSTANCE.getCreationTemplate_Exam();

		/**
		 * The meta object literal for the '<em><b>Encoded Document</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATION_TEMPLATE__ENCODED_DOCUMENT = eINSTANCE.getCreationTemplate_EncodedDocument();

	}

} //TemplatesPackage
