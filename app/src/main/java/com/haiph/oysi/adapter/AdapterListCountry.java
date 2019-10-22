package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.model.city.City;
import com.haiph.oysi.model.country.Country;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCountry extends RecyclerView.Adapter<AdapterListCountry.ViewHolder> implements Filterable {
    List<Country> list;
    List<Country> listFilter;
    boolean isDark = false;
    Context context;


    public AdapterListCountry(List<Country> list, Context context,boolean isDark) {
        this.list = list;
        this.context = context;
        this.listFilter=new ArrayList<>(list);
        this.isDark=isDark;
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
        Country country=listFilter.get(position);
        holder.tvCountry.setText(country.country);



    }

    @Override
    public Filter getFilter() {
       return exampleFilter;

    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

          List<Country> filteredList = new ArrayList<>();
          if (constraint == null || constraint.length()==0){
              filteredList.addAll(listFilter);
          }else {
              String filterParttern = constraint.toString().toLowerCase().trim();

              for( Country item : listFilter){
                  if (item.country.toLowerCase().contains(filterParttern)){
                      filteredList.add(item);
                  }
              }
          }
          FilterResults results = new FilterResults();
          results.values=filteredList;
          return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry;
        ImageView imgCountry;
        LinearLayout cardViewCountry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry=itemView.findViewById(R.id.tvCountry);
            imgCountry=itemView.findViewById(R.id.imgCountry);
            cardViewCountry=itemView.findViewById(R.id.cardViewCountry);
        }
    }
}
