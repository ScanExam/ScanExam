/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.Exam;

import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
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
 * An implementation of the model object '<em><b>Correction Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getEncodedDocument <em>Encoded Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getExam <em>Exam</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getStudentsheets <em>Studentsheets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CorrectionTemplateImpl extends MinimalEObjectImpl.Container implements CorrectionTemplate {
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
	 * The cached value of the '{@link #getStudentsheets() <em>Studentsheets</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentsheets()
	 * @generated
	 * @ordered
	 */
	protected EList<Exam> studentsheets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorrectionTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TemplatesPackage.Literals.CORRECTION_TEMPLATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT, oldEncodedDocument, encodedDocument));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CORRECTION_TEMPLATE__EXAM, oldExam, exam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Exam> getStudentsheets() {
		if (studentsheets == null) {
			studentsheets = new EDataTypeEList<Exam>(Exam.class, this, TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS);
		}
		return studentsheets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				return getEncodedDocument();
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
				return getExam();
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				return getStudentsheets();
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
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument((String)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
				setExam((Exam)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				getStudentsheets().clear();
				getStudentsheets().addAll((Collection<? extends Exam>)newValue);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument(ENCODED_DOCUMENT_EDEFAULT);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
				setExam(EXAM_EDEFAULT);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				getStudentsheets().clear();
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
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				return ENCODED_DOCUMENT_EDEFAULT == null ? encodedDocument != null : !ENCODED_DOCUMENT_EDEFAULT.equals(encodedDocument);
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
				return EXAM_EDEFAULT == null ? exam != null : !EXAM_EDEFAULT.equals(exam);
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				return studentsheets != null && !studentsheets.isEmpty();
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
		result.append(", exam: ");
		result.append(exam);
		result.append(", studentsheets: ");
		result.append(studentsheets);
		result.append(')');
		return result.toString();
	}

} //CorrectionTemplateImpl
