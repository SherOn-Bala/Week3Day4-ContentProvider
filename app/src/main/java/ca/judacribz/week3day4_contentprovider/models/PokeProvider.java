package ca.judacribz.week3day4_contentprovider.models;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Locale;

import static ca.judacribz.week3day4_contentprovider.models.PokeContract.*;
import static ca.judacribz.week3day4_contentprovider.models.PokeProviderContract.*;

public class PokeProvider extends ContentProvider {
    public static final UriMatcher uriMatcher = buildUriMatcher();
    private PokeHelper pokeHelper;
    public static final int POKEMON = 100;
    public static final int POKE_ITEM = 101;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_POKEMON, POKEMON);
        matcher.addURI(CONTENT_AUTHORITY, PATH_POKEMON + "/#", POKE_ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        pokeHelper = new PokeHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = pokeHelper.getWritableDatabase();
        Cursor retCursor = null;
        switch(uriMatcher.match(uri)) {
            case POKE_ITEM:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        PokeEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case POKEMON:
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case POKEMON:
                return PokeEntry.CONTENT_TYPE;
            case POKE_ITEM:
                return PokeEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnUri;
        long _id;
        SQLiteDatabase writableDatabase = pokeHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case POKEMON:
                _id = writableDatabase.insert(TABLE_NAME, null, contentValues);
                if(_id > 0){
                    returnUri =  PokeEntry.buildPokemonUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase writableDatabase = pokeHelper.getWritableDatabase();
        int rowsAffected = writableDatabase.delete(
                TABLE_NAME,
                String.format(Locale.US, "WHERE %s = ", COL_ID),
                strings);
        if(rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase writeableDatabase = pokeHelper.getWritableDatabase();
        int rowsAffected =  writeableDatabase.update(
                TABLE_NAME,
                contentValues,
                String.format(Locale.US, "WHERE %s = ", COL_ID),
                strings);
        if(rowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsAffected;
    }
}
