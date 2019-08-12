package ca.judacribz.week3day4_contentprovider.models;

import java.util.Locale;

public class PokeContract {
    public static final String DATABASE_NAME = "pokemon_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Pokemon_Table";
    public static final String STMT_CREATE_TABLE =
            "CREATE TABLE %s( %s INTEGER, %s TEXT PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PICTURE = "picture";
    public static final String COL_ATTACK = "attack";
    public static final String COL_DEFENSE = "defense";
    public static final String COL_HP = "hp";

    public static final String[] COLS = new String[]{
            COL_ID,
            COL_NAME,
            COL_PICTURE,
            COL_ATTACK,
            COL_DEFENSE,
            COL_HP
    };

    public static final String DROP_TABLE_QUERY = "DROP TABLE " + TABLE_NAME;
    public static final String SELECT_ALL_POKEMON = "SELECT * FROM " + TABLE_NAME;


    public static String getCreateTableQuery() {
        return String.format(
                Locale.US,
                STMT_CREATE_TABLE,
                TABLE_NAME,
                COL_ID,
                COL_NAME,
                COL_PICTURE,
                COL_ATTACK,
                COL_DEFENSE,
                COL_HP
        );
    }

    public static String getPokemonByIdQuery(int id) {
        return String.format(
                Locale.US,
                "SELECT * FROM %s WHERE %s = \'%s\'",
                TABLE_NAME,
                COL_ID,
                id
        );
    }
}
