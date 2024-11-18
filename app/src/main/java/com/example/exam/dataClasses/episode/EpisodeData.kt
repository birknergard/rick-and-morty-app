package com.example.exam.dataClasses.episode

import android.util.Log
import com.google.gson.annotations.SerializedName

data class EpisodeData(
    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("characters")
    private val _appearingCharacterUrls: List<String>,

    val created: String,

    @SerializedName("episode")
    private val _episode: String,
    val id: Int,
    val name: String,
    val url: String
) {
    fun getAppearingCharacters() : List<String>{
        return _appearingCharacterUrls
    }

    fun getSeasonAndEpisode() : Pair<Int /*Season*/, Int /*Episode*/>{
        Log.d("EpisodeData", "Episode field raw: ${this._episode}")
        val season = this._episode.drop(1).dropLast(3).toInt()
        val episode = this._episode.drop(4).toInt()
        Log.d("EpisodeData", "Season: $season - Episode: $episode")
        return Pair(season, episode)
    }
}