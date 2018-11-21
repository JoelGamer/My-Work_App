package guitheodoro.gmail.com.myworkapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import static android.support.design.widget.Snackbar.*;

public class MyWorkLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_login);
        mAuth = FirebaseAuth.getInstance();

        mainact();
        getSenha();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getSenha(){
        EditText email = findViewById(R.id.txtUsr);
        EditText senha = findViewById(R.id.txtSenha);
        Switch lsenha = findViewById(R.id.swhLSenha);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String Email = settings.getString("Email", "");
        String Senha = settings.getString("Senha", "");
        Boolean lSenha = settings.getBoolean("LSenha", false);

        email.setText(Email);
        senha.setText(Senha);
        lsenha.setChecked(lSenha);
    }

    private void setSenha(int selection){
        EditText email = findViewById(R.id.txtUsr);
        EditText senha = findViewById(R.id.txtSenha);
        Switch lsenha = findViewById(R.id.swhLSenha);

        if(selection == 1){
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Email", email.getText().toString());
            editor.putString("Senha", senha.getText().toString());
            editor.putBoolean("LSenha", lsenha.isChecked());
            editor.apply();
        }
        else {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Email", "");
            editor.putString("Senha", "");
            editor.putBoolean("LSenha", false);
            editor.apply();
        }
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

    private void mainact(){
        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText usr = findViewById(R.id.txtUsr);
        final EditText sen = findViewById(R.id.txtSenha);
        final Switch swh = findViewById(R.id.swhLSenha);
        final boolean connection = isNetworkAvailable();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!connection){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Não tem conexão!", LENGTH_LONG).show();
                }
                else if(usr.getText().toString().equals("") && sen.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira o seu usuário e senha!", LENGTH_LONG).show();
                }
                else if(usr.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira o seu usuário!", LENGTH_LONG).show();
                }
                else if(sen.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Insira a sua senha!", LENGTH_LONG).show();
                }
                else {
                    if(swh.isChecked()){
                        setSenha(1);
                        firebaseLogIn();
                    }
                    else{
                        setSenha(2);
                        firebaseLogIn();
                    }
                }
                hideKeyboard(MyWorkLogin.this);
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
                                    Intent intent = new Intent(MyWorkLogin.this, MyWorkMain.class);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void Click(View v){
        Intent reg = null;
        switch(v.getId()) {
            case R.id.lblRegister:
                reg = new Intent(this, MyWorkRegister.class);
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