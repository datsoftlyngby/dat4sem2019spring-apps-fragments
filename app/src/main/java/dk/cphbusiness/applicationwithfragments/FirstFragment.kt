package dk.cphbusiness.applicationwithfragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
      ) : View {
    return inflater.inflate(R.layout.fragment_first, container, false)
    }

  override fun onResume() {
    super.onResume()
    val model = ViewModelProviders.of(activity!!).get(ExampleViewModel::class.java)
    message_label.text = model.message
    }

  }
