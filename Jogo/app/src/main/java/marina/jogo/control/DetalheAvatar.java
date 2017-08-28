package marina.jogo.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import marina.jogo.R;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;


public class DetalheAvatar extends Fragment {

    private Avatar a;
    private ImageView img;
    private TextView nome;
    private CheckBox cb;
    private Jogador j;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        j = (Jogador) getArguments().getSerializable("JOGADOR");
        a = (Avatar) getArguments().getSerializable("AVATAR");
        fragment = (Fragment) getArguments().getSerializable("FRAGMENT");


        View view = inflater.inflate(R.layout.detalhe_avatar, container, false);

        setHasOptionsMenu(true);

        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
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
                    case R.id.menuitem_salvar: {
                        if(a.isBloqueado()){
                            Toast.makeText(getContext(), "Ops! Esse avatar ainda está bloqueado!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(j.getJogo() == null){
                                Avatar avatar = new Avatar();
                                avatar.escolherAvatar(a);
                                getFragmentManager().popBackStack();
                                getFragmentManager().popBackStack();

                            }else{
                                j.setAvatar(a);
                                JogadorDAO jogadorDAO = JogadorDAO.getInstance(getContext());
                                jogadorDAO.open();
                                jogadorDAO.alterar(j);
                                jogadorDAO.close();
                                getFragmentManager().popBackStack();
                                getFragmentManager().popBackStack();
                                getFragmentManager().popBackStack();
                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });

        img = (ImageView) view.findViewById(R.id.detalhe_imageAvatar);
        nome = (TextView) view.findViewById(R.id.detalhe_textNomeAvatar);

        cb = (CheckBox) view.findViewById(R.id.detalhe_checkBox);
        cb.setEnabled(false);

        nome.setText(a.nome);

        Bitmap b = BitmapFactory.decodeByteArray(a.imagem,0,a.imagem.length);
        img.setImageBitmap(b);

        if(a.isBloqueado()==true){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }

        MainActivity.t.setTitle(a.getNome());

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detalhe_avatar, menu);
    }

    }
