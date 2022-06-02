
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import model.CustomTableCellRenderer;
import model.Exercicio;
import org.hibernate.Session;

/** * * @author Danilo*/

public class IFrmExercicios extends javax.swing.JInternalFrame {

    private Session sessao;
    private DefaultTableModel dtmExercicios, dtmGrupoMuscular;
    
    private int linhaSelecionada;
    
    private List<Exercicio> listaExercicios;
    private List<String> listaGrupos;
    private Exercicio exercicio;
    
    public CustomTableCellRenderer cellRenderer;
    
    public IFrmExercicios() {
        initComponents();
    }
    
    public IFrmExercicios(Session sessao) {
        initComponents();
        this.sessao = sessao;
        this.dtmExercicios = (DefaultTableModel) tblExercicios.getModel();
        this.dtmGrupoMuscular = (DefaultTableModel) tblGrupoMuscular.getModel();
        
        listaExercicios = new ArrayList<>();
        
        //##ALTERANDO A TABELA 'EXERCICIOS'
        
            tblExercicios.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            //tblExercicios.setTableHeader(null);
            tblExercicios.setBackground(Color.white);

            tblExercicios.getParent().setBackground(painelExercicios.getBackground());
            tblExercicios.setShowGrid(true);
            tblExercicios.setShowHorizontalLines(true);
            tblExercicios.setShowVerticalLines(false);
            
            cellRenderer = new CustomTableCellRenderer();
            
            tblExercicios.getColumn("EXERCÍCIO").setCellRenderer(cellRenderer);
            tblExercicios.getColumn("GRUPO MUSCULAR").setCellRenderer(cellRenderer);
            tblExercicios.getColumn("OBSERVAÇÕES").setCellRenderer(cellRenderer);
            
            tblGrupoMuscular.getColumn("GRUPO MUSCULAR").setCellRenderer(cellRenderer);
            
        
        //------------------------------------------------------------
            
        //##ALTERANDO A TABELA 'GRUPO MUSCULAR'
            tblGrupoMuscular.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            //tirando o cabeçalho da tabela
            tblGrupoMuscular.setTableHeader(null);
            
            tblGrupoMuscular.setBackground(Color.white);

            tblGrupoMuscular.getParent().setBackground(painelGrupoMuscular.getBackground());
            tblGrupoMuscular.setShowGrid(true);
            tblGrupoMuscular.setShowHorizontalLines(true);
            tblGrupoMuscular.setShowVerticalLines(false);
        //------------------------------------------------------
        
        
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior
         // this.setBorder(null);//retirar bordas
        
        popularTabela();
        popularTabelaGrupoMuscular();
        
        tblGrupoMuscular.addRowSelectionInterval(0, 0);
    }
    
    
    
