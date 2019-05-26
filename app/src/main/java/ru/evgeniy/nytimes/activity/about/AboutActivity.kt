package ru.evgeniy.nytimes.activity.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.linear.*
import ru.evgeniy.nytimes.R

class AboutActivity : MvpAppCompatActivity(), AboutView {

    companion object {
        const val EMAIL = "jonart2008@gmail.com"
    }

    @InjectPresenter
    lateinit var aboutPresenter: AboutPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        ib_telegram.setOnClickListener {
            aboutPresenter.clickTelegram(getString(R.string.telegram))
        }
        ib_vk.setOnClickListener {
            aboutPresenter.clickVk(getString(R.string.vk))
        }
        ib_github.setOnClickListener {
            aboutPresenter.clickGit(getString(R.string.github))
        }
        btn_send_comment.setOnClickListener {
            if (et_message.toString().isNotEmpty())aboutPresenter.sendClicked(et_message.toString(),this)
        }
        createTextView()
    }

    override fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showMessage(getString(R.string.no_apps_browser))
        }
    }

    override fun sendMessage(intent:Intent) {
            startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)))
    }

    override fun showMessage(message: String) {
        Snackbar.make(linear_layout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun createTextView() {
        val tv = TextView(this)
        tv.setText(R.string.disclaimer)
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))

        val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        tv.layoutParams = layoutParams
        tv.gravity = Gravity.CENTER
        linear_layout.addView(tv)
    }
}
