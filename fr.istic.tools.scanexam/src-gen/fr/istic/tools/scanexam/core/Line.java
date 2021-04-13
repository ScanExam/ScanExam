/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getX1 <em>X1</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getY1 <em>Y1</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getX2 <em>X2</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getY2 <em>Y2</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getThinkness <em>Thinkness</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Line#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getLine()
 * @model
 * @generated
 */
public interface Line extends EObject {
	/**
	 * Returns the value of the '<em><b>X1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X1</em>' attribute.
	 * @see #setX1(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_X1()
	 * @model unique="false"
	 * @generated
	 */
	float getX1();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getX1 <em>X1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X1</em>' attribute.
	 * @see #getX1()
	 * @generated
	 */
	void setX1(float value);

	/**
	 * Returns the value of the '<em><b>Y1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y1</em>' attribute.
	 * @see #setY1(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_Y1()
	 * @model unique="false"
	 * @generated
	 */
	float getY1();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getY1 <em>Y1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y1</em>' attribute.
	 * @see #getY1()
	 * @generated
	 */
	void setY1(float value);

	/**
	 * Returns the value of the '<em><b>X2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X2</em>' attribute.
	 * @see #setX2(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_X2()
	 * @model unique="false"
	 * @generated
	 */
	float getX2();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getX2 <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X2</em>' attribute.
	 * @see #getX2()
	 * @generated
	 */
	void setX2(float value);

	/**
	 * Returns the value of the '<em><b>Y2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y2</em>' attribute.
	 * @see #setY2(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_Y2()
	 * @model unique="false"
	 * @generated
	 */
	float getY2();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getY2 <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y2</em>' attribute.
	 * @see #getY2()
	 * @generated
	 */
	void setY2(float value);

	/**
	 * Returns the value of the '<em><b>Thinkness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thinkness</em>' attribute.
	 * @see #setThinkness(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_Thinkness()
	 * @model unique="false"
	 * @generated
	 */
	float getThinkness();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getThinkness <em>Thinkness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thinkness</em>' attribute.
	 * @see #getThinkness()
	 * @generated
	 */
	void setThinkness(float value);

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getLine_Color()
	 * @model unique="false"
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Line#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

} // Line
