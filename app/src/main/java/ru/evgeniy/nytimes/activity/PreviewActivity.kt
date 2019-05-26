package ru.evgeniy.nytimes.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_preview.*
import ru.evgeniy.nytimes.R

class PreviewActivity : AppCompatActivity() {

    companion object {
        private var EMAIL_KEY = "EMAIL_KEY"
    }
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        text = intent.getStringExtra(EMAIL_KEY)

        tv_previewText!!.text = text

        btn_send?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type = "text/plain"
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_to)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject))
            intent.putExtra(Intent.EXTRA_TEXT, text)
            if (isEmailInstalled(applicationContext)) {
                startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)))
            } else {
                showToast()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this, R.string.no_apps_email, Toast.LENGTH_LONG).show()
    }

    private fun isEmailInstalled(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent, 0)

        return list.size != 0
    }
}
