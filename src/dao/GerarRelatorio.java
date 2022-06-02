package dao;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import static view.FrmPrincipal.dskPrincipal;

/** * * @author Danilo */
public class GerarRelatorio {
    
    private ConectaBanco conexao;
    private JasperViewer jpViewer;
    
   public JasperViewer gerarRelatorioAluno(String sql, String caminho){
        
        try{
            conexao= new ConectaBanco();
            
            conexao.conecta();
            conexao.executaSQL(sql);
            
            InputStream fonte = GerarRelatorio.class.getResourceAsStream(caminho);
            
            JRResultSetDataSource relatorioResult = new JRResultSetDataSource(conexao.resultSet);
            JasperPrint jpPrint = JasperFillManager.fillReport(fonte, new HashMap<>(), relatorioResult);
                
                jpViewer = new JasperViewer(jpPrint, false); //cria instancia para impressao
            
            
                jpViewer.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSoutFit50x50.png")).getImage());
                jpViewer.setTitle("Imprimir Relatório");
                //jpViewer.setAlwaysOnTop(true);

                
                
                jpViewer.setSize(jpViewer.getWidth(), dskPrincipal.getHeight());
                jpViewer.setLocationRelativeTo(dskPrincipal);
            
                conexao.desconecta();
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao chamar o relatório. \nERRO: "+ex);
   
        }
        
        return jpViewer;
    }
}