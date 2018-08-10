package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.sql.*;
import static android.support.design.widget.Snackbar.*;

public class MyWorkLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_login);

        Switch swh = findViewById(R.id.swhLSenha2);
        EditText senha = findViewById(R.id.txtSenha);

        SharedPreferences sharedPrefs = getSharedPreferences("SSenha", MODE_PRIVATE);
        swh.setChecked(sharedPrefs.getBoolean(senha.getText().toString(), true));

        mAuth = FirebaseAuth.getInstance();

        mainact();
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
        Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
    }

    private void Logf(){
        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText senha = findViewById(R.id.txtSenha);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch swh = findViewById(R.id.swhLSenha2);
                if (swh.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("SSenha", MODE_PRIVATE).edit();
                    editor.putBoolean(senha.getText().toString(), true);
                    editor.commit();

                    Intent login = new Intent(MyWorkLogin.this, MyWorkMain.class);
                    startActivity(login);
                }
                else
                {
                    Intent login = new Intent(MyWorkLogin.this, MyWorkMain.class);
                    startActivity(login);
                }
            }
        });

    }

    private void loginConf(String resultu, String results){
        EditText usr = findViewById(R.id.txtUsr);
        EditText senha = findViewById(R.id.txtSenha);

        if(resultu == usr.getText().toString() && results == senha.getText().toString()){
            Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Seja bem vindo" + usr.getText().toString() +"!", LENGTH_LONG).show();
            Intent login = new Intent(MyWorkLogin.this, MyWorkMain.class);
            startActivity(login);
        }
        else {
            Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Você errou o seu usuário e/ou senha!", LENGTH_LONG).show();
        }
    }

    private void mainact(){
        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText usr = findViewById(R.id.txtUsr);
        final EditText sen = findViewById(R.id.txtSenha);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usr.getText().toString().equals("") && sen.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira o seu usuário e senha!", LENGTH_LONG).show();
                }
                else if(usr.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira o seu usuário!", LENGTH_LONG).show();
                }
                else if(sen.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira a sua senha!", LENGTH_LONG).show();
                }
                else {
                    firebaseLogIn();
                }
            }
        });
    }

    private void firebaseLogIn(){
        EditText email = findViewById(R.id.txtUsr);
        EditText password = findViewById(R.id.txtSenha);

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("WIN", "signInWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                boolean emailVerified = user.isEmailVerified();
                                if(emailVerified){
                                    Intent intent = new Intent(MyWorkLogin.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Você precisa confirmar o seu email!", LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Log.w("FAIL", "signInWithEmail:failure", task.getException());
                            Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Você errou o email e/ou a senha!", LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void Click(View v){
        Intent reg = null;
        switch(v.getId()) {
            case R.id.lblRegister:
                reg = new Intent(this, MyWorkRegisterFirebase.class);
                break;
            case R.id.lblLogin:
                reg = new Intent(this, MyWorkLogin.class);
                break;
            case R.id.lblESenha:
                reg = new Intent(this, MyWorkESenha.class);
        }
        startActivity(reg);
    }
}
