package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class RotateLeftButton(private val model : Model) : Button("Rotate Left") {
    init {
        graphic = ImageView(Image("rotate_left5.png", 20.0, 20.0, false, true)).apply {
            isCache = true
        }
        onAction = EventHandler {
            model.rotateLeft()
        }
    }
}