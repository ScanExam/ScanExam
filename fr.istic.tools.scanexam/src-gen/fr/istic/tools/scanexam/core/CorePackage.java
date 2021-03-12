/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.QuestionZoneImpl <em>Question Zone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.QuestionZoneImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestionZone()
	 * @generated
	 */
	int QUESTION_ZONE = 0;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE__Y = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Heigth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE__HEIGTH = 3;

	/**
	 * The number of structural features of the '<em>Question Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Question Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_ZONE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.QuestionImpl <em>Question</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.QuestionImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestion()
	 * @generated
	 */
	int QUESTION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Grade Scale</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__GRADE_SCALE = 2;

	/**
	 * The feature id for the '<em><b>Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__ZONE = 3;

	/**
	 * The number of structural features of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_FEATURE_COUNT = 4;

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
	int GRADE_SCALE = 2;

	/**
	 * The feature id for the '<em><b>Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE__STEPS = 0;

	/**
	 * The number of structural features of the '<em>Grade Scale</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Grade Scale</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.GradeEntryImpl <em>Grade Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.GradeEntryImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGradeEntry()
	 * @generated
	 */
	int GRADE_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_ENTRY__ID = 0;

	/**
	 * The feature id for the '<em><b>Step</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_ENTRY__STEP = 1;

	/**
	 * The feature id for the '<em><b>Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_ENTRY__HEADER = 2;

	/**
	 * The number of structural features of the '<em>Grade Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_ENTRY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Grade Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.ExamImpl <em>Exam</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.ExamImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExam()
	 * @generated
	 */
	int EXAM = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__NAME = 1;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__PAGES = 2;

	/**
	 * The number of structural features of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.PageImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ID = 0;

	/**
	 * The feature id for the '<em><b>Questions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__QUESTIONS = 1;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.CommentImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 6;

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
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.TextCommentImpl <em>Text Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.TextCommentImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getTextComment()
	 * @generated
	 */
	int TEXT_COMMENT = 7;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__X = COMMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__Y = COMMENT__Y;

	/**
	 * The number of structural features of the '<em>Text Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Text</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT___GET_TEXT = COMMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Text Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT_OPERATION_COUNT = COMMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl <em>Handwriting Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getHandwritingComment()
	 * @generated
	 */
	int HANDWRITING_COMMENT = 8;

	/**
	 * The number of structural features of the '<em>Handwriting Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Handwriting Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.GradeImpl <em>Grade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.GradeImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGrade()
	 * @generated
	 */
	int GRADE = 9;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE__ENTRIES = 0;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE__COMMENTS = 1;

	/**
	 * The number of structural features of the '<em>Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_FEATURE_COUNT = 2;

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
	int STUDENT_SHEET = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__ID = 0;

	/**
	 * The feature id for the '<em><b>Student Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__STUDENT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Pos Page</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__POS_PAGE = 2;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__GRADES = 3;

	/**
	 * The number of structural features of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Compute Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___COMPUTE_GRADE = 0;

	/**
	 * The number of operations of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_OPERATION_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.QuestionZone <em>Question Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Question Zone</em>'.
	 * @see fr.istic.tools.scanexam.core.QuestionZone
	 * @generated
	 */
	EClass getQuestionZone();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QuestionZone#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see fr.istic.tools.scanexam.core.QuestionZone#getX()
	 * @see #getQuestionZone()
	 * @generated
	 */
	EAttribute getQuestionZone_X();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QuestionZone#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see fr.istic.tools.scanexam.core.QuestionZone#getY()
	 * @see #getQuestionZone()
	 * @generated
	 */
	EAttribute getQuestionZone_Y();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QuestionZone#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see fr.istic.tools.scanexam.core.QuestionZone#getWidth()
	 * @see #getQuestionZone()
	 * @generated
	 */
	EAttribute getQuestionZone_Width();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QuestionZone#getHeigth <em>Heigth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Heigth</em>'.
	 * @see fr.istic.tools.scanexam.core.QuestionZone#getHeigth()
	 * @see #getQuestionZone()
	 * @generated
	 */
	EAttribute getQuestionZone_Heigth();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Question#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fr.istic.tools.scanexam.core.Question#getName()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Name();

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
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.core.Question#getZone <em>Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Zone</em>'.
	 * @see fr.istic.tools.scanexam.core.Question#getZone()
	 * @see #getQuestion()
	 * @generated
	 */
	EReference getQuestion_Zone();

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
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.GradeScale#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Steps</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeScale#getSteps()
	 * @see #getGradeScale()
	 * @generated
	 */
	EReference getGradeScale_Steps();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.GradeEntry <em>Grade Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Grade Entry</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeEntry
	 * @generated
	 */
	EClass getGradeEntry();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.GradeEntry#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeEntry#getId()
	 * @see #getGradeEntry()
	 * @generated
	 */
	EAttribute getGradeEntry_Id();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.GradeEntry#getStep <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Step</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeEntry#getStep()
	 * @see #getGradeEntry()
	 * @generated
	 */
	EAttribute getGradeEntry_Step();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.GradeEntry#getHeader <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeEntry#getHeader()
	 * @see #getGradeEntry()
	 * @generated
	 */
	EAttribute getGradeEntry_Header();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Exam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam
	 * @generated
	 */
	EClass getExam();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Exam#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam#getId()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_Id();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Exam#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam#getName()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.Exam#getPages <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pages</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam#getPages()
	 * @see #getExam()
	 * @generated
	 */
	EReference getExam_Pages();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see fr.istic.tools.scanexam.core.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Page#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.Page#getId()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.Page#getQuestions <em>Questions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Questions</em>'.
	 * @see fr.istic.tools.scanexam.core.Page#getQuestions()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Questions();

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
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.TextComment <em>Text Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Comment</em>'.
	 * @see fr.istic.tools.scanexam.core.TextComment
	 * @generated
	 */
	EClass getTextComment();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.TextComment#getText() <em>Get Text</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Text</em>' operation.
	 * @see fr.istic.tools.scanexam.core.TextComment#getText()
	 * @generated
	 */
	EOperation getTextComment__GetText();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.HandwritingComment <em>Handwriting Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Handwriting Comment</em>'.
	 * @see fr.istic.tools.scanexam.core.HandwritingComment
	 * @generated
	 */
	EClass getHandwritingComment();

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
	 * Returns the meta object for the reference list '{@link fr.istic.tools.scanexam.core.Grade#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Entries</em>'.
	 * @see fr.istic.tools.scanexam.core.Grade#getEntries()
	 * @see #getGrade()
	 * @generated
	 */
	EReference getGrade_Entries();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentSheet#getStudentName <em>Student Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Student Name</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getStudentName()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_StudentName();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.core.StudentSheet#getPosPage <em>Pos Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pos Page</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getPosPage()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_PosPage();

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
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#computeGrade() <em>Compute Grade</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compute Grade</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#computeGrade()
	 * @generated
	 */
	EOperation getStudentSheet__ComputeGrade();

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
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.QuestionZoneImpl <em>Question Zone</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.QuestionZoneImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestionZone()
		 * @generated
		 */
		EClass QUESTION_ZONE = eINSTANCE.getQuestionZone();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_ZONE__X = eINSTANCE.getQuestionZone_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_ZONE__Y = eINSTANCE.getQuestionZone_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_ZONE__WIDTH = eINSTANCE.getQuestionZone_Width();

		/**
		 * The meta object literal for the '<em><b>Heigth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_ZONE__HEIGTH = eINSTANCE.getQuestionZone_Heigth();

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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__NAME = eINSTANCE.getQuestion_Name();

		/**
		 * The meta object literal for the '<em><b>Grade Scale</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__GRADE_SCALE = eINSTANCE.getQuestion_GradeScale();

		/**
		 * The meta object literal for the '<em><b>Zone</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__ZONE = eINSTANCE.getQuestion_Zone();

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
		 * The meta object literal for the '<em><b>Steps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRADE_SCALE__STEPS = eINSTANCE.getGradeScale_Steps();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.GradeEntryImpl <em>Grade Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.GradeEntryImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGradeEntry()
		 * @generated
		 */
		EClass GRADE_ENTRY = eINSTANCE.getGradeEntry();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_ENTRY__ID = eINSTANCE.getGradeEntry_Id();

		/**
		 * The meta object literal for the '<em><b>Step</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_ENTRY__STEP = eINSTANCE.getGradeEntry_Step();

		/**
		 * The meta object literal for the '<em><b>Header</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_ENTRY__HEADER = eINSTANCE.getGradeEntry_Header();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.ExamImpl <em>Exam</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.ExamImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getExam()
		 * @generated
		 */
		EClass EXAM = eINSTANCE.getExam();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__ID = eINSTANCE.getExam_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__NAME = eINSTANCE.getExam_Name();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__PAGES = eINSTANCE.getExam_Pages();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.PageImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__ID = eINSTANCE.getPage_Id();

		/**
		 * The meta object literal for the '<em><b>Questions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGE__QUESTIONS = eINSTANCE.getPage_Questions();

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
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.TextCommentImpl <em>Text Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.TextCommentImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getTextComment()
		 * @generated
		 */
		EClass TEXT_COMMENT = eINSTANCE.getTextComment();

		/**
		 * The meta object literal for the '<em><b>Get Text</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TEXT_COMMENT___GET_TEXT = eINSTANCE.getTextComment__GetText();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl <em>Handwriting Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getHandwritingComment()
		 * @generated
		 */
		EClass HANDWRITING_COMMENT = eINSTANCE.getHandwritingComment();

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
		 * The meta object literal for the '<em><b>Entries</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRADE__ENTRIES = eINSTANCE.getGrade_Entries();

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
		 * The meta object literal for the '<em><b>Student Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__STUDENT_NAME = eINSTANCE.getStudentSheet_StudentName();

		/**
		 * The meta object literal for the '<em><b>Pos Page</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__POS_PAGE = eINSTANCE.getStudentSheet_PosPage();

		/**
		 * The meta object literal for the '<em><b>Grades</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDENT_SHEET__GRADES = eINSTANCE.getStudentSheet_Grades();

		/**
		 * The meta object literal for the '<em><b>Compute Grade</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___COMPUTE_GRADE = eINSTANCE.getStudentSheet__ComputeGrade();

	}

} //CorePackage
