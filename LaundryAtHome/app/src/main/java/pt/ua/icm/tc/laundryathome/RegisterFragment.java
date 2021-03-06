package pt.ua.icm.tc.laundryathome;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import pt.ua.icm.tc.laundryathome.model.RegisterRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    Button btnRegister;
    TextInputEditText inputUsername2;
    EditText inputPassword2, inputEmail, inputFullName, inputPhone;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputUsername2 = view.findViewById(R.id.inputUsername2);
        inputPassword2 = view.findViewById(R.id.inputPassword2);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputFullName = view.findViewById(R.id.inputFullName);
        inputPhone = view.findViewById(R.id.inputPhone);
        btnRegister = view.findViewById(R.id.btnRegister);

        // Register Button
        btnRegister.setOnClickListener(v -> {
            System.err.println("heyyyyyyyyyyy");

            Thread thread = new Thread(() -> {
                try {
                    String username = inputUsername2.getText().toString();
                    String password = inputPassword2.getText().toString();
                    String email = inputEmail.getText().toString();
                    String fullName = inputFullName.getText().toString();
                    String phone = inputPhone.getText().toString();

                    String uri = "http://51.142.78.179:81/mobile/auth/register";

                    // Create Rest template instance and add the Jackson and String message converters
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                    System.err.println("inputUsername: " + inputUsername2.getText().toString());
                    System.err.println("inputPassword: " + inputPassword2.getText().toString());
                    System.err.println("inputEmail: " + inputEmail.getText().toString());
                    System.err.println("inputFullName: " + inputFullName.getText().toString());
                    System.err.println("inputPhone: " + inputPhone.getText().toString());

                    String response = restTemplate.postForObject(uri, new RegisterRequest(username, email, password, fullName, Integer.parseInt(phone)), String.class);

                    if (Objects.equals(response, "success")) {
                        Intent intent = new Intent(getActivity(), MainActivity2.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }

                    System.err.println("Registration successful");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        });

        // Buttton switch Register -> Login
        Button btnSwitchLogin = view.findViewById(R.id.btnSwitchLogin);
        btnSwitchLogin.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_tag, new LoginFragment()).commit();
        });

    }
}