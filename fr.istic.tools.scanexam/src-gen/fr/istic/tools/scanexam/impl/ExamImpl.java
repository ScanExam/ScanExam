/**
 */
package fr.istic.tools.scanexam.impl;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.Question;
import fr.istic.tools.scanexam.ScanexamPackage;

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
 * An implementation of the model object '<em><b>Exam</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.impl.ExamImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ExamImpl#getFolderPath <em>Folder Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ExamImpl#getNumberOfPages <em>Number Of Pages</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.ExamImpl#getQuestions <em>Questions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExamImpl extends MinimalEObjectImpl.Container implements Exam {
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
	 * The default value of the '{@link #getFolderPath() <em>Folder Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolderPath()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolderPath() <em>Folder Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolderPath()
	 * @generated
	 * @ordered
	 */
	protected String folderPath = FOLDER_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfPages() <em>Number Of Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfPages()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_PAGES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfPages() <em>Number Of Pages</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfPages()
	 * @generated
	 * @ordered
	 */
	protected int numberOfPages = NUMBER_OF_PAGES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getQuestions() <em>Questions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuestions()
	 * @generated
	 * @ordered
	 */
	protected EList<Question> questions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExamImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScanexamPackage.Literals.EXAM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.EXAM__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFolderPath() {
		return folderPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFolderPath(String newFolderPath) {
		String oldFolderPath = folderPath;
		folderPath = newFolderPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.EXAM__FOLDER_PATH, oldFolderPath, folderPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfPages(int newNumberOfPages) {
		int oldNumberOfPages = numberOfPages;
		numberOfPages = newNumberOfPages;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.EXAM__NUMBER_OF_PAGES, oldNumberOfPages, numberOfPages));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Question> getQuestions() {
		if (questions == null) {
			questions = new EObjectContainmentEList<Question>(Question.class, this, ScanexamPackage.EXAM__QUESTIONS);
		}
		return questions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScanexamPackage.EXAM__QUESTIONS:
				return ((InternalEList<?>)getQuestions()).basicRemove(otherEnd, msgs);
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
			case ScanexamPackage.EXAM__LABEL:
				return getLabel();
			case ScanexamPackage.EXAM__FOLDER_PATH:
				return getFolderPath();
			case ScanexamPackage.EXAM__NUMBER_OF_PAGES:
				return getNumberOfPages();
			case ScanexamPackage.EXAM__QUESTIONS:
				return getQuestions();
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
			case ScanexamPackage.EXAM__LABEL:
				setLabel((String)newValue);
				return;
			case ScanexamPackage.EXAM__FOLDER_PATH:
				setFolderPath((String)newValue);
				return;
			case ScanexamPackage.EXAM__NUMBER_OF_PAGES:
				setNumberOfPages((Integer)newValue);
				return;
			case ScanexamPackage.EXAM__QUESTIONS:
				getQuestions().clear();
				getQuestions().addAll((Collection<? extends Question>)newValue);
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
			case ScanexamPackage.EXAM__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ScanexamPackage.EXAM__FOLDER_PATH:
				setFolderPath(FOLDER_PATH_EDEFAULT);
				return;
			case ScanexamPackage.EXAM__NUMBER_OF_PAGES:
				setNumberOfPages(NUMBER_OF_PAGES_EDEFAULT);
				return;
			case ScanexamPackage.EXAM__QUESTIONS:
				getQuestions().clear();
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
			case ScanexamPackage.EXAM__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ScanexamPackage.EXAM__FOLDER_PATH:
				return FOLDER_PATH_EDEFAULT == null ? folderPath != null : !FOLDER_PATH_EDEFAULT.equals(folderPath);
			case ScanexamPackage.EXAM__NUMBER_OF_PAGES:
				return numberOfPages != NUMBER_OF_PAGES_EDEFAULT;
			case ScanexamPackage.EXAM__QUESTIONS:
				return questions != null && !questions.isEmpty();
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
		result.append(", folderPath: ");
		result.append(folderPath);
		result.append(", numberOfPages: ");
		result.append(numberOfPages);
		result.append(')');
		return result.toString();
	}

} //ExamImpl
