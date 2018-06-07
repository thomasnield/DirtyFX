package org.nield.dirtyfx.extensions

import org.nield.dirtyfx.tracking.CompositeDirtyProperty
import org.nield.dirtyfx.tracking.DirtyProperty
import kotlin.reflect.KProperty

fun <P: DirtyProperty> P.addTo(compositeDirtyProperty: CompositeDirtyProperty): P {
    compositeDirtyProperty.add(this)
    return this
}

operator fun CompositeDirtyProperty.getValue(thisRef: Any, property: KProperty<*>) = isDirty
