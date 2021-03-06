package n7.myperfectemptyproject.utils.extension

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import n7.myperfectemptyproject.R

// create or grubbing existing spring animation
fun View.spring(property: DynamicAnimation.ViewProperty): SpringAnimation {
    val key = getKey(property)
    var springAnimation = getTag(key) as? SpringAnimation?
    if (springAnimation == null) {
        springAnimation = SpringAnimation(this, property)
        setTag(key, springAnimation)
    }
    return springAnimation
}

fun getKey(property: DynamicAnimation.ViewProperty): Int {
    return when (property) {
        SpringAnimation.TRANSLATION_X -> R.id.TranslationY
        else -> 0
    }
}

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
                // EspressoIdlingResource.increment()
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                // EspressoIdlingResource.decrement()
            }
        })
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupErrorSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<String>,
    timeLength: Int = Snackbar.LENGTH_SHORT
) {
    snackbarEvent.observe(
        lifecycleOwner,
        Observer { event ->
            event?.let {
                showSnackbar(it, timeLength)
            }
        }
    )
}

/**
 * show keyboard without any problems
 * @link (https://developer.squareup.com/blog/showing-the-android-keyboard-reliably)
 */
fun View.focusAndShowKeyboard() {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        // It’s very important to remove this listener once we are done.
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}
