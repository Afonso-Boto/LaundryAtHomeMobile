package pt.ua.icm.tc.laundryathome;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pt.ua.icm.tc.laundryathome.model.Order;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public OrdersFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OrdersFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragments newInstance(String param1) {
        OrdersFragments fragment = new OrdersFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    RecyclerView recyclerView;
    List<Order> orders = new ArrayList<>();
    Thread thread;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_fragments, container, false);

        System.err.println("OrdersFragments.onCreateView");
        System.err.println("OrdersFragments.onCreateView.mParam1: " + mParam1);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // Thread
        thread = new Thread(() -> {
            try {
                String uri = "http://10.0.2.2:81/orders-mobile?username=" + mParam1;


                // Create Rest template instance and add the Jackson and String message converters
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                // Make the call to the server
                String response = restTemplate.getForObject(uri, String.class);
                String[] responseArray = response.split(",");

                for (int i = 0; i < responseArray.length; i += 4) {
                    orders.add(new Order(Integer.parseInt(responseArray[i]), responseArray[i + 1], Boolean.parseBoolean(responseArray[i + 2]), Double.parseDouble(responseArray[i + 3])));
                }

                System.err.println("OrdersFragments.getData: " + orders.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.err.println("OrdersFragments.onCreateView: " + orders);
        ItemAdapter itemAdapter = new ItemAdapter(orders);
        System.err.println("OrdersFragments.onCreateView: " + itemAdapter.getItemCount());
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener(new ItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                TrackingFragment trackingFragment = TrackingFragment.newInstance(orders.get(position).getId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, trackingFragment).commit();
            }
        });


        return view;
    }
}