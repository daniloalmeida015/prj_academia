package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1



/**
 * Parq generated by hbm2java
 */
public class Parq  implements java.io.Serializable {


     private int avaliacaoFisicaId;
     private Avaliacaofisica avaliacaofisica;
     private boolean resp1;
     private boolean resp2;
     private boolean resp3;
     private boolean resp4;
     private boolean resp5;
     private boolean resp6;
     private boolean resp7;

    public Parq() {
    }

    public Parq(Avaliacaofisica avaliacaofisica, boolean resp1, boolean resp2, boolean resp3, boolean resp4, boolean resp5, boolean resp6, boolean resp7) {
       this.avaliacaofisica = avaliacaofisica;
       this.resp1 = resp1;
       this.resp2 = resp2;
       this.resp3 = resp3;
       this.resp4 = resp4;
       this.resp5 = resp5;
       this.resp6 = resp6;
       this.resp7 = resp7;
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
    public boolean isResp1() {
        return this.resp1;
    }
    
    public void setResp1(boolean resp1) {
        this.resp1 = resp1;
    }
    public boolean isResp2() {
        return this.resp2;
    }
    
    public void setResp2(boolean resp2) {
        this.resp2 = resp2;
    }
    public boolean isResp3() {
        return this.resp3;
    }
    
    public void setResp3(boolean resp3) {
        this.resp3 = resp3;
    }
    public boolean isResp4() {
        return this.resp4;
    }
    
    public void setResp4(boolean resp4) {
        this.resp4 = resp4;
    }
    public boolean isResp5() {
        return this.resp5;
    }
    
    public void setResp5(boolean resp5) {
        this.resp5 = resp5;
    }
    public boolean isResp6() {
        return this.resp6;
    }
    
    public void setResp6(boolean resp6) {
        this.resp6 = resp6;
    }
    public boolean isResp7() {
        return this.resp7;
    }
    
    public void setResp7(boolean resp7) {
        this.resp7 = resp7;
    }




}


