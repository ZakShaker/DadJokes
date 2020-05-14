package com.zakshaker.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.zakshaker.core.BaseFragment
import com.zakshaker.core.VerticalSpaceItemDecoration
import com.zakshaker.home.feed.adapter.FeedAdapter
import com.zakshaker.home.feed.adapter.leftJokeAdapterDelegate
import com.zakshaker.home.feed.adapter.rightJokeAdapterDelegate
import com.zakshaker.home.feed.adapter.welcomeMessageAdapterDelegate
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalStdlibApi
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
        homeViewModel.loadingState
            .observe(
                viewLifecycleOwner,
                Observer {
                    if (it == HomeViewModel.LoadingState.Loading) {
                        pb_loading?.visibility = View.VISIBLE
                    } else {
                        pb_loading?.visibility = View.GONE
                    }
                }
            )

        homeViewModel.feed
            .observe(
                viewLifecycleOwner,
                Observer {
                    jokesAdapter.items = it.toList()
                    showMoreJokesButton()
                }
            )

        btn_more_jokes?.visibility = View.GONE
    }

    private lateinit var jokesAdapter: FeedAdapter
    private lateinit var jokesLayoutManager: LinearLayoutManager
    private fun initJokesFeed() {
        rv_feed?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                .also { jokesLayoutManager = it }
            adapter = FeedAdapter(
                welcomeMessageAdapterDelegate(),
                rightJokeAdapterDelegate(),
                leftJokeAdapterDelegate()
            ).also {
                jokesAdapter = it
            }

            addItemDecoration(VerticalSpaceItemDecoration())
        }
    }

    private fun scrollFeedToPosition(position: Int) {
        rv_feed?.apply {
            layoutManager?.startSmoothScroll(
                feedScroller.apply { targetPosition = position }
            )
        }
    }

    private val feedScroller: LinearSmoothScroller by lazy {
        object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_END
        }
    }

    private fun showMoreJokesButton() {
        if (jokesLayoutManager.findFirstVisibleItemPosition() == 0)
            btn_more_jokes?.visibility = View.GONE
        else
            if (btn_more_jokes?.visibility == View.GONE)
                btn_more_jokes?.apply {
                    visibility = View.VISIBLE
                    text = getText(R.string.feed_more_jokes_btn)
                    setOnClickListener {
                        scrollFeedToPosition(0)
                        visibility = View.GONE
                    }
                }

    }

}