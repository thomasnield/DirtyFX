package org.nield.dirtyfx.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DirtyObservableListTest {

    @Test
    public void testDirtyObservableList() {
            List<String> source = Arrays.asList("Alpha","Beta","Gamma");

            DirtyObservableList<String> myList = new DirtyObservableList<>(source);

            Assert.assertFalse(myList.isDirty());

            myList.add("Delta");

            Assert.assertTrue(myList.isDirty());

            myList.rebaseline();

            Assert.assertFalse(myList.isDirty());

            myList.add("Epsilon");
            myList.reset();

            Assert.assertEquals(Arrays.asList("Alpha", "Beta", "Gamma", "Delta"), myList);
        }
}
