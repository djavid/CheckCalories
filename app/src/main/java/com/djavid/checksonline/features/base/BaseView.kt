package com.djavid.checksonline.features.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@Deprecated("")
interface BaseView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorMessage(message: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)


    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToastyError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToastyWarning(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToastySuccess(message: String)

}