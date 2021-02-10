/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.Exam;

import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correction Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getPdfPath <em>Pdf Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getExam <em>Exam</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CorrectionTemplateImpl extends MinimalEObjectImpl.Container implements CorrectionTemplate {
	/**
	 * The default value of the '{@link #getPdfPath() <em>Pdf Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPdfPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PDF_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPdfPath() <em>Pdf Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPdfPath()
	 * @generated
	 * @ordered
	 */
	protected String pdfPath = PDF_PATH_EDEFAULT;

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
	@Override
	public String getPdfPath() {
		return pdfPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPdfPath(String newPdfPath) {
		String oldPdfPath = pdfPath;
		pdfPath = newPdfPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatesPackage.CORRECTION_TEMPLATE__PDF_PATH, oldPdfPath, pdfPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Exam getExam() {
		return exam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TemplatesPackage.CORRECTION_TEMPLATE__PDF_PATH:
				return getPdfPath();
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
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
			case TemplatesPackage.CORRECTION_TEMPLATE__PDF_PATH:
				setPdfPath((String)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
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
			case TemplatesPackage.CORRECTION_TEMPLATE__PDF_PATH:
				setPdfPath(PDF_PATH_EDEFAULT);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
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
			case TemplatesPackage.CORRECTION_TEMPLATE__PDF_PATH:
				return PDF_PATH_EDEFAULT == null ? pdfPath != null : !PDF_PATH_EDEFAULT.equals(pdfPath);
			case TemplatesPackage.CORRECTION_TEMPLATE__EXAM:
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
		result.append(" (pdfPath: ");
		result.append(pdfPath);
		result.append(", exam: ");
		result.append(exam);
		result.append(')');
		return result.toString();
	}

} //CorrectionTemplateImpl
