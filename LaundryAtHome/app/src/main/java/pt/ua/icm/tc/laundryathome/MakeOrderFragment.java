package pt.ua.icm.tc.laundryathome;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pt.ua.icm.tc.laundryathome.model.Item;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeOrderFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "USERNAME";


    View view;
    String user;
    EditText addr;
    EditText numOfItems;
    Button addItem;
    Button makeOrder;

    List<Item> items = new ArrayList<>();


    ArrayAdapter<CharSequence> adapterType;
    ArrayAdapter<CharSequence> adapterColor;


    private Spinner spinnerType;
    private Spinner spinnerColor;

    public MakeOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MakeOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeOrderFragment newInstance(String param1) {
        MakeOrderFragment fragment = new MakeOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_make_order, container, false);

        // Inflate the layout for this fragment
        spinnerType = view.findViewById(R.id.spinner_type);
        spinnerColor = view.findViewById(R.id.spinner_color);
        addr = view.findViewById(R.id.addr_input);
        numOfItems = view.findViewById(R.id.number_input);
        addItem = view.findViewById(R.id.add_btn);
        makeOrder = view.findViewById(R.id.make_order_btn);

        addItem.setOnClickListener(this);
        makeOrder.setOnClickListener(this);

        adapterType = ArrayAdapter.createFromResource(getContext(), R.array.services_array, android.R.layout.simple_spinner_item);
        adapterColor = ArrayAdapter.createFromResource(getContext(), R.array.item_color, android.R.layout.simple_spinner_item);

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerType.setAdapter(adapterType);
        spinnerColor.setAdapter(adapterColor);

        return view;
    }

    public boolean addItem(){
        String type = spinnerType.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();
        if(this.addr.getText().toString().equals("") || this.numOfItems.getText().toString().equals("")){
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        String addr = this.addr.getText().toString();
        String numOfItems = this.numOfItems.getText().toString();


        Item item = new Item(addr, type, color, numOfItems);
        System.err.println(item.toJson());
        return  items.add(item);
    }

    public void makeOrder(){
        Thread thread = new Thread(() -> {
            try {
                String uri = "http://10.0.2.2:81/order/make-order-mobile/" + user;
                List<JSONObject> jsonObjects = new ArrayList<>();
                for (Item item : items) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("itemType", item.getItemType());
                    jsonObject.put("isDark", item.getIsDark());
                    jsonObject.put("number", item.getNumber());
                    jsonObject.put("address", item.getAddress());
                    System.err.println(jsonObject);

                    jsonObjects.add(jsonObject);
                }
                if(items.isEmpty()){
                    throw new IllegalArgumentException("No items to order");
                }

                JSONArray jsonArray = new JSONArray(jsonObjects);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("its", jsonArray);
                String send = jsonObject.toString().replaceAll("\"", "\'");
                System.err.println(send);


                // Create Rest template instance and add the Jackson and String message converters
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                String response = restTemplate.postForObject(uri, send,String.class);

                if(Objects.equals(response, "true")) {

                    ServicesFragment servicesFragment = ServicesFragment.newInstance(user);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_tag, servicesFragment).commit();
                    return ;
                }

            } catch (IllegalArgumentException e){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "No Items added! Pls try add some!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                addItem();
                break;
            case R.id.make_order_btn:
                makeOrder();
                break;
            default:
                break;
        }
    }
}