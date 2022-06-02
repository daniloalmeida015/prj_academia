package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1



/**
 * Login generated by hbm2java
 */
public class Login  implements java.io.Serializable {


     private int pessoaFisicaId;
     private Pessoafisica pessoafisica;
     private String usuario;
     private String senha;
     private String tipoPermissao;

    public Login() {
    }

    public Login(Pessoafisica pessoafisica, String usuario, String senha, String tipoPermissao) {
       this.pessoafisica = pessoafisica;
       this.usuario = usuario;
       this.senha = senha;
       this.tipoPermissao = tipoPermissao;
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
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return this.senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getTipoPermissao() {
        return this.tipoPermissao;
    }
    
    public void setTipoPermissao(String tipoPermissao) {
        this.tipoPermissao = tipoPermissao;
    }




}


