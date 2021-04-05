package com.snowcorp.caandmvvm.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snowcorp.caandmvvm.R
import com.snowcorp.core.data.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(var notes: ArrayList<Note>, val listAction: ListAction): RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val layout = view.noteLayout
        private val noteTitle = view.title
        private val noteContent = view.content
        private val noteDate = view.date
        private val wordCount = view.wordCount

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.UK)
            val resultDate = Date(note.updateTime)

            "Last updated: ${sdf.format(resultDate)}".also { noteDate.text = it }
            "Words: ${note.wordCount}".also { wordCount.text = it }

            layout.setOnClickListener { listAction.onClick(note.id) }
        }
    }


}