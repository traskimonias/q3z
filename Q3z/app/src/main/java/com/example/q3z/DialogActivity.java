package com.example.q3z;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogActivity extends Activity {
    TextView texto;
    ImageView character;
    GameCharacter gameCharacter;
    int cont;
    String[] speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        initialize();
    }
    void initialize(){
        speech= getResources().getStringArray(R.array.Capitulo1);
        cont=0;
        character=findViewById(R.id.dialogSprite);
        gameCharacter= new GameCharacter("Sabio",1,10,R.drawable.dulcinea1);
        character.setImageResource(gameCharacter.spriteID);
        texto=findViewById(R.id.dialogText);
        texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });
        texto.setText(speech[cont]);
    }
    void Next(){
        cont+=1;

        if(cont<speech.length){
            if(speech[cont].equals("combat")){
                Intent i = new Intent(this,CombatActivity.class);
                startActivity(i);
            }else if(speech[cont].contains("#change")){
                changeCharacter();
            }
            else {
                texto.setText(speech[cont]);
            }
            
        }else {
            finish();
        }
    }
    void changeCharacter(){
        character.setImageResource(R.drawable.dulcinea2);
    }

}
