package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class QualityServiceFragment extends Fragment {

    Button btnSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_quality, container, false);
        btnSend=v.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSelectCylinderType= new Intent(getActivity(), SelectCylinderTypeActivity.class);
                startActivity(goSelectCylinderType);
                Toast.makeText(getContext(),R.string.mjsExitoEnvio, Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }



}
