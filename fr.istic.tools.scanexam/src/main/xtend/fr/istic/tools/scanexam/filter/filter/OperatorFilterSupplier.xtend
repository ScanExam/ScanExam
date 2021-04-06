package fr.istic.tools.scanexam.filter.filter

/**
 * Une interface fonctionnelle pour obtenir une nouvelle instance d'un {@link OperatorFilter}
 * @see OperatorFilter
 * @author Théo Giraudet
 */
 @FunctionalInterface
interface OperatorFilterSupplier {
	
	/**
	 * @return une nouvelle instance d'un {@link OperatorFilter OperatorFilterr&lt;U&gt;}
	 */
	def <U> OperatorFilter<U> get()
	
}