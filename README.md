# MaterialLibrary
This is a simple library that provides some unique components for aiding in making apps comply with the material design guidelines.

<h2>Components</h2>
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
