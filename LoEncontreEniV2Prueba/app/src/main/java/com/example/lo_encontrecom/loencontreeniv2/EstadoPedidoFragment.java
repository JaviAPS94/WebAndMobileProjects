package com.example.lo_encontrecom.loencontreeniv2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class EstadoPedidoFragment extends Fragment {

    Button btnConfirmOrder,btnCancelOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_estado_pedido, container, false);

        btnCancelOrder=v.findViewById(R.id.btnCancelOrder);
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.mjsTituloCancelOrder);
                builder.setMessage(R.string.mjsBodyCancelOrder);
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent goSelectCylinderType= new Intent(getActivity(), SelectCylinderTypeActivity.class);
                        startActivity(goSelectCylinderType);
                        Toast.makeText(getContext(),R.string.mjsExitoCancelOrder, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(R.string.btnNo, null);
                Dialog dialog = builder.create();
                dialog.show();

            }
        });

        return v;
    }



}
