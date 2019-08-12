package ca.judacribz.week3day4_contentprovider.async;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.judacribz.week3day4_contentprovider.models.Pokemon;

public class PokeInfoTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {
    private static final String URL_GAMEPRESS = "https://pokemongo.gamepress.gg%s";
    private static final String URL_GAMEPRESS_POKELIST = String.format(
            URL_GAMEPRESS,
            "/pokemon-list"
    );

    private PokeInfoRetrievedListener pokeInfoRetrieved;
    private InputStream in;

    public interface PokeInfoRetrievedListener {
        void onPokeInfoRetrieved(ArrayList<Pokemon> pokeList);
    }

    public PokeInfoTask(PokeInfoRetrievedListener pokeInfoRetrieved, InputStream in) {
        this.pokeInfoRetrieved = pokeInfoRetrieved;
        this.in = in;
    }

    @Override
    protected ArrayList<Pokemon> doInBackground(Void... voids) {
        ArrayList<Pokemon> pokeList = new ArrayList<>();

        int id;
        String name, picUrl;
        int attack, defense, hp;

        ArrayList<Element>
                tdEls,
                aTagEls,
                statEls;

        int num;
        try {
            Document document = Jsoup.parse(in, null, URL_GAMEPRESS_POKELIST);

            for (Element element : document.getElementById("pokemon-table")
                    .getElementsByClass("list-row")) {

                tdEls = element.getElementsByTag("td");

                aTagEls = tdEls.get(1).getElementsByTag("a");
                statEls = tdEls.get(2).getElementsByClass("stat-cell");

                num = (aTagEls.size() < 3) ? 0 : 1;

                pokeList.add(new Pokemon(
                        Integer.valueOf(aTagEls.get(num).text().substring(1)), // id - pokemon #
                        aTagEls.get(num + 1).text(), // name
                        num == 0
                                ?
                                null :
                                String.format( // pokemon image url
                                        URL_GAMEPRESS,
                                        aTagEls.get(0).getElementsByTag("img").get(0).attr("src")
                                ),
                        Integer.valueOf(statEls.get(1).text()), // attack points
                        Integer.valueOf(statEls.get(2).text()), // defense points
                        Integer.valueOf(statEls.get(0).text())  // hp points
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pokeList;
    }

    @Override
    protected void onPostExecute(ArrayList<Pokemon> pokeList) {
        pokeInfoRetrieved.onPokeInfoRetrieved(pokeList);
    }
}