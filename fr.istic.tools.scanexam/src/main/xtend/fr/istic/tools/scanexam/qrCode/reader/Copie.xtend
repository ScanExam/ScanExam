package fr.istic.tools.scanexam.qrCode.reader

import java.util.Set
import java.util.HashSet

class Copie {

	Set<Page> setPages
	int numCopie

	new(int numCopie, int numPageInPDF, int numPageInSubject) {

		this.numCopie = numCopie
		setPages = new HashSet<Page>()
		setPages.add(new Page(numPageInSubject, numPageInPDF))
	}

	def addInSet(Set<Page> pages) {
		for(i : 0 ..< pages.length)
			setPages.add(pages.get(i))
	}
	
	def int getNumCopie(){
		return this.numCopie
	}
	
	def Set<Page> getPagesCopie(){
		return setPages
	}
	
	def boolean isCopyComplete(int nbPageSubject){
		return nbPageSubject == setPages.length
	}
	
	def int getNumPageInPDF(int numPage){
		for(i : 0 ..< setPages.length)
			if(setPages.get(i).numPageInSubject == numPage)
				return setPages.get(i).numPageInPDF
	}
	
	override String toString(){
		var String res = "Copie " + numCopie + "[\n"
		for(i : 0 ..< setPages.length)
			res += setPages.get(i).toString() + "\n"
		return res + "]"
	}
	// TODO a voir comment définir la finalité de tri, où on envoie? comment?
	//définir les copies auxquelles il manque des pages
	// ---> donner le nombre de copies manquantes??
	
	
}
