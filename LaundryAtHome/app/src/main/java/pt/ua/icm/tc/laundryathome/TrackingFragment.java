package pt.ua.icm.tc.laundryathome;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

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
    private static final String ARG_PARAM2 = "param2";


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
    public static TrackingFragment newInstance(int param1, String param2) {
        TrackingFragment fragment = new TrackingFragment();
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

    MapView mMapView;
    private GoogleMap googleMap;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        System.err.println("TrackingFragment.onCreateView");
        System.err.println("mParam1: " + mParam1);
        System.err.println("mParam2: " + mParam2);

        Order order = new Order();

        // Thread
        Thread thread = new Thread(() -> {
            try {
                String uri = "http://52.233.236.63:81/tracking-mobile?orderId=" + mParam1;


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
                order.setDeliveryLocation(responseArray[4]);


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
        TextView orderDeliveryLocation = view.findViewById(R.id.deliveryLocation);

        orderId.setText("Order #" + String.valueOf(order.getId()));
        orderDate.setText("Date: " + order.getDate());
        orderCompleted.setText("Completed: " + String.valueOf(order.isCompleted()));
        orderTotalPrice.setText("Total Price: " + String.valueOf(order.getTotalPrice()));
        orderDeliveryLocation.setText("Delivery Location: " + order.getDeliveryLocation());



        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (orderDeliveryLocation.getText().toString() != null && !orderDeliveryLocation.getText().toString().equals("")) {
                    LatLng latLng = getLocationFromAddress(orderDeliveryLocation.getText().toString());


                    if (latLng != null) {
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Delivery Location"));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else{
                        latLng = new LatLng(40.631230465638644, -8.657472830474786);
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Delivery Location"));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }

            }
        });

        Button btnComplaint = view.findViewById(R.id.btnComplaint);
        if (mParam2.equals("admin"))
            btnComplaint.setVisibility(View.GONE);
        else{
            btnComplaint.setOnClickListener(v -> {
                ComplaintFragment complaintFragment = ComplaintFragment.newInstance(order.getId(), mParam2);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tag2, complaintFragment).commit();
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity().getApplicationContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}