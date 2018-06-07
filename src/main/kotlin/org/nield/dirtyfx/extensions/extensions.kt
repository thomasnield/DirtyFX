package org.nield.dirtyfx.extensions

import org.nield.dirtyfx.tracking.CompositeDirtyProperty
import org.nield.dirtyfx.tracking.DirtyProperty

fun <P: DirtyProperty> P.addTo(compositeDirtyProperty: CompositeDirtyProperty): P {
    compositeDirtyProperty.add(this)
    return this
}