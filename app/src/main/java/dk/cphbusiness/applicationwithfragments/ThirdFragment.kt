package dk.cphbusiness.applicationwithfragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup


class ThirdFragment : Fragment() {

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
      ) = inflater.inflate(R.layout.fragment_third, container, false)

  }
