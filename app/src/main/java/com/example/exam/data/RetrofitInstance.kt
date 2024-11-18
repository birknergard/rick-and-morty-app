package com.example.exam.data

import android.util.Log
import com.example.exam.dataClasses.ApiOutput
import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.EpisodeData
import com.example.exam.dataClasses.LocationFull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
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

    private val _rickAndMortyApiService = _retrofit.create(RickAndMortyApiService::class.java)

    val characterAPI = CharacterAPI()
    val locationAPI = LocationAPI()
    val episodeAPI = EpisodeAPI()

    inner class CharacterAPI{

        private fun parseBasic(response: Response<List<Character>>) : ApiOutput<Character>{
            if(response.isSuccessful){
                return ApiOutput(
                    isSuccessful = true,
                    output = response.body()!!
                )
            } else {
                return ApiOutput()
            }
        }

        private fun parse(response : Response<ApiResponse<Character>>) : ApiOutput<Character>{
            if(response.isSuccessful){
                return ApiOutput(
                    isSuccessful = true,
                    output = response.body()!!.result,
                    pages = response.body()!!.info.pages
                )
            } else {
                return ApiOutput()
            }
        }


        suspend fun get(page : Int) : ApiOutput<Character> {
            try {
                val response = _rickAndMortyApiService.getAllCharacters(page)
                return parse(response)
                // If page parameter exceeds total pages it returns an empty list.

            } catch (e : UnknownHostException){
                Log.e("API","Could not establish connection to API.")
                e.printStackTrace()
                return ApiOutput()
            } catch (f : IllegalStateException){
                f.printStackTrace()
                return ApiOutput()
            }

        }

        suspend fun get(listOfIds : List<Int>) : ApiOutput<Character>{
            try {
                val response = _rickAndMortyApiService.getMultipleCharacters(listOfIds)
                return parseBasic(response)

            } catch (e : UnknownHostException){
                Log.e("API", "Could not establish connection to API.")
                e.printStackTrace()
                return ApiOutput()

            } catch (f : IllegalStateException){
                f.printStackTrace()
                return ApiOutput()
            }
        }

    }

    inner class LocationAPI{
        private fun parse(response : Response<ApiResponse<LocationFull>>) : ApiOutput<LocationFull>{
            if(response.isSuccessful){
                return ApiOutput(
                    isSuccessful = true,
                    output = response.body()!!.result,
                    pages = response.body()!!.info.pages
                )
            } else {
                return ApiOutput()
            }
        }
        suspend fun get(page : Int) : ApiOutput<LocationFull>{
            try {
                val response = _rickAndMortyApiService.getAllLocations(page)
                return parse(response)

            } catch (e : UnknownHostException){
                Log.e("API","Could not establish connection to API.")
                return ApiOutput()
            }
        }

        suspend fun getPageCount() : Int{
            try {
                val response = _rickAndMortyApiService.getAllLocations(1)
                return parse(response).pages

            } catch (e : UnknownHostException){
                Log.e("API","Could not establish connection to API.")
                return 0
            } catch (f : IllegalStateException){
                f.printStackTrace()
                return 0
            }
        }
    }

    inner class EpisodeAPI{
        suspend fun get(page : Int) : ApiOutput<EpisodeData>{
            try {
                val response = _rickAndMortyApiService.getEpisodes(page)
                if(response.isSuccessful){
                    return ApiOutput(
                        isSuccessful = true,
                        output = response.body()!!.result,
                        pages = response.body()!!.info.pages
                    )
                } else {
                    return ApiOutput()
                }

            } catch (e : UnknownHostException){
                Log.e("API", "Could not establish connection to API.")
                return ApiOutput()

            } catch (f : IllegalStateException){
                f.printStackTrace()
                return ApiOutput()
            }
        }
    }

}
