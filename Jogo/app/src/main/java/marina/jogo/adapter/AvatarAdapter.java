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
import marina.jogo.model.Avatar;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>{
    private final List<Avatar> lista;
    private final Context context;

    private AvatarOnClickListener onClickListener;

    public AvatarAdapter(List<Avatar> lista, Context context, AvatarOnClickListener onClickListener) {
        this.lista = lista;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public AvatarViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_avatar, vg, false);

        AvatarViewHolder holder = new AvatarViewHolder(view);
        Log.d("jogo", String.valueOf(holder.getLayoutPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(final AvatarViewHolder holder, final int position) {
        Avatar a = lista.get(position);

        holder.nome.setText(a.getNome());

        Bitmap b = BitmapFactory.decodeByteArray(a.getImagem(),0,a.getImagem().length);
        holder.img.setImageBitmap(b);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndexOrThrow("imagem")), 0, cursor.getBlob(cursor.getColumnIndexOrThrow("imagem")).length);
        //imvImagem.setImageBitmap(bitmap); //carrega a imagem na ImageView do item da ListView

        if (onClickListener != null) {
            holder.cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickAvatar(holder.cd, position);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView img;

        private CardView cd;

        public AvatarViewHolder(View view) {
            super(view);

            cd = (CardView) view.findViewById(R.id.cardview1);
            nome = (TextView) view.findViewById(R.id.lista_textNomeAvatar);
            img = (ImageView) view.findViewById(R.id.lista_imgAvatar);

        }
    }


    public interface AvatarOnClickListener {
        public void onClickAvatar(View view, int idx);
    }

}
