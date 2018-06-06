package org.nield.dirtyfx.beans

import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener

class DirtyObjectProperty<T>(initialValue: T): Property<T> by SimpleObjectProperty(initialValue) {
    private var originalValue = initialValue
    private val _isDirtyProperty = SimpleBooleanProperty(false)

    init {
        addListener(
                WeakChangeListener<T> (
                    ChangeListener<T> { _,_,_ ->
                        _isDirtyProperty.set(originalValue != value)
                    }
                )
        )
    }
    /** Sets the current value to now be the "original" value **/
    fun rebaseline() {
        originalValue = value
        _isDirtyProperty.set(false)
    }
    /** Resets the current value to the "original" value **/
    fun reset() {
        value = originalValue
        _isDirtyProperty.set(false)
    }
    fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    val isDirty get() = _isDirtyProperty.get()
}