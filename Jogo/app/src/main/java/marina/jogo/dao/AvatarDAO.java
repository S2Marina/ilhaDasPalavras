package marina.jogo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import marina.jogo.model.Avatar;

public class AvatarDAO {
    private static AvatarDAO a;
    private final Context context;
    private DBHelper myHelper;
    private SQLiteDatabase database;

    private AvatarDAO(Context context){
        this.context = context;
    }

    public static AvatarDAO getInstance(Context ctx){
        if(a == null){
            a = new AvatarDAO(ctx);
            return a;
        }
        return a;
    }

    public void open() {
        myHelper = DBHelper.getInstance(context);
        database = myHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }


    public Boolean salvar(Avatar a) {
        if (a.getId_avatar() == null) {
            return inserir(a);
        } else {
            return alterar(a);
        }
    }

    // Método para inserir um contato:
    public Boolean inserir(Avatar a) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Avatar.COLUNA_ID, a.getId_avatar());
        valores.put(Contract.Avatar.COLUNA_NOME, a.getNome());
        valores.put(Contract.Avatar.COLUNA_BLOQUEADO, a.isBloqueado());
        valores.put(Contract.Avatar.COLUNA_IMAGEM, a.getImagem());

        database.insert(Contract.Avatar.TABELA_NOME, null, valores);

        return true;
    }



    public Boolean alterar(Avatar a) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Avatar.COLUNA_ID, a.getId_avatar());
        valores.put(Contract.Avatar.COLUNA_NOME, a.getNome());
        valores.put(Contract.Avatar.COLUNA_BLOQUEADO, a.isBloqueado());
        valores.put(Contract.Avatar.COLUNA_IMAGEM, a.getImagem());

        database.update(Contract.Avatar.TABELA_NOME, valores, Contract.Avatar.COLUNA_ID + " = " + a.getId_avatar(), null);
        return true;
    }


    public Cursor getCursor() {
        return database.rawQuery("SELECT  * FROM " + Contract.Avatar.TABELA_NOME, null);

    }


    public List<Avatar> toList(Cursor c){
        List<Avatar> lista = new ArrayList<>();
        if(c.moveToFirst()) do {
            Avatar a = new Avatar();
            a.id_avatar = c.getInt(c.getColumnIndex("_id"));
            a.nome = c.getString(c.getColumnIndex("nome"));
            if(c.getInt(c.getColumnIndex("bloqueado")) > 0){
                a.bloqueado = true;
            }
            else{
                a.bloqueado = false;
            }
            a.imagem = c.getBlob(c.getColumnIndex("imagem"));
            lista.add(a);
        } while (c.moveToNext());
        return lista;
    }

    public void apaga(){
        SQLiteDatabase db = myHelper.getWritableDatabase();
        db.execSQL("delete from avatar");
    }

    /*
    public Cursor getAvatarById(int id){
        return database.rawQuery("SELECT * FROM " + Contract.Avatar.TABELA_NOME + " WHERE ID = " + id + ";", null);
    }
    */

  }
