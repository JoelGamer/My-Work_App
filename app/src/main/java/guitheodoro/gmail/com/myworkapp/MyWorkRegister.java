package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private int sckbar = 1;
    private int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_register);

        Register();

        Spinner spinner = findViewById(R.id.spnSex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void Register(){
        Button register = findViewById(R.id.btnRegistrar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerfRegister();
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

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        parent.getItemAtPosition(pos);
        if(pos == 0){
            sex = 1;
        }
        else if(pos == 1){
            sex = 2;
        }
        else if(pos == 2){
            sex = 3;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void VerfRegister(){
        final EditText nome = findViewById(R.id.txtNome);
        final EditText senha = findViewById(R.id.txtSenha);
        final EditText csenha = findViewById(R.id.txtCSenha);
        final EditText Email = findViewById(R.id.txtEmail);
        final EditText Tel = findViewById(R.id.txtTelefone);

        if (senha.getText().toString().equals("") && csenha.getText().toString().equals("")){
            Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "Registrado com sucesso!", LENGTH_LONG).show();
        }
        else if(senha.getText().toString().equals(csenha.getText().toString())) {
            Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "Registrado com sucesso!", LENGTH_LONG).show();
        }
        else {
            Snackbar.make(findViewById(R.id.MyWorkRegisterLayout), "As senhas não estão iguais!", LENGTH_LONG).show();
        }
    }
}
