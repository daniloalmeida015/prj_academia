package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CustomTableCellRenderer;
import model.Exercicio;
import org.hibernate.Session;

/** * * @author Danilo */
public class JDialogExercicios extends javax.swing.JDialog {
    
    private Session sessao;
    private DefaultTableModel dtm;
    
    private int linhaSelecionada;
    
    private List<Exercicio> listaExercicios;
    private Exercicio exercicio;
    public CustomTableCellRenderer cellRenderer;
    
    public JDialogExercicios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public JDialogExercicios(java.awt.Frame parent, boolean modal, Session sessao) {
        super(parent, modal);
        initComponents();
        
        this.sessao = sessao;
        this.dtm = (DefaultTableModel) tblExercicios.getModel();
        listaExercicios = new ArrayList<>();
        
        //## EDITANDO A TABELA ---------------
            tblExercicios.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblExercicios.setBackground(Color.white);         

            tblExercicios.getParent().setBackground(jPanelExercicios.getBackground());
            tblExercicios.setShowGrid(true);
            tblExercicios.setShowHorizontalLines(true);
            tblExercicios.setShowVerticalLines(false);
        
        
            cellRenderer = new CustomTableCellRenderer();
            
            tblExercicios.getColumn("ID").setCellRenderer(cellRenderer);
            tblExercicios.getColumn("EXERCICIO").setCellRenderer(cellRenderer);
            tblExercicios.getColumn("GRUPO MUSCULAR").setCellRenderer(cellRenderer);
        
        popularTabela();
    }
    
    private void popularTabela() {

        listaExercicios = sessao.createQuery("from Exercicio").list();

        if (listaExercicios != null) {

            for (int aux = 0; aux < listaExercicios.size(); aux++) {

                novaLinha(aux);

            }
        }
    }
    
    private void novaLinha(int aux) {

        Exercicio e = listaExercicios.get(aux);

        dtm.addRow(e.montarLinha());
    }
    
    private void cadastrar() {
        
        exercicio.setNome(txtNomeExercicio.getText());
        exercicio.setGrupoMuscular(cbxGrupoMuscular.getSelectedItem().toString());
        exercicio.setObservacoes(txtAreaObservacao.getText());
        exercicio.setImgDemonstrativa("img/"+txtNomeExercicio.getText());
        
        try {
            sessao.beginTransaction();
            sessao.save(exercicio);

            sessao.getTransaction().commit();
            JOptionPane.showMessageDialog(this, "Salvo com sucesso!");

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(this, "Erro ao salvar as informa????es!" + "\n" + erro);
            sessao.getTransaction().rollback();

        }
    }
    
