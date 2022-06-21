package pt.ua.icm.tc.laundryathome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView washService;
    ImageView dryCleaningService;
    ImageView washAndLaundryService;
    ImageView specialItemsService;

    View fragmentView;

    public ServicesFragment() {
        // Required empty public constructor
    }


    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_services, container, false);

        washService = fragmentView.findViewById(R.id.wash_service);
        dryCleaningService = fragmentView.findViewById(R.id.dry_cleaning_service);
        washAndLaundryService = fragmentView.findViewById(R.id.wash_and_laundry_service);
        specialItemsService = fragmentView.findViewById(R.id.special_items_service);

        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onClick(View view) {

    }
}