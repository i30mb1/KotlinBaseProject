package n7.myperfectemptyproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import n7.myperfectemptyproject.R
import n7.myperfectemptyproject.databinding.MainFragmentBinding
import n7.myperfectemptyproject.di.injector
import n7.myperfectemptyproject.utils.NetworkEvents
import n7.myperfectemptyproject.utils.NetworkState
import n7.myperfectemptyproject.utils.NetworkStateHolder
import n7.myperfectemptyproject.utils.viewModelWithSavedStateHandle

class MainFragment : Fragment() {

    private var previousSate: Boolean? = null
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

        viewmodel.errorMessage.observe(this) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        NetworkEvents.observe(this) {
            handleConnectivityChange(it.networkState)
        }
    }

    private fun handleConnectivityChange(networkState: NetworkState) {
        if (networkState.isConnected && previousSate != true) {
            binding.tvNetworkStatus.text = "The network is back !"
        }

        if (!networkState.isConnected && previousSate == false) {
            binding.tvNetworkStatus.text = "No Network !"
        }

        previousSate = networkState.isConnected
    }

    override fun onResume() {
        super.onResume()
        handleConnectivityChange(NetworkStateHolder)
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
                Snackbar.make(
                    binding.root,
                    getString(R.string.all_press_again_for_exit),
                    Snackbar.LENGTH_SHORT
                ).show()
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
