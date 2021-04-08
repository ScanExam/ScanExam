package fr.istic.tools.scanexam.view.fX.utils

import javafx.scene.control.TextInputControl
import javafx.animation.KeyValue
import javafx.animation.KeyFrame
import javafx.scene.Node
import javafx.animation.Timeline
import javafx.util.Duration
import javafx.animation.Interpolator

class ShakeEffect {

    def static void shake(Node n) {
        val tl = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(n.translateXProperty(), 0,	Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(100), new KeyValue(n.translateXProperty(), -10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(200), new KeyValue(n.translateXProperty(), 10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(300), new KeyValue(n.translateXProperty(), -10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(400), new KeyValue(n.translateXProperty(), 10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(500), new KeyValue(n.translateXProperty(), -10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(600), new KeyValue(n.translateXProperty(), 10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(700), new KeyValue(n.translateXProperty(), -10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(800), new KeyValue(n.translateXProperty(), 10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(900), new KeyValue(n.translateXProperty(), -10, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(1000), new KeyValue(n.translateXProperty(), 0, Interpolator.EASE_BOTH))
        )
        tl.setDelay(Duration.seconds(0.2))
        tl.play()
    }

    def static void shake(Node... n) {
        for(Node node : n)
            shake(node)
    }

    def static boolean isFullOrElseShake(TextInputControl... nodes) {
        var isFull = true
        for(TextInputControl n : nodes)
            if(n.getText().equals("")) {
                shake(n)
                isFull = false
            }
        return isFull
    }
}