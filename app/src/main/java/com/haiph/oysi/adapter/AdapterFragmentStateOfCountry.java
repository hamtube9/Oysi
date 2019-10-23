package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.filter.FilterHelperState;
import com.haiph.oysi.model.state.State;

import java.util.ArrayList;
import java.util.List;

public class AdapterFragmentStateOfCountry extends RecyclerView.Adapter<AdapterFragmentStateOfCountry.ViewHolder> implements Filterable {
    ArrayList<State> listState;
    ArrayList<State> filterlist;
    Context context;
    ItemOnclickState itemOnclickState;

    public AdapterFragmentStateOfCountry(ArrayList<State> listState, Context context,ItemOnclickState itemOnclickState)  {
        this.listState = listState;
        this.context = context;
        this.filterlist=listState;
        this.itemOnclickState=itemOnclickState;
    }
    public interface ItemOnclickState{
        void ItemOnclickSelected(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_state_of_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        State state=listState.get(position);
        holder.tv.setText(state.state);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnclickState.ItemOnclickSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listState.size();
    }

    @Override
    public Filter getFilter() {

        return FilterHelperState.newInstance(filterlist,this);
    }
    public void setStateCraft(ArrayList<State> filteredStateCraft){
        this.listState=filteredStateCraft;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardViewStateOfCountry);
            tv=itemView.findViewById(R.id.tvStateOfCountry);
            img=itemView.findViewById(R.id.iconStateOfCountry);
        }
    }
}
