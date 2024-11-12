package com.example.exam.viewModels

import com.example.exam.dataClasses.Episode
import kotlinx.coroutines.flow.MutableStateFlow

class Screen04ViewModel {

    val episodeList = MutableStateFlow<List<Episode>>(emptyList())

    val navUIState = listOf(false, false, false, true)
}