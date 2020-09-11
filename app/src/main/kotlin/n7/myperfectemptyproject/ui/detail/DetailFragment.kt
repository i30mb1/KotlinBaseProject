package n7.myperfectemptyproject.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import n7.myperfectemptyproject.R
import n7.myperfectemptyproject.databinding.DetailFragmentBinding
import n7.myperfectemptyproject.utils.loadImageUrl

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailFragmentBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        binding.iv.transitionName = args.tn
        binding.iv.loadImageUrl(args.url)
        sharedElementEnterTransition = buildContainerTransform(MaterialContainerTransform.FADE_MODE_OUT)
        sharedElementReturnTransition = buildContainerTransform(MaterialContainerTransform.FADE_MODE_IN)
        // TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
        //     sharedElementEnterTransition = this
        //     // sharedElementReturnTransition = this
        // }
    }

    private fun buildContainerTransform(mode: Int) = MaterialContainerTransform().apply {
        drawingViewId = R.id.nav_main_activity
        interpolator = FastOutSlowInInterpolator()
        containerColor = Color.TRANSPARENT
        fadeMode = mode
        duration = 1000
        scrimColor = Color.TRANSPARENT
    }

}