package com.example.sta_op_hulp.client.zorgpersoneel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.Database.DataContract;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRequestZorgpersoneel extends RecyclerView.Adapter < RecyclerView.ViewHolder > implements View.OnClickListener {

    private ArrayList < ZorgpersoneelClient > items = new ArrayList < > ();
    private Context ctx;
    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;
    private AdapterRequestZorgpersoneel.OnItemClickListener OnItemLongClickListener;
    public AdapterRequestZorgpersoneel(Context context, List < ZorgpersoneelClient > items) {
        this.items = (ArrayList < ZorgpersoneelClient > ) items;
        ctx = context;
    }

    public void setOnItemClickLongListener(AdapterRequestZorgpersoneel.OnItemClickListener OnItemClickLongListener) {
        this.OnItemLongClickListener = OnItemClickLongListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMoreButtonClickListener(OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ZorgpersoneelClient obj, int item);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ZorgpersoneelClient obj, MenuItem item);

    }

    public interface OnItemLongClickListener {
        void onItemClick(View view, ZorgpersoneelClient obj, int item);
    }

    // standaard onClick methode
    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView naam, personeelnummer;
        public ImageButton more;
        public View lyt_parent;

        public ViewHolder(View v) {
            super(v);
            naam = (TextView) v.findViewById(R.id.naam);
            personeelnummer = (TextView) v.findViewById(R.id.textPersoneelnummer);
            more = (ImageButton) v.findViewById(R.id.imageButton);
            lyt_parent = (View) v.findViewById(R.id.mainLayout);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zorgpersoneel_row, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // aangeroepen door RecyclerView .....?
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ZorgpersoneelClient o = items.get(position);

        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            view.personeelnummer.setText(String.valueOf(o.getPersoneelnummer()));

            DatabaseHelper dbH = new DatabaseHelper(ctx.getApplicationContext());
            SQLiteDatabase db = dbH.getReadableDatabase();
            try {
                String sql = "select * from " + DataContract.Gebruiker.TABLE_Gebruikers + " Where  gebruikersnummer = '" + o.getPersoneelnummer() + "'";
                Cursor cursor = db.rawQuery(sql, null);
                // ophalen van productnaam

                if (cursor.moveToFirst()) {
                    do {
                        String naam = cursor.getString(cursor.getColumnIndex("naam"));
                        view.naam.setText(String.valueOf(naam));
                    } while (cursor.moveToNext());
                } else {
                    view.naam.setText(String.valueOf("Gebruiker onbekend"));
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }


            // set OnClick Listener
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(view, o, position);
                }
            });

            // set LongClick Listener
            view.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    OnItemLongClickListener.onItemClick(v, o, position);
                    return false;
                }
            });

            // set onClick more Listener

        }

    }

    @Override
    public int getItemCount() {
        int uniqueItemIdCount = items.size();
        return uniqueItemIdCount;
    }

    private void onMoreButtonClick(final View view, final ZorgpersoneelClient zorgpersoneelClient) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, zorgpersoneelClient, item);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_people_more);
        popupMenu.show();
    }

}