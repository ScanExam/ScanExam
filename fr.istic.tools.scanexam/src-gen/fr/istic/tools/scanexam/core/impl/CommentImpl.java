/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CorePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getPageId <em>Page Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getX <em>X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getY <em>Y</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getPointerX <em>Pointer X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.CommentImpl#getPointerY <em>Pointer Y</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommentImpl extends MinimalEObjectImpl.Container implements Comment {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageId()
	 * @generated
	 * @ordered
	 */
	protected static final int PAGE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageId()
	 * @generated
	 * @ordered
	 */
	protected int pageId = PAGE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final float X_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected float x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final float Y_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected float y = Y_EDEFAULT;

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
	protected CommentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.COMMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPageId() {
		return pageId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPageId(int newPageId) {
		int oldPageId = pageId;
		pageId = newPageId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__PAGE_ID, oldPageId, pageId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(float newX) {
		float oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(float newY) {
		float oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__Y, oldY, y));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__POINTER_X, oldPointerX, pointerX));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.COMMENT__POINTER_Y, oldPointerY, pointerY));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.COMMENT__ID:
				return getId();
			case CorePackage.COMMENT__PAGE_ID:
				return getPageId();
			case CorePackage.COMMENT__X:
				return getX();
			case CorePackage.COMMENT__Y:
				return getY();
			case CorePackage.COMMENT__POINTER_X:
				return getPointerX();
			case CorePackage.COMMENT__POINTER_Y:
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
			case CorePackage.COMMENT__ID:
				setId((Integer)newValue);
				return;
			case CorePackage.COMMENT__PAGE_ID:
				setPageId((Integer)newValue);
				return;
			case CorePackage.COMMENT__X:
				setX((Float)newValue);
				return;
			case CorePackage.COMMENT__Y:
				setY((Float)newValue);
				return;
			case CorePackage.COMMENT__POINTER_X:
				setPointerX((Float)newValue);
				return;
			case CorePackage.COMMENT__POINTER_Y:
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
			case CorePackage.COMMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case CorePackage.COMMENT__PAGE_ID:
				setPageId(PAGE_ID_EDEFAULT);
				return;
			case CorePackage.COMMENT__X:
				setX(X_EDEFAULT);
				return;
			case CorePackage.COMMENT__Y:
				setY(Y_EDEFAULT);
				return;
			case CorePackage.COMMENT__POINTER_X:
				setPointerX(POINTER_X_EDEFAULT);
				return;
			case CorePackage.COMMENT__POINTER_Y:
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
			case CorePackage.COMMENT__ID:
				return id != ID_EDEFAULT;
			case CorePackage.COMMENT__PAGE_ID:
				return pageId != PAGE_ID_EDEFAULT;
			case CorePackage.COMMENT__X:
				return x != X_EDEFAULT;
			case CorePackage.COMMENT__Y:
				return y != Y_EDEFAULT;
			case CorePackage.COMMENT__POINTER_X:
				return pointerX != POINTER_X_EDEFAULT;
			case CorePackage.COMMENT__POINTER_Y:
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
		result.append(" (id: ");
		result.append(id);
		result.append(", pageId: ");
		result.append(pageId);
		result.append(", x: ");
		result.append(x);
		result.append(", y: ");
		result.append(y);
		result.append(", pointerX: ");
		result.append(pointerX);
		result.append(", pointerY: ");
		result.append(pointerY);
		result.append(')');
		return result.toString();
	}

} //CommentImpl
