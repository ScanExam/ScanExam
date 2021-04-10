/**
 */
package fr.istic.tools.scanexam.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.TextComment#getText <em>Text</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.TextComment#getPointerX <em>Pointer X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.TextComment#getPointerY <em>Pointer Y</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getTextComment()
 * @model
 * @generated
 */
public interface TextComment extends Comment {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getTextComment_Text()
	 * @model unique="false"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.TextComment#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Pointer X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pointer X</em>' attribute.
	 * @see #setPointerX(float)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getTextComment_PointerX()
	 * @model unique="false"
	 * @generated
	 */
	float getPointerX();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.TextComment#getPointerX <em>Pointer X</em>}' attribute.
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
	 * @see fr.istic.tools.scanexam.core.CorePackage#getTextComment_PointerY()
	 * @model unique="false"
	 * @generated
	 */
	float getPointerY();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.TextComment#getPointerY <em>Pointer Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pointer Y</em>' attribute.
	 * @see #getPointerY()
	 * @generated
	 */
	void setPointerY(float value);

} // TextComment
