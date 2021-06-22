/**
 */
package fr.istic.tools.scanexam.core.templates.impl;

import fr.istic.tools.scanexam.core.StudentInformation;
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

import org.eclipse.emf.ecore.util.EDataTypeEList;
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
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getInformations <em>Informations</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getStudentsheets <em>Studentsheets</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getFailedPages <em>Failed Pages</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.templates.impl.CorrectionTemplateImpl#getEncodedDocument <em>Encoded Document</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CorrectionTemplateImpl extends MinimalEObjectImpl.Container implements CorrectionTemplate {
	/**
	 * The cached value of the '{@link #getInformations() <em>Informations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInformations()
	 * @generated
	 * @ordered
	 */
	protected EList<StudentInformation> informations;

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
	 * The cached value of the '{@link #getFailedPages() <em>Failed Pages</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFailedPages()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> failedPages;

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
	public EList<StudentInformation> getInformations() {
		if (informations == null) {
			informations = new EObjectContainmentEList<StudentInformation>(StudentInformation.class, this, TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS);
		}
		return informations;
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
	public EList<Integer> getFailedPages() {
		if (failedPages == null) {
			failedPages = new EDataTypeEList<Integer>(Integer.class, this, TemplatesPackage.CORRECTION_TEMPLATE__FAILED_PAGES);
		}
		return failedPages;
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
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS:
				return ((InternalEList<?>)getInformations()).basicRemove(otherEnd, msgs);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS:
				return getInformations();
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				return getStudentsheets();
			case TemplatesPackage.CORRECTION_TEMPLATE__FAILED_PAGES:
				return getFailedPages();
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				return getEncodedDocument();
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
			case TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS:
				getInformations().clear();
				getInformations().addAll((Collection<? extends StudentInformation>)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				getStudentsheets().clear();
				getStudentsheets().addAll((Collection<? extends StudentSheet>)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__FAILED_PAGES:
				getFailedPages().clear();
				getFailedPages().addAll((Collection<? extends Integer>)newValue);
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument((String)newValue);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS:
				getInformations().clear();
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				getStudentsheets().clear();
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__FAILED_PAGES:
				getFailedPages().clear();
				return;
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				setEncodedDocument(ENCODED_DOCUMENT_EDEFAULT);
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
			case TemplatesPackage.CORRECTION_TEMPLATE__INFORMATIONS:
				return informations != null && !informations.isEmpty();
			case TemplatesPackage.CORRECTION_TEMPLATE__STUDENTSHEETS:
				return studentsheets != null && !studentsheets.isEmpty();
			case TemplatesPackage.CORRECTION_TEMPLATE__FAILED_PAGES:
				return failedPages != null && !failedPages.isEmpty();
			case TemplatesPackage.CORRECTION_TEMPLATE__ENCODED_DOCUMENT:
				return ENCODED_DOCUMENT_EDEFAULT == null ? encodedDocument != null : !ENCODED_DOCUMENT_EDEFAULT.equals(encodedDocument);
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
		result.append(" (failedPages: ");
		result.append(failedPages);
		result.append(", encodedDocument: ");
		result.append(encodedDocument);
		result.append(')');
		return result.toString();
	}

} //CorrectionTemplateImpl
