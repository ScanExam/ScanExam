/**
 */
package fr.istic.tools.scanexam;

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
 * @see fr.istic.tools.scanexam.ScanexamFactory
 * @model kind="package"
 * @generated
 */
public interface ScanexamPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "scanexam";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "fr.istic.tools.scanexam";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "scanexam";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScanexamPackage eINSTANCE = fr.istic.tools.scanexam.impl.ScanexamPackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.ExamImpl <em>Exam</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.ExamImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getExam()
	 * @generated
	 */
	int EXAM = 0;

	/**
	 * The feature id for the '<em><b>Filepath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__FILEPATH = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Folder Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__FOLDER_PATH = 2;

	/**
	 * The feature id for the '<em><b>Number Of Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__NUMBER_OF_PAGES = 3;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__SCALE = 4;

	/**
	 * The feature id for the '<em><b>Questions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__QUESTIONS = 5;

	/**
	 * The number of structural features of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.QuestionImpl <em>Question</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.QuestionImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getQuestion()
	 * @generated
	 */
	int QUESTION = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__ZONE = 1;

	/**
	 * The feature id for the '<em><b>Mark Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__MARK_ZONE = 2;

	/**
	 * The feature id for the '<em><b>Default Grade Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__DEFAULT_GRADE_INDEX = 3;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__GRADES = 4;

	/**
	 * The feature id for the '<em><b>Feedback</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__FEEDBACK = 5;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__WEIGHT = 6;

	/**
	 * The number of structural features of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.InfoFieldImpl <em>Info Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.InfoFieldImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getInfoField()
	 * @generated
	 */
	int INFO_FIELD = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__LABEL = QUESTION__LABEL;

	/**
	 * The feature id for the '<em><b>Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__ZONE = QUESTION__ZONE;

	/**
	 * The feature id for the '<em><b>Mark Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__MARK_ZONE = QUESTION__MARK_ZONE;

	/**
	 * The feature id for the '<em><b>Default Grade Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__DEFAULT_GRADE_INDEX = QUESTION__DEFAULT_GRADE_INDEX;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__GRADES = QUESTION__GRADES;

	/**
	 * The feature id for the '<em><b>Feedback</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__FEEDBACK = QUESTION__FEEDBACK;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD__WEIGHT = QUESTION__WEIGHT;

	/**
	 * The number of structural features of the '<em>Info Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD_FEATURE_COUNT = QUESTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Info Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FIELD_OPERATION_COUNT = QUESTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.ScanZoneImpl <em>Scan Zone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.ScanZoneImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getScanZone()
	 * @generated
	 */
	int SCAN_ZONE = 3;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE__Y = 1;

	/**
	 * The feature id for the '<em><b>W</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE__W = 2;

	/**
	 * The feature id for the '<em><b>H</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE__H = 3;

	/**
	 * The feature id for the '<em><b>Page</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE__PAGE = 4;

	/**
	 * The number of structural features of the '<em>Scan Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Scan Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCAN_ZONE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.GradingDataImpl <em>Grading Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.GradingDataImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getGradingData()
	 * @generated
	 */
	int GRADING_DATA = 4;

	/**
	 * The feature id for the '<em><b>Solution Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA__SOLUTION_PATH = 0;

	/**
	 * The feature id for the '<em><b>Excel File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA__EXCEL_FILE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Images</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA__IMAGES = 2;

	/**
	 * The feature id for the '<em><b>Exam</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA__EXAM = 3;

	/**
	 * The feature id for the '<em><b>Grades</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA__GRADES = 4;

	/**
	 * The number of structural features of the '<em>Grading Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Grading Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRADING_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.StudentGradeImpl <em>Student Grade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.StudentGradeImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getStudentGrade()
	 * @generated
	 */
	int STUDENT_GRADE = 5;

	/**
	 * The feature id for the '<em><b>Student ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_GRADE__STUDENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Num Anonymat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_GRADE__NUM_ANONYMAT = 1;

	/**
	 * The feature id for the '<em><b>Question Grades</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_GRADE__QUESTION_GRADES = 2;

	/**
	 * The number of structural features of the '<em>Student Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_GRADE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Student Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_GRADE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.istic.tools.scanexam.impl.QuestionGradeImpl <em>Question Grade</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.istic.tools.scanexam.impl.QuestionGradeImpl
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getQuestionGrade()
	 * @generated
	 */
	int QUESTION_GRADE = 6;

	/**
	 * The feature id for the '<em><b>Question</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE__QUESTION = 0;

	/**
	 * The feature id for the '<em><b>Validated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE__VALIDATED = 1;

	/**
	 * The feature id for the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE__FILENAME = 2;

	/**
	 * The feature id for the '<em><b>Grade</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE__GRADE = 3;

	/**
	 * The number of structural features of the '<em>Question Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Question Grade</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_GRADE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>File</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.io.File
	 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getFile()
	 * @generated
	 */
	int FILE = 7;


	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.Exam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.Exam
	 * @generated
	 */
	EClass getExam();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Exam#getFilepath <em>Filepath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filepath</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getFilepath()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_Filepath();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Exam#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getLabel()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_Label();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Exam#getFolderPath <em>Folder Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder Path</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getFolderPath()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_FolderPath();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Exam#getNumberOfPages <em>Number Of Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Pages</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getNumberOfPages()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_NumberOfPages();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Exam#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getScale()
	 * @see #getExam()
	 * @generated
	 */
	EAttribute getExam_Scale();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.Exam#getQuestions <em>Questions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Questions</em>'.
	 * @see fr.istic.tools.scanexam.Exam#getQuestions()
	 * @see #getExam()
	 * @generated
	 */
	EReference getExam_Questions();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.InfoField <em>Info Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Info Field</em>'.
	 * @see fr.istic.tools.scanexam.InfoField
	 * @generated
	 */
	EClass getInfoField();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.Question <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Question</em>'.
	 * @see fr.istic.tools.scanexam.Question
	 * @generated
	 */
	EClass getQuestion();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Question#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see fr.istic.tools.scanexam.Question#getLabel()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Label();

	/**
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.Question#getZone <em>Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Zone</em>'.
	 * @see fr.istic.tools.scanexam.Question#getZone()
	 * @see #getQuestion()
	 * @generated
	 */
	EReference getQuestion_Zone();

	/**
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.Question#getMarkZone <em>Mark Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mark Zone</em>'.
	 * @see fr.istic.tools.scanexam.Question#getMarkZone()
	 * @see #getQuestion()
	 * @generated
	 */
	EReference getQuestion_MarkZone();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Question#getDefaultGradeIndex <em>Default Grade Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Grade Index</em>'.
	 * @see fr.istic.tools.scanexam.Question#getDefaultGradeIndex()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_DefaultGradeIndex();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.Question#getGrades <em>Grades</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Grades</em>'.
	 * @see fr.istic.tools.scanexam.Question#getGrades()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Grades();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.Question#getFeedback <em>Feedback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Feedback</em>'.
	 * @see fr.istic.tools.scanexam.Question#getFeedback()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Feedback();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.Question#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see fr.istic.tools.scanexam.Question#getWeight()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Weight();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.ScanZone <em>Scan Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scan Zone</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone
	 * @generated
	 */
	EClass getScanZone();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.ScanZone#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone#getX()
	 * @see #getScanZone()
	 * @generated
	 */
	EAttribute getScanZone_X();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.ScanZone#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone#getY()
	 * @see #getScanZone()
	 * @generated
	 */
	EAttribute getScanZone_Y();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.ScanZone#getW <em>W</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>W</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone#getW()
	 * @see #getScanZone()
	 * @generated
	 */
	EAttribute getScanZone_W();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.ScanZone#getH <em>H</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>H</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone#getH()
	 * @see #getScanZone()
	 * @generated
	 */
	EAttribute getScanZone_H();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.ScanZone#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Page</em>'.
	 * @see fr.istic.tools.scanexam.ScanZone#getPage()
	 * @see #getScanZone()
	 * @generated
	 */
	EAttribute getScanZone_Page();

	/**
	 * Returns the meta object for the '{@link fr.istic.tools.scanexam.ScanZone#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see fr.istic.tools.scanexam.ScanZone#toString()
	 * @generated
	 */
	EOperation getScanZone__ToString();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.GradingData <em>Grading Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Grading Data</em>'.
	 * @see fr.istic.tools.scanexam.GradingData
	 * @generated
	 */
	EClass getGradingData();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.GradingData#getSolutionPath <em>Solution Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Solution Path</em>'.
	 * @see fr.istic.tools.scanexam.GradingData#getSolutionPath()
	 * @see #getGradingData()
	 * @generated
	 */
	EAttribute getGradingData_SolutionPath();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.GradingData#getExcelFileName <em>Excel File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Excel File Name</em>'.
	 * @see fr.istic.tools.scanexam.GradingData#getExcelFileName()
	 * @see #getGradingData()
	 * @generated
	 */
	EAttribute getGradingData_ExcelFileName();

	/**
	 * Returns the meta object for the attribute list '{@link fr.istic.tools.scanexam.GradingData#getImages <em>Images</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Images</em>'.
	 * @see fr.istic.tools.scanexam.GradingData#getImages()
	 * @see #getGradingData()
	 * @generated
	 */
	EAttribute getGradingData_Images();

	/**
	 * Returns the meta object for the containment reference '{@link fr.istic.tools.scanexam.GradingData#getExam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exam</em>'.
	 * @see fr.istic.tools.scanexam.GradingData#getExam()
	 * @see #getGradingData()
	 * @generated
	 */
	EReference getGradingData_Exam();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.GradingData#getGrades <em>Grades</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Grades</em>'.
	 * @see fr.istic.tools.scanexam.GradingData#getGrades()
	 * @see #getGradingData()
	 * @generated
	 */
	EReference getGradingData_Grades();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.StudentGrade <em>Student Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Student Grade</em>'.
	 * @see fr.istic.tools.scanexam.StudentGrade
	 * @generated
	 */
	EClass getStudentGrade();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.StudentGrade#getStudentID <em>Student ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Student ID</em>'.
	 * @see fr.istic.tools.scanexam.StudentGrade#getStudentID()
	 * @see #getStudentGrade()
	 * @generated
	 */
	EAttribute getStudentGrade_StudentID();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.StudentGrade#getNumAnonymat <em>Num Anonymat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Anonymat</em>'.
	 * @see fr.istic.tools.scanexam.StudentGrade#getNumAnonymat()
	 * @see #getStudentGrade()
	 * @generated
	 */
	EAttribute getStudentGrade_NumAnonymat();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.istic.tools.scanexam.StudentGrade#getQuestionGrades <em>Question Grades</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Question Grades</em>'.
	 * @see fr.istic.tools.scanexam.StudentGrade#getQuestionGrades()
	 * @see #getStudentGrade()
	 * @generated
	 */
	EReference getStudentGrade_QuestionGrades();

	/**
	 * Returns the meta object for class '{@link fr.istic.tools.scanexam.QuestionGrade <em>Question Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Question Grade</em>'.
	 * @see fr.istic.tools.scanexam.QuestionGrade
	 * @generated
	 */
	EClass getQuestionGrade();

	/**
	 * Returns the meta object for the reference '{@link fr.istic.tools.scanexam.QuestionGrade#getQuestion <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Question</em>'.
	 * @see fr.istic.tools.scanexam.QuestionGrade#getQuestion()
	 * @see #getQuestionGrade()
	 * @generated
	 */
	EReference getQuestionGrade_Question();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.QuestionGrade#isValidated <em>Validated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validated</em>'.
	 * @see fr.istic.tools.scanexam.QuestionGrade#isValidated()
	 * @see #getQuestionGrade()
	 * @generated
	 */
	EAttribute getQuestionGrade_Validated();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.QuestionGrade#getFilename <em>Filename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filename</em>'.
	 * @see fr.istic.tools.scanexam.QuestionGrade#getFilename()
	 * @see #getQuestionGrade()
	 * @generated
	 */
	EAttribute getQuestionGrade_Filename();

	/**
	 * Returns the meta object for the attribute '{@link fr.istic.tools.scanexam.QuestionGrade#getGrade <em>Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Grade</em>'.
	 * @see fr.istic.tools.scanexam.QuestionGrade#getGrade()
	 * @see #getQuestionGrade()
	 * @generated
	 */
	EAttribute getQuestionGrade_Grade();

	/**
	 * Returns the meta object for data type '{@link java.io.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>File</em>'.
	 * @see java.io.File
	 * @model instanceClass="java.io.File"
	 * @generated
	 */
	EDataType getFile();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ScanexamFactory getScanexamFactory();

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
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.ExamImpl <em>Exam</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.ExamImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getExam()
		 * @generated
		 */
		EClass EXAM = eINSTANCE.getExam();

		/**
		 * The meta object literal for the '<em><b>Filepath</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__FILEPATH = eINSTANCE.getExam_Filepath();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__LABEL = eINSTANCE.getExam_Label();

		/**
		 * The meta object literal for the '<em><b>Folder Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__FOLDER_PATH = eINSTANCE.getExam_FolderPath();

		/**
		 * The meta object literal for the '<em><b>Number Of Pages</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__NUMBER_OF_PAGES = eINSTANCE.getExam_NumberOfPages();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXAM__SCALE = eINSTANCE.getExam_Scale();

		/**
		 * The meta object literal for the '<em><b>Questions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__QUESTIONS = eINSTANCE.getExam_Questions();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.InfoFieldImpl <em>Info Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.InfoFieldImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getInfoField()
		 * @generated
		 */
		EClass INFO_FIELD = eINSTANCE.getInfoField();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.QuestionImpl <em>Question</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.QuestionImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getQuestion()
		 * @generated
		 */
		EClass QUESTION = eINSTANCE.getQuestion();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__LABEL = eINSTANCE.getQuestion_Label();

		/**
		 * The meta object literal for the '<em><b>Zone</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__ZONE = eINSTANCE.getQuestion_Zone();

		/**
		 * The meta object literal for the '<em><b>Mark Zone</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__MARK_ZONE = eINSTANCE.getQuestion_MarkZone();

		/**
		 * The meta object literal for the '<em><b>Default Grade Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__DEFAULT_GRADE_INDEX = eINSTANCE.getQuestion_DefaultGradeIndex();

		/**
		 * The meta object literal for the '<em><b>Grades</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__GRADES = eINSTANCE.getQuestion_Grades();

		/**
		 * The meta object literal for the '<em><b>Feedback</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__FEEDBACK = eINSTANCE.getQuestion_Feedback();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__WEIGHT = eINSTANCE.getQuestion_Weight();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.ScanZoneImpl <em>Scan Zone</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.ScanZoneImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getScanZone()
		 * @generated
		 */
		EClass SCAN_ZONE = eINSTANCE.getScanZone();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCAN_ZONE__X = eINSTANCE.getScanZone_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCAN_ZONE__Y = eINSTANCE.getScanZone_Y();

		/**
		 * The meta object literal for the '<em><b>W</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCAN_ZONE__W = eINSTANCE.getScanZone_W();

		/**
		 * The meta object literal for the '<em><b>H</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCAN_ZONE__H = eINSTANCE.getScanZone_H();

		/**
		 * The meta object literal for the '<em><b>Page</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCAN_ZONE__PAGE = eINSTANCE.getScanZone_Page();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SCAN_ZONE___TO_STRING = eINSTANCE.getScanZone__ToString();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.GradingDataImpl <em>Grading Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.GradingDataImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getGradingData()
		 * @generated
		 */
		EClass GRADING_DATA = eINSTANCE.getGradingData();

		/**
		 * The meta object literal for the '<em><b>Solution Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADING_DATA__SOLUTION_PATH = eINSTANCE.getGradingData_SolutionPath();

		/**
		 * The meta object literal for the '<em><b>Excel File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADING_DATA__EXCEL_FILE_NAME = eINSTANCE.getGradingData_ExcelFileName();

		/**
		 * The meta object literal for the '<em><b>Images</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRADING_DATA__IMAGES = eINSTANCE.getGradingData_Images();

		/**
		 * The meta object literal for the '<em><b>Exam</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRADING_DATA__EXAM = eINSTANCE.getGradingData_Exam();

		/**
		 * The meta object literal for the '<em><b>Grades</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRADING_DATA__GRADES = eINSTANCE.getGradingData_Grades();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.StudentGradeImpl <em>Student Grade</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.StudentGradeImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getStudentGrade()
		 * @generated
		 */
		EClass STUDENT_GRADE = eINSTANCE.getStudentGrade();

		/**
		 * The meta object literal for the '<em><b>Student ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_GRADE__STUDENT_ID = eINSTANCE.getStudentGrade_StudentID();

		/**
		 * The meta object literal for the '<em><b>Num Anonymat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT_GRADE__NUM_ANONYMAT = eINSTANCE.getStudentGrade_NumAnonymat();

		/**
		 * The meta object literal for the '<em><b>Question Grades</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDENT_GRADE__QUESTION_GRADES = eINSTANCE.getStudentGrade_QuestionGrades();

		/**
		 * The meta object literal for the '{@link fr.istic.tools.scanexam.impl.QuestionGradeImpl <em>Question Grade</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.istic.tools.scanexam.impl.QuestionGradeImpl
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getQuestionGrade()
		 * @generated
		 */
		EClass QUESTION_GRADE = eINSTANCE.getQuestionGrade();

		/**
		 * The meta object literal for the '<em><b>Question</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION_GRADE__QUESTION = eINSTANCE.getQuestionGrade_Question();

		/**
		 * The meta object literal for the '<em><b>Validated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_GRADE__VALIDATED = eINSTANCE.getQuestionGrade_Validated();

		/**
		 * The meta object literal for the '<em><b>Filename</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_GRADE__FILENAME = eINSTANCE.getQuestionGrade_Filename();

		/**
		 * The meta object literal for the '<em><b>Grade</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION_GRADE__GRADE = eINSTANCE.getQuestionGrade_Grade();

		/**
		 * The meta object literal for the '<em>File</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.io.File
		 * @see fr.istic.tools.scanexam.impl.ScanexamPackageImpl#getFile()
		 * @generated
		 */
		EDataType FILE = eINSTANCE.getFile();

	}

} //ScanexamPackage
