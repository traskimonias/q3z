package com.example.q3z;


public class GameCharacter {
    String name;
    int hp;
    int maxhp;
    int attack;
    int defense;
    int spriteID;
    int level;
    int currentxp;
    int maxxp;
    GameCharacter versus; // a que character puede atacar
    public GameCharacter(String name, int hp, int attack, int spriteID){
        this.name=name;
        this.hp=hp;
        this.attack=attack;
        this.spriteID=spriteID;
    }

    public void setVersus(GameCharacter versus) {
        this.versus = versus;
    }

    public void Attack(){
        versus.Hurt(attack);
    }
    public void Skill(){
        //El personaje usa su habilidad
        attack+=2;
    }
    public void Hurt(int damage){
        hp-=damage;
        if(hp<=0){
            Die();
        }
    }
    void Die(){
        //Does something when it dies
    }
    void levelUp(){
        while (currentxp>maxxp){
            level+=1;
            attack+=2;
            defense+=2;
            maxhp+=20;
            hp=maxhp;
            currentxp-=50;
        }
    }
    public void gainXp(int xp){
        currentxp+=xp;
        levelUp();
    }
}
