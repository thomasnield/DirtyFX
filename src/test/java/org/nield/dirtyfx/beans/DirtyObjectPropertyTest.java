package org.nield.dirtyfx.beans;

import org.junit.Assert;
import org.junit.Test;

public class DirtyObjectPropertyTest {

    @Test
    public void dirtyTest() {
        DirtyObjectProperty<String> myProperty = new DirtyObjectProperty<>("Hello");
        myProperty.setValue("Hello");
        Assert.assertTrue(!myProperty.isDirty());

        myProperty.setValue("World");
        Assert.assertTrue(myProperty.isDirty());

        myProperty.rebaseline();
        Assert.assertTrue(!myProperty.isDirty());

        myProperty.setValue("Hello");
        myProperty.reset();
        Assert.assertEquals("World", myProperty.getValue());
        Assert.assertTrue(!myProperty.isDirty());
    }
}
