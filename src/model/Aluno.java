package model;

import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

public class Aluno  implements java.io.Serializable {

     private int pessoaFisicaId;
     private Pessoafisica pessoafisica;
     private String status;
     private Set frequencias = new HashSet(0);
     private Set matriculas = new HashSet(0);
     private Set avaliacaofisicas = new HashSet(0);
     private Set fichatreinos = new HashSet(0);
     
     
     //PARA AJUDAR A MONTAR AS LINHAS DA TABELA NO INTERNALFRAE
     public String[] montarLinha(){
        
        //PEGA A DATA DE NASCIMENTO E RETORNA NO PADRÃO DO PC
        String dataNasc = DateFormat.getDateInstance().format(pessoafisica.getDataNascimento());
        
        String[] resp = {pessoafisica.getPessoa().getId().toString(),
            pessoafisica.getPessoa().getNome(),
            pessoafisica.getSexo(),
            dataNasc,
            status};
        
        return resp;
    }
     
     
     public String[] montarLinhaPesquisa(){
        
        String[] resp = {pessoafisica.getPessoa().getId().toString(),
                         pessoafisica.getPessoa().getNome()};
        
        return resp;
    }
     
      
     
    public Aluno() {
    
    }
    

    //construtor usado
    public Aluno(Pessoafisica pessoafisica, String status) {
        this.pessoafisica = pessoafisica;
        this.status = status;
    }
    
    
    
    public Aluno(Pessoafisica pessoafisica, String status, Set frequencias, Set matriculas, Set avaliacaofisicas, Set fichatreinos) {
       this.pessoafisica = pessoafisica;
       this.status = status;
       this.frequencias = frequencias;
       this.matriculas = matriculas;
       this.avaliacaofisicas = avaliacaofisicas;
       this.fichatreinos = fichatreinos;
    }
   
    public int getPessoaFisicaId() {
        return this.pessoaFisicaId;
    }
    
    public void setPessoaFisicaId(int pessoaFisicaId) {
        this.pessoaFisicaId = pessoaFisicaId;
    }
    public Pessoafisica getPessoafisica() {
        return this.pessoafisica;
    }
    
    public void setPessoafisica(Pessoafisica pessoafisica) {
        this.pessoafisica = pessoafisica;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Set getFrequencias() {
        return this.frequencias;
    }
    
    public void setFrequencias(Set frequencias) {
        this.frequencias = frequencias;
    }
    public Set getMatriculas() {
        return this.matriculas;
    }
    
    public void setMatriculas(Set matriculas) {
        this.matriculas = matriculas;
    }
    public Set getAvaliacaofisicas() {
        return this.avaliacaofisicas;
    }
    
    public void setAvaliacaofisicas(Set avaliacaofisicas) {
        this.avaliacaofisicas = avaliacaofisicas;
    }
    public Set getFichatreinos() {
        return this.fichatreinos;
    }
    
    public void setFichatreinos(Set fichatreinos) {
        this.fichatreinos = fichatreinos;
    }
}


