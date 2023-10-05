package com.yilin.mybase.ui.activity

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yilin.mybase.R
import com.yilin.mybase.databinding.ActivityCalendarBinding
import com.yilin.mybase.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.CalendarNoteAdapter
import com.yilin.mybase.ui.fragment.CalendarNoteAddFragment
import com.yilin.mybase.ui.fragment.CalendarSelectorFragment
import com.yilin.mybase.utils.DateUtil
import com.yilin.mybase.view.callback.ItemTouchCallback
import com.yilin.mybase.view.decoration.MyCalendarSelectedDecorator
import com.yilin.mybase.view.decoration.MyCalendarSelectedNowDecorator
import com.yilin.mybase.viewmodel.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CalendarActivity : BaseActivity<ActivityCalendarBinding>() {

    private val calendarViewModel: CalendarViewModel by lazy { getViewModel() }
    private val itemTouchCallback: ItemTouchCallback by lazy { ItemTouchCallback() }
    private var calendarSelectedDecorator: MyCalendarSelectedDecorator? = null
    private var calendarSelectedNowDecorator: MyCalendarSelectedNowDecorator? = null

    override fun getViewBinding(): ActivityCalendarBinding {
        return ActivityCalendarBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
        binding.rvNote.itemAnimator?.let {
            (it as SimpleItemAnimator).supportsChangeAnimations = false // 防止更新畫面閃爍
        }
    }

    override fun initOnClick() {
        binding.actionBar.setOnActionUpListener {
            finish()
        }
        binding.ivNoteAdd.setOnClickListener {
            CalendarNoteAddFragment.newInstance(supportFragmentManager, dateTime = getCurrentTime())
        }
        binding.mcv.setOnDateChangedListener { mcv, date, _ ->
            itemTouchCallback.clearView()
            val time = date.date.time
            calendarViewModel.setDateTime(time)
            calendarSelectedDecorator?.setCurrentDate(time)
            calendarSelectedNowDecorator?.setCurrentDate(time)
            mcv.invalidateDecorators()
        }
        binding.mcv.setOnMonthChangedListener { _, calendarDay ->
            setYearMonth(calendarDay.calendar)
        }
        binding.clMonthYear.setOnClickListener {
            showMonthSelector()
        }
    }

    override fun initViewData() {
        initNote()
        initCalendar()
    }

    private fun initCalendar() {
        binding.mcv.topbarVisible = false
        binding.mcv.setWeekDayLabels(resources.getStringArray(R.array.calendar_weekdays_simple))
        val todayTime = DateUtil.getTodayStartMillisecond()
        calendarViewModel.setDateTime(todayTime)
        val calendar = Calendar.getInstance()
        binding.mcv.setDateSelected(calendar, true)
        setYearMonth(calendar)
        calendarSelectedNowDecorator =
            MyCalendarSelectedNowDecorator(this, todayTime)
        calendarSelectedDecorator =
            MyCalendarSelectedDecorator(this, todayTime, emptyList())
        binding.mcv.addDecorator(calendarSelectedNowDecorator)
        binding.mcv.addDecorator(calendarSelectedDecorator)
        calendarViewModel.onNoteDateListener.observe(this) {
            calendarSelectedDecorator?.updateData(it)
            binding.mcv.invalidateDecorators()
        }
    }

    private fun setYearMonth(calendar: Calendar) {
        val month = calendar.get(Calendar.MONTH) + 1
        binding.tvMonth.text = month.toString()
        binding.tvYear.text = calendar.get(Calendar.YEAR).toString()
    }

    private fun initNote() {
        val onNoteDeleteListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
                if (adapter is CalendarNoteAdapter) {
                    val note = adapter.currentList[index]
                    calendarViewModel.deleteNote(note.id)
                }
            }
        }
        val noteAdapter = CalendarNoteAdapter(onNoteDeleteListener) { adapter, index ->
            if (adapter is CalendarNoteAdapter) {
                val note = adapter.currentList[index]
                CalendarNoteAddFragment.newInstance(supportFragmentManager, note.id, getCurrentTime())
            }
        }
        binding.rvNote.adapter = noteAdapter
        calendarViewModel.onNoteListListener.observe(this) {
            noteAdapter.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        itemTouchCallback.setSlideLayout(R.id.tv_delete)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNote)
    }

    private fun getCurrentTime(): Long = binding.mcv.selectedDate.date.time

    private fun showMonthSelector() {
        val year = binding.tvYear.text.toString().toInt()
        val month = binding.tvMonth.text.toString().toInt()
        val f = CalendarSelectorFragment.newInstance(supportFragmentManager, year, month, binding.clMonthYear.bottom)
        f.setMonthItemClickListener { y, m ->
            val c = Calendar.getInstance()
            c.set(y, m, 0, 0, 0)
            binding.mcv.setCurrentDate(c)
            setYearMonth(c)
        }
    }

    override fun onPause() {
        super.onPause()
        itemTouchCallback.clearView()
    }
}