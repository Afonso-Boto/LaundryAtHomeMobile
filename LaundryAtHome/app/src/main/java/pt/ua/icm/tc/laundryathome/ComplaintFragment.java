package pt.ua.icm.tc.laundryathome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(int param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);

        System.err.println("ComplaintFragment.onCreateView");
        System.err.println("mParam1: " + mParam1);
        System.err.println("mParam2: " + mParam2);

        Button btnSubmitComplaint = view.findViewById(R.id.btnSubmitComplaint);
        btnSubmitComplaint.setOnClickListener(v -> {
            // Thread
            Thread thread = new Thread(() -> {
                try {
                    String uri = "http://51.142.78.179:81/mobile/order/complaint";


                    // Create Rest template instance and add the Jackson and String message converters
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                    EditText title = view.findViewById(R.id.title);
                    EditText description = view.findViewById(R.id.description);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("orderId", String.valueOf(mParam1));
                    jsonObject.put("title", title.getText().toString());
                    jsonObject.put("description", description.getText().toString());
                    String request = jsonObject.toString().replace("\"", "\'");
                    System.err.println("request: " + request);

                    String response = restTemplate.postForObject(uri, request, String.class);

                    if(Objects.equals(response, "true")) {
                        OrdersFragments ordersFragments = OrdersFragments.newInstance(mParam2);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, ordersFragments).commit();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        return view;
    }
}