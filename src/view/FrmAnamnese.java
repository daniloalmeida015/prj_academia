
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Anamnese;
import model.Avaliacaofisica;
import org.hibernate.Session;

/** ** @author Danilo*/
public class FrmAnamnese extends javax.swing.JFrame {
    
    public FrmAnamnese() {
        initComponents();
    }
    
    private Session sessao;
    private Anamnese anamnese;
    private Avaliacaofisica avaliacaoFisica;
    
    private List<Anamnese> listaAnamnese;
    
    public FrmAnamnese(Session sessao, Avaliacaofisica avaliacaoFisica){
        this();
        this.sessao= sessao;
        this.avaliacaoFisica=avaliacaoFisica;
        listaAnamnese= new ArrayList<>();
        popularCampos();
    }
    
    private void popularCampos(){
        listaAnamnese =  sessao.createQuery("from Anamnese where id = '"+avaliacaoFisica.getId()+"'").list();
        
        if(listaAnamnese.size()>0){
            anamnese= listaAnamnese.get(0);
            txtResp1.setText(anamnese.getResp1());
            txtResp2.setText(anamnese.getResp2());
            txtResp3.setText(anamnese.getResp3());
            txtResp4.setText(anamnese.getResp4());
            txtResp5.setText(anamnese.getResp5());
            txtResp6.setText(anamnese.getResp6());
        }
    }
    
    public void salvar(){
        
        anamnese = new Anamnese(avaliacaoFisica, txtResp1.getText(), txtResp2.getText(), txtResp3.getText(), txtResp4.getText(),
                                                txtResp5.getText(), txtResp6.getText(), "","","","");
        
        try {
                sessao.beginTransaction();
                    sessao.save(anamnese);
                sessao.getTransaction().commit();
                
                JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar!"+"\n\nERRO: "+e.getMessage()+"\n\nCAUSA: "+e.getCause());
                //sessao.getTransaction().rollback();
        }
    }
    
    public void atualizar(){
        anamnese = listaAnamnese.get(0);
        
        anamnese.setResp1(txtResp1.getText());
        anamnese.setResp2(txtResp2.getText());
        anamnese.setResp3(txtResp3.getText());
        anamnese.setResp4(txtResp4.getText());
        anamnese.setResp5(txtResp5.getText());
        anamnese.setResp6(txtResp6.getText());
        
        try {
            
                sessao.beginTransaction();
                    sessao.update(anamnese);
                sessao.getTransaction().commit();
                
                JOptionPane.showMessageDialog(this,"Atualizado com sucesso!");
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar!"+"\n\nERRO: "+e.getMessage()+"\n\nCAUSA: "+e.getCause());
                //sessao.beginTransaction().rollback();
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtResp1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtResp2 = new javax.swing.JTextField();
        txtResp3 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtResp4 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtResp5 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResp6 = new javax.swing.JTextArea();
        btnSalvarAnamnese = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Anamnese");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setText("1 - Quais os objetivos com relação a atividade física?");

        jLabel49.setText("2 - Pratica atividade física atualmente?");

        txtResp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResp2ActionPerformed(evt);
            }
        });

        jLabel50.setText("3 - Utiliza algum tipo de medicamento?");

        jLabel51.setText("4 - Já passou por alguma cirurgia?");

        jLabel53.setText("5 - Possui histórico de doenças na família?");

        jLabel54.setText("Observações:");

        txtResp6.setColumns(20);
        txtResp6.setRows(5);
        jScrollPane1.setViewportView(txtResp6);

        btnSalvarAnamnese.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvarAnamnese.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvar20x20.png"))); // NOI18N
        btnSalvarAnamnese.setText("Salvar");
        btnSalvarAnamnese.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarAnamneseActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAnamnese64x64.png"))); // NOI18N
        jLabel1.setText("Anamnese");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnCancelar.setBackground(new java.awt.Color(31, 158, 150));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCancelar20x20.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvarAnamnese, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(249, 249, 249))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(325, 325, 325))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(326, 326, 326))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(353, 353, 353))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(309, 309, 309))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                        .addGap(470, 470, 470))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtResp2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtResp1))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtResp4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtResp3))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtResp5))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addGap(4, 4, 4)
                .addComponent(txtResp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addGap(4, 4, 4)
                .addComponent(txtResp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addGap(4, 4, 4)
                .addComponent(txtResp3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(4, 4, 4)
                .addComponent(txtResp4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addGap(4, 4, 4)
                .addComponent(txtResp5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvarAnamnese, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtResp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResp2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtResp2ActionPerformed

    private void btnSalvarAnamneseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarAnamneseActionPerformed

        if(listaAnamnese.size()>0){
            JOptionPane.showMessageDialog(this, "atualizar");
            atualizar();
        } else{
            JOptionPane.showMessageDialog(this, "salvar");
            salvar();
        }
    }//GEN-LAST:event_btnSalvarAnamneseActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        this.requestFocus();
    }//GEN-LAST:event_formWindowIconified

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAnamnese().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvarAnamnese;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtResp1;
    private javax.swing.JTextField txtResp2;
    private javax.swing.JTextField txtResp3;
    private javax.swing.JTextField txtResp4;
    private javax.swing.JTextField txtResp5;
    private javax.swing.JTextArea txtResp6;
    // End of variables declaration//GEN-END:variables
}
