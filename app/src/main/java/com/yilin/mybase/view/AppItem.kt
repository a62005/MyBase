package com.yilin.mybase.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.yilin.mybase.R
import com.yilin.mybase.databinding.ItemAppItemBinding

/**
 * Create in 2022/7 by Link Hsieh
 */
class AppItem(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding: ItemAppItemBinding

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    var content: String?
        get() = binding.tvContent.text.toString()
        set(content) {
            binding.tvContent.text = content
        }

    fun setOnSwitchChangeListener(l: CompoundButton.OnCheckedChangeListener) {
        binding.suffixSwitch.setOnCheckedChangeListener(l)
    }

    fun setSwitch(isCheck: Boolean) {
        binding.suffixSwitch.isChecked = isCheck
    }

    val switchCheck: Boolean
        get() = binding.suffixSwitch.isChecked

    fun hideSuffixIcon() {
        binding.ivSuffixIcon.visibility = GONE
    }

    fun setSuffixIconOnClickListener(onClickListener: OnClickListener) {
        binding.ivSuffixIcon.setOnClickListener(onClickListener)
    }

    init {
        binding = ItemAppItemBinding.inflate(LayoutInflater.from(context), this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AppItem)
        binding.tvTitle.text = ta.getString(R.styleable.AppItem_title)
        ta.getString(R.styleable.AppItem_titleHint)?.let { titleHint ->
            binding.tvTitleHint.visibility = VISIBLE
            binding.tvTitleHint.text = titleHint
        }
        ta.getString(R.styleable.AppItem_contentHint)?.let { contentHint ->
            binding.tvContent.hint = contentHint
        }
        if (ta.getBoolean(R.styleable.AppItem_isSwitchMode, false)) {
            binding.ivSuffixIcon.visibility = GONE
            binding.suffixSwitch.visibility = VISIBLE
        }
        ta.getDrawable(R.styleable.AppItem_prefixIcon)?.let { icResource ->
            binding.ivPrefixIcon.visibility = VISIBLE
            binding.ivPrefixIcon.setImageDrawable(icResource)
        }

        if (ta.getBoolean(R.styleable.AppItem_hideSuffixIcon, false)) {
            binding.ivSuffixIcon.visibility = GONE
            binding.suffixSwitch.visibility = GONE
        }
        ta.recycle()
    }
}