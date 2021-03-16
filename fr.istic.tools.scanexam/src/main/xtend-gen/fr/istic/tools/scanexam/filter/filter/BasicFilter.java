package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.filter.param.FilterParam;
import java.util.List;
import java.util.function.Predicate;

/**
 * Représente un filtre appliquable sur T<br/>
 * Peut être considéré comme un {@link Predicate Predicater&lt;T&gt;}
 * @author Théo Giraudet
 * @see Predicate
 */
@SuppressWarnings("all")
public interface BasicFilter<T extends Object> extends Predicate<T> {
  /**
   * @return la liste des paramètres que prend le filtre courant
   * @see FilterParam
   */
  List<FilterParam<?>> getParams();
}
