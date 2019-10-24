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
import com.haiph.oysi.filter.FilterHelperCountry;
import com.haiph.oysi.model.country.Country;

import java.util.ArrayList;

public class AdapterFragmentCountryInTheWorld extends RecyclerView.Adapter<AdapterFragmentCountryInTheWorld.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Country> listCurrent;
    ArrayList<Country> listFilter;
    ItemOnclick itemOnclick;


    public AdapterFragmentCountryInTheWorld(Context context, ArrayList<Country> listCurrent, ItemOnclick itemOnclick) {
        this.context = context;
        this.listCurrent = listCurrent;
        this.listFilter = listCurrent;
        this.itemOnclick = itemOnclick;
    }

    public interface ItemOnclick {
        void itemOnclickListener(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fragment_country_in_world, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMain.setText(listCurrent.get(position).country);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnclick.itemOnclickListener(position);
            }
        });
        if (holder.tvMain.getText().equals("Afghanistan")) {
            holder.iconCountry.setImageResource(R.drawable.afghanistan);
        } else if (holder.tvMain.getText().equals("Algeria")) {
            holder.iconCountry.setImageResource(R.drawable.algeria);
        } else if (holder.tvMain.getText().equals("Andorra")) {
            holder.iconCountry.setImageResource(R.drawable.andorra);
        } else if (holder.tvMain.getText().equals("Algola")) {
            holder.iconCountry.setImageResource(R.drawable.angola);
        } else if (holder.tvMain.getText().equals("Argentina")) {
            holder.iconCountry.setImageResource(R.drawable.argentina);
        } else if (holder.tvMain.getText().equals("Armenia")) {
            holder.iconCountry.setImageResource(R.drawable.armenia);
        } else if (holder.tvMain.getText().equals("Australia")) {
            holder.iconCountry.setImageResource(R.drawable.australia);
        } else if (holder.tvMain.getText().equals("Austria")) {
            holder.iconCountry.setImageResource(R.drawable.austria);
        } else if (holder.tvMain.getText().equals("Bahamas")) {
            holder.iconCountry.setImageResource(R.drawable.bahamas);
        } else if (holder.tvMain.getText().equals("Bahrain")) {
            holder.iconCountry.setImageResource(R.drawable.bahrain);
        } else if (holder.tvMain.getText().equals("Bangladest")) {
            holder.iconCountry.setImageResource(R.drawable.bangladesh);
        } else if (holder.tvMain.getText().equals("Belgium")) {
            holder.iconCountry.setImageResource(R.drawable.belgium);
        } else if (holder.tvMain.getText().equals("Bovilia")) {
            holder.iconCountry.setImageResource(R.drawable.bolivia);
        } else if (holder.tvMain.getText().equals("Bosnia Herzegovina")) {
            holder.iconCountry.setImageResource(R.drawable.bosnia);
        } else if (holder.tvMain.getText().equals("Brazil")) {
            holder.iconCountry.setImageResource(R.drawable.brazil);
        } else if (holder.tvMain.getText().equals("Brunei")) {
            holder.iconCountry.setImageResource(R.drawable.brunei);
        } else if (holder.tvMain.getText().equals("Bulgaria")) {
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvMain.getText().equals("Bulgaria")) {
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvMain.getText().equals("Canada")) {
            holder.iconCountry.setImageResource(R.drawable.canada);
        } else if (holder.tvMain.getText().equals("Chile")) {
            holder.iconCountry.setImageResource(R.drawable.chile);
        } else if (holder.tvMain.getText().equals("China")) {
            holder.iconCountry.setImageResource(R.drawable.china);
        } else if (holder.tvMain.getText().equals("Colombia")) {
            holder.iconCountry.setImageResource(R.drawable.colombia);
        } else if (holder.tvMain.getText().equals("Croatia")) {
            holder.iconCountry.setImageResource(R.drawable.croatia);
        } else if (holder.tvMain.getText().equals("Cyprus")) {
            holder.iconCountry.setImageResource(R.drawable.cyrus);
        } else if (holder.tvMain.getText().equals("Czech Republic")) {
            holder.iconCountry.setImageResource(R.drawable.czech);
        } else if (holder.tvMain.getText().equals("Democratic Republic of the Congo")) {
            holder.iconCountry.setImageResource(R.drawable.democratic);
        } else if (holder.tvMain.getText().equals("Denmark")) {
            holder.iconCountry.setImageResource(R.drawable.denmark);
        } else if (holder.tvMain.getText().equals("Ecuador")) {
            holder.iconCountry.setImageResource(R.drawable.ecuador);
        } else if (holder.tvMain.getText().equals("Ethiopia")) {
            holder.iconCountry.setImageResource(R.drawable.ethiopia);
        } else if (holder.tvMain.getText().equals("Finland")) {
            holder.iconCountry.setImageResource(R.drawable.finland);
        } else if (holder.tvMain.getText().equals("France")) {
            holder.iconCountry.setImageResource(R.drawable.france);
        } else if (holder.tvMain.getText().equals("Germany")) {
            holder.iconCountry.setImageResource(R.drawable.germany);
        } else if (holder.tvMain.getText().equals("Ghana")) {
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvMain.getText().equals("Greece")) {
            holder.iconCountry.setImageResource(R.drawable.greece);
        } else if (holder.tvMain.getText().equals("Guatemala")) {
            holder.iconCountry.setImageResource(R.drawable.guatemala);
        } else if (holder.tvMain.getText().equals("Haiti")) {
            holder.iconCountry.setImageResource(R.drawable.haiti);
        } else if (holder.tvMain.getText().equals("Hong Kong")) {
            holder.iconCountry.setImageResource(R.drawable.hongkong);
        } else if (holder.tvMain.getText().equals("Hungary")) {
            holder.iconCountry.setImageResource(R.drawable.hungary);
        } else if (holder.tvMain.getText().equals("Iceland")) {
            holder.iconCountry.setImageResource(R.drawable.iceland);
        } else if (holder.tvMain.getText().equals("India")) {
            holder.iconCountry.setImageResource(R.drawable.india);
        } else if (holder.tvMain.getText().equals("Indonesia")) {
            holder.iconCountry.setImageResource(R.drawable.indonesia);
        } else if (holder.tvMain.getText().equals("Iran")) {
            holder.iconCountry.setImageResource(R.drawable.iran);
        } else if (holder.tvMain.getText().equals("Iraq")) {
            holder.iconCountry.setImageResource(R.drawable.iraq);
        } else if (holder.tvMain.getText().equals("Ireland")) {
            holder.iconCountry.setImageResource(R.drawable.ireland);
        } else if (holder.tvMain.getText().equals("Israel")) {
            holder.iconCountry.setImageResource(R.drawable.israel);
        } else if (holder.tvMain.getText().equals("Italy")) {
            holder.iconCountry.setImageResource(R.drawable.italy);
        } else if (holder.tvMain.getText().equals("Ivory Coast")) {
            holder.iconCountry.setImageResource(R.drawable.ivory_coast);
        } else if (holder.tvMain.getText().equals("Japan")) {
            holder.iconCountry.setImageResource(R.drawable.japan);
        } else if (holder.tvMain.getText().equals("Jordan")) {
            holder.iconCountry.setImageResource(R.drawable.jordan);
        } else if (holder.tvMain.getText().equals("Kazakhstan")) {
            holder.iconCountry.setImageResource(R.drawable.kazakhstan);
        } else if (holder.tvMain.getText().equals("Kosovo")) {
            holder.iconCountry.setImageResource(R.drawable.kosovo);
        } else if (holder.tvMain.getText().equals("Kuwait")) {
            holder.iconCountry.setImageResource(R.drawable.kuwait);
        } else if (holder.tvMain.getText().equals("Kyrgyzstan")) {
            holder.iconCountry.setImageResource(R.drawable.kyrgyzstan);
        } else if (holder.tvMain.getText().equals("Laos")) {
            holder.iconCountry.setImageResource(R.drawable.laos);
        } else if (holder.tvMain.getText().equals("Latvia")) {
            holder.iconCountry.setImageResource(R.drawable.latvia);
        } else if (holder.tvMain.getText().equals("Lithuania")) {
            holder.iconCountry.setImageResource(R.drawable.lithuania);
        } else if (holder.tvMain.getText().equals("Luxembourg")) {
            holder.iconCountry.setImageResource(R.drawable.luxembourg);
        } else if (holder.tvMain.getText().equals("Macao")) {
            holder.iconCountry.setImageResource(R.drawable.macao);
        } else if (holder.tvMain.getText().equals("Macedonia")) {
            holder.iconCountry.setImageResource(R.drawable.macedonia);
        } else if (holder.tvMain.getText().equals("Malaysia")) {
            holder.iconCountry.setImageResource(R.drawable.malaysia);
        } else if (holder.tvMain.getText().equals("Malta")) {
            holder.iconCountry.setImageResource(R.drawable.malta);
        } else if (holder.tvMain.getText().equals("Mexico")) {
            holder.iconCountry.setImageResource(R.drawable.mexico);
        } else if (holder.tvMain.getText().equals("Mongolia")) {
            holder.iconCountry.setImageResource(R.drawable.mongolia);
        } else if (holder.tvMain.getText().equals("Myanmar")) {
            holder.iconCountry.setImageResource(R.drawable.myanmar);
        } else if (holder.tvMain.getText().equals("Nepal")) {
            holder.iconCountry.setImageResource(R.drawable.nepal);
        } else if (holder.tvMain.getText().equals("Netherlands")) {
            holder.iconCountry.setImageResource(R.drawable.netherlands);
        } else if (holder.tvMain.getText().equals("New Zealand")) {
            holder.iconCountry.setImageResource(R.drawable.newzealand);
        } else if (holder.tvMain.getText().equals("Nigeria")) {
            holder.iconCountry.setImageResource(R.drawable.nigeria);
        } else if (holder.tvMain.getText().equals("Norway")) {
            holder.iconCountry.setImageResource(R.drawable.norway);
        } else if (holder.tvMain.getText().equals("Pakistan")) {
            holder.iconCountry.setImageResource(R.drawable.pakistan);
        } else if (holder.tvMain.getText().equals("Peru")) {
            holder.iconCountry.setImageResource(R.drawable.peru);
        } else if (holder.tvMain.getText().equals("Philippines")) {
            holder.iconCountry.setImageResource(R.drawable.philippines);
        } else if (holder.tvMain.getText().equals("Poland")) {
            holder.iconCountry.setImageResource(R.drawable.poland);
        } else if (holder.tvMain.getText().equals("Portugal")) {
            holder.iconCountry.setImageResource(R.drawable.portugal);
        } else if (holder.tvMain.getText().equals("Puerto Rico")) {
            holder.iconCountry.setImageResource(R.drawable.puertorico);
        } else if (holder.tvMain.getText().equals("Romania")) {
            holder.iconCountry.setImageResource(R.drawable.romania);
        } else if (holder.tvMain.getText().equals("Russia")) {
            holder.iconCountry.setImageResource(R.drawable.russia);
        } else if (holder.tvMain.getText().equals("San Marino")) {
            holder.iconCountry.setImageResource(R.drawable.sanmarino);
        } else if (holder.tvMain.getText().equals("Saudi Arabia")) {
            holder.iconCountry.setImageResource(R.drawable.saudiarabia);
        } else if (holder.tvMain.getText().equals("Serbia")) {
            holder.iconCountry.setImageResource(R.drawable.serbia);
        } else if (holder.tvMain.getText().equals("Singapore")) {
            holder.iconCountry.setImageResource(R.drawable.singapore);
        } else if (holder.tvMain.getText().equals("Slovakia")) {
            holder.iconCountry.setImageResource(R.drawable.slovakia);
        } else if (holder.tvMain.getText().equals("Slovenia")) {
            holder.iconCountry.setImageResource(R.drawable.slovenia);
        } else if (holder.tvMain.getText().equals("South Africa")) {
            holder.iconCountry.setImageResource(R.drawable.southafrica);
        } else if (holder.tvMain.getText().equals("South Korea")) {
            holder.iconCountry.setImageResource(R.drawable.southkorea);
        } else if (holder.tvMain.getText().equals("Spain")) {
            holder.iconCountry.setImageResource(R.drawable.spain);
        } else if (holder.tvMain.getText().equals("Sri Lanka")) {
            holder.iconCountry.setImageResource(R.drawable.srilanka);
        } else if (holder.tvMain.getText().equals("Sweden")) {
            holder.iconCountry.setImageResource(R.drawable.sweden);
        } else if (holder.tvMain.getText().equals("Switzerland")) {
            holder.iconCountry.setImageResource(R.drawable.switzerland);
        } else if (holder.tvMain.getText().equals("Taiwan")) {
            holder.iconCountry.setImageResource(R.drawable.taiwan);
        } else if (holder.tvMain.getText().equals("Thailand")) {
            holder.iconCountry.setImageResource(R.drawable.thailand);
        } else if (holder.tvMain.getText().equals("Uganda")) {
            holder.iconCountry.setImageResource(R.drawable.uganda);
        } else if (holder.tvMain.getText().equals("Ukraine")) {
            holder.iconCountry.setImageResource(R.drawable.ukraine);
        } else if (holder.tvMain.getText().equals("United Arab Emirates")) {
            holder.iconCountry.setImageResource(R.drawable.unitedarabemirates);
        } else if (holder.tvMain.getText().equals("United Kingdom")) {
            holder.iconCountry.setImageResource(R.drawable.unitedkingdom);
        } else if (holder.tvMain.getText().equals("USA")) {
            holder.iconCountry.setImageResource(R.drawable.unitedstates);
        } else if (holder.tvMain.getText().equals("Uzbekistan")) {
            holder.iconCountry.setImageResource(R.drawable.uzbekistan);
        } else if (holder.tvMain.getText().equals("Vietnam")) {
            holder.iconCountry.setImageResource(R.drawable.vietnam);
        } else if (holder.tvMain.getText().equals("Yemen")) {
            holder.iconCountry.setImageResource(R.drawable.yemen);
        }
    }

    @Override
    public int getItemCount() {
        return listCurrent.size();
    }

    @Override
    public Filter getFilter() {

        return FilterHelperCountry.newInstance(listFilter, this);
    }

    public void setCountryCraft(ArrayList<Country> filteredSpacecraft) {
        this.listCurrent = filteredSpacecraft;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvMain;
        ImageView iconCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconCountry = itemView.findViewById(R.id.iconCountry);
            cardView = itemView.findViewById(R.id.cardViewMain);
            tvMain = itemView.findViewById(R.id.tvMain);
        }
    }
}
