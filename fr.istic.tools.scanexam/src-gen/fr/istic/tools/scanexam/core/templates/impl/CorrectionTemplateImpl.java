/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.StudentSheet;

import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;

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
 * An implementation of the model object '<em><b>Correction Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getEncodedDocument <em>Encoded Document</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getStudentListPath <em>Student List Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getStudentListShift <em>Student List Shift</em>}</li>
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
	 * The default value of the '{@link #getStudentListPath() <em>Student List Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentListPath()
	 * @generated
	 * @ordered
	 */
	protected static final String STUDENT_LIST_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStudentListPath() <em>Student List Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentListPath()
	 * @generated
	 * @ordered
	 */
	protected String studentListPath = STUDENT_LIST_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getStudentListShift() <em>Student List Shift</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentListShift()
	 * @generated
	 * @ordered
	 */
	protected static final String STUDENT_LIST_SHIFT_EDEFAULT = "A1";

	/**
	 * The cached value of the '{@link #getStudentListShift() <em>Student List Shift</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentListShift()
	 * @generated
	 * @ordered
	 */
	protected String studentListShift = STUDENT_LIST_SHIFT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStudentsheets() <em>Studentsheets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentsheets()
	 * @generated
	 * @ordered
	 */
	protected EList<StudentSheet> studentsheets;

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
	public String getStudentListPath() {
		return studentListPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStudentListPath(String newStudentListPath) {
		String oldStudentListPath = studentListPath;
		studentListPath = newStudentListPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_PATH, oldStudentListPath, studentListPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStudentListShift() {
		return studentListShift;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStudentListShift(String newStudentListShift) {
		String oldStudentListShift = studentListShift;
		studentListShift = newStudentListShift;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_SHIFT, oldStudentListShift, studentListShift));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StudentSheet> getStudentsheets() {
		if (studentsheets == null) {
			studentsheets = new EObjectContainmentEList<StudentSheet>(StudentSheet.class, this, TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS);
		}
		return studentsheets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				return ((InternalEList<?>)getStudentsheets()).basicRemove(otherEnd, msgs);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				return getEncodedDocument();
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_PATH:
				return getStudentListPath();
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_SHIFT:
				return getStudentListShift();
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
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_PATH:
				setStudentListPath((String)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_SHIFT:
				setStudentListShift((String)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				getStudentsheets().clear();
				getStudentsheets().addAll((Collection<? extends StudentSheet>)newValue);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_PATH:
				setStudentListPath(STUDENT_LIST_PATH_EDEFAULT);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_SHIFT:
				setStudentListShift(STUDENT_LIST_SHIFT_EDEFAULT);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_PATH:
				return STUDENT_LIST_PATH_EDEFAULT == null ? studentListPath != null : !STUDENT_LIST_PATH_EDEFAULT.equals(studentListPath);
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENT_LIST_SHIFT:
				return STUDENT_LIST_SHIFT_EDEFAULT == null ? studentListShift != null : !STUDENT_LIST_SHIFT_EDEFAULT.equals(studentListShift);
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
		result.append(", studentListPath: ");
		result.append(studentListPath);
		result.append(", studentListShift: ");
		result.append(studentListShift);
		result.append(')');
		return result.toString();
	}

} //CorrectionTemplateImpl
