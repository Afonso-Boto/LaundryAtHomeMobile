package pt.ua.icm.tc.laundryathome;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pt.ua.icm.tc.laundryathome.model.Order;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ORDER";


    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    public TrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TrackingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackingFragment newInstance(int param1) {
        TrackingFragment fragment = new TrackingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        System.err.println("TrackingFragment.onCreateView");
        System.err.println("mParam1: " + mParam1);

        Order order = new Order();

        // Thread
        Thread thread = new Thread(() -> {
            try {
                String uri = "http://10.0.2.2:81/tracking-mobile?orderId=" + mParam1;


                // Create Rest template instance and add the Jackson and String message converters
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                // Make the call to the server
                String response = restTemplate.getForObject(uri, String.class);
                String[] responseArray = response.split(",");
                order.setId(Integer.parseInt(responseArray[0]));
                order.setDate(responseArray[1]);
                order.setCompleted(Boolean.parseBoolean(responseArray[2]));
                order.setTotalPrice(Double.parseDouble(responseArray[3]));


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

        TextView orderId = view.findViewById(R.id.orderId);
        TextView orderDate = view.findViewById(R.id.date);
        TextView orderCompleted = view.findViewById(R.id.isCompleted);
        TextView orderTotalPrice = view.findViewById(R.id.totalPrice);

        orderId.setText("Order #" + String.valueOf(order.getId()));
        orderDate.setText("Date: " + order.getDate());
        orderCompleted.setText("Completed: " + String.valueOf(order.isCompleted()));
        orderTotalPrice.setText("Total Price: " + String.valueOf(order.getTotalPrice()));


        return view;
    }
}