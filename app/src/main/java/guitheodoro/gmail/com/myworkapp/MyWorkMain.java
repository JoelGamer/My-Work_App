package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkMain extends AppCompatActivity {

    private ArrayAdapter adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_main);

        BottomNavigationView navigation = findViewById(R.id.nav_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_main);
        ListView listView = findViewById(R.id.lstMain);
        
        listView.setAdapter(adapter);
    }

    private void UpdateList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("/Empresas");
        final TextView item = findViewById(R.id.Itemname);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    dataList.add(data.toString());
                }
                adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, dataList);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ListView lstMain = findViewById(R.id.lstMain);
            switch (item.getItemId()) {
                case R.id.nav_main:
                    UpdateList();
                    return true;
                case R.id.nav_chat:
                    lstMain.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.chat));
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_notf:
                    lstMain.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, getResources().getStringArray(R.array.notf));
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
                SnackbarCreate();
        }
        if(nav_set != null){
            startActivity(nav_set);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SnackbarCreate(){
        Snackbar.make(findViewById(R.id.MyWorkMainLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
    }
}
