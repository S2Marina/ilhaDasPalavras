package marina.jogo.model;


import java.io.Serializable;

import marina.jogo.control.CadastrarJogador;

public class Avatar implements Serializable {
    public String nome;
    public boolean bloqueado;
    public Integer id_avatar;
    public byte[] imagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Integer getId_avatar() {
        return id_avatar;
    }

    public void setId_avatar(Integer id_avatar) {
        this.id_avatar = id_avatar;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public void escolherAvatar(Avatar a){
        CadastrarJogador.setAvatar(a);
    }

}
