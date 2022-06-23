package pt.ua.icm.tc.laundryathome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pt.ua.icm.tc.laundryathome.model.LoginRequest;

public class ServicesFragment extends Fragment implements View.OnClickListener {

    View fragmentView;
    String user;

    ImageView washService;
    ImageView dryCleaningService;
    ImageView washAndLaundryService;
    ImageView specialItemsService;


    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        user = args.getString("USERNAME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_services, container, false);

        //set ImageViews
        washService = fragmentView.findViewById(R.id.wash_service);
        dryCleaningService = fragmentView.findViewById(R.id.dry_cleaning_service);
        washAndLaundryService = fragmentView.findViewById(R.id.wash_and_laundry_service);
        specialItemsService = fragmentView.findViewById(R.id.special_items_service);

        //set click listeners
        washService.setOnClickListener(this);
        dryCleaningService.setOnClickListener(this);
        washAndLaundryService.setOnClickListener(this);
        specialItemsService.setOnClickListener(this);

        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wash_service:
                System.err.println("------WASH SERVICE CLICKED--------");
                callAPI(1);
                break;
            case R.id.dry_cleaning_service:
                System.err.println("------DRY CLEANING SERVICE CLICKED--------");
                callAPI(2);
                break;
            case R.id.wash_and_laundry_service:
                System.err.println("------WASH AND LAUNDRY SERVICE CLICKED--------");
                callAPI(3);
                break;
            case R.id.special_items_service:
                System.err.println("------SPECIAL ITEMS SERVICE CLICKED--------");
                callAPI(4);
                break;
            default:
                break;
        }
    }

    public void callAPI(long orderType){

        Thread thread = new Thread(() -> {
            try {
                String uri = "http://51.142.78.179:81/mobile/order/init-order/" + String.valueOf(orderType) + "/" + user;

                // Create Rest template instance and add the Jackson and String message converters
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                String response = restTemplate.getForObject(uri,String.class);

                if(Objects.equals(response, "true")) {
                    MakeOrderFragment makeOrderFragment = MakeOrderFragment.newInstance(user);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_tag2, makeOrderFragment).commit();
                    return ;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    public static ServicesFragment newInstance(String username){
        ServicesFragment servicesFragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putString("USERNAME", username);
        servicesFragment.setArguments(args);

        return servicesFragment;
    }

}