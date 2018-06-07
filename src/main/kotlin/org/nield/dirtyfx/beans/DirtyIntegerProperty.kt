package org.nield.dirtyfx.beans

import javafx.beans.InvalidationListener
import javafx.beans.property.*
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyIntegerProperty(initialValue: Int): IntegerProperty(), DirtyProperty{

    private var originalValue = initialValue
    private val _isDirtyProperty = SimpleBooleanProperty(false)

    private val delegate = SimpleIntegerProperty(initialValue)

    init {
        addListener(
                WeakChangeListener<Number> (
                    ChangeListener<Number> { _,_,_ ->
                        _isDirtyProperty.set(originalValue != value)
                    }
                )
        )
    }
    /** Sets the current value to now be the "original" value **/
    override fun rebaseline() {
        originalValue = value
        _isDirtyProperty.set(false)
    }
    /** Resets the current value to the "original" value **/
    override fun reset() {
        value = originalValue
        _isDirtyProperty.set(false)
    }
    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun getName() = delegate.name

    override fun addListener(listener: ChangeListener<in Number>?) = delegate.addListener(listener)

    override fun addListener(listener: InvalidationListener?) = delegate.addListener(listener)

    override fun removeListener(listener: ChangeListener<in Number>?) = delegate.addListener(listener)

    override fun removeListener(listener: InvalidationListener?) = delegate.removeListener(listener)

    override fun get() =  delegate.get()

    override fun getBean() = delegate.bean

    override fun set(value: Int) = delegate.set(value)

    override fun unbind() = delegate.unbind()

    override fun bind(observable: ObservableValue<out Number>?) = delegate.bind(observable)

    override fun isBound() = delegate.isBound
}