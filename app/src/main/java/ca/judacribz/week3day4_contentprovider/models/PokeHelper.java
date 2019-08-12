package ca.judacribz.week3day4_contentprovider.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import ca.judacribz.week3day4_contentprovider.R;
import ca.judacribz.week3day4_contentprovider.async.SetPokeImageTask;

import static ca.judacribz.week3day4_contentprovider.Util.*;
import static ca.judacribz.week3day4_contentprovider.models.PokeContract.*;


public class PokeHelper extends SQLiteOpenHelper implements
        SetPokeImageTask.ImageDownloadedListener {

    public PokeHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
        onCreate(sqLiteDatabase);
    }


    /* Create --------------------------------------------------------------------------------- */
    public void insertPokemon(Pokemon pokemon) {
        if (getPokemonByName(pokemon.getName()) == null) {

            SQLiteDatabase database = this.getWritableDatabase();

            Log.d("YOOO", "insertPokemon: " +
                    database.insert(TABLE_NAME, null, createContentValues(pokemon))
            );
        }
    }

    /* Retrieve ------------------------------------------------------------------------------- */
    public ArrayList<Pokemon> getAllPokemon() {
        return getPokemonListUsingQuery(SELECT_ALL_POKEMON);
    }

    private ArrayList<Pokemon> getPokemonListUsingQuery(String query) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        ArrayList<Pokemon> pokeList = new ArrayList<>();
        ;

        Cursor cursor = readableDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                pokeList.add(getPokemon(
                        cursor,
                        cursor.getInt(cursor.getColumnIndex(COL_ID))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return pokeList;
    }

    public Pokemon getPokemonById(int id) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Pokemon Pokemon = null;

        Cursor cursor = readableDatabase.rawQuery(getPokemonByIdQuery(id), null);

        if (cursor.moveToFirst()) {
            Pokemon = getPokemon(cursor, id);
        }
        cursor.close();
        return Pokemon;
    }

    public Pokemon getPokemonByName(String name) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Pokemon Pokemon = null;

        Cursor cursor = readableDatabase.rawQuery(getPokemonByNameQuery(name), null);

        if (cursor.moveToFirst()) {
            Pokemon = getPokemon(cursor, -1);
        }
        cursor.close();
        return Pokemon;
    }


    /* Update --------------------------------------------------------------------------------- */
    public void updatePokemon(Pokemon Pokemon) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.update(
                TABLE_NAME,
                createContentValues(Pokemon),
                COL_ID + " = ?",
                new String[]{String.valueOf(Pokemon.getId())}
        );
    }

    /* Delete --------------------------------------------------------------------------------- */
    public void deleteCar(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void onPokeImageSet(Pokemon pokemon) {
        insertPokemon(pokemon);
    }
}
