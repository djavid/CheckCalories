package com.djavid.checksonline.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.djavid.checksonline.BuildConfig
import com.djavid.checksonline.base.CancelableCoroutineScope
import com.djavid.checksonline.utils.DrawableGenerator
import com.djavid.checksonline.utils.MODULE_APP
import com.djavid.checksonline.utils.NAME_SHARED_PREFS
import com.djavid.checksonline.utils.SavedPreferences
import com.djavid.checksonline.utils.TAG_BASE_URL
import com.djavid.checksonline.utils.TAG_CICERONE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class AppModule(application: Application) {
	val kodein = Kodein.Module(MODULE_APP) {
		
		bind<String>(TAG_BASE_URL) with singleton { BuildConfig.BASE_URL }
		
		bind<DecimalFormatSymbols>() with singleton {
			DecimalFormatSymbols.getInstance().apply { groupingSeparator = ' ' }
		}
		bind<DecimalFormat>() with singleton {
			//			val symbols = DecimalFormatSymbols.getInstance()
//			symbols.groupingSeparator = ' '
			
			DecimalFormat("###,###", instance())
		}
		
		val scope = CancelableCoroutineScope(Dispatchers.Main)
		bind<CoroutineScope>() with singleton { scope }
		
		
		bind<SavedPreferences>() with singleton {
			val prefs = application.applicationContext
					.getSharedPreferences(NAME_SHARED_PREFS, Context.MODE_PRIVATE)
			SavedPreferences(prefs)
		}
		
		bind<Cicerone<Router>>(TAG_CICERONE) with singleton { Cicerone.create() }
		
		bind<NavigatorHolder>() with singleton { (instance(TAG_CICERONE) as Cicerone<Router>).navigatorHolder }
		
		bind<Router>() with singleton { (instance(TAG_CICERONE) as Cicerone<Router>).router }
		
		bind<Resources>() with singleton { application.resources }
		
		bind<DrawableGenerator>() with singleton { DrawableGenerator() }
		
	}
}