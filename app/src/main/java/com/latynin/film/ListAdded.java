package com.latynin.film;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListAdded extends Fragment {

    static List<String> list = new ArrayList<>();
    static List<Integer> listid = new ArrayList<>();
    static ContentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadDATA();
        return recyclerView;
    }

    public static void loadDATA() {
        list = new ArrayList<>();
        listid = new ArrayList<>();
        SQLiteDatabase sql = MainActivity.dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            do {
                list.add(cursor.getString(nameIndex));
                listid.add(cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageButton btn;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            name = itemView.findViewById(R.id.list_title);
            btn = itemView.findViewById(R.id.imageButton);
        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private String[] aName;
        private DelClick[] delClicks;
        public ContentAdapter(Context context) {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            System.out.println(list.size());
            System.out.println(list.toString());
            delClicks = new DelClick[list.size()];
            aName = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                aName[i] = list.get(i);
            }
            for (int i = 0; i < list.size(); i++) {
                delClicks[i] = new DelClick(i,listid.get(i));
            }
            holder.name.setText(aName[position % aName.length]);
            holder.btn.setOnClickListener(delClicks[position]);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
class DelClick implements View.OnClickListener{

    private int id;
    private int dbid;

    public DelClick(int id, int dbid){
        this.id = id;
        this.dbid = dbid;
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase sql = MainActivity.dbHelper.getWritableDatabase();
        sql.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID +"="+ dbid, null);
        ListAdded.loadDATA();
    }
}
