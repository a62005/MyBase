package com.yilin.common.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yilin.common.R

abstract class BaseBottomSheetFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    companion object {
        private const val TAG = "BaseBottomSheetFragment"
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
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialog)
        Log.i(TAG, this.javaClass.simpleName + " onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        _binding = getViewBinding()
        dialog.setContentView(binding.root)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        initLayoutView()
        initOnClick()
        setScrollView()
        setKeyboardEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewData()
    }


    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setKeyboardEvent() {
        binding.root.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val manager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            false
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

    fun show(manager: FragmentManager) {
        if (!isAdded) {
            super.show(manager, this.javaClass.simpleName)
        }
    }

    protected inline fun <reified VM : ViewModel> getViewModel(owner: ViewModelStoreOwner): VM {
        return ViewModelProvider(owner)[VM::class.java]
    }
}