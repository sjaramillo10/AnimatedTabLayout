# AnimatedTabLayout
A nice Animated TabLayout for Android

### Demo

![AnimatedTabLayout](https://i.imgur.com/OSbTWV3.gif)


### Usage

#### Gradle

```groovy
dependencies {
   implementation 'com.github.sjaramillo10:AnimatedTabLayout:v1.0.1'
}
```

Use it in your layouts:

```xml
	<com.sjaramillo10.animatedtablayout.AnimatedTabLayout
        android:id="@+id/animatedTabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary" />
```

You can change some attributes, these are the default values:

```xml
	<com.sjaramillo10.animatedtablayout.AnimatedTabLayout
        android:id="@+id/animatedTabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary"
        app:animationDuration="500"
        app:selectedTabTextColor="@color/white"
        app:unselectedTabTextColor="@color/semiTransparentWhite"
        app:smallTextSize="16sp"
        app:bigTextSize="20sp"/>
```

Depending on the number of tabs you may want to use the `scrollable` attribute:

`app:tabMode="scrollable"`

For this release I am extending from the standard *TabLayout* and therefore all of its attributes are available for the *AnimatedTabLayout*, however as I am adding a custom view to each tab some attributes like the following will have no effect:

`app:tabTextColor`
`app:tabSelectedTextColor`
`app:tabIndicatorColor`
`app:tabIndicatorHeight`
`app:tabTextAppearance`

I have some improvements in mind, stay tuned.
