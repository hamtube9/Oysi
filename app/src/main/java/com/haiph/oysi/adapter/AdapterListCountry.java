package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.model.country.Country;

import java.util.ArrayList;

public class AdapterListCountry extends RecyclerView.Adapter<AdapterListCountry.ViewHolder> {
    ArrayList<Country> list;
    Context context;
    ItemListener listener;

    public AdapterListCountry(ArrayList<Country> list, Context context, ItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public  interface ItemListener{
        public void ItemOnclickListener(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Country country=list.get(position);
        holder.tvCountry.setText(country.getData().get(position).getCountry());
        holder.cardViewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemOnclickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry;
        CardView cardViewCountry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry=itemView.findViewById(R.id.tvCountry);
            cardViewCountry=itemView.findViewById(R.id.cardViewCountry);
        }
    }
}
