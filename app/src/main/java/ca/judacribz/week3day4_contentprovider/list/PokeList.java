package ca.judacribz.week3day4_contentprovider.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

import ca.judacribz.week3day4_contentprovider.R;
import ca.judacribz.week3day4_contentprovider.models.PokeProviderContract.*;
import ca.judacribz.week3day4_contentprovider.models.Pokemon;

import static ca.judacribz.week3day4_contentprovider.models.PokeContract.*;
import static ca.judacribz.week3day4_contentprovider.Util.*;
import static ca.judacribz.week3day4_contentprovider.models.PokeProviderContract.PokeEntry.CONTENT_URI;

public class PokeList extends AppCompatActivity {

    RecyclerView rvPokeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_list);
        rvPokeList = findViewById(R.id.rvPokeList);
        rvPokeList.setLayoutManager(new LinearLayoutManager(this));
        getPokemonWithContentProvider();
    }

    public void getPokemonWithContentProvider() {
        Cursor cursor = getContentResolver().query(CONTENT_URI,  COLS, null, null, null);
        if (cursor != null) {
            ArrayList<Pokemon> pokeList = new ArrayList<>();
            while (cursor.moveToNext()) {
                pokeList.add(getPokemon(
                        cursor,
                        cursor.getInt(cursor.getColumnIndex(COL_ID))
                ));
            }

            rvPokeList.setAdapter(new PokeAdapter(pokeList));

        }
    }
}
