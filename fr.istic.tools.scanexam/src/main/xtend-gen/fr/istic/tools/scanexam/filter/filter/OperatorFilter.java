package fr.istic.tools.scanexam.filter.filter;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Représente un opérateur pour lier une liste de {@link BasicFilter BasicFilter&lt;T&gt;}
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public interface OperatorFilter<T extends Object> extends Predicate<T> {
  /**
   * Ajoute un filtre à la liste des filtres sur lequel appliquer l'opérateur courant
   * @param filter un filtre à ajouter à la liste
   */
  void addFilter(final Predicate<T> filter);
  
  /**
   * Ajoute une liste de filtre à la liste des filtres sur lequel appliquer l'opérateur courant
   * @param filters une liste de filtre à ajouter à la liste
   */
  default void addFilters(final List<Predicate<T>> filters) {
    final Consumer<Predicate<T>> _function = (Predicate<T> filter) -> {
      this.addFilter(filter);
    };
    filters.forEach(_function);
  }
}
