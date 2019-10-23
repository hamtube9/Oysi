package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.filter.FilterHelperCity;
import com.haiph.oysi.model.city.City;

import java.util.ArrayList;
import java.util.List;

public class AdapterFragmentCity extends RecyclerView.Adapter<AdapterFragmentCity.ViewHolder> implements Filterable {
    ArrayList<City> listCity;
    Context context;
    ArrayList<City> filterList;
    itemOnclick itemOnclick;

    public AdapterFragmentCity(ArrayList<City> listCity, Context context,itemOnclick itemOnclick) {
        this.listCity = listCity;
        this.context = context;
        this.itemOnclick=itemOnclick;
        this.filterList=listCity;
    }
    public interface itemOnclick{
        void itemOnclick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        City city = listCity.get(position);
        holder.tv.setText(city.city);
        holder.cardViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnclick.itemOnclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }

    @Override
    public Filter getFilter() {
        return FilterHelperCity.newInstance(filterList,this);
    }

    public void setCityCraft(ArrayList<City> filteredCityCraft){
        this.listCity=filteredCityCraft;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewCity;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewCity=itemView.findViewById(R.id.cardViewCity);
            tv=itemView.findViewById(R.id.tvFragmentCity);
        }
    }
}
