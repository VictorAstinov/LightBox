package controller

import javafx.event.EventHandler
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import model.Model

class TileButton(private val model : Model) : ToggleButton("Tile") {
    init {
        graphic = ImageView(Image("tile.png", 19.0, 19.0, false, true)).apply {
            isCache = true
        }
        toggleGroup = model.viewMode
        onAction = EventHandler {
            model.tile()
        }
    }
}