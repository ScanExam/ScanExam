/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.HandwritingComment;
import fr.istic.tools.scanexam.core.Line;

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
 * An implementation of the model object '<em><b>Handwriting Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl#getPageId <em>Page Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.HandwritingCommentImpl#getLines <em>Lines</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HandwritingCommentImpl extends MinimalEObjectImpl.Container implements HandwritingComment {
	/**
	 * The default value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageId()
	 * @generated
	 * @ordered
	 */
	protected static final int PAGE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPageId() <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPageId()
	 * @generated
	 * @ordered
	 */
	protected int pageId = PAGE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLines() <em>Lines</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected EList<Line> lines;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HandwritingCommentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.HANDWRITING_COMMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPageId() {
		return pageId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPageId(int newPageId) {
		int oldPageId = pageId;
		pageId = newPageId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.HANDWRITING_COMMENT__PAGE_ID, oldPageId, pageId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Line> getLines() {
		if (lines == null) {
			lines = new EObjectContainmentEList<Line>(Line.class, this, CorePackage.HANDWRITING_COMMENT__LINES);
		}
		return lines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.HANDWRITING_COMMENT__LINES:
				return ((InternalEList<?>)getLines()).basicRemove(otherEnd, msgs);
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
			case CorePackage.HANDWRITING_COMMENT__PAGE_ID:
				return getPageId();
			case CorePackage.HANDWRITING_COMMENT__LINES:
				return getLines();
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
			case CorePackage.HANDWRITING_COMMENT__PAGE_ID:
				setPageId((Integer)newValue);
				return;
			case CorePackage.HANDWRITING_COMMENT__LINES:
				getLines().clear();
				getLines().addAll((Collection<? extends Line>)newValue);
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
			case CorePackage.HANDWRITING_COMMENT__PAGE_ID:
				setPageId(PAGE_ID_EDEFAULT);
				return;
			case CorePackage.HANDWRITING_COMMENT__LINES:
				getLines().clear();
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
			case CorePackage.HANDWRITING_COMMENT__PAGE_ID:
				return pageId != PAGE_ID_EDEFAULT;
			case CorePackage.HANDWRITING_COMMENT__LINES:
				return lines != null && !lines.isEmpty();
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
		result.append(" (pageId: ");
		result.append(pageId);
		result.append(')');
		return result.toString();
	}

} //HandwritingCommentImpl
