package com.example.appointmentplanner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NoteAdd extends AppCompatActivity {
    Statusbar statusbar;
    LinearLayout layoutMiscellaneous,layoutWebUrl,layouttextWebUrl;
    TextView date, textWebUrl;
    Button back,save,update;
    String currentDate;
    ImageView imageNote;
    AlertDialogClass alertDialogClass;
    LoadingDialog loadingDialog = new LoadingDialog(this);
    EditText titleInput, noteTextInput;
    private static HandelDB database ;
    private static String selectNoteColor ;
    private View view;
    private String imagePath;
    private AlertDialog dialogAddUrl;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private Note alreadyAvailableNote;
    ImageView imageColor1,imageColor2,imageColor3,imageColor4,imageColor5,imageColor6,imageColor7;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES );
        setContentView(R.layout.activity_note_add);
        statusbar = new Statusbar();
        database = new HandelDB();
        alertDialogClass = new AlertDialogClass();
        statusbar.check_sdk(NoteAdd.this, Build.VERSION.SDK_INT);
        layoutMiscellaneous= findViewById(R.id.layoutMiscellaneous);
        view = findViewById(R.id.colorview);
        back= findViewById(R.id.back);
        date = findViewById(R.id.date);
        save = findViewById(R.id.save);
        update = findViewById(R.id.update);
        titleInput = findViewById(R.id.noteTitleInput);
        noteTextInput = findViewById(R.id.noteTextInput);
        textWebUrl = findViewById(R.id.textWebURL);
        layouttextWebUrl= findViewById(R.id.layoutWebUrl);
        layoutWebUrl =findViewById(R.id.addUrlContainer);
        imageColor1= layoutMiscellaneous.findViewById(R.id.imageColor1);
        imageColor2= layoutMiscellaneous.findViewById(R.id.imageColor2);
        imageColor3= layoutMiscellaneous.findViewById(R.id.imageColor3);
        imageColor4= layoutMiscellaneous.findViewById(R.id.imageColor4);
        imageColor5= layoutMiscellaneous.findViewById(R.id.imageColor5);
        imageColor6= layoutMiscellaneous.findViewById(R.id.imageColor6);
        imageColor7= layoutMiscellaneous.findViewById(R.id.imageColor7);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        imageNote = findViewById(R.id.imageNote);
        date.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date()));
        save.setOnClickListener(v -> saveNote());
        update.setOnClickListener(v -> updateNote());
        back.setOnClickListener(v -> openPage(NoteOverview.class));
        if(getIntent().getBooleanExtra("isViewOrUpdate",false))
        {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewUpdateNote();
        }
        findViewById(R.id.imageRemoveWebUrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textWebUrl.setText(null);
                layouttextWebUrl.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.imageRemoveImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               imageNote.setImageBitmap(null);
               imageNote.setVisibility(View.GONE);
               findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
               setImagePath("");
            }
        });
        layoutMiscellaneous.findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectNoteColor = "#121617";
            imageColor1.setImageResource(R.drawable.ic_check);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor1).performClick();
        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(v -> {
            selectNoteColor = "#E91E63";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_check);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectNoteColor = "#00BCD4";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_check);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectNoteColor = "#673AB7";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_check);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectNoteColor = "#FF9800";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_check);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor6).setOnClickListener(v -> {
            selectNoteColor = "#F44336";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(R.drawable.ic_check);
            imageColor7.setImageResource(0);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.viewColor7).setOnClickListener(v -> {
            selectNoteColor = "#8BC34A";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(R.drawable.ic_check);
            setViewColor(selectNoteColor);

        });
        layoutMiscellaneous.findViewById(R.id.layoutAddImage).setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(
                        NoteAdd.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION
                );
            }
            else
            {
                selectImage();
            }
        });
        layoutMiscellaneous.findViewById(R.id.layoutAddUrl).setOnClickListener(V -> showAddUrl());
        layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setOnClickListener(v -> deleteNote());
    }
    private void setViewUpdateNote()
    {

        save.setVisibility(View.GONE);
        update.setVisibility(View.VISIBLE);
        layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
       titleInput.setText(alreadyAvailableNote.getTitle());
       noteTextInput.setText(alreadyAvailableNote.getNoteText());
       date.setText(alreadyAvailableNote.getCurrent_Date());
        if (alreadyAvailableNote.getLink() != null && !alreadyAvailableNote.getLink().equals("null"))
        {
            textWebUrl.setText(alreadyAvailableNote.getLink());
            layouttextWebUrl.setVisibility(View.VISIBLE);
        }

       if(alreadyAvailableNote.getImagePath()!= null && !alreadyAvailableNote.getImagePath().trim().isEmpty()&&!alreadyAvailableNote.getImagePath().equals("null"))
       {
           imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getImagePath()));
           imageNote.setVisibility(View.VISIBLE);
           findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
           imagePath = alreadyAvailableNote.getImagePath();


       }

        if (!alreadyAvailableNote.getColor().trim().isEmpty() && alreadyAvailableNote.getColor() != null)
        {
            switch (alreadyAvailableNote.getColor())
            {
                case "#121617":
                    selectNoteColor = "#121617";
                    imageColor1.setImageResource(R.drawable.ic_check);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#E91E63":
                    selectNoteColor = "#E91E63";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(R.drawable.ic_check);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#00BCD4":
                    selectNoteColor = "#00BCD4";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(R.drawable.ic_check);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#673AB7":
                    selectNoteColor = "#673AB7";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(R.drawable.ic_check);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#FF9800":
                    selectNoteColor = "#FF9800";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(R.drawable.ic_check);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#F44336":
                    selectNoteColor = "#F44336";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(R.drawable.ic_check);
                    imageColor7.setImageResource(0);
                    setViewColor(selectNoteColor);
                    break;
                case "#8BC34A":
                    selectNoteColor = "#8BC34A";
                    imageColor1.setImageResource(0);
                    imageColor2.setImageResource(0);
                    imageColor3.setImageResource(0);
                    imageColor4.setImageResource(0);
                    imageColor5.setImageResource(0);
                    imageColor6.setImageResource(0);
                    imageColor7.setImageResource(R.drawable.ic_check);
                    setViewColor(selectNoteColor);
                    break;
            }
        }
    }
    private void saveNote ()
    {
        if(titleInput.getText().toString().isEmpty() && noteTextInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Title and Text is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else if(titleInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Title is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else if( noteTextInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "The Text is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String title , text , link= null;
            title = titleInput.getText().toString().trim();
            text = noteTextInput.getText().toString().trim();
            currentDate = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date());
            if(layouttextWebUrl.getVisibility() == View.VISIBLE) {
                link = textWebUrl.getText().toString();
            }
            final Note note = new Note();
            note.setTitle(title);
            note.setNoteText(text);
            note.setCurrent_Date(currentDate);
            note.setColor(selectNoteColor);
            note.setImagePath(imagePath);
            if(link != null && !link.equals("null"))
            {
                note.setLink(link);
            }
            if(alreadyAvailableNote != null)
            {
                note.setId(alreadyAvailableNote.getId());
            }
            if(database.insert_note(note.getTitle(),note.getNoteText(),selectNoteColor,note.getImagePath(),note.getLink()))
            {
                //loadingDialog.startLoadingDialog();
                Toast.makeText(getApplicationContext(), "Successful added", Toast.LENGTH_LONG).show();
                Log.d("AddNote","Successful");
                openPage(NoteOverview.class);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to add the Note", Toast.LENGTH_LONG).show();
                Log.d("AddNote","Failed");
            }
        }

    }
    private void updateNote ()
    {
        if(titleInput.getText().toString().isEmpty() && noteTextInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Title and Text is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else if(titleInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Title is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else if( noteTextInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "The Text is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String title , text , link= null;
            title = titleInput.getText().toString().trim();
            text = noteTextInput.getText().toString().trim();
            currentDate = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date());
            if(layouttextWebUrl.getVisibility() == View.VISIBLE) {
                link = textWebUrl.getText().toString();
            }
            final Note note = new Note();
            note.setTitle(title);
            note.setNoteText(text);
            note.setCurrent_Date(currentDate);
            note.setColor(selectNoteColor);
            note.setImagePath(imagePath);
            note.setLink(link);
            if(alreadyAvailableNote != null)
            {
                note.setId(alreadyAvailableNote.getId());
            }
            if(database.update_note(note.getTitle(),note.getNoteText(),selectNoteColor,note.getImagePath(),note.getLink(),alreadyAvailableNote.getId()))
            {
                save.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Successful updated", Toast.LENGTH_LONG).show();
                Log.d("AddNote","Successful");
                openPage(NoteOverview.class);
                finish();
            }
            else
            {
                Log.d("UpdateNote: ","Failed");
            }
        }

    }
    private void deleteNote ()
    {
        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(this);
        alert.setTitle(R.string.delete_note);
        alert.setMessage(R.string.delete_note_text);
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(database.delete_note(alreadyAvailableNote.getId()))
                {
                    layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "field to delete the Note",
                            Toast.LENGTH_SHORT).show();
                }
                openPage(NoteOverview.class);
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.create().dismiss();
            }
        });
        alert.create().show();

    }
    public void openPage(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }


    public void setViewColor(String color)
    {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(Color.parseColor(color));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }
    @SuppressLint("QueryPermissionsNeeded")
    private void selectImage ()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0)
        {
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                selectImage();
            }
            else
            {
                Toast.makeText(this,"Permission Denied!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK)
        {
            if(data!=null)
            {
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null)
                {
                    try{
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);
                        findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
                        setImagePath(getPathFromUri(selectedImageUri));

                    }catch (Exception exception)
                    {
                        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    private String getPathFromUri(Uri contentUri)
    {
        String filePath;
        Cursor cursor = getContentResolver().query(
                contentUri,null,null,null,null
        );
        if (cursor == null)
        {
            filePath = contentUri.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
    private void showAddUrl()
    {

        if (dialogAddUrl == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteAdd.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_add_url,
                    findViewById(R.id.layoutAddUrl), false);
            builder.setView(view);
            dialogAddUrl = builder.create();
            if (dialogAddUrl.getWindow() != null)
            {
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialogAddUrl.show();
            view.findViewById(R.id.textAdd);
            view.setOnClickListener(v -> {
                EditText inputUrl = view.findViewById(R.id.inputUrl);
                if (inputUrl.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NoteAdd.this, "Enter URl", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                    Toast.makeText(NoteAdd.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                } else {
                    layouttextWebUrl.setVisibility(View.VISIBLE);
                    textWebUrl.setText(inputUrl.getText().toString());
                    dialogAddUrl.dismiss();
                }
            });
            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogAddUrl.dismiss());

        }
        else
        {
            dialogAddUrl.show();
        }

    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}