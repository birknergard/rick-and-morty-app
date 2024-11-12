package com.example.exam.data

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.Episode
import com.example.exam.dataClasses.EpisodeData
import com.example.exam.dataClasses.Info
import com.example.exam.dataClasses.Location
import com.example.exam.dataClasses.LocationFull
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class RetrofitInstance{
    private val _url = "https://rickandmortyapi.com/"
    private val _httpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val _retrofit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Stores the highest page count of the current api call.
    private var _charactersMaxPage = MutableStateFlow<Int>(43)

    private val _rickAndMortyApiService = _retrofit.create(RickAndMortyApiService::class.java)

    suspend fun getAllCharactersFromApi(page : Int) : Pair<List<Character>, Boolean> {
        try {
            val output : Pair<List<Character>, Boolean>
            val response = _rickAndMortyApiService.getAllCharacters(page)
            _charactersMaxPage.value = response.body()!!.info.pages

            if(page > _charactersMaxPage.value){
                return Pair(
                    first = emptyList(),
                    second = true
                )
            }

            if(response.isSuccessful){
                output = Pair(
                    first = response.body()!!.result,
                    second = true
                )
            } else {
                output = Pair(
                    first = emptyList(),
                    second = false
                )
            }
            return output

        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return Pair(
                    first = emptyList(),
                    second = false
            )
        }

    }
    suspend fun getLocationsWithPagesFromApi(page : Int) : Triple<Boolean, Int, List<LocationFull>>{
        try {
            val response = _rickAndMortyApiService.getAllLocations(page)
            var output : Pair<List<LocationFull>, Boolean>
            if(response.isSuccessful){
                return Triple(
                    first = true,
                    second = response.body()!!.info.pages,
                    third = response.body()!!.result
                )
            } else if(response.errorBody()!!.equals("There is nothing here")) {
                Log.e("API Location", "PageCount is too high. No results found.")
                return Triple(false, 0, emptyList())
            } else {
                return Triple(false, 0, emptyList())
            }

        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return Triple(false, 0, emptyList())
        }
    }

    suspend fun getLocationsFromApi(page : Int) : Pair<Boolean, List<LocationFull>>{
        try {
            val response = _rickAndMortyApiService.getAllLocations(page)
            var output : Pair<List<LocationFull>, Boolean>

            if(response.isSuccessful){
                return Pair(
                    first = true,
                    second = response.body()!!.result
                )

            } else if(response.errorBody()!!.equals("There is nothing here")) {
                Log.e("API Location", "PageCount is too high. No results found.")
                return Pair(false, emptyList())

            } else {
                return Pair(false, emptyList())
            }

        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return Pair(false, emptyList())
        }
    }
    suspend fun getLocationCountFromAPI() : Int{
        try {
            val response = _rickAndMortyApiService.getAllLocations(1)
            if (response.isSuccessful){
                return response.body()!!.info.count

            } else if(response.errorBody()!!.equals("There is nothing here")){
                return 0

            } else {
                return 0
            }

        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return 0
        }
    }


   suspend fun getEpisodesWithPages() : Triple<Boolean, List<EpisodeData>, Int>{
           try {
               val response = _rickAndMortyApiService.getEpisodes(1)
               if(response.isSuccessful){
                   return Triple(first = true, second = response.body()!!.result, third = response.body()!!.info.pages)
               } else if (response.errorBody()!!.equals("There is nothing here")){
                   return Triple(first = false, second = emptyList(), third = 0)
               } else {
                   return Triple(first = false, second = emptyList(), third = 0)
               }
           } catch (e : UnknownHostException){
               Log.e("API", "Could not establish connection to API.")
               return Triple(first = false, second = emptyList(), third = 0)
           }
   }

    suspend fun getEpisodesFromAPI(page : Int) : Pair<Boolean, List<EpisodeData>>{
        try {
            val response = _rickAndMortyApiService.getEpisodes(page)
            if(response.isSuccessful){
                return Pair(first = true, second = response.body()!!.result)
            } else if (response.errorBody()!!.equals("There is nothing here")){
                return Pair(first = false, second = emptyList())
            } else {
                return Pair(first = false, second = emptyList())
            }
        } catch (e : UnknownHostException){
            Log.e("API", "Could not establish connection to API.")
            return Pair(first = false, second = emptyList())
        }
    }

    suspend fun getMultipleCharactersFromAPI(listOfIds : List<Int>) : Pair<Boolean, List<Character>>{
        try {
            val response = _rickAndMortyApiService.getMultipleCharacters(listOfIds)
            if(response.isSuccessful){
                return Pair(first = true, second = response.body()!!)
            } else if(response.errorBody()!!.equals("There is nothing here")){
                return Pair(first = false, second = emptyList())
            } else {
                return Pair(first = false, second = emptyList())
            }
        } catch (e : UnknownHostException){
            Log.e("API", "Could not establish connection to API.")
            return Pair(first = false, second = emptyList())
        }
    }
}
