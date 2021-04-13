/**
 */
package fr.istic.tools.scanexam.core;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Handwriting Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.istic.tools.scanexam.core.HandwritingComment#getPageId <em>Page Id</em>}</li>
 *   <li>{@link fr.istic.tools.scanexam.core.HandwritingComment#getLines <em>Lines</em>}</li>
 * </ul>
 *
 * @see fr.istic.tools.scanexam.core.CorePackage#getHandwritingComment()
 * @model
 * @generated
 */
public interface HandwritingComment extends EObject {
	/**
	 * Returns the value of the '<em><b>Page Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page Id</em>' attribute.
	 * @see #setPageId(int)
	 * @see fr.istic.tools.scanexam.core.CorePackage#getHandwritingComment_PageId()
	 * @model unique="false"
	 * @generated
	 */
	int getPageId();

	/**
	 * Sets the value of the '{@link fr.istic.tools.scanexam.core.HandwritingComment#getPageId <em>Page Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page Id</em>' attribute.
	 * @see #getPageId()
	 * @generated
	 */
	void setPageId(int value);

	/**
	 * Returns the value of the '<em><b>Lines</b></em>' containment reference list.
	 * The list contents are of type {@link fr.istic.tools.scanexam.core.Line}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lines</em>' containment reference list.
	 * @see fr.istic.tools.scanexam.core.CorePackage#getHandwritingComment_Lines()
	 * @model containment="true"
	 * @generated
	 */
	EList<Line> getLines();

} // HandwritingComment
