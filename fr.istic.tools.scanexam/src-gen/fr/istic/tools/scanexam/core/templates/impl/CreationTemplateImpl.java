/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.Exam;

import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Creation Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl#getDocument <em>Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreationTemplateImpl extends MinimalEObjectImpl.Container implements CreationTemplate {
	/**
	 * The cached value of the '{@link #getDocument() <em>Document</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocument()
	 * @generated
	 * @ordered
	 */
	protected EList<Byte> document;

	/**
	 * The default value of the '{@link #getExam() <em>Exam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExam()
	 * @generated
	 * @ordered
	 */
	protected static final Exam EXAM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExam() <em>Exam</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExam()
	 * @generated
	 * @ordered
	 */
	protected Exam exam = EXAM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreationTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TemplatesPackage.Literals.CREATION_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Byte> getDocument() {
		if (document == null) {
			document = new EDataTypeEList<Byte>(Byte.class, this, TemplatesPackage.CREATION_TEMPLATE__DOCUMENT);
		}
		return document;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Exam getExam() {
		return exam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExam(Exam newExam) {
		Exam oldExam = exam;
		exam = newExam;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CREATION_TEMPLATE__EXAM, oldExam, exam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TemplatesPackage.CREATION_TEMPLATE__DOCUMENT:
				return getDocument();
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				return getExam();
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
			case TemplatesPackage.CREATION_TEMPLATE__DOCUMENT:
				getDocument().clear();
				getDocument().addAll((Collection<? extends Byte>)newValue);
				return;
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				setExam((Exam)newValue);
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
			case TemplatesPackage.CREATION_TEMPLATE__DOCUMENT:
				getDocument().clear();
				return;
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				setExam(EXAM_EDEFAULT);
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
			case TemplatesPackage.CREATION_TEMPLATE__DOCUMENT:
				return document != null && !document.isEmpty();
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				return EXAM_EDEFAULT == null ? exam != null : !EXAM_EDEFAULT.equals(exam);
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
		result.append(" (document: ");
		result.append(document);
		result.append(", exam: ");
		result.append(exam);
		result.append(')');
		return result.toString();
	}

} //CreationTemplateImpl
