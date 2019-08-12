package ca.judacribz.week3day4_contentprovider.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.judacribz.week3day4_contentprovider.R;
import ca.judacribz.week3day4_contentprovider.list.PokeAdapter.*;
import ca.judacribz.week3day4_contentprovider.models.Pokemon;

public class PokeAdapter extends RecyclerView.Adapter<PokeViewHolder> {

    ArrayList<Pokemon> pokeList;

    public PokeAdapter(ArrayList<Pokemon> pokeList) {
        this.pokeList = pokeList;
    }

    @NonNull
    @Override
    public PokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokeViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_pokemon,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull PokeViewHolder holder, int position) {
        holder.setViews(pokeList.get(position));
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    class PokeViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPicture;
        private TextView
                tvId,
                tvName,
                tvAttackNum,
                tvDefenseNum,
                tvHpNum;

        PokeViewHolder(View view) {
            super(view);
            ivPicture = view.findViewById(R.id.ivPicture);
            tvId = view.findViewById(R.id.tvId);
            tvName = view.findViewById(R.id.tvName);
            tvAttackNum = view.findViewById(R.id.tvAttackNum);
            tvDefenseNum = view.findViewById(R.id.tvDefenseNum);
            tvHpNum = view.findViewById(R.id.tvHpNum);
        }

        void setViews(Pokemon pokemon) {
            ivPicture.setImageBitmap(pokemon.getPicture());
            tvId.setText(String.valueOf(pokemon.getId()));
            tvName.setText(pokemon.getName());
            tvAttackNum.setText(String.valueOf(pokemon.getAttack()));
            tvDefenseNum.setText(String.valueOf(pokemon.getDefense()));
            tvHpNum.setText(new StringBuilder("#").append(pokemon.getHp()).toString());
        }
    }
}
