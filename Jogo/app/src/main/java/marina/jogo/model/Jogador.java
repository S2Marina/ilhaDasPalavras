package marina.jogo.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import marina.jogo.control.MainActivity;
import marina.jogo.dao.Contract;
import marina.jogo.dao.JogadorDAO;
import marina.jogo.dao.JogoDAO;
import marina.jogo.dao.NivelDAO;

public class Jogador implements Serializable{

    public String nome;
    public Integer pontuacao_total;
    public Jogo jogo;
    public Avatar avatar;
    public Integer id_jogador;

    private static Jogador j = null;

    public static Jogador getInstance(){
        if(j == null){
            j = new Jogador();
            return j;
        }
        return  j;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(Integer id_jogador) {
        this.id_jogador = id_jogador;
    }

    public Integer getPontuacao_total() {
        return pontuacao_total;
    }

    public void setPontuacao_total(Integer pontuacao_total) {
        this.pontuacao_total = pontuacao_total;
    }

    public boolean cadastrarJogador(String nome, Avatar avatar, Context ctx){
            Nivel nivel;
            NivelDAO nivelDAO = NivelDAO.getInstance(ctx);
            nivelDAO.open();
            List<Nivel> lista = nivelDAO.toList(nivelDAO.getCursor());
            nivel = lista.get(0);
            Log.e("jogo" , "primeiro nivel cadastrado no banco: " + nivel.getId_nivel());
            nivelDAO.close();

            Jogo jogo = new Jogo();
            jogo.setPontos(0);
            jogo.setNivel(nivel);

            JogoDAO jogoDAO = JogoDAO.getInstance(ctx);
            jogoDAO.open();
            jogoDAO.inserir(jogo);
            jogoDAO.close();

            jogoDAO.open();
            jogo = jogoDAO.getUltimoJogo();
            jogoDAO.close();

            Jogador j = new Jogador();
            j.setNome(nome);
            j.setAvatar(avatar);
            j.setPontuacao_total(0);
            j.setJogo(jogo);

            JogadorDAO jd = JogadorDAO.getInstance(ctx);
            jd.open();
            jd.salvar(j);
            jd.close();

            Log.e("jogo", "jogo: " + jogo.toString());
            Log.e("jogo", "id do jogo: " + jogo.getId_jogo());
            Log.e("jogo", "id do jogo do jogador: " + j.getJogo().getId_jogo());
            Log.e("jogo", "nivel do jogo:" + jogo.getNivel());
            Log.e("jogo", "nivel do jogo do jogador: " + j.getJogo().getNivel());


            return true;
         }

    public String visualizarRanking(){
        String r = null;
        return r;
    }
}
