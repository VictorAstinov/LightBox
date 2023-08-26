package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class ZoomInButton(private val model : Model) : Button("Zoom In") {
    init {
        graphic = ImageView(Image("zoom_in.png", 15.0, 15.0, false, true)).apply {
            isCache = true
        }
        onAction = EventHandler {
            model.zoomIn()
        }
    }
}