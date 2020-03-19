package org.nield.dirtyfx.tracking

import javafx.beans.InvalidationListener
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.WeakListChangeListener

class CompositeDirtyProperty: ReadOnlyBooleanProperty(), DirtyProperty {

    private val items = FXCollections.observableArrayList<DirtyProperty> { arrayOf(it.isDirtyProperty()) }
    private val _dirtyStateProperty = SimpleBooleanProperty()

    private val listener = ListChangeListener<DirtyProperty> { _ ->
        _dirtyStateProperty.set(
                items.any { it.isDirty }
        )
    }

    init {
        items.addListener(WeakListChangeListener(listener))
    }

    /**
     * Indicates if any tracked DirtyProperties are dirty
     */
    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _dirtyStateProperty

    /**
     * Indicates if any tracked DirtyProperties are dirty
     */
    override val isDirty get() = _dirtyStateProperty.get()

    /**
     * Rebaselines all tracked DirtyProperties
     */
    override fun rebaseline() {
        items.forEach { it.rebaseline() }
    }
    /**
     * Resets all tracked DirtyProperties
     */
    override fun reset() {
        items.forEach { it.reset() }
    }

    fun addAll(vararg dirtyProperties: DirtyProperty) {
        dirtyProperties.forEach { add(it) }
    }
    fun add(dirtyProperty: DirtyProperty) {
        items += dirtyProperty
    }
    fun remove(dirtyProperty: DirtyProperty) {
        items += dirtyProperty
    }
    fun clear() = items.clear()

    operator fun plusAssign(dirtyProperty: DirtyProperty) {
        items += dirtyProperty
    }
    operator fun minusAssign(dirtyProperty: DirtyProperty) {
        items += dirtyProperty
    }

    override fun removeListener(listener: ChangeListener<in Boolean>?) = _dirtyStateProperty.removeListener(listener)

    override fun removeListener(listener: InvalidationListener?) = _dirtyStateProperty.removeListener(listener)

    override fun addListener(listener: ChangeListener<in Boolean>?) = _dirtyStateProperty.addListener(listener)

    override fun addListener(listener: InvalidationListener?)  = _dirtyStateProperty.addListener(listener)

    override fun getValue() = _dirtyStateProperty.value

    override fun get(): Boolean = _dirtyStateProperty.get()

    override fun getName(): String = _dirtyStateProperty.name

    override fun getBean(): Any = _dirtyStateProperty.bean


}
