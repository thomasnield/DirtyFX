![](logo.jpg)


### Dirty state tracking properties and collections for JavaFX

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
```

Each of these dirty-tracking components have the same behaviors as their vanilla JavaFX counterparts, but have three additional methods/properties:

|Property/Method|Description|
|----|----|
|isDirtyProperty()|Read-only ObservableValue indicating whether this item is dirty|
|isDirty|Delegates to isDirtyProperty()|
|reset()|Sets the value back to the baseline value, removing dirty state|
|rebaseline()|Sets the baseline value to the current value, resetting dirty state|

Note this was built in Kotlin, but works with both Java and Kotlin.

### Example

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

### Dependencies

**Maven**

```xml 
<dependency>
    <groupId>org.nield</groupId>
    <artifactId>dirtyfx</artifactId>
    <version>0.0.1</version>
</dependency>
```

**Gradle**

```groovy
compile 'org.nield:dirtyfx:0.0.1'
```
