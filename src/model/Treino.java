package model;

public class Treino  implements java.io.Serializable {


     private Integer id;
     private Exercicio exercicio;
     private Fichatreino fichatreino;
     private String nome;
     private Byte series;
     private Byte repeticoes;
     private Float peso;

    public Treino() {
    }

	
    public Treino(Exercicio exercicio, Fichatreino fichatreino) {
        this.exercicio = exercicio;
        this.fichatreino = fichatreino;
    }
    
    
    public Treino(Exercicio exercicio, Fichatreino fichatreino, String nome, Byte series, Byte repeticoes, Float peso) {
       this.exercicio = exercicio;
       this.fichatreino = fichatreino;
       this.nome = nome;
       this.series = series;
       this.repeticoes = repeticoes;
       this.peso = peso;
    }
   
    
    //PARA AJUDAR A MONTAR AS LINHAS DA TABELA NO INTERNALFRAE
     public String[] montarLinha(){
        
        String[] resp = {String.valueOf(getId()),
                         getFichatreino().getId().toString(),
                         getExercicio().getId().toString(),
                         getExercicio().getNome(),
                         getSeries().toString(),
                         getRepeticoes().toString(),
                         getPeso().toString()};
        
        return resp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
     
     
    
    
    public Exercicio getExercicio() {
        return this.exercicio;
    }
    
    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }
    public Fichatreino getFichatreino() {
        return this.fichatreino;
    }
    
    public void setFichatreino(Fichatreino fichatreino) {
        this.fichatreino = fichatreino;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Byte getSeries() {
        return this.series;
    }
    
    public void setSeries(Byte series) {
        this.series = series;
    }
    public Byte getRepeticoes() {
        return this.repeticoes;
    }
    
    public void setRepeticoes(Byte repeticoes) {
        this.repeticoes = repeticoes;
    }
    public Float getPeso() {
        return this.peso;
    }
    
    public void setPeso(Float peso) {
        this.peso = peso;
    }

}


