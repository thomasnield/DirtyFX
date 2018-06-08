package org.nield.dirtyfx.collections


import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.collections.WeakListChangeListener
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyObservableList<T> private constructor(_originalList: List<T> = listOf(),
                                                 val currentList: ObservableList<T>):
        ObservableList<T> by currentList, DirtyProperty {

    constructor(originalList: List<T>): this(originalList,FXCollections.observableArrayList<T>(originalList))
    constructor(vararg items: T):  this(items.toList())

    private val _originalList = FXCollections.observableArrayList(_originalList)
    private val _isDirtyProperty = SimpleBooleanProperty()

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
    init {
        addListener(
                WeakListChangeListener<T> (
                        ListChangeListener<T> { _ ->
                            _isDirtyProperty.set(_originalList != this)
                        }
                )
        )
    }

    val originalList get() = FXCollections.unmodifiableObservableList(_originalList)

    override fun isDirtyProperty(): ObservableValue<Boolean> = _isDirtyProperty
    override val isDirty get() = _isDirtyProperty.get()

    override fun hashCode() = currentList.hashCode()
    override fun equals(other: Any?) = currentList == other
}
