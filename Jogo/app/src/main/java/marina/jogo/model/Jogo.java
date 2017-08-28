package marina.jogo.model;


public class Jogo {

    public Integer id_jogo;
    public Nivel nivel;
    public Integer pontos;


    private static Jogo j = null;

    public static Jogo getInstance(){
        if(j == null){
            j = new Jogo();
            return j;
        }
        return  j;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Integer getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(Integer id_jogo) {
        this.id_jogo = id_jogo;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }


}
















