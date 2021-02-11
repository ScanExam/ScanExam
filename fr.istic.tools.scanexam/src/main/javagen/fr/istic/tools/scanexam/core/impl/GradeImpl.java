/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.Grade;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Grade</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.GradeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.GradeImpl#getGradeLabel <em>Grade Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.GradeImpl#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GradeImpl extends MinimalEObjectImpl.Container implements Grade {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final float VALUE_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected float value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getGradeLabel() <em>Grade Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGradeLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String GRADE_LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGradeLabel() <em>Grade Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGradeLabel()
	 * @generated
	 * @ordered
	 */
	protected String gradeLabel = GRADE_LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Comment> comments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GradeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.GRADE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(float newValue) {
		float oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.GRADE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGradeLabel() {
		return gradeLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGradeLabel(String newGradeLabel) {
		String oldGradeLabel = gradeLabel;
		gradeLabel = newGradeLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.GRADE__GRADE_LABEL, oldGradeLabel, gradeLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<Comment>(Comment.class, this, CorePackage.GRADE__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.GRADE__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.GRADE__VALUE:
				return getValue();
			case CorePackage.GRADE__GRADE_LABEL:
				return getGradeLabel();
			case CorePackage.GRADE__COMMENTS:
				return getComments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.GRADE__VALUE:
				setValue((Float)newValue);
				return;
			case CorePackage.GRADE__GRADE_LABEL:
				setGradeLabel((String)newValue);
				return;
			case CorePackage.GRADE__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
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
			case CorePackage.GRADE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case CorePackage.GRADE__GRADE_LABEL:
				setGradeLabel(GRADE_LABEL_EDEFAULT);
				return;
			case CorePackage.GRADE__COMMENTS:
				getComments().clear();
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
			case CorePackage.GRADE__VALUE:
				return value != VALUE_EDEFAULT;
			case CorePackage.GRADE__GRADE_LABEL:
				return GRADE_LABEL_EDEFAULT == null ? gradeLabel != null : !GRADE_LABEL_EDEFAULT.equals(gradeLabel);
			case CorePackage.GRADE__COMMENTS:
				return comments != null && !comments.isEmpty();
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
		result.append(" (value: ");
		result.append(value);
		result.append(", gradeLabel: ");
		result.append(gradeLabel);
		result.append(')');
		return result.toString();
	}

} //GradeImpl
