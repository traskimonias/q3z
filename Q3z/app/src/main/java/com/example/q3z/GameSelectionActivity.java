package com.example.q3z;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSelectionActivity extends AppCompatActivity {

    List<GameInfo> gameInfoList= new ArrayList<GameInfo>();
    List<TextView> gameLayout = new ArrayList<>();
    ConstraintLayout constraintLayout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        constraintLayout= findViewById(R.id.game_selection_layout);
        context=this;
        GetDirectories();
    }
    void GetDirectories(){
        File file = new File("sdcard/q3z");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        for(int i = 0; i< directories.length;i++){
            gameInfoList.add(GetGameInfoFromDirectory(directories[i]));
            TextView textView = new TextView(this);
            textView.setText(gameInfoList.get(0).name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,NarrativeActivity.class);
                    i.putExtra("chapterName","start.txt");
                    startActivity(i);
                }
            });
            constraintLayout.addView(textView);
            gameLayout.add(textView);
        }
        /*
        TextView textView = findViewById(R.id.nombresDeJuegos);
        textView.setText(gameInfoList.get(0).name);
        */
        System.out.println(Arrays.toString(directories));
    }
    GameInfo GetGameInfoFromDirectory(String dirname){
        File file = new File("sdcard/q3z/"+dirname+"/conf/info.txt");
        //Read the info file
        ArrayList<String> info= new ArrayList<String>();
        String line="";
        try{
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                info.add(line.substring(line.indexOf("=")+1).trim());
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        GameInfo gameInfo = new GameInfo(info.get(0),info.get(1),info.get(2),dirname);
        return gameInfo;
    }
}