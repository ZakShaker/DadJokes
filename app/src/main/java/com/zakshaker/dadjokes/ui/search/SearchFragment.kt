package com.zakshaker.dadjokes.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zakshaker.dadjokes.R
import com.zakshaker.dadjokes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    override var layoutRes: Int = R.layout.fragment_search

    private lateinit var searchViewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)

    }
}