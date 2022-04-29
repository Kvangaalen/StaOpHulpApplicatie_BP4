package com.example.sta_op_hulp.client.hulpmiddelen;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sta_op_hulp.Database.DataContract;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Hulpmiddel;
import com.example.sta_op_hulp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_DATUM;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_STOELNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_TIJDOPSTAAN;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_TIJDZIT;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.TABLE_Activiteiten;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_GEBRUIKERSNUMMER;

public class EditHulpmiddelFragment extends Fragment {
    private View view;
    private SeekBar seekbar;
    public static Hulpmiddel h;
    private TextView t, timer;
    private Button oplsaan, start, pause;
    int Seconds, Minutes, MilliSeconds;
    int graden;
    int step = 1;
    int max = 10;
    int min = 0;
    private String currentStartZitTime, currentDate, currentStartOpstaanTime;
    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    private Handler handler;

    public EditHulpmiddelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        h = getArguments().getParcelable("hulpmiddelen");
        view = inflater.inflate(R.layout.fragment_edit_hulpmiddel, container, false);

        t = (TextView) view.findViewById(R.id.textViewValue);
        oplsaan = view.findViewById(R.id.btnopslaan);
        timer = (TextView) view.findViewById(R.id.tvTimer);
        start = (Button) view.findViewById(R.id.btStart);
        pause = (Button) view.findViewById(R.id.btPause);

        seekbar = view.findViewById(R.id.seekBar);
        seekbar.setProgress(h.getGraden());
        t.setText(String.valueOf(h.getGraden()));
        seekbar.setMax((max - min) / step);
        handler = new Handler();



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(Calendar.getInstance().getTime());
                StartTime = SystemClock.uptimeMillis();
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentStartZitTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                System.out.println(currentStartZitTime);
                handler.postDelayed(runnable, 0);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                if (currentStartZitTime == null || currentDate == null) {
                    System.out.println("String is null");
                } else {
                    DatabaseHelper dbH = new DatabaseHelper(getContext());
                    SQLiteDatabase db = dbH.getWritableDatabase();
                    currentStartOpstaanTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    try {
                        ContentValues cv = new ContentValues();
                        cv.put(COLUMN_NAME_GEBRUIKERSNUMMER, h.getGebruikersnummer());
                        cv.put(COLUMN_NAME_STOELNUMMER, h.getStoelnummer());
                        cv.put(COLUMN_NAME_TIJDZIT, currentStartZitTime);
                        cv.put(COLUMN_NAME_TIJDOPSTAAN, currentStartOpstaanTime);
                        cv.put(COLUMN_NAME_DATUM, currentDate);

                        long result = db.insert(TABLE_Activiteiten, null, cv);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        t = (TextView) view.findViewById(R.id.textViewValue);
                        t.setText(String.valueOf(progress));
                        graden = progress;
                        double value = min + (progress * step);
                    }
                }
        );

        oplsaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opslaanStoelinstellingen();
            }
        });
        return view;
    }

    private void opslaanStoelinstellingen() {
        DatabaseHelper dbH = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataContract.Hulpmiddel.COLUMN_NAME_GRADEN, graden);
        try {
            // controleren of lijstnaam niet leeg is.

            // opslaan in productlijst table
            long result = db.update(DataContract.Hulpmiddel.TABLE_Hulpmiddelen, cv, "stoelnummer=?", new String[] {
                    String.valueOf(h.getStoelnummer())
            });
            if (result == -1) {

                Toast.makeText(getContext(), "Failed2", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getContext(), "Stoel instelling aangepast", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int)(UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int)(UpdateTime % 1000);

            timer.setText("" + Minutes + ":" +
                    String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };
}