package com.example.q3z;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExploreActivity extends Activity {
    GameMap map= new GameMap(10);
    Button up,down,left,right;
    TextView texto;
    int x, y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_layout);
        initialize();
    }
    void refresh(){
        if(map.map[x][y+1]!=0) up.setEnabled(true);
        else up.setEnabled(false);
        if(map.map[x][y-1]!=0) down.setEnabled(true);
        else down.setEnabled(false);
        if(map.map[x+1][y]!=0) right.setEnabled(true);
        else right.setEnabled(false);
        if(map.map[x-1][y]!=0) left.setEnabled(true);
        else left.setEnabled(false);
        switch (map.map[x][y]){
            case 1:
                texto.setText("Aquí no hay nada");
                break;
            case 2:
                texto.setText("Aquí has empezado");
                break;
            case 3:
                texto.setText("Aquí está la salida");
                break;
            case 4:
                texto.setText("Aquí hay un combate");
                combat();
                break;
                default: texto.setText("La liaste");
        }
    }
    void initialize(){
        up=findViewById(R.id.buttonUp);
        down=findViewById(R.id.buttonDown);
        left=findViewById(R.id.buttonLeft);
        right=findViewById(R.id.buttonRight);
        texto=findViewById(R.id.exploreText);
        x=5;
        y=5;
        refresh();
    }
    public void UP(View view){
        y+=1;
        refresh();
    }
    public void DOWN(View view){
        y-=1;
        refresh();
    }
    public void LEFT(View view){
        x-=1;
        refresh();
    }
    public void RIGHT(View view){
        x+=1;
        refresh();
    }
    void combat(){
        Intent i= new Intent(this,CombatActivity.class);
        startActivity(i);
    }
}
