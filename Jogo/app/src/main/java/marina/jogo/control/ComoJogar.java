package marina.jogo.control;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import marina.jogo.R;

public class ComoJogar extends Fragment {

    private ImageView navio;
    private TextView text1;
    private TextView text2;
    private Integer m = 0;
    private ObjectAnimator x;
    private ObjectAnimator y;
    private ObjectAnimator v;
    private ObjectAnimator h;
    private ObjectAnimator v2;
    private ObjectAnimator h2;
    ImageView circulo;
    ImageView ilhaA;
    ImageView ilhaB;
    ImageView ilhaC;
    AnimatorSet lista;
    TextView silaba1;
    TextView silaba2;
    TextView t;
    TextView t2;
    TextView a;
    TextView b;
    TextView c;


    private Animator.AnimatorListener listenerX = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            y.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };

    private Animator.AnimatorListener listenerY = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            x.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };

    private Animator.AnimatorListener listener1 = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            lista = new AnimatorSet();
            lista.addListener(listener2);
            lista.playTogether(h2, v2);
            lista.setDuration(2000);
            lista.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };

    private Animator.AnimatorListener listener2 = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            lista = new AnimatorSet();
            lista.addListener(listener1);
            lista.playTogether(h,v);
            lista.setDuration(2000);
            lista.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };

    public ComoJogar() {
        circulo = null;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cj1, container, false);

        m = (Integer) getArguments().getSerializable("m");

        silaba1 = (TextView) view.findViewById(R.id.mapa1_s1);
        silaba2 = (TextView) view.findViewById(R.id.mapa1_s2);
        t = (TextView) view.findViewById(R.id.textView6);
        t2 = (TextView) view.findViewById(R.id.textView7);

        silaba1.setVisibility(View.INVISIBLE);
        silaba2.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);

        ilhaA = (ImageView) view.findViewById(R.id.nivel_1_ilha_a);
        ilhaB = (ImageView) view.findViewById(R.id.nivel_1_ilha_b);
        ilhaC = (ImageView) view.findViewById(R.id.nivel_1_ilha_c);
        a = (TextView) view.findViewById(R.id.textViewA);
        b =(TextView) view.findViewById(R.id.textViewB);
        c = (TextView) view.findViewById(R.id.textViewC);
        navio = (ImageView) view.findViewById(R.id.cj_navio);
        text1 = (TextView) view.findViewById(R.id.como1_textView);
        text2 = (TextView) view.findViewById(R.id.como2_textView);
        circulo = (ImageView) view.findViewById(R.id.cj1_c);

        h = ObjectAnimator.ofFloat(navio, "x", 0, 140);
        v = ObjectAnimator.ofFloat(navio, "y", 95, 35);

        h2 = ObjectAnimator.ofFloat(navio, "x", 140, 340);
        v2 = ObjectAnimator.ofFloat(navio, "y", 35, 60);

        x = ObjectAnimator.ofFloat(circulo, "alpha", 0f, 1f);
        x.setDuration(250);
        x.addListener(listenerX);

        y = ObjectAnimator.ofFloat(circulo, "alpha", 1f, 0f);
        y.setDuration(250);
        y.addListener(listenerY);

        if (m == 0) {
            x.start();
        }
        else if(m == 1){
            x.cancel();
            y.cancel();
            lista = new AnimatorSet();
            lista.playTogether(h,v);
            lista.setDuration(2000);
            lista.addListener(listener1);
            lista.start();
            text1.setText("Mas, para isso, você deve parar nas ilhas com ");
            text2.setText("as sílabas para formar palavras pelo caminho!");
            circulo.setVisibility(View.INVISIBLE);
        }
        else if(m == 2){
            h.cancel();
            v.cancel();
            h2.cancel();
            v2.cancel();

            ilhaA.setVisibility(View.INVISIBLE);
            ilhaB.setVisibility(View.INVISIBLE);
            ilhaC.setVisibility(View.INVISIBLE);
            navio.setVisibility(View.INVISIBLE);
            circulo.setVisibility(View.INVISIBLE);
            a.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            c.setVisibility(View.INVISIBLE);

            silaba1.setVisibility(View.VISIBLE);
            silaba2.setVisibility(View.VISIBLE);
            t.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);

            view.setBackgroundResource(R.drawable.mapa);

            text1.setText("Se quiser saber por quais ilhas já passou ");
            text2.setText("ou se quiser uma dica é só acessar o mapa!");
        }

        setHasOptionsMenu(true);

        Log.e("jogo", "m view = " + m.toString());

        MainActivity.t.setTitle("Como Jogar?");
        if(m > 0){
            MainActivity.t.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
            MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m--;
                    Log.e("jogo", "m voltar: " + m);
                    getFragmentManager().popBackStack();
                    replaceFragment(m);
                }
            });
        }
        else{
            MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                    getFragmentManager().popBackStack();
                }
            });
        }

        MainActivity.t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_proximo: {
                        prox();
                        break;
                    }
                }
                return true;
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_como_jogar, menu);
    }

    private void replaceFragment(int m){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ComoJogar comoJogar = new ComoJogar();
        Bundle args = new Bundle();
        args.putInt("m", m);
        comoJogar.setArguments(args);
        transaction.replace(R.id.fragment_container, comoJogar);
        transaction.addToBackStack("ComoJogar");
        transaction.commit();
    }

    private void prox(){
        switch (m){
            case 2:{
                break;
            }
            default:{
                m++;
                replaceFragment(m);
                break;
            }
        }
    }


}
