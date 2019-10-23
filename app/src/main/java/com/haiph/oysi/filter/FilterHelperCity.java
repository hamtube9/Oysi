package com.haiph.oysi.filter;

import android.widget.Filter;

import com.haiph.oysi.adapter.AdapterFragmentCity;
import com.haiph.oysi.model.city.City;

import java.util.ArrayList;

public class FilterHelperCity extends Filter {
    static ArrayList<City> filterList;
    static AdapterFragmentCity adapter;

    public static FilterHelperCity newInstance(ArrayList<City> filterList,AdapterFragmentCity adapter) {
        FilterHelperCity.filterList=filterList;
        FilterHelperCity.adapter=adapter;
        return new FilterHelperCity();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint !=null || constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<City> foundFilter = new ArrayList<>();
            City city;
            for(int i = 0;i<filterList.size();i++){
                city=filterList.get(i);
                if (city.city.toUpperCase().contains(constraint)){
                    foundFilter.add(city);
                }
            }
            filterResults.count=foundFilter.size();
            filterResults.values=foundFilter;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setCityCraft((ArrayList<City>) results.values);
        adapter.notifyDataSetChanged();
    }
}
