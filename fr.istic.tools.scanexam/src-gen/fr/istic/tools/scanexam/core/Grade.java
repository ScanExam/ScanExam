/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Grade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.Grade#getEntries <em>Entries</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.Grade#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade()
 * @model
 * @generated
 */
public interface Grade extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.GradeEntry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade_Entries()
	 * @model
	 * @generated
	 */
	EList<GradeEntry> getEntries();

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Comment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getGrade_Comments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Comment> getComments();

} // Grade
