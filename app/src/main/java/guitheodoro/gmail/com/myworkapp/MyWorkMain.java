package guitheodoro.gmail.com.myworkapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkMain extends AppCompatActivity {

    private int sckbar = 1;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_main);

        BottomNavigationView navigation = findViewById(R.id.nav_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_main);
        ListView listView = findViewById(R.id.lstMain);

        adapter = new ArrayAdapter<String>(this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.main));
        listView.setAdapter(adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ListView lstMain = findViewById(R.id.lstMain);
            BottomNavigationView navigation = findViewById(R.id.nav_menu);
            switch (item.getItemId()) {
                case R.id.nav_main:
                    lstMain.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.main));
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_chat:
                    lstMain.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.chat));
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_notf:
                    lstMain.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.notf));
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_curr:
                    lstMain.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.nav_perf:
                    lstMain.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_nav, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent nav_set = null;
        switch (item.getItemId()) {
            case R.id.nav_settings:
                nav_set = new Intent(this, MyWorkSettings.class);
                break;
            case R.id.nav_autor:
                SnackbarCreate(this.sckbar);
        }
        if(nav_set != null){
            startActivity(nav_set);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SnackbarCreate(int sckbar){
        if(sckbar == 1) {
            Snackbar.make(findViewById(R.id.MyWorkMainLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
        }
        else{}
    }
}
