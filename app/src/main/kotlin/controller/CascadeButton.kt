package controller

import javafx.event.EventHandler
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class CascadeButton(private val model : Model) : ToggleButton("Cascade") {
    init {
        graphic = ImageView(Image("cascade2.png", 18.0, 18.0, false, true)).apply {
            isCache = true
        }
        toggleGroup = model.viewMode
        onAction = EventHandler {
            model.cascade()
        }
    }
}