package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import javafx.collections.WeakSetChangeListener

class DirtyObservableSet<T>(originalSet: Set<T> = setOf()):
        ObservableSet<T> by FXCollections.observableSet(HashSet<T>(originalSet)) {

    private val originalSet = FXCollections.observableSet(HashSet<T>(originalSet))
    private val _isDirtyProperty = SimpleBooleanProperty()

    fun rebaseline() {
        originalSet.clear()
        originalSet.addAll(this)
        _isDirtyProperty.set(false)
    }
    fun reset() {
        clear()
        addAll(originalSet)
        _isDirtyProperty.set(false)
    }
    init {
        addListener(
                WeakSetChangeListener<T>(
                    SetChangeListener<T> {
                        _isDirtyProperty.set(originalSet != this)
                    }
                )
        )
    }
    fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    val isDirty get() = _isDirtyProperty.get()
}
