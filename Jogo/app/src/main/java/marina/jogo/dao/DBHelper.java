package marina.jogo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper myHelper = null;

    private DBHelper(Context context) {
        super(context, Contract.BD_NOME, null, Contract.BD_VERSAO);
    }

    public static DBHelper getInstance(Context context){
        if(myHelper == null){
            myHelper = new DBHelper(context);
            return myHelper;
        }
        return  myHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String avatar = "create table " + Contract.Avatar.TABELA_NOME + " ("
            + Contract.Avatar.COLUNA_ID + " Integer primary key, "
            + Contract.Avatar.COLUNA_NOME + " text, "
            + Contract.Avatar.COLUNA_IMAGEM + " blob, "
            + Contract.Avatar.COLUNA_BLOQUEADO + " boolean); ";

        db.execSQL(avatar);

        String jogador = " create table " + Contract.Jogador.TABELA_NOME + " ("
            + Contract.Jogador.COLUNA_ID + " Integer primary key autoincrement, "
            + Contract.Jogador.COLUNA_NOME + " text, "
            + Contract.Jogador.COLUNA_PONTUACAO_TOTAL + " Integer, "
            + Contract.Jogador.COLUNA_JOGO + " Integer, "
            + Contract.Jogador.COLUNA_AVATAR + " Integer, "
            + " Foreign key (" + Contract.Jogador.COLUNA_JOGO + ") references " + Contract.Jogo.COLUNA_ID + ", "
            + " Foreign key (" + Contract.Jogador.COLUNA_AVATAR + ") references " + Contract.Avatar.COLUNA_ID + "); ";

        db.execSQL(jogador);

        String jogo = " create table " + Contract.Jogo.TABELA_NOME + " ("
                + Contract.Jogo.COLUNA_ID + " Integer primary key autoincrement, "
                + Contract.Jogo.COLUNA_PONTOS + " Integer, "
                + Contract.Jogo.COLUNA_NIVEL + " Integer, "
                + " Foreign key (" + Contract.Jogo.COLUNA_NIVEL + ") references " + Contract.Nivel.COLUNA_ID + "); ";

        db.execSQL(jogo);

        String nivel = " create table " + Contract.Nivel.TABELA_NOME + " ("
            + Contract.Nivel.COLUNA_ID + " Integer primary key);";

        db.execSQL(nivel);

        Log.d("jogo", "Executou o script de criacao do banco de dados!!!");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Avatar.TABELA_NOME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Jogo.TABELA_NOME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Jogador.TABELA_NOME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Nivel.TABELA_NOME);
        onCreate(db);
    }
}
