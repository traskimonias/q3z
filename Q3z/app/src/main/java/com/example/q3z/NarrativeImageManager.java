package com.example.q3z;

import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class NarrativeImageManager {
    //Para poder actuar sobre las imágenes que hay en pantalla
    //Lista de imágenes
    private ArrayList<ImageView> characterImages = new ArrayList<ImageView>( );
    //Lista de identificadores de imagen
    private ArrayList<String> characterIdentifiers= new ArrayList<String>();

    //Cambiar las iniciales por nombres en el diálogo
    private String[] abreviations;
    private String[] names;
    public NarrativeImageManager(String gameName){
        //Coger la informacion de los nombres/abreviaturas
        //Obterner el archivo de texto
        File file= new File("sdcard/q3z/"+gameName+"/conf/names.txt");
        //Leer información del texto
        ArrayList<String> nameList= new ArrayList<String>();
        ArrayList<String> abList= new ArrayList<String>();
        String line="";
        try{
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                abList.add(line.substring(0,line.indexOf("=")).trim());
                nameList.add(line.substring(line.indexOf("=")+1).trim());
            }
            br.close();
            names = nameList.toArray(new String[0]);
            abreviations= abList.toArray(new String[0]);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void AddCharacter(ImageView characterView,String characterIdentifier){
        characterImages.add(characterView);
        characterIdentifiers.add(characterIdentifier);
    }
    public ImageView GetViewFromIdentifier(String characterIdentifier) throws Exception {
        if(characterIdentifiers.contains(characterIdentifier)){
            return characterImages.get(characterIdentifiers.indexOf(characterIdentifier));
        }else{
            throw new Exception("Character with identifier "+ characterIdentifier+ " not found");
        }
    }
    public String GetNameFromAbreviation(String ab){
        for(int i=0; i<abreviations.length;i++){
            if(abreviations[i].equals( ab)){
                return names[i];
            }
        }
        return null;
    }

}
