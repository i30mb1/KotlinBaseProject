package n7.myperfectemptyproject

import android.Manifest
import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TimingLogger
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import n7.myperfectemptyproject.databinding.MainActivityBinding
import n7.myperfectemptyproject.ui.ErrorDialogDirections
import n7.myperfectemptyproject.ui.ErrorDialogListener
import n7.myperfectemptyproject.utils.logPlease

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel by viewModels<SimpleLifeCycleAwareObservable>()
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isGranted!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        // The window will not be resized when virtual keyboard is shown (bottom navigation bar will be hidden under virtual keyboard)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        viewModel.liveData.observe(this) {
        }

        // method that listening all fragment in NavController
        findNavController(R.id.nav_main_activity).addOnDestinationChangedListener { controller: NavController, destination: NavDestination, arguments: Bundle? ->
            logPlease(destination.label.toString())
        }
        // registerFragmentLifecycle()
    }

    fun startActivityAndGetResult() {
        requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun showDialogError(message: String, listener: ErrorDialogListener) {
        findNavController(R.id.nav_main_activity).navigate(ErrorDialogDirections.actionGlobalErrorDialog(message, listener))
    }

    // listen what happening with our fragments (much better than ActivityLifecycleCallbacks)
    private fun registerFragmentLifecycle() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                // override necessary method
            },
            false
        )
    }

    private fun measurementOperations() {
        //  An idiomatic Android approach to measuring execution time
        val timingLogger = TimingLogger("N7", "work")
        timingLogger.addSplit("(1)")
        timingLogger.dumpToLog()
        // - adb shell setprop log.tag.N7 VERBOSE
    }
}

// cool idea how to listen changes from SharedPreference with automatic subscription/unsubscription that prevent memory leaks
class SimpleLifeCycleAwareObservable(application: Application) : AndroidViewModel(application) {

    internal val liveData: LiveData<Boolean> =
        object : MutableLiveData<Boolean>(), SharedPreferences.OnSharedPreferenceChangeListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(application)

            override fun onActive() {
                sp.registerOnSharedPreferenceChangeListener(this)
            }

            override fun onSharedPreferenceChanged(
                sharedPreferences: SharedPreferences,
                key: String
            ) {
//            if (MainActivity.KEY == key) {
//                value = sp.getBoolean(MainActivity.KEY, false)
//            }
            }

            override fun onInactive() {
                sp.unregisterOnSharedPreferenceChangeListener(this)
            }
        }
}
