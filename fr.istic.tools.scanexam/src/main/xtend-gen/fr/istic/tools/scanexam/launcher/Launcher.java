package fr.istic.tools.scanexam.launcher;

/**
 * Interface de lancement de vue
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Launcher {
  /**
   * Créé l'adapter et initialise les attributs du presenter
   */
  void setup();
  
  /**
   * Lance l'application
   */
  void launch();
}
