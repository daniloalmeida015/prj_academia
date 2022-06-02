package view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Fichatreino;
import model.Professor;
import org.hibernate.Session;

/** * * @author Danilo */
public class JDialogFichaTreino1 extends javax.swing.JDialog {
    
    private Session sessao;
    
    public Aluno aluno;
    public Professor professor;
    private Fichatreino fichaTreino;
    
    private List<Professor> listaProfessor;
    private List<Fichatreino> listaFichaTreino;
    
    private DefaultComboBoxModel dcmProfessor;
    
    public JDialogFichaTreino1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    
    public JDialogFichaTreino1(java.awt.Frame parent, boolean modal, Session sessao, Aluno aluno,Fichatreino fichaTreino) {
        super(parent, modal);
        initComponents();
        
        this.sessao=sessao;
        this.aluno = aluno;
        this.fichaTreino = fichaTreino;
        
        listaFichaTreino = new ArrayList<>();
        listaFichaTreino = sessao.createQuery("from Fichatreino where aluno_id = "+aluno.getPessoafisica().getPessoa().getId()).list();
        
        dcmProfessor = (DefaultComboBoxModel) cbxProfessor.getModel();
        
        popularComboProfessores();
        popularCampos();
        
    }
    
    
    private void popularComboProfessores(){
        listaProfessor= sessao.createQuery("from Professor").list();
        
        try {
            for(int i =0; i<listaProfessor.size(); i++){
               dcmProfessor.addElement(listaProfessor.get(i).getPessoafisica().getPessoa().getNome());
           }
        } catch (Exception e) {
        }
    }
    
    
    private void popularCampos(){
        
        String data;
        
        if(fichaTreino != null){
            
            txtAluno.setText(aluno.getPessoafisica().getPessoa().getNome());
            
            data= DateFormat.getDateInstance().format(fichaTreino.getDataInicio());
            data = data.replace("/", "-");
            jtxtDataInicio.setValue(data);

            data= DateFormat.getDateInstance().format(fichaTreino.getValidade());
            data = data.replace("/", "-");
            
            jtxtValidade.setValue(data);
            jTextAreaObservacoes.setText(fichaTreino.getObservacoes());
            cbxProfessor.setSelectedItem(fichaTreino.getProfessor().getPessoafisica().getPessoa().getNome());
            
            lblNovaFicha.setText("Editar Ficha");
            
        }else{
            
            txtAluno.setText(aluno.getPessoafisica().getPessoa().getNome());
            
            data = DateFormat.getDateInstance().format(new Date());
            data = data.replace("/", "-");
            jtxtDataInicio.setValue(data);

            //data= DateFormat.getDateInstance().format(new Date());
            //data = data.replace("/", "-");
            
            //jtxtValidade.setValue(data);
            jTextAreaObservacoes.setText("");
            cbxProfessor.setSelectedIndex(0);
            
            lblNovaFicha.setText("Nova Ficha");
        }
    }
    
    
    
    public static Date formatarData(String data){
      
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String nascimento = data;
        Date dataInicio=null;
        
        try {
            dataInicio = sdf.parse(nascimento);
        } catch (ParseException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return dataInicio;
    }
    
    
    
    private void salvar(){
        Date dataInicio = formatarData(jtxtDataInicio.getValue().toString());
        Date validade = formatarData(jtxtValidade.getValue().toString());
        
        for (int i=0; i<listaProfessor.size(); i++) {    
                if(listaProfessor.get(i).getPessoafisica().getPessoa().getNome().equals(cbxProfessor.getSelectedItem().toString())){
                    professor= listaProfessor.get(i);
                }
        }

        if(fichaTreino == null){
            fichaTreino = new Fichatreino(aluno, professor, dataInicio,validade, jTextAreaObservacoes.getText());
        } else{
            fichaTreino.setProfessor(professor);
            fichaTreino.setDataInicio(dataInicio);
            fichaTreino.setValidade(validade);
            fichaTreino.setObservacoes(jTextAreaObservacoes.getText());
        }

        try {
                sessao.beginTransaction();
                sessao.save(fichaTreino);
                sessao.getTransaction().commit();

                JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
                listaFichaTreino.add(fichaTreino);
                //dispose();

        } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }
    


    
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelDetalhes = new javax.swing.JPanel();
        jPanelFichaDoAluno = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtxtDataInicio = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtValidade = new javax.swing.JFormattedTextField();
        cbxProfessor = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaObservacoes = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtAluno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblNovaFicha = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        jPanelDetalhes.setBackground(new java.awt.Color(250, 250, 250));

        jPanelFichaDoAluno.setBackground(new java.awt.Color(250, 250, 250));

        jLabel3.setText("Data de início:");

        try {
            jtxtDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Validade:");

        try {
            jtxtValidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtxtValidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtValidadeActionPerformed(evt);
            }
        });

