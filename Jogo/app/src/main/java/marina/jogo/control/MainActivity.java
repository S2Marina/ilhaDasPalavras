package marina.jogo.control;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import java.io.ByteArrayOutputStream;

import marina.jogo.R;
import marina.jogo.dao.AvatarDAO;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.NivelDAO;
import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Nivel;

public class MainActivity extends AppCompatActivity {
    public static Toolbar t;
    public JogadorDAO jogDAO;
    public Jogador jog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nivel nivel = new Nivel();
        nivel.setId_nivel(1);

        Nivel nivel2 = new Nivel();
        nivel2.setId_nivel(2);

        Nivel nivel3 = new Nivel();
        nivel3.setId_nivel(3);

        NivelDAO nivelDAO = NivelDAO.getInstance(MainActivity.this);
        nivelDAO.open();
        nivelDAO.excluirTudo();
        nivelDAO.inserir(nivel);
        nivelDAO.inserir(nivel2);
        nivelDAO.inserir(nivel3);
        nivelDAO.close();

        Log.e("jogo", "nivel id: " + nivel.getId_nivel());
        Log.e("jogo", "nivel id: " + nivel2.getId_nivel());
        Log.e("jogo", "nivel id: " + nivel3.getId_nivel());


        t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);


        TelaInicio inicio = new TelaInicio();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, inicio).commit();

    }
}
