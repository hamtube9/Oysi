package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.model.state.State;

import java.util.List;

public class AdapterListState extends RecyclerView.Adapter<AdapterListState.ViewHolder> {
    List<State> list;
    Context context;
    ItemListener listener;

    public  interface ItemListener{
        public void ItemOnclickListener(int position);
    }

    public AdapterListState(List<State> list, Context context, ItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_state, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        State state=list.get(position);
        holder.tvState.setText(state.state);
        holder.itemState.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout itemState;
        TextView tvState;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemState=itemView.findViewById(R.id.itemState);
            tvState=itemView.findViewById(R.id.tvState);
        }
    }
}
