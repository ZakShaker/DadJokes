package com.zakshaker.dadjokes.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zakshaker.dadjokes.R
import com.zakshaker.dadjokes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment() {
    override var layoutRes: Int = R.layout.fragment_favorites

    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesViewModel =
            ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

    }
}