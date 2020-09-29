/**
 */
package fr.istic.tools.scanexam.impl;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.GradingData;
import fr.istic.tools.scanexam.ScanexamPackage;
import fr.istic.tools.scanexam.StudentGrade;

import java.io.File;

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
 * An implementation of the model object '<em><b>Grading Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.impl.GradingDataImpl#getSolutionPath <em>Solution Path</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.GradingDataImpl#getExcelFileName <em>Excel File Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.GradingDataImpl#getImages <em>Images</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.GradingDataImpl#getExam <em>Exam</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.GradingDataImpl#getGrades <em>Grades</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GradingDataImpl extends MinimalEObjectImpl.Container implements GradingData {
	/**
	 * The default value of the '{@link #getSolutionPath() <em>Solution Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolutionPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SOLUTION_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSolutionPath() <em>Solution Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolutionPath()
	 * @generated
	 * @ordered
	 */
	protected String solutionPath = SOLUTION_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getExcelFileName() <em>Excel File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcelFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String EXCEL_FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExcelFileName() <em>Excel File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcelFileName()
	 * @generated
	 * @ordered
	 */
	protected String excelFileName = EXCEL_FILE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getImages() <em>Images</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImages()
	 * @generated
	 * @ordered
	 */
	protected EList<File> images;

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
	 * The cached value of the '{@link #getGrades() <em>Grades</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGrades()
	 * @generated
	 * @ordered
	 */
	protected EList<StudentGrade> grades;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GradingDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScanexamPackage.Literals.GRADING_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSolutionPath() {
		return solutionPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSolutionPath(String newSolutionPath) {
		String oldSolutionPath = solutionPath;
		solutionPath = newSolutionPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.GRADING_DATA__SOLUTION_PATH, oldSolutionPath, solutionPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExcelFileName() {
		return excelFileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExcelFileName(String newExcelFileName) {
		String oldExcelFileName = excelFileName;
		excelFileName = newExcelFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.GRADING_DATA__EXCEL_FILE_NAME, oldExcelFileName, excelFileName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<File> getImages() {
		if (images == null) {
			images = new EDataTypeEList<File>(File.class, this, ScanexamPackage.GRADING_DATA__IMAGES);
		}
		return images;
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
	public NotificationChain basicSetExam(Exam newExam, NotificationChain msgs) {
		Exam oldExam = exam;
		exam = newExam;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScanexamPackage.GRADING_DATA__EXAM, oldExam, newExam);
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
	public void setExam(Exam newExam) {
		if (newExam != exam) {
			NotificationChain msgs = null;
			if (exam != null)
				msgs = ((InternalEObject)exam).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.GRADING_DATA__EXAM, null, msgs);
			if (newExam != null)
				msgs = ((InternalEObject)newExam).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScanexamPackage.GRADING_DATA__EXAM, null, msgs);
			msgs = basicSetExam(newExam, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.GRADING_DATA__EXAM, newExam, newExam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StudentGrade> getGrades() {
		if (grades == null) {
			grades = new EObjectContainmentEList<StudentGrade>(StudentGrade.class, this, ScanexamPackage.GRADING_DATA__GRADES);
		}
		return grades;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScanexamPackage.GRADING_DATA__EXAM:
				return basicSetExam(null, msgs);
			case ScanexamPackage.GRADING_DATA__GRADES:
				return ((InternalEList<?>)getGrades()).basicRemove(otherEnd, msgs);
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
			case ScanexamPackage.GRADING_DATA__SOLUTION_PATH:
				return getSolutionPath();
			case ScanexamPackage.GRADING_DATA__EXCEL_FILE_NAME:
				return getExcelFileName();
			case ScanexamPackage.GRADING_DATA__IMAGES:
				return getImages();
			case ScanexamPackage.GRADING_DATA__EXAM:
				return getExam();
			case ScanexamPackage.GRADING_DATA__GRADES:
				return getGrades();
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
			case ScanexamPackage.GRADING_DATA__SOLUTION_PATH:
				setSolutionPath((String)newValue);
				return;
			case ScanexamPackage.GRADING_DATA__EXCEL_FILE_NAME:
				setExcelFileName((String)newValue);
				return;
			case ScanexamPackage.GRADING_DATA__IMAGES:
				getImages().clear();
				getImages().addAll((Collection<? extends File>)newValue);
				return;
			case ScanexamPackage.GRADING_DATA__EXAM:
				setExam((Exam)newValue);
				return;
			case ScanexamPackage.GRADING_DATA__GRADES:
				getGrades().clear();
				getGrades().addAll((Collection<? extends StudentGrade>)newValue);
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
			case ScanexamPackage.GRADING_DATA__SOLUTION_PATH:
				setSolutionPath(SOLUTION_PATH_EDEFAULT);
				return;
			case ScanexamPackage.GRADING_DATA__EXCEL_FILE_NAME:
				setExcelFileName(EXCEL_FILE_NAME_EDEFAULT);
				return;
			case ScanexamPackage.GRADING_DATA__IMAGES:
				getImages().clear();
				return;
			case ScanexamPackage.GRADING_DATA__EXAM:
				setExam((Exam)null);
				return;
			case ScanexamPackage.GRADING_DATA__GRADES:
				getGrades().clear();
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
			case ScanexamPackage.GRADING_DATA__SOLUTION_PATH:
				return SOLUTION_PATH_EDEFAULT == null ? solutionPath != null : !SOLUTION_PATH_EDEFAULT.equals(solutionPath);
			case ScanexamPackage.GRADING_DATA__EXCEL_FILE_NAME:
				return EXCEL_FILE_NAME_EDEFAULT == null ? excelFileName != null : !EXCEL_FILE_NAME_EDEFAULT.equals(excelFileName);
			case ScanexamPackage.GRADING_DATA__IMAGES:
				return images != null && !images.isEmpty();
			case ScanexamPackage.GRADING_DATA__EXAM:
				return exam != null;
			case ScanexamPackage.GRADING_DATA__GRADES:
				return grades != null && !grades.isEmpty();
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
		result.append(" (solutionPath: ");
		result.append(solutionPath);
		result.append(", excelFileName: ");
		result.append(excelFileName);
		result.append(", images: ");
		result.append(images);
		result.append(')');
		return result.toString();
	}

} //GradingDataImpl
