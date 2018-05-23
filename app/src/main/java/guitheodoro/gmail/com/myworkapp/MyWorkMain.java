package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkMain extends AppCompatActivity {

    public ArrayList <String> dataList;

    private ArrayAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_main);

        BottomNavigationView navigation = findViewById(R.id.nav_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_main);
        ListView listView = findViewById(R.id.lstMain);
        listView.setAdapter(adapter);

        getAllDocs();
    }

    private void getAllDocs() {
        db.collection("Perfil das Empresas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        dataList.add(document.getData().toString());
                    }
                } else {
                    Log.w("FAIL", "Error getting documents.", task.getException());
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ListView lstMain = findViewById(R.id.lstMain);
            View fragPerfil = findViewById(R.id.fragPerfil);
            View fragChat = findViewById(R.id.fragChat);
            View fragNotf = findViewById(R.id.fragNotf);
            View fragMain = findViewById(R.id.fragMain);
            View fragCurr = findViewById(R.id.fragCurr);

            switch (item.getItemId()) {
                case R.id.nav_main:
                    lstMain.setVisibility(View.VISIBLE);
                    fragMain.setVisibility(View.VISIBLE);
                    fragChat.setVisibility(View.INVISIBLE);
                    fragCurr.setVisibility(View.INVISIBLE);
                    fragNotf.setVisibility(View.INVISIBLE);
                    fragPerfil.setVisibility(View.INVISIBLE);

                    adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, dataList);
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_chat:
                    lstMain.setVisibility(View.VISIBLE);
                    fragMain.setVisibility(View.INVISIBLE);
                    fragChat.setVisibility(View.VISIBLE);
                    fragCurr.setVisibility(View.INVISIBLE);
                    fragNotf.setVisibility(View.INVISIBLE);
                    fragPerfil.setVisibility(View.INVISIBLE);

                    adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, dataList);
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_notf:
                    lstMain.setVisibility(View.VISIBLE);
                    fragMain.setVisibility(View.INVISIBLE);
                    fragChat.setVisibility(View.INVISIBLE);
                    fragCurr.setVisibility(View.INVISIBLE);
                    fragNotf.setVisibility(View.VISIBLE);
                    fragPerfil.setVisibility(View.INVISIBLE);

                    adapter = new ArrayAdapter<>(MyWorkMain.this, R.layout.my_work_main_list, R.id.Itemname, dataList);
                    lstMain.setAdapter(adapter);
                    return true;
                case R.id.nav_curr:
                    lstMain.setVisibility(View.INVISIBLE);
                    fragMain.setVisibility(View.INVISIBLE);
                    fragChat.setVisibility(View.INVISIBLE);
                    fragCurr.setVisibility(View.VISIBLE);
                    fragNotf.setVisibility(View.INVISIBLE);
                    fragPerfil.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.nav_perf:
                    lstMain.setVisibility(View.INVISIBLE);
                    fragMain.setVisibility(View.INVISIBLE);
                    fragChat.setVisibility(View.INVISIBLE);
                    fragCurr.setVisibility(View.INVISIBLE);
                    fragNotf.setVisibility(View.INVISIBLE);
                    fragPerfil.setVisibility(View.VISIBLE);

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
