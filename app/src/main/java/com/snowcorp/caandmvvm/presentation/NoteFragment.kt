package com.snowcorp.caandmvvm.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.snowcorp.caandmvvm.R
import com.snowcorp.caandmvvm.framework.NoteViewModel
import com.snowcorp.core.data.Note
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.item_note.*

class NoteFragment : Fragment() {

    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        checkButton.setOnClickListener {
            handleNoteFields()
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(context!!)
                            .setTitle("Delete note?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Delete") {dialogInterface, i ->
                                viewModel.deleteNote(currentNote)
                            }
                            .setNegativeButton("Cancel") {dialogInterface, i ->}
                            .create()
                            .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleNoteFields() {
        if (titleView.text.toString() != "" || contentView.text.toString() != "") {
            val time: Long = System.currentTimeMillis()
            currentNote.title = titleView.text.toString()
            currentNote.content = contentView.text.toString()
            currentNote.updateTime = time
            if (currentNote.id == 0L) {
                currentNote.creationTime = time
            }
            viewModel.saveNote(currentNote)
        } else {
            Navigation.findNavController(titleView).popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.saved.observe(this, {
            if (it) {
                Toast.makeText(context, "Done.", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(titleView).popBackStack()
            } else {
                Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(this, {note ->
            note?.let {
                currentNote = it
                titleView.setText(it.title, TextView.BufferType.EDITABLE)
                contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }
}