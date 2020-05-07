package com.zakshaker.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakshaker.core.BaseFragment
import com.zakshaker.core.SimpleRecyclerAdapter
import com.zakshaker.core.VerticalSpaceItemDecoration
import com.zakshaker.model.JokeModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_feed_joke.view.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class HomeFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_home
    override var koinModules: List<Module>? = listOf(
        module {
            viewModel { HomeViewModel(get()) }
        }
    )

    private val homeViewModel: HomeViewModel by viewModel()

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
                    jokesAdapter.setItems(it.toList())
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

            addItemDecoration(VerticalSpaceItemDecoration())
        }
    }

}