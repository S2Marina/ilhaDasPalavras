package marina.jogo.control;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import marina.jogo.R;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.JogoDAO;
import marina.jogo.dao.NivelDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;
import marina.jogo.model.Nivel;


public class Nivel2 extends Fragment implements View.OnTouchListener, View.OnDragListener{

    private String tagIlha;
    private String tagNavio;
    private ImageView ilhaA;
    private ImageView ilhaB;
    private ImageView ilhaC;
    private ImageView ilhaD;
    private ImageView ilhaE;
    private ImageView ilhaF;
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private ImageView navio;
    private TextView pontos;
    private View v;
    private int fim = 0;
    private Jogador jogador;
    private String s1 = null;
    private String s2 = null;
    private String s3 = null;
    private ObjectAnimator x;
    private ObjectAnimator y;
    private AnimatorSet lista;
    private Nivel nivel;
    private Integer certa;
    private Integer certa2;
    private Integer certa3;
    private int n;
    String dica = "É uma palavra de 3 sílabas!";
    String dica2;
    Integer p = 0;
    String resposta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jogador = (Jogador) getArguments().getSerializable("JOGADOR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nivel_2, container, false);
        setHasOptionsMenu(true);

        MainActivity.t.setTitle("Nível 2");
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
                        replaceFragment2();
                        break;
                    }
                    case R.id.menuitem_dica: {
                        replaceFragmentMapa(s1, s2, s3, dica, dica2);
                        break;
                    }
                }
                return true;
            }
        });

        pontos = (TextView) view.findViewById(R.id.nivel_2_pontos);

        s1 = null;
        s2 = null;
        s3 = null;

        navio = (ImageView) view.findViewById(R.id.nivel_2_navio);

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

        a = (TextView) view.findViewById(R.id.nivel_2_text_a);
        b = (TextView) view.findViewById(R.id.nivel_2_text_b);
        c = (TextView) view.findViewById(R.id.nivel_2_text_c);
        d = (TextView) view.findViewById(R.id.nivel_2_text_d);
        e = (TextView) view.findViewById(R.id.nivel_2_text_e);
        f = (TextView) view.findViewById(R.id.nivel_2_text_f);

        ilhaA = (ImageView) view.findViewById(R.id.nivel_2_ilha_a);
        ilhaB = (ImageView) view.findViewById(R.id.nivel_2_ilha_b);
        ilhaC = (ImageView) view.findViewById(R.id.nivel_2_ilha_c);
        ilhaD = (ImageView) view.findViewById(R.id.nivel_2_ilha_d);
        ilhaE = (ImageView) view.findViewById(R.id.nivel_2_ilha_e);
        ilhaF = (ImageView) view.findViewById(R.id.nivel_2_ilha_f);

        navio.setOnTouchListener(this);

        ilhaA.setOnDragListener(this);
        ilhaB.setOnDragListener(this);
        ilhaC.setOnDragListener(this);

        ilhaD.setOnDragListener(this);
        ilhaE.setOnDragListener(this);
        ilhaF.setOnDragListener(this);

        Random rand = new Random();
        n = rand.nextInt(4) + 1;

        switch (n){
            case 0:{
                a.setText("CE");
                d.setText("BO");
                f.setText("LA");
                b.setText("MU");
                c.setText("DE");
                e.setText("LI");
                resposta = "CEBOLA";
                dica2 = "Alimento que faz chorar";
                certa = 1;
                certa2 = 2;
                certa3 = 1;
                break;
            }
            case 1:{
                a.setText("MA");
                c.setText("CA");
                e.setText("CO");
                b.setText("JI");
                d.setText("ME");
                f.setText("RA");
                resposta = "MACACO";
                dica2 = "Animal que come banana";
                certa = 1;
                certa2 = 1;
                certa3 = 2;
                break;
            }
            case 2:{
                b.setText("CA");
                c.setText("DEI");
                e.setText("RA");
                a.setText("HI");
                d.setText("FI");
                f.setText("TI");
                resposta = "CADEIRA";
                dica2 = "Serve para sentar";
                certa = 2;
                certa2 = 1;
                certa3 = 2;
                break;
            }
            case 3:{
                b.setText("PI");
                c.setText("RA");
                f.setText("TA");
                a.setText("BU");
                d.setText("DU");
                e.setText("LO");
                resposta = "PIRATA";
                dica2 = "Personagem deste jogo";
                certa = 2;
                certa2 = 1;
                certa3 = 1;
                break;
            }
            case 4:{
                a.setText("MO");
                d.setText("CHI");
                e.setText("LA");
                b.setText("SA");
                c.setText("DI");
                f.setText("MU");
                resposta = "MOCHILA";
                dica2 = "Objeto para guardar o material da escola";
                certa = 1;
                certa2 = 2;
                certa3 = 2;
                break;
            }
        }

        v = view;


        return view;
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

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final View dragged = (View) event.getLocalState();

        x = null;
        y = null;

        lista = new AnimatorSet();


        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED: {
                return true;
            }
            case DragEvent.ACTION_DRAG_ENTERED: {
                //Log.e("jogo", "Entrou na área de Drag");
                break;
            }
            case DragEvent.ACTION_DRAG_EXITED: {
                //Log.e("jogo", "Saiu da área de Drag");
                break;
            }
            case DragEvent.ACTION_DRAG_ENDED: {
                //Log.e("jogo", "Ação final do Drag");
                break;
            }
            case DragEvent.ACTION_DROP: {
                Log.e("jogo", "Objeto colado");

                tagIlha = v.getTag().toString();
                tagNavio = dragged.getTag().toString();

                Log.e("jogo", "tag ilha a = " + (Integer.parseInt(tagIlha)));
                Log.e("jogo", "tag navio a= " + (Integer.parseInt(tagNavio)));

                Integer soma = Integer.parseInt(tagIlha) + Integer.parseInt(tagNavio);
                Log.e("jogo", "soma" + soma.toString());

                switch (soma) {
                    //primeira silaba
                    case 10: {
                        Log.e("jogo", "a");
                        x = ObjectAnimator.ofFloat(navio, "x", 0, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 95, 50);
                        if (certa == 1) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s1 = a.getText().toString();
                        tagNavio += tagIlha;
                        dragged.setTag(tagNavio);
                        break;

                    }
                    case 8: {
                        Log.e("jogo", "b");
                        x = ObjectAnimator.ofFloat(navio, "x", 0, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 95, 175);
                        if (certa == 2) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s1 = b.getText().toString();
                        tagNavio += tagIlha;
                        dragged.setTag(tagNavio);
                        break;
                    }

                    //segunda sílaba
                    case 14: {
                        Log.e("jogo", "a-c");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 240);
                        y = ObjectAnimator.ofFloat(navio, "y", 50, 30);
                        if (certa2 == 1) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s2 = c.getText().toString();
                        tagNavio = "50";
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 13: {
                        Log.e("jogo", "b-d");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 220);
                        y = ObjectAnimator.ofFloat(navio, "y", 175, 195);
                        if (certa2 == 2) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s2 = d.getText().toString();
                        tagNavio = "71";
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 15: {
                        Log.e("jogo", "a-d");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 220);
                        y = ObjectAnimator.ofFloat(navio, "y", 50, 195);
                        if (certa2 == 2) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s2 = d.getText().toString();
                        tagNavio = "71";
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 12: {
                        Log.e("jogo", "b-c");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 240);
                        y = ObjectAnimator.ofFloat(navio, "y", 175, 30);
                        if (certa2 == 1) {
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s2 = c.getText().toString();
                        tagNavio = "50";
                        dragged.setTag(tagNavio);
                        break;
                    }

                    //terceira silaba

                    case 80:{
                            Log.e("jogo", "c-f");
                            x = ObjectAnimator.ofFloat(navio, "x", 240, 360);
                            y = ObjectAnimator.ofFloat(navio, "y", 30, 55);
                            if (certa3 == 1) {
                                p += 1;
                                pontos.setText(p.toString());
                            }
                            s3 = f.getText().toString();
                            if(resposta.equals(s1 + s2 + s3)){
                                fim = 1;
                            } else{
                                fim = 2;
                            }
                            dragged.setTag(tagNavio);
                            break;
                    }
                    case 90:{
                        Log.e("jogo", "c-e");
                        x = ObjectAnimator.ofFloat(navio, "x", 240, 360);
                        y = ObjectAnimator.ofFloat(navio, "y", 30, 200);
                        if(certa3 == 2){
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s3 = e.getText().toString();
                        if(resposta.equals(s1 + s2 + s3)){
                            fim = 1;
                        } else{
                            fim = 2;
                        }
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 101:{
                        Log.e("jogo", "d-f");
                        x = ObjectAnimator.ofFloat(navio, "x", 220, 360);
                        y = ObjectAnimator.ofFloat(navio, "y", 195, 55);
                        if(certa3 == 1){
                            p+=1;
                            pontos.setText(p.toString());
                        }
                        s3 = f.getText().toString();
                        if(resposta.equals(s1 + s2 + s3)){
                            fim = 1;
                        } else{
                            fim = 2;
                        }
                        dragged.setTag(tagNavio);
                        break;
                    }
                    case 111:{
                        Log.e("jogo", "d-e");
                        x = ObjectAnimator.ofFloat(navio, "x", 220, 360);
                        y = ObjectAnimator.ofFloat(navio, "y", 195, 200);
                        if(certa3 == 2){
                            p+=1;
                        }
                        s3 = e.getText().toString();
                        if(resposta.equals(s1 + s2 + s3)){
                            fim = 1;
                        } else{
                            fim = 2;
                        }
                        dragged.setTag(tagNavio);
                        break;
                    }

                    //não move o navio
                    case 18: {
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        if (tagIlha.equals("10")) { //b-a
                            Log.e("jogo", "18Não mover!");
                            x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                            y = ObjectAnimator.ofFloat(navio, "y", 175, 175); //o navio não se move
                            break;
                        }
                        else{ //a-b
                                Log.e("jogo", "18Não mover!");
                                x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                                y = ObjectAnimator.ofFloat(navio, "y", 50, 50); //o navio não se move
                                break;
                            }
                        }
                    case 55: {
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                            // /c-d
                            Log.e("jogo", "55Não mover!");
                            x = ObjectAnimator.ofFloat(navio, "x", 240, 240);
                            y = ObjectAnimator.ofFloat(navio, "y", 30, 30); //o navio não se move
                            break;
                    }
                    case 75:{
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "74Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 220, 220);
                        y = ObjectAnimator.ofFloat(navio, "y", 195, 195); //o navio não se move
                        break;
                    }
                    case 60: { //c-a
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "60Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 240, 240);
                        y = ObjectAnimator.ofFloat(navio, "y", 30, 30); //o navio não se move
                        break;
                    }
                    case 79: { //d-b
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "78Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 220, 220);
                        y = ObjectAnimator.ofFloat(navio, "y", 195, 195); //o navio não se move
                        break;
                    }
                    case 81:{ //d-a
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "81Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 220, 220);
                        y = ObjectAnimator.ofFloat(navio, "y", 195, 195); //o navio não se move
                        break;
                    }
                    case 58: { //c-b
                        Toast.makeText(getContext(), "Você não pode voltar com o seu navio! Siga em frente em direção a Ilha do Tesouro!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "78Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 240, 240);
                        y = ObjectAnimator.ofFloat(navio, "y", 30, 30); //o navio não se move
                        break;
                    }
                    case 50:{//a-e
                        Toast.makeText(getContext(), "Não se esqueça que neste nível as palavras tem 3 sílabas!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "50Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 50, 50); //o navio não se move
                        break;
                    }
                    case 40:{//a-f
                        Toast.makeText(getContext(), "Não se esqueça que neste nível as palavras tem 3 sílabas!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "40Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 50, 50); //o navio não se move
                        break;
                    }
                    case 38:{//b-f
                        Toast.makeText(getContext(), "Não se esqueça que neste nível as palavras tem 3 sílabas!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "38Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 175, 175); //o navio não se move
                        break;
                    }
                    case 48:{//b-e
                        Toast.makeText(getContext(), "Não se esqueça que neste nível as palavras tem 3 sílabas!", Toast.LENGTH_LONG).show();
                        Log.e("jogo", "48Não mover!");
                        x = ObjectAnimator.ofFloat(navio, "x", 95, 95);
                        y = ObjectAnimator.ofFloat(navio, "y", 175, 175); //o navio não se move
                        break;
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
                                    if (niveis.get(a).getId_nivel() == 3) {
                                        nivel = niveis.get(a);
                                        break;
                                    }

                                }

                                Jogo jogo = jogador.getJogo();
                                jogo.setPontos(3);
                                jogo.setNivel(nivel);

                                JogoDAO jogoDAO = JogoDAO.getInstance(getContext());
                                jogoDAO.open();
                                jogoDAO.alterar(jogo);
                                jogoDAO.close();

                                Log.e("jogo", "jogador:" + jogador);

                                jogador.setPontuacao_total(jogador.getPontuacao_total() + 3);
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
                                builder.setTitle("Parabéns! Você completou o nível 2!");
                                builder.setMessage("Você encontrou o tesouro escondido e formou a palavra " + s1 + s2 + s3 + "!");
                                builder.setPositiveButton("Próximo nível", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.e("jogo", "Próximo nível");
                                        getFragmentManager().popBackStack();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                        } else if (fim == 2){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setIcon(R.drawable.tesouro);
                            builder.setTitle("Ops! Algo deu errado!");
                            builder.setMessage("Você não conseguiu completar a palavra corretamente e ficou perdido no oceano!");
                            builder.setPositiveButton("Tentar Novamente", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("jogo", "Tentar Novamente");
                                    getFragmentManager().popBackStack();
                                    replaceFragment2();
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
        }
            return false;
        }


    private void replaceFragment2(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Nivel2 nivel2 = new Nivel2();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        nivel2.setArguments(args);
        transaction.replace(R.id.fragment_container, nivel2);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void replaceFragmentMapa(String s1, String s2, String s3, String dica, String dica2){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        getFragmentManager().saveFragmentInstanceState(Nivel2.this);
        Mapa2 mapa2 = new Mapa2();
        Bundle args = new Bundle();
        args.putString("s1", s1);
        args.putString("s2", s2);
        args.putString("s3", s3);
        args.putString("dica", dica);
        args.putString("dica2", dica2);
        mapa2.setArguments(args);
        transaction.replace(R.id.fragment_container, mapa2);
        transaction.addToBackStack("Nivel2");
        transaction.commit();
    }

}
