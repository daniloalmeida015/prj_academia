/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Danilo
 */
public class ConectaBanco {
    
    public Statement stm; //responsavel pela pesquisa no DB
    public ResultSet resultSet; //armazena o resultado da pesquisa
    private final String driver = "com.mysql.jdbc.Driver";
    private final String caminho = "jdbc:mysql://localhost:3306/prj_academia_final";
    private final String usuario =  "root";
    private final String senha = "";
    public Connection conn;
    
    public void conecta(){        
        try {
            
            System.setProperty("jdbc.drivers", driver);
            conn = DriverManager.getConnection(caminho, usuario, senha);
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void executaSQL(String sql){
        
        try{
            stm = conn.createStatement(resultSet.TYPE_SCROLL_INSENSITIVE, resultSet.CONCUR_READ_ONLY);
            resultSet = stm.executeQuery(sql);                    
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao executar o SQL:\n ERRO: "+ex.getMessage());
        }
    }
    
    public void desconecta(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
