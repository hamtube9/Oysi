package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.model.city.City;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCity extends RecyclerView.Adapter<AdapterListCity.ViewHolder> {
    List<City> list;
    Context context;
    ItemListener listener;

    public AdapterListCity(List<City> list,  Context context, ItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }




    public interface ItemListener {
        public void ItemOnclickListener();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city=list.get(position);
        holder.tvCity.setText(city.city);
        holder.itemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemOnclickListener();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemCity;
        TextView tvCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCity = itemView.findViewById(R.id.itemCity);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }
}
