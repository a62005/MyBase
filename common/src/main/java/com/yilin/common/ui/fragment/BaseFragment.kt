package com.yilin.common.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    companion object {
        private const val TAG = "BaseFragment"
    }

    private var _binding: T? = null
    protected val binding get() = _binding!!
    private var mScrollY: Int? = null

    override fun onResume() {
        super.onResume()
        Log.i(TAG, this.javaClass.simpleName + " onResume")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, this.javaClass.simpleName + " onHiddenChanged " + hidden)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, this.javaClass.simpleName + " onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = getViewBinding()
        initLayoutView()
        initOnClick()
        setScrollView()
        Log.i(TAG, this.javaClass.simpleName + " onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewData()
        Log.i(TAG, this.javaClass.simpleName + " onViewCreated")
    }

    private fun setScrollView() {
        binding.root.let { view ->
            if (view is NestedScrollView) {
                view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                    mScrollY = scrollY
                })
                mScrollY?.let {
                    view.post {
                        view.scrollTo(0, it)
                    }
                } ?: run {
                    mScrollY = 0
                }
            }
        }
    }

    protected abstract fun getViewBinding(): T

    protected abstract fun initLayoutView()

    protected abstract fun initViewData()

    protected open fun initOnClick() {}

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, this.javaClass.simpleName + " onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(TAG, this.javaClass.simpleName + " onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, this.javaClass.simpleName + " onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, this.javaClass.simpleName + " onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, this.javaClass.simpleName + " onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, this.javaClass.simpleName + " onStop")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        Log.i(TAG, this.javaClass.simpleName + " onDestroyView ")
    }

    protected inline fun <reified VM : ViewModel> getViewModel(owner: ViewModelStoreOwner): VM {
        return ViewModelProvider(owner)[VM::class.java]
    }
}