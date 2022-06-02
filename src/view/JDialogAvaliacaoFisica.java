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
import model.Avaliacaofisica;
import model.Pessoafisica;
import model.Professor;
import org.hibernate.Session;

/**
 *
 * @author Danilo
 */
public class JDialogAvaliacaoFisica extends javax.swing.JDialog {

    private Session sessao;
    private Aluno aluno;
    private Professor professor;
    private Avaliacaofisica avaliacaoFisica;
    
    private List<Professor> listaProfessor;
    private List<Aluno> listaAluno;
    public List<Avaliacaofisica> listaAvaliacaoFisica;
    
    private DefaultComboBoxModel dcmProfessor;
    private IFrmAvaliacaoFisica frame;
    
    public JDialogAvaliacaoFisica(java.awt.Frame parent, boolean modal) {
        initComponents();
    }
    
    public JDialogAvaliacaoFisica(java.awt.Frame parent, boolean modal, Session sessao, Aluno aluno, Avaliacaofisica avaliacaoFisica, 
                                  List<Avaliacaofisica> listaAvaliacaoFisica, IFrmAvaliacaoFisica frame) {
        super(parent, modal);
        initComponents();
        
        this.sessao=sessao;
        this.aluno=aluno;
        this.avaliacaoFisica=avaliacaoFisica;
        this.listaAvaliacaoFisica=listaAvaliacaoFisica;
        this.frame=frame;
        
        
        listaProfessor = new ArrayList<>();
        //listaAvaliacaoFisica= new ArrayList<>();
        dcmProfessor = (DefaultComboBoxModel) cbxProfessor.getModel();
        popularCampos();
    }
    
    private void popularCampos(){
        String data;
       
        listaProfessor= sessao.createQuery("from Professor").list();
        listaAvaliacaoFisica = sessao.createQuery("from Avaliacaofisica where aluno.id = "+aluno.getPessoafisica().getPessoa().getId()).list();
        
        dcmProfessor.addElement("--SELECIONE--");
        
        try {
            for(int i =0; i<listaProfessor.size(); i++){
               dcmProfessor.addElement(listaProfessor.get(i).getPessoafisica().getPessoa().getNome());
           }
        } catch (Exception e) {
        }
        txtAluno.setText(aluno.getPessoafisica().getPessoa().getNome());
        
        if(avaliacaoFisica != null){
            data= DateFormat.getDateInstance().format(avaliacaoFisica.getData());
            data = data.replace("/", "-");
            jtxtDataAvaliacao.setValue(data);
            cbxProfessor.setSelectedItem(avaliacaoFisica.getProfessor().getPessoafisica().getPessoa().getNome());
            
            lblNovaAvaliacao.setText("Editar Avaliação");
        }else{
            data = DateFormat.getDateInstance().format(new Date());
            data = data.replace("/", "-");
            jtxtDataAvaliacao.setValue(data);
            
            lblNovaAvaliacao.setText("Nova Avaliação");
        }
    }
    
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
    
    
    private void salvar(){
        Date dataAvaliacao = formatarData(jtxtDataAvaliacao.getValue().toString());
        for (int i=0; i<listaProfessor.size(); i++) {    
                    if(listaProfessor.get(i).getPessoafisica().getPessoa().getNome().equals(cbxProfessor.getSelectedItem().toString())){
                        professor= listaProfessor.get(i);
                    }
        }

        if(avaliacaoFisica == null){
            avaliacaoFisica = new Avaliacaofisica(aluno, professor, dataAvaliacao);
        } else{
            avaliacaoFisica.setProfessor(professor);
            avaliacaoFisica.setData(dataAvaliacao);
        }

        try {
                sessao.beginTransaction();
                sessao.save(avaliacaoFisica);
                sessao.getTransaction().commit();

                JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
                listaAvaliacaoFisica.add(avaliacaoFisica);
                dispose();

        } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }
    
    private void editar(){
        Date dataAvaliacao = Pessoafisica.formatarData(jtxtDataAvaliacao.getValue().toString()); 
        
        if(cbxProfessor.getSelectedItem().toString().equals("--SELECIONE--")){
                JOptionPane.showMessageDialog(this, "Selecione um Professor!");
        } else{
            
            for (int i=0; i<listaProfessor.size(); i++) {    
                    if(listaProfessor.get(i).getPessoafisica().getPessoa().getNome().equals(cbxProfessor.getSelectedItem().toString())){
                        professor= listaProfessor.get(i);
                    }
            }
            
            avaliacaoFisica.setProfessor(professor);
            avaliacaoFisica.setData(dataAvaliacao);
            
            try {
                sessao.beginTransaction();
                    sessao.update(avaliacaoFisica);
                sessao.getTransaction().commit();

                JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
                dispose();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "ERRO: "+e.getMessage());
            }
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

        jPanel2 = new javax.swing.JPanel();
        lblNovaAvaliacao = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAluno = new javax.swing.JTextField();
        cbxProfessor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jtxtDataAvaliacao = new javax.swing.JFormattedTextField();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(250, 250, 250));

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        lblNovaAvaliacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaAvaliacao.setForeground(new java.awt.Color(102, 102, 102));
        lblNovaAvaliacao.setText("Nova Avaliação");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Avaliações Físicas >");

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Aluno:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Professor:");

        txtAluno.setEnabled(false);

        cbxProfessor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProfessorActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Data:");

        btnSalvar.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSalvar.setIconTextGap(8);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        try {
            jtxtDataAvaliacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnCancelar.setBackground(new java.awt.Color(255, 51, 51));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCancelarBranco16x16.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setIconTextGap(8);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 192, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAluno)
                    .addComponent(cbxProfessor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDataAvaliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtDataAvaliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNovaAvaliacao)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4))
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNovaAvaliacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
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

    private void cbxProfessorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProfessorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProfessorActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        boolean temDataRepetida = false;
        String data="";

        if((jtxtDataAvaliacao.getValue() != null) && !cbxProfessor.getSelectedItem().equals("--SELECIONE--")){

            for(int i=0; i< listaAvaliacaoFisica.size(); i++){
                data = DateFormat.getDateInstance().format(listaAvaliacaoFisica.get(i).getData());
                data = data.replace("/", "-");

                if(jtxtDataAvaliacao.getValue().equals(data)){
                    temDataRepetida = true;
                }
            }

            JOptionPane.showMessageDialog(this,data);
            JOptionPane.showMessageDialog(this,jtxtDataAvaliacao.getValue());

            if(temDataRepetida && avaliacaoFisica == null){
                JOptionPane.showMessageDialog(this, "Já existe uma avaliação nessa data.");
            } else{
                salvar();
            }

            frame.popularAvaliacoes();

        } else{
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogAvaliacaoFisica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogAvaliacaoFisica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogAvaliacaoFisica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogAvaliacaoFisica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogAvaliacaoFisica dialog = new JDialogAvaliacaoFisica(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JFormattedTextField jtxtDataAvaliacao;
    private javax.swing.JLabel lblNovaAvaliacao;
    private javax.swing.JTextField txtAluno;
    // End of variables declaration//GEN-END:variables
}
