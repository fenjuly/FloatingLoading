##FloatingLoading

---
This is a simple view that can be used into loading, refresh or wait. This is a copycat just like this [https://dribbble.com/shots/2031906-Loading](https://dribbble.com/shots/2031906-Loading).


 
###ScreenShot

![Imgur](http://i.imgur.com/PeY5nZl.gif)


###Usage

* **XML**
 
  for every point in the view, you can set a color. The attributes are `loading_large_color`, `loading_middle_color`, `loading_small_color`, `loading_special_color`.
  
```xml
<com.fenjuly.mylibrary.FloatingLoading
        android:id="@+id/sampple"
        android:layout_width="100dp"
        android:layout_height="25dp"
        custom:loading_large_color="#FFE7BA"
        custom:loading_middle_color="#CDBA96"
        custom:loading_small_color="#8B7E66"
        />
```

* **Change Colors**

if you want to change every point's color dynamically. 
 
 * ```public void setPointColor(int position, int color)```
 * ```public void setPointsColor(int[] color_array)```


```java
final FloatingLoading v1 = (FloatingLoading) findViewById(R.id.sampple1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.setPointColor(0, Color.GREEN);
                v1.setDuration(2000);
            }
        });
 ```
 
* **Change Refresh Rate**

and there also offered a method you can set the refresh rate.
  
  ``` public void setDuration(int duration)```         
	 
  

###Other
All rights `unreserved`!

I'll be very glad that somebody use this shit code.