    private void limpar(){
        jTExercicios.setSelectedComponent(jPanelExercicios);
        jTExercicios.setEnabledAt(0, true);
        jTExercicios.setEnabledAt(1, false);
        
        
        txtNomeExercicio.setText("");
        cbxGrupoMuscular.setSelectedIndex(0);
        txtAreaObservacao.setText("");
        
        exercicio = null;
    }
    
    
    private void deletar() {

        try {
            sessao.beginTransaction();
                sessao.delete(exercicio);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "EXCLUIDO com sucesso!");
            listaExercicios.remove(exercicio);
            dtm.removeRow(linhaSelecionada);
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informa????es!" + "\n" + erro + "\n Causa: " + erro.getCause());
            sessao.getTransaction().rollback();
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
        jTExercicios = new javax.swing.JTabbedPane();
        jPanelExercicios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExercicios = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanelCadastrar = new javax.swing.JPanel();
        lblImagmeExercicio1 = new javax.swing.JLabel();
        btnCancelar1 = new javax.swing.JButton();
        btnSalvar1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaObservacao = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtNomeExercicio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbxGrupoMuscular = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exerc??cios f??sicos");
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTExercicios.setBackground(new java.awt.Color(250, 250, 250));
        jTExercicios.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanelExercicios.setBackground(new java.awt.Color(250, 250, 250));

        tblExercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "EXERC??CIO", "GRUPO MUSCULAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblExercicios);
        if (tblExercicios.getColumnModel().getColumnCount() > 0) {
            tblExercicios.getColumnModel().getColumn(0).setPreferredWidth(60);
        }

        btnNovo.setBackground(new java.awt.Color(31, 158, 150));
        btnNovo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAdicionar2.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNovo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(31, 158, 150));
        btnEditar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconEditar3.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(31, 158, 150));
        btnExcluir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconDelete20x20.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnFechar.setBackground(new java.awt.Color(255, 51, 51));
        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFechar.setForeground(new java.awt.Color(255, 255, 255));
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco16x16.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFechar.setIconTextGap(8);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        jLabel11.setText("Procurar Exerc??cio:");

        txtPesquisa.setBackground(new java.awt.Color(250, 250, 250));
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisa2.png"))); // NOI18N

        javax.swing.GroupLayout jPanelExerciciosLayout = new javax.swing.GroupLayout(jPanelExercicios);
        jPanelExercicios.setLayout(jPanelExerciciosLayout);
        jPanelExerciciosLayout.setHorizontalGroup(
            jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnFechar, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelExerciciosLayout.setVerticalGroup(
            jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelExerciciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTExercicios.addTab("Exerc??cios", jPanelExercicios);

        jPanelCadastrar.setBackground(new java.awt.Color(250, 250, 250));

        lblImagmeExercicio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagmeExercicio1.setText("Aqui colocar a img do exercicio");
        lblImagmeExercicio1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        btnSalvar1.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar1.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar1.setText("Salvar");
        btnSalvar1.setIconTextGap(8);
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Observa????es:");

        txtAreaObservacao.setColumns(20);
        txtAreaObservacao.setRows(5);
        jScrollPane2.setViewportView(txtAreaObservacao);

        jLabel4.setText("Nome do exerc??cio:");

        jLabel5.setText("Grupo muscular:");

        cbxGrupoMuscular.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ABDOMEM", "B??CEPS", "COSTAS", "GL??TEO", "OMBRO", "PEITO", "PERNA" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(432, 432, 432))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(408, 408, 408))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(421, 421, 421))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxGrupoMuscular, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNomeExercicio))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeExercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxGrupoMuscular, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelCadastrarLayout = new javax.swing.GroupLayout(jPanelCadastrar);
        jPanelCadastrar.setLayout(jPanelCadastrarLayout);
        jPanelCadastrarLayout.setHorizontalGroup(
            jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadastrarLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblImagmeExercicio1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadastrarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelCadastrarLayout.setVerticalGroup(
            jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImagmeExercicio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTExercicios.addTab("Cadastrar/Editar", jPanelCadastrar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTExercicios)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        jTExercicios.setSelectedComponent(jPanelCadastrar);
        jTExercicios.setEnabledAt(0, false);
        jTExercicios.setEnabledAt(1, true);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        linhaSelecionada= tblExercicios.getSelectedRow();
        
        if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblExercicios.getValueAt(linhaSelecionada, 0).toString());
            exercicio= (Exercicio) sessao.get(Exercicio.class, id);

            txtNomeExercicio.setText(exercicio.getNome());
            cbxGrupoMuscular.setSelectedItem(exercicio.getGrupoMuscular().toUpperCase());
            txtAreaObservacao.setText(exercicio.getObservacoes());
            
            jTExercicios.setSelectedComponent(jPanelCadastrar);
            jTExercicios.setEnabledAt(0, false);
            jTExercicios.setEnabledAt(1, true);
            
        }else{
           JOptionPane.showMessageDialog(this, "Selecione um Exerc??cio primeiro");
       }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        linhaSelecionada= tblExercicios.getSelectedRow();
        Integer id;
        int opcao;
        
        
        if(linhaSelecionada >=0){
            id = Integer.valueOf(tblExercicios.getValueAt(linhaSelecionada, 0).toString());
            exercicio= (Exercicio) sessao.get(Exercicio.class, id);

            opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir "+exercicio.getNome().toUpperCase()+"? ", "Excluir exercicio!", JOptionPane.YES_NO_OPTION);

            if(opcao==0){
                deletar();
            }
        } else{
            JOptionPane.showMessageDialog(this, "Selecione um exerc??cio primeiro");
        }

        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased

        txtPesquisa.setText(txtPesquisa.getText().toUpperCase());
        String pesquisa= txtPesquisa.getText();
        
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        limpar();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        
    if(!txtNomeExercicio.getText().equals("")){
        if(exercicio == null){
            exercicio = new Exercicio();
            cadastrar();
            
            listaExercicios.add(exercicio);
            dtm.addRow(exercicio.montarLinha());
        } else{
            cadastrar();
            dtm.setValueAt(exercicio.getNome(), linhaSelecionada, 1);
            dtm.setValueAt(exercicio.getGrupoMuscular(), linhaSelecionada, 2);
        }
        
        limpar();
    } else{
        JOptionPane.showMessageDialog(this, "Preencha o campo Nome do Exerc??cio primeiro");
    }
        
    }//GEN-LAST:event_btnSalvar1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogExercicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogExercicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogExercicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogExercicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogExercicios dialog = new JDialogExercicios(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar1;
    private javax.swing.JComboBox<String> cbxGrupoMuscular;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCadastrar;
    private javax.swing.JPanel jPanelExercicios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTExercicios;
    private javax.swing.JLabel lblImagmeExercicio1;
    private javax.swing.JTable tblExercicios;
    private javax.swing.JTextArea txtAreaObservacao;
    private javax.swing.JTextField txtNomeExercicio;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
