package com.zakshaker.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment : Fragment() {
    abstract val layoutRes: Int
    abstract var koinModules: List<Module>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        koinModules?.let { loadKoinModules(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onDestroy() {
        super.onDestroy()
        koinModules?.let { unloadKoinModules(it) }
    }
}