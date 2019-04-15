package ru.evgeniy.nytimes.fragments

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {
    var disposable = CompositeDisposable()

    fun disposeAll() {
        disposable.clear()
    }
}