package com.example.a38853841x.todofirebase;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<String> lista;
    private ArrayAdapter<String> adapter;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button send = (Button) view.findViewById(R.id.bt_send);

        final EditText item = (EditText) view.findViewById(R.id.et_item);
        ListView lv_ToDo = (ListView) view.findViewById(R.id.lv_ToDo);

        setupAuth();

        lista = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.item_celda,
                R.id.tv_Item,
                lista
        );

        lv_ToDo.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                adapter.add(item.getText().toString());
                item.setText("");

            }
        });


        return view;

    }

    private void setupAuth(){
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){

            Log.d("Current user: ", String.valueOf(auth.getCurrentUser()));
        }
        else
        {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())
                            ).build(),
                    RC_SIGN_IN);
        }

    }


}
