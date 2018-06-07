package org.nield.dirtyfx.tracking;

import org.junit.Assert;
import org.junit.Test;
import org.nield.dirtyfx.beans.DirtyObjectProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;

import java.util.Arrays;

public class MutliDirtyTrackerTest {

    @Test
    public void multiDirtyTrackerTest() {

        CompositeDirtyProperty manager = new CompositeDirtyProperty();

        DirtyObjectProperty<Integer> property1 = new DirtyObjectProperty<>(3);
        DirtyObjectProperty<Integer> property2 = new DirtyObjectProperty<>(2);
        DirtyObservableList<String> list1 = new DirtyObservableList<>(Arrays.asList("Alpha","Beta","Gamma"));
        DirtyObservableList<String> list2 = new DirtyObservableList<>(Arrays.asList("Zeta","Theta","Eta"));

        manager.addAll(property1,property2,list1,list2);
        Assert.assertFalse(manager.isDirty());

        property1.setValue(3);
        Assert.assertFalse(manager.isDirty());

        property1.setValue(4);
        Assert.assertTrue(manager.isDirty());

        manager.reset();
        Assert.assertEquals(3, (int) property1.getValue());
        Assert.assertFalse(manager.isDirty());

        list1.add("Delta");
        Assert.assertTrue(manager.isDirty());

        manager.rebaseline();
        Assert.assertFalse(manager.isDirty());
        Assert.assertArrayEquals(list1.toArray(), new String[] {"Alpha", "Beta", "Gamma", "Delta"});
    }
}
