package com.example.q3z;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class NarrativeActivity extends Activity {
    private String gameTitle="majicas-del-copon";
    RelativeLayout relativeLayout;//Layaout en el que está repartida la escena
    ImageView backgroundView;//El fondo
    private NarrativeImageManager narrativeImageManager;
    ArrayList<ImageView> characters = new ArrayList<ImageView>( );//Todos los personajes que hay en escena
    TextView nameView; //Venatana con el nombre del personaje que está hablando
    TextView dialog; //Ventana con el texto que hablan los personajes
    int cont;//Contador de por qué parte del guión está
    String[] speech;//Array con todas las líneas del guion
    private MediaPlayer mediaPlayer; //Hace que suene la música
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrative_layout);
        initialize();
    }
    //Hacer que pare la música cuando no estás en la app
    @Override
    protected void onPause(){
        super.onPause();
        //Make the media player stop
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        //Make the media player start again
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }
    void initialize(){
        narrativeImageManager= new NarrativeImageManager(gameTitle);
        //Crear el layaout
        relativeLayout= findViewById(R.id.narrativeRelativeLayout);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(Color.GRAY);
        //Poner imagen de fondo
        backgroundView= new ImageView(this);
        //backgroundView.setImageResource(R.drawable.forest_background);
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
        dialog.setPadding(32,32,32,32);
        //Recoge el dialogo del capitulo
        cont=-1;
        speech=ChapterFromMemory(gameTitle,getIntent().getExtras().getString("chapterName"));
        System.out.println(speech);
        Next();

    }
    void Next(){
        cont+=1;
        if(cont<speech.length){
            speech[cont].trim();
            if(speech[cont].startsWith("#")){
                if(speech[cont].startsWith("#Add")){
                    //#Add directory/fileName.png@identifier
                    //characterInfo es lo que hay escrito después de "#Add "
                    dialog.setText("Aqui se añadiría a un personaje");
                    String characterInfo=speech[cont].substring(speech[cont].indexOf(" ")+1);
                    AddCharacter(characterInfo);
                }else if(speech[cont].startsWith("#Move")){
                    //#Move identifier/posx,posy
                    //characterInfo es lo que hay escrito despues de #Move
                    String characterInfo=speech[cont].substring(speech[cont].indexOf(" ")+1);
                    dialog.setText("Aquí se movería a un personaje");
                    MoveCharacter(characterInfo);
                }else if(speech[cont].startsWith("#Combat")){
                    dialog.setText("Aquí se iniciaría un combate");
                }else  if(speech[cont].startsWith("#Background")){
                    //#Background directory/fileName.jpg
                    String backgroundInfo=speech[cont].substring(speech[cont].indexOf(" ")+1);
                    ChangeBackground(backgroundInfo);
                    dialog.setText("Aquí se cambiaría el background");
                }else if(speech[cont].startsWith("#BGM")){
                    //#BGM fileName
                    String bgmInfo= speech[cont].substring(speech[cont].indexOf(" ")+1);
                    PlayBGM(bgmInfo);
                    dialog.setText("Aquí se cambiaría la bgm");
                }else if(speech[cont].startsWith("#Change")){
                    //#Change directory/fileName.png@identifier
                    String characterInfo=speech[cont].substring(speech[cont].indexOf(" ")+1);
                    ChangeCharacter(characterInfo);
                    dialog.setText("Aquí se cambia el sprite de algun personaje");
                }
                else if(speech[cont].startsWith("#Chapter")){
                    //#Chapter fileName.txt
                    String chapterInfo= speech[cont].substring(speech[cont].indexOf(" ")+1);
                    OpenNewChapter(chapterInfo);
                }else if(speech[cont].startsWith("#Jump")){
                    //#Jump markName
                    String jumpInfo= speech[cont].substring(speech[cont].indexOf(" ")+1);
                    JumpToLine(jumpInfo);
                }
                //Despues de estos eventos se pasa línea automáticamente
                Next();
            }else if(speech[cont].contains("-") && speech[cont].indexOf("-")<14){ //Antes del guión va el nombre de quien habla y después del guión quién lo dice
                String name= speech[cont].substring(0,speech[cont].indexOf("-"));
                String text= speech[cont].substring(speech[cont].indexOf("-"));
                dialog.setText(text);
                nameView.setText(nameTranslate(name));
            }
        }
    }
    void AddCharacter(String characterInfo){//añade un personaje a partir de su información
        try{
            //La info es carpetapersonaje/nombreimagen.png@identificador tiene que incluir la extensión del archivo
            String directory= characterInfo.substring(0,characterInfo.indexOf("/"));
            String fileName= characterInfo.substring(characterInfo.indexOf("/")+1,characterInfo.indexOf("@"));
            String identifier= characterInfo.substring(characterInfo.indexOf("@")+1);
            ImageView chara= new ImageView(this);
            chara.setImageBitmap(BitmapFromFilePath(gameTitle,directory,fileName));
            relativeLayout.addView(chara);
            narrativeImageManager.AddCharacter(chara,identifier);
            //characters.add(chara);
            dialog.bringToFront();
        }catch (Exception e){
            dialog.setText(e.getMessage()+ "\n"+characterInfo);
        }
    }
    void MoveCharacter(String characterInfo){
        //mueve la imagen de u personaje en pantalla
        //charname/x,y
        try {
            String charName=characterInfo.substring(0,characterInfo.indexOf("/"));
            int posX= Integer.parseInt(characterInfo.substring(characterInfo.indexOf("/")+1,characterInfo.indexOf(",")));
            int posY= Integer.parseInt(characterInfo.substring(characterInfo.indexOf(",")+1));
            ImageView charView= narrativeImageManager.GetViewFromIdentifier(charName);
            charView.setX(posX);
            charView.setY(posY);
        }catch (Exception e){
            dialog.setText(e.getMessage()+"\n"+characterInfo);
        }
    }
    void ChangeCharacter(String characterInfo){
        //La info carpetapersonaje/nombreimagen.png@identificador
        String directory= characterInfo.substring(0,characterInfo.indexOf("/"));
        String fileName= characterInfo.substring(characterInfo.indexOf("/")+1,characterInfo.indexOf("@"));
        String identifier= characterInfo.substring(characterInfo.indexOf("@")+1);
        try{
            ImageView charView= narrativeImageManager.GetViewFromIdentifier(identifier);
            charView.setImageBitmap(BitmapFromFilePath(gameTitle,directory,fileName));
        }catch (Exception e){
            dialog.setText(e.getMessage()+ "\n"+characterInfo);
        }
    }
    void ChangeBackground(String backgroundInfo){
        //La info es carpetaBackground/nombreBackground.jpeg
        try{
            String directory= backgroundInfo.substring(0,backgroundInfo.indexOf("/"));
            String fileName= backgroundInfo.substring(backgroundInfo.indexOf("/")+1);
            backgroundView.setImageBitmap(BitmapFromFilePath(gameTitle,directory,fileName));
        }catch (Exception e){
            dialog.setText(e.getMessage()+"\n"+backgroundInfo);
        }
    }
    void JumpToLine(String jumpInfo){
        for(int i=0; i<speech.length;i++){
            if(speech[i].startsWith("#Mark "+jumpInfo)){
                cont=i;
            }
        }
    }
    void PlayBGM(String bgmInfo){
        System.out.println(bgmInfo);
        try{
            String path = "/sdcard/q3z/"+gameTitle+"/sound"+"/"+bgmInfo;
            if(mediaPlayer==null){
                mediaPlayer = new  MediaPlayer();
            }else {
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            dialog.setText(e.getMessage()+ "\n"+bgmInfo);
        }
    }
    String nameTranslate(String initial){
        initial=initial.trim();
        String result= narrativeImageManager.GetNameFromAbreviation(initial);
        if(result!=null) return result;
        return initial;

    }
    private Bitmap BitmapFromFilePath(String gameName,String directory, String fileName){
        File imgFile= new File("sdcard/q3z/"+gameName+"/img/"+directory+"/"+fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        return bitmap;
    }
    private String[] ChapterFromMemory(String gameName, String chapterName){
        //Obterner el archivo de texto
        File file= new File("sdcard/q3z/"+gameName+"/chapters/"+chapterName);
        //Leer información del texto
        ArrayList<String> script= new ArrayList<String>();
        String line="";
        try{
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                script.add(line);
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return script.toArray(new String[0]);

    }
    private void OpenNewChapter(String chapterInfo){
        Intent intent= new Intent(this, NarrativeActivity.class);
        intent.putExtra("chapterName",chapterInfo);
        finish();
        startActivity(intent);
    }
}
