package org.nield.dirtyfx.collections

import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import javafx.collections.WeakSetChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableSet<T> private constructor(_originalSet: Set<T> = setOf(),
                                                val currentSet: ObservableSet<T>): DirtyProperty, ObservableSet<T> by currentSet {

    constructor(originalSet: Set<T>): this(originalSet, FXCollections.observableSet(HashSet<T>(originalSet)))
    constructor(vararg items: T): this(items.toSet())

    private val _originalSet = FXCollections.observableSet(HashSet<T>(_originalSet))
    private val _isDirtyProperty = SimpleBooleanProperty()

    private val listener = SetChangeListener<T> {
        _isDirtyProperty.set(_originalSet != this)
    }

    init {
        addListener(WeakSetChangeListener(listener))
    }

    override fun rebaseline() {
        _originalSet.clear()
        _originalSet.addAll(this)
        _isDirtyProperty.set(false)
    }
    override fun reset() {
        clear()
        addAll(_originalSet)
        _isDirtyProperty.set(false)
    }
    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    val originalSet get() = FXCollections.unmodifiableObservableSet(_originalSet)

    override fun equals(other: Any?) = currentSet == other
    override fun hashCode() = currentSet.hashCode()
}
