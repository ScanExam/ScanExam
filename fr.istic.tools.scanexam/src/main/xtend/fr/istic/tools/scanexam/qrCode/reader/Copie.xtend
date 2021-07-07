package fr.istic.tools.scanexam.qrCode.reader

import java.util.Set
import org.eclipse.xtend.lib.annotations.Data
import java.util.HashSet

@Data class Copie {

	Set<Page> setPages
	int numCopie
	String studentName

	new(int numCopie, int numPageInPDF, int numPageInSubject) {
		this.numCopie = numCopie
		this.setPages = new HashSet<Page>()
		this.setPages.add(new Page(numPageInSubject, numPageInPDF))
		this.studentName = null
	}

	new(int numCopie, int numPageInPDF, int numPageInSubject, String studentName) {
		this.numCopie = numCopie
		this.setPages = new HashSet<Page>()
		this.setPages.add(new Page(numPageInSubject, numPageInPDF))
		this.studentName = studentName
	}

	new(int numCopie, Set<Page> setPages, String studentName) {
		this.numCopie = numCopie
		this.setPages = setPages
		this.studentName = studentName
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

	def String getStudentName() {
		return studentName
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
		if (studentName !== null) {
			res += " " + studentName
		}
		res += "[\n"
		for (i : 0 ..< setPages.length)
			res += setPages.get(i).toString() + "\n"
		return res + "]"
	}
// TODO a voir comment définir la finalité de tri, où on envoie? comment?
// définir les copies auxquelles il manque des pages
// ---> donner le nombre de copies manquantes??
}
