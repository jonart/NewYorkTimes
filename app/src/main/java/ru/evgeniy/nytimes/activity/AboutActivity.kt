package ru.evgeniy.nytimes.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import ru.evgeniy.nytimes.R

class AboutActivity : AppCompatActivity() {

    private var mLinearLayout: LinearLayout? = null
    private var mImageButtonTelegram: ImageButton? = null
    private var mImageButtonVk: ImageButton? = null
    private var mImageButtonGit: ImageButton? = null
    private var mButtonSendComment: Button? = null
    private var mEditTextMessage: EditText? = null


    private val isEmpty: Boolean
        get() = !mEditTextMessage!!.text.toString().isEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        init()

        mImageButtonTelegram!!.setOnClickListener { openLink(getString(R.string.telegram)) }
        mImageButtonVk!!.setOnClickListener { openLink(getString(R.string.vk)) }
        mImageButtonGit!!.setOnClickListener { openLink(getString(R.string.github)) }
        mButtonSendComment!!.setOnClickListener { if (isEmpty) sendMessage() }
        createTextView()
    }

    private fun init() {
        mImageButtonTelegram = findViewById(R.id.ib_telegram)
        mImageButtonVk = findViewById(R.id.ib_vk)
        mImageButtonGit = findViewById(R.id.ib_github)
        mButtonSendComment = findViewById(R.id.btn_send_comment)
        mLinearLayout = findViewById(R.id.my_linear)
        mEditTextMessage = findViewById(R.id.et_message)
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showMessage(getString(R.string.no_apps_browser))
        }
    }

    private fun sendMessage() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_send)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject))
        intent.putExtra(Intent.EXTRA_TEXT, mEditTextMessage!!.text.toString())
        if (isEmailInstalled(applicationContext)) {
            startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)))
        } else {
            showMessage(getString(R.string.no_apps_email))
        }
    }

    private fun showMessage(msg: String) {
        Snackbar.make(this.mLinearLayout!!, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun isEmailInstalled(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent, 0)

        return list.size != 0
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
        mLinearLayout!!.addView(tv)
    }
}
