package marina.jogo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import marina.jogo.model.Avatar;
import marina.jogo.model.Jogo;
import marina.jogo.model.Nivel;

public class JogoDAO {
    private static JogoDAO j;
    private final Context context;
    private DBHelper myHelper;
    private SQLiteDatabase database;

    private JogoDAO(Context context){
        this.context = context;
    }

    public static JogoDAO getInstance(Context ctx){
        if(j == null){
            j = new JogoDAO(ctx);
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


    public Boolean salvar(Jogo j) {
        if (j.getId_jogo() == null) {
            return inserir(j);
        } else {
            return alterar(j);
        }
    }


    public Boolean inserir(Jogo j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Jogo.COLUNA_PONTOS, j.getPontos());
        valores.put(Contract.Jogo.COLUNA_NIVEL, j.getNivel().getId_nivel());

        database.insert(Contract.Jogo.TABELA_NOME, null, valores);

        Log.e("jogo", (Contract.Jogo.TABELA_NOME + " " + valores.toString()));

        return true;
    }


    public Boolean alterar(Jogo j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Jogo.COLUNA_PONTOS, j.getPontos());
        valores.put(Contract.Jogo.COLUNA_NIVEL, j.getNivel().getId_nivel());

        database.update(Contract.Jogo.TABELA_NOME, valores, Contract.Jogo.COLUNA_ID + " = " + j.getId_jogo(), null);
        return true;
    }

    public Cursor getCursor() {
        return database.rawQuery("SELECT  * FROM " + Contract.Jogo.TABELA_NOME, null);
    }


    //repensar isso tbm
    public List<Jogo> toList(Cursor c){
        List<Jogo> lista = new ArrayList<>();

        if(c.moveToFirst()) do {
            Nivel n = new Nivel();
            Jogo j = new Jogo();
            j.id_jogo = c.getInt(c.getColumnIndex("_id"));
            j.pontos = c.getInt(c.getColumnIndex("pontos"));
            n.id_nivel = c.getInt(c.getColumnIndex("nivel"));
            j.nivel = n;

            lista.add(j);

        } while (c.moveToNext());
        return lista;
    }

    public Jogo getUltimoJogo(){
        int tamanho;
        List<Jogo> lista;
        Jogo jogo;

        lista = toList(getCursor());
        tamanho = lista.size();
        jogo = lista.get(tamanho - 1);

        return jogo;
    }

}
