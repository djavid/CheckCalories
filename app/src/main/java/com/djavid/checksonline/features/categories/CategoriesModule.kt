package com.djavid.checksonline.features.categories

import android.view.View
import com.djavid.checksonline.features.root.ViewRoot
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_categories.*

@Module(includes = [Bindings::class])
class CategoriesModule {

    @Provides
    @ViewRoot
    fun provideCategoriesViewRoot(fragment: CategoriesFragment): View = fragment.categoriesFragment

}

@Module
interface Bindings {

    @Binds
    fun bindCategoriesPresenter(impl: CategoriesPresenter): CategoriesContract.Presenter

    @Binds
    fun bindCategoriesView(view: CategoriesView): CategoriesContract.View

}