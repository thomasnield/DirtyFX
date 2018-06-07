package org.nield.dirtyfx.tracking;

import org.junit.Assert;
import org.junit.Test;
import org.nield.dirtyfx.beans.DirtyObjectProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;

import java.util.Arrays;

public class CompositeDirtyPropertyTest {

    @Test
    public void compositeDirtyPropertyTest() {

        CompositeDirtyProperty composite = new CompositeDirtyProperty();

        DirtyObjectProperty<Integer> property1 = new DirtyObjectProperty<>(3);
        DirtyObjectProperty<Integer> property2 = new DirtyObjectProperty<>(2);
        DirtyObservableList<String> list1 = new DirtyObservableList<>(Arrays.asList("Alpha","Beta","Gamma"));
        DirtyObservableList<String> list2 = new DirtyObservableList<>(Arrays.asList("Zeta","Theta","Eta"));

        composite.addAll(property1,property2,list1,list2);
        Assert.assertFalse(composite.isDirty());

        property1.setValue(3);
        Assert.assertFalse(composite.isDirty());

        property1.setValue(4);
        Assert.assertTrue(composite.isDirty());

        composite.reset();
        Assert.assertEquals(3, (int) property1.getValue());
        Assert.assertFalse(composite.isDirty());

        list1.add("Delta");
        Assert.assertTrue(composite.isDirty());

        composite.rebaseline();
        Assert.assertFalse(composite.isDirty());
        Assert.assertArrayEquals(list1.toArray(), new String[] {"Alpha", "Beta", "Gamma", "Delta"});
    }
}
