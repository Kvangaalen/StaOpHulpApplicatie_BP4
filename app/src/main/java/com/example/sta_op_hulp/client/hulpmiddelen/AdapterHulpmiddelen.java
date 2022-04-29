package com.example.sta_op_hulp.client.hulpmiddelen;

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

import com.example.sta_op_hulp.Model.Hulpmiddel;
import com.example.sta_op_hulp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterHulpmiddelen extends RecyclerView.Adapter < RecyclerView.ViewHolder > implements View.OnClickListener {

    private ArrayList < Hulpmiddel > items = new ArrayList < > ();
    private Context ctx;
    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;
    private OnItemClickListener OnItemLongClickListener;
    public AdapterHulpmiddelen(Context context, List < Hulpmiddel > items) {
        this.items = (ArrayList < Hulpmiddel > ) items;
        ctx = context;
    }

    public void setOnItemClickLongListener(OnItemClickListener OnItemClickLongListener) {
        this.OnItemLongClickListener = OnItemClickLongListener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMoreButtonClickListener(OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Hulpmiddel obj, int item);

    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, Hulpmiddel obj, MenuItem item);

    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Hulpmiddel obj, MenuItem item);
    }

    // standaard onClick methode
    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stoelnummer, hoogte;
        public ImageButton more;
        public View lyt_parent;

        public ViewHolder(View v) {
            super(v);
            stoelnummer = (TextView) v.findViewById(R.id.hulpmiddelnummer);
            hoogte = (TextView) v.findViewById(R.id.stoelhoogte);
            more = (ImageButton) v.findViewById(R.id.imageButton);
            lyt_parent = (View) v.findViewById(R.id.mainLayout);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hulpmiddelen_row, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // aangeroepen door RecyclerView .....?
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Hulpmiddel o = items.get(position);

        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;

            view.stoelnummer.setText(String.valueOf(o.getStoelnummer()));
            view.hoogte.setText(String.valueOf(o.getGraden()));

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
            view.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMoreButtonClickListener == null) return;
                    onMoreButtonClick(view, o);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        int uniqueItemIdCount = items.size();
        return uniqueItemIdCount;
    }

    private void onMoreButtonClick(final View view, final Hulpmiddel Hulpmiddel) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, Hulpmiddel, item);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_people_more);
        popupMenu.show();
    }

}