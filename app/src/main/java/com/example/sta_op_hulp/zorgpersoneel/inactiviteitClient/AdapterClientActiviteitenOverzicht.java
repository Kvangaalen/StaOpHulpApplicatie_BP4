package com.example.sta_op_hulp.zorgpersoneel.inactiviteitClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.Model.Activiteit;
import com.example.sta_op_hulp.R;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AdapterClientActiviteitenOverzicht extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private ArrayList<Activiteit> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;
    private AdapterClientActiviteitenOverzicht.OnItemLongClickListener OnItemLongClickListener;
    private Object ViewHolder;

    public AdapterClientActiviteitenOverzicht(Context context, List<Activiteit> items) {
        this.items = (ArrayList<Activiteit>) items;
        ctx = context;
    }

    public void setOnItemClickLongListener(AdapterClientActiviteitenOverzicht.OnItemLongClickListener OnItemClickLongListener) {
        this.OnItemLongClickListener = (AdapterClientActiviteitenOverzicht.OnItemLongClickListener) OnItemClickLongListener;
    }

    public void setOnMoreButtonClickListener(OnMoreButtonClickListener OnMoreButtonClickListener) {
        this.onMoreButtonClickListener = OnMoreButtonClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, Activiteit obj, int item);
    }


    public interface OnMoreButtonClickListener {
        void onItemClick(View view, Activiteit ArrayList, MenuItem item);
    }

    public interface OnItemLongClickListener{
        void onItemClick(View view, Activiteit obj, int item);
    }

    // standaard onClick methode
    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eamnummer, totaaltijd, productgroep, tijd;
        public ImageButton more;
        public View lyt_parent;

        public ViewHolder(View v) {
            super(v);
            eamnummer = (TextView) v.findViewById(R.id.naam);
            tijd = (TextView) v.findViewById(R.id.textTijd);
            productgroep = (TextView) v.findViewById(R.id.textNaam);
            more = (ImageButton) v.findViewById(R.id.imageButton);
            lyt_parent = (View) v.findViewById(R.id.mainLayout);
            totaaltijd = (TextView) v.findViewById(R.id.textTijd);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inactiviteit_row, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // aangeroepen door RecyclerView .....?
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Activiteit o = items.get(position);

        if (holder instanceof AdapterClientActiviteitenOverzicht.ViewHolder) {
            AdapterClientActiviteitenOverzicht.ViewHolder view = (AdapterClientActiviteitenOverzicht.ViewHolder) holder;
            LocalTime t1 = LocalTime.parse(o.getTijdzit());
            LocalTime t2 = LocalTime.parse(o.getTijdopstaan());
            Duration diff = Duration.between(t1, t2);

            view.eamnummer.setText(o.getTijdzit() + " " + o.getTijdopstaan()  );
            view.productgroep.setText(o.getDatum());
            view.totaaltijd.setText(String.valueOf(diff.toMinutes()));

//            DatabaseHelper dbH = new DatabaseHelper(ctx.getApplicationContext());
//            SQLiteDatabase db = dbH.getReadableDatabase();
//
//            String sql = "select * from " + DataContract.Gebruiker.TABLE_Gebruikers + " Where  gebruikersnummer = '" + o.() + "'";
//            Cursor cursor = db.rawQuery(sql, null);
//            // ophalen van productnaam
//            if (cursor.moveToFirst()) {
//                do {
//                    String naam = cursor.getString(cursor.getColumnIndex("naam"));
//                    view.naam.setText(String.valueOf(naam));
//                }  while (cursor.moveToNext());
//            } else{
//                view.naam.setText(String.valueOf("Gebruiker onbekend"));
//            }
//            cursor.close();
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




        }

    }

    @Override
    public int getItemCount() {
        int uniqueItemIdCount = items.size();
        return uniqueItemIdCount;
    }

    private void onMoreButtonClick(final View view, final Activiteit Activiteit) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, Activiteit, item);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_people_more);
        popupMenu.show();
    }

}
