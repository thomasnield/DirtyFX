package org.nield.dirtyfx.tracking

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.WeakListChangeListener

class CompositeDirtyProperty: DirtyProperty {

    private val items = FXCollections.observableArrayList<DirtyProperty> { arrayOf(it.isDirtyProperty()) }
    private val _dirtyStateProperty = SimpleBooleanProperty()

    init {
        items.addListener(
                WeakListChangeListener<DirtyProperty> (
                        ListChangeListener<DirtyProperty> { _ ->
                            _dirtyStateProperty.set(
                                    items.any { it.isDirty }
                            )
                        }
                )
        )
    }

    /**
     * Indicates if any tracked DirtyProperties are dirty
     */
    override fun isDirtyProperty(): ObservableValue<Boolean> = _dirtyStateProperty

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

}