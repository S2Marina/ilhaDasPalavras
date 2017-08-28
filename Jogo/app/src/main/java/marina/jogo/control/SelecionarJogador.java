package marina.jogo.control;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import marina.jogo.R;
import marina.jogo.adapter.AvatarAdapter;
import marina.jogo.adapter.JogadorAdapter;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.dao.Contract;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.NivelDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;
import marina.jogo.model.Nivel;

public class SelecionarJogador extends Fragment {

    private List<Jogador> lista = new ArrayList<Jogador>();
    private RecyclerView rv;
    private JogadorDAO jd;
    private CardView cd;
    public AvatarDAO aDAO;
    private Jogador j = new Jogador();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selecionar_jogador, container, false);
        setHasOptionsMenu(true);

        Avatar a = new Avatar();

        Drawable d = getResources().getDrawable(R.drawable.download);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        a.setId_avatar(1);
        a.setNome("Pirata");
        a.setImagem(bitmapdata);
        a.setBloqueado(false);

        Avatar a2 = new Avatar();

        Drawable d2 = getResources().getDrawable(R.drawable.estrelabranco);
        Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
        byte[] bitmapdata2 = stream2.toByteArray();

        a2.setId_avatar(2);
        a2.setNome("Estrela Pirata");
        a2.setImagem(bitmapdata2);
        a2.setBloqueado(true);

        aDAO = AvatarDAO.getInstance(getContext());
        aDAO.open();
        aDAO.apaga();
        aDAO.inserir(a);
        aDAO.inserir(a2);
        aDAO.close();

        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        MainActivity.t.setTitle("Jogadores");

        MainActivity.t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_novo: {
                        replaceFragmentCadastrarJogador();
                        break;
                    }
                    case R.id.menuitem_excluir: {
                        if (j == null) {
                            Toast.makeText(getContext(), "Selecione um jogador!", Toast.LENGTH_LONG).show();
                        } else {
                            jd.getInstance(getContext());
                            jd.open();
                            jd.excluir(j);
                            lista = jd.toList(jd.getCursor(), getContext());
                            rv.setAdapter(new JogadorAdapter(lista, getContext(), onClick(), onLongClick()));
                            jd.close();
                        }
                    }
                }
                return true;
            }
        });


        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        cd = (CardView) view.findViewById(R.id.cardview1);

        jd  = JogadorDAO.getInstance(getContext());
        jd.open();
        lista = jd.toList(jd.getCursor(), getContext());
        rv.setAdapter(new JogadorAdapter(lista, getContext(), onClick(), onLongClick()));
        jd.close();

        for (int x = 0; x < lista.size(); x++) {
            Log.e("jogo", "jogador: " + lista.get(x).getNome());
            Log.e("jogo", "avatar: " + lista.get(x).getAvatar());
            Log.e("jogo", "jogo: " + lista.get(x).getJogo());
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_selecionar_jogador, menu);
    }

    protected JogadorAdapter.JogadorOnLongClickListener onLongClick(){
        return new JogadorAdapter.JogadorOnLongClickListener() {
            @Override
            public void onLongClickJogador(View view, int idx) {
                /*CardView cd;
                cd = (CardView) view.findViewById(R.id.cardview1);
                cd.setCardBackgroundColor(getResources().getColor(R.color.azulClarinho));
                view.setSelected(true);
                if(view.isSelected()){
                    view.setBackgroundColor(getResources().getColor(R.color.azulClarinho));
                }*/
                j = lista.get(idx);
                Log.e("jogo", "Selecionou!!!");
                //Log.e("jogo", cd.toString());

            }
        };
    }

    protected JogadorAdapter.JogadorOnClickListener onClick() {
        return new JogadorAdapter.JogadorOnClickListener() {
            @Override
            public void onClickJogador(View view, int idx) {
                j = lista.get(idx);
                Log.e("jogo", "avatar: " + j.getAvatar());
                replaceFragmentNiveis();

            }
        };
    }

    private void replaceFragmentCadastrarJogador(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        CadastrarJogador cadastrar = new CadastrarJogador();
        transaction.replace(R.id.fragment_container, cadastrar);
        transaction.addToBackStack("SelecionarJogador");
        transaction.commit();
    }


    private void replaceFragmentNiveis(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Niveis niveis = new Niveis();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", j);
        niveis.setArguments(args);
        transaction.replace(R.id.fragment_container, niveis);
        transaction.addToBackStack("SelecionarJogador");
        transaction.commit();
    }
}
