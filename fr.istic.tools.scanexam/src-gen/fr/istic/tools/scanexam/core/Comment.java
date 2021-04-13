/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Comment#getPageId <em>Page Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Comment#getX <em>X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Comment#getY <em>Y</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Comment#getPointerX <em>Pointer X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Comment#getPointerY <em>Pointer Y</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getComment()
 * @model
 * @generated
 */
public interface Comment extends EObject {
	/**
	 * Returns the value of the '<em><b>Page Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page Id</em>' attribute.
	 * @see #setPageId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getComment_PageId()
	 * @model unique="false"
	 * @generated
	 */
	int getPageId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Comment#getPageId <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page Id</em>' attribute.
	 * @see #getPageId()
	 * @generated
	 */
	void setPageId(int value);

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getComment_X()
	 * @model unique="false"
	 * @generated
	 */
	float getX();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Comment#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(float value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getComment_Y()
	 * @model unique="false"
	 * @generated
	 */
	float getY();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Comment#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(float value);

	/**
	 * Returns the value of the '<em><b>Pointer X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pointer X</em>' attribute.
	 * @see #setPointerX(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getComment_PointerX()
	 * @model unique="false"
	 * @generated
	 */
	float getPointerX();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Comment#getPointerX <em>Pointer X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pointer X</em>' attribute.
	 * @see #getPointerX()
	 * @generated
	 */
	void setPointerX(float value);

	/**
	 * Returns the value of the '<em><b>Pointer Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pointer Y</em>' attribute.
	 * @see #setPointerY(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getComment_PointerY()
	 * @model unique="false"
	 * @generated
	 */
	float getPointerY();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Comment#getPointerY <em>Pointer Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pointer Y</em>' attribute.
	 * @see #getPointerY()
	 * @generated
	 */
	void setPointerY(float value);

} // Comment
