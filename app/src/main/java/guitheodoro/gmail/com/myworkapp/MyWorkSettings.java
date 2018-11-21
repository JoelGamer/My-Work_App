package guitheodoro.gmail.com.myworkapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MyWorkSettings extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile1";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChanger.onActivityCreateSetTheme(MyWorkSettings.this);
        setContentView(R.layout.activity_my_work_settings);

        Theme();
    }

    private void Theme(){
        Switch swh = findViewById(R.id.swhTheme);
        final Boolean swhChecked = swh.isChecked();

        swh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(swhChecked == true){
                    ThemeChanger.changeToTheme(MyWorkSettings.this, ThemeChanger.THEME_DEFAULT);
                }
                else if(swhChecked == false){
                    ThemeChanger.changeToTheme(MyWorkSettings.this, ThemeChanger.THEME_NIGHT);
                }
            }
        });
    }
}
