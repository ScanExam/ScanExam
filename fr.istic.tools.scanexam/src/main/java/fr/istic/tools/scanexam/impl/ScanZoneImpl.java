/**
 */
package fr.istic.tools.scanexam.impl;

import fr.istic.tools.scanexam.ScanZone;
import fr.istic.tools.scanexam.ScanexamPackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scan Zone</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.impl.ScanZoneImpl#getX <em>X</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ScanZoneImpl#getY <em>Y</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ScanZoneImpl#getW <em>W</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ScanZoneImpl#getH <em>H</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ScanZoneImpl#getPage <em>Page</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScanZoneImpl extends MinimalEObjectImpl.Container implements ScanZone {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * The default value of the '{@link #getW() <em>W</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getW()
	 * @generated
	 * @ordered
	 */
	protected static final int W_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getW() <em>W</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getW()
	 * @generated
	 * @ordered
	 */
	protected int w = W_EDEFAULT;

	/**
	 * The default value of the '{@link #getH() <em>H</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getH()
	 * @generated
	 * @ordered
	 */
	protected static final int H_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getH() <em>H</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getH()
	 * @generated
	 * @ordered
	 */
	protected int h = H_EDEFAULT;

	/**
	 * The default value of the '{@link #getPage() <em>Page</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPage()
	 * @generated
	 * @ordered
	 */
	protected static final int PAGE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPage() <em>Page</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPage()
	 * @generated
	 * @ordered
	 */
	protected int page = PAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScanZoneImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScanexamPackage.Literals.SCAN_ZONE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.SCAN_ZONE__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.SCAN_ZONE__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getW() {
		return w;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setW(int newW) {
		int oldW = w;
		w = newW;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.SCAN_ZONE__W, oldW, w));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getH() {
		return h;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setH(int newH) {
		int oldH = h;
		h = newH;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.SCAN_ZONE__H, oldH, h));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPage() {
		return page;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPage(int newPage) {
		int oldPage = page;
		page = newPage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.SCAN_ZONE__PAGE, oldPage, page));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		int _x = this.getX();
		String _plus = ("[" + Integer.valueOf(_x));
		String _plus_1 = (_plus + ",");
		int _y = this.getY();
		String _plus_2 = (_plus_1 + Integer.valueOf(_y));
		String _plus_3 = (_plus_2 + "]->[");
		int _x_1 = this.getX();
		int _w = this.getW();
		int _plus_4 = (_x_1 + _w);
		String _plus_5 = (_plus_3 + Integer.valueOf(_plus_4));
		String _plus_6 = (_plus_5 + ",");
		int _y_1 = this.getY();
		int _h = this.getH();
		int _plus_7 = (_y_1 + _h);
		String _plus_8 = (_plus_6 + Integer.valueOf(_plus_7));
		String _plus_9 = (_plus_8 + "]@");
		int _page = this.getPage();
		return (_plus_9 + Integer.valueOf(_page));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ScanexamPackage.SCAN_ZONE__X:
				return getX();
			case ScanexamPackage.SCAN_ZONE__Y:
				return getY();
			case ScanexamPackage.SCAN_ZONE__W:
				return getW();
			case ScanexamPackage.SCAN_ZONE__H:
				return getH();
			case ScanexamPackage.SCAN_ZONE__PAGE:
				return getPage();
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
			case ScanexamPackage.SCAN_ZONE__X:
				setX((Integer)newValue);
				return;
			case ScanexamPackage.SCAN_ZONE__Y:
				setY((Integer)newValue);
				return;
			case ScanexamPackage.SCAN_ZONE__W:
				setW((Integer)newValue);
				return;
			case ScanexamPackage.SCAN_ZONE__H:
				setH((Integer)newValue);
				return;
			case ScanexamPackage.SCAN_ZONE__PAGE:
				setPage((Integer)newValue);
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
			case ScanexamPackage.SCAN_ZONE__X:
				setX(X_EDEFAULT);
				return;
			case ScanexamPackage.SCAN_ZONE__Y:
				setY(Y_EDEFAULT);
				return;
			case ScanexamPackage.SCAN_ZONE__W:
				setW(W_EDEFAULT);
				return;
			case ScanexamPackage.SCAN_ZONE__H:
				setH(H_EDEFAULT);
				return;
			case ScanexamPackage.SCAN_ZONE__PAGE:
				setPage(PAGE_EDEFAULT);
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
			case ScanexamPackage.SCAN_ZONE__X:
				return x != X_EDEFAULT;
			case ScanexamPackage.SCAN_ZONE__Y:
				return y != Y_EDEFAULT;
			case ScanexamPackage.SCAN_ZONE__W:
				return w != W_EDEFAULT;
			case ScanexamPackage.SCAN_ZONE__H:
				return h != H_EDEFAULT;
			case ScanexamPackage.SCAN_ZONE__PAGE:
				return page != PAGE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ScanexamPackage.SCAN_ZONE___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ScanZoneImpl
