package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.view.Adapter;

/**
 * Interface de lancement de vue
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Launcher {
  /**
   * Lancement de l'application
   */
  Adapter launchApp();
}
