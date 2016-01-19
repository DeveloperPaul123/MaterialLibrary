# MaterialLibrary

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialLibrary-blue.svg?style=flat)](http://android-arsenal.com/details/1/2594)

[![](https://jitpack.io/v/DeveloperPaul123/MaterialLibrary.svg)](https://jitpack.io/#DeveloperPaul123/MaterialLibrary)

This is a simple library that provides some unique components for aiding in making apps comply with the material design guidelines. (Please note that this Readme is a work in progress as there are a lot of components in this library.)

<h2>Motivation</h2>
I know what you're thinking: "Another material design library? Why?" Well, this library actually started as something I wanted to create to have some unique and cool looking floating action button menus to use in my apps that were still up to material design specs. In the process of making these menus I ended up making a lot of other material design components and a lot of really useful, easily injectable utilities to use in other custom views that others may create, so I decided that it would be great to share this work with others. I got my inspiration for the material design menus from materialup.com (great website btw, you should check it out) and will be adding more menus over time. If you have any suggestions for a menu you'd like to see implemented and have a link for me to look at, shoot me an email and I'll try to add it. If you use this library in your app, please let me know! I'd be happy to make a list and post it on this readme. 

<h2>How to use</h2>
Add the following lines to you top level build.gradle

````java
allprojects {
    repositories {
    ...
    maven {url "https://jitpack.io"}
    }
}
````
Then add the following to your apps build.gradle

````java

dependencies {
    ....
    compile 'com.github.DeveloperPaul123:MaterialLibrary:1.0.5'
}
````
<h2>Video Demo</h2>
https://youtu.be/miLUyFCyxZw

<h2>Components</h2>

#### Note 
* Please note that a more in depth look at all the components and instructions on how to use them will be added to the [wiki](https://github.com/DeveloperPaul123/MaterialLibrary/wiki) for this repository. This will make it much easier to organize (instead of having a really, really long README). 
<h3>Buttons</h3>
- Material Floating Action Button
    - Normal
            
- Material Flat Button
Examples:

````xml
    //default
   <com.devpaul.materiallibrary.views.MaterialFlatButton
            android:id="@+id/activity_button_default_flat_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@id/activity_button_title_one"
            android:layout_weight="1"/>
    //truly flat
    <com.devpaul.materiallibrary.views.MaterialFlatButton
        android:id="@+id/activity_button_agree_flat_button"
        android:layout_toRightOf="@id/activity_button_default_flat_button"
        android:layout_below="@id/activity_button_title_one"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:mat_flat_button_color="#303F9F"
        app:mat_flat_button_is_flat="true"
        app:mat_flat_button_text="Agree"
        android:layout_weight="1"/>
    //not flat
    <com.devpaul.materiallibrary.views.MaterialFlatButton
        android:id="@+id/activity_button_cancel_flat_button"
        android:layout_toRightOf="@id/activity_button_agree_flat_button"
        android:layout_below="@id/activity_button_title_one"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:mat_flat_button_color="#F44336"
        app:mat_flat_button_is_flat="false"
        app:mat_flat_button_text="Cancel"
        android:layout_weight="1"/>
````

<h3>Menus</h3>
- Material Circular FAB Menu
- FAB Linear Menu

<h3>Utilities</h3>
- Color Generator
- Shadow Generator
- Ripple Generator
- ShadowRippleGenerator
- SelectorShadowGenerator

<h3>Abstract Classes</h3>
- BaseToolbarActivity

<h2>Usage</h2>

<h2>License</h2>

Copyright 2015 Paul T

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
