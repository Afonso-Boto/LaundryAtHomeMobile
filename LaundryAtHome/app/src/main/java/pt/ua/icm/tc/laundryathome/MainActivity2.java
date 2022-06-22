package pt.ua.icm.tc.laundryathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String username;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        username = getIntent().getExtras().getString("username");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (username.equals("admin")) {
            OrdersFragments ordersFragments = OrdersFragments.newInstance(username);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, ordersFragments).commit();
            bottomNavigationView.setSelectedItemId(R.id.orders);
        }else{
            ServicesFragment servicesFragment = ServicesFragment.newInstance(username);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, servicesFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.initOrder);
        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.initOrder:
                System.err.println("initOrder");
                ServicesFragment servicesFragment = ServicesFragment.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, servicesFragment).commit();
                return true;

            case R.id.orders:
                System.err.println("orders");
                OrdersFragments ordersFragments = OrdersFragments.newInstance(username);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, ordersFragments).commit();
                return true;
        }
        return false;
    }
}