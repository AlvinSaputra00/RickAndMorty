package com.alvinsaputra.rickmorty.api

import com.alvinsaputra.rickmorty.model.Character
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character/1")
    fun getCharacter(): Call<Character>
}
