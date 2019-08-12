package ca.judacribz.week3day4_contentprovider.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.URL;

public class Pokemon {
    private int id;
    private String
            name,
            picUrl;
    Bitmap picture;

    int
            attack,
            defense,
            hp;

    public Pokemon(int id, String name, @Nullable String picUrl, int attack, int defense, int hp) {
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
//        Log.d("YOOOO", String.format("Pokemon: %d, %s, %d, %d, %d\n%s", id, name, attack, defense, hp, picUrl));
    }

    public Pokemon(int id, String name, @Nullable Bitmap picture, int attack, int defense, int hp) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Bitmap getPicture() {
        return picture;
    }

    private Thread setPicture() {
        if (picUrl != null) {
            return new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        setPicture(BitmapFactory.decodeStream(new URL(picUrl).openStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return null;
    }

    public void setPicture(@Nullable Bitmap picture) {
        this.picture = picture;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
