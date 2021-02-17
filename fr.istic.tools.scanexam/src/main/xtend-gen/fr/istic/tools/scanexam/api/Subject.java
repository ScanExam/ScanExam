package fr.istic.tools.scanexam.api;

import fr.istic.tools.scanexam.api.Observer;

/**
 * Une classe représentant un Sujet à observer.
 * 
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public interface Subject<T extends Object> {
  /**
   * Ajoute un observateur à la liste des observateurs de l'objet
   * @param observer un Observateur (non null)
   */
  void addObserver(final Observer<T> observer);
  
  /**
   * Retire un observateur à la liste des observateurs de l'objet
   * @param observer un Observateur (non null)
   */
  void removeObserver(final Observer<T> observer);
  
  /**
   * Notifie tous les observateurs pour la valeur <b>value</b>
   * @param value une valeur (non null)
   */
  void notifyObservers(final T value);
}
