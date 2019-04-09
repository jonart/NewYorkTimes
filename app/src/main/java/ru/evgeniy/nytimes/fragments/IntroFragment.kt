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
    private val SCREEN_NUMBER = "SCREEN_NUMBER"
    var screenId = 0
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
        if (arguments != null){
            screenId = arguments!!.getInt(SCREEN_NUMBER)
            setImage()
        }
    }

    private fun setImage(){
        when(screenId){
            1 -> image.setImageResource(R.drawable.device)
            2 -> image.setImageResource(R.drawable.device_edit)
            3 -> image.setImageResource(R.drawable.device_read)
            else -> image.setImageResource(R.drawable.notavailable)
        }
    }

}
