package com.haiph.oysi.filter;

import android.widget.Filter;

import com.haiph.oysi.adapter.AdapterFragmentCountryInTheWorld;
import com.haiph.oysi.model.country.Country;

import java.util.ArrayList;

public class FilterHelperCountry extends Filter {
    static ArrayList<Country> filterList;
    static AdapterFragmentCountryInTheWorld adapter;



    public static FilterHelperCountry newInstance(ArrayList<Country> filterList, AdapterFragmentCountryInTheWorld adapter) {
        FilterHelperCountry.filterList=filterList;
        FilterHelperCountry.adapter=adapter;
        return new FilterHelperCountry();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults =new FilterResults();
        if (constraint !=null || constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Country> foundFilter = new ArrayList<>();
            Country spacecraf;
            for(int i = 0;i<filterList.size();i++){
                spacecraf=filterList.get(i);
                if (spacecraf.country.toUpperCase().contains(constraint)){
                    foundFilter.add(spacecraf);
                }

            }
            filterResults.count=foundFilter.size();
            filterResults.values=foundFilter;

        }
        else{
            filterResults.count=filterList.size();
            filterResults.values=filterList;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setCountryCraft( (ArrayList<Country>) results.values);
        adapter.notifyDataSetChanged();

    }
}
