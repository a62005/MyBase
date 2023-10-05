package com.yilin.mybase.ui.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.yilin.mybase.R
import com.yilin.mybase.databinding.FragmentCalendarNoteAddBinding
import com.yilin.mybase.viewmodel.CalendarNoteAddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarNoteAddFragment private constructor() :
    BaseBottomSheetFragment<FragmentCalendarNoteAddBinding>() {

    private val noteViewModel: CalendarNoteAddViewModel by lazy { getViewModel(this) }

    companion object {
        private const val TAG_ID = "tag_id"
        private const val TAG_DATE_TIME = "date_time"
        fun newInstance(manager: FragmentManager, id: Int? = null, dateTime: Long) {
            val bundle = Bundle()
            bundle.putInt(TAG_ID, id ?: -1)
            bundle.putLong(TAG_DATE_TIME, dateTime)
            val f = CalendarNoteAddFragment()
            f.arguments = bundle
            f.show(manager, id.toString())
        }
    }

    override fun getViewBinding(): FragmentCalendarNoteAddBinding {
        return FragmentCalendarNoteAddBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initOnClick() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnFinish.setOnClickListener {
            saveNote()
            dismiss()
        }
        binding.ivPrevious.setOnClickListener {
            noteViewModel.setPreviousDay()
        }
        binding.ivNext.setOnClickListener {
            noteViewModel.setNextDay()
        }
    }

    override fun initViewData() {
        initNote()
        initDate()
    }

    private fun initNote() {
        val id = requireArguments().getInt(TAG_ID)
        if (id != -1) {
            noteViewModel.onNoteListener.observe(viewLifecycleOwner) { note ->
                note?.let {
                    binding.etTitle.setText(it.title)
                    binding.etContent.setText(it.content)
                    binding.btnFinish.text = getString(R.string.calendar_note_btn_save)
                    binding.tvTitle.text = getString(R.string.calendar_title_note_edit)
                }
            }
            noteViewModel.setNote(id)
        }
    }

    private fun initDate() {
        noteViewModel.setDate(requireArguments().getLong(TAG_DATE_TIME))
        noteViewModel.onDateListener.observe(viewLifecycleOwner) {
            binding.tvDate.text = it
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.isNotEmpty() || content.isNotEmpty()) {
            noteViewModel.saveNote(title, content)
        }
    }
}