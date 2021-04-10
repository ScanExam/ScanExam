/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.TextComment;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.TextCommentImpl#getText <em>Text</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.TextCommentImpl#getPointerX <em>Pointer X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.TextCommentImpl#getPointerY <em>Pointer Y</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TextCommentImpl extends CommentImpl implements TextComment {
	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPointerX() <em>Pointer X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointerX()
	 * @generated
	 * @ordered
	 */
	protected static final float POINTER_X_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getPointerX() <em>Pointer X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointerX()
	 * @generated
	 * @ordered
	 */
	protected float pointerX = POINTER_X_EDEFAULT;

	/**
	 * The default value of the '{@link #getPointerY() <em>Pointer Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointerY()
	 * @generated
	 * @ordered
	 */
	protected static final float POINTER_Y_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getPointerY() <em>Pointer Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointerY()
	 * @generated
	 * @ordered
	 */
	protected float pointerY = POINTER_Y_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextCommentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.TEXT_COMMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEXT_COMMENT__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getPointerX() {
		return pointerX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointerX(float newPointerX) {
		float oldPointerX = pointerX;
		pointerX = newPointerX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEXT_COMMENT__POINTER_X, oldPointerX, pointerX));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getPointerY() {
		return pointerY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointerY(float newPointerY) {
		float oldPointerY = pointerY;
		pointerY = newPointerY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEXT_COMMENT__POINTER_Y, oldPointerY, pointerY));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.TEXT_COMMENT__TEXT:
				return getText();
			case CorePackage.TEXT_COMMENT__POINTER_X:
				return getPointerX();
			case CorePackage.TEXT_COMMENT__POINTER_Y:
				return getPointerY();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.TEXT_COMMENT__TEXT:
				setText((String)newValue);
				return;
			case CorePackage.TEXT_COMMENT__POINTER_X:
				setPointerX((Float)newValue);
				return;
			case CorePackage.TEXT_COMMENT__POINTER_Y:
				setPointerY((Float)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CorePackage.TEXT_COMMENT__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case CorePackage.TEXT_COMMENT__POINTER_X:
				setPointerX(POINTER_X_EDEFAULT);
				return;
			case CorePackage.TEXT_COMMENT__POINTER_Y:
				setPointerY(POINTER_Y_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorePackage.TEXT_COMMENT__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case CorePackage.TEXT_COMMENT__POINTER_X:
				return pointerX != POINTER_X_EDEFAULT;
			case CorePackage.TEXT_COMMENT__POINTER_Y:
				return pointerY != POINTER_Y_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (text: ");
		result.append(text);
		result.append(", pointerX: ");
		result.append(pointerX);
		result.append(", pointerY: ");
		result.append(pointerY);
		result.append(')');
		return result.toString();
	}

} //TextCommentImpl
