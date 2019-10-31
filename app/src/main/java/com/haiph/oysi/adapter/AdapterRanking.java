package com.haiph.oysi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haiph.oysi.R;
import com.haiph.oysi.model.ranking.World;

import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.ViewHolder> {
    Context context;
    List<World> list;

    public AdapterRanking(Context context, List<World> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        World rank = list.get(position);
        holder.tvCitiRanking.setText(rank.getData().get(position).getCity());
        holder.tvCountryRanking.setText(rank.getData().get(position).getCountry());
        holder.tvAQIranking.setText(rank.getData().get(position).getRanking().getCurrentAqi());


        if (holder.tvCountryRanking.getText().equals("Afghanistan")) {
            holder.imgRanking.setImageResource(R.drawable.afghanistan);
        } else if (holder.tvCountryRanking.getText().equals("Algeria")) {
            holder.imgRanking.setImageResource(R.drawable.algeria);
        } else if (holder.tvCountryRanking.getText().equals("Andorra")) {
            holder.imgRanking.setImageResource(R.drawable.andorra);
        } else if (holder.tvCountryRanking.getText().equals("Algola")) {
            holder.imgRanking.setImageResource(R.drawable.angola);
        } else if (holder.tvCountryRanking.getText().equals("Argentina")) {
            holder.imgRanking.setImageResource(R.drawable.argentina);
        } else if (holder.tvCountryRanking.getText().equals("Armenia")) {
            holder.imgRanking.setImageResource(R.drawable.armenia);
        } else if (holder.tvCountryRanking.getText().equals("Australia")) {
            holder.imgRanking.setImageResource(R.drawable.australia);
        } else if (holder.tvCountryRanking.getText().equals("Austria")) {
            holder.imgRanking.setImageResource(R.drawable.austria);
        } else if (holder.tvCountryRanking.getText().equals("Bahamas")) {
            holder.imgRanking.setImageResource(R.drawable.bahamas);
        } else if (holder.tvCountryRanking.getText().equals("Bahrain")) {
            holder.imgRanking.setImageResource(R.drawable.bahrain);
        } else if (holder.tvCountryRanking.getText().equals("Bangladest")) {
            holder.imgRanking.setImageResource(R.drawable.bangladesh);
        } else if (holder.tvCountryRanking.getText().equals("Belgium")) {
            holder.imgRanking.setImageResource(R.drawable.belgium);
        } else if (holder.tvCountryRanking.getText().equals("Bovilia")) {
            holder.imgRanking.setImageResource(R.drawable.bolivia);
        } else if (holder.tvCountryRanking.getText().equals("Bosnia Herzegovina")) {
            holder.imgRanking.setImageResource(R.drawable.bosnia);
        } else if (holder.tvCountryRanking.getText().equals("Brazil")) {
            holder.imgRanking.setImageResource(R.drawable.brazil);
        } else if (holder.tvCountryRanking.getText().equals("Brunei")) {
            holder.imgRanking.setImageResource(R.drawable.brunei);
        } else if (holder.tvCountryRanking.getText().equals("Bulgaria")) {
            holder.imgRanking.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvCountryRanking.getText().equals("Bulgaria")) {
            holder.imgRanking.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvCountryRanking.getText().equals("Canada")) {
            holder.imgRanking.setImageResource(R.drawable.canada);
        } else if (holder.tvCountryRanking.getText().equals("Chile")) {
            holder.imgRanking.setImageResource(R.drawable.chile);
        } else if (holder.tvCountryRanking.getText().equals("China")) {
            holder.imgRanking.setImageResource(R.drawable.china);
        } else if (holder.tvCountryRanking.getText().equals("Colombia")) {
            holder.imgRanking.setImageResource(R.drawable.colombia);
        } else if (holder.tvCountryRanking.getText().equals("Croatia")) {
            holder.imgRanking.setImageResource(R.drawable.croatia);
        } else if (holder.tvCountryRanking.getText().equals("Cyprus")) {
            holder.imgRanking.setImageResource(R.drawable.cyrus);
        } else if (holder.tvCountryRanking.getText().equals("Czech Republic")) {
            holder.imgRanking.setImageResource(R.drawable.czech);
        } else if (holder.tvCountryRanking.getText().equals("Democratic Republic of the Congo")) {
            holder.imgRanking.setImageResource(R.drawable.democratic);
        } else if (holder.tvCountryRanking.getText().equals("Denmark")) {
            holder.imgRanking.setImageResource(R.drawable.denmark);
        } else if (holder.tvCountryRanking.getText().equals("Ecuador")) {
            holder.imgRanking.setImageResource(R.drawable.ecuador);
        } else if (holder.tvCountryRanking.getText().equals("Ethiopia")) {
            holder.imgRanking.setImageResource(R.drawable.ethiopia);
        } else if (holder.tvCountryRanking.getText().equals("Finland")) {
            holder.imgRanking.setImageResource(R.drawable.finland);
        } else if (holder.tvCountryRanking.getText().equals("France")) {
            holder.imgRanking.setImageResource(R.drawable.france);
        } else if (holder.tvCountryRanking.getText().equals("Germany")) {
            holder.imgRanking.setImageResource(R.drawable.germany);
        } else if (holder.tvCountryRanking.getText().equals("Ghana")) {
            holder.imgRanking.setImageResource(R.drawable.bulgaria);
        } else if (holder.tvCountryRanking.getText().equals("Greece")) {
            holder.imgRanking.setImageResource(R.drawable.greece);
        } else if (holder.tvCountryRanking.getText().equals("Guatemala")) {
            holder.imgRanking.setImageResource(R.drawable.guatemala);
        } else if (holder.tvCountryRanking.getText().equals("Haiti")) {
            holder.imgRanking.setImageResource(R.drawable.haiti);
        } else if (holder.tvCountryRanking.getText().equals("Hong Kong")) {
            holder.imgRanking.setImageResource(R.drawable.hongkong);
        } else if (holder.tvCountryRanking.getText().equals("Hungary")) {
            holder.imgRanking.setImageResource(R.drawable.hungary);
        } else if (holder.tvCountryRanking.getText().equals("Iceland")) {
            holder.imgRanking.setImageResource(R.drawable.iceland);
        } else if (holder.tvCountryRanking.getText().equals("India")) {
            holder.imgRanking.setImageResource(R.drawable.india);
        } else if (holder.tvCountryRanking.getText().equals("Indonesia")) {
            holder.imgRanking.setImageResource(R.drawable.indonesia);
        } else if (holder.tvCountryRanking.getText().equals("Iran")) {
            holder.imgRanking.setImageResource(R.drawable.iran);
        } else if (holder.tvCountryRanking.getText().equals("Iraq")) {
            holder.imgRanking.setImageResource(R.drawable.iraq);
        } else if (holder.tvCountryRanking.getText().equals("Ireland")) {
            holder.imgRanking.setImageResource(R.drawable.ireland);
        } else if (holder.tvCountryRanking.getText().equals("Israel")) {
            holder.imgRanking.setImageResource(R.drawable.israel);
        } else if (holder.tvCountryRanking.getText().equals("Italy")) {
            holder.imgRanking.setImageResource(R.drawable.italy);
        } else if (holder.tvCountryRanking.getText().equals("Ivory Coast")) {
            holder.imgRanking.setImageResource(R.drawable.ivory_coast);
        } else if (holder.tvCountryRanking.getText().equals("Japan")) {
            holder.imgRanking.setImageResource(R.drawable.japan);
        } else if (holder.tvCountryRanking.getText().equals("Jordan")) {
            holder.imgRanking.setImageResource(R.drawable.jordan);
        } else if (holder.tvCountryRanking.getText().equals("Kazakhstan")) {
            holder.imgRanking.setImageResource(R.drawable.kazakhstan);
        } else if (holder.tvCountryRanking.getText().equals("Kosovo")) {
            holder.imgRanking.setImageResource(R.drawable.kosovo);
        } else if (holder.tvCountryRanking.getText().equals("Kuwait")) {
            holder.imgRanking.setImageResource(R.drawable.kuwait);
        } else if (holder.tvCountryRanking.getText().equals("Kyrgyzstan")) {
            holder.imgRanking.setImageResource(R.drawable.kyrgyzstan);
        } else if (holder.tvCountryRanking.getText().equals("Laos")) {
            holder.imgRanking.setImageResource(R.drawable.laos);
        } else if (holder.tvCountryRanking.getText().equals("Latvia")) {
            holder.imgRanking.setImageResource(R.drawable.latvia);
        } else if (holder.tvCountryRanking.getText().equals("Lithuania")) {
            holder.imgRanking.setImageResource(R.drawable.lithuania);
        } else if (holder.tvCountryRanking.getText().equals("Luxembourg")) {
            holder.imgRanking.setImageResource(R.drawable.luxembourg);
        } else if (holder.tvCountryRanking.getText().equals("Macao")) {
            holder.imgRanking.setImageResource(R.drawable.macao);
        } else if (holder.tvCountryRanking.getText().equals("Macedonia")) {
            holder.imgRanking.setImageResource(R.drawable.macedonia);
        } else if (holder.tvCountryRanking.getText().equals("Malaysia")) {
            holder.imgRanking.setImageResource(R.drawable.malaysia);
        } else if (holder.tvCountryRanking.getText().equals("Malta")) {
            holder.imgRanking.setImageResource(R.drawable.malta);
        } else if (holder.tvCountryRanking.getText().equals("Mexico")) {
            holder.imgRanking.setImageResource(R.drawable.mexico);
        } else if (holder.tvCountryRanking.getText().equals("Mongolia")) {
            holder.imgRanking.setImageResource(R.drawable.mongolia);
        } else if (holder.tvCountryRanking.getText().equals("Myanmar")) {
            holder.imgRanking.setImageResource(R.drawable.myanmar);
        } else if (holder.tvCountryRanking.getText().equals("Nepal")) {
            holder.imgRanking.setImageResource(R.drawable.nepal);
        } else if (holder.tvCountryRanking.getText().equals("Netherlands")) {
            holder.imgRanking.setImageResource(R.drawable.netherlands);
        } else if (holder.tvCountryRanking.getText().equals("New Zealand")) {
            holder.imgRanking.setImageResource(R.drawable.newzealand);
        } else if (holder.tvCountryRanking.getText().equals("Nigeria")) {
            holder.imgRanking.setImageResource(R.drawable.nigeria);
        } else if (holder.tvCountryRanking.getText().equals("Norway")) {
            holder.imgRanking.setImageResource(R.drawable.norway);
        } else if (holder.tvCountryRanking.getText().equals("Pakistan")) {
            holder.imgRanking.setImageResource(R.drawable.pakistan);
        } else if (holder.tvCountryRanking.getText().equals("Peru")) {
            holder.imgRanking.setImageResource(R.drawable.peru);
        } else if (holder.tvCountryRanking.getText().equals("Philippines")) {
            holder.imgRanking.setImageResource(R.drawable.philippines);
        } else if (holder.tvCountryRanking.getText().equals("Poland")) {
            holder.imgRanking.setImageResource(R.drawable.poland);
        } else if (holder.tvCountryRanking.getText().equals("Portugal")) {
            holder.imgRanking.setImageResource(R.drawable.portugal);
        } else if (holder.tvCountryRanking.getText().equals("Puerto Rico")) {
            holder.imgRanking.setImageResource(R.drawable.puertorico);
        } else if (holder.tvCountryRanking.getText().equals("Romania")) {
            holder.imgRanking.setImageResource(R.drawable.romania);
        } else if (holder.tvCountryRanking.getText().equals("Russia")) {
            holder.imgRanking.setImageResource(R.drawable.russia);
        } else if (holder.tvCountryRanking.getText().equals("San Marino")) {
            holder.imgRanking.setImageResource(R.drawable.sanmarino);
        } else if (holder.tvCountryRanking.getText().equals("Saudi Arabia")) {
            holder.imgRanking.setImageResource(R.drawable.saudiarabia);
        } else if (holder.tvCountryRanking.getText().equals("Serbia")) {
            holder.imgRanking.setImageResource(R.drawable.serbia);
        } else if (holder.tvCountryRanking.getText().equals("Singapore")) {
            holder.imgRanking.setImageResource(R.drawable.singapore);
        } else if (holder.tvCountryRanking.getText().equals("Slovakia")) {
            holder.imgRanking.setImageResource(R.drawable.slovakia);
        } else if (holder.tvCountryRanking.getText().equals("Slovenia")) {
            holder.imgRanking.setImageResource(R.drawable.slovenia);
        } else if (holder.tvCountryRanking.getText().equals("South Africa")) {
            holder.imgRanking.setImageResource(R.drawable.southafrica);
        } else if (holder.tvCountryRanking.getText().equals("South Korea")) {
            holder.imgRanking.setImageResource(R.drawable.southkorea);
        } else if (holder.tvCountryRanking.getText().equals("Spain")) {
            holder.imgRanking.setImageResource(R.drawable.spain);
        } else if (holder.tvCountryRanking.getText().equals("Sri Lanka")) {
            holder.imgRanking.setImageResource(R.drawable.srilanka);
        } else if (holder.tvCountryRanking.getText().equals("Sweden")) {
            holder.imgRanking.setImageResource(R.drawable.sweden);
        } else if (holder.tvCountryRanking.getText().equals("Switzerland")) {
            holder.imgRanking.setImageResource(R.drawable.switzerland);
        } else if (holder.tvCountryRanking.getText().equals("Taiwan")) {
            holder.imgRanking.setImageResource(R.drawable.taiwan);
        } else if (holder.tvCountryRanking.getText().equals("Thailand")) {
            holder.imgRanking.setImageResource(R.drawable.thailand);
        } else if (holder.tvCountryRanking.getText().equals("Uganda")) {
            holder.imgRanking.setImageResource(R.drawable.uganda);
        } else if (holder.tvCountryRanking.getText().equals("Ukraine")) {
            holder.imgRanking.setImageResource(R.drawable.ukraine);
        } else if (holder.tvCountryRanking.getText().equals("United Arab Emirates")) {
            holder.imgRanking.setImageResource(R.drawable.unitedarabemirates);
        } else if (holder.tvCountryRanking.getText().equals("United Kingdom")) {
            holder.imgRanking.setImageResource(R.drawable.unitedkingdom);
        } else if (holder.tvCountryRanking.getText().equals("USA")) {
            holder.imgRanking.setImageResource(R.drawable.unitedstates);
        } else if (holder.tvCountryRanking.getText().equals("Uzbekistan")) {
            holder.imgRanking.setImageResource(R.drawable.uzbekistan);
        } else if (holder.tvCountryRanking.getText().equals("Vietnam")) {
            holder.imgRanking.setImageResource(R.drawable.vietnam);
        } else if (holder.tvCountryRanking.getText().equals("Yemen")) {
            holder.imgRanking.setImageResource(R.drawable.yemen);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCitiRanking, tvCountryRanking,tvAQIranking;
        ImageView imgRanking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAQIranking=itemView.findViewById(R.id.tvAQIranking);
            tvCitiRanking=itemView.findViewById(R.id.tvrankingCity);
            tvCountryRanking=itemView.findViewById(R.id.tvCountryRanking);
            imgRanking=itemView.findViewById(R.id.imgRanking);

        }
    }
}
