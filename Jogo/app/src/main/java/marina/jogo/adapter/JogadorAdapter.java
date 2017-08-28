package marina.jogo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import marina.jogo.R;
import marina.jogo.control.SelecionarJogador;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;

public class JogadorAdapter extends RecyclerView.Adapter<JogadorAdapter.JogadorViewHolder>{
    private final List<Jogador> lista;
    private final Context context;

    private JogadorOnClickListener onClickListener;
    private JogadorOnLongClickListener onLongClickListener;

    public JogadorAdapter(List<Jogador> lista, Context context, JogadorOnClickListener onClickListener, JogadorOnLongClickListener onLongClickListener) {
        this.lista = lista;
        this.context = context;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public JogadorViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selecionar_jogador, vg, false);

        JogadorViewHolder holder = new JogadorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final JogadorViewHolder holder, final int position) {
        Jogador j = lista.get(position);
        holder.textNome.setText(j.nome);
        holder.textPontuacao.setText(j.pontuacao_total.toString());

        if (onClickListener != null) {
            holder.cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickJogador(holder.cd, position);
                }
            });
        }


        if(onLongClickListener != null){
            holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onLongClickJogador(holder.cd, position);
                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class JogadorViewHolder extends RecyclerView.ViewHolder {
        public TextView textNome;
        public TextView textPontuacao;
        public ImageView imageView;
        public CardView cd;

        public JogadorViewHolder(View view) {
            super(view);
            cd = (CardView) view.findViewById(R.id.cardview1);

            textNome = (TextView) view.findViewById(R.id.escolherJogador_textNome);
            textPontuacao = (TextView) view.findViewById(R.id.escolherJogador_textPontuacao);
        }
    }

    public interface JogadorOnLongClickListener{
        public void onLongClickJogador(View view, int idx);
    }

    public interface JogadorOnClickListener {
        public void onClickJogador(View view, int idx);
    }

}
