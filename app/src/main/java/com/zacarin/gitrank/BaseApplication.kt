package com.zacarin.gitrank

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.zacarin.gitrank.di.appComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.io.File
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val SSL_PROTOCOL = "SSL"
const val MAX_CACHE_SIZE = 10L * 1024L * 1024L

/**
 * Base application class.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appComponent)
        }

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(createBuilderWithTrustManager()?.build()))
        val built = builder.build()
        with(built) {
            isLoggingEnabled = true
        }
        Picasso.setSingletonInstance(built)
    }

    private fun createBuilderWithTrustManager(): OkHttpClient.Builder? {
        val builder = OkHttpClient.Builder()
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out java.security.cert.X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out java.security.cert.X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            val sslContext = SSLContext.getInstance(SSL_PROTOCOL).also {
                it.init(null, trustAllCerts, SecureRandom())
            }
            val sslSocketFactory = sslContext.socketFactory
            val cache = Cache(File(cacheDir, "http-cache"), MAX_CACHE_SIZE)
            builder.apply {
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { _: String?, _: SSLSession? -> true}
                cache(cache)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return builder
    }
}