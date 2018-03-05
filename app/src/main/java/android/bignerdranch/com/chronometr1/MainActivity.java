package android.bignerdranch.com.chronometr1;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Chronometer mChronomer;
    long timeWhenStop;
    long timeBefReturn;
    boolean isStarted;

    private static  final String KEY_INDEX="index";
    private static  final String KEY_INDEX_IS_START="index_is_start";
    private static  final String KEY_INDEX_BEF_RET="index_bef_ret";
    private static  final String TAG="MainActivity";
//Тестовый комментарий
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mChronomer = findViewById(R.id.chronomer2);

//        if(savedInstanceState!=null){
//            timeWhenStop = savedInstanceState.getLong(KEY_INDEX);
//        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                if (!isStarted){
                 mChronomer.start();
                 mChronomer.setBase(SystemClock.elapsedRealtime()+timeWhenStop);
                 fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause));
                 isStarted = true;
                }else{
                    mChronomer.stop();
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
                    timeWhenStop= mChronomer.getBase()- SystemClock.elapsedRealtime();
                    isStarted=false;
                }
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
            public boolean onLongClick(View v){
            mChronomer.stop();
            mChronomer.setBase(SystemClock.elapsedRealtime());
            isStarted=false;
            timeWhenStop = 0;
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
            return true;
        }
        });
    }
    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        //mChronomer.setBase(mChronomer.getBase()- SystemClock.elapsedRealtime());

        timeWhenStop=saveInstanceState.getLong(KEY_INDEX);
        isStarted = saveInstanceState.getBoolean(KEY_INDEX_IS_START);
        timeBefReturn = saveInstanceState.getLong(KEY_INDEX_BEF_RET);

    //    Toast.makeText(this,String.valueOf(timeBefReturn),Toast.LENGTH_LONG).show();
        if (!isStarted){
            mChronomer.stop();
            mChronomer.setBase(SystemClock.elapsedRealtime()+timeWhenStop);
         }else {
            mChronomer.start();
            mChronomer.setBase(timeBefReturn);
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
      //  timeWhenReturn=timeWhenStop;
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putLong(KEY_INDEX,timeWhenStop);

        timeBefReturn = mChronomer.getBase();
        savedInstanceState.putLong(KEY_INDEX_BEF_RET,timeBefReturn);
        savedInstanceState.putBoolean(KEY_INDEX_IS_START,isStarted);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

}
