package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Avaliacaofisica;
import model.Parq;
import org.hibernate.Session;

/*** * @author Danilo*/
public class JDialogParQ extends javax.swing.JDialog {

    private Session sessao;
    private Avaliacaofisica avaliacaoFisica;
    private Parq parQ;
    private List<Parq> listaParQ;
    
    public JDialogParQ(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public JDialogParQ(java.awt.Frame parent, boolean modal, Session sessao, Avaliacaofisica avaliacaoFisica) {
        super(parent, modal);
        initComponents();
        
        this.sessao=sessao;
        this.avaliacaoFisica=avaliacaoFisica;
        listaParQ= new ArrayList<>();
        popularCampos();
    }
    
    
    private void popularCampos(){
        listaParQ = sessao.createQuery("from Parq where id = '"+avaliacaoFisica.getId()+"'").list();

        if(listaParQ.size()>0){
            parQ = listaParQ.get(0);
            
            buttonGroup1.setSelected(parQ.isResp1()? radioBtn1Sim.getModel(): radioBtn1Nao.getModel(), true);
            buttonGroup2.setSelected(parQ.isResp2()? radioBtn2Sim.getModel(): radionBtn2Nao.getModel(), true);
            buttonGroup3.setSelected(parQ.isResp3()? radioBtn3Sim.getModel(): radionBtn3Nao.getModel(), true);
            buttonGroup4.setSelected(parQ.isResp4()? radioBtn4Sim.getModel(): radioBtn4Nao.getModel(), true);
            buttonGroup5.setSelected(parQ.isResp5()? radioBtn5Sim.getModel(): radioBtn5Nao.getModel(), true);
            buttonGroup6.setSelected(parQ.isResp6()? radioBtn6Sim.getModel(): radioBtn6Nao.getModel(), true);
            buttonGroup7.setSelected(parQ.isResp7()? radioBtn7Sim.getModel(): radioBtn7Nao.getModel(), true);
        }
    }
    
    private void salvar(){
    
        parQ.setAvaliacaofisica(avaliacaoFisica);
        parQ.setResp1(radioBtn1Sim.isSelected());
        parQ.setResp2(radioBtn2Sim.isSelected());
        parQ.setResp3(radioBtn3Sim.isSelected());
        parQ.setResp4(radioBtn4Sim.isSelected());
        parQ.setResp5(radioBtn5Sim.isSelected());
        parQ.setResp6(radioBtn6Sim.isSelected());
        parQ.setResp7(radioBtn7Sim.isSelected());
        
        try {
                sessao.beginTransaction();

                    if(listaParQ.size()>0){
                        sessao.update(parQ);
                        JOptionPane.showMessageDialog(this, "Alterado com sucesso!");
                    } else{
                        sessao.save(parQ);
                        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                    }
                    
                sessao.getTransaction().commit();
                dispose();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar!"+"\n\nERRO: "+e.getMessage()+"\n\nCAUSA: "+e.getCause());
                //sessao.getTransaction().rollback();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        jPanel13 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        radioBtn1Sim = new javax.swing.JRadioButton();
        radioBtn1Nao = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel62 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        radioBtn2Sim = new javax.swing.JRadioButton();
        radionBtn2Nao = new javax.swing.JRadioButton();
        jLabel63 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        radioBtn4Sim = new javax.swing.JRadioButton();
        radioBtn4Nao = new javax.swing.JRadioButton();
        jLabel64 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        radioBtn5Sim = new javax.swing.JRadioButton();
        radioBtn5Nao = new javax.swing.JRadioButton();
        jLabel65 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        radioBtn6Sim = new javax.swing.JRadioButton();
        radioBtn6Nao = new javax.swing.JRadioButton();
        jLabel66 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        radioBtn7Sim = new javax.swing.JRadioButton();
        radioBtn7Nao = new javax.swing.JRadioButton();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel67 = new javax.swing.JLabel();
        radioBtn3Sim = new javax.swing.JRadioButton();
        radionBtn3Nao = new javax.swing.JRadioButton();
        btnCancelar1 = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblDuvidaParQ = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNovaModalidade = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quetion??rio de prontid??o");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel13.setBackground(new java.awt.Color(245, 245, 245));
        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setText("1 - Algum m??dico j?? disse que voc?? possui algum problema do cora????o?");

        buttonGroup1.add(radioBtn1Sim);
        radioBtn1Sim.setText("SIM");

        buttonGroup1.add(radioBtn1Nao);
        radioBtn1Nao.setText("N??O");

        jLabel62.setText("2 - Voc?? sente dor no peito quando pratica atividade f??sica?");

        buttonGroup2.add(radioBtn2Sim);
        radioBtn2Sim.setText("SIM");

        buttonGroup2.add(radionBtn2Nao);
        radionBtn2Nao.setText("N??O");

        jLabel63.setText("4 - Voc?? sente tontura ou desmaio quando pratica alguma atividade f??sica?");

        buttonGroup4.add(radioBtn4Sim);
        radioBtn4Sim.setText("SIM");

        buttonGroup4.add(radioBtn4Nao);
        radioBtn4Nao.setText("N??O");

        jLabel64.setText("5 - Voc?? tem algum problema muscular ou ??sseo que poderia ser agravado pela atividade f??sica?");

        buttonGroup5.add(radioBtn5Sim);
        radioBtn5Sim.setText("SIM");

        buttonGroup5.add(radioBtn5Nao);
        radioBtn5Nao.setText("N??O");

        jLabel65.setText("6 - Algum m??dico ja lhe recomendou o uso de medicamentos para press??o arterial ou circula????o?");

        buttonGroup6.add(radioBtn6Sim);
        radioBtn6Sim.setText("SIM");

        buttonGroup6.add(radioBtn6Nao);
        radioBtn6Nao.setText("N??O");

        jLabel66.setText("7 - Existem alguma raz??o ou impedimento para que voc?? pratique atividades f??sicas sem supervis??o m??dica?");

        buttonGroup7.add(radioBtn7Sim);
        radioBtn7Sim.setText("SIM");

        buttonGroup7.add(radioBtn7Nao);
        radioBtn7Nao.setText("N??O");

        jLabel67.setText("3 - Voc?? sentiu dor no peito no ??ltimo m??s?");

        buttonGroup3.add(radioBtn3Sim);
        radioBtn3Sim.setText("SIM");

        buttonGroup3.add(radionBtn3Nao);
        radionBtn3Nao.setText("N??O");

        btnCancelar1.setBackground(new java.awt.Color(255, 51, 51));
        btnCancelar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar1.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCancelarBranco16x16.png"))); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.setIconTextGap(8);
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setIconTextGap(8);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblDuvidaParQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPergunta24x24.png"))); // NOI18N
        lblDuvidaParQ.setToolTipText("O que ???");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Avalia????es F??sicas >");

        lblNovaModalidade.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaModalidade.setForeground(new java.awt.Color(102, 102, 102));
        lblNovaModalidade.setText("Par-Q");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNovaModalidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDuvidaParQ))
                            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn2Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radionBtn2Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn4Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtn4Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn5Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtn5Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn6Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtn6Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn7Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtn7Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn1Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtn1Nao))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(radioBtn3Sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radionBtn3Nao)))
                                .addGap(16, 16, 16))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator6)
                            .addComponent(jSeparator7)
                            .addComponent(jSeparator8)
                            .addComponent(jSeparator10)
                            .addComponent(jSeparator11))))
                .addGap(25, 25, 25))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lblNovaModalidade))
                    .addComponent(lblDuvidaParQ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(radioBtn1Sim)
                    .addComponent(radioBtn1Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(radioBtn2Sim)
                    .addComponent(radionBtn2Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioBtn3Sim)
                        .addComponent(radionBtn3Nao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(radioBtn4Sim)
                    .addComponent(radioBtn4Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(radioBtn5Sim)
                    .addComponent(radioBtn5Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(radioBtn6Sim)
                    .addComponent(radioBtn6Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(radioBtn7Sim)
                    .addComponent(radioBtn7Nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        
        Boolean isOK;
        isOK = ((radioBtn1Sim.isSelected() || radioBtn1Nao.isSelected()) &&
            (radioBtn2Sim.isSelected() || radionBtn2Nao.isSelected()) &&
            (radioBtn3Sim.isSelected() || radionBtn3Nao.isSelected()) &&
            (radioBtn4Sim.isSelected() || radioBtn4Nao.isSelected()) &&
            (radioBtn5Sim.isSelected() || radioBtn5Nao.isSelected()) &&
            (radioBtn6Sim.isSelected() || radioBtn6Nao.isSelected()) &&
            (radioBtn7Sim.isSelected() || radioBtn7Nao .isSelected()));

        if(isOK){

            if(listaParQ.size()>0){
                parQ = listaParQ.get(0);
                salvar();
            } else{
                parQ = new Parq();
                salvar();
            }

        } else{
            JOptionPane.showMessageDialog(this, "Selecione todos os campos primeiro!");
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogParQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogParQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogParQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogParQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogParQ dialog = new JDialogParQ(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblDuvidaParQ;
    private javax.swing.JLabel lblNovaModalidade;
    private javax.swing.JRadioButton radioBtn1Nao;
    private javax.swing.JRadioButton radioBtn1Sim;
    private javax.swing.JRadioButton radioBtn2Sim;
    private javax.swing.JRadioButton radioBtn3Sim;
    private javax.swing.JRadioButton radioBtn4Nao;
    private javax.swing.JRadioButton radioBtn4Sim;
    private javax.swing.JRadioButton radioBtn5Nao;
    private javax.swing.JRadioButton radioBtn5Sim;
    private javax.swing.JRadioButton radioBtn6Nao;
    private javax.swing.JRadioButton radioBtn6Sim;
    private javax.swing.JRadioButton radioBtn7Nao;
    private javax.swing.JRadioButton radioBtn7Sim;
    private javax.swing.JRadioButton radionBtn2Nao;
    private javax.swing.JRadioButton radionBtn3Nao;
    // End of variables declaration//GEN-END:variables
}
