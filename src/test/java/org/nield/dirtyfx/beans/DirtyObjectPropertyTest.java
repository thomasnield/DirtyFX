package org.nield.dirtyfx.beans;

import org.junit.Assert;
import org.junit.Test;

public class DirtyObjectPropertyTest {

    @Test
    public void dirtyTest() {

        // Initialize with "Hello" being the baseline
        DirtyObjectProperty<String> myProperty = new DirtyObjectProperty<>("Hello");

        // Setting existing value should not change
        myProperty.setValue("Hello");
        Assert.assertTrue(!myProperty.isDirty());

        // Changing the value will result in a dirty state
        myProperty.setValue("World");
        Assert.assertTrue(myProperty.isDirty());

        // Rebaselining will set the current value to be the new baseline
        myProperty.rebaseline();
        Assert.assertTrue(!myProperty.isDirty());

        // Reset will restore the baseline as the current value, and no longer be dirty
        myProperty.setValue("Greetings");
        myProperty.reset();
        Assert.assertEquals("World", myProperty.getValue());
        Assert.assertTrue(!myProperty.isDirty());
    }
}
