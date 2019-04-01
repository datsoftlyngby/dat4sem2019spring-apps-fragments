package dk.cphbusiness.applicationwithfragments

import android.arch.lifecycle.ViewModel

class ExampleViewModel : ViewModel() {
  private var count = 0

  val message: String
    get() = "Killroy was here ${count++} times"

  }
