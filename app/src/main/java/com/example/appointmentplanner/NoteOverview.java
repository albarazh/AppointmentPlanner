package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteOverview extends AppCompatActivity  implements NotesListener{
    Statusbar statusbar;
    ImageButton addNote;
    public static final int REQUEST_CODE_ADD_NOTE= 1;
    public static final int REQUEST_CODE_UPDATE_NOTE= 2;
    public static final int REQUEST_CODE_SHOW_NOTES= 3;
    private HandelDB datenBank;
    ArrayList<Note> noteList;
    NotesAdapter notesAdapter;
    RecyclerView notesRecyclerView;
    int noteClickedPosition = -1;
    EditText search_bar_input;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(NoteOverview.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_note_overview);
        datenBank = new HandelDB();
        TextView item = findViewById(R.id.item);
        item.setText(datenBank.getTableRows("note_table") +" /Notes");

        addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(v -> startActivityForResult(new Intent(getApplicationContext(),NoteAdd.class),REQUEST_CODE_ADD_NOTE));
        search_bar_input =findViewById(R.id.search_bar_input);
        search_bar_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   notesAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(noteList.size()!=0)
                {
                    notesAdapter.searchNote(s.toString());
                }
            }
        });
        notesRecyclerView = findViewById(R.id.note_list);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        getNotes(REQUEST_CODE_SHOW_NOTES);


    }
    private void getNotes (final int requestCode)
    {
        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList,this);
        notesRecyclerView.setAdapter(notesAdapter);
        if(requestCode == REQUEST_CODE_SHOW_NOTES)
        {
            noteList.addAll(getAllNoteFromDb());
            notesAdapter.notifyDataSetChanged();
        }
        else if (requestCode == REQUEST_CODE_ADD_NOTE)
        {
            noteList.add(0,getAllNoteFromDb().get(0));
            notesAdapter.notifyItemInserted(0);
            notesRecyclerView.smoothScrollToPosition(0);
        }
        else if (requestCode == REQUEST_CODE_UPDATE_NOTE)
        {
            noteList.remove(noteClickedPosition);
            noteList.add(noteClickedPosition, getAllNoteFromDb().get(noteClickedPosition));
            notesAdapter.notifyItemChanged(noteClickedPosition);
        }

    }

    @Override
    public void onNoteClicked(Note note, int position) {
         noteClickedPosition = position;
         Intent intent = new Intent(getApplicationContext(),NoteAdd.class);
         intent.putExtra("isViewOrUpdate",true);
         intent.putExtra("note", note);
         startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
    }

    public void openPage(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        openPage(Homepage.class);
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK)
        {
            getNotes(REQUEST_CODE_ADD_NOTE);
        } else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                getNotes(REQUEST_CODE_UPDATE_NOTE);
            }
        }


    }
    private List<Note> getAllNoteFromDb ()
    {
        List<Note> note = null;
        try{
             note = datenBank.getAllNote();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Failed to get the Notes from DB", Toast.LENGTH_SHORT).show();
        }
        return note;
    }

}