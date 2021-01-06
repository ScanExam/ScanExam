/**
 */
package fr.istic.tools.scanexam.impl;

import fr.istic.tools.scanexam.QuestionGrade;
import fr.istic.tools.scanexam.ScanexamPackage;
import fr.istic.tools.scanexam.StudentGrade;

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
 * An implementation of the model object '<em><b>Student Grade</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.impl.StudentGradeImpl#getStudentID <em>Student ID</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.StudentGradeImpl#getNumAnonymat <em>Num Anonymat</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.impl.StudentGradeImpl#getQuestionGrades <em>Question Grades</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StudentGradeImpl extends MinimalEObjectImpl.Container implements StudentGrade {
	/**
	 * The default value of the '{@link #getStudentID() <em>Student ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentID()
	 * @generated
	 * @ordered
	 */
	protected static final String STUDENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStudentID() <em>Student ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStudentID()
	 * @generated
	 * @ordered
	 */
	protected String studentID = STUDENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumAnonymat() <em>Num Anonymat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumAnonymat()
	 * @generated
	 * @ordered
	 */
	protected static final long NUM_ANONYMAT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getNumAnonymat() <em>Num Anonymat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumAnonymat()
	 * @generated
	 * @ordered
	 */
	protected long numAnonymat = NUM_ANONYMAT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getQuestionGrades() <em>Question Grades</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuestionGrades()
	 * @generated
	 * @ordered
	 */
	protected EList<QuestionGrade> questionGrades;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StudentGradeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScanexamPackage.Literals.STUDENT_GRADE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStudentID() {
		return studentID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStudentID(String newStudentID) {
		String oldStudentID = studentID;
		studentID = newStudentID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.STUDENT_GRADE__STUDENT_ID, oldStudentID, studentID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getNumAnonymat() {
		return numAnonymat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumAnonymat(long newNumAnonymat) {
		long oldNumAnonymat = numAnonymat;
		numAnonymat = newNumAnonymat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScanexamPackage.STUDENT_GRADE__NUM_ANONYMAT, oldNumAnonymat, numAnonymat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<QuestionGrade> getQuestionGrades() {
		if (questionGrades == null) {
			questionGrades = new EObjectContainmentEList<QuestionGrade>(QuestionGrade.class, this, ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES);
		}
		return questionGrades;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES:
				return ((InternalEList<?>)getQuestionGrades()).basicRemove(otherEnd, msgs);
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
			case ScanexamPackage.STUDENT_GRADE__STUDENT_ID:
				return getStudentID();
			case ScanexamPackage.STUDENT_GRADE__NUM_ANONYMAT:
				return getNumAnonymat();
			case ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES:
				return getQuestionGrades();
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
			case ScanexamPackage.STUDENT_GRADE__STUDENT_ID:
				setStudentID((String)newValue);
				return;
			case ScanexamPackage.STUDENT_GRADE__NUM_ANONYMAT:
				setNumAnonymat((Long)newValue);
				return;
			case ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES:
				getQuestionGrades().clear();
				getQuestionGrades().addAll((Collection<? extends QuestionGrade>)newValue);
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
			case ScanexamPackage.STUDENT_GRADE__STUDENT_ID:
				setStudentID(STUDENT_ID_EDEFAULT);
				return;
			case ScanexamPackage.STUDENT_GRADE__NUM_ANONYMAT:
				setNumAnonymat(NUM_ANONYMAT_EDEFAULT);
				return;
			case ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES:
				getQuestionGrades().clear();
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
			case ScanexamPackage.STUDENT_GRADE__STUDENT_ID:
				return STUDENT_ID_EDEFAULT == null ? studentID != null : !STUDENT_ID_EDEFAULT.equals(studentID);
			case ScanexamPackage.STUDENT_GRADE__NUM_ANONYMAT:
				return numAnonymat != NUM_ANONYMAT_EDEFAULT;
			case ScanexamPackage.STUDENT_GRADE__QUESTION_GRADES:
				return questionGrades != null && !questionGrades.isEmpty();
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
		result.append(" (studentID: ");
		result.append(studentID);
		result.append(", numAnonymat: ");
		result.append(numAnonymat);
		result.append(')');
		return result.toString();
	}

} //StudentGradeImpl
