package org.nield.dirtyfx.beans

import javafx.beans.InvalidationListener
import javafx.beans.property.*
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty



class DirtySetProperty<T> constructor(originalSet: Set<T> = mutableSetOf()): DirtyProperty, SetProperty<T>() {

    constructor(vararg items: T):  this(items.toMutableSet())

    private val _originalSet = FXCollections.observableSet(originalSet.toMutableSet())
    private val _isDirtyProperty = SimpleBooleanProperty()
    private val backingSet = SimpleSetProperty(FXCollections.observableSet(originalSet.toMutableSet()))

    private val listener = ChangeListener<Set<T>> { _,_,_ ->
        _isDirtyProperty.set(_originalSet != this)
    }

    init {
        addListener(WeakChangeListener(listener))
    }


    /** Sets this `ObservableList` to now be the "original" list **/
    override fun rebaseline() {
        _originalSet.clear()
        _originalSet.addAll(this)
        _isDirtyProperty.set(false)
    }
    /** Resets this `ObservableList` to the "original" list **/
    override fun reset() {
        clear()
        addAll(_originalSet)
        _isDirtyProperty.set(false)
    }


    val originalSet get() = FXCollections.unmodifiableObservableSet(_originalSet)

    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun hashCode() = backingSet.hashCode()
    override fun equals(other: Any?) = backingSet == other

    override fun emptyProperty() = backingSet.emptyProperty()

    override fun sizeProperty() = backingSet.sizeProperty()

    override fun getName() = backingSet.name

    override fun addListener(listener: ChangeListener<in ObservableSet<T>>?) = backingSet.addListener(listener)

    override fun addListener(listener: InvalidationListener?) = backingSet.addListener(listener)

    override fun addListener(listener: SetChangeListener<in T>?) = backingSet.addListener(listener)

    override fun removeListener(listener: ChangeListener<in ObservableSet<T>>?) = backingSet.removeListener(listener)

    override fun removeListener(listener: InvalidationListener?) = backingSet.removeListener(listener)

    override fun removeListener(listener: SetChangeListener<in T>?) = backingSet.removeListener(listener)

    override fun get() = backingSet.get()

    override fun getBean() = backingSet.bean

    override fun set(value: ObservableSet<T>?) = backingSet.set(value)

    override fun unbind() = backingSet.unbind()

    override fun bind(observable: ObservableValue<out ObservableSet<T>>?) = backingSet.bind(observable)

    override fun isBound() = backingSet.isBound
}

