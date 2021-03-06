package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Professor generated by hbm2java
 */
public class Professor  implements java.io.Serializable {


     private int pessoaFisicaId;
     private Pessoafisica pessoafisica;
     private String cref;
     private Date dataContratacao;
     private BigDecimal salario;
     private Set avaliacaofisicas = new HashSet(0);
     private Set fichatreinos = new HashSet(0);
     
     
     
     
     
      public String[] montarLinha(){
        
        //pega a data Nasc e retorna no formato padrão do PC
        String dataNasc = DateFormat.getDateInstance().format(pessoafisica.getDataNascimento());
        String dataContratacao= DateFormat.getDateInstance().format(getDataContratacao());
        
        String[] resp = 
           {pessoafisica.getPessoa().getId().toString(),
            pessoafisica.getPessoa().getNome(),
            pessoafisica.getSexo(),
            dataNasc,
            getCref(),
            dataContratacao,
            getSalario().toString()};
        
        return resp;
        
        
    }

    public Professor() {
    }

	
    public Professor(Pessoafisica pessoafisica, String cref, Date dataContratacao, BigDecimal salario) {
        this.pessoafisica = pessoafisica;
        this.cref = cref;
        this.dataContratacao = dataContratacao;
        this.salario = salario;
    }
    public Professor(Pessoafisica pessoafisica, String cref, Date dataContratacao, BigDecimal salario, Set avaliacaofisicas, Set fichatreinos) {
       this.pessoafisica = pessoafisica;
       this.cref = cref;
       this.dataContratacao = dataContratacao;
       this.salario = salario;
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
    public String getCref() {
        return this.cref;
    }
    
    public void setCref(String cref) {
        this.cref = cref;
    }
    public Date getDataContratacao() {
        return this.dataContratacao;
    }
    
    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
    public BigDecimal getSalario() {
        return this.salario;
    }
    
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
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


