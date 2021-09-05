package ru.freeezzzi.tinkoff_test_task.presentation.showgif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO
import ru.freeezzzi.tinkoff_test_task.presentation.DataState
import ru.freeezzzi.tinkoff_test_task.presentation.R
import ru.freeezzzi.tinkoff_test_task.presentation.baseimpl.BaseFragment
import ru.freeezzzi.tinkoff_test_task.presentation.databinding.FragmentShowGifBinding

@AndroidEntryPoint
class ShowGifFragment : BaseFragment<FragmentShowGifBinding>() {

    private val viewModel: ShowGifViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowGifBinding
        get() = FragmentShowGifBinding::inflate

    override fun setup() {
        initObservers()
        viewModel.handleEvent(ShowGifContract.Event.OnNextButtonClicked)
    }

    private fun initObservers() {
        binding.run {
            sgCvNext.setOnClickListener {
                viewModel.setEvent(ShowGifContract.Event.OnNextButtonClicked)
            }
            sgCvBack.setOnClickListener {
                viewModel.setEvent(ShowGifContract.Event.OnPrevButtonClicked)
            }

            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect { state ->
                    when (state.gif) {
                        is DataState.Loading -> { sgProgressbar.visibility = View.VISIBLE }
                        is DataState.Error -> { sgProgressbar.visibility = View.INVISIBLE }
                        is DataState.Empty -> { sgProgressbar.visibility = View.INVISIBLE }
                        is DataState.Success -> {
                            sgProgressbar.visibility = View.INVISIBLE
                            setUpGif(state.gif.data)
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                val retryClickListener: (View) -> Unit = { view -> viewModel.setEvent(ShowGifContract.Event.OnNextButtonClicked) }
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is ShowGifContract.Effect.ShowToast -> {
                            sgProgressbar.visibility = View.INVISIBLE
                            val msg = ShowGifContract.Effect.ShowToast.msg ?: "Error"
                            showSnackBar(binding.root, msg, retryClickListener)
                        }
                    }
                }
            }
        }
    }

    private fun setUpGif(gif: GifDTO) {
        binding.run {
            Glide
                .with(requireContext())
                .asGif()
                .load(gif.url)
                .centerCrop()
                //.placeholder(R.drawable.ic_launcher_background)
                .into(sgIvGif)
            sgTvIamgetext.text = gif.title
            sgCvBack.visibility = if(gif.isFirst) View.GONE else View.VISIBLE

        }
    }
}