    private void popularTabelaGrupoMuscular(){
        
        dtmGrupoMuscular.addRow(new String[]{"TODOS"});
        dtmGrupoMuscular.addRow(new String[]{"ABDOMEM"});
        dtmGrupoMuscular.addRow(new String[]{"BÍCEPS"});
        dtmGrupoMuscular.addRow(new String[]{"COSTAS"});
        dtmGrupoMuscular.addRow(new String[]{"GLÚTEO"});
        dtmGrupoMuscular.addRow(new String[]{"OMBRO"});
        dtmGrupoMuscular.addRow(new String[]{"PEITO"});
        dtmGrupoMuscular.addRow(new String[]{"PERNA"});
        
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

        dtmExercicios.addRow(e.montarLinha());
        //dtmGrupoMuscular.addRow(e.montarLinhaGrupoMuscular());
    }
    
    
    private void deletar() {

        try {
            sessao.beginTransaction();
                sessao.delete(exercicio);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "EXCLUIDO com sucesso!");
            listaExercicios.remove(exercicio);
            dtmExercicios.removeRow(linhaSelecionada);
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!" + "\n" + erro + "\n Causa: " + erro.getCause());
            sessao.getTransaction().rollback();
        }
    }
    
    private void pesquisar(){
        txtPesquisa.setText(txtPesquisa.getText().toUpperCase());
        String pesquisa= txtPesquisa.getText();
        dtmExercicios.setRowCount(0);
        
        for(int i=0; i <listaExercicios.size(); i++){
               
            String nome = listaExercicios.get(i).getNome();
            
            if(nome.contains(pesquisa)){
               
                Exercicio e = listaExercicios.get(i);
                
                dtmExercicios.addRow(e.montarLinha());
            }
        }
    }
    
    private void filtrarExercicios(){
        
        Exercicio e;
        dtmExercicios.setRowCount(0);
        
        for(int i=0; i < listaExercicios.size(); i++){
            if(listaExercicios.get(i).getGrupoMuscular().equals(tblGrupoMuscular.getValueAt(tblGrupoMuscular.getSelectedRow(), 0))){
                e = listaExercicios.get(i);
                dtmExercicios.addRow(e.montarLinha());
            }
            
            if(tblGrupoMuscular.getValueAt(tblGrupoMuscular.getSelectedRow(), 0).equals("TODOS")){
                e = listaExercicios.get(i);
                dtmExercicios.addRow(e.montarLinha());
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

        jPanel1 = new javax.swing.JPanel();
        jPanelExercicios = new javax.swing.JPanel();
        painelExercicios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExercicios = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnRelatorio = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        painelGrupoMuscular = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGrupoMuscular = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        jPanelExercicios.setBackground(new java.awt.Color(255, 255, 255));

        painelExercicios.setBackground(new java.awt.Color(242, 242, 242));
        painelExercicios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane1.setBorder(null);

        tblExercicios.setAutoCreateRowSorter(true);
        tblExercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "EXERCÍCIO", "GRUPO MUSCULAR", "OBSERVAÇÕES"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblExercicios.setGridColor(new java.awt.Color(204, 204, 204));
        tblExercicios.setRowHeight(40);
        tblExercicios.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblExercicios.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblExercicios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblExercicios);
        if (tblExercicios.getColumnModel().getColumnCount() > 0) {
            tblExercicios.getColumnModel().getColumn(0).setMinWidth(0);
            tblExercicios.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblExercicios.getColumnModel().getColumn(0).setMaxWidth(0);
            tblExercicios.getColumnModel().getColumn(1).setMinWidth(150);
            tblExercicios.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblExercicios.getColumnModel().getColumn(1).setMaxWidth(150);
            tblExercicios.getColumnModel().getColumn(2).setMinWidth(150);
            tblExercicios.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblExercicios.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        jPanel5.setBackground(new java.awt.Color(31, 158, 150));

        jLabel20.setBackground(new java.awt.Color(28, 140, 132));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisaBranco.png"))); // NOI18N
        jLabel20.setOpaque(true);

        btnRelatorio.setBackground(new java.awt.Color(31, 158, 150));
        btnRelatorio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconImprimirBranco20x20.png"))); // NOI18N
        btnRelatorio.setText("Imprimir");
        btnRelatorio.setContentAreaFilled(false);
        btnRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRelatorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRelatorio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRelatorio.setOpaque(true);
        btnRelatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRelatorioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRelatorioMouseExited(evt);
            }
        });
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });

        btnNovo.setBackground(new java.awt.Color(31, 158, 150));
        btnNovo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnNovo.setForeground(new java.awt.Color(255, 255, 255));
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAdicionarBranco20x20.png"))); // NOI18N
        btnNovo.setText("Adicionar");
        btnNovo.setContentAreaFilled(false);
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNovo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNovo.setIconTextGap(8);
        btnNovo.setOpaque(true);
        btnNovo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNovoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNovoMouseExited(evt);
            }
        });
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(31, 158, 150));
        btnExcluir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconDeleteBranco20x20.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnExcluir.setOpaque(true);
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(31, 158, 150));
        btnEditar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconEditarBranco20x20.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEditar.setIconTextGap(8);
        btnEditar.setOpaque(true);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        txtPesquisa.setBackground(new java.awt.Color(34, 170, 160));
        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        txtPesquisa.setText(" PESQUISAR EXERCICIO");
        txtPesquisa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesquisaFocusLost(evt);
            }
        });
        txtPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPesquisaMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtPesquisaMouseReleased(evt);
            }
        });
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRelatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPesquisa)
                    .addComponent(btnRelatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelExerciciosLayout = new javax.swing.GroupLayout(painelExercicios);
        painelExercicios.setLayout(painelExerciciosLayout);
        painelExerciciosLayout.setHorizontalGroup(
            painelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(painelExerciciosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        painelExerciciosLayout.setVerticalGroup(
            painelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelExerciciosLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Exercícios");

        painelGrupoMuscular.setBackground(new java.awt.Color(242, 242, 242));
        painelGrupoMuscular.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane2.setBorder(null);

        tblGrupoMuscular.setBackground(new java.awt.Color(204, 204, 204));
        tblGrupoMuscular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRUPO MUSCULAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGrupoMuscular.setGridColor(new java.awt.Color(204, 204, 204));
        tblGrupoMuscular.setRowHeight(40);
        tblGrupoMuscular.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblGrupoMuscular.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblGrupoMuscular.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblGrupoMuscular.getTableHeader().setResizingAllowed(false);
        tblGrupoMuscular.getTableHeader().setReorderingAllowed(false);
        tblGrupoMuscular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGrupoMuscularMouseClicked(evt);
            }
        });
        tblGrupoMuscular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGrupoMuscularKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblGrupoMuscular);

        jPanel3.setBackground(new java.awt.Color(31, 158, 150));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Grupo muscular111");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelGrupoMuscularLayout = new javax.swing.GroupLayout(painelGrupoMuscular);
        painelGrupoMuscular.setLayout(painelGrupoMuscularLayout);
        painelGrupoMuscularLayout.setHorizontalGroup(
            painelGrupoMuscularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        painelGrupoMuscularLayout.setVerticalGroup(
            painelGrupoMuscularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelGrupoMuscularLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanelExerciciosLayout = new javax.swing.GroupLayout(jPanelExercicios);
        jPanelExercicios.setLayout(jPanelExerciciosLayout);
        jPanelExerciciosLayout.setHorizontalGroup(
            jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelExerciciosLayout.createSequentialGroup()
                        .addComponent(painelGrupoMuscular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(painelExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );
        jPanelExerciciosLayout.setVerticalGroup(
            jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelExerciciosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelExerciciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelGrupoMuscular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnRelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseEntered
        btnRelatorio.setBackground(Color.gray);
    }//GEN-LAST:event_btnRelatorioMouseEntered

    private void btnRelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseExited
        btnRelatorio.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnRelatorioMouseExited

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed

    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setBackground(Color.gray);
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnNovoMouseExited

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        
        exercicio = null;
    
        JDialogNovoExercicio jdNovoExercicio = new JDialogNovoExercicio(new Frame(), true, sessao, exercicio);
        jdNovoExercicio.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
        jdNovoExercicio.setVisible(true);
        
         listaExercicios.clear();
         dtmExercicios.setRowCount(0);
         popularTabela();
       
         txtPesquisa.setText(" PESQUISAR EXERCÍCIO");
         tblGrupoMuscular.addRowSelectionInterval(0, 0);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

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
            JOptionPane.showMessageDialog(this, "Selecione um exercício primeiro");
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(Color.gray);
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
 
        linhaSelecionada= tblExercicios.getSelectedRow();
        
        if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblExercicios.getValueAt(linhaSelecionada, 0).toString());
            exercicio= (Exercicio) sessao.get(Exercicio.class, id);
            
            JDialogNovoExercicio jdNovoExercicio = new JDialogNovoExercicio(new Frame(), true, sessao, exercicio);
            jdNovoExercicio.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
            jdNovoExercicio.setVisible(true);
            
            listaExercicios.clear();
            dtmExercicios.setRowCount(0);
            popularTabela();
            
            txtPesquisa.setText(" PESQUISAR EXERCÍCIO");
            tblGrupoMuscular.addRowSelectionInterval(0, 0);
            
        } else{
            JOptionPane.showMessageDialog(this, "Selecione um Exercício primeiro.");
        }
        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        
        if(txtPesquisa.getText().equals("")){
            txtPesquisa.setText(" PESQUISAR EXERCÍCIO");
        }else{
            txtPesquisa.requestFocus();
        }
        
         
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void txtPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseClicked
        txtPesquisa.setText("");
        pesquisar();
    }//GEN-LAST:event_txtPesquisaMouseClicked

    private void txtPesquisaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseReleased

    }//GEN-LAST:event_txtPesquisaMouseReleased

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed

    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        pesquisar();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void txtPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyTyped
        
    }//GEN-LAST:event_txtPesquisaKeyTyped

    private void tblGrupoMuscularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGrupoMuscularMouseClicked
        
        filtrarExercicios();
    }//GEN-LAST:event_tblGrupoMuscularMouseClicked

    private void tblGrupoMuscularKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGrupoMuscularKeyReleased
        filtrarExercicios();
    }//GEN-LAST:event_tblGrupoMuscularKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelExercicios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel painelExercicios;
    private javax.swing.JPanel painelGrupoMuscular;
    private javax.swing.JTable tblExercicios;
    private javax.swing.JTable tblGrupoMuscular;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
