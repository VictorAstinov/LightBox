package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class AddButton(private val model : Model) : Button("Add") {
    init {
        graphic = ImageView(Image("add_button2.png", 16.0, 16.0, false, false)).apply {
            isCache = true
        }
        onAction = EventHandler {
            model.addImage()
        }
    }
}