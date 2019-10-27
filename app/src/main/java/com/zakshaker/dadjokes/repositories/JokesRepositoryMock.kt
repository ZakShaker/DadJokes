package com.zakshaker.dadjokes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zakshaker.dadjokes.domain.entities.Joke

class JokesRepositoryMock : JokesRepository {

    override fun getRandomJoke(): LiveData<Joke> = MutableLiveData<Joke>().apply {
        value = jokes.random()
    }

    override fun getTheJoke(id: String): LiveData<Joke> = MutableLiveData<Joke>().apply {
        value = jokes.random()
    }

    override fun searchJokes(page: Int, limit: Int, query: String): LiveData<List<Joke>> =
        MutableLiveData<List<Joke>>().apply {
            value = listOf(
                jokes.random(),
                jokes.random(),
                jokes.random()
            )
        }


    private val jokes = listOf<Joke>(
        Joke(
            "1",
            "I'm tired of following my dreams. I'm just going to ask them where they are going and meet up with them later."
        ),
        Joke(
            "2",
            "Did you hear about the guy whose whole left side was cut off? He's all right now."
        ),
        Joke("3", "Why didn’t the skeleton cross the road? Because he had no guts."),
        Joke("4", "What did one nut say as he chased another nut?  I'm a cashew!"),
        Joke("5", "Chances are if you' ve seen one shopping center, you've seen a mall. "),
        Joke(
            "6",
            "I knew I shouldn't steal a mixer from work, but it was a whisk I was willing to take."
        ),
        Joke("7", "How come the stadium got hot after the game? Because all of the fans left."),
        Joke("8", "Why was it called the dark ages? Because of all the knights. ")
    )

}