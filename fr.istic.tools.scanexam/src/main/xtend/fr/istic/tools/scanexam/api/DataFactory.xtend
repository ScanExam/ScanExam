package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.Rectangle
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.GradeScale
import fr.istic.tools.scanexam.core.Question

/**
 * Factory qui crée des objets de type Data: {@link Rectangle},   {@link StudentSheet},  {@link Grade},  {@link GradeScale}, {@link Question}
 * 
 * @author Antoine Degas
 */
class DataFactory {
	def Rectangle createRectangle(float x, float y, float width, float height){
		val rec = CoreFactory.eINSTANCE.createRectangle
		rec.x = x
		rec.y = y
		rec.width = width
		rec.heigth = height
		rec
	}
	
	def StudentSheet createStudentSheet(){
		CoreFactory.eINSTANCE.createStudentSheet
	}
	
	def Grade createGrade(){
		CoreFactory.eINSTANCE.createGrade
	}
	
	def GradeScale createGradeScale(){
		CoreFactory.eINSTANCE.createGradeScale
	}
	
	def Question createQuestion(int id){
		val question = CoreFactory.eINSTANCE.createQuestion
		question.id = id
		question
	}
}

