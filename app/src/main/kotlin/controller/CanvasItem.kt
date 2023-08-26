package controller

import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.event.EventHandler
import javafx.scene.image.ImageView
import model.Model
import kotlin.math.max
import kotlin.math.min
import javafx.scene.layout.Pane



class CanvasItem(private val model : Model, img : ImageView, xVal : Double, yVal : Double, var index : Int) : Pane() {

    private var offsetX = 0.0
    private var offsetY = 0.0
    val maxHeightProperty = ReadOnlyDoubleWrapper(0.0)
    val maxWidthProperty = ReadOnlyDoubleWrapper(0.0)
    var name = ""

    // this function was inspired by the draggable sample code found here:
    // https://git.uwaterloo.ca/cs349/public/sample-code/-/blob/master/12.Input/Dragging/src/main/kotlin/Draggable.kt
    private fun makeDraggable() {
        onMousePressed = EventHandler {
            if (model.isTiled.value) {
                return@EventHandler
            }
            model.selectItem(index)
            offsetX = this.translateX - it.sceneX
            offsetY = this.translateY - it.sceneY
            it.consume()
        }
        onMouseDragged = EventHandler {
            if (model.isTiled.value) {
                return@EventHandler
            }
            val mouseX = min(max(0.0, it.sceneX), maxWidthProperty.value)
            val mouseY = min(max(0.0, it.sceneY), maxHeightProperty.value)
            //this.translateX = min(max(0.0, (mouseX+ offsetX)), maxWidthProperty.value )
            //this.translateY = min(max(0.0, (mouseY + offsetY)), maxHeightProperty.value)
            this.translateX = mouseX + offsetX
            this.translateY = mouseY + offsetY

        }
    }
    init {
        isCache = true
        children.add(img.apply {
            isCache = true
        })
        // align child inside the outline
        children[0].translateX = 2.0
        children[0].translateY = 2.0
        rotate = 0.0
        scaleX = 1.0
        scaleY = 1.0
        translateX = xVal
        translateY = yVal
        offsetX = 0.0
        offsetY = 0.0
        onMouseClicked = EventHandler {
            // println("Index : $index")
            model.selectItem(index)
            it.consume()
        }
        makeDraggable()
    }
}