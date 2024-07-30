package com.alvinsaputra.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.alvinsaputra.rickmorty.api.RickAndMortyApi
import com.alvinsaputra.rickmorty.model.Character
import com.alvinsaputra.rickmorty.network.RetrofitClient
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var characterName: TextView
    private lateinit var characterImage: ImageView
    private lateinit var characterStatus: TextView
    private lateinit var characterSpecies: TextView
    private lateinit var characterGender: TextView
    private lateinit var characterOrigin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterName = findViewById(R.id.characterName)
        characterImage = findViewById(R.id.characterImage)
        characterStatus = findViewById(R.id.characterStatus)
        characterSpecies = findViewById(R.id.characterSpecies)
        characterGender = findViewById(R.id.characterGender)
        characterOrigin = findViewById(R.id.characterOrigin)

        val api = RetrofitClient.instance.create(RickAndMortyApi::class.java)

        val call = api.getCharacter()
        call.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful) {
                    val character = response.body()
                    character?.let {
                        characterName.text = it.name
                        characterStatus.text = "Status: ${it.status}"
                        characterSpecies.text = "Species: ${it.species}"
                        characterGender.text = "Gender: ${it.gender}"
                        characterOrigin.text = "Origin: ${it.origin.name}"

                        Glide.with(this@MainActivity)
                            .load(it.image)
                            .into(characterImage)
                    }
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e("MainActivity", "Error fetching data", t)
            }
        })
    }
}
