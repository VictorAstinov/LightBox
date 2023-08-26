package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class ResetButton(private val model : Model) : Button("Reset") {
    init {
        graphic = ImageView(Image("reset3.png", 16.0, 16.0, false, true)).apply {
            isCache = true
        }
        onAction = EventHandler {
            model.reset()
        }
    }
}