/**
 */
package fr.istic.tools.scanexam.core.templates;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

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
	 * The feature id for the '<em><b>Pdf Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__PDF_PATH = 0;

	/**
	 * The feature id for the '<em><b>Exam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE__EXAM = 1;

	/**
	 * The number of structural features of the '<em>Correction Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRECTION_TEMPLATE_FEATURE_COUNT = 2;

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
	 * The feature id for the '<em><b>Pdf Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE__PDF_PATH = 0;

	/**
	 * The feature id for the '<em><b>Exam</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATION_TEMPLATE__EXAM = 1;

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
	 * The meta object id for the '<em>Exam</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.Exam
	 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getExam()
	 * @generated
	 */
	int EXAM = 2;


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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getPdfPath <em>Pdf Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pdf Path</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getPdfPath()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EAttribute getCorrectionTemplate_PdfPath();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getExam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CorrectionTemplate#getExam()
	 * @see #getCorrectionTemplate()
	 * @generated
	 */
	EAttribute getCorrectionTemplate_Exam();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getPdfPath <em>Pdf Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pdf Path</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CreationTemplate#getPdfPath()
	 * @see #getCreationTemplate()
	 * @generated
	 */
	EAttribute getCreationTemplate_PdfPath();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.core.templates.CreationTemplate#getExam()
	 * @see #getCreationTemplate()
	 * @generated
	 */
	EAttribute getCreationTemplate_Exam();

	/**
	 * Returns the meta object for data type '{@link fr.istic.tools.scanexam.core.Exam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam
	 * @model instanceClass="fr.istic.tools.scanexam.core.Exam"
	 * @generated
	 */
	EDataType getExam();

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
		 * The meta object literal for the '<em><b>Pdf Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRECTION_TEMPLATE__PDF_PATH = eINSTANCE.getCorrectionTemplate_PdfPath();

		/**
		 * The meta object literal for the '<em><b>Exam</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRECTION_TEMPLATE__EXAM = eINSTANCE.getCorrectionTemplate_Exam();

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
		 * The meta object literal for the '<em><b>Pdf Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATION_TEMPLATE__PDF_PATH = eINSTANCE.getCreationTemplate_PdfPath();

		/**
		 * The meta object literal for the '<em><b>Exam</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATION_TEMPLATE__EXAM = eINSTANCE.getCreationTemplate_Exam();

		/**
		 * The meta object literal for the '<em>Exam</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.Exam
		 * @see fr.istic.tools.scanexam.core.templates.impl.TemplatesPackageImpl#getExam()
		 * @generated
		 */
		EDataType EXAM = eINSTANCE.getExam();

	}

} //TemplatesPackage
