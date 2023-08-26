package controller

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class DeleteButton(private val model : Model) : Button("Delete") {
    init {
        onAction = EventHandler {
            model.deleteImage()
        }
        graphic = ImageView(Image("delete_button.png", 10.0, 10.0, false, true)).apply {
            isCache = true
        }
    }
}