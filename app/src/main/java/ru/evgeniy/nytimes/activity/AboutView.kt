package ru.evgeniy.nytimes.activity

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.annotations.NonNull
import javax.annotation.Nonnull

interface AboutView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun sendMessage(@NonNull intent: Intent)

    @StateStrategyType(SkipStrategy::class)
    fun openLink(@Nonnull link:String)

    fun showMessage(message:String)

}