package n7.myperfectemptyproject.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import n7.myperfectemptyproject.data.source.remote.retrofit.UserApi
import n7.myperfectemptyproject.utils.logPlease
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RetrofitModule {

    private const val randomUserBaseUrl = "https://randomuser.me/"

    @Provides
    @Reusable
    fun provideRandomUser(client: OkHttpClient): UserApi = Retrofit.Builder()
        .baseUrl(randomUserBaseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(UserApi::class.java)

    @Provides
    @Reusable
    fun provideClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logPlease(message)
            }
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }
}
