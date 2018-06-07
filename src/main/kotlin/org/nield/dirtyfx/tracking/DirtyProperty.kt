package org.nield.dirtyfx.tracking

import javafx.beans.value.ObservableValue

interface DirtyProperty {
    fun isDirtyProperty(): ObservableValue<Boolean>
    val isDirty: Boolean
    fun rebaseline()
    fun reset()
}