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
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.QrCodeZoneImpl <em>Qr Code Zone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.QrCodeZoneImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQrCodeZone()
	 * @generated
	 */
	int QR_CODE_ZONE = 1;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE__Y = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE__HEIGHT = 3;

	/**
	 * The number of structural features of the '<em>Qr Code Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Qr Code Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QR_CODE_ZONE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.QuestionImpl <em>Question</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.QuestionImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQuestion()
	 * @generated
	 */
	int QUESTION = 2;

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
	int GRADE_SCALE = 3;

	/**
	 * The feature id for the '<em><b>Max Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_SCALE__MAX_POINT = 0;

	/**
	 * The feature id for the '<em><b>Steps</b></em>' containment reference list.
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
	int GRADE_ENTRY = 4;

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
	int EXAM = 5;

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
	 * The feature id for the '<em><b>Qr Code Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__QR_CODE_ZONE = 2;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__PAGES = 3;

	/**
	 * The number of structural features of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Compute Max Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM___COMPUTE_MAX_GRADE = 0;

	/**
	 * The number of operations of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.PageImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 6;

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
	int COMMENT = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Page Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__PAGE_ID = 1;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__X = 2;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__Y = 3;

	/**
	 * The feature id for the '<em><b>Pointer X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__POINTER_X = 4;

	/**
	 * The feature id for the '<em><b>Pointer Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__POINTER_Y = 5;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = 6;

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
	int TEXT_COMMENT = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__ID = COMMENT__ID;

	/**
	 * The feature id for the '<em><b>Page Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__PAGE_ID = COMMENT__PAGE_ID;

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
	 * The feature id for the '<em><b>Pointer X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__POINTER_X = COMMENT__POINTER_X;

	/**
	 * The feature id for the '<em><b>Pointer Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__POINTER_Y = COMMENT__POINTER_Y;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT__TEXT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Text Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMMENT_OPERATION_COUNT = COMMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl <em>Handwriting Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getHandwritingComment()
	 * @generated
	 */
	int HANDWRITING_COMMENT = 9;

	/**
	 * The feature id for the '<em><b>Page Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT__PAGE_ID = 0;

	/**
	 * The feature id for the '<em><b>Lines</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT__LINES = 1;

	/**
	 * The number of structural features of the '<em>Handwriting Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Handwriting Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDWRITING_COMMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.LineImpl <em>Line</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.LineImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getLine()
	 * @generated
	 */
	int LINE = 10;

	/**
	 * The feature id for the '<em><b>X1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__X1 = 0;

	/**
	 * The feature id for the '<em><b>Y1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__Y1 = 1;

	/**
	 * The feature id for the '<em><b>X2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__X2 = 2;

	/**
	 * The feature id for the '<em><b>Y2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__Y2 = 3;

	/**
	 * The feature id for the '<em><b>Thinkness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__THINKNESS = 4;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE__COLOR = 5;

	/**
	 * The number of structural features of the '<em>Line</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Line</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.GradeImpl <em>Grade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.GradeImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getGrade()
	 * @generated
	 */
	int GRADE = 11;

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
	 * The operation id for the '<em>Get Grade Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE___GET_GRADE_VALUE = 0;

	/**
	 * The number of operations of the '<em>Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl <em>Student Sheet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.StudentSheetImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getStudentSheet()
	 * @generated
	 */
	int STUDENT_SHEET = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__ID = 0;

	/**
	 * The feature id for the '<em><b>Student ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__STUDENT_ID = 1;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__FIRST_NAME = 2;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__LAST_NAME = 3;

	/**
	 * The feature id for the '<em><b>Pos Page</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__POS_PAGE = 4;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET__GRADES = 5;

	/**
	 * The number of structural features of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_FEATURE_COUNT = 6;

	/**
	 * The operation id for the '<em>Compute Grade</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___COMPUTE_GRADE = 0;

	/**
	 * The operation id for the '<em>Is Graded</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___IS_GRADED = 1;

	/**
	 * The operation id for the '<em>Get Student Info</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET___GET_STUDENT_INFO = 2;

	/**
	 * The number of operations of the '<em>Student Sheet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_SHEET_OPERATION_COUNT = 3;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.core.impl.StudentInformationImpl <em>Student Information</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.core.impl.StudentInformationImpl
	 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getStudentInformation()
	 * @generated
	 */
	int STUDENT_INFORMATION = 13;

	/**
	 * The feature id for the '<em><b>User Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION__USER_ID = 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION__LAST_NAME = 1;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION__FIRST_NAME = 2;

	/**
	 * The feature id for the '<em><b>Email Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION__EMAIL_ADDRESS = 3;

	/**
	 * The number of structural features of the '<em>Student Information</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Student Information</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_INFORMATION_OPERATION_COUNT = 0;


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
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.QrCodeZone <em>Qr Code Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qr Code Zone</em>'.
	 * @see fr.istic.tools.scanexam.core.QrCodeZone
	 * @generated
	 */
	EClass getQrCodeZone();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QrCodeZone#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see fr.istic.tools.scanexam.core.QrCodeZone#getX()
	 * @see #getQrCodeZone()
	 * @generated
	 */
	EAttribute getQrCodeZone_X();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QrCodeZone#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see fr.istic.tools.scanexam.core.QrCodeZone#getY()
	 * @see #getQrCodeZone()
	 * @generated
	 */
	EAttribute getQrCodeZone_Y();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QrCodeZone#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see fr.istic.tools.scanexam.core.QrCodeZone#getWidth()
	 * @see #getQrCodeZone()
	 * @generated
	 */
	EAttribute getQrCodeZone_Width();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.QrCodeZone#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see fr.istic.tools.scanexam.core.QrCodeZone#getHeight()
	 * @see #getQrCodeZone()
	 * @generated
	 */
	EAttribute getQrCodeZone_Height();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.GradeScale#getMaxPoint <em>Max Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Point</em>'.
	 * @see fr.istic.tools.scanexam.core.GradeScale#getMaxPoint()
	 * @see #getGradeScale()
	 * @generated
	 */
	EAttribute getGradeScale_MaxPoint();

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
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.core.Exam#getQrCodeZone <em>Qr Code Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Qr Code Zone</em>'.
	 * @see fr.istic.tools.scanexam.core.Exam#getQrCodeZone()
	 * @see #getExam()
	 * @generated
	 */
	EReference getExam_QrCodeZone();

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
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.Exam#computeMaxGrade() <em>Compute Max Grade</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compute Max Grade</em>' operation.
	 * @see fr.istic.tools.scanexam.core.Exam#computeMaxGrade()
	 * @generated
	 */
	EOperation getExam__ComputeMaxGrade();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getId()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Id();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getPageId <em>Page Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Page Id</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getPageId()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_PageId();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getPointerX <em>Pointer X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pointer X</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getPointerX()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_PointerX();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Comment#getPointerY <em>Pointer Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pointer Y</em>'.
	 * @see fr.istic.tools.scanexam.core.Comment#getPointerY()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_PointerY();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.TextComment#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see fr.istic.tools.scanexam.core.TextComment#getText()
	 * @see #getTextComment()
	 * @generated
	 */
	EAttribute getTextComment_Text();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.HandwritingComment#getPageId <em>Page Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Page Id</em>'.
	 * @see fr.istic.tools.scanexam.core.HandwritingComment#getPageId()
	 * @see #getHandwritingComment()
	 * @generated
	 */
	EAttribute getHandwritingComment_PageId();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.core.HandwritingComment#getLines <em>Lines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Lines</em>'.
	 * @see fr.istic.tools.scanexam.core.HandwritingComment#getLines()
	 * @see #getHandwritingComment()
	 * @generated
	 */
	EReference getHandwritingComment_Lines();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.Line <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line</em>'.
	 * @see fr.istic.tools.scanexam.core.Line
	 * @generated
	 */
	EClass getLine();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getX1 <em>X1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X1</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getX1()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_X1();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getY1 <em>Y1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y1</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getY1()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Y1();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getX2 <em>X2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X2</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getX2()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_X2();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getY2 <em>Y2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y2</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getY2()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Y2();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getThinkness <em>Thinkness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thinkness</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getThinkness()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Thinkness();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.Line#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see fr.istic.tools.scanexam.core.Line#getColor()
	 * @see #getLine()
	 * @generated
	 */
	EAttribute getLine_Color();

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
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.Grade#getGradeValue() <em>Get Grade Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Grade Value</em>' operation.
	 * @see fr.istic.tools.scanexam.core.Grade#getGradeValue()
	 * @generated
	 */
	EOperation getGrade__GetGradeValue();

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
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentSheet#getStudentID <em>Student ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Student ID</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getStudentID()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_StudentID();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentSheet#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getFirstName()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentSheet#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getLastName()
	 * @see #getStudentSheet()
	 * @generated
	 */
	EAttribute getStudentSheet_LastName();

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
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#isGraded() <em>Is Graded</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Graded</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#isGraded()
	 * @generated
	 */
	EOperation getStudentSheet__IsGraded();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.core.StudentSheet#getStudentInfo() <em>Get Student Info</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Student Info</em>' operation.
	 * @see fr.istic.tools.scanexam.core.StudentSheet#getStudentInfo()
	 * @generated
	 */
	EOperation getStudentSheet__GetStudentInfo();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.core.StudentInformation <em>Student Information</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Student Information</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentInformation
	 * @generated
	 */
	EClass getStudentInformation();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentInformation#getUserId <em>User Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Id</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentInformation#getUserId()
	 * @see #getStudentInformation()
	 * @generated
	 */
	EAttribute getStudentInformation_UserId();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentInformation#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentInformation#getLastName()
	 * @see #getStudentInformation()
	 * @generated
	 */
	EAttribute getStudentInformation_LastName();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentInformation#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentInformation#getFirstName()
	 * @see #getStudentInformation()
	 * @generated
	 */
	EAttribute getStudentInformation_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.core.StudentInformation#getEmailAddress <em>Email Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email Address</em>'.
	 * @see fr.istic.tools.scanexam.core.StudentInformation#getEmailAddress()
	 * @see #getStudentInformation()
	 * @generated
	 */
	EAttribute getStudentInformation_EmailAddress();

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
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.QrCodeZoneImpl <em>Qr Code Zone</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.QrCodeZoneImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getQrCodeZone()
		 * @generated
		 */
		EClass QR_CODE_ZONE = eINSTANCE.getQrCodeZone();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QR_CODE_ZONE__X = eINSTANCE.getQrCodeZone_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QR_CODE_ZONE__Y = eINSTANCE.getQrCodeZone_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QR_CODE_ZONE__WIDTH = eINSTANCE.getQrCodeZone_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QR_CODE_ZONE__HEIGHT = eINSTANCE.getQrCodeZone_Height();

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
		 * The meta object literal for the '<em><b>Max Point</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADE_SCALE__MAX_POINT = eINSTANCE.getGradeScale_MaxPoint();

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
		 * The meta object literal for the '<em><b>Qr Code Zone</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__QR_CODE_ZONE = eINSTANCE.getExam_QrCodeZone();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__PAGES = eINSTANCE.getExam_Pages();

		/**
		 * The meta object literal for the '<em><b>Compute Max Grade</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXAM___COMPUTE_MAX_GRADE = eINSTANCE.getExam__ComputeMaxGrade();

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
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__ID = eINSTANCE.getComment_Id();

		/**
		 * The meta object literal for the '<em><b>Page Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__PAGE_ID = eINSTANCE.getComment_PageId();

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
		 * The meta object literal for the '<em><b>Pointer X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__POINTER_X = eINSTANCE.getComment_PointerX();

		/**
		 * The meta object literal for the '<em><b>Pointer Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__POINTER_Y = eINSTANCE.getComment_PointerY();

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
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_COMMENT__TEXT = eINSTANCE.getTextComment_Text();

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
		 * The meta object literal for the '<em><b>Page Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HANDWRITING_COMMENT__PAGE_ID = eINSTANCE.getHandwritingComment_PageId();

		/**
		 * The meta object literal for the '<em><b>Lines</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDWRITING_COMMENT__LINES = eINSTANCE.getHandwritingComment_Lines();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.LineImpl <em>Line</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.LineImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getLine()
		 * @generated
		 */
		EClass LINE = eINSTANCE.getLine();

		/**
		 * The meta object literal for the '<em><b>X1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__X1 = eINSTANCE.getLine_X1();

		/**
		 * The meta object literal for the '<em><b>Y1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__Y1 = eINSTANCE.getLine_Y1();

		/**
		 * The meta object literal for the '<em><b>X2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__X2 = eINSTANCE.getLine_X2();

		/**
		 * The meta object literal for the '<em><b>Y2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__Y2 = eINSTANCE.getLine_Y2();

		/**
		 * The meta object literal for the '<em><b>Thinkness</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__THINKNESS = eINSTANCE.getLine_Thinkness();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE__COLOR = eINSTANCE.getLine_Color();

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
		 * The meta object literal for the '<em><b>Get Grade Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GRADE___GET_GRADE_VALUE = eINSTANCE.getGrade__GetGradeValue();

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
		 * The meta object literal for the '<em><b>Student ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__STUDENT_ID = eINSTANCE.getStudentSheet_StudentID();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__FIRST_NAME = eINSTANCE.getStudentSheet_FirstName();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_SHEET__LAST_NAME = eINSTANCE.getStudentSheet_LastName();

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

		/**
		 * The meta object literal for the '<em><b>Is Graded</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___IS_GRADED = eINSTANCE.getStudentSheet__IsGraded();

		/**
		 * The meta object literal for the '<em><b>Get Student Info</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STUDENT_SHEET___GET_STUDENT_INFO = eINSTANCE.getStudentSheet__GetStudentInfo();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.core.impl.StudentInformationImpl <em>Student Information</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.core.impl.StudentInformationImpl
		 * @see fr.istic.tools.scanexam.core.impl.CorePackageImpl#getStudentInformation()
		 * @generated
		 */
		EClass STUDENT_INFORMATION = eINSTANCE.getStudentInformation();

		/**
		 * The meta object literal for the '<em><b>User Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_INFORMATION__USER_ID = eINSTANCE.getStudentInformation_UserId();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_INFORMATION__LAST_NAME = eINSTANCE.getStudentInformation_LastName();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_INFORMATION__FIRST_NAME = eINSTANCE.getStudentInformation_FirstName();

		/**
		 * The meta object literal for the '<em><b>Email Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_INFORMATION__EMAIL_ADDRESS = eINSTANCE.getStudentInformation_EmailAddress();

	}

} //CorePackage
