package marina.jogo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import marina.jogo.model.Jogador;
import marina.jogo.model.Jogo;
import marina.jogo.model.Nivel;


public class NivelDAO {
    private static NivelDAO n;
    private final Context context;
    private DBHelper myHelper;
    private SQLiteDatabase database;

    private NivelDAO(Context context){
        this.context = context;
    }

    public static NivelDAO getInstance(Context ctx){
        if(n == null){
            n = new NivelDAO(ctx);
            return n;
        }
        return n;
    }

    public void open() {
        myHelper = DBHelper.getInstance(context);
        database = myHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }


    public Boolean salvar(Nivel n) {
        if (n.getId_nivel() == null) {
            return inserir(n);
        } else {
            return alterar(n);
        }
    }

    // Método para inserir um contato:
    public Boolean inserir(Nivel j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Nivel.COLUNA_ID, j.getId_nivel());


        database.insert(Contract.Nivel.TABELA_NOME, null, valores);

        return true;
    }

    public Integer excluirTudo(){
        return database.delete(Contract.Nivel.TABELA_NOME, null, null);
    }

    public Boolean alterar(Nivel j) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Nivel.COLUNA_ID, j.getId_nivel());

        database.update(Contract.Nivel.TABELA_NOME, valores, Contract.Nivel.COLUNA_ID + " = " + j.getId_nivel(), null);
        return true;
    }

    public Cursor getCursor() {
        return database.rawQuery("SELECT  * FROM " + Contract.Nivel.TABELA_NOME, null);
    }


    public List<Nivel> toList(Cursor c){
        Log.e("jogo", "cursor: " + c.getCount());
        List<Nivel> lista = new ArrayList<>();
        if(c.moveToFirst()) do {
            Nivel n = new Nivel();

            n.id_nivel = c.getInt(c.getColumnIndex("_id"));

            lista.add(n);
        } while (c.moveToNext());

        Log.e("jogo", "lista: " + lista.size());
        return lista;
    }

}
