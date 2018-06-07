package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import javafx.collections.WeakSetChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableSet<T>(originalSet: Set<T> = setOf()):
        ObservableSet<T> by FXCollections.observableSet(HashSet<T>(originalSet)), DirtyProperty {

    private val originalSet = FXCollections.observableSet(HashSet<T>(originalSet))
    private val _isDirtyProperty = SimpleBooleanProperty()

    override fun rebaseline() {
        originalSet.clear()
        originalSet.addAll(this)
        _isDirtyProperty.set(false)
    }
    override fun reset() {
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
    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()
}
