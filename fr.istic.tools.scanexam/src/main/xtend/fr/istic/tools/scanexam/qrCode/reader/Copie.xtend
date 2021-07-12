package fr.istic.tools.scanexam.qrCode.reader

import java.util.Set
import org.eclipse.xtend.lib.annotations.Data
import java.util.HashSet

@Data class Copie {

	Set<Page> setPages
	int numCopie
	String studentId
	String studentLastName
	String studentFirstName

	new(int numCopie, int numPageInPDF, int numPageInSubject) {
		this.numCopie = numCopie
		this.setPages = new HashSet<Page>()
		this.setPages.add(new Page(numPageInSubject, numPageInPDF))
		this.studentId = null
		this.studentLastName = null
		this.studentFirstName = null
	}

	new(int numCopie, int numPageInPDF, int numPageInSubject, String studentId, String studentLastName,
		String studentFirstName) {
		this.numCopie = numCopie
		this.setPages = new HashSet<Page>()
		this.setPages.add(new Page(numPageInSubject, numPageInPDF))
		this.studentId = studentId
		this.studentLastName = studentLastName
		this.studentFirstName = studentFirstName
	}

	new(int numCopie, Set<Page> setPages, String studentId, String studentLastName, String studentFirstName) {
		this.numCopie = numCopie
		this.setPages = setPages
		this.studentId = studentId
		this.studentLastName = studentLastName
		this.studentFirstName = studentFirstName
	}

	def addInSet(Set<Page> pages) {
		for (i : 0 ..< pages.length)
			setPages.add(pages.get(i))
	}

	def int getNumCopie() {
		return this.numCopie
	}

	def Set<Page> getPagesCopie() {
		return setPages
	}

	def String getStudentId() {
		return studentId
	}

	def String getStudentLastName() {
		return studentLastName
	}

	def String getStudentFirstName() {
		return studentFirstName
	}

	def boolean isCopyComplete(int nbPageSubject) {
		return nbPageSubject == setPages.length
	}

	def int getNumPageInPDF(int numPage) {
		for (i : 0 ..< setPages.length)
			if (setPages.get(i).numPageInSubject == numPage)
				return setPages.get(i).numPageInPDF
	}

	override String toString() {
		var String res = "Copie " + numCopie
		if (studentId !== null && studentLastName !== null && studentFirstName !== null) {
			res += " " + studentId + " (" + studentLastName + " " + studentFirstName + ")"
		}
		res += " [\n"
		for (i : 0 ..< setPages.length)
			res += setPages.get(i).toString() + "\n"
		return res + "]"
	}
// TODO a voir comment définir la finalité de tri, où on envoie? comment?
// définir les copies auxquelles il manque des pages
// ---> donner le nombre de copies manquantes??
}
