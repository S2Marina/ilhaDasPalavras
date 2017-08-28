package marina.jogo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import marina.jogo.R;
import marina.jogo.model.Jogador;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder>{

    private final List<Jogador> lista;
    private final Context context;


    public RankingAdapter(List<Jogador> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ranking, vg, false);

        RankingViewHolder holder = new RankingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        Jogador j = lista.get(position);
        holder.textNome.setText(j.nome);
        holder.textPontuacao.setText(j.pontuacao_total.toString());
        Integer p = position + 1;
        holder.text.setText(p.toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        public TextView textNome;
        public TextView textPontuacao;
        public TextView text;
        public CardView cd;

        public RankingViewHolder(View view) {
            super(view);
            cd = (CardView) view.findViewById(R.id.ranking_cardview1);

            textNome = (TextView) view.findViewById(R.id.ranking_textNome);
            textPontuacao = (TextView) view.findViewById(R.id.ranking_textPontuacao);
            text = (TextView) view.findViewById(R.id.ranking_text);
        }
    }


}
