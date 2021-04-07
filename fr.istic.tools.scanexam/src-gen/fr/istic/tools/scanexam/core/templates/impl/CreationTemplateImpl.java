/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.Exam;

import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Creation Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl#getEncodedDocument <em>Encoded Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CreationTemplateImpl#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreationTemplateImpl extends MinimalEObjectImpl.Container implements CreationTemplate {
	/**
	 * The default value of the '{@link #getEncodedDocument() <em>Encoded Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodedDocument()
	 * @generated
	 * @ordered
	 */
	protected static final String ENCODED_DOCUMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEncodedDocument() <em>Encoded Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodedDocument()
	 * @generated
	 * @ordered
	 */
	protected String encodedDocument = ENCODED_DOCUMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExam() <em>Exam</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExam()
	 * @generated
	 * @ordered
	 */
	protected Exam exam;

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
	public String getEncodedDocument() {
		return encodedDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncodedDocument(String newEncodedDocument) {
		String oldEncodedDocument = encodedDocument;
		encodedDocument = newEncodedDocument;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CREATION_TEMPLATE__ENCODED_DOCUMENT, oldEncodedDocument, encodedDocument));
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
	public NotificationChain basicSetExam(Exam newExam, NotificationChain msgs) {
		Exam oldExam = exam;
		exam = newExam;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TemplatesPackage.CREATION_TEMPLATE__EXAM, oldExam, newExam);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExam(Exam newExam) {
		if (newExam != exam) {
			NotificationChain msgs = null;
			if (exam != null)
				msgs = ((InternalEObject)exam).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TemplatesPackage.CREATION_TEMPLATE__EXAM, null, msgs);
			if (newExam != null)
				msgs = ((InternalEObject)newExam).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TemplatesPackage.CREATION_TEMPLATE__EXAM, null, msgs);
			msgs = basicSetExam(newExam, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CREATION_TEMPLATE__EXAM, newExam, newExam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				return basicSetExam(null, msgs);
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
			case TemplatesPackage.CREATION_TEMPLATE__ENCODED_DOCUMENT:
				return getEncodedDocument();
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TemplatesPackage.CREATION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument((String)newValue);
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
			case TemplatesPackage.CREATION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument(ENCODED_DOCUMENT_EDEFAULT);
				return;
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				setExam((Exam)null);
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
			case TemplatesPackage.CREATION_TEMPLATE__ENCODED_DOCUMENT:
				return ENCODED_DOCUMENT_EDEFAULT == null ? encodedDocument != null : !ENCODED_DOCUMENT_EDEFAULT.equals(encodedDocument);
			case TemplatesPackage.CREATION_TEMPLATE__EXAM:
				return exam != null;
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
		result.append(" (encodedDocument: ");
		result.append(encodedDocument);
		result.append(')');
		return result.toString();
	}

} //CreationTemplateImpl
