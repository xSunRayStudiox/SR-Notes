package com.sun_ray.srnotes;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.model.Note;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Write_Notes extends AppCompatActivity {

    private OnBackPressedDispatcher onBackPressed;
    private ImageView back_Button, save_Note, fabColor_Pick;

    private RelativeLayout Screen_change;
    private int[] colorArray;
    private int ArgID, ArgIndex, colorIndex = 0;

    private float current_Rotation = 0f;
    private RotateAnimation rotateAnimation;

    private EditText et_Title, et_Content;
    private TextView set_date;
    private String date, Title, Des, ArgTitle, ArgDes;

    private NoteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);

        // initialize
        back_Button = findViewById(R.id.backButton);
        back_Button.setOnClickListener(v -> Back());
        
        et_Title = findViewById(R.id.etTitle);
        et_Content = findViewById(R.id.etContent);
        
        save_Note = findViewById(R.id.saveNote);
        database = NoteDatabase.getDB(this);

        fabColor_Pick = findViewById(R.id.fabColorPick);
        Screen_change = findViewById(R.id.noteContentFragmentParent);

        // Retrieve Colour Array
        TypedArray ta = getResources().obtainTypedArray(R.array.color_picker);
        colorArray = new int[ta.length()];

        for (int i = 0; i < ta.length(); i++){
            colorArray[i] = ta.getColor(i,0);
        }
        ta.recycle();

        // get Date
        date = new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        set_date = findViewById(R.id.get_date);

        // Set Date
        set_date.setText(date);

        // Get Argument
        ArgTitle = getIntent().getStringExtra("Title");
        ArgDes = getIntent().getStringExtra("Description");
        ArgID = getIntent().getIntExtra("ID",0);
        ArgIndex = getIntent().getIntExtra("Index",1);

        // Set Data
        if (ArgTitle != null){
            et_Title.setText(ArgTitle);
            et_Content.setText(ArgDes);
            colorIndex = ArgIndex;
        }

        // color set
        Screen_change.setBackgroundColor(colorArray[colorIndex]);

        fabColor_Pick.setOnClickListener(v -> {
            Rotate_Animation();
        });

        // Save Data
        save_Note.setOnClickListener(v -> {
            SaveNotes();
        });
    }

    private void Get_Data(){
        // get data
        Title = et_Title.getText().toString();
        Des = et_Content.getText().toString();
    }

    private void SaveNotes() {
        Get_Data();
        if (!Des.isEmpty()) {
            if (ArgTitle != null) {
                //Update
                database.noteDao().updateNote(new Note(ArgID,Title, Des, date,colorIndex));
                Back();
            } else {
                // Add
                database.noteDao().addNote(new Note(Title, Des, date,colorIndex));
                Back();
            }
        }
    }

    private void Back_Pressed(){
        onBackPressed = getOnBackPressedDispatcher();
        onBackPressed.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {Back();}
        });
    }

    private void Back(){
        Intent i = new Intent(getApplicationContext(),Activity_Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void Change_Color(){
        colorIndex++;
        if (colorIndex>= colorArray.length){
            colorIndex = 0;
        }
        Screen_change.setBackgroundColor(colorArray[colorIndex]);
    }

    private void Rotate_Animation(){
        current_Rotation += 120f;
        rotateAnimation = new RotateAnimation(
                current_Rotation-120f,
                current_Rotation,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        fabColor_Pick.startAnimation(rotateAnimation);
        Change_Color();
    }
}