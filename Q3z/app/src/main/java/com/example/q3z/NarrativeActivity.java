package com.example.q3z;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NarrativeActivity extends Activity {
    RelativeLayout relativeLayout;//Layaout en el que está repartida la escena
    ImageView backgroundView;//El fondo
    ArrayList<ImageView> characters = new ArrayList<ImageView>( );//Todos los personajes que hay en escena
    TextView nameView; //Venatana con el nombre del personaje que está hablando
    TextView dialog; //Ventana con el texto que hablan los personajes
    int cont;//Contador de por qué parte del guión está
    String[] speech;//Array con todas las líneas del guion
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrative_layout);
        initialize();
    }
    void initialize(){
        //Crear el layaout
        relativeLayout= findViewById(R.id.narrativeRelativeLayout);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(Color.GRAY);
        //Poner imagen de fondo
        backgroundView= new ImageView(this);
        backgroundView.setImageResource(R.drawable.forest_background);
        RelativeLayout.LayoutParams backgroundParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(backgroundView,params);
        //Poner un texto
        dialog=new TextView(this);
        dialog.setBackgroundResource(R.drawable.text_rectangle);
        dialog.setTextColor(Color.WHITE);
        dialog.setWidth(1920);

        dialog.setText("Puta vida esta mierda va a cachos");
        RelativeLayout.LayoutParams dialogParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dialogParams.leftMargin=30;
        dialogParams.rightMargin=30;
        dialogParams.bottomMargin=30;
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });
        relativeLayout.addView(dialog,dialogParams);
        //Poner el nombre de quien está hablando
        nameView= new TextView(this);
        RelativeLayout.LayoutParams nameParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        nameView.setBackgroundColor(Color.BLACK);
        nameView.setTextColor(Color.WHITE);
        relativeLayout.addView(nameView,nameParams);
        //Mete un usuario
        AddCharacter(R.drawable.dulcinea1);
        dialog.setPadding(32,32,32,32);
        MoveCharacter(0,500);
        //Recoge el dialogo del capitulo
        cont=-1;
        speech= getResources().getStringArray(R.array.Capitulo1);
        Next();

    }
    void Next(){
        cont+=1;
        if(cont<speech.length){
            if(speech[cont].startsWith("#")){
                if(speech[cont].startsWith("#Add")){
                    dialog.setText("Aqui se añadiría a un personaje");
                }else if(speech[cont].startsWith("#Move")){
                    dialog.setText("Aquí se movería a un personaje");
                }else if(speech[cont].startsWith("#Combat")){
                    dialog.setText("Aquí se iniciaría un combate");
                }else  if(speech[cont].startsWith("#Background")){
                    dialog.setText("Aquí se cambiaría el background");
                }else if(speech[cont].startsWith("#BGM")){
                    dialog.setText("Aquí se cambiaría la bgm");
                }else if(speech[cont].startsWith("#Change")){
                    dialog.setText("Aquí se cambia el sprite de algun personaje");
                }
            }else if(speech[cont].contains("-")){
                String name= speech[cont].substring(0,speech[cont].indexOf("-"));
                String text= speech[cont].substring(speech[cont].indexOf("-"));
                dialog.setText(text);
                nameView.setText(nameTranslate(name));
            }
        }
    }
    void AddCharacter(int characterResource){
        try {
            ImageView chara= new ImageView(this);
            chara.setImageResource(characterResource);
            relativeLayout.addView(chara);
            characters.add(chara);
            dialog.bringToFront();
        }catch (Exception e){
            dialog.setText(e.getMessage());
        }
    }
    void MoveCharacter(int chara ,int posx){
        try{
            characters.get(chara).setX(posx);
        }catch (Exception e){
            dialog.setText(e.getMessage());
        }
    }
    void ChangeCharacter(int chara, int newChara){
        try{
            characters.get(chara).setImageResource(newChara);
        }catch (Exception e){
            dialog.setText(e.getMessage());
        }
    }
    void ChangeBackground(int newBackground){
        try{
            backgroundView.setImageResource(newBackground);
        }catch (Exception e){
            dialog.setText(e.getMessage());
        }
    }
    String nameTranslate(String initial){
        switch (initial){
            case "H":
                return "Helena";
            case "T":
                return "Troya";
            case "M":
                return "María";
            case "D":
                return "Dulcinea";
            case "P":
                return "Profesora";
            case "L":
                return "Legend";
            default:
                return initial;
        }
    }
}
