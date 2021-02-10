package fr.istic.tools.scanexam.sessions

import fr.istic.tools.scanexam.core.Question
import java.io.File

/*
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
class ExamSession extends Session // TODO : renommer
{
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def void addQuestion(Question q)
	{
		getPage().questions.add(q);
	}
	
	/**
	 * Supprime une question
	 * @param index Index de la question à supprimer
	 * 
	 * @author degas
	 */
	def void removeQuestion(int index)
	{
		getPage().questions.remove(index);
	}
	
	override save() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override open(File xmiFile) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	
}