package com.zakshaker.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakshaker.core.BaseFragment
import com.zakshaker.core.SimpleRecyclerAdapter
import com.zakshaker.model.JokeModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_feed_joke.view.*
import org.koin.android.ext.android.get
import org.koin.core.module.Module
import org.koin.dsl.module

class HomeFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_home
    override var koinModules: List<Module>? = listOf(
        module {
            single<HomeViewModelFactory> { HomeViewModelFactory(get()) }
        }
    )

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =
            ViewModelProvider(this, get<HomeViewModelFactory>()).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initJokesFeed()

        homeViewModel.homeState
            .observe(
                viewLifecycleOwner,
                Observer {
                    tv_state?.text = it.name
                }
            )

        homeViewModel.jokes
            .observe(
                viewLifecycleOwner,
                Observer {
                    jokesAdapter.setItems(it)
                }
            )

        btn_favorites?.setOnClickListener {
            homeViewModel.onRefresh()
        }
    }

    private lateinit var jokesAdapter: SimpleRecyclerAdapter<JokeModel>
    private fun initJokesFeed() {
        rv_feed?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = SimpleRecyclerAdapter<JokeModel>(
                R.layout.item_feed_joke,
                attachView = { view, item ->
                    view.apply {
                        tv_text?.text = item.text
                    }
                }
            ).also { jokesAdapter = it }
        }
    }

}