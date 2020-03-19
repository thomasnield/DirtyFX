package org.nield.dirtyfx.beans


import javafx.beans.InvalidationListener
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyBooleanProperty(initialValue: Boolean): BooleanProperty(), DirtyProperty {

    private var _originalValueProperty = SimpleBooleanProperty(initialValue)
    private val _isDirtyProperty = SimpleBooleanProperty(false)

    private val delegate = SimpleBooleanProperty(initialValue)

    private val listener = ChangeListener<Boolean> { _, _, _ ->
        _isDirtyProperty.set(_originalValueProperty.get() != value)
    }
    init {
        addListener(WeakChangeListener(listener))
    }

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

    fun originalValueProperty(): ReadOnlyBooleanProperty = _originalValueProperty
    val originalValue get() = _originalValueProperty.get()

    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun getName() = delegate.name

    override fun addListener(listener: ChangeListener<in Boolean>?) = delegate.addListener(listener)

    override fun addListener(listener: InvalidationListener?) = delegate.addListener(listener)

    override fun removeListener(listener: ChangeListener<in Boolean>?) = delegate.addListener(listener)

    override fun removeListener(listener: InvalidationListener?) = delegate.removeListener(listener)

    override fun get() = delegate.get()

    override fun getBean() = delegate.bean

    override fun set(value: Boolean) = delegate.set(value)

    override fun unbind() = delegate.unbind()

    override fun bind(observable: ObservableValue<out Boolean>?) = delegate.bind(observable)

    override fun isBound() = delegate.isBound
}
