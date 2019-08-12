package ca.judacribz.week3day4_contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import ca.judacribz.week3day4_contentprovider.models.Pokemon;

import static ca.judacribz.week3day4_contentprovider.models.PokeContract.*;

public class Util {

    /* Helper Functions ----------------------------------------------------------------------- */
    public static String getBase64FromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);

        return Base64.encodeToString(blob.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap getBitmapFromBase64(String imgStr) {
        byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Pokemon getPokemon(Cursor cursor, int id) {
        return new Pokemon(
                id != -1 ? id : cursor.getInt(cursor.getColumnIndex(COL_ID)),
                cursor.getString(cursor.getColumnIndex(COL_NAME)),
                getBitmapFromBase64(cursor.getString(cursor.getColumnIndex(COL_PICTURE))),
                cursor.getInt(cursor.getColumnIndex(COL_ATTACK)),
                cursor.getInt(cursor.getColumnIndex(COL_DEFENSE)),
                cursor.getInt(cursor.getColumnIndex(COL_HP))
        );
    }

    public static ContentValues createContentValues(Pokemon Pokemon) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, Pokemon.getId());
        contentValues.put(COL_NAME, Pokemon.getName());
        contentValues.put(COL_PICTURE, getBase64FromBitmap(Pokemon.getPicture()));
        contentValues.put(COL_ATTACK, Pokemon.getAttack());
        contentValues.put(COL_DEFENSE, Pokemon.getDefense());
        contentValues.put(COL_HP, Pokemon.getHp());

        return contentValues;
    }
    /* --- END --- Helper Functions ------------------------------------------------------------ */
}
