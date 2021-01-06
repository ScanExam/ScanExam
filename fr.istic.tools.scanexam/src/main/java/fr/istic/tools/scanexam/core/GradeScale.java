/**
 */
package fr.istic.tools.scanexam.core;

import fr.istic.tools.scanexam.utils.Pair;

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
 *   <li>{@link fr.istic.tools.scanexam.core.GradeScale#getWeigth <em>Weigth</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.GradeScale#getSteps <em>Steps</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale()
 * @model
 * @generated
 */
public interface GradeScale extends EObject {
	/**
	 * Returns the value of the '<em><b>Weigth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weigth</em>' attribute.
	 * @see #setWeigth(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale_Weigth()
	 * @model unique="false"
	 * @generated
	 */
	float getWeigth();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.GradeScale#getWeigth <em>Weigth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weigth</em>' attribute.
	 * @see #getWeigth()
	 * @generated
	 */
	void setWeigth(float value);

	/**
	 * Returns the value of the '<em><b>Steps</b></em>' attribute list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.utils.Pair}<code>&lt;java.lang.String, java.lang.Float&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' attribute list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGradeScale_Steps()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.Pair&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EFloatObject&gt;"
	 * @generated
	 */
	EList<Pair<String, Float>> getSteps();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" idUnique="false" valueUnique="false"
	 * @generated
	 */
	boolean modifyStep(int id, float value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" idUnique="false"
	 * @generated
	 */
	boolean removeStep(int id);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" idUnique="false"
	 * @generated
	 */
	float getStep(int id);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" commentUnique="false" valueUnique="false"
	 * @generated
	 */
	boolean addStep(String comment, float value);

} // GradeScale
