package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Perimetria generated by hbm2java
 */
public class Perimetria  implements java.io.Serializable {


     private int avaliacaoFisicaId;
     private Avaliacaofisica avaliacaofisica;
     private BigDecimal pescoco;
     private BigDecimal bracoDireito;
     private BigDecimal bracoEsquerdo;
     private BigDecimal torax;
     private BigDecimal abdomem;
     private BigDecimal quadril;
     private BigDecimal coxaSuperiorDireita;
     private BigDecimal coxaSuperiorEsquerda;
     private BigDecimal coxaInferiorDireita;
     private BigDecimal coxaInferiorEsquerda;
     private BigDecimal pernaDireita;
     private BigDecimal pernaEsquerda;
     private BigDecimal antebracoDireito;
     private BigDecimal antebracoEsquerdo;

    public Perimetria() {
    }

	
    public Perimetria(Avaliacaofisica avaliacaofisica) {
        this.avaliacaofisica = avaliacaofisica;
    }
    public Perimetria(Avaliacaofisica avaliacaofisica, BigDecimal pescoco, BigDecimal bracoDireito, BigDecimal bracoEsquerdo, BigDecimal torax, BigDecimal abdomem, BigDecimal quadril, BigDecimal coxaSuperiorDireita, BigDecimal coxaSuperiorEsquerda, BigDecimal coxaInferiorDireita, BigDecimal coxaInferiorEsquerda, BigDecimal pernaDireita, BigDecimal pernaEsquerda, BigDecimal antebracoDireito, BigDecimal antebracoEsquerdo) {
       this.avaliacaofisica = avaliacaofisica;
       this.pescoco = pescoco;
       this.bracoDireito = bracoDireito;
       this.bracoEsquerdo = bracoEsquerdo;
       this.torax = torax;
       this.abdomem = abdomem;
       this.quadril = quadril;
       this.coxaSuperiorDireita = coxaSuperiorDireita;
       this.coxaSuperiorEsquerda = coxaSuperiorEsquerda;
       this.coxaInferiorDireita = coxaInferiorDireita;
       this.coxaInferiorEsquerda = coxaInferiorEsquerda;
       this.pernaDireita = pernaDireita;
       this.pernaEsquerda = pernaEsquerda;
       this.antebracoDireito = antebracoDireito;
       this.antebracoEsquerdo = antebracoEsquerdo;
    }
   
    public int getAvaliacaoFisicaId() {
        return this.avaliacaoFisicaId;
    }
    
    public void setAvaliacaoFisicaId(int avaliacaoFisicaId) {
        this.avaliacaoFisicaId = avaliacaoFisicaId;
    }
    public Avaliacaofisica getAvaliacaofisica() {
        return this.avaliacaofisica;
    }
    
    public void setAvaliacaofisica(Avaliacaofisica avaliacaofisica) {
        this.avaliacaofisica = avaliacaofisica;
    }
    public BigDecimal getPescoco() {
        return this.pescoco;
    }
    
    public void setPescoco(BigDecimal pescoco) {
        this.pescoco = pescoco;
    }
    public BigDecimal getBracoDireito() {
        return this.bracoDireito;
    }
    
    public void setBracoDireito(BigDecimal bracoDireito) {
        this.bracoDireito = bracoDireito;
    }
    public BigDecimal getBracoEsquerdo() {
        return this.bracoEsquerdo;
    }
    
    public void setBracoEsquerdo(BigDecimal bracoEsquerdo) {
        this.bracoEsquerdo = bracoEsquerdo;
    }
    public BigDecimal getTorax() {
        return this.torax;
    }
    
    public void setTorax(BigDecimal torax) {
        this.torax = torax;
    }
    public BigDecimal getAbdomem() {
        return this.abdomem;
    }
    
    public void setAbdomem(BigDecimal abdomem) {
        this.abdomem = abdomem;
    }
    public BigDecimal getQuadril() {
        return this.quadril;
    }
    
    public void setQuadril(BigDecimal quadril) {
        this.quadril = quadril;
    }
    public BigDecimal getCoxaSuperiorDireita() {
        return this.coxaSuperiorDireita;
    }
    
    public void setCoxaSuperiorDireita(BigDecimal coxaSuperiorDireita) {
        this.coxaSuperiorDireita = coxaSuperiorDireita;
    }
    public BigDecimal getCoxaSuperiorEsquerda() {
        return this.coxaSuperiorEsquerda;
    }
    
    public void setCoxaSuperiorEsquerda(BigDecimal coxaSuperiorEsquerda) {
        this.coxaSuperiorEsquerda = coxaSuperiorEsquerda;
    }
    public BigDecimal getCoxaInferiorDireita() {
        return this.coxaInferiorDireita;
    }
    
    public void setCoxaInferiorDireita(BigDecimal coxaInferiorDireita) {
        this.coxaInferiorDireita = coxaInferiorDireita;
    }
    public BigDecimal getCoxaInferiorEsquerda() {
        return this.coxaInferiorEsquerda;
    }
    
    public void setCoxaInferiorEsquerda(BigDecimal coxaInferiorEsquerda) {
        this.coxaInferiorEsquerda = coxaInferiorEsquerda;
    }
    public BigDecimal getPernaDireita() {
        return this.pernaDireita;
    }
    
    public void setPernaDireita(BigDecimal pernaDireita) {
        this.pernaDireita = pernaDireita;
    }
    public BigDecimal getPernaEsquerda() {
        return this.pernaEsquerda;
    }
    
    public void setPernaEsquerda(BigDecimal pernaEsquerda) {
        this.pernaEsquerda = pernaEsquerda;
    }
    public BigDecimal getAntebracoDireito() {
        return this.antebracoDireito;
    }
    
    public void setAntebracoDireito(BigDecimal antebracoDireito) {
        this.antebracoDireito = antebracoDireito;
    }
    public BigDecimal getAntebracoEsquerdo() {
        return this.antebracoEsquerdo;
    }
    
    public void setAntebracoEsquerdo(BigDecimal antebracoEsquerdo) {
        this.antebracoEsquerdo = antebracoEsquerdo;
    }




}


