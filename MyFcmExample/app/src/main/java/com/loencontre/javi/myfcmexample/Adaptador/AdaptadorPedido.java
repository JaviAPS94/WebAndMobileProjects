package com.loencontre.javi.myfcmexample.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.loencontre.javi.myfcmexample.R;
import com.loencontre.javi.myfcmexample.entity.Order;

/**
 * Created by javi on 05/01/2018.
 */

public class AdaptadorPedido extends ArrayAdapter<Order> {
    //Atributo
    Context context;
    int LayoutResortId;
    private Order[] orders;



    public AdaptadorPedido(@NonNull Context context, int resource, @NonNull Order[] orders) {
        super(context, resource, orders);
        this.context= context;
        this.LayoutResortId= LayoutResortId;
        this.orders = orders;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BandasHolder holder = null;
        if (convertView == null) {
            LayoutInflater li = ((Activity)context).getLayoutInflater();
            convertView = li.inflate(LayoutResortId, parent, false);
            holder= new BandasHolder();
            holder.txtNameClient= convertView.findViewById(R.id.txtNameClient);
            holder.txtNumberGas=  convertView.findViewById(R.id.txtNumberGas);
            holder.txtAddres = convertView.findViewById((R.id.txtAddres));
            // holder.btnSi = convertView.findViewById((R.id.btnSi));
            // holder.btnNo = convertView.findViewById((R.id.btnNo));
            convertView.setTag(holder);
        }
        else{
            holder= (BandasHolder) convertView.getTag();
        }

        Order order = orders[position];
        holder.txtNameClient= convertView.findViewById(R.id.txtNameClient);
        holder.txtNumberGas=  convertView.findViewById(R.id.txtNumberGas);
        holder.txtAddres = convertView.findViewById((R.id.txtAddres));
        //holder.btnSi = convertView.findViewById((R.id.btnSi));
        //holder.btnNo = convertView.findViewById((R.id.btnNo));

        holder.txtNameClient.setText(orders[position].getIdgasorder());
        holder.txtNumberGas.setText(orders[position].getQuantitygasorder());
        holder.txtAddres.setText((int) orders[position].getXgascoordinate());
        return super.getView(position, convertView, parent);

    }

    static class BandasHolder{
        TextView txtNameClient;
        TextView txtNumberGas;
        TextView txtAddres;
        //Button btnSi;
        // Button btnNo;
    }
}
