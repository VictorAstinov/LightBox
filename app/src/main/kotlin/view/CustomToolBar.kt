package view

import controller.*
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.Separator
import javafx.scene.control.ToolBar
import model.Model

class CustomToolBar(private val model : Model) : ToolBar(), InvalidationListener {
    init {
        // add buttons
        items.add(AddButton(model))
        items.add(DeleteButton(model).apply{
            disableProperty().bind(model.selectedItem.lessThan(0))
        })
        items.add(Separator())
        items.add(RotateLeftButton(model).apply {
            disableProperty().bind(model.selectedItem.lessThan(0).or(model.isTiled))
        })
        items.add(RotateRightButton(model).apply {
            disableProperty().bind(model.selectedItem.lessThan(0).or(model.isTiled))
        })
        items.add(ZoomInButton(model).apply {
            disableProperty().bind(model.selectedItem.lessThan(0).or(model.isTiled))
        })
        items.add(ZoomOutButton(model).apply {
            disableProperty().bind(model.selectedItem.lessThan(0).or(model.isTiled))
        })
        items.add(ResetButton(model).apply {
            disableProperty().bind(model.selectedItem.lessThan(0).or(model.isTiled))
        })
        items.add(Separator())
        items.add(CascadeButton(model).apply {
            isSelected = true // default selection
        })
        items.add(TileButton(model))

    }

    // while this is never implemented as the buttons use bindings directly
    // could be useful for future things, so leaving class as an Interface of InvalidationListener
    override fun invalidated(observable: Observable?) {}

}