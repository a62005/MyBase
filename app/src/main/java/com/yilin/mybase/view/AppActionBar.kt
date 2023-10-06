package com.yilin.mybase.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.yilin.mybase.R
import com.yilin.mybase.databinding.ItemAppActionBarBinding

class AppActionBar(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding: ItemAppActionBarBinding

    fun setOnActionUpListener(l: OnClickListener) {
        binding.llPrefix.setOnClickListener(l)
    }

    fun setActionTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun hideActionUp() {
        binding.ivPrefixIcon.visibility = GONE
    }

    fun showActionUp() {
        binding.ivBack.visibility = VISIBLE
    }

    fun setOnSuffixClickListener(onClickListener: OnClickListener) {
        binding.llSuffix.setOnClickListener(onClickListener)
    }

    fun setSuffixContent(content: String) {
        binding.tvSuffixContent.text = content
    }

    init {
        binding = ItemAppActionBarBinding.inflate(LayoutInflater.from(context), this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AppActionBar)
        binding.tvTitle.text = ta.getString(R.styleable.AppActionBar_actionTitle)
        if (ta.getBoolean(R.styleable.AppActionBar_hideActionUp, false)) {
            binding.llPrefix.visibility = GONE
        }
        ta.getDrawable(R.styleable.AppActionBar_actionUpIcon)?.let { actionUpIcon ->
            binding.ivBack.setImageDrawable(actionUpIcon)
        }
        ta.getDrawable(R.styleable.AppActionBar_actionSuffixIcon)?.let { actionSuffixIcon ->
            binding.llSuffix.visibility = VISIBLE
            binding.ivSuffixIcon.visibility = VISIBLE
            binding.ivSuffixIcon.setImageDrawable(actionSuffixIcon)
        }
        ta.getDrawable(R.styleable.AppActionBar_actionPrefixIcon)?.let { actionPrefixIcon ->
            binding.llPrefix.visibility = GONE
            binding.ivPrefixIcon.visibility = VISIBLE
            binding.ivPrefixIcon.setImageDrawable(actionPrefixIcon)
        }
        ta.getString(R.styleable.AppActionBar_actionContent)?.let { actionContent ->
            binding.llSuffix.visibility = VISIBLE
            binding.tvSuffixContent.text = actionContent
        }
        ta.recycle()
    }
}