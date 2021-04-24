package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.filter.filter.OperatorFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Représente une conjonction de tous les prédicats associés à ce filtre
 * @author Théo Giraudet
 * @see BasicFilter
 * @see Predicate
 */
@SuppressWarnings("all")
public class AndFilter<T extends Object> implements OperatorFilter<T> {
  private final List<Predicate<T>> filters;
  
  /**
   * Construit une nouvelle conjonction de prédicats
   */
  public AndFilter() {
    LinkedList<Predicate<T>> _linkedList = new LinkedList<Predicate<T>>();
    this.filters = _linkedList;
  }
  
  /**
   * Ajoute un nouveau prédicat à la conjonction
   * @param filter un prédicat à ajouter
   */
  @Override
  public void addFilter(final Predicate<T> filter) {
    Objects.<Predicate<T>>requireNonNull(filter);
    this.filters.add(filter);
  }
  
  /**
   * @param t une valeur sur laquelle appliquer le prédicat
   * @return true si l'évaluation de <i>t</i> par au moins l'un des prédicats du filtre renvoie vrai, false sinon
   */
  @Override
  public boolean test(final T t) {
    boolean _xblockexpression = false;
    {
      final Function2<Predicate<T>, Predicate<T>, Predicate<T>> _function = (Predicate<T> acc, Predicate<T> elem) -> {
        return acc.and(elem);
      };
      final Predicate<T> bigAnd = IterableExtensions.<Predicate<T>>reduce(this.filters, _function);
      _xblockexpression = bigAnd.test(t);
    }
    return _xblockexpression;
  }
}
