/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
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
 * @see fr.istic.tools.scanexam.core.CoreFactory
 * @model kind="package"
 * @generated
 */
public interface CorePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "core";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "fr.istic.tools.scanexam.core";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "core";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorePackage eINSTANCE = fr.istic.tools.scanexam.core.impl.CorePackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.QuestionImpl <em>Question</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.QuestionImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestion()
	 * @generated
	 */
	int QUESTION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__ID = 0;

	/**
	 * The feature id for the '<em><b>Grade Scale</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__GRADE_SCALE = 1;

	/**
	 * The number of structural features of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.GradeScaleImpl <em>Grade Scale</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.GradeScaleImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGradeScale()
	 * @generated
	 */
	int GRADE_SCALE = 1;

	/**
	 * The feature id for the '<em><b>Weigth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE__WEIGTH = 0;

	/**
	 * The feature id for the '<em><b>Steps</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE__STEPS = 1;

	/**
	 * The number of structural features of the '<em>Grade Scale</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Modify Step</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE___MODIFY_STEP__INT_FLOAT = 0;

	/**
	 * The operation id for the '<em>Remove Step</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE___REMOVE_STEP__INT = 1;

	/**
	 * The operation id for the '<em>Get Step</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE___GET_STEP__INT = 2;

	/**
	 * The operation id for the '<em>Add Step</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE___ADD_STEP__STRING_FLOAT = 3;

	/**
	 * The number of operations of the '<em>Grade Scale</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.ExamMarkerImpl <em>Exam Marker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.ExamMarkerImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExamMarker()
	 * @generated
	 */
	int EXAM_MARKER = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Questions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER__QUESTIONS = 2;

	/**
	 * The number of structural features of the '<em>Exam Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Question Zone</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER___GET_QUESTION_ZONE__INT = 0;

	/**
	 * The number of operations of the '<em>Exam Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MARKER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.ExamManagerImpl <em>Exam Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.ExamManagerImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExamManager()
	 * @generated
	 */
	int EXAM_MANAGER = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER__ID = EXAM_MARKER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER__NAME = EXAM_MARKER__NAME;

	/**
	 * The feature id for the '<em><b>Questions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER__QUESTIONS = EXAM_MARKER__QUESTIONS;

	/**
	 * The number of structural features of the '<em>Exam Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER_FEATURE_COUNT = EXAM_MARKER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Question Zone</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER___GET_QUESTION_ZONE__INT = EXAM_MARKER___GET_QUESTION_ZONE__INT;

	/**
	 * The operation id for the '<em>Add Question</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER___ADD_QUESTION__QUESTION_RECTANGLE = EXAM_MARKER_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Exam Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_MANAGER_OPERATION_COUNT = EXAM_MARKER_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.CommentImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 4;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__Y = 1;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.GradeImpl <em>Grade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.GradeImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGrade()
	 * @generated
	 */
	int GRADE = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Grade Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE__GRADE_LABEL = 1;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE__COMMENTS = 2;

	/**
	 * The number of structural features of the '<em>Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl <em>Student Sheet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.StudentSheetImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getStudentSheet()
	 * @generated
	 */
	int STUDENT_SHEET = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__ID = 0;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__GRADES = 1;

	/**
	 * The number of structural features of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Get Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___GET_GRADE__INT = 0;

	/**
	 * The operation id for the '<em>Remove Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___REMOVE_GRADE__INT = 1;

	/**
	 * The operation id for the '<em>Add Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___ADD_GRADE__INT_GRADE = 2;

	/**
	 * The number of operations of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_OPERATION_COUNT = 3;

	/**
	 * The meta object id for the '<em>Pair</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.utils.Pair
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getPair()
	 * @generated
	 */
	int PAIR = 7;

	/**
	 * The meta object id for the '<em>Rectangle</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.utils.Rectangle
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getRectangle()
	 * @generated
	 */
	int RECTANGLE = 8;


	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Question <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Question</em>'.
	 * @see fr.istic.tools.scanexam.core.Question
	 * @generated
	 */
	EClass getQuestion();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Question#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.Question#getId()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Id();

	/**
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.core.Question#getGradeScale <em>Grade Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Grade Scale</em>'.
	 * @see fr.istic.tools.scanexam.core.Question#getGradeScale()
	 * @see #getQuestion()
	 * @generated
	 */
	EReference getQuestion_GradeScale();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.GradeScale <em>Grade Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Grade Scale</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeScale
	 * @generated
	 */
	EClass getGradeScale();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.GradeScale#getWeigth <em>Weigth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weigth</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeScale#getWeigth()
	 * @see #getGradeScale()
	 * @generated
	 */
	EAttribute getGradeScale_Weigth();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.core.GradeScale#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Steps</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeScale#getSteps()
	 * @see #getGradeScale()
	 * @generated
	 */
	EAttribute getGradeScale_Steps();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.GradeScale#modifyStep(int, float) <em>Modify Step</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Modify Step</em>' operation.
	 * @see fr.istic.tools.scanexam.core.GradeScale#modifyStep(int, float)
	 * @generated
	 */
	EOperation getGradeScale__ModifyStep__int_float();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.GradeScale#removeStep(int) <em>Remove Step</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Remove Step</em>' operation.
	 * @see fr.istic.tools.scanexam.core.GradeScale#removeStep(int)
	 * @generated
	 */
	EOperation getGradeScale__RemoveStep__int();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.GradeScale#getStep(int) <em>Get Step</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Step</em>' operation.
	 * @see fr.istic.tools.scanexam.core.GradeScale#getStep(int)
	 * @generated
	 */
	EOperation getGradeScale__GetStep__int();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.GradeScale#addStep(java.lang.String, float) <em>Add Step</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Step</em>' operation.
	 * @see fr.istic.tools.scanexam.core.GradeScale#addStep(java.lang.String, float)
	 * @generated
	 */
	EOperation getGradeScale__AddStep__String_float();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.ExamMarker <em>Exam Marker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exam Marker</em>'.
	 * @see fr.istic.tools.scanexam.core.ExamMarker
	 * @generated
	 */
	EClass getExamMarker();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.ExamMarker#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.ExamMarker#getId()
	 * @see #getExamMarker()
	 * @generated
	 */
	EAttribute getExamMarker_Id();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.ExamMarker#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fr.istic.tools.scanexam.core.ExamMarker#getName()
	 * @see #getExamMarker()
	 * @generated
	 */
	EAttribute getExamMarker_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.ExamMarker#getQuestions <em>Questions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Questions</em>'.
	 * @see fr.istic.tools.scanexam.core.ExamMarker#getQuestions()
	 * @see #getExamMarker()
	 * @generated
	 */
	EReference getExamMarker_Questions();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.ExamMarker#getQuestionZone(int) <em>Get Question Zone</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Question Zone</em>' operation.
	 * @see fr.istic.tools.scanexam.core.ExamMarker#getQuestionZone(int)
	 * @generated
	 */
	EOperation getExamMarker__GetQuestionZone__int();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.ExamManager <em>Exam Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exam Manager</em>'.
	 * @see fr.istic.tools.scanexam.core.ExamManager
	 * @generated
	 */
	EClass getExamManager();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.ExamManager#addQuestion(fr.istic.tools.scanexam.core.Question, fr.istic.tools.scanexam.utils.Rectangle) <em>Add Question</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Question</em>' operation.
	 * @see fr.istic.tools.scanexam.core.ExamManager#addQuestion(fr.istic.tools.scanexam.core.Question, fr.istic.tools.scanexam.utils.Rectangle)
	 * @generated
	 */
	EOperation getExamManager__AddQuestion__Question_Rectangle();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getX()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_X();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getY()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Y();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Grade <em>Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Grade</em>'.
	 * @see fr.istic.tools.scanexam.core.Grade
	 * @generated
	 */
	EClass getGrade();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Grade#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see fr.istic.tools.scanexam.core.Grade#getValue()
	 * @see #getGrade()
	 * @generated
	 */
	EAttribute getGrade_Value();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Grade#getGradeLabel <em>Grade Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Grade Label</em>'.
	 * @see fr.istic.tools.scanexam.core.Grade#getGradeLabel()
	 * @see #getGrade()
	 * @generated
	 */
	EAttribute getGrade_GradeLabel();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.Grade#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see fr.istic.tools.scanexam.core.Grade#getComments()
	 * @see #getGrade()
	 * @generated
	 */
	EReference getGrade_Comments();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.StudentSheet <em>Student Sheet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Student Sheet</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet
	 * @generated
	 */
	EClass getStudentSheet();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentSheet#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getId()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.StudentSheet#getGrades <em>Grades</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Grades</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getGrades()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EReference getStudentSheet_Grades();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#getGrade(int) <em>Get Grade</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Grade</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getGrade(int)
	 * @generated
	 */
	EOperation getStudentSheet__GetGrade__int();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#removeGrade(int) <em>Remove Grade</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Remove Grade</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#removeGrade(int)
	 * @generated
	 */
	EOperation getStudentSheet__RemoveGrade__int();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#addGrade(int, fr.istic.tools.scanexam.core.Grade) <em>Add Grade</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Grade</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#addGrade(int, fr.istic.tools.scanexam.core.Grade)
	 * @generated
	 */
	EOperation getStudentSheet__AddGrade__int_Grade();

	/**
	 * Returns the meta object for data type '{@link fr.istic.tools.scanexam.utils.Pair <em>Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pair</em>'.
	 * @see fr.istic.tools.scanexam.utils.Pair
	 * @model instanceClass="fr.istic.tools.scanexam.utils.Pair" typeParameters="T1 T2"
	 * @generated
	 */
	EDataType getPair();

	/**
	 * Returns the meta object for data type '{@link fr.istic.tools.scanexam.utils.Rectangle <em>Rectangle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rectangle</em>'.
	 * @see fr.istic.tools.scanexam.utils.Rectangle
	 * @model instanceClass="fr.istic.tools.scanexam.utils.Rectangle"
	 * @generated
	 */
	EDataType getRectangle();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoreFactory getCoreFactory();

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
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.QuestionImpl <em>Question</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.QuestionImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestion()
		 * @generated
		 */
		EClass QUESTION = eINSTANCE.getQuestion();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__ID = eINSTANCE.getQuestion_Id();

		/**
		 * The meta object literal for the '<em><b>Grade Scale</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__GRADE_SCALE = eINSTANCE.getQuestion_GradeScale();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.GradeScaleImpl <em>Grade Scale</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.GradeScaleImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGradeScale()
		 * @generated
		 */
		EClass GRADE_SCALE = eINSTANCE.getGradeScale();

		/**
		 * The meta object literal for the '<em><b>Weigth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_SCALE__WEIGTH = eINSTANCE.getGradeScale_Weigth();

		/**
		 * The meta object literal for the '<em><b>Steps</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_SCALE__STEPS = eINSTANCE.getGradeScale_Steps();

		/**
		 * The meta object literal for the '<em><b>Modify Step</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GRADE_SCALE___MODIFY_STEP__INT_FLOAT = eINSTANCE.getGradeScale__ModifyStep__int_float();

		/**
		 * The meta object literal for the '<em><b>Remove Step</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GRADE_SCALE___REMOVE_STEP__INT = eINSTANCE.getGradeScale__RemoveStep__int();

		/**
		 * The meta object literal for the '<em><b>Get Step</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GRADE_SCALE___GET_STEP__INT = eINSTANCE.getGradeScale__GetStep__int();

		/**
		 * The meta object literal for the '<em><b>Add Step</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GRADE_SCALE___ADD_STEP__STRING_FLOAT = eINSTANCE.getGradeScale__AddStep__String_float();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.ExamMarkerImpl <em>Exam Marker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.ExamMarkerImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExamMarker()
		 * @generated
		 */
		EClass EXAM_MARKER = eINSTANCE.getExamMarker();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM_MARKER__ID = eINSTANCE.getExamMarker_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM_MARKER__NAME = eINSTANCE.getExamMarker_Name();

		/**
		 * The meta object literal for the '<em><b>Questions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM_MARKER__QUESTIONS = eINSTANCE.getExamMarker_Questions();

		/**
		 * The meta object literal for the '<em><b>Get Question Zone</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXAM_MARKER___GET_QUESTION_ZONE__INT = eINSTANCE.getExamMarker__GetQuestionZone__int();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.ExamManagerImpl <em>Exam Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.ExamManagerImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExamManager()
		 * @generated
		 */
		EClass EXAM_MANAGER = eINSTANCE.getExamManager();

		/**
		 * The meta object literal for the '<em><b>Add Question</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXAM_MANAGER___ADD_QUESTION__QUESTION_RECTANGLE = eINSTANCE.getExamManager__AddQuestion__Question_Rectangle();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.CommentImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__X = eINSTANCE.getComment_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__Y = eINSTANCE.getComment_Y();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.GradeImpl <em>Grade</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.GradeImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGrade()
		 * @generated
		 */
		EClass GRADE = eINSTANCE.getGrade();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE__VALUE = eINSTANCE.getGrade_Value();

		/**
		 * The meta object literal for the '<em><b>Grade Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE__GRADE_LABEL = eINSTANCE.getGrade_GradeLabel();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRADE__COMMENTS = eINSTANCE.getGrade_Comments();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl <em>Student Sheet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.StudentSheetImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getStudentSheet()
		 * @generated
		 */
		EClass STUDENT_SHEET = eINSTANCE.getStudentSheet();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__ID = eINSTANCE.getStudentSheet_Id();

		/**
		 * The meta object literal for the '<em><b>Grades</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDENT_SHEET__GRADES = eINSTANCE.getStudentSheet_Grades();

		/**
		 * The meta object literal for the '<em><b>Get Grade</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___GET_GRADE__INT = eINSTANCE.getStudentSheet__GetGrade__int();

		/**
		 * The meta object literal for the '<em><b>Remove Grade</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___REMOVE_GRADE__INT = eINSTANCE.getStudentSheet__RemoveGrade__int();

		/**
		 * The meta object literal for the '<em><b>Add Grade</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___ADD_GRADE__INT_GRADE = eINSTANCE.getStudentSheet__AddGrade__int_Grade();

		/**
		 * The meta object literal for the '<em>Pair</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.utils.Pair
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getPair()
		 * @generated
		 */
		EDataType PAIR = eINSTANCE.getPair();

		/**
		 * The meta object literal for the '<em>Rectangle</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.utils.Rectangle
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getRectangle()
		 * @generated
		 */
		EDataType RECTANGLE = eINSTANCE.getRectangle();

	}

} //CorePackage
