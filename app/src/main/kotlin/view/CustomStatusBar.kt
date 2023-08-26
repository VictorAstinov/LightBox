package view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import model.Model

class CustomStatusBar(private val model : Model): VBox(), InvalidationListener {

    private val label = Label("Testing").apply {
        prefHeight = 10.0
        alignment = Pos.CENTER_LEFT
    }
    // possibly add file name as well
    init {
        background = Background(BackgroundFill(Color.WHITE, CornerRadii(0.0), Insets(0.0)))
        children.add(label)
        padding = Insets(2.5)
        model.size.addListener(this)

        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        if (observable == model.size || observable == null) {
            label.text = "${model.size.get()} image${if (model.size.get() == 1 ) "" else "s"} loaded"
        }
    }
}