package com.example.q3z;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CombatActivity extends Activity {
    //Cosas de la UI
    TextView enemyName;
    TextView enemyHealth;
    Button attack;
    Button magic;
    ImageView enemySprite;
    TextView playerName;
    TextView playerHealth;
    //Variables del enemigo
    int enemyHP=100;
    String enemyString= "Ataulfo";
    GameCharacter enemy= new GameCharacter("Ataulfo",100,5,R.drawable.pha);
    //Variables del jugador
    GameCharacter player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat_layout);
        initialize();
    }
    void initialize(){
        player=MainActivity.player;
        enemyName=findViewById(R.id.enemyName);
        enemyHealth=findViewById(R.id.enemyHP);
        attack=findViewById(R.id.attackButton);
        magic=findViewById(R.id.magicButon);
        enemySprite=findViewById(R.id.enemySprite);
        enemyName.setText(enemy.name);
        enemyHealth.setText("HP: "+enemy.hp);
        enemySprite.setImageResource(enemy.spriteID);
        playerName=findViewById(R.id.playerName);
        playerHealth=findViewById(R.id.playerHP);
        playerName.setText(player.name);
        playerHealth.setText("HP: "+player.hp);
        enemy.setVersus(player);
        player.setVersus(enemy);
    }
    void UpdateScreen(){
        enemyHealth.setText("HP: "+enemy.hp);
        if(enemy.hp<=0){
            System.out.println("entra al if");
            player.gainXp(200);
            System.out.println("Gana xp");
            finish();
            System.out.println("hace el finish");
            Toast.makeText(this,"Enemigo derrotado",Toast.LENGTH_SHORT).show();
            System.out.println("Entra al Toast");
        }
        playerHealth.setText("HP: "+player.hp);
        if (player.hp<=0){
            finish();
            Toast.makeText(this,"Jugador derrotado",Toast.LENGTH_SHORT).show();
        }
    }
    void EnemyAttack(){
        enemy.Attack();
        UpdateScreen();
    }
    public void Attack(View view){
        //Ataca
        player.Attack();
        UpdateScreen();
        EnemyAttack();
    }
    public void Magic(View view){
        //Usa magia
        player.Skill();
        UpdateScreen();
        EnemyAttack();
    }
}
