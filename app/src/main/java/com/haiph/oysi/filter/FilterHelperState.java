package com.haiph.oysi.filter;

import android.widget.Filter;

import com.haiph.oysi.adapter.AdapterFragmentStateOfCountry;
import com.haiph.oysi.model.state.State;

import java.util.ArrayList;

public class FilterHelperState extends Filter {
    static ArrayList<State> filterList;
    static AdapterFragmentStateOfCountry adapter;
    public static FilterHelperState newInstance(ArrayList<State> filterList,
                                                AdapterFragmentStateOfCountry adapter){
        FilterHelperState.filterList=filterList;
        FilterHelperState.adapter=adapter;
        return new FilterHelperState();

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint!=null || constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<State> foundFilter = new ArrayList<>();
            State state;
            for(int i =0;i<filterList.size();i++){
                state=filterList.get(i);
                if (state.state.toUpperCase().contains(constraint)){
                    foundFilter.add(state);
                }
            }
            filterResults.count=foundFilter.size();
            filterResults.values=foundFilter;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setStateCraft((ArrayList<State>) results.values);
        adapter.notifyDataSetChanged();
    }
}
