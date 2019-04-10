package ru.evgeniy.nytimes.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.evgeniy.nytimes.R
import kotlinx.android.synthetic.main.fragment_intro.*


class IntroFragment : Fragment() {
companion object {
    private const val SCREEN_NUMBER = "SCREEN_NUMBER"
}

    fun newInstance(screenNumber:Int): Fragment{
        val fragment = IntroFragment()
        val bundle = Bundle()
        bundle.putInt(SCREEN_NUMBER, screenNumber)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            arguments?.apply {
                val screenId = getInt(SCREEN_NUMBER)
                image.setImageResource(screenId)
            }
    }

}
