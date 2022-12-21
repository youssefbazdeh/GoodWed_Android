package tn.esprit.wedding.retrofit

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.wedding.PREF_LOGIN
import tn.esprit.wedding.TOKEN


class OAuthInterceptor(context: Context): Interceptor {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        val token = prefs.getString(TOKEN,"")
        Log.i("token",  token.toString())
        request = request.newBuilder().header("Authorization", "Bearer $token").build()

        return chain.proceed(request)
    }
}

class ApiClient {
    companion object{
        val URL ="http://192.168.234.33:9092/"
        //val URL="https://moody-kiwis-wear-196-203-207-178.loca.lt"
        var retrofitToken: Retrofit? = null
        fun getApiClientWithToken(context: Context): Retrofit? {
            if (retrofitToken == null) {
                val client =  OkHttpClient.Builder()
                    .addInterceptor(OAuthInterceptor(context))
                    .build()
                retrofitToken = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitToken
        }

        var retrofit: Retrofit? = null
        fun getApiClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    }
}