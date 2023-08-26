package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class ZoomOutButton(private val model : Model) : Button("Zoom Out") {
    init {
        graphic = ImageView(Image("zoom_out.png", 16.0, 16.0, false, true)).apply {
            isCache = true
        }
        onAction = EventHandler {
            model.zoomOut()
        }
    }
}