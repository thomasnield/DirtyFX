package org.nield.dirtyfx.beans

import javafx.beans.InvalidationListener
import javafx.beans.property.*
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.beans.value.WeakChangeListener
import javafx.collections.FXCollections
import javafx.collections.MapChangeListener
import javafx.collections.ObservableMap
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyMapProperty<K,V> constructor(originalMap: Map<K,V>): DirtyProperty, MapProperty<K, V>() {

    private val _originalMap = FXCollections.observableMap(originalMap)
    private val _isDirtyProperty = SimpleBooleanProperty()
    private val backingMap = SimpleMapProperty(FXCollections.observableMap(originalMap))

    private val listener = ChangeListener<Map<K,V>> { _,_,_ ->
        _isDirtyProperty.set(_originalMap != this)
    }

    init {
        addListener(WeakChangeListener(listener))
    }

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

    val originalMap get() = FXCollections.unmodifiableObservableMap(_originalMap)

    override fun isDirtyProperty(): ReadOnlyBooleanProperty = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun hashCode() = backingMap.hashCode()
    override fun equals(other: Any?) = backingMap.equals(other)


    override fun addListener(listener: ChangeListener<in ObservableMap<K, V>>?) = backingMap.addListener(listener)

    override fun addListener(listener: InvalidationListener?) = backingMap.addListener(listener)

    override fun addListener(listener: MapChangeListener<in K, in V>?) = backingMap.addListener(listener)

    override fun removeListener(listener: ChangeListener<in ObservableMap<K, V>>?) = backingMap.removeListener(listener)

    override fun removeListener(listener: InvalidationListener?) = backingMap.removeListener(listener)

    override fun removeListener(listener: MapChangeListener<in K, in V>?) = backingMap.removeListener(listener)

    override fun getBean() = backingMap.bean

    override fun isBound() = backingMap.isBound

    override fun emptyProperty() = backingMap.emptyProperty()

    override fun sizeProperty() = backingMap.sizeProperty()

    override fun getName() = backingMap.name

    override fun get() = backingMap.get()

    override fun set(value: ObservableMap<K, V>?) = backingMap.set(value)

    override fun unbind() = backingMap.unbind()

    override fun bind(observable: ObservableValue<out ObservableMap<K, V>>?) = backingMap.bind(observable)
}
