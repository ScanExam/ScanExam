package fr.istic.tools.scanexam.qrCode.reader

import java.util.Set
import java.util.HashSet

class Examen {
	int nbCopies
	int pagesSubject
	Set<Copie> lesCopies
	
	new(int pS, int nbCop){
		this.nbCopies = nbCop
		this.pagesSubject = pS
		this.lesCopies = new HashSet<Copie>()
	}
	
	def boolean isExamenComplete(){
		var boolean ret = true
		for(i : 0 ..< lesCopies.length){
			ret = ret && lesCopies.get(i).isCopyComplete(this.pagesSubject)
		}
		return ret && (nbCopies == lesCopies.length)
	}
	
	def Set<Copie> getUncompleteCopies(){
		val Set<Copie> uncompleteCopies = new HashSet<Copie>()
		
		for(i : 0 ..< lesCopies.length){
			if(!lesCopies.get(i).isCopyComplete(this.pagesSubject))
				uncompleteCopies.add(lesCopies.get(i))
		}
		return uncompleteCopies
	}
	
	def Set<Copie> getCompleteCopies(){
		val Set<Copie> completeCopies = new HashSet<Copie>()
		
		for(i : 0 ..< lesCopies.length){
			if(lesCopies.get(i).isCopyComplete(this.pagesSubject))
				completeCopies.add(lesCopies.get(i))
		}
		return completeCopies
	}
	
	def Copie getCopie(int numCopie){
		for(i : 0 ..< lesCopies.length)
			if(lesCopies.get(i).numCopie == numCopie)
				return lesCopies.get(i)
	}
	
	def addCopie(Copie copie){
		var boolean trouve = false
		var int i = 0
		
		while(!trouve && i < lesCopies.length){
			if(lesCopies.get(i).numCopie == copie.numCopie)
				trouve = true
			i++
		}//while
		
		i--
		if(trouve){
			lesCopies.get(i).addInSet(copie.pagesCopie)
		}
		else
			lesCopies.add(copie)
	}
	
	override String toString(){
		var String res = "Examen \n"
		
		for(i : 0 ..< lesCopies.length)
			res += lesCopies.get(i).toString() + "\n"
		
		return res
	}
	
	
	def static void main(String[] arg) {

	val Examen exam = new Examen(2, 1)
	
	val Copie copie1 = new Copie(0, 0, 0)
	val Copie copie2 = new Copie(0, 1, 1)
	
	val Copie copie3 = new Copie(1, 2, 0)
	val Copie copie4 = new Copie(1, 3, 1)
	
	exam.addCopie(copie1)
	exam.addCopie(copie2)
	exam.addCopie(copie3)
	exam.addCopie(copie4)
	
	println(exam.toString())
	}
}