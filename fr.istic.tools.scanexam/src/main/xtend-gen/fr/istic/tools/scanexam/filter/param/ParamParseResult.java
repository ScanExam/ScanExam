package fr.istic.tools.scanexam.filter.param;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Représente la réussite ou non du parse d'une valeur par un {@link FilterParam}
 * @author Théo Giraudet
 * @see FilterParam
 */
@SuppressWarnings("all")
class ParamParseResult {
  /**
   * @param message le message décrivant la raison de l'échec du parse (non null)
   * @return un {@link ParamParseResult} représentant l'échec du parse
   */
  public static ParamParseResult failed(final String message) {
    Objects.<String>requireNonNull(message);
    return new ParamParseResult(message);
  }
  
  /**
   * @return un {@link ParamParseResult} représentant la réussite du parse
   */
  public static ParamParseResult succeed() {
    return new ParamParseResult(null);
  }
  
  private final String message;
  
  private ParamParseResult(final String message) {
    this.message = message;
  }
  
  /**
   * @return true si le {@link ParamParseResult} courant représente une réussite du parse, false sinon
   */
  public boolean succeeded() {
    return (this.message != null);
  }
  
  /**
   * @return le message décrivant la raison de l'échec si le {@link ParamParseResult} courant représente un échec du parse
   * @throw NoSuchElementException si le {@link ParamParseResult} courant représente une réussite du parse
   */
  public String getFailureMessage() {
    if ((this.message == null)) {
      throw new NoSuchElementException("No message present");
    }
    return this.message;
  }
}
