package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.MapChangeListener
import javafx.collections.ObservableMap
import javafx.collections.WeakMapChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableMap<K,V> private constructor(_originalMap: Map<K,V>,
                                                  val currentMap: ObservableMap<K,V>):
        ObservableMap<K,V> by currentMap, DirtyProperty {

    private val _originalMap = FXCollections.observableMap(_originalMap)
    private val _isDirtyProperty = SimpleBooleanProperty()

    constructor(originalMap: Map<K,V> = mapOf()): this(originalMap, FXCollections.observableMap(HashMap<K,V>(originalMap)))

    override fun rebaseline() {
        _originalMap.clear()
        forEach { k, v -> _originalMap[k] = v }
        _isDirtyProperty.set(false)
    }
    override fun reset() {
        clear()
        _originalMap.forEach { k, v -> set(k,v) }
        _isDirtyProperty.set(false)
    }
    init {
        addListener(
                WeakMapChangeListener<K,V>(
                        MapChangeListener<K,V> {
                            _isDirtyProperty.set(_originalMap != this)
                        }
                )
        )
    }
    val originalMap get() = FXCollections.unmodifiableObservableMap(_originalMap)

    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun hashCode() = currentMap.hashCode()
    override fun equals(other: Any?) = currentMap.equals(other)
}
