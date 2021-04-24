package fr.istic.tools.scanexam.view.fx.utils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.util.Duration;

/**
 * Classe utilitaire pour signaler un mauvais format d'un nœud à l'utilisateur de diverses manières
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class BadFormatDisplayer {
  /**
   * Déclenche une petite animation de secouement sur le nœud passé en paramètre
   * @param node un nœud JavaFX
   */
  public static void shake(final Node node) {
    Duration _millis = Duration.millis(0);
    DoubleProperty _translateXProperty = node.translateXProperty();
    KeyValue _keyValue = new <Number>KeyValue(_translateXProperty, Integer.valueOf(0), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame = new KeyFrame(_millis, _keyValue);
    Duration _millis_1 = Duration.millis(100);
    DoubleProperty _translateXProperty_1 = node.translateXProperty();
    KeyValue _keyValue_1 = new <Number>KeyValue(_translateXProperty_1, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_1 = new KeyFrame(_millis_1, _keyValue_1);
    Duration _millis_2 = Duration.millis(200);
    DoubleProperty _translateXProperty_2 = node.translateXProperty();
    KeyValue _keyValue_2 = new <Number>KeyValue(_translateXProperty_2, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_2 = new KeyFrame(_millis_2, _keyValue_2);
    Duration _millis_3 = Duration.millis(300);
    DoubleProperty _translateXProperty_3 = node.translateXProperty();
    KeyValue _keyValue_3 = new <Number>KeyValue(_translateXProperty_3, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_3 = new KeyFrame(_millis_3, _keyValue_3);
    Duration _millis_4 = Duration.millis(400);
    DoubleProperty _translateXProperty_4 = node.translateXProperty();
    KeyValue _keyValue_4 = new <Number>KeyValue(_translateXProperty_4, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_4 = new KeyFrame(_millis_4, _keyValue_4);
    Duration _millis_5 = Duration.millis(500);
    DoubleProperty _translateXProperty_5 = node.translateXProperty();
    KeyValue _keyValue_5 = new <Number>KeyValue(_translateXProperty_5, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_5 = new KeyFrame(_millis_5, _keyValue_5);
    Duration _millis_6 = Duration.millis(600);
    DoubleProperty _translateXProperty_6 = node.translateXProperty();
    KeyValue _keyValue_6 = new <Number>KeyValue(_translateXProperty_6, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_6 = new KeyFrame(_millis_6, _keyValue_6);
    Duration _millis_7 = Duration.millis(700);
    DoubleProperty _translateXProperty_7 = node.translateXProperty();
    KeyValue _keyValue_7 = new <Number>KeyValue(_translateXProperty_7, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_7 = new KeyFrame(_millis_7, _keyValue_7);
    Duration _millis_8 = Duration.millis(800);
    DoubleProperty _translateXProperty_8 = node.translateXProperty();
    KeyValue _keyValue_8 = new <Number>KeyValue(_translateXProperty_8, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_8 = new KeyFrame(_millis_8, _keyValue_8);
    Duration _millis_9 = Duration.millis(900);
    DoubleProperty _translateXProperty_9 = node.translateXProperty();
    KeyValue _keyValue_9 = new <Number>KeyValue(_translateXProperty_9, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_9 = new KeyFrame(_millis_9, _keyValue_9);
    Duration _millis_10 = Duration.millis(1000);
    DoubleProperty _translateXProperty_10 = node.translateXProperty();
    KeyValue _keyValue_10 = new <Number>KeyValue(_translateXProperty_10, Integer.valueOf(0), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_10 = new KeyFrame(_millis_10, _keyValue_10);
    final Timeline tl = new Timeline(_keyFrame, _keyFrame_1, _keyFrame_2, _keyFrame_3, _keyFrame_4, _keyFrame_5, _keyFrame_6, _keyFrame_7, _keyFrame_8, _keyFrame_9, _keyFrame_10);
    tl.setDelay(Duration.seconds(0.2));
    tl.play();
  }
  
  /**
   * Déclenche une petite animation de secouement sur les nœuds passés en paramètre
   * @param nodes des nœuds JavaFX
   */
  public static void shake(final Node... nodes) {
    for (final Node node : nodes) {
      BadFormatDisplayer.shake(node);
    }
  }
  
  /**
   * Déclenche une petite animation de secouement sur les {@link TextInputControl} passés en paramètre si ceux-ci sont vide, ne fait rien sinon
   * @param nodes des {@link TextInputControl}
   */
  public static boolean isFullOrElseShake(final TextInputControl... nodes) {
    boolean isFull = true;
    for (final TextInputControl n : nodes) {
      boolean _equals = n.getText().equals("");
      if (_equals) {
        BadFormatDisplayer.shake(n);
        isFull = false;
      }
    }
    return isFull;
  }
  
  /**
   * Active ou désactive l'affichage de mauvais format pour le {@link TextInputControl} passé en paramètre. C'est-à-dire anime le nœud ({@link #shake shake}) et met lui une bordure rouge
   * @param node un nœud JavaFX
   * @param disp si il faut activer ou non l'affichage
   */
  public static void dispBadFormatView(final TextInputControl node, final boolean disp) {
    final PseudoClass errorClass = PseudoClass.getPseudoClass("wrong-format");
    if (disp) {
      node.pseudoClassStateChanged(errorClass, true);
      BadFormatDisplayer.shake(node);
    } else {
      node.pseudoClassStateChanged(errorClass, false);
    }
  }
}
