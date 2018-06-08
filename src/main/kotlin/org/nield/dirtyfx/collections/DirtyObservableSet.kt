package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import javafx.collections.WeakSetChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableSet<T> private constructor(originalSet: Set<T> = setOf(),
                                                val currentSet: ObservableSet<T>): DirtyProperty, ObservableSet<T> by currentSet {

    constructor(originalSet: Set<T>): this(originalSet, FXCollections.observableSet(HashSet<T>(originalSet)))
    constructor(vararg items: T): this(items.toSet())

    private val originalSet = FXCollections.observableSet(HashSet<T>(originalSet))
    private val _isDirtyProperty = SimpleBooleanProperty()

    init {
        addListener(
                WeakSetChangeListener<T>(
                        SetChangeListener<T> {
                            _isDirtyProperty.set(originalSet != this)
                        }
                )
        )
    }

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
    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    val baselineSet get() = FXCollections.unmodifiableObservableSet(originalSet)

    override fun equals(other: Any?) = currentSet == other
    override fun hashCode() = currentSet.hashCode()
}
