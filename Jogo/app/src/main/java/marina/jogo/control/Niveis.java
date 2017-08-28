package marina.jogo.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import marina.jogo.R;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;

public class Niveis extends Fragment{
    Button nivel1;
    Button nivel2;
    Button nivel3;
    ImageView i1;
    ImageView i2;
    ImageView i3;
    TextView textView3;
    Jogador jogador;
    Jogo jogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jogador = (Jogador) getArguments().getSerializable("JOGADOR");

        Log.e("jogo", "avatar: " + jogador.getAvatar().getNome());

        View view = inflater.inflate(R.layout.niveis, container, false);
        setHasOptionsMenu(true);

        MainActivity.t.setTitle("Níveis");
        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        jogo = jogador.getJogo();

        Log.e("jogo", "jogo: " + jogo.toString());
        Log.e("jogo", "id do jogo: " + jogo.getId_jogo());
        Log.e("jogo", "nivel: " + jogo.getNivel().getId_nivel());


        Integer nivel;
        nivel = jogo.getNivel().getId_nivel();

        if (nivel == 1) {
            i1 = (ImageView) view.findViewById(R.id.niveis_i1);
            nivel1 = (Button) view.findViewById(R.id.nivel1);
            nivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment1();
                }
            });

            nivel1.setBackgroundColor(getResources().getColor(R.color.azul));
            i1.setImageResource(R.drawable.ic_lock_open_black_24dp);

            i2 = (ImageView) view.findViewById(R.id.niveis_i2);
            i2.setImageResource(R.drawable.ic_lock_outline_black_24dp);

            i3 = (ImageView) view.findViewById(R.id.niveis_i3);
            i3.setImageResource(R.drawable.ic_lock_outline_black_24dp);

        } else if (nivel == 2) {
            i1 = (ImageView) view.findViewById(R.id.niveis_i1);
            nivel1 = (Button) view.findViewById(R.id.nivel1);

            nivel1.setBackgroundColor(getResources().getColor(R.color.azul));
            i1.setImageResource(R.drawable.ic_lock_open_black_24dp);

            nivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment1();
                }
            });

            i2 = (ImageView) view.findViewById(R.id.niveis_i2);
            nivel2 = (Button) view.findViewById(R.id.nivel2);

            i2.setImageResource(R.drawable.ic_lock_open_black_24dp);
            nivel2.setBackgroundColor(getResources().getColor(R.color.azul));

            nivel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment2();
                }
            });

            i3 = (ImageView) view.findViewById(R.id.niveis_i3);
            i3.setImageResource(R.drawable.ic_lock_outline_black_24dp);
        }
        else if(nivel == 3){
            i1 = (ImageView) view.findViewById(R.id.niveis_i1);
            nivel1 = (Button) view.findViewById(R.id.nivel1);

            nivel1.setBackgroundColor(getResources().getColor(R.color.azul));
            i1.setImageResource(R.drawable.ic_lock_open_black_24dp);

            nivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment1();
                }
            });

            i2 = (ImageView) view.findViewById(R.id.niveis_i2);
            nivel2 = (Button) view.findViewById(R.id.nivel2);

            i2.setImageResource(R.drawable.ic_lock_open_black_24dp);
            nivel2.setBackgroundColor(getResources().getColor(R.color.azul));

            nivel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment2();
                }
            });

            i3 = (ImageView) view.findViewById(R.id.niveis_i3);
            nivel3 = (Button) view.findViewById(R.id.nivel3);

            i3.setImageResource(R.drawable.ic_lock_open_black_24dp);
            nivel3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Ops! Esse nível ainda não está pronto para jogar!!!", Toast.LENGTH_LONG).show();
                }
            });

        }

        return view;
    }


    private void replaceFragment1(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Nivel1 nivel1 = new Nivel1();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        nivel1.setArguments(args);
        transaction.replace(R.id.fragment_container, nivel1);
        transaction.addToBackStack("Niveis");
        transaction.commit();
    }

    private void replaceFragment2(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Nivel2 nivel2 = new Nivel2();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        nivel2.setArguments(args);
        transaction.replace(R.id.fragment_container, nivel2);
        transaction.addToBackStack("Niveis");
        transaction.commit();
    }
}
