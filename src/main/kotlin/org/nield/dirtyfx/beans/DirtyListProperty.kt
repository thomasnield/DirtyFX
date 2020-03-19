package org.nield.dirtyfx.beans

import javafx.beans.InvalidationListener
import javafx.beans.property.*
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyListProperty<T> constructor(originalList: List<T> = mutableListOf()): DirtyProperty, ListProperty<T>() {

    constructor(vararg items: T):  this(items.toMutableList())

    private val _originalList = FXCollections.observableArrayList(originalList)
    private val _isDirtyProperty = SimpleBooleanProperty()
    private val backingList = SimpleListProperty(FXCollections.observableArrayList(originalList))

    private val listener = ChangeListener<List<T>> { _,_,_ ->
        _isDirtyProperty.set(_originalList != this)
    }

    init {
        addListener(WeakChangeListener(listener))
    }


    /** Sets this `ObservableList` to now be the "original" list **/
    override fun rebaseline() {
        _originalList.setAll(this)
        _isDirtyProperty.set(false)
    }
    /** Resets this `ObservableList` to the "original" list **/
    override fun reset() {
        setAll(_originalList)
        _isDirtyProperty.set(false)
    }


    val originalList get() = FXCollections.unmodifiableObservableList(_originalList)

    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun hashCode() = backingList.hashCode()
    override fun equals(other: Any?) = backingList == other


    override fun addListener(listener: ChangeListener<in ObservableList<T>>?) = backingList.addListener(listener)

    override fun addListener(listener: InvalidationListener?) = backingList.addListener(listener)

    override fun addListener(listener: ListChangeListener<in T>?) = backingList.addListener(listener)

    override fun removeListener(listener: ChangeListener<in ObservableList<T>>?) = backingList.removeListener(listener)

    override fun removeListener(listener: InvalidationListener?) = backingList.removeListener(listener)

    override fun removeListener(listener: ListChangeListener<in T>?) = backingList.removeListener(listener)

    override fun getBean() = backingList.bean

    override fun isBound() = backingList.isBound

    override fun emptyProperty() = backingList.emptyProperty()

    override fun sizeProperty() = backingList.sizeProperty()

    override fun getName() = backingList.name

    override fun get() = backingList.get()

    override fun set(value: ObservableList<T>?) = backingList.set(value)

    override fun unbind() = backingList.unbind()

    override fun bind(observable: ObservableValue<out ObservableList<T>>?)  = backingList.bind(observable)
}
