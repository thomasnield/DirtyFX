package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.collections.WeakListChangeListener

class DirtyObservableList<T>(originalList: List<T> = listOf()):
        ObservableList<T> by FXCollections.observableArrayList<T>(originalList) {

    private val originalList = FXCollections.observableArrayList<T>(originalList)
    private val _isDirtyProperty = SimpleBooleanProperty()

    /** Sets this `ObservableList` to now be the "original" list **/
    fun rebaseline() {
        originalList.setAll(this)
        _isDirtyProperty.set(false)
    }
    /** Resets this `ObservableList` to the "original" list **/
    fun reset() {
        setAll(originalList)
        _isDirtyProperty.set(false)
    }
    init {
        addListener(
                WeakListChangeListener<T> (
                        ListChangeListener<T> { _ ->
                            _isDirtyProperty.set(originalList != this)
                        }
                )
        )
    }
    fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    val isDirty get() = _isDirtyProperty.get()
}