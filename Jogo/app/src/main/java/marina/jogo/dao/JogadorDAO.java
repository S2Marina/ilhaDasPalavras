package marina.jogo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import marina.jogo.model.Avatar;
import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;

public class JogadorDAO {
    private static JogadorDAO j;
    private final Context context;
    private DBHelper myHelper;
    private SQLiteDatabase database ;

    private JogadorDAO(Context context){
        this.context = context;
    }

    public static JogadorDAO getInstance(Context ctx){
        if(j == null){
            j = new JogadorDAO(ctx);
            return j;
        }
        return j;
    }

    public void open() {
        myHelper = DBHelper.getInstance(context);
        database = myHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }


    public Boolean salvar(Jogador j) {
        open();
       if (j.getId_jogador() == null) {
            return inserir(j);
        } else {
            return alterar(j);
        }
    }

    public Boolean inserir(Jogador j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Jogador.COLUNA_NOME, j.getNome());
        valores.put(Contract.Jogador.COLUNA_PONTUACAO_TOTAL, j.getPontuacao_total());
        valores.put(Contract.Jogador.COLUNA_AVATAR, j.getAvatar().getId_avatar());
        valores.put(Contract.Jogador.COLUNA_JOGO, j.getJogo().getId_jogo());

        database.insert(Contract.Jogador.TABELA_NOME, null , valores);
        close();
        return true;
    }

    public Integer excluir(Jogador j) {
        return database.delete(Contract.Jogador.TABELA_NOME, Contract.Jogador.COLUNA_ID + " = " + j.getId_jogador(), null);
    }

    public Boolean alterar(Jogador j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Jogador.COLUNA_PONTUACAO_TOTAL, j.getPontuacao_total());
        valores.put(Contract.Jogador.COLUNA_NOME, j.getNome());
        valores.put(Contract.Jogador.COLUNA_JOGO, j.getJogo().getId_jogo());
        valores.put(Contract.Jogador.COLUNA_AVATAR, j.getAvatar().getId_avatar());

        //database.update(Contract.Jogador.TABELA_NOME, valores, Contract.Jogador.COLUNA_ID + " =")
        database.update(Contract.Jogador.TABELA_NOME, valores, Contract.Jogador.COLUNA_ID + "=" + j.getId_jogador(), null);

        return true;
    }

    public Cursor getCursor() {
        Cursor c = database.rawQuery("SELECT  * FROM " + Contract.Jogador.TABELA_NOME, null);
        return c;
    }

    public Cursor getRanking(){
        Cursor c = database.rawQuery("SELECT * FROM " + Contract.Jogador.TABELA_NOME + " order by " + Contract.Jogador.COLUNA_PONTUACAO_TOTAL + " desc", null);
        return c;
    }


    public List<Jogador> toList(Cursor c, Context ctx){
        List<Jogador> lista = new ArrayList<>();
        List<Jogo> jogos = new ArrayList<>();
        List<Avatar> avatares = new ArrayList<>();

        AvatarDAO avatarDAO = AvatarDAO.getInstance(ctx);
        avatarDAO.open();
        avatares = avatarDAO.toList(avatarDAO.getCursor());

        JogoDAO jogoDAO = JogoDAO.getInstance(ctx);
        jogoDAO.open();
        jogos = jogoDAO.toList(jogoDAO.getCursor());

        if(c.moveToFirst()) do {
            Avatar a = new Avatar();
            Jogo jogo = new Jogo();
            Jogador j = new Jogador();

            j.id_jogador = c.getInt(c.getColumnIndex("_id"));
            j.nome = c.getString(c.getColumnIndex("nome"));
            j.pontuacao_total = c.getInt(c.getColumnIndex("pontuacao_total"));

            a.id_avatar = c.getInt(c.getColumnIndex("avatar"));
            for (int x = 0; x < avatares.size(); x++) {
                if(avatares.get(x).getId_avatar() == a.id_avatar){
                    j.avatar = avatares.get(x);
                    Log.e("jogo", "JogadorDAO/toList()/j.avatar: " + j.avatar);
                    Log.e("jogo", "JogadorDAO/toList()/j.avatar.getNome(): " + j.avatar.getNome());
                }
            }

            jogo.id_jogo = c.getInt(c.getColumnIndex("jogo"));
            for (int x = 0; x < jogos.size(); x++) {
                if(jogos.get(x).getId_jogo() == jogo.id_jogo){
                    j.jogo = jogos.get(x);
                }
            }

            lista.add(j);
        } while (c.moveToNext());
        return lista;
    }

}
