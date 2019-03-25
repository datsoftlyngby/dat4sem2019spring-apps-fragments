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
