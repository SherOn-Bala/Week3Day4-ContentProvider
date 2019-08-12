package ca.judacribz.week3day4_contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import ca.judacribz.week3day4_contentprovider.async.PokeInfoTask;
import ca.judacribz.week3day4_contentprovider.async.SetPokeImageTask;
import ca.judacribz.week3day4_contentprovider.list.PokeList;
import ca.judacribz.week3day4_contentprovider.models.PokeHelper;
import ca.judacribz.week3day4_contentprovider.models.Pokemon;

public class MainActivity extends AppCompatActivity implements
        PokeInfoTask.PokeInfoRetrievedListener {

    private static final String HTML_FILE = "poke_list.html";
    private PokeHelper pokeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokeHelper = new PokeHelper(this);


        try {
            new PokeInfoTask(this, getAssets().open(HTML_FILE)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPokeInfoRetrieved(ArrayList<Pokemon> pokeList) {
        ArrayList<Pokemon> pokemons = pokeHelper.getAllPokemon();

        if (pokemons.size() != pokeList.size()) {
            for (Pokemon pokemon : pokeList) {
                if (pokemon.getPicture() == null) {
                    pokemon.setPicture(BitmapFactory.decodeResource(
                            getResources(),
                            R.drawable.no_img
                    ));
                }

                new SetPokeImageTask(pokeHelper).execute(pokemon);
            }
        }
    }

    public void seePokemon(View view) {
        startActivity(new Intent(this, PokeList.class));
    }
}
