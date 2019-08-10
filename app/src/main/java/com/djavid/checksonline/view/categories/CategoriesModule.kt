package com.djavid.checksonline.view.categories

import android.view.View
import kotlinx.android.synthetic.main.fragment_categories.*

@Module(includes = [Bindings::class])
class CategoriesModule {

    @Provides
    fun provideCategoriesViewRoot(fragment: CategoriesFragment): View = fragment.categoriesFragment

}

@Module
interface Bindings {

    @Binds
    fun bindCategoriesPresenter(impl: CategoriesPresenter): CategoriesContract.Presenter

    @Binds
    fun bindCategoriesView(view: CategoriesView): CategoriesContract.View

}