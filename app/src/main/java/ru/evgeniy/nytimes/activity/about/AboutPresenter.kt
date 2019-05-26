package ru.evgeniy.nytimes.activity.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import ru.evgeniy.nytimes.R
import ru.evgeniy.nytimes.screens.BasePresenter

@InjectViewState
class AboutPresenter: BasePresenter<AboutView>() {

    fun sendClicked(message:String, context:Context){
        if(message.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(AboutActivity.EMAIL))
            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.Subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
        if (isEmailInstalled(context)) {
            viewState.sendMessage(intent)
        } else {
            viewState.showMessage(R.string.no_apps_email.toString())
        }
        }
        else viewState.showMessage("Empty")
    }

    fun openLink(link: String) {
        viewState.openLink(link)
    }

    fun clickVk(link: String){
        openLink(link)
    }

    fun clickTelegram(link: String){
        openLink(link)
    }

    fun clickGit(link: String){
        openLink(link)
    }

        private fun isEmailInstalled(context: Context): Boolean {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val packageManager = context.packageManager
            val list = packageManager.queryIntentActivities(intent, 0)

            return list.size != 0
        }
}