package com.example.sta_op_hulp.client.zorgpersoneel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.ClientActivity;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;

public class ViewRequestFragment extends Fragment {
    private static Context context;
    private RecyclerView recyclerView;
    private AdapterRequestZorgpersoneel adapter;
    private View root;
    private TextView emptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_view_request, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterRequestZorgpersoneel(getContext(), getNotAcceptedZorgpersoneelClient(this));
        recyclerView.setAdapter(adapter);
        emptyView = (TextView) root.findViewById(R.id.empty_view);
        if (getNotAcceptedZorgpersoneelClient(this).isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        adapter.setOnItemClickListener(new AdapterRequestZorgpersoneel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, int item) {
                openRequest(obj);
            }
        });

        adapter.setOnItemClickLongListener(new AdapterRequestZorgpersoneel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, int item) {
                openRequest(obj);
            }

        });

        return root;
    }

    public List < ZorgpersoneelClient > getNotAcceptedZorgpersoneelClient(ViewRequestFragment viewRequestFragment) {
        List < ZorgpersoneelClient > LVZorgpersoneelClientNotAccepted = new ArrayList < > ();
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        try {
            String sql = "select * from " + TABLE_ZorgpersoneelClienten + " where " + COLUMN_NAME_CLIENTNUMMER + " = '" + ClientActivity.g.getGebruikersnummer() + "' and " + COLUMN_NAME_AKKOORD + " = 0";
            System.out.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int persooneelnummer = cursor.getInt(cursor.getColumnIndex("persooneelnummer"));
                    int clientnummer = cursor.getInt(cursor.getColumnIndex("clientnummer"));
                    int akkoord = cursor.getInt(cursor.getColumnIndex("akkoord"));
                    System.out.println(persooneelnummer);
                    com.example.sta_op_hulp.Model.ZorgpersoneelClient zc = new ZorgpersoneelClient(persooneelnummer, clientnummer, akkoord);

                    LVZorgpersoneelClientNotAccepted.add(zc);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        } finally {
            db.close();
        }
        return LVZorgpersoneelClientNotAccepted;
    }

    // opent het scherm om een product te bewerrken
    private void openRequest(ZorgpersoneelClient obj) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Product", obj);
        Navigation.findNavController(root).navigate(R.id.mangeRequestFragment, bundle);
    }
}