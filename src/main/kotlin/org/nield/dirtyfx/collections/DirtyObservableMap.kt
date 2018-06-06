package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.*

class DirtyObservableMap<K,V>(originalMap: Map<K,V> = mapOf()):
        ObservableMap<K,V> by FXCollections.observableMap(HashMap<K,V>(originalMap)) {

    private val originalMap = FXCollections.observableMap(HashMap<K,V>(originalMap))
    private val _isDirtyProperty = SimpleBooleanProperty()

    fun rebaseline() {
        originalMap.clear()
        forEach { k, v -> originalMap[k] = v }
        _isDirtyProperty.set(false)
    }
    fun reset() {
        clear()
        originalMap.forEach { k, v -> set(k,v) }
        _isDirtyProperty.set(false)
    }
    init {
        addListener(
                WeakMapChangeListener<K,V>(
                    MapChangeListener<K,V> {
                        _isDirtyProperty.set(originalMap != this)
                    }
                )
        )
    }
    fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    val isDirty get() = _isDirtyProperty.get()
}
