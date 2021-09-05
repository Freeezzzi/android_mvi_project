package ru.freeezzzi.tinkoff_test_task.presentation.baseimpl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.freeezzzi.tinkoff_test_task.presentation.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = _binding as VB

    /**
     * Вызывается в onViewCreated для заполнения данными.
     */
    abstract fun setup()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showSnackBar(view: View, text: String, listener: View.OnClickListener? = null) {
        Snackbar
            .make(view, text, Snackbar.LENGTH_LONG)
            .setAction(resources.getString(R.string.retry), listener)
            .show()
    }
}
