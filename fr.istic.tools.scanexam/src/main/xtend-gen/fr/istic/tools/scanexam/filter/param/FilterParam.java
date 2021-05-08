package fr.istic.tools.scanexam.filter.param;

/**
 * Représente un paramètre de filtre
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public interface FilterParam<T extends Object> {
  /**
   * @param string un string à faire parser par le paramètre. Si le string a pu être parsé, le résultat du parse devient la valeur courante du filtre
   * @return un {@link ParamParseResult} décrivant la réussite ou non du parse
   */
  ParamParseResult parse(final String string);
  
  /**
   * @return une formule regex représentant le pattern que doit respecter tout string pour être correctement parsé
   */
  String getStringPattern();
  
  /**
   * @return la valeur du paramètre
   */
  T getValue();
  
  /**
   * @return le name code du paramètre
   */
  String getNameCode();
}
