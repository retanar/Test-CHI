package retanar.test.chi.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://shibe.online/api/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ShibeApiService {
    @GET("shibes?count=10&urls=true&httpsUrls=true")
    suspend fun getShibes(): Response<List<String>>
}

val shibeApiService by lazy {
    retrofit.create(ShibeApiService::class.java)
}