package com.example.q3z;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static GameCharacter player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player= new GameCharacter("Jugador",100,10,R.drawable.phw);

    }
    public void Combat(View view){
        Intent i = new Intent(this,NarrativeActivity.class);
        i.putExtra("chapterName","start.txt");
        startActivity(i);
    }
    public void Dialog(View view){
        Intent i = new Intent(this,DialogActivity.class);
        startActivity(i);
    }
}
