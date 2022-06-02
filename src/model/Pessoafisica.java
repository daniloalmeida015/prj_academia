package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.FrmPrincipal;

/**
 * Pessoafisica generated by hbm2java
 */
public class Pessoafisica  implements java.io.Serializable {


     private int pessoaId;
     private Pessoa pessoa;
     private String sexo;
     private Date dataNascimento;
     private String rg;
     private String cpf;
     private String foto;
     private Set biometrias = new HashSet(0);
     private Funcionario funcionario;
     private Login login;
     private Professor professor;
     private Aluno aluno;
     
     
     //CONSTRUTOR USADO
     public Pessoafisica(String sexo, Date dataNascimento, String rg, String cpf, String foto) {
       
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.rg = rg;
        this.cpf = cpf;
        this.foto= foto;
    }
     
     
     
     //esta função/metodo servira para formatar a data informada pelo usuario para somente depois salvar no banco como DATE
     public static Date formatarData(String data){
      
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String nascimento = data;
        Date nascimentoDate=null;
        
        try {
            nascimentoDate = sdf.parse(nascimento);
        } catch (ParseException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return nascimentoDate;
    }
     
      
     
     
     
     
     
     
     
     
     

    public Pessoafisica() {
    }

	
    
    public int getPessoaId() {
        return this.pessoaId;
    }
    
    public void setPessoaId(int pessoaId) {
        this.pessoaId = pessoaId;
    }
    public Pessoa getPessoa() {
        return this.pessoa;
    }
    
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public String getSexo() {
        return this.sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Date getDataNascimento() {
        return this.dataNascimento;
    }
    
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getRg() {
        return this.rg;
    }
    
    public void setRg(String rg) {
        this.rg = rg;
    }
    public String getCpf() {
        return this.cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getFoto() {
        return this.foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public Set getBiometrias() {
        return this.biometrias;
    }
    
    public void setBiometrias(Set biometrias) {
        this.biometrias = biometrias;
    }
    public Funcionario getFuncionario() {
        return this.funcionario;
    }
    
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public Login getLogin() {
        return this.login;
    }
    
    public void setLogin(Login login) {
        this.login = login;
    }
    public Professor getProfessor() {
        return this.professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public Aluno getAluno() {
        return this.aluno;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }




}


