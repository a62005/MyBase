package com.yilin.mybase.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.yilin.mybase.R
import com.yilin.mybase.databinding.LayoutBottomSheetSelectorDialogBinding
import com.yilin.common.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.BottomSheetSelectorAdapter
import com.yilin.common.ui.fragment.BaseBottomSheetFragment

class BottomSheetSelector private constructor() :
    BaseBottomSheetFragment<LayoutBottomSheetSelectorDialogBinding>() {

    object Builder {
        private var title: String? = null
        private var content = emptyList<String>()
        private var callback: ((item: String) -> Unit)? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: List<String>): Builder {
            this.content = content
            return this
        }

        fun setItemClickListener(listener: (item: String) -> Unit): Builder {
            this.callback = listener
            return this
        }

        fun build(manager: FragmentManager) {
            BottomSheetSelector().apply {
                setContent(title, ArrayList(content))
                setOnItemClickListener {
                    callback?.invoke(it)
                }
                show(manager)
            }
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    private var onItemClickCallback: ((item: String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetSelectorDialog)
    }

    override fun getViewBinding(): LayoutBottomSheetSelectorDialogBinding {
        return LayoutBottomSheetSelectorDialogBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initOnClick() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun initViewData() {
        val adapter = BottomSheetSelectorAdapter(onItemClickListener)
        binding.rvContent.adapter = adapter
        arguments?.let {
            it.getString(TITLE)?.let { title ->
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvTitle.text = title
            } ?: run { binding.tvTitle.visibility = View.GONE }
            it.getStringArrayList(CONTENT)?.let { content ->
                adapter.submitList(content)
            } ?: run { adapter.submitList(emptyList()) }
        }
    }

    private fun setContent(title: String?, content: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putString(TITLE, title)
        bundle.putStringArrayList(CONTENT, content)
        this.arguments = bundle
    }

    private fun setOnItemClickListener(onItemClickListener: (item: String) -> Unit) {
        onItemClickCallback = onItemClickListener
    }

    private val onItemClickListener = object : BaseListAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
            onItemClickCallback?.let {
                val result = (adapter as BottomSheetSelectorAdapter).currentList[index]
                onItemClickCallback?.invoke(result)
                dismiss()
            }
        }

    }
}