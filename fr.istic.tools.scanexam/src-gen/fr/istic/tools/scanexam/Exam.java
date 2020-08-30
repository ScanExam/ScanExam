/**
 */
package fr.istic.tools.scanexam;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exam</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getLabel <em>Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getFolderPath <em>Folder Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getNumberOfPages <em>Number Of Pages</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getScale <em>Scale</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Exam#getQuestions <em>Questions</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam()
 * @model
 * @generated
 */
public interface Exam extends EObject {
	/**
	 * Returns the value of the '<em><b>Filepath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filepath</em>' attribute.
	 * @see #setFilepath(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_Filepath()
	 * @model unique="false"
	 * @generated
	 */
	String getFilepath();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Exam#getFilepath <em>Filepath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filepath</em>' attribute.
	 * @see #getFilepath()
	 * @generated
	 */
	void setFilepath(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_Label()
	 * @model unique="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Exam#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Folder Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder Path</em>' attribute.
	 * @see #setFolderPath(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_FolderPath()
	 * @model unique="false"
	 * @generated
	 */
	String getFolderPath();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Exam#getFolderPath <em>Folder Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder Path</em>' attribute.
	 * @see #getFolderPath()
	 * @generated
	 */
	void setFolderPath(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Pages</em>' attribute.
	 * @see #setNumberOfPages(int)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_NumberOfPages()
	 * @model unique="false"
	 * @generated
	 */
	int getNumberOfPages();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Exam#getNumberOfPages <em>Number Of Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Pages</em>' attribute.
	 * @see #getNumberOfPages()
	 * @generated
	 */
	void setNumberOfPages(int value);

	/**
	 * Returns the value of the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale</em>' attribute.
	 * @see #setScale(int)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_Scale()
	 * @model unique="false"
	 * @generated
	 */
	int getScale();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Exam#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale</em>' attribute.
	 * @see #getScale()
	 * @generated
	 */
	void setScale(int value);

	/**
	 * Returns the value of the '<em><b>Questions</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.Question}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Questions</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getExam_Questions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Question> getQuestions();

} // Exam
