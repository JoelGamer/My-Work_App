package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.sql.ResultSet;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkESenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_esenha);

        RecuperarSenha();
    }

    private void RecuperarSenha(){
        Button btnRec = findViewById(R.id.btnEnviarEmail);
        final EditText email = findViewById(R.id.txtEmailES);

        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(R.id.MyWorkESenhaLayout), "O Email foi enviado!", LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

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
        Snackbar.make(findViewById(R.id.MyWorkESenhaLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
    }

    public void Click(View v){
        Intent reg = new Intent(this, MyWorkLogin.class);
        startActivity(reg);
    }
}
