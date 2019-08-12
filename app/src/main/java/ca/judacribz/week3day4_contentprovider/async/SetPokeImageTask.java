package ca.judacribz.week3day4_contentprovider.async;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import ca.judacribz.week3day4_contentprovider.models.Pokemon;

public class SetPokeImageTask extends AsyncTask<Pokemon, Void, Pokemon> {
    private static final String URL_GAMEPRESS = "https://pokemongo.gamepress.gg%s";

    private ImageDownloadedListener imageDownloadedListener;

    public interface ImageDownloadedListener {
        void onPokeImageSet(Pokemon pokemon);
    }

    public SetPokeImageTask(ImageDownloadedListener imageDownloadedListener) {
        this.imageDownloadedListener = imageDownloadedListener;
    }

    @Override
    protected Pokemon doInBackground(Pokemon... pokeList) {
        Pokemon pokemon = pokeList[0];
        String url = pokemon.getPicUrl();
        try {
            pokemon.setPicture(
                    url != null ?
                            BitmapFactory.decodeStream(new URL(url).openStream()) :
                            null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pokemon;
    }

    @Override
    protected void onPostExecute(Pokemon pokemon) {
        imageDownloadedListener.onPokeImageSet(pokemon);
    }
}