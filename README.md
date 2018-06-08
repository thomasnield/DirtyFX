![](logo.jpg)


### Dirty state-tracking properties and collections for JavaFX

I built this library out of tremendous need to keep track of dirty states in various JavaFX Properties and Collections, as well as rebaseline and reset against the dirty values.


Here are the current types:

```
DirtyBooleanProperty
DirtyIntegerProperty
DirtyLongProperty
DirtyObjectProperty
DirtyStringProperty
DirtyObservableList
DirtyObservableMap
DirtyObservableSet
CompositeDirtyProperty
```

Each of these dirty-tracking components have the same behaviors as their vanilla JavaFX counterparts, but have three additional methods/properties:

|Property/Method|Description|
|----|----|
|isDirtyProperty()|Read-only ObservableValue indicating whether this item is dirty|
|isDirty|Delegates to isDirtyProperty()|
|reset()|Sets the value back to the baseline value, removing dirty state|
|rebaseline()|Sets the baseline value to the current value, resetting dirty state|

Note this was built in Kotlin, but works with both Java and Kotlin.

### Java Example

```java
// Initialize with "Hello" being the baseline
DirtyObjectProperty<String> myProperty = new DirtyObjectProperty<>("Hello");

// Setting existing value should not change
myProperty.setValue("Hello");
Assert.assertFalse(myProperty.isDirty());

// Changing the value will result in a dirty state
myProperty.setValue("World");
Assert.assertTrue(myProperty.isDirty());

// Rebaselining will set the current value to be the new baseline
myProperty.rebaseline();
Assert.assertFalse(myProperty.isDirty());

// Reset will restore the baseline as the current value, and no longer be dirty
myProperty.setValue("Greetings");
myProperty.reset();
Assert.assertEquals("World", myProperty.getValue());
Assert.assertFalse(myProperty.isDirty());
```

### CompositeDirtyProperty

The `CompositeDirtyProperty` is a powerful utility that can manage dirty properties in sweeping fashion. All the `DirtyXXX` types in this library implement `DirtyProperty`, and the `CompositeDirtyProperty` accepts any number of `DirtyProperty` items and aggregates their state.

It can also `rebaseline()` or `reset()` all the items it is tracking.

```java
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
```

### Dependencies

**Maven**

```xml 
<dependency>
    <groupId>org.nield</groupId>
    <artifactId>dirtyfx</artifactId>
    <version>0.1.0</version>
</dependency>
```

**Gradle**

```groovy
compile 'org.nield:dirtyfx:0.1.0'
```
