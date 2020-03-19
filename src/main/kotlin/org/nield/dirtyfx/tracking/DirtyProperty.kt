package org.nield.dirtyfx.tracking

import javafx.beans.property.ReadOnlyBooleanProperty

interface DirtyProperty {
    fun isDirtyProperty(): ReadOnlyBooleanProperty
    val isDirty: Boolean
    fun rebaseline()
    fun reset()
}