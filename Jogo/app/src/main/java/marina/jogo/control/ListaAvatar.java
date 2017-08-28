package marina.jogo.control;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import marina.jogo.R;
import marina.jogo.adapter.AvatarAdapter;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;

public class ListaAvatar extends Fragment implements Serializable {

    private List<Avatar> lista;
    private RecyclerView rv;
    private AvatarDAO ad;
    private Jogador jogador;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        jogador = (Jogador) getArguments().getSerializable("JOGADOR");

        View view = inflater.inflate(R.layout.lista_avatar, container, false);

        MainActivity.t.setTitle("Avatares");
        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        ad = AvatarDAO.getInstance(getContext());
        ad.open();
        lista = ad.toList(ad.getCursor());
        rv.setAdapter(new AvatarAdapter(lista, getContext(), onClick()));
        ad.close();

        return view;
    }

    protected AvatarAdapter.AvatarOnClickListener onClick() {
        return new AvatarAdapter.AvatarOnClickListener() {
            @Override
            public void onClickAvatar(View view, int idx) {
                Log.d("jogo", "Clicou!");
                replaceFragment(idx);
            }
        };
    }


    private void replaceFragment(Integer idx){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        DetalheAvatar detalhe = new DetalheAvatar();
        Bundle args = new Bundle();
        args.putSerializable("AVATAR", lista.get(idx));
        args.putSerializable("JOGADOR", jogador);
        args.putSerializable("FRAGMENT", this);
        detalhe.setArguments(args);
        transaction.replace(R.id.fragment_container, detalhe, "tag");
        transaction.addToBackStack("ListaAvatar");
        transaction.commit();

    }

}
