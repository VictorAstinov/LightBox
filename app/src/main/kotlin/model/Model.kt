package model

import controller.CanvasItem
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.scene.control.ToggleGroup
import javafx.scene.control.ToolBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Stage
import kotlin.random.Random
class Model(private val stage : Stage) {
    private val imageList = ReadOnlyListWrapper<CanvasItem>(FXCollections.observableArrayList())
    private val sizeProperty = ReadOnlyIntegerWrapper()
    private val selectedItemProperty = ReadOnlyIntegerWrapper(-1)
    private val layoutProperty = ReadOnlyBooleanWrapper(false)

    val viewMode = ToggleGroup().apply {
        selectedToggleProperty().addListener { _, old, new ->
            run {
                if (new == null) {
                    old.isSelected = true
                }
            }
        }
    }
    val size : ReadOnlyIntegerProperty = sizeProperty.readOnlyProperty
    val list : ReadOnlyListProperty<CanvasItem> = imageList.readOnlyProperty
    val selectedItem : ReadOnlyIntegerProperty = selectedItemProperty.readOnlyProperty
    val isTiled : ReadOnlyBooleanProperty = layoutProperty.readOnlyProperty
    fun addImage() : Unit {
        val alert = FileChooser().apply {
            title = "LightBox - Choose Image"
            extensionFilters.add(FileChooser.ExtensionFilter("Image Files", "*.png", "*.bmp", "*.jpg"))
        }
        val file = alert.showOpenDialog(stage) ?: return

        // this is to make sure the loaded image is at least partially visible
        val root : BorderPane = stage.scene.root as BorderPane
        val x  = Random.nextInt(0, (root.widthProperty().value - 300).toInt()).toDouble()
        val y = Random.nextInt(0, (root.heightProperty().value - 200).toInt()).toDouble()
        val idx = imageList.value.size

        imageList.value.add(CanvasItem(this,
            ImageView(Image(file.absolutePath)).apply {
                fitWidth = 300.0
                isPreserveRatio = true
                isSmooth = true
        }, x, y, idx).apply{
            maxHeightProperty.bind(root.heightProperty().subtract((root.bottom as VBox).heightProperty().add((root.top as ToolBar).heightProperty())))
            maxWidthProperty.bind(root.widthProperty())
            name = file.absolutePath // here for debugging purposes
        })
        selectItem(idx)
    }

    fun deleteImage() : Unit {
        imageList.value.removeAt(selectedItem.value)
        for (i in 0 until imageList.value.size) {
            imageList.value[i].index = i
        }
        selectedItemProperty.value = -1
    }
    fun rotateRight() : Unit {
        val index = selectedItemProperty.value
        imageList.value[index].rotate += 10.0
    }
    fun rotateLeft() : Unit {
        val index = selectedItemProperty.value
        imageList.value[index].rotate -= 10.0
    }
    fun zoomIn() : Unit {
        val index = selectedItemProperty.value
        imageList.value[index].scaleX *= 1.25
        imageList.value[index].scaleY *= 1.25
    }
    fun zoomOut() : Unit {
        val index = selectedItemProperty.value
        imageList.value[index].scaleX /= 1.25
        imageList.value[index].scaleY /= 1.25
    }
    fun reset() : Unit {
        val index = selectedItemProperty.value
        imageList.value[index].scaleX = 1.0
        imageList.value[index].scaleY = 1.0
        imageList.value[index].rotate = 0.0
    }


    fun cascade() : Unit {
        layoutProperty.value = false
    }

    fun tile() : Unit {
        layoutProperty.value = true
    }

    fun selectItem(index : Int) : Unit {
        // if we clicked the canvas
        if (index < 0) {
            selectedItemProperty.value = -1
            return
        }
        if (isTiled.value) {
            selectedItemProperty.value = index
            return
        }
        //move selected item to back of list -> this will move it to foreground
        val temp = imageList.value[index]

        imageList.value.removeAt(index)
        imageList.value.add(temp)

        // must update indices -> this can be done a bit faster, still on O(n) time. Don't want to make a mistake
        for (i in 0 until imageList.value.size) {
            imageList.value[i].index = i
        }
        // technically the selected index never changes here (last one in the list), can manually force it to change like this
        selectedItemProperty.value = -1
        selectedItemProperty.value = imageList.value.size - 1
    }

    init {
        sizeProperty.bind(imageList.sizeProperty())
    }
}