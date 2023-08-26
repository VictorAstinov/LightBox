package view

import controller.CanvasItem
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Orientation
import javafx.scene.layout.FlowPane
import javafx.event.EventHandler

import model.Model

class CustomTileView(private val model : Model) : FlowPane(), InvalidationListener {

    private fun clearCV(cv : CanvasItem) : CanvasItem {
        return cv.apply {
            children[0].translateX = 2.0
            children[0].translateY = 2.0
            rotate = 0.0
            scaleX = 1.0
            scaleY = 1.0
            translateX = 0.0
            translateY = 0.0
        }
    }
    init {
        orientation = Orientation.HORIZONTAL
        onMouseClicked = EventHandler {
            // this kinda breaks MVC principles, but the canvas here is both a controller and a view
            // besides, its one little base case
            model.selectItem(-1)
            // println("clicked Tile pane")

        }
        model.list.addListener(this)
        model.selectedItem.addListener(this)
        vgap = 5.0
        hgap = 5.0
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        // don't make changes if we're not in tiled mode
        if (!model.isTiled.value) {
            return
        }
        if (observable == null || observable == model.list) {
            children.clear()
            model.list.forEach {
                children.add(clearCV(it))
            }
        }
        if (observable == model.selectedItem) {
            children.clear()
            model.list.forEach {
                children.add(clearCV(it).apply {
                    style = " -fx-border-style: none;"
                })
            }
            if (model.selectedItem.value >= 0 && children.size > 0) {
                children[model.selectedItem.value].style = "-fx-border-color: darkturquoise; -fx-border-width: 2px"
            }
        }
    }
}