package com.example.appointmentplanner;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder>{

    private Note noteObject;
    private List<Note> note;
    private NotesListener notesListener;
    private Timer timer;
    private List<Note> noteSource;


    public NotesAdapter(List<Note> note,NotesListener notesListener)
    {
        this.note = note;
        this.notesListener = notesListener;
        noteSource = note;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_contener_note,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        noteObject = note.get(Objects.requireNonNull(holder).getAdapterPosition());
        holder.setNote(noteObject);
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListener.onNoteClicked(note.get(Objects.requireNonNull(holder).getAdapterPosition()),Objects.requireNonNull(holder).getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return note.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        TextView textTitle, textNote,noteDate, webUrl;
        LinearLayout layoutNote,layoutimageNote;
        RoundedImageView imageNote;
        NoteViewHolder (@NonNull View itemView)
        {
            super(itemView);
            textTitle = itemView.findViewById(R.id.noteTextTitle);
            textNote  = itemView.findViewById(R.id.noteText);
            noteDate  = itemView.findViewById(R.id.noteDate);
            layoutNote= itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.noteImageNote);
            webUrl = itemView.findViewById(R.id.noteWebUrl);

        }
        void setNote (Note note)
        {
            textTitle.setText(note.getTitle());
            textNote.setText(note.getNoteText());
            noteDate.setText(note.getCurrent_Date());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            gradientDrawable.setColor(Color.parseColor(note.getColor()));
            if (note.getImagePath() != null && !note.getImagePath().equals("null"))
            {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            }
            else
            {
                imageNote.setVisibility(View.GONE);
            }
            if (note.getLink() != null && !note.getLink().equals("null"))
            {
                webUrl.setText(note.getLink());
                webUrl.setVisibility(View.VISIBLE);
            }
            else
            {
                webUrl.setVisibility(View.GONE);
            }


        }
    }
    public void searchNote(final String searchKeyWord)
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyWord.trim().isEmpty())
                {
                    note = noteSource;
                }
                else
                {
                    ArrayList<Note> temp = new ArrayList<>();
                    for(Note note : noteSource)
                    {
                        if(note.getTitle().toLowerCase().contains(searchKeyWord.toLowerCase())
                        || note.getNoteText().toLowerCase().contains(searchKeyWord.toLowerCase()))
                        {
                            temp.add(note);
                        }
                    }
                    note = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },5000);

    }
    public void cancelTimer()
    {
        if(timer!= null)
        {
            timer.cancel();
        }
    }
}
