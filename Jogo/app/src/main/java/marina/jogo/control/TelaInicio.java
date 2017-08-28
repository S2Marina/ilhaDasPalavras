package marina.jogo.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import marina.jogo.R;
import marina.jogo.dao.NivelDAO;
import marina.jogo.model.Nivel;

public class TelaInicio extends Fragment{

    private Button jogar;
    private Button ranking;
    private Button comoJogar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio, container, false);
        setHasOptionsMenu(true);

        MainActivity.t.setTitle("Jogo");
        MainActivity.t.setNavigationIcon(null);

        jogar = (Button) view.findViewById(R.id.inicio_btJogar);
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        comoJogar = (Button) view.findViewById(R.id.inicio_btComoJogar);
        comoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentComoJogar();
            }
        });

        ranking = (Button) view.findViewById(R.id.inicio_btRanking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentRanking();
            }
        });

        return view;

    }

    private void replaceFragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        SelecionarJogador sj = new SelecionarJogador();
        transaction.replace(R.id.fragment_container, sj);
        transaction.addToBackStack("Inicio");
        transaction.commit();
    }

    private void replaceFragmentRanking(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Ranking r = new Ranking();
        transaction.replace(R.id.fragment_container, r);
        transaction.addToBackStack("Inicio");
        transaction.commit();
    }

    private void replaceFragmentComoJogar(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ComoJogar cj = new ComoJogar();
        Bundle args = new Bundle();
        args.putInt("m", 0);
        cj.setArguments(args);
        transaction.replace(R.id.fragment_container, cj);
        transaction.addToBackStack("ComoJogar");
        transaction.commit();
    }

}
