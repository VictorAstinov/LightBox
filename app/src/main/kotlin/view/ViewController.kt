package view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.layout.BorderPane
import model.Model

class ViewController(private val model: Model) : BorderPane(), InvalidationListener {
    init {
        model.isTiled.addListener(this)
        top = CustomToolBar(model).apply {
            prefHeight = 35.0
        }
        bottom = CustomStatusBar(model).apply {
            prefHeight = 10.0
        }
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        if (observable == model.isTiled || observable == null) {
            if (model.isTiled.value) {
                center = ScrollPane(CustomTileView(model).apply {
                    prefWidthProperty().bind(this@ViewController.widthProperty().subtract(3.0))
                    prefWrapLengthProperty().bind(this@ViewController.widthProperty())
                    minHeightProperty().bind(this@ViewController.heightProperty().subtract((bottom as CustomStatusBar).heightProperty()).subtract((top as CustomToolBar).heightProperty()).subtract(3.0))
                }).apply { hbarPolicy = ScrollBarPolicy.NEVER }
            }
            else {

                center = ScrollPane(CustomLightBox(model).apply {
                    minWProperty.bind(this@ViewController.widthProperty().subtract(3.0))
                    minHProperty.bind(this@ViewController.heightProperty().subtract((bottom as CustomStatusBar).heightProperty()).subtract((top as CustomToolBar).heightProperty()).subtract(3.0))
                    //minHProperty.bind(heightProperty().subtract((bottom as CustomStatusBar).heightProperty()).subtract((top as CustomToolBar).heightProperty()))

                }).apply {
                    hbarPolicy = ScrollBarPolicy.AS_NEEDED
                    vbarPolicy = ScrollBarPolicy.AS_NEEDED
                }
            }
        }
    }
}