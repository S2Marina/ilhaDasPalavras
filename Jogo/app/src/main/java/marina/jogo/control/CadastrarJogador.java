package marina.jogo.control;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import marina.jogo.R;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.JogoDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;


public class CadastrarJogador extends Fragment {

    private EditText etNome;
    private TextView textAvatar;
    private static Avatar avatar;
    private String nome;
    private Button btAvatar;

    public static void setAvatar(Avatar avatar) {
        CadastrarJogador.avatar = avatar;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cadastrar_jogador, container, false);
        setHasOptionsMenu(true);

        etNome = (EditText) view.findViewById(R.id.cadastrarJogador_editTextNome);
        textAvatar = (TextView) view.findViewById(R.id.cadastrarJogador_textAvatar);

        if(avatar == null){
            textAvatar.setText("Escolha um avatar");
        }
        else{
            textAvatar.setText(avatar.getNome());
        }

        MainActivity.t.setTitle("Cadastrar Jogador");

        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatar = null;
                limpar();
                getFragmentManager().popBackStack();
            }
        });

        MainActivity.t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_salvar: {

                        if (etNome.getText().toString().isEmpty() || textAvatar.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Você deve preencher todos os campos.", Toast.LENGTH_SHORT).show();

                        } else {
                            Jogador j = new Jogador();

                            nome = etNome.getText().toString();

                            boolean ok;
                            ok = j.cadastrarJogador(nome, avatar, getContext());

                            if (ok == true) {
                                Toast.makeText(getContext(), "Jogador salvo com sucesso", Toast.LENGTH_LONG).show();
                                limpar();
                                avatar = null;
                            }
                            getFragmentManager().popBackStack();
                        }
                        break;
                    }
                }
                return true;
            }
        });


        btAvatar = (Button) view.findViewById(R.id.cadastrarJogador_btAvatar);

        btAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

       return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_cadastrar_jogador, menu);
    }

    private void replaceFragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Jogador jogador = new Jogador();
        ListaAvatar listaAvatar = new ListaAvatar();
        Bundle args = new Bundle();
        args.putSerializable("JOGADOR", jogador);
        listaAvatar.setArguments(args);
        transaction.replace(R.id.fragment_container, listaAvatar);
        transaction.addToBackStack("CadastrarJogador");
        transaction.commit();
    }

    public void limpar(){
        etNome.setText(null);
        textAvatar.setText(null);
    }
}



