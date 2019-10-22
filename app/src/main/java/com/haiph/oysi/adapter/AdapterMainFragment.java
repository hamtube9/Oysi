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

public class AdapterMainFragment extends RecyclerView.Adapter<AdapterMainFragment.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Country> listCurrent;
    ArrayList<Country> listFilter;


    public AdapterMainFragment(Context context, ArrayList<Country> listCurrent ) {
        this.context = context;
        this.listCurrent = listCurrent;
        this.listFilter = listCurrent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fragment_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMain.setText(listCurrent.get(position).country);
        if (holder.tvMain.getText().equals("Afghanistan")){
            holder.iconCountry.setImageResource(R.drawable.afghanistan);
        } else  if (holder.tvMain.getText().equals("Algeria")){
            holder.iconCountry.setImageResource(R.drawable.algeria);
        } else  if (holder.tvMain.getText().equals("Andorra")){
            holder.iconCountry.setImageResource(R.drawable.andorra);
        } else  if (holder.tvMain.getText().equals("Algola")){
            holder.iconCountry.setImageResource(R.drawable.angola);
        } else  if (holder.tvMain.getText().equals("Argentina")){
            holder.iconCountry.setImageResource(R.drawable.argentina);
        } else  if (holder.tvMain.getText().equals("Armenia")){
            holder.iconCountry.setImageResource(R.drawable.armenia);
        } else  if (holder.tvMain.getText().equals("Australia")){
            holder.iconCountry.setImageResource(R.drawable.australia);
        } else  if (holder.tvMain.getText().equals("Austria")){
            holder.iconCountry.setImageResource(R.drawable.austria);
        } else  if (holder.tvMain.getText().equals("Bahamas")){
            holder.iconCountry.setImageResource(R.drawable.bahamas);
        } else  if (holder.tvMain.getText().equals("Bahrain")){
            holder.iconCountry.setImageResource(R.drawable.bahrain);
        } else  if (holder.tvMain.getText().equals("Bangladest")){
            holder.iconCountry.setImageResource(R.drawable.bangladesh);
        } else  if (holder.tvMain.getText().equals("Belgium")){
            holder.iconCountry.setImageResource(R.drawable.belgium);
        } else  if (holder.tvMain.getText().equals("Bovilia")){
            holder.iconCountry.setImageResource(R.drawable.bolivia);
        } else  if (holder.tvMain.getText().equals("Bosnia Herzegovina")){
            holder.iconCountry.setImageResource(R.drawable.bosnia);
        } else  if (holder.tvMain.getText().equals("Brazil")){
            holder.iconCountry.setImageResource(R.drawable.brazil);
        } else  if (holder.tvMain.getText().equals("Brunei")){
            holder.iconCountry.setImageResource(R.drawable.brunei);
        } else  if (holder.tvMain.getText().equals("Bulgaria")){
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        }else  if (holder.tvMain.getText().equals("Bulgaria")){
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        }else  if (holder.tvMain.getText().equals("Canada")){
            holder.iconCountry.setImageResource(R.drawable.canada);
        }else  if (holder.tvMain.getText().equals("Chile")){
            holder.iconCountry.setImageResource(R.drawable.chile);
        }else  if (holder.tvMain.getText().equals("China")){
            holder.iconCountry.setImageResource(R.drawable.chile);
        }else  if (holder.tvMain.getText().equals("Colombia")){
            holder.iconCountry.setImageResource(R.drawable.colombia);
        }else  if (holder.tvMain.getText().equals("Croatia")){
            holder.iconCountry.setImageResource(R.drawable.croatia);
        }else  if (holder.tvMain.getText().equals("Cyprus")){
            holder.iconCountry.setImageResource(R.drawable.cyrus);
        }else  if (holder.tvMain.getText().equals("Czech Republic")){
            holder.iconCountry.setImageResource(R.drawable.czech);
        }else  if (holder.tvMain.getText().equals("Democratic Republic of the Congo")){
            holder.iconCountry.setImageResource(R.drawable.democratic);
        }else  if (holder.tvMain.getText().equals("Denmark")){
            holder.iconCountry.setImageResource(R.drawable.denmark);
        }else  if (holder.tvMain.getText().equals("Ecuador")){
            holder.iconCountry.setImageResource(R.drawable.ecuador);
        }else  if (holder.tvMain.getText().equals("Ethiopia")){
            holder.iconCountry.setImageResource(R.drawable.ethiopia);
        }else  if (holder.tvMain.getText().equals("Finland")){
            holder.iconCountry.setImageResource(R.drawable.finland);
        }else  if (holder.tvMain.getText().equals("France")){
            holder.iconCountry.setImageResource(R.drawable.france);
        }else  if (holder.tvMain.getText().equals("Germany")){
            holder.iconCountry.setImageResource(R.drawable.germany);
        }else  if (holder.tvMain.getText().equals("Ghana")){
            holder.iconCountry.setImageResource(R.drawable.bulgaria);
        }
    }

    @Override
    public int getItemCount() {
        return listCurrent.size();
    }

    @Override
    public Filter getFilter() {
        return FilterHelperCountry.newInstance(listFilter,this);
    }
    public void setSpacecraft(ArrayList<Country> filteredSpacecraft){
        this.listCurrent=filteredSpacecraft;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvMain;
        ImageView iconCountry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconCountry=itemView.findViewById(R.id.iconCountry);
            cardView=itemView.findViewById(R.id.cardViewMain);
            tvMain=itemView.findViewById(R.id.tvMain);
        }
    }
}
