package com.latynin.film;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ListFilm extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public TextView ret;
        public Button btn;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description =  itemView.findViewById(R.id.card_text);
            ret =  itemView.findViewById(R.id.ret);
            btn =  itemView.findViewById(R.id.action_button);
        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 10;
        private final String[] aName;
        private final String[] aFilmDesc;
        private final String[] aRet;
        private final AddClick[] aClick;
        private static List<Movie> movies = Controller.getMovies();
        public ImageView[] aIV;
        public ContentAdapter(Context context) {

            aName = new String[movies.size()];
            for (int i = 0; i < movies.size(); i++) {
                aName[i] = movies.get(i).getName();
            }
            aFilmDesc = new String[movies.size()];
            for (int i = 0; i < movies.size(); i++) {
                aFilmDesc[i] = movies.get(i).getDesk();
            }
            aRet = new String[movies.size()];
            for (int i = 0; i < movies.size(); i++) {
                aRet[i] = movies.get(i).getRet();
            }
            aClick = new AddClick[movies.size()];

            for (int i = 0; i < movies.size(); i++) {
                aClick[i] = new AddClick(i);
            }

            aIV = new ImageView[movies.size()];
            for (int i = 0; i < movies.size(); i++) {
                aIV[i] = new ImageView(context);
            }
            for (int i = 0; i < movies.size(); i++) {
                Picasso.with(context)
                        .load("http://image.tmdb.org/t/p/w185"+movies.get(i).getImgUrl())
                        .error(R.drawable.ic_perm_scan_wifi_black_24dp)
                        .placeholder(R.drawable.ic_perm_scan_wifi_black_24dp)
                        .into(aIV[i]);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(aName[position % aName.length]);
            holder.description.setText(aFilmDesc[position % aFilmDesc.length]);
            holder.ret.setText("Рейтинг: "+aRet[position % aRet.length]);
            holder.btn.setOnClickListener(aClick[position]);
            holder.picture.setImageDrawable(aIV[position].getDrawable());
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}
class AddClick implements View.OnClickListener{

    private int id;

    public AddClick(int id){
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase sql = MainActivity.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainActivity.dbHelper.KEY_NAME, Controller.getMovies().get(id).getName());
        sql.insert(MainActivity.dbHelper.TABLE_NAME,null,contentValues);
        ListAdded.loadDATA();
        MainActivity.tabs.getTabAt(1).select();
    }
}
