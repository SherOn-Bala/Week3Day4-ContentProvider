package ca.judacribz.week3day4_contentprovider.models;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Locale;

public class PokeProviderContract {
    public static final String CONTENT_AUTHORITY = "ca.judacribz.week3day4_contentprovider";
    public static final Uri BASE_CONTENT_ID = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_POKEMON = "pokemon";

    public static final class PokeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_POKEMON).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_POKEMON;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_POKEMON;

        public static Uri buildPokemonUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
