/**
 */
package fr.istic.tools.scanexam.core.impl;

import com.google.common.base.Objects;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.StudentSheet;

import java.lang.reflect.InvocationTargetException;

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

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Student Sheet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getStudentID <em>Student ID</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getFirstName <em>First Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getPosPage <em>Pos Page</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.StudentSheetImpl#getGrades <em>Grades</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StudentSheetImpl extends MinimalEObjectImpl.Container implements StudentSheet {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

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
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPosPage() <em>Pos Page</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosPage()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> posPage;

	/**
	 * The cached value of the '{@link #getGrades() <em>Grades</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGrades()
	 * @generated
	 * @ordered
	 */
	protected EList<Grade> grades;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StudentSheetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.STUDENT_SHEET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_SHEET__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStudentID(String newStudentID) {
		String oldStudentID = studentID;
		studentID = newStudentID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_SHEET__STUDENT_ID, oldStudentID, studentID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstName(String newFirstName) {
		String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_SHEET__FIRST_NAME, oldFirstName, firstName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastName(String newLastName) {
		String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.STUDENT_SHEET__LAST_NAME, oldLastName, lastName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getPosPage() {
		if (posPage == null) {
			posPage = new EDataTypeEList<Integer>(Integer.class, this, CorePackage.STUDENT_SHEET__POS_PAGE);
		}
		return posPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Grade> getGrades() {
		if (grades == null) {
			grades = new EObjectContainmentEList<Grade>(Grade.class, this, CorePackage.STUDENT_SHEET__GRADES);
		}
		return grades;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float computeGrade() {
		float result = 0f;
		for (int i = 0; (i < this.getGrades().size()); i++) {
			float _result = result;
			float _gradeValue = this.getGrades().get(i).getGradeValue();
			result = (_result + _gradeValue);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGraded() {
		final Function1<Grade, Boolean> _function = new Function1<Grade, Boolean>() {
			public Boolean apply(final Grade g) {
				return Boolean.valueOf(g.getEntries().isEmpty());
			}
		};
		Grade _findFirst = IterableExtensions.<Grade>findFirst(this.getGrades(), _function);
		return (_findFirst == null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStudentInfo() {
		String resLastName = this.getLastName();
		String resFirstName = this.getFirstName();
		String resID = this.getStudentID();
		if ((Objects.equal(resLastName, "?") || Objects.equal(resFirstName, "?"))) {
			resLastName = "";
			resFirstName = "";
		}
		boolean _equals = Objects.equal(resID, "?");
		if (_equals) {
			resID = "";
		}
		return ((((resID + "_") + resFirstName) + "_") + resLastName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.STUDENT_SHEET__GRADES:
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
			case CorePackage.STUDENT_SHEET__ID:
				return getId();
			case CorePackage.STUDENT_SHEET__STUDENT_ID:
				return getStudentID();
			case CorePackage.STUDENT_SHEET__FIRST_NAME:
				return getFirstName();
			case CorePackage.STUDENT_SHEET__LAST_NAME:
				return getLastName();
			case CorePackage.STUDENT_SHEET__POS_PAGE:
				return getPosPage();
			case CorePackage.STUDENT_SHEET__GRADES:
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
			case CorePackage.STUDENT_SHEET__ID:
				setId((Integer)newValue);
				return;
			case CorePackage.STUDENT_SHEET__STUDENT_ID:
				setStudentID((String)newValue);
				return;
			case CorePackage.STUDENT_SHEET__FIRST_NAME:
				setFirstName((String)newValue);
				return;
			case CorePackage.STUDENT_SHEET__LAST_NAME:
				setLastName((String)newValue);
				return;
			case CorePackage.STUDENT_SHEET__POS_PAGE:
				getPosPage().clear();
				getPosPage().addAll((Collection<? extends Integer>)newValue);
				return;
			case CorePackage.STUDENT_SHEET__GRADES:
				getGrades().clear();
				getGrades().addAll((Collection<? extends Grade>)newValue);
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
			case CorePackage.STUDENT_SHEET__ID:
				setId(ID_EDEFAULT);
				return;
			case CorePackage.STUDENT_SHEET__STUDENT_ID:
				setStudentID(STUDENT_ID_EDEFAULT);
				return;
			case CorePackage.STUDENT_SHEET__FIRST_NAME:
				setFirstName(FIRST_NAME_EDEFAULT);
				return;
			case CorePackage.STUDENT_SHEET__LAST_NAME:
				setLastName(LAST_NAME_EDEFAULT);
				return;
			case CorePackage.STUDENT_SHEET__POS_PAGE:
				getPosPage().clear();
				return;
			case CorePackage.STUDENT_SHEET__GRADES:
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
			case CorePackage.STUDENT_SHEET__ID:
				return id != ID_EDEFAULT;
			case CorePackage.STUDENT_SHEET__STUDENT_ID:
				return STUDENT_ID_EDEFAULT == null ? studentID != null : !STUDENT_ID_EDEFAULT.equals(studentID);
			case CorePackage.STUDENT_SHEET__FIRST_NAME:
				return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
			case CorePackage.STUDENT_SHEET__LAST_NAME:
				return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
			case CorePackage.STUDENT_SHEET__POS_PAGE:
				return posPage != null && !posPage.isEmpty();
			case CorePackage.STUDENT_SHEET__GRADES:
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CorePackage.STUDENT_SHEET___COMPUTE_GRADE:
				return computeGrade();
			case CorePackage.STUDENT_SHEET___IS_GRADED:
				return isGraded();
			case CorePackage.STUDENT_SHEET___GET_STUDENT_INFO:
				return getStudentInfo();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", studentID: ");
		result.append(studentID);
		result.append(", firstName: ");
		result.append(firstName);
		result.append(", lastName: ");
		result.append(lastName);
		result.append(", posPage: ");
		result.append(posPage);
		result.append(')');
		return result.toString();
	}

} //StudentSheetImpl
