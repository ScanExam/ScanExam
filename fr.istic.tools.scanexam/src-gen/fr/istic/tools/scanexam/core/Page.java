/**
 */
package fr.istic.tools.scanexam.core;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Page#getId <em>Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Page#getQuestions <em>Questions</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getPage()
 * @model
 * @generated
 */
public interface Page extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getPage_Id()
	 * @model unique="false"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Page#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Questions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Questions</em>' attribute.
	 * @see #setQuestions(Map)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getPage_Questions()
	 * @model unique="false" dataType="fr.istic.tools.scanexam.core.Map&lt;org.eclipse.emf.ecore.EIntegerObject, fr.istic.tools.scanexam.core.Question&gt;"
	 * @generated
	 */
	Map<Integer, Question> getQuestions();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.Page#getQuestions <em>Questions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Questions</em>' attribute.
	 * @see #getQuestions()
	 * @generated
	 */
	void setQuestions(Map<Integer, Question> value);

} // Page
