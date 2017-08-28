package marina.jogo.dao;

import android.provider.BaseColumns;


public final class Contract {

    public static final String BD_NOME = "banco.db";
    public static final int BD_VERSAO = 1;

    public Contract() {
    }

    public static abstract class Avatar implements BaseColumns{
        public static final String TABELA_NOME = "avatar";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_BLOQUEADO = "bloqueado";
        public static final String COLUNA_IMAGEM = "imagem";
    }

    public static abstract class Jogador implements BaseColumns {
        public static final String TABELA_NOME = "jogador";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_PONTUACAO_TOTAL = "pontuacao_total";
        public static final String COLUNA_AVATAR = "avatar";
        public static final String COLUNA_JOGO = "jogo";
    }

    public static abstract class Jogo implements BaseColumns{
        public static final String TABELA_NOME = "jogo";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_PONTOS = "pontos";
        public static final String COLUNA_NIVEL = "nivel";
    }

    public static abstract class Nivel implements BaseColumns{
        public static final String TABELA_NOME = "nivel";
        public static final String COLUNA_ID = "_id";
    }




}
