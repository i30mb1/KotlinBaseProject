package com.example.myperfectemptyproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myperfectemptyproject.R
import com.example.myperfectemptyproject.databinding.MainFragmentBinding
import com.example.myperfectemptyproject.di.injector
import com.example.myperfectemptyproject.utils.viewModelWithSavedStateHandle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private var finishActivity = false
    private val viewmodel by viewModelWithSavedStateHandle {
        injector.mainViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupOnBackPressedAction()
    }

    private fun setupListAdapter() {
//        mainAdapter = MainAdapter(this)
//        binding.rvMainFragment.apply {
//            adapter = mainAdapter
//            // exitTransition animation with this works as intended
//            postponeEnterTransition()
//            viewTreeObserver.addOnPreDrawListener { startPostponedEnterTransition(); true }
//        }
//        myViewModel.lastItem.observe(this, mainAdapter::submitList)
    }

    private fun setupOnBackPressedAction() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (finishActivity) {
                requireActivity().finish()
            } else {
                finishActivity = true
                Snackbar.make(binding.root, getString(R.string.all_press_again_for_exit), Snackbar.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    delay(MILLIS_FOR_EXIT)
                    finishActivity = false
                }
            }
        }
    }

    companion object {
        const val MILLIS_FOR_EXIT = 2000L
    }
}
