package pt.ua.icm.tc.laundryathome;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new LoginFragment()).commit();

        setContentView(R.layout.activity_main);
    }
}