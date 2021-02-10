package fr.istic.tools.scanexam.api

/**
 * Une classe représentant un Sujet à observer.
 * 
 * @author Théo Giraudet
 */
interface Subject<T> {
	
	/**
	 * Ajoute un observateur à la liste des observateurs de l'objet
	 * @param observer un Observateur (non null)
	 */
	def void addObserver(Observer<T> observer)
	
	/**
	 * Retire un observateur à la liste des observateurs de l'objet
	 * @param observer un Observateur (non null)
	 */
	def void removeObserver(Observer<T> observer)
	
	/**
	 * Notifie tous les observateurs pour la valeur <b>value</b>
	 * @param value une valeur (non null)
	 */
	def void notifyObservers(T value)
	
}