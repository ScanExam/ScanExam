/**
 */
package fr.istic.tools.scanexam;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Question</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.Question#getLabel <em>Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getZone <em>Zone</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getMarkZone <em>Mark Zone</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getDefaultGradeIndex <em>Default Grade Index</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getGrades <em>Grades</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getFeedback <em>Feedback</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.Question#getWeight <em>Weight</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion()
 * @model
 * @generated
 */
public interface Question extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_Label()
	 * @model unique="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Question#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zone</em>' containment reference.
	 * @see #setZone(ScanZone)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_Zone()
	 * @model containment="true"
	 * @generated
	 */
	ScanZone getZone();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Question#getZone <em>Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zone</em>' containment reference.
	 * @see #getZone()
	 * @generated
	 */
	void setZone(ScanZone value);

	/**
	 * Returns the value of the '<em><b>Mark Zone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mark Zone</em>' containment reference.
	 * @see #setMarkZone(ScanZone)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_MarkZone()
	 * @model containment="true"
	 * @generated
	 */
	ScanZone getMarkZone();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Question#getMarkZone <em>Mark Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mark Zone</em>' containment reference.
	 * @see #getMarkZone()
	 * @generated
	 */
	void setMarkZone(ScanZone value);

	/**
	 * Returns the value of the '<em><b>Default Grade Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Grade Index</em>' attribute.
	 * @see #setDefaultGradeIndex(int)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_DefaultGradeIndex()
	 * @model unique="false"
	 * @generated
	 */
	int getDefaultGradeIndex();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Question#getDefaultGradeIndex <em>Default Grade Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Grade Index</em>' attribute.
	 * @see #getDefaultGradeIndex()
	 * @generated
	 */
	void setDefaultGradeIndex(int value);

	/**
	 * Returns the value of the '<em><b>Grades</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grades</em>' attribute list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_Grades()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getGrades();

	/**
	 * Returns the value of the '<em><b>Feedback</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feedback</em>' attribute list.
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_Feedback()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getFeedback();

	/**
	 * Returns the value of the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight</em>' attribute.
	 * @see #setWeight(double)
	 * @see fr.istic.tools.scanexam.ScanexamPackage#getQuestion_Weight()
	 * @model unique="false"
	 * @generated
	 */
	double getWeight();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.Question#getWeight <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weight</em>' attribute.
	 * @see #getWeight()
	 * @generated
	 */
	void setWeight(double value);

} // Question
