package com.djavid.checksonline.di

import com.djavid.checksonline.interactors.ChecksInteractor
import com.djavid.checksonline.interactors.QrCodeInteractor
import com.djavid.checksonline.interactors.StatsInteractor
import com.djavid.checksonline.interactors.TokenInteractor
import com.djavid.checksonline.model.networking.apis.BaseApi
import com.djavid.checksonline.model.networking.apis.FnsApi
import com.djavid.checksonline.model.repositories.BaseRepository
import com.djavid.checksonline.model.repositories.FnsRepository
import com.djavid.checksonline.model.repositories.MockRepository
import com.djavid.checksonline.utils.MODULE_NETWORK
import com.djavid.checksonline.utils.TAG_BASE_URL
import com.djavid.checksonline.utils.TAG_GSON
import com.djavid.checksonline.utils.TAG_RETROFIT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
	val kodein = Kodein.Module(MODULE_NETWORK) {
		
		bind<OkHttpClient>() with singleton {
			val logging = HttpLoggingInterceptor()
			logging.level = HttpLoggingInterceptor.Level.BODY
			
			OkHttpClient.Builder().addInterceptor(logging).build()
		}
		
		bind<Gson>(TAG_GSON) with singleton { GsonBuilder().setDateFormat("yyyy-mm-dd HH:mm:ss").create() }
		
		bind<Converter.Factory>() with singleton { GsonConverterFactory.create(instance(TAG_GSON)) }
		
		bind<Retrofit>(TAG_RETROFIT) with singleton {
			Retrofit.Builder()
					.addConverterFactory(instance())
					.baseUrl(instance(TAG_BASE_URL) as String)
					.client(instance())
					.build()
		}
		
		bind<BaseApi>() with singleton { (instance(TAG_RETROFIT) as Retrofit).create(BaseApi::class.java) }
		bind<FnsApi>() with singleton { (instance(TAG_RETROFIT) as Retrofit).create(FnsApi::class.java) }
		
		bind<BaseRepository>() with singleton { BaseRepository(instance()) }
		bind<FnsRepository>() with singleton { FnsRepository(instance()) }
		bind<MockRepository>() with singleton { MockRepository(instance()) }
		
		bind<ChecksInteractor>() with singleton { ChecksInteractor(instance(), instance()) }
		bind<QrCodeInteractor>() with singleton { QrCodeInteractor(instance(), instance(), instance()) }
		bind<StatsInteractor>() with singleton { StatsInteractor(instance(), instance()) }
		bind<TokenInteractor>() with singleton { TokenInteractor(instance(), instance()) }
	}
}