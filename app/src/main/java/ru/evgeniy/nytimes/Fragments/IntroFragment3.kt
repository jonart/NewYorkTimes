package ru.evgeniy.nytimes.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.evgeniy.nytimes.R

class IntroFragment3 : Fragment() {


    fun newInstance(): Fragment{
        return IntroFragment3()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_3, container, false)
    }

}
