package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyWorkStartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_start_up);
        startup();
    }

    private void startup(){
        Intent intent = new Intent(this, MyWorkLogin.class);
        startActivity(intent);
    }
}