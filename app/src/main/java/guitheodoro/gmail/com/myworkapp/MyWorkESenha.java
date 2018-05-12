package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkESenha extends AppCompatActivity {

    private int sckbar = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_esenha);
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
        if(sckbar == 1) {
            Snackbar.make(findViewById(R.id.MyWorkESenhaLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
        }
        else{}
    }
}
