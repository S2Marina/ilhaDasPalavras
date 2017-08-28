package marina.jogo.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import marina.jogo.R;
import marina.jogo.adapter.RankingAdapter;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.model.Jogador;

public class Ranking extends Fragment {

    private RecyclerView rv;
    private JogadorDAO jd;
    private CardView cd;
    private List<Jogador> lista = new ArrayList<Jogador>();
    private Jogador j = new Jogador();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking, container, false);
        setHasOptionsMenu(true);

        MainActivity.t.setTitle("Ranking");
        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.ranking_recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        cd = (CardView) view.findViewById(R.id.ranking_cardview1);

        jd  = JogadorDAO.getInstance(getContext());
        jd.open();
        lista = jd.toList(jd.getRanking(), getContext());
        rv.setAdapter(new RankingAdapter(lista, getContext()));
        jd.close();


        return view;
    }

}
