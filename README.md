# Activities with Fragments

Fragments are, as Activities, code with some layout attached.
A fragment has to live inside an activity,
but the same definition can be used in several activities.

When you create a fragment you are asked if you want to create an XML layout,
good idea.
You can also include factory methods and interface callbacks,
wait with that.

![New Fragment](new-fragment.png)

Change the name to something useful.

## Static fragments

When you are developing apps
that work in different contexts,
as landscape and portrait mode,
it is convenient to reuse not only the code,
but also the layout.

After running the wizard and cleaning the code up a bit we have:

```kotlin
package dk.cphbusiness.applicationwithfragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup


class FirstFragment : Fragment() {

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
      ) = inflater.inflate(R.layout.fragment_first, container, false)

  }
```

and

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FirstFragment"
    >

  <TextView
    android:id="@+id/first_label"
    android:text="@string/first_text"
    android:textAlignment="center"
    android:textSize="40pt"
    android:background="@color/first_color"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />

</FrameLayout>
```

for each fragment.

To reuse them in the two `activity_static_fragments` layout files
(one for each orientation)
we just have to write (for portrait):

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    tools:context=".StaticFragmentsActivity"
    >
  <fragment
      android:id="@+id/first_fragment"
      android:name="dk.cphbusiness.applicationwithfragments.FirstFragment"
      android:layout_width="fill_parent"
      android:layout_height="0dp"
      android:layout_weight="1"
      />
  <fragment
      android:id="@+id/second_fragment"
      android:name="dk.cphbusiness.applicationwithfragments.SecondFragment"
      android:layout_width="fill_parent"
      android:layout_height="0dp"
      android:layout_weight="1"
      />
  <TextView
      android:id="@+id/textView"
      android:text="@string/go_landscape_text"
      android:textAlignment="center"
      android:layout_width="fill_parent"
      android:layout_height="wrap_content"
      />
</LinearLayout>
```

and (for landscape):

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    tools:context=".StaticFragmentsActivity"
    >
  <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="0dp"
      android:layout_weight="1"
      >
    <fragment
        android:id="@+id/first_fragment"
        android:name="dk.cphbusiness.applicationwithfragments.FirstFragment"
        android:layout_height="match_parent"
        android:layout_width="0dp"
        android:layout_weight="1"
        />
    <fragment
        android:id="@+id/second_fragment"
        android:name="dk.cphbusiness.applicationwithfragments.SecondFragment"
        android:layout_height="match_parent"
        android:layout_width="0dp"
        android:layout_weight="1"
        />
  </LinearLayout>
  <TextView
      android:id="@+id/textView"
      android:text="@string/go_portrait_text"
      android:textAlignment="center"
      android:layout_width="fill_parent"
      android:layout_height="wrap_content"
      />
</LinearLayout>
```

I have used `LinearLayout`s for less xml in the example,
but `ConstraintLayout`s works fine as well.

## Dynamic Fragments

Fragments can also be used to change part of (or all af) the GUI
depending on data.

The fragments are defined the same way as for the static use,
but the layout of the hosting activity will define a `ViewGroup`
in which to put the fragment when initialized.

In this case it is a `FrameLayout`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".DynamicFragmentsActivity">
  <FrameLayout
      android:layout_width="0dp"
      android:layout_height="0dp"
      app:layout_constraintTop_toTopOf="parent"
      android:id="@+id/toggle_frame"
      app:layout_constraintBottom_toTopOf="@+id/toggle_button"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintEnd_toEndOf="parent"
      android:layout_marginStart="8dp"
      android:layout_marginEnd="8dp">
  </FrameLayout>
  <Button
      android:id="@+id/toggle_button"
      android:text="Toggle Fragments"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginEnd="8dp"
      android:layout_marginStart="8dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintTop_toBottomOf="@+id/toggle_frame"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      />
</android.support.constraint.ConstraintLayout>
```

```kotlin
package dk.cphbusiness.applicationwithfragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic_fragments.*

class DynamicFragmentsActivity : AppCompatActivity() {
  val fragments = arrayOf(
    FirstFragment(), SecondFragment(), ThirdFragment()
    )
  var nextIndex = 0
  val fragmentManager = supportFragmentManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dynamic_fragments)
    toggleFragment() // Shows the first fragment
    toggle_button.setOnClickListener {
      toggleFragment()
      }
    }

  fun toggleFragment() {
    fragmentManager.beginTransaction()
        .replace(R.id.toggle_frame, fragments[nextIndex])
        .commit()
    nextIndex = (nextIndex + 1)%3
    }

  }
```

Note that:
* You should use `supportFragmentManager` (Java: `getSupportFragmentManager()`) and
  not `fragmentManager` (Java: `getFragmentManager()`),
  the later is deprecated and dependent on local implementations.
