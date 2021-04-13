/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.Line;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getX1 <em>X1</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getY1 <em>Y1</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getX2 <em>X2</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getY2 <em>Y2</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getThinkness <em>Thinkness</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.LineImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LineImpl extends MinimalEObjectImpl.Container implements Line {
	/**
	 * The default value of the '{@link #getX1() <em>X1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX1()
	 * @generated
	 * @ordered
	 */
	protected static final float X1_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getX1() <em>X1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX1()
	 * @generated
	 * @ordered
	 */
	protected float x1 = X1_EDEFAULT;

	/**
	 * The default value of the '{@link #getY1() <em>Y1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY1()
	 * @generated
	 * @ordered
	 */
	protected static final float Y1_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getY1() <em>Y1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY1()
	 * @generated
	 * @ordered
	 */
	protected float y1 = Y1_EDEFAULT;

	/**
	 * The default value of the '{@link #getX2() <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX2()
	 * @generated
	 * @ordered
	 */
	protected static final float X2_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getX2() <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX2()
	 * @generated
	 * @ordered
	 */
	protected float x2 = X2_EDEFAULT;

	/**
	 * The default value of the '{@link #getY2() <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY2()
	 * @generated
	 * @ordered
	 */
	protected static final float Y2_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getY2() <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY2()
	 * @generated
	 * @ordered
	 */
	protected float y2 = Y2_EDEFAULT;

	/**
	 * The default value of the '{@link #getThinkness() <em>Thinkness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThinkness()
	 * @generated
	 * @ordered
	 */
	protected static final float THINKNESS_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getThinkness() <em>Thinkness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThinkness()
	 * @generated
	 * @ordered
	 */
	protected float thinkness = THINKNESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.LINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getX1() {
		return x1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX1(float newX1) {
		float oldX1 = x1;
		x1 = newX1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__X1, oldX1, x1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY1(float newY1) {
		float oldY1 = y1;
		y1 = newY1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__Y1, oldY1, y1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX2(float newX2) {
		float oldX2 = x2;
		x2 = newX2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__X2, oldX2, x2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY2(float newY2) {
		float oldY2 = y2;
		y2 = newY2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__Y2, oldY2, y2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getThinkness() {
		return thinkness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThinkness(float newThinkness) {
		float oldThinkness = thinkness;
		thinkness = newThinkness;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__THINKNESS, oldThinkness, thinkness));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(String newColor) {
		String oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.LINE__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.LINE__X1:
				return getX1();
			case CorePackage.LINE__Y1:
				return getY1();
			case CorePackage.LINE__X2:
				return getX2();
			case CorePackage.LINE__Y2:
				return getY2();
			case CorePackage.LINE__THINKNESS:
				return getThinkness();
			case CorePackage.LINE__COLOR:
				return getColor();
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
			case CorePackage.LINE__X1:
				setX1((Float)newValue);
				return;
			case CorePackage.LINE__Y1:
				setY1((Float)newValue);
				return;
			case CorePackage.LINE__X2:
				setX2((Float)newValue);
				return;
			case CorePackage.LINE__Y2:
				setY2((Float)newValue);
				return;
			case CorePackage.LINE__THINKNESS:
				setThinkness((Float)newValue);
				return;
			case CorePackage.LINE__COLOR:
				setColor((String)newValue);
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
			case CorePackage.LINE__X1:
				setX1(X1_EDEFAULT);
				return;
			case CorePackage.LINE__Y1:
				setY1(Y1_EDEFAULT);
				return;
			case CorePackage.LINE__X2:
				setX2(X2_EDEFAULT);
				return;
			case CorePackage.LINE__Y2:
				setY2(Y2_EDEFAULT);
				return;
			case CorePackage.LINE__THINKNESS:
				setThinkness(THINKNESS_EDEFAULT);
				return;
			case CorePackage.LINE__COLOR:
				setColor(COLOR_EDEFAULT);
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
			case CorePackage.LINE__X1:
				return x1 != X1_EDEFAULT;
			case CorePackage.LINE__Y1:
				return y1 != Y1_EDEFAULT;
			case CorePackage.LINE__X2:
				return x2 != X2_EDEFAULT;
			case CorePackage.LINE__Y2:
				return y2 != Y2_EDEFAULT;
			case CorePackage.LINE__THINKNESS:
				return thinkness != THINKNESS_EDEFAULT;
			case CorePackage.LINE__COLOR:
				return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
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
		result.append(" (x1: ");
		result.append(x1);
		result.append(", y1: ");
		result.append(y1);
		result.append(", x2: ");
		result.append(x2);
		result.append(", y2: ");
		result.append(y2);
		result.append(", thinkness: ");
		result.append(thinkness);
		result.append(", color: ");
		result.append(color);
		result.append(')');
		return result.toString();
	}

} //LineImpl
