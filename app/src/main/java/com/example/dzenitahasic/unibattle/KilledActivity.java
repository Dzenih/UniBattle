package com.example.dzenitahasic.unibattle;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dzenitahasic.unibattle.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class KilledActivity  extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killed);
        listView=(ListView)findViewById(R.id.list);

       db=openOrCreateDatabase("MonsterDB", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM monster", null);
        if(c.getCount()==0)
        {
            Log.d("Errors", "No records found");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        dataModels= new ArrayList<>();
        while(c.moveToNext())
        {
            dataModels.add(new DataModel("Monster", "Brutally murdered",c.getString(0) ,""));
        }
        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" Name: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}