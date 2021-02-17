/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Question</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.QuestionImpl#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.QuestionImpl#getName <em>Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.QuestionImpl#getGradeScale <em>Grade Scale</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.QuestionImpl#getZone <em>Zone</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QuestionImpl extends MinimalEObjectImpl.Container implements Question {
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGradeScale() <em>Grade Scale</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGradeScale()
	 * @generated
	 * @ordered
	 */
	protected GradeScale gradeScale;

	/**
	 * The cached value of the '{@link #getZone() <em>Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZone()
	 * @generated
	 * @ordered
	 */
	protected QuestionZone zone;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QuestionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.QUESTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GradeScale getGradeScale() {
		return gradeScale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGradeScale(GradeScale newGradeScale, NotificationChain msgs) {
		GradeScale oldGradeScale = gradeScale;
		gradeScale = newGradeScale;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__GRADE_SCALE, oldGradeScale, newGradeScale);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGradeScale(GradeScale newGradeScale) {
		if (newGradeScale != gradeScale) {
			NotificationChain msgs = null;
			if (gradeScale != null)
				msgs = ((InternalEObject)gradeScale).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CorePackage.QUESTION__GRADE_SCALE, null, msgs);
			if (newGradeScale != null)
				msgs = ((InternalEObject)newGradeScale).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CorePackage.QUESTION__GRADE_SCALE, null, msgs);
			msgs = basicSetGradeScale(newGradeScale, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__GRADE_SCALE, newGradeScale, newGradeScale));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QuestionZone getZone() {
		return zone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetZone(QuestionZone newZone, NotificationChain msgs) {
		QuestionZone oldZone = zone;
		zone = newZone;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__ZONE, oldZone, newZone);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZone(QuestionZone newZone) {
		if (newZone != zone) {
			NotificationChain msgs = null;
			if (zone != null)
				msgs = ((InternalEObject)zone).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CorePackage.QUESTION__ZONE, null, msgs);
			if (newZone != null)
				msgs = ((InternalEObject)newZone).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CorePackage.QUESTION__ZONE, null, msgs);
			msgs = basicSetZone(newZone, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.QUESTION__ZONE, newZone, newZone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.QUESTION__GRADE_SCALE:
				return basicSetGradeScale(null, msgs);
			case CorePackage.QUESTION__ZONE:
				return basicSetZone(null, msgs);
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
			case CorePackage.QUESTION__ID:
				return getId();
			case CorePackage.QUESTION__NAME:
				return getName();
			case CorePackage.QUESTION__GRADE_SCALE:
				return getGradeScale();
			case CorePackage.QUESTION__ZONE:
				return getZone();
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
			case CorePackage.QUESTION__ID:
				setId((Integer)newValue);
				return;
			case CorePackage.QUESTION__NAME:
				setName((String)newValue);
				return;
			case CorePackage.QUESTION__GRADE_SCALE:
				setGradeScale((GradeScale)newValue);
				return;
			case CorePackage.QUESTION__ZONE:
				setZone((QuestionZone)newValue);
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
			case CorePackage.QUESTION__ID:
				setId(ID_EDEFAULT);
				return;
			case CorePackage.QUESTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CorePackage.QUESTION__GRADE_SCALE:
				setGradeScale((GradeScale)null);
				return;
			case CorePackage.QUESTION__ZONE:
				setZone((QuestionZone)null);
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
			case CorePackage.QUESTION__ID:
				return id != ID_EDEFAULT;
			case CorePackage.QUESTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CorePackage.QUESTION__GRADE_SCALE:
				return gradeScale != null;
			case CorePackage.QUESTION__ZONE:
				return zone != null;
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
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //QuestionImpl
