/**
 */
package fr.istic.tools.scanexam.impl;

import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.ScanZone;
import fr.istic.tools.scanexam.ScanexamPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Question</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getZone <em>Zone</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getMarkZone <em>Mark Zone</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getDefaultGradeIndex <em>Default Grade Index</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getGrades <em>Grades</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getFeedback <em>Feedback</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.QuestionImpl#getWeight <em>Weight</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QuestionImpl extends MinimalEObjectImpl.Container implements Question {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getZone() <em>Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZone()
	 * @generated
	 * @ordered
	 */
	protected ScanZone zone;

	/**
	 * The cached value of the '{@link #getMarkZone() <em>Mark Zone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkZone()
	 * @generated
	 * @ordered
	 */
	protected ScanZone markZone;

	/**
	 * The default value of the '{@link #getDefaultGradeIndex() <em>Default Grade Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultGradeIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int DEFAULT_GRADE_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDefaultGradeIndex() <em>Default Grade Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultGradeIndex()
	 * @generated
	 * @ordered
	 */
	protected int defaultGradeIndex = DEFAULT_GRADE_INDEX_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGrades() <em>Grades</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGrades()
	 * @generated
	 * @ordered
	 */
	protected EList<String> grades;

	/**
	 * The cached value of the '{@link #getFeedback() <em>Feedback</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeedback()
	 * @generated
	 * @ordered
	 */
	protected EList<String> feedback;

	/**
	 * The default value of the '{@link #getWeight() <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWeight()
	 * @generated
	 * @ordered
	 */
	protected static final double WEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getWeight() <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWeight()
	 * @generated
	 * @ordered
	 */
	protected double weight = WEIGHT_EDEFAULT;

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
		return ScanexamPackage.Literals.QUESTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ScanZone getZone() {
		return zone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetZone(ScanZone newZone, NotificationChain msgs) {
		ScanZone oldZone = zone;
		zone = newZone;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__ZONE, oldZone, newZone);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setZone(ScanZone newZone) {
		if (newZone != zone) {
			NotificationChain msgs = null;
			if (zone != null)
				msgs = ((InternalEObject)zone).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.QUESTION__ZONE, null, msgs);
			if (newZone != null)
				msgs = ((InternalEObject)newZone).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.QUESTION__ZONE, null, msgs);
			msgs = basicSetZone(newZone, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__ZONE, newZone, newZone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ScanZone getMarkZone() {
		return markZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMarkZone(ScanZone newMarkZone, NotificationChain msgs) {
		ScanZone oldMarkZone = markZone;
		markZone = newMarkZone;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__MARK_ZONE, oldMarkZone, newMarkZone);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMarkZone(ScanZone newMarkZone) {
		if (newMarkZone != markZone) {
			NotificationChain msgs = null;
			if (markZone != null)
				msgs = ((InternalEObject)markZone).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.QUESTION__MARK_ZONE, null, msgs);
			if (newMarkZone != null)
				msgs = ((InternalEObject)newMarkZone).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.QUESTION__MARK_ZONE, null, msgs);
			msgs = basicSetMarkZone(newMarkZone, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__MARK_ZONE, newMarkZone, newMarkZone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDefaultGradeIndex() {
		return defaultGradeIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefaultGradeIndex(int newDefaultGradeIndex) {
		int oldDefaultGradeIndex = defaultGradeIndex;
		defaultGradeIndex = newDefaultGradeIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__DEFAULT_GRADE_INDEX, oldDefaultGradeIndex, defaultGradeIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getGrades() {
		if (grades == null) {
			grades = new EDataTypeEList<String>(String.class, this, ScanexamPackage.QUESTION__GRADES);
		}
		return grades;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getFeedback() {
		if (feedback == null) {
			feedback = new EDataTypeEList<String>(String.class, this, ScanexamPackage.QUESTION__FEEDBACK);
		}
		return feedback;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getWeight() {
		return weight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWeight(double newWeight) {
		double oldWeight = weight;
		weight = newWeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.QUESTION__WEIGHT, oldWeight, weight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScanexamPackage.QUESTION__ZONE:
				return basicSetZone(null, msgs);
			case ScanexamPackage.QUESTION__MARK_ZONE:
				return basicSetMarkZone(null, msgs);
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
			case ScanexamPackage.QUESTION__LABEL:
				return getLabel();
			case ScanexamPackage.QUESTION__ZONE:
				return getZone();
			case ScanexamPackage.QUESTION__MARK_ZONE:
				return getMarkZone();
			case ScanexamPackage.QUESTION__DEFAULT_GRADE_INDEX:
				return getDefaultGradeIndex();
			case ScanexamPackage.QUESTION__GRADES:
				return getGrades();
			case ScanexamPackage.QUESTION__FEEDBACK:
				return getFeedback();
			case ScanexamPackage.QUESTION__WEIGHT:
				return getWeight();
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
			case ScanexamPackage.QUESTION__LABEL:
				setLabel((String)newValue);
				return;
			case ScanexamPackage.QUESTION__ZONE:
				setZone((ScanZone)newValue);
				return;
			case ScanexamPackage.QUESTION__MARK_ZONE:
				setMarkZone((ScanZone)newValue);
				return;
			case ScanexamPackage.QUESTION__DEFAULT_GRADE_INDEX:
				setDefaultGradeIndex((Integer)newValue);
				return;
			case ScanexamPackage.QUESTION__GRADES:
				getGrades().clear();
				getGrades().addAll((Collection<? extends String>)newValue);
				return;
			case ScanexamPackage.QUESTION__FEEDBACK:
				getFeedback().clear();
				getFeedback().addAll((Collection<? extends String>)newValue);
				return;
			case ScanexamPackage.QUESTION__WEIGHT:
				setWeight((Double)newValue);
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
			case ScanexamPackage.QUESTION__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ScanexamPackage.QUESTION__ZONE:
				setZone((ScanZone)null);
				return;
			case ScanexamPackage.QUESTION__MARK_ZONE:
				setMarkZone((ScanZone)null);
				return;
			case ScanexamPackage.QUESTION__DEFAULT_GRADE_INDEX:
				setDefaultGradeIndex(DEFAULT_GRADE_INDEX_EDEFAULT);
				return;
			case ScanexamPackage.QUESTION__GRADES:
				getGrades().clear();
				return;
			case ScanexamPackage.QUESTION__FEEDBACK:
				getFeedback().clear();
				return;
			case ScanexamPackage.QUESTION__WEIGHT:
				setWeight(WEIGHT_EDEFAULT);
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
			case ScanexamPackage.QUESTION__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ScanexamPackage.QUESTION__ZONE:
				return zone != null;
			case ScanexamPackage.QUESTION__MARK_ZONE:
				return markZone != null;
			case ScanexamPackage.QUESTION__DEFAULT_GRADE_INDEX:
				return defaultGradeIndex != DEFAULT_GRADE_INDEX_EDEFAULT;
			case ScanexamPackage.QUESTION__GRADES:
				return grades != null && !grades.isEmpty();
			case ScanexamPackage.QUESTION__FEEDBACK:
				return feedback != null && !feedback.isEmpty();
			case ScanexamPackage.QUESTION__WEIGHT:
				return weight != WEIGHT_EDEFAULT;
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
		result.append(" (label: ");
		result.append(label);
		result.append(", defaultGradeIndex: ");
		result.append(defaultGradeIndex);
		result.append(", grades: ");
		result.append(grades);
		result.append(", feedback: ");
		result.append(feedback);
		result.append(", weight: ");
		result.append(weight);
		result.append(')');
		return result.toString();
	}

} //QuestionImpl
