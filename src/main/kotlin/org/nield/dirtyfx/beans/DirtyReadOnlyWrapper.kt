package org.nield.dirtyfx.beans

import javafx.beans.property.ReadOnlyBooleanWrapper
import org.nield.dirtyfx.tracking.DirtyProperty

class DirtyReadOnlyWrapper(override val isDirty: Boolean): DirtyProperty {

    override fun isDirtyProperty() = ReadOnlyBooleanWrapper(isDirty)

    override fun rebaseline() {
        // do nothing
    }

    override fun reset() {
        // do nothing
    }
}
