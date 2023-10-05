package com.yilin.mybase.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.yilin.mybase.R
import com.yilin.mybase.databinding.FragmentCalendarSelectorBinding
import com.yilin.mybase.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.CalendarMonthSelectorAdapter

/***
 * 選擇月份的彈窗
 */
class CalendarSelectorFragment private constructor() : DialogFragment() {

    companion object {
        private const val TAG_YEAR = "tag_year"
        private const val TAG_MONTH = "tag_month"
        private const val TAG_Y_POSITION = "tag_y_position"

        /***
         * 根據當前年月顯示年月
         */
        fun newInstance(
            manager: FragmentManager,
            year: Int,
            month: Int,
            yPosition: Int = 0
        ): CalendarSelectorFragment {
            val bundle = Bundle()
            bundle.putInt(TAG_YEAR, year)
            bundle.putInt(TAG_MONTH, month)
            bundle.putInt(TAG_Y_POSITION, yPosition)
            val f = CalendarSelectorFragment()
            f.arguments = bundle
            f.show(manager, "$year + $month")
            return f
        }

        private val MONTH_LIST = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    }

    private lateinit var binding: FragmentCalendarSelectorBinding
    private var listener: ((year: Int, month: Int) -> Unit)? = null
    private lateinit var monthAdapter: CalendarMonthSelectorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TopSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        binding = FragmentCalendarSelectorBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.let { window ->
            window.attributes?.let { att ->
                att.windowAnimations = R.style.MyCalendarSelectorScroll
                att.gravity = Gravity.TOP
                att.width = LayoutParams.MATCH_PARENT
                att.height = LayoutParams.WRAP_CONTENT

                val yPosition = requireArguments().getInt(TAG_Y_POSITION)
                if (yPosition != 0) {
                    att.y = yPosition
                }
                window.attributes = att
            }
        }
        initOnClick()
        initViewData()
        return dialog
    }

    private fun initOnClick() {
        binding.ivNext.setOnClickListener {
            var year = getYear()
            setYear(++year)
        }
        binding.ivPrevious.setOnClickListener {
            var year = getYear()
            setYear(--year)
        }
    }

    private fun initViewData() {
        val month = requireArguments().getInt(TAG_MONTH)
        val year = requireArguments().getInt(TAG_YEAR)
        binding.tvYear.text = year.toString()
        monthAdapter = CalendarMonthSelectorAdapter(onMonthItemClickListener)
        monthAdapter.setDefaultMonth(year, month)
        binding.rvMonth.adapter = monthAdapter
        monthAdapter.submitList(MONTH_LIST)
    }

    private fun setYear(year: Int) {
        binding.tvYear.text = year.toString()
        monthAdapter.checkSelectMonth(year)
    }

    private val onMonthItemClickListener = object : BaseListAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
            if (adapter is CalendarMonthSelectorAdapter) {
                val year = getYear()
                val month = adapter.currentList[index].toInt()
                listener?.invoke(year, month)
                dismiss()
            }
        }
    }

    private fun getYear() = binding.tvYear.text.toString().toInt()

    fun setMonthItemClickListener(listener: (year: Int, month: Int) -> Unit) {
        this.listener = listener
    }
}