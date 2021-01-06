/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.ExamManager;
import fr.istic.tools.scanexam.core.ExamMarker;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.StudentSheet;

import fr.istic.tools.scanexam.utils.Pair;
import fr.istic.tools.scanexam.utils.Rectangle;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
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
public class CorePackageImpl extends EPackageImpl implements CorePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass questionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gradeScaleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass examMarkerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass examManagerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gradeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass studentSheetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pairEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rectangleEDataType = null;

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
	 * @see fr.istic.tools.scanexam.core.CorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CorePackageImpl() {
		super(eNS_URI, CoreFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CorePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CorePackage init() {
		if (isInited) return (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCorePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CorePackageImpl theCorePackage = registeredCorePackage instanceof CorePackageImpl ? (CorePackageImpl)registeredCorePackage : new CorePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCorePackage.createPackageContents();

		// Initialize created meta-data
		theCorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCorePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CorePackage.eNS_URI, theCorePackage);
		return theCorePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getQuestion() {
		return questionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getQuestion_Id() {
		return (EAttribute)questionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getQuestion_GradeScale() {
		return (EReference)questionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGradeScale() {
		return gradeScaleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGradeScale_Weigth() {
		return (EAttribute)gradeScaleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGradeScale_Steps() {
		return (EAttribute)gradeScaleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getGradeScale__ModifyStep__int_float() {
		return gradeScaleEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getGradeScale__RemoveStep__int() {
		return gradeScaleEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getGradeScale__GetStep__int() {
		return gradeScaleEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getGradeScale__AddStep__String_float() {
		return gradeScaleEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExamMarker() {
		return examMarkerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExamMarker_Id() {
		return (EAttribute)examMarkerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExamMarker_Name() {
		return (EAttribute)examMarkerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExamMarker_Questions() {
		return (EReference)examMarkerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExamMarker__GetQuestionZone__int() {
		return examMarkerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExamManager() {
		return examManagerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExamManager__AddQuestion__Question_Rectangle() {
		return examManagerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComment_X() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComment_Y() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGrade() {
		return gradeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGrade_Value() {
		return (EAttribute)gradeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGrade_GradeLabel() {
		return (EAttribute)gradeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGrade_Comments() {
		return (EReference)gradeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStudentSheet() {
		return studentSheetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStudentSheet_Id() {
		return (EAttribute)studentSheetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStudentSheet_Grades() {
		return (EReference)studentSheetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getStudentSheet__GetGrade__int() {
		return studentSheetEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getStudentSheet__RemoveGrade__int() {
		return studentSheetEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getStudentSheet__AddGrade__int_Grade() {
		return studentSheetEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPair() {
		return pairEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRectangle() {
		return rectangleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CoreFactory getCoreFactory() {
		return (CoreFactory)getEFactoryInstance();
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
		questionEClass = createEClass(QUESTION);
		createEAttribute(questionEClass, QUESTION__ID);
		createEReference(questionEClass, QUESTION__GRADE_SCALE);

		gradeScaleEClass = createEClass(GRADE_SCALE);
		createEAttribute(gradeScaleEClass, GRADE_SCALE__WEIGTH);
		createEAttribute(gradeScaleEClass, GRADE_SCALE__STEPS);
		createEOperation(gradeScaleEClass, GRADE_SCALE___MODIFY_STEP__INT_FLOAT);
		createEOperation(gradeScaleEClass, GRADE_SCALE___REMOVE_STEP__INT);
		createEOperation(gradeScaleEClass, GRADE_SCALE___GET_STEP__INT);
		createEOperation(gradeScaleEClass, GRADE_SCALE___ADD_STEP__STRING_FLOAT);

		examMarkerEClass = createEClass(EXAM_MARKER);
		createEAttribute(examMarkerEClass, EXAM_MARKER__ID);
		createEAttribute(examMarkerEClass, EXAM_MARKER__NAME);
		createEReference(examMarkerEClass, EXAM_MARKER__QUESTIONS);
		createEOperation(examMarkerEClass, EXAM_MARKER___GET_QUESTION_ZONE__INT);

		examManagerEClass = createEClass(EXAM_MANAGER);
		createEOperation(examManagerEClass, EXAM_MANAGER___ADD_QUESTION__QUESTION_RECTANGLE);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__X);
		createEAttribute(commentEClass, COMMENT__Y);

		gradeEClass = createEClass(GRADE);
		createEAttribute(gradeEClass, GRADE__VALUE);
		createEAttribute(gradeEClass, GRADE__GRADE_LABEL);
		createEReference(gradeEClass, GRADE__COMMENTS);

		studentSheetEClass = createEClass(STUDENT_SHEET);
		createEAttribute(studentSheetEClass, STUDENT_SHEET__ID);
		createEReference(studentSheetEClass, STUDENT_SHEET__GRADES);
		createEOperation(studentSheetEClass, STUDENT_SHEET___GET_GRADE__INT);
		createEOperation(studentSheetEClass, STUDENT_SHEET___REMOVE_GRADE__INT);
		createEOperation(studentSheetEClass, STUDENT_SHEET___ADD_GRADE__INT_GRADE);

		// Create data types
		pairEDataType = createEDataType(PAIR);
		rectangleEDataType = createEDataType(RECTANGLE);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		addETypeParameter(pairEDataType, "T1");
		addETypeParameter(pairEDataType, "T2");

		// Set bounds for type parameters

		// Add supertypes to classes
		examManagerEClass.getESuperTypes().add(this.getExamMarker());

		// Initialize classes, features, and operations; add parameters
		initEClass(questionEClass, Question.class, "Question", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuestion_Id(), theEcorePackage.getEInt(), "id", null, 0, 1, Question.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuestion_GradeScale(), this.getGradeScale(), null, "gradeScale", null, 0, 1, Question.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gradeScaleEClass, GradeScale.class, "GradeScale", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGradeScale_Weigth(), theEcorePackage.getEFloat(), "weigth", null, 0, 1, GradeScale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		EGenericType g1 = createEGenericType(this.getPair());
		EGenericType g2 = createEGenericType(theEcorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEFloatObject());
		g1.getETypeArguments().add(g2);
		initEAttribute(getGradeScale_Steps(), g1, "steps", null, 0, -1, GradeScale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getGradeScale__ModifyStep__int_float(), theEcorePackage.getEBoolean(), "modifyStep", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEFloat(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getGradeScale__RemoveStep__int(), theEcorePackage.getEBoolean(), "removeStep", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getGradeScale__GetStep__int(), theEcorePackage.getEFloat(), "getStep", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getGradeScale__AddStep__String_float(), theEcorePackage.getEBoolean(), "addStep", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "comment", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEFloat(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(examMarkerEClass, ExamMarker.class, "ExamMarker", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExamMarker_Id(), theEcorePackage.getEInt(), "id", null, 0, 1, ExamMarker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExamMarker_Name(), theEcorePackage.getEString(), "name", null, 0, 1, ExamMarker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExamMarker_Questions(), this.getQuestion(), null, "questions", null, 0, -1, ExamMarker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getExamMarker__GetQuestionZone__int(), this.getRectangle(), "getQuestionZone", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "index", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(examManagerEClass, ExamManager.class, "ExamManager", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getExamManager__AddQuestion__Question_Rectangle(), null, "addQuestion", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getQuestion(), "question", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRectangle(), "r", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_X(), theEcorePackage.getEFloat(), "x", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComment_Y(), theEcorePackage.getEFloat(), "y", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gradeEClass, Grade.class, "Grade", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGrade_Value(), theEcorePackage.getEFloat(), "value", null, 0, 1, Grade.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGrade_GradeLabel(), theEcorePackage.getEString(), "gradeLabel", null, 0, 1, Grade.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGrade_Comments(), this.getComment(), null, "comments", null, 0, -1, Grade.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(studentSheetEClass, StudentSheet.class, "StudentSheet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStudentSheet_Id(), theEcorePackage.getEInt(), "id", null, 0, 1, StudentSheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStudentSheet_Grades(), this.getGrade(), null, "grades", null, 0, -1, StudentSheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getStudentSheet__GetGrade__int(), this.getGrade(), "getGrade", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStudentSheet__RemoveGrade__int(), theEcorePackage.getEBoolean(), "removeGrade", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStudentSheet__AddGrade__int_Grade(), theEcorePackage.getEBoolean(), "addGrade", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "id", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGrade(), "grade", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize data types
		initEDataType(pairEDataType, Pair.class, "Pair", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rectangleEDataType, Rectangle.class, "Rectangle", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //CorePackageImpl
