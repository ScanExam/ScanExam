package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.filter.filter.OperatorFilter;

/**
 * Une interface fonctionnelle pour obtenir une nouvelle instance d'un {@link OperatorFilter}
 * @see OperatorFilter
 * @author Théo Giraudet
 */
@FunctionalInterface
@SuppressWarnings("all")
public interface OperatorFilterSupplier {
  /**
   * @return une nouvelle instance d'un {@link OperatorFilter OperatorFilterr&lt;U&gt;}
   */
  <U extends Object> OperatorFilter<U> get();
}
