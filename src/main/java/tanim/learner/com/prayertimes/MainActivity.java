package tanim.learner.com.prayertimes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import tanim.learner.com.prayertimes.Util.JsonTask;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    TextView date;
    TextView[] timeTextView;
    SharedPreferences sharedPreferences;
    String city, country;
    final ThreadLocal<Toolbar> toolbar = new ThreadLocal<>();

    public MainActivity() {
        toolbar.set((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        city = preferences.getString("CITY", "Dhaka");
        country = preferences.getString("COUNTRY", "BN");

        setActionBar(toolbar.get());


        date = (TextView) findViewById(R.id.date);
        timeTextView = new TextView[5];
        timeTextView[0] = (TextView) findViewById(R.id.fazr_time);
        timeTextView[1] = (TextView) findViewById(R.id.dhuhr_time);
        timeTextView[2] = (TextView) findViewById(R.id.asr_time);
        timeTextView[3] = (TextView) findViewById(R.id.maghrib_time);
        timeTextView[4] = (TextView) findViewById(R.id.isha_time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd-MM-yyyy " );
        String d =  simpleDateFormat.format( new Date() );
        date.setText(d);

        JsonTask jsonTask = new JsonTask(timeTextView, this);
        jsonTask.execute("http://api.aladhan.com/timingsByCity?city=" + city + "&country=" + country);

    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
               Intent intent = new Intent(this, SettingsActivity.class);
                      startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}