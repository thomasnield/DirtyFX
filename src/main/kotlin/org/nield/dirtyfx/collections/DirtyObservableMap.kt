package org.nield.dirtyfx.collections

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.MapChangeListener
import javafx.collections.ObservableMap
import javafx.collections.WeakMapChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableMap<K,V>(originalMap: Map<K,V> = mapOf()):
        ObservableMap<K,V> by FXCollections.observableMap(HashMap<K,V>(originalMap)), DirtyProperty {

    private val originalMap = FXCollections.observableMap(HashMap<K,V>(originalMap))
    private val _isDirtyProperty = SimpleBooleanProperty()

    override fun rebaseline() {
        originalMap.clear()
        forEach { k, v -> originalMap[k] = v }
        _isDirtyProperty.set(false)
    }
    override fun reset() {
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
    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()
}
