package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkRegisterFirebase extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_register_firebase);

        mAuth = FirebaseAuth.getInstance();

        Register();
    }

    public void Click(View v) {
        Intent login = new Intent(this,MyWorkLogin.class);
        startActivity(login);
    }

    public void Register(){
        Button register = findViewById(R.id.btnRegistrarFB);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerfRegister();
            }
        });
    }

    private void VerfRegister(){
        final EditText email = findViewById(R.id.txtEmailFB);
        final EditText senha = findViewById(R.id.txtSenhaFB);
        final EditText csenha = findViewById(R.id.txtCSenhaFB);

        if (senha.getText().toString().equals("") && csenha.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "Insira a sua senha!", LENGTH_LONG).show();
        }
        else if(email.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "Insira seu email!", LENGTH_LONG).show();
        }
        else if(senha.getText().toString().equals(csenha.getText().toString())) {
            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "Registrado com sucesso!", LENGTH_LONG).show();
            FirebaseRegister();
        }
        else {
            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "As senhas não estão iguais!", LENGTH_LONG).show();
        }
    }

    private void FirebaseRegister(){
        EditText email = findViewById(R.id.txtEmailFB);
        EditText password = findViewById(R.id.txtSenhaFB);

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("WIN", "createUserWithEmail:success");
                            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "Registrado com sucesso!", LENGTH_LONG).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user != null){
                                user.sendEmailVerification();
                            }
                        } else {
                            Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                            Snackbar.make(findViewById(R.id.MyWorkRegisterFirebaseLayout), "Erro no registro do usuário", LENGTH_LONG).show();
                        }
                    }
                });
    }
}
