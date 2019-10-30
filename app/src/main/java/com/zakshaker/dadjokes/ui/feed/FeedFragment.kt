package com.zakshaker.dadjokes.ui.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zakshaker.dadjokes.R
import com.zakshaker.dadjokes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : BaseFragment() {

    override var layoutRes: Int = R.layout.fragment_feed

    private lateinit var feedViewModel: FeedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        feedViewModel.randomJoke.observe(this, Observer {
            tv_random_joke.text = getString(R.string.joke_placeholder, it.value)
        })

        btn_get_random_joke?.setOnClickListener { feedViewModel.onLoadRandomJoke() }
        feedViewModel.isNextRandomJokeButtonEnabled.observe(this, Observer { isEnabled ->
            if (isEnabled) {
                btn_get_random_joke?.visibility = View.VISIBLE
                pb_loading?.visibility = View.GONE
            } else {
                btn_get_random_joke?.visibility = View.GONE
                pb_loading?.visibility = View.VISIBLE
            }
        })
    }

}