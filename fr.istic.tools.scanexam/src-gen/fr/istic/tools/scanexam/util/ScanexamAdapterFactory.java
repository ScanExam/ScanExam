/**
 */
package fr.istic.tools.scanexam.util;

import fr.istic.tools.scanexam.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.istic.tools.scanexam.ScanexamPackage
 * @generated
 */
public class ScanexamAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ScanexamPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScanexamAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ScanexamPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScanexamSwitch<Adapter> modelSwitch =
		new ScanexamSwitch<Adapter>() {
			@Override
			public Adapter caseExam(Exam object) {
				return createExamAdapter();
			}
			@Override
			public Adapter caseInfoField(InfoField object) {
				return createInfoFieldAdapter();
			}
			@Override
			public Adapter caseQuestion(Question object) {
				return createQuestionAdapter();
			}
			@Override
			public Adapter caseScanZone(ScanZone object) {
				return createScanZoneAdapter();
			}
			@Override
			public Adapter caseGradingData(GradingData object) {
				return createGradingDataAdapter();
			}
			@Override
			public Adapter caseStudentGrade(StudentGrade object) {
				return createStudentGradeAdapter();
			}
			@Override
			public Adapter caseQuestionGrade(QuestionGrade object) {
				return createQuestionGradeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.Exam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.Exam
	 * @generated
	 */
	public Adapter createExamAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.InfoField <em>Info Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.InfoField
	 * @generated
	 */
	public Adapter createInfoFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.Question <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.Question
	 * @generated
	 */
	public Adapter createQuestionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.ScanZone <em>Scan Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.ScanZone
	 * @generated
	 */
	public Adapter createScanZoneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.GradingData <em>Grading Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.GradingData
	 * @generated
	 */
	public Adapter createGradingDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.StudentGrade <em>Student Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.StudentGrade
	 * @generated
	 */
	public Adapter createStudentGradeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.istic.tools.scanexam.QuestionGrade <em>Question Grade</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.istic.tools.scanexam.QuestionGrade
	 * @generated
	 */
	public Adapter createQuestionGradeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ScanexamAdapterFactory
