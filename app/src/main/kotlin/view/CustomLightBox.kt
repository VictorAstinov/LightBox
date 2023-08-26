package view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.event.EventHandler
import javafx.scene.layout.Pane
import model.Model

class CustomLightBox(private val model : Model) : Pane(), InvalidationListener {

    private val wProperty = ReadOnlyDoubleWrapper(0.0)
    private val hProperty = ReadOnlyDoubleWrapper(0.0)
    val minHProperty = ReadOnlyDoubleWrapper()
    val minWProperty = ReadOnlyDoubleWrapper()

    private fun getMaxX() : Double {
        return model.list.value.maxOfOrNull { it.translateX + it.boundsInParent.width } ?: 0.0
    }

    private fun getMaxY(): Double {
        return model.list.value.maxOfOrNull { it.translateY + it.boundsInParent.height } ?: 0.0
    }

    init {
        minWidthProperty().bind(Bindings.max(wProperty, minWProperty))
        minHeightProperty().bind(Bindings.max(hProperty, minHProperty))
        onMouseClicked = EventHandler {
            // this kinda breaks MVC principles, but the canvas here is both a controller and a view
            // besides, its one little base case
            model.selectItem(-1)
            // println("clicked pane (${minWProperty.value}, ${minHProperty.value})")

        }
        model.list.addListener(this)
        model.selectedItem.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        // don't make changes if we're not in cascade mode
        if (model.isTiled.value) {
            return
        }
        if (observable == model.list || observable == null) {
            children.clear()
            model.list.forEach {
                children.add(it)
            }
            // println("children : (${children.size}, ${model.size.get()})")
            wProperty.value = getMaxX()
            hProperty.value = getMaxY()
            // println("moving: ($width, $height)")
        }
        if (observable == model.selectedItem) {
            children.clear()
            model.list.forEach {
                children.add(it.apply {
                    style = " -fx-border-style: none;"
                })
            }
            if (model.selectedItem.value >= 0 && children.size > 0) {
                children[model.selectedItem.value].style = "-fx-border-color: darkturquoise; -fx-border-width: 2px"
            }
        }
    }
}