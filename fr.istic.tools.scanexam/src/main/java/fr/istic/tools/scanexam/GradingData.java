/**
 */
package fr.istic.tools.scanexam;

import java.io.File;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Grading Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.GradingData#getSolutionPath <em>Solution Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.GradingData#getExcelFileName <em>Excel File Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.GradingData#getImages <em>Images</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.GradingData#getExam <em>Exam</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.GradingData#getGrades <em>Grades</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData()
 * @model
 * @generated
 */
public interface GradingData extends EObject {
	/**
	 * Returns the value of the '<em><b>Solution Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solution Path</em>' attribute.
	 * @see #setSolutionPath(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData_SolutionPath()
	 * @model unique="false"
	 * @generated
	 */
	String getSolutionPath();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.GradingData#getSolutionPath <em>Solution Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solution Path</em>' attribute.
	 * @see #getSolutionPath()
	 * @generated
	 */
	void setSolutionPath(String value);

	/**
	 * Returns the value of the '<em><b>Excel File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excel File Name</em>' attribute.
	 * @see #setExcelFileName(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData_ExcelFileName()
	 * @model unique="false"
	 * @generated
	 */
	String getExcelFileName();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.GradingData#getExcelFileName <em>Excel File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excel File Name</em>' attribute.
	 * @see #getExcelFileName()
	 * @generated
	 */
	void setExcelFileName(String value);

	/**
	 * Returns the value of the '<em><b>Images</b></em>' attribute list.
	 * The list contents are of type {@link java.io.File}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Images</em>' attribute list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData_Images()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.File"
	 * @generated
	 */
	EList<File> getImages();

	/**
	 * Returns the value of the '<em><b>Exam</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exam</em>' containment reference.
	 * @see #setExam(Exam)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData_Exam()
	 * @model containment="true"
	 * @generated
	 */
	Exam getExam();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.GradingData#getExam <em>Exam</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exam</em>' containment reference.
	 * @see #getExam()
	 * @generated
	 */
	void setExam(Exam value);

	/**
	 * Returns the value of the '<em><b>Grades</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.StudentGrade}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grades</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getGradingData_Grades()
	 * @model containment="true"
	 * @generated
	 */
	EList<StudentGrade> getGrades();

} // GradingData
