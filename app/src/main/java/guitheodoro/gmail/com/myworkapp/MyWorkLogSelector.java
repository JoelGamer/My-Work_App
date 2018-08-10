package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyWorkLogSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_log_selector);

        LoginEmail();
        LoginTelefone();
    }

    private void LoginEmail(){
        Button loginE = findViewById(R.id.btnEmail);
        loginE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkLogSelector.this,MyWorkLogin.class);
                startActivity(intent);
            }
        });
    }

    private void LoginTelefone(){
        Button loginT = findViewById(R.id.btnTel);
        loginT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkLogSelector.this,MyWorkLoginTel.class);
                startActivity(intent);
            }
        });
    }
}
