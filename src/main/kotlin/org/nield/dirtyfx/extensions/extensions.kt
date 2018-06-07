package org.nield.dirtyfx.extensions

import org.nield.dirtyfx.tracking.CompositeDirtyProperty
import org.nield.dirtyfx.tracking.DirtyProperty

fun DirtyProperty.addTo(compositeDirtyProperty: CompositeDirtyProperty): DirtyProperty {
    compositeDirtyProperty.add(this)
    return this
}