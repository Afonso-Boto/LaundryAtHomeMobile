package pt.ua.icm.tc.laundryathome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    ArrayAdapter<CharSequence> adapterType;
    ArrayAdapter<CharSequence> adapterColor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeOrderFragment newInstance(String param1, String param2) {
        MakeOrderFragment fragment = new MakeOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_make_order, container, false);

        // Inflate the layout for this fragment
        spinnerType = view.findViewById(R.id.spinner_type);
        spinnerColor = view.findViewById(R.id.spinner_color);

        adapterType = ArrayAdapter.createFromResource(getContext(), R.array.services_array, android.R.layout.simple_spinner_item);
        adapterColor = ArrayAdapter.createFromResource(getContext(), R.array.item_color, android.R.layout.simple_spinner_item);


        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerType.setAdapter(adapterType);
        spinnerColor.setAdapter(adapterColor);

        return view;
    }
}