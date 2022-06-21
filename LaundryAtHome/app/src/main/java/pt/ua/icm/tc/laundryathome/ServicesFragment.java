package pt.ua.icm.tc.laundryathome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

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
                break;
            case R.id.dry_cleaning_service:
                System.err.println("------DRY CLEANING SERVICE CLICKED--------");
                break;
            case R.id.wash_and_laundry_service:
                System.err.println("------WASH AND LAUNDRY SERVICE CLICKED--------");
                break;
            case R.id.special_items_service:
                System.err.println("------SPECIAL ITEMS SERVICE CLICKED--------");
                break;
            default:
                break;
        }
    }
}