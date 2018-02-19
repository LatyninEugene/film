package com.latynin.film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Controller extends Thread {

    private final String KEY="b977c4a7d221efbbf49c2eefaa09ab2c";
    private static List<Movie> movies;
    @Override
    public void run() {
        super.run();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?page=1&language=ru-RU&region=RU&api_key="+KEY)
                .get()
                .build();
        try {
            movies = new ArrayList<>();
            okhttp3.Response response = client.newCall(request).execute();

            JSONObject o = new JSONObject(response.body().string());
            JSONArray a = o.getJSONArray("results");
            for (int i = 0; i < a.length(); i++) {
                System.out.println(a.getJSONObject(i).getString("title"));
                movies.add(new Movie(a.getJSONObject(i).getString("title"),
                        a.getJSONObject(i).getString("overview"),
                        a.getJSONObject(i).getString("poster_path"),
                        String.valueOf(a.getJSONObject(i).getDouble("vote_average"))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static List<Movie> getMovies() {
        return movies;
    }
}
