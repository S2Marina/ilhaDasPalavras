package marina.jogo.control;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import marina.jogo.R;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.dao.Contract;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.JogoDAO;
import marina.jogo.dao.NivelDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;
import marina.jogo.model.Nivel;


public class Nivel1 extends Fragment implements View.OnTouchListener, View.OnDragListener{

    private String tagIlha;
    private String tagNavio;
    private ImageView ilhaTesouro;
    private ImageView ilha;
    private ImageView ilha2;
    private ImageView navio;
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView pontos;
    private View v;
    private int fim = 0;
    private Jogador jogador;
    private String s1 = null;
    private String s2 = null;
    private Nivel nivel;
    private Integer certa;
    private int n;
    private ObjectAnimator x;
    private ObjectAnimator y;
    private AnimatorSet lista;
    private String dica = "É uma palavra de 2 sílabas!";
    private String dica2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jogador = (Jogador) getArguments().getSerializable("JOGADOR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nivel_1, container, false);
        setHasOptionsMenu(true);

        MainActivity.t.setTitle("Nível 1");
        MainActivity.t.setNavigationIcon(R.drawable.ic_cancel_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });
        MainActivity.t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_reiniciar: {
                        getFragmentManager().popBackStack();
                        replaceFragment1();
                        break;
                    }
                    case R.id.menuitem_dica: {
                        replaceFragmentMapa(s1, s2, dica, dica2);
                        break;
                    }
                }
                return true;
            }

        });

        s1 = null;
        s2 = null;

        pontos = (TextView) view.findViewById(R.id.nivel_1_pontos);
        navio = (ImageView) view.findViewById(R.id.nivel_1_navio);

        Log.e("jogo", " "+ savedInstanceState);

        if(savedInstanceState == null){
            if(jogador.getAvatar().getId_avatar() == 1){
                navio.setImageResource(R.drawable.navio_pirata);
            }
            else{
                navio.setImageResource(R.drawable.navio_estrela);
            }
            pontos.setText("0");
        }


        ilha = (ImageView) view.findViewById(R.id.nivel_1_ilha_a);
        ilha2 = (ImageView) view.findViewById(R.id.nivel_1_ilha_b);
        ilhaTesouro = (ImageView) view.findViewById(R.id.nivel_1_ilha_c);
        ilhaTesouro.setTag("3");

        navio.setOnTouchListener(this);
        ilha.setOnDragListener(this);
        ilha2.setOnDragListener(this);
        ilhaTesouro.setOnDragListener(this);

        a = (TextView) view.findViewById(R.id.textViewA);
        b = (TextView) view.findViewById(R.id.textViewB);
        c = (TextView) view.findViewById(R.id.textViewC);


        Random rand = new Random();
        n = rand.nextInt(4) + 1;

        switch (n){
            case 0:{
                a.setText("CA");
                c.setText("SA");
                b.setText("MO");
                certa = 1;
                dica2 = "Lugar onde se mora";
                break;
            }
            case 1:{
                a.setText("NA");
                c.setText("VIO");
                b.setText("IA");
                certa = 1;
                dica2 = "Barco grande";
                break;
            }
            case 2:{
                a.setText("LI");
                c.setText("VRO");
                b.setText("ME");
                certa = 1;
                dica2 = "Onde se escrevem as histórias";
                break;
            }
            case 3:{
                a.setText("MU");
                c.setText("CA");
                b.setText("FA");
                certa = 2;
                dica2 = "Serve para cortar alimentos";
                break;
            }
            case 4:{
                a.setText("LU");
                c.setText("IA");
                b.setText("JO");
                certa = 2;
                dica2 = "Colar ou anel precioso";
                break;
            }
        }

        v = view;

        return view;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final View dragged = (View) event.getLocalState();

        x = null;
        y = null;

        lista = new AnimatorSet();


        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:{
                return true;
            }
            case DragEvent.ACTION_DRAG_ENTERED:{
                //Log.e("jogo", "Entrou na área de Drag");
                break;
            }
            case DragEvent.ACTION_DRAG_EXITED:{
                //Log.e("jogo", "Saiu da área de Drag");
                break;
            }
            case DragEvent.ACTION_DRAG_ENDED:{
                //Log.e("jogo", "Ação final do Drag");
                break;
            }
            case DragEvent.ACTION_DROP: {
                Log.e("jogo", "Objeto colado");

                //a tag do navio comeca com 0
                //a tag das ilhas varia
                //ilha a = 2
                //ilha b = 1
                //ilha c = 3

                tagIlha = v.getTag().toString();
                tagNavio = dragged.getTag().toString();

                Log.e("jogo", "tag ilha a = " + (Integer.parseInt(tagIlha)));
                Log.e("jogo", "tag navio a= " + (Integer.parseInt(tagNavio)));

                int soma = Integer.parseInt(tagIlha) + Integer.parseInt(tagNavio);

                switch(soma){ //soma-se a tag da ilha com a do navio para determinar qual a rota percorrida
                    case 2:{ //se a soma é 2 (2 ilha + 0 navio)
                        Log.e("jogo", "a");                //o navio se move até a ilha a
                        x = ObjectAnimator.ofFloat(navio, "x", 20, 160);
                        y = ObjectAnimator.ofFloat(navio, "y", 140, 65);
                        if(certa == 1){
                            pontos.setText("1");
                        }
                        s1 = a.getText().toString();
                        tagNavio+=tagIlha;       //e troca sua tag para a soma
                        dragged.setTag(tagNavio);          //isso será necessário para mover o navio até a prox ilha
                        break;
                    }
                    case 1:{ //se a soma é 1 (1 ilha + 0 navio)
                        Log.e("jogo", "b");   //o navio vai em direção a ilha b
                        x = ObjectAnimator.ofFloat(navio, "x", 20, 180);
                        y = ObjectAnimator.ofFloat(navio, "y", 140, 180);
                        if(certa == 2){
                            pontos.setText("1");
                        }
                        s1 = b.getText().toString();
                        tagNavio+=tagIlha;                      //e sua tag passa a valer a soma
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 5:{ //se a soma é 5 (3 ilha + 2 navio)
                        Log.e("jogo", "a-c");   //o navio vai da ilha a para a ilha c
                        x = ObjectAnimator.ofFloat(navio, "x", 160, 400);
                        y = ObjectAnimator.ofFloat(navio, "y", 65, 90);
                        s2 = c.getText().toString();

                        if(certa == 1){
                            pontos.setText("2");
                            fim = 1;
                        }else{
                            fim = 2;
                        }
                        break;
                    }
                    case 4:{ //se a soma é 4 (3 ilha + 1 navio)
                        Log.e("jogo", "b-c");  //da ilha b para a ilha c
                        s2 = c.getText().toString();
                        x = ObjectAnimator.ofFloat(navio, "x", 180, 400);
                        y = ObjectAnimator.ofFloat(navio, "y", 180, 90);

                        if(certa == 2){
                            pontos.setText("2");
                            fim = 1;
                        }else{
                            fim = 2;
                        }
                        break;
                    }
                    case 3:{ //se a soma é 3 o jogador está tentando fazer um movimento não permitido no jogo
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        if(Integer.parseInt(tagNavio) == 2){ //da ilha a para ilha b
                            Log.e("jogo", "Não mover!");
                            x = ObjectAnimator.ofFloat(navio, "x", 160, 160);
                            y = ObjectAnimator.ofFloat(navio, "y", 65, 65); //o navio não se move
                            break;
                        }else{
                            Log.e("jogo", "Não mover!");  //o mesmo acontece da ilha b para a ilha a
                            x = ObjectAnimator.ofFloat(navio, "x", 180, 180);
                            y = ObjectAnimator.ofFloat(navio, "y", 180, 180);
                            break;
                        }
                    }
                }

                lista.playTogether(x, y);

                lista.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (fim == 1) {

                            NivelDAO nivelDAO = NivelDAO.getInstance(getContext());
                            nivelDAO.open();
                            List<Nivel> niveis = nivelDAO.toList(nivelDAO.getCursor());
                            nivelDAO.close();

                            for (int a = 0; a < niveis.size(); a++) {
                                if (niveis.get(a).getId_nivel() == 2) {
                                    nivel = niveis.get(a);
                                    break;
                                }
                            }

                            Jogo jogo = jogador.getJogo();
                            jogo.setPontos(2);
                            if(jogo.getNivel().getId_nivel() == 1){
                                jogo.setNivel(nivel);
                            }


                            JogoDAO jogoDAO = JogoDAO.getInstance(getContext());
                            jogoDAO.open();
                            jogoDAO.alterar(jogo);
                            jogoDAO.close();

                            Log.e("jogo", "jogador:" + jogador);

                            jogador.setPontuacao_total(jogador.getPontuacao_total() + 2);
                            jogador.setJogo(jogo);
                            Log.e("jogo", "avatar do jogador: " + jogador.getAvatar());

                            JogadorDAO jogadorDAO = JogadorDAO.getInstance(getContext());
                            jogadorDAO.open();
                            jogadorDAO.alterar(jogador);
                            jogadorDAO.close();

                            Log.e("jogo", "jogo: " + jogo.toString());
                            Log.e("jogo", "id do jogo: " + jogo.getId_jogo());
                            Log.e("jogo", "id do jogo do jogador: " + jogador.getJogo().getId_jogo());
                            Log.e("jogo", "nivel do jogo:" + jogo.getNivel());
                            Log.e("jogo", "nivel do jogo do jogador: " + jogador.getJogo().getNivel());


                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setIcon(R.drawable.tesouro);
                            builder.setTitle("Parabéns! Você completou o nível 1!");
                            builder.setMessage("Você encontrou o tesouro escondido e formou a palavra " + s1 + s2 + "!");
                            builder.setPositiveButton("Próximo nível", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("jogo", "Próximo nível");
                                    getFragmentManager().popBackStack();
                                }
                            });
                            builder.setNeutralButton("Trocar avatar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("jogo", "Trocar avatar");


                                    Avatar a = new Avatar();
                                    AvatarDAO avatarDAO = AvatarDAO.getInstance(getContext());
                                    avatarDAO.open();
                                    List<Avatar> lista = avatarDAO.toList(avatarDAO.getCursor());
                                    for (int m = 0; m < lista.size(); m++) {
                                        if (lista.get(m).getId_avatar() == 2) {
                                            a = lista.get(m);
                                        }
                                    }
                                    a.setBloqueado(false);
                                    avatarDAO.alterar(a);
                                    avatarDAO.close();

                                    replaceFragmentAvatar();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (fim == 2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setIcon(R.drawable.tesouro);
                            builder.setTitle("Ops! Algo deu errado!");
                            builder.setMessage("Você não conseguiu completar a palavra corretamente e ficou perdido no oceano!");
                            builder.setPositiveButton("Tentar Novamente", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("jogo", "Tentar Novamente");
                                    getFragmentManager().popBackStack();
                                    replaceFragment1();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
                lista.setDuration(2000);
                lista.start();
                Log.e("jogo", "tag ilha d = " + (Integer.parseInt(tagIlha)));
                Log.e("jogo", "tag navio d = " + (Integer.parseInt(tagNavio)));

                break;
            }
            default:{
                break;
            }
        }
        return false;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_jogo, menu);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("jogo", "Entrou no onTouch. Tag da view = " + v.getTag());
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData clipData = ClipData.newPlainText("jogo", v.getTag().toString());
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
            v.startDrag(clipData, dsb, v, 0);
            return true;
        }
        return false;
    }

    private void replaceFragment1(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Nivel1 nivel1 = new Nivel1();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        nivel1.setArguments(args);
        transaction.replace(R.id.fragment_container, nivel1);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragmentMapa(String s1, String s2, String dica, String dica2){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        getFragmentManager().saveFragmentInstanceState(Nivel1.this);
        Mapa1 mapa1 = new Mapa1();
        Bundle args = new Bundle();
        args.putString("s1", s1);
        args.putString("s2", s2);
        args.putString("dica", dica);
        args.putString("dica2", dica2);
        mapa1.setArguments(args);
        transaction.replace(R.id.fragment_container, mapa1);
        transaction.addToBackStack("Nivel1");
        transaction.commit();
    }

    private void replaceFragmentAvatar(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ListaAvatar listaAvatar = new ListaAvatar();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        listaAvatar.setArguments(args);
        transaction.replace(R.id.fragment_container, listaAvatar);
        transaction.addToBackStack("Nivel1");
        transaction.commit();
    }

}
