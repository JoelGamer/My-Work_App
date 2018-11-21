package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkRegister extends AppCompatActivity{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int sckbar = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_register);

        Register();
    }

    public void Register(){
        Button register = findViewById(R.id.btnRegistrar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    VerfRegister();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Click(View v) {
        Intent login = new Intent(this,MyWorkLogin.class);
        startActivity(login);
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
                SnackbarCreate(this.sckbar);
        }
        if(nav_set != null){
            startActivity(nav_set);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SnackbarCreate(int sckbar){
        if(sckbar == 1)
            Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
    }

    private void VerfRegister() throws ParseException {
        final EditText _Senha = findViewById(R.id.txtSenha);
        final EditText _cSenha = findViewById(R.id.txtCSenha);

        String Senha = _Senha.getText().toString();
        String cSenha = _cSenha.getText().toString();

        if (Senha.equals("") && cSenha.equals("")){
            spnConfg();
        }
        else if(Senha.equals(cSenha)) {
            spnConfg();
        }
        else {
            Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "As senhas não estão iguais!", LENGTH_LONG).show();
        }
    }

    private void spnConfg() throws ParseException {
        Spinner _Sexo = findViewById(R.id.spnSex);
        EditText _Date = findViewById(R.id.txtData);
        EditText _Email = findViewById(R.id.txtEmail);
        EditText _Senha = findViewById(R.id.txtSenha);

        String Sexo = _Sexo.getSelectedItem().toString();
        String Date = _Date.getText().toString();
        String Email = _Email.getText().toString();
        String Senha = _Senha.getText().toString();

        DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date date = inputFormat.parse(Date);
        String _date = outputFormat.format(date);

        addUser(_date, Sexo);
        addUserAuth(Email, Senha);
    }

    private void addUser(String Date, String Sexo){
        EditText _Nome = findViewById(R.id.txtNome);
        EditText _Email = findViewById(R.id.txtEmail);
        EditText _Telefone = findViewById(R.id.txtTelefone);

        String Nome = _Nome.getText().toString();
        String Email = _Email.getText().toString();
        String Telefone = _Telefone.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Name", Nome);
        user.put("Email", Email);
        user.put("Telefone", Telefone);
        user.put("Nascimento", Date);
        user.put("Sexo", Sexo);

        db.collection("Usuarios").document(Email).set(user);

        /*
        db.collection("Usuarios").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "Registrado com sucesso!", LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "Falha no Registro!", LENGTH_LONG).show();
            }
        });*/
    }

    private void addUserAuth(String Email, String Senha){
        mAuth.createUserWithEmailAndPassword(Email, Senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        user.sendEmailVerification();
                    }
                }
            }
        });
    }
}
