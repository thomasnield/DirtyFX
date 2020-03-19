package org.nield.dirtyfx.beans


import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObjectProperty<T>(initialValue: T): Property<T> by SimpleObjectProperty(initialValue), DirtyProperty {
    private val _originalValueProperty = SimpleObjectProperty(initialValue)
    private val _isDirtyProperty = SimpleBooleanProperty(false)

    private val listener = ChangeListener<T> { _,_,_ ->
        _isDirtyProperty.set(_originalValueProperty.get() != value)
    }
    
    init {
        addListener(WeakChangeListener(listener))
    }
    
    fun originalValueProperty(): ObservableValue<T> = _originalValueProperty
    val originalValue get() = _originalValueProperty.get()

    /** Sets the current value to now be the "original" value **/
    override fun rebaseline() {
        _originalValueProperty.set(value)
        _isDirtyProperty.set(false)
    }
    /** Resets the current value to the "original" value **/
    override fun reset() {
        value = _originalValueProperty.get()
        _isDirtyProperty.set(false)
    }
    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()
}
