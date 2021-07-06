/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Optional;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Grade</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.GradeImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.impl.GradeImpl#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GradeImpl extends MinimalEObjectImpl.Container implements Grade {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<GradeEntry> entries;

	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Comment> comments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GradeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.GRADE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GradeEntry> getEntries() {
		if (entries == null) {
			entries = new EObjectResolvingEList<GradeEntry>(GradeEntry.class, this, CorePackage.GRADE__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<Comment>(Comment.class, this, CorePackage.GRADE__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getGradeValue() {
		Float _xblockexpression = null;
		{
			final Function<GradeEntry, Float> _function = new Function<GradeEntry, Float>() {
				public Float apply(final GradeEntry grade) {
					return Float.valueOf(grade.getStep());
				}
			};
			final BinaryOperator<Float> _function_1 = new BinaryOperator<Float>() {
				public Float apply(final Float acc, final Float v) {
					return Float.valueOf(((v).floatValue() + (acc).floatValue()));
				}
			};
			final Optional<Float> res = this.getEntries().stream().<Float>map(_function).reduce(_function_1);
			Float _xifexpression = null;
			boolean _isPresent = res.isPresent();
			if (_isPresent) {
				_xifexpression = res.get();
			}
			else {
				_xifexpression = Float.valueOf(0f);
			}
			_xblockexpression = _xifexpression;
		}
		return (_xblockexpression).floatValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.GRADE__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
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
			case CorePackage.GRADE__ENTRIES:
				return getEntries();
			case CorePackage.GRADE__COMMENTS:
				return getComments();
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
			case CorePackage.GRADE__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends GradeEntry>)newValue);
				return;
			case CorePackage.GRADE__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
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
			case CorePackage.GRADE__ENTRIES:
				getEntries().clear();
				return;
			case CorePackage.GRADE__COMMENTS:
				getComments().clear();
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
			case CorePackage.GRADE__ENTRIES:
				return entries != null && !entries.isEmpty();
			case CorePackage.GRADE__COMMENTS:
				return comments != null && !comments.isEmpty();
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
			case CorePackage.GRADE___GET_GRADE_VALUE:
				return getGradeValue();
		}
		return super.eInvoke(operationID, arguments);
	}

} //GradeImpl
