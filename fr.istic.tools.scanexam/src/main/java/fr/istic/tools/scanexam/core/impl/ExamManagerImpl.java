/**
 */
package fr.istic.tools.scanexam.core.impl;

import fr.istic.tools.scanexam.core.CorePackage;
import fr.istic.tools.scanexam.core.ExamManager;
import fr.istic.tools.scanexam.core.Question;

import fr.istic.tools.scanexam.utils.Rectangle;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exam Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ExamManagerImpl extends ExamMarkerImpl implements ExamManager {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExamManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.EXAM_MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addQuestion(Question question, Rectangle r) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CorePackage.EXAM_MANAGER___ADD_QUESTION__QUESTION_RECTANGLE:
				addQuestion((Question)arguments.get(0), (Rectangle)arguments.get(1));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExamManagerImpl
