package model;
// Generated 08/11/2018 14:48:11 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Produto generated by hbm2java
 */
public class Produto  implements java.io.Serializable {


     private Integer id;
     private Categoria categoria;
     private String nome;
     private String descricao;
     private Date dataEntrada;
     private BigDecimal precoCompra;
     private BigDecimal precoVenda;
     private int quantidade;
     private int qtdMinima;
     private Set vendas = new HashSet(0);
     private Set fornecedors = new HashSet(0);

    public Produto() {
    }

	
    public Produto(Categoria categoria, String nome, Date dataEntrada, BigDecimal precoCompra, BigDecimal precoVenda, int quantidade, int qtdMinima) {
        this.categoria = categoria;
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.qtdMinima = qtdMinima;
    }
    public Produto(Categoria categoria, String nome, String descricao, Date dataEntrada, BigDecimal precoCompra, BigDecimal precoVenda, int quantidade, int qtdMinima, Set vendas, Set fornecedors) {
       this.categoria = categoria;
       this.nome = nome;
       this.descricao = descricao;
       this.dataEntrada = dataEntrada;
       this.precoCompra = precoCompra;
       this.precoVenda = precoVenda;
       this.quantidade = quantidade;
       this.qtdMinima = qtdMinima;
       this.vendas = vendas;
       this.fornecedors = fornecedors;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Date getDataEntrada() {
        return this.dataEntrada;
    }
    
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    public BigDecimal getPrecoCompra() {
        return this.precoCompra;
    }
    
    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }
    public BigDecimal getPrecoVenda() {
        return this.precoVenda;
    }
    
    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }
    public int getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public int getQtdMinima() {
        return this.qtdMinima;
    }
    
    public void setQtdMinima(int qtdMinima) {
        this.qtdMinima = qtdMinima;
    }
    public Set getVendas() {
        return this.vendas;
    }
    
    public void setVendas(Set vendas) {
        this.vendas = vendas;
    }
    public Set getFornecedors() {
        return this.fornecedors;
    }
    
    public void setFornecedors(Set fornecedors) {
        this.fornecedors = fornecedors;
    }




}


