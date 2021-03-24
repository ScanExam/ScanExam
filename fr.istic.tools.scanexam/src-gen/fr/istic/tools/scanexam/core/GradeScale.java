/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Grade Scale</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.GradeScale#getMaxPoint <em>Max Point</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.GradeScale#getSteps <em>Steps</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale()
 * @model
 * @generated
 */
public interface GradeScale extends EObject {
	/**
	 * Returns the value of the '<em><b>Max Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Point</em>' attribute.
	 * @see #setMaxPoint(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale_MaxPoint()
	 * @model unique="false"
	 * @generated
	 */
	float getMaxPoint();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.GradeScale#getMaxPoint <em>Max Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Point</em>' attribute.
	 * @see #getMaxPoint()
	 * @generated
	 */
	void setMaxPoint(float value);

	/**
	 * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.GradeEntry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale_Steps()
	 * @model containment="true"
	 * @generated
	 */
	EList<GradeEntry> getSteps();

} // GradeScale
