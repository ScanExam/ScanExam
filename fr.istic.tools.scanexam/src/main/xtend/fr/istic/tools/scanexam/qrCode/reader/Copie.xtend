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

	def addInSet(int numPageInPDF, int numPageInSubject) {
		setPages.add(new Page(numPageInSubject, numPageInPDF))
	}
	
	def int getNumCopie(){
		return this.numCopie
	}
	//TODO peut etre changer
	def boolean isCopyComplete(int nbPageSubject){
		return nbPageSubject == setPages.size
	}
	
	
	
	// TODO a voir comment définir la finalité de tri, où on envoie? comment?
	//définir les copies auxquelles il manque des pages
	// ---> donner le nombre de copies manquantes??
	
		def static void main(String[] arg) {

		val Copie copieTest = new Copie(0, 0, 0)
		copieTest.addInSet(0,1)
		copieTest.addInSet(1,0)

	}
}