        cbxProfessor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECIONE--" }));

        jLabel2.setText("Professor:");

        jTextAreaObservacoes.setColumns(20);
        jTextAreaObservacoes.setRows(5);
        jScrollPane1.setViewportView(jTextAreaObservacoes);

        jLabel5.setText("Observações:");

        txtAluno.setEditable(false);
        txtAluno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Aluno:");

        javax.swing.GroupLayout jPanelFichaDoAlunoLayout = new javax.swing.GroupLayout(jPanelFichaDoAluno);
        jPanelFichaDoAluno.setLayout(jPanelFichaDoAlunoLayout);
        jPanelFichaDoAlunoLayout.setHorizontalGroup(
            jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAluno)
                    .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(166, 166, 166))
                            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                                .addComponent(jtxtDataInicio)
                                .addGap(16, 16, 16)))
                        .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jtxtValidade))))
                    .addComponent(jScrollPane1)
                    .addComponent(cbxProfessor, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelFichaDoAlunoLayout.setVerticalGroup(
            jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnCancelar.setBackground(new java.awt.Color(255, 51, 51));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco16x16.png"))); // NOI18N
        btnCancelar.setText("Fechar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.setIconTextGap(8);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setIconTextGap(8);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Fichas de Treino >");

        lblNovaFicha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaFicha.setForeground(new java.awt.Color(102, 102, 102));
        lblNovaFicha.setText("Nova Ficha");

        javax.swing.GroupLayout jPanelDetalhesLayout = new javax.swing.GroupLayout(jPanelDetalhes);
        jPanelDetalhes.setLayout(jPanelDetalhesLayout);
        jPanelDetalhesLayout.setHorizontalGroup(
            jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                .addGroup(jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelFichaDoAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                        .addComponent(jSeparator4)
                        .addContainerGap())
                    .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNovaFicha)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelDetalhesLayout.setVerticalGroup(
            jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNovaFicha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelFichaDoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        boolean temDataRepetida = false;
        String data="";

        if((jtxtDataInicio.getValue() != null) && !cbxProfessor.getSelectedItem().equals("--SELECIONE--")){

            for(int i=0; i< listaFichaTreino.size(); i++){
                data = DateFormat.getDateInstance().format(listaFichaTreino.get(i).getDataInicio());
                data = data.replace("/", "-");

                if(jtxtDataInicio.getValue().equals(data)){
                    temDataRepetida = true;
                }
            }

            JOptionPane.showMessageDialog(this,"data inicio:"+jtxtDataInicio.getValue());
            JOptionPane.showMessageDialog(this,"data fim:"+jtxtValidade.getValue());

            if(temDataRepetida && fichaTreino == null){
                JOptionPane.showMessageDialog(this, "Já existe uma Ficha de Treino nessa data.");
            } else{
                salvar();
                dispose();
            }

        } else{
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
        }
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jtxtValidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtValidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtValidadeActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogFichaTreino1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogFichaTreino1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogFichaTreino1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogFichaTreino1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogFichaTreino1 dialog = new JDialogFichaTreino1(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxProfessor;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelDetalhes;
    private javax.swing.JPanel jPanelFichaDoAluno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextAreaObservacoes;
    private javax.swing.JFormattedTextField jtxtDataInicio;
    private javax.swing.JFormattedTextField jtxtValidade;
    private javax.swing.JLabel lblNovaFicha;
    private javax.swing.JTextField txtAluno;
    // End of variables declaration//GEN-END:variables

}
