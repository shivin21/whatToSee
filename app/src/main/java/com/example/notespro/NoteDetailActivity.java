package com.example.notespro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailActivity extends AppCompatActivity {
    EditText titleEditText,contentEditText;
    ImageButton saveNoteBtn;
    TextView deleteNoteViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        titleEditText = findViewById(R.id.notes_content_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        deleteNoteViewBtn = findViewById(R.id.delete_note_text_view_btn);

        saveNoteBtn.setOnClickListener((v) -> saveNote());
        deleteNoteViewBtn.setOnClickListener((v)-> deleteNoteFromFirebase ());
    }
        void saveNote() {
            String noteTitle = titleEditText.getText().toString();
            String noteContent = contentEditText.getText().toString();
            if (noteTitle == null || noteTitle.isEmpty()) {
                titleEditText.setError("Title is required");
                return;

            }
            Note note = new Note();
            note.setTitle(noteTitle);
            note.setContent(noteContent);
            note.setTimestamp(Timestamp.now());
            saveNoteToFirebase(note);
        }
        void saveNoteToFirebase(Note note){
            DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForNotes().document();
            documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Utility.showToast(NoteDetailActivity.this,"Note Added Successfully");

                }else{
                        Utility.showToast(NoteDetailActivity.this,"Failed white adding note");
                    }
                }
            });

        }
    void deleteNoteFromFirebase(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is deleted
                    Utility.showToast(NoteDetailActivity.this,"Note deleted successfully");
                    finish();
                }else{
                    Utility.showToast(NoteDetailActivity.this,"Failed while deleting note");
                }
            }
        });
    }
    }