package view;

import dao.GerarRelatorio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.CustomTableCellRenderer;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

/** * * @author danilo */
public class IFrmAluno extends javax.swing.JInternalFrame {

    
    public static IFrmAluno ifrmAluno;
    private Session sessao;
    
    public CustomTableCellRenderer cellRenderer;
    
    private Aluno aluno;
    
    private DefaultTableModel dtm;
    
    private List<Aluno> listaAlunos;
    
    private IFrmAvaliacaoFisica avaliacaoFisica;
    private int linhaSelecionada;
    
    JDialogCidade jdCidade;
    
    private GerarRelatorio relatorio;
    private JasperViewer jpViewer;
    
    
    public IFrmAluno() {
        initComponents();
    }
    
    public IFrmAluno(Session sessao) {
        this();
        this.sessao=sessao;
        
        listaAlunos= new ArrayList<>();
        dtm = (DefaultTableModel)tblAluno.getModel();
        
        popularTabela();
        
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior
         // this.setBorder(null);//retirar bordas

         //## EDITANDO A TABELA ---------------
            tblAluno.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblAluno.setBackground(Color.white);         

            tblAluno.getParent().setBackground(jPanel8.getBackground());
            tblAluno.setShowGrid(true);
            tblAluno.setShowHorizontalLines(true);
            tblAluno.setShowVerticalLines(false);
            

            cellRenderer = new CustomTableCellRenderer();
            
            tblAluno.getColumn("#").setCellRenderer(cellRenderer);
            tblAluno.getColumn("NOME").setCellRenderer(cellRenderer);
            tblAluno.getColumn("SEXO").setCellRenderer(cellRenderer);
            tblAluno.getColumn("NASCIMENTO").setCellRenderer(cellRenderer);
            tblAluno.getColumn("STATUS").setCellRenderer(cellRenderer);
            
            
            
         //## FIM EDITANDO A TABELA ---------------
         
        //cbxStatusAluno.setSelectedItem("ATIVO");
        
    }
    
    private void popularTabela(){     
        listaAlunos= sessao.createQuery("from Aluno").list();
        if(listaAlunos!=null){
            for(int aux=0; aux <listaAlunos.size(); aux++){
                novaLinha(aux);
            }            
        }
    }
    
    
    private void novaLinha(int aux){
        Aluno a = listaAlunos.get(aux);
        dtm.addRow(a.montarLinha());
    }
    
    private void atualizarTabela(){
       
        //PEGA A ULTIMA POSIÇÃO DA LISTA
        int aux = listaAlunos.size()-1;
        
        novaLinha(aux);
    }
    
    
    //AO INVÉS DE DELETAR, ESTOU USANDO A FUNÇAO 'TORNARALUNOINATIVO'
    private void deletar(){
        try{

            sessao.beginTransaction();
                sessao.delete(aluno);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "EXCLUIDO com sucesso!");
            listaAlunos.remove(aluno);
            dtm.removeRow(linhaSelecionada);
            
        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!"+"\n"+erro+"\n Causa: "+erro.getCause());
            sessao.getTransaction().rollback();
        }
    }
    
    
    private void tornarAlunoInativo(){
        
        aluno.setStatus("INATIVO");
        
        try{
            sessao.beginTransaction();
                sessao.merge(aluno);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "EXCLUIDO com sucesso!");
            listaAlunos.set(linhaSelecionada, aluno);
            //dtm.removeRow(linhaSelecionada);
            
        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!"+"\n"+erro+"\n Causa: "+erro.getCause());
            sessao.getTransaction().rollback();
        }
    }

   
   public void filtrar(){
       
       //JOptionPane.showMessageDialog(this, "Filtrando");
       
       if(cbxStatusAluno.getSelectedItem().equals("ATIVO")){
            dtm.setRowCount(0);
            for(int aux=0; aux <listaAlunos.size(); aux++){
                
                if(listaAlunos.get(aux).getStatus().equals("ATIVO")){
                    novaLinha(aux);
                }
            }
            
        } else if(cbxStatusAluno.getSelectedItem().equals("INATIVO")){
            dtm.setRowCount(0);
            for(int aux=0; aux <listaAlunos.size(); aux++){

                if(listaAlunos.get(aux).getStatus().equals("INATIVO")){
                    novaLinha(aux);
                }
            }
        } else{
            dtm.setRowCount(0);
            for(int aux=0; aux <listaAlunos.size(); aux++){
                    novaLinha(aux);
            }
        }
   }
   
   private void pesquisar(){
       txtPesquisa.setText(txtPesquisa.getText().toUpperCase());
        String pesquisa= txtPesquisa.getText();
        dtm.setRowCount(0);
        
        for(int i=0; i <listaAlunos.size(); i++){
               
            String nome = listaAlunos.get(i).getPessoafisica().getPessoa().getNome();
            
            if(nome.contains(pesquisa)){
               
                Aluno a = listaAlunos.get(i);
                
                dtm.addRow(a.montarLinha());
            }
        }
        
        if(pesquisa.equals("")){
                cbxStatusAluno.setSelectedIndex(0);
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

        jPanel7 = new javax.swing.JPanel();
        jPanelAlunos = new javax.swing.JPanel();
        btnFechar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnRelatorio = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAluno = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnMatricula = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnAvalFisicaEMais = new javax.swing.JButton();
        btnFichaTreino1 = new javax.swing.JButton();
        btnTurmas = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        cbxStatusAluno = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(250, 250, 250));
        setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255)));
        setTitle("ALUNOS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setOpaque(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setFocusable(false);

        jPanelAlunos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAlunos.setFocusable(false);

        btnFechar.setBackground(new java.awt.Color(255, 51, 51));
        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFechar.setForeground(new java.awt.Color(255, 255, 255));
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco16x16.png"))); // NOI18N
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFechar.setIconTextGap(8);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(242, 242, 242));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(31, 158, 150));

        txtPesquisa.setBackground(new java.awt.Color(34, 170, 160));
        txtPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        txtPesquisa.setText(" PESQUISAR ALUNO");
        txtPesquisa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtPesquisa.setMargin(new java.awt.Insets(2, 2, 2, 0));
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
        });

        jLabel15.setBackground(new java.awt.Color(31, 158, 150));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisaBranco.png"))); // NOI18N
        jLabel15.setOpaque(true);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRelatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addGap(115, 115, 115)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPesquisa)
                    .addComponent(btnRelatorio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jScrollPane1.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tblAluno.setAutoCreateRowSorter(true);
        tblAluno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "NOME", "SEXO", "NASCIMENTO", "STATUS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAluno.setToolTipText("Tabela de alunos");
        tblAluno.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblAluno.setGridColor(new java.awt.Color(204, 204, 204));
        tblAluno.setRowHeight(40);
        tblAluno.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblAluno.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblAluno.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAluno.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblAluno);
        if (tblAluno.getColumnModel().getColumnCount() > 0) {
            tblAluno.getColumnModel().getColumn(0).setMinWidth(60);
            tblAluno.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblAluno.getColumnModel().getColumn(0).setMaxWidth(60);
            tblAluno.getColumnModel().getColumn(2).setMinWidth(100);
            tblAluno.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblAluno.getColumnModel().getColumn(2).setMaxWidth(100);
            tblAluno.getColumnModel().getColumn(3).setMinWidth(120);
            tblAluno.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblAluno.getColumnModel().getColumn(3).setMaxWidth(120);
            tblAluno.getColumnModel().getColumn(4).setMinWidth(120);
            tblAluno.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblAluno.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(31, 158, 150));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Opções para o aluno");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btnMatricula.setBackground(new java.awt.Color(250, 250, 250));
        btnMatricula.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btnMatricula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconModalidade32x32.png"))); // NOI18N
        btnMatricula.setText("Matriculas");
        btnMatricula.setBorderPainted(false);
        btnMatricula.setContentAreaFilled(false);
        btnMatricula.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMatricula.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMatricula.setIconTextGap(8);
        btnMatricula.setOpaque(true);
        btnMatricula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMatriculaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMatriculaMouseExited(evt);
            }
        });
        btnMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatriculaActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOpaque(true);

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOpaque(true);

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator3.setOpaque(true);

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator5.setOpaque(true);

        btnAvalFisicaEMais.setBackground(new java.awt.Color(250, 250, 250));
        btnAvalFisicaEMais.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btnAvalFisicaEMais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAvaliacaoFisica32x32.png"))); // NOI18N
        btnAvalFisicaEMais.setText("Aval. fisicas");
        btnAvalFisicaEMais.setBorderPainted(false);
        btnAvalFisicaEMais.setContentAreaFilled(false);
        btnAvalFisicaEMais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAvalFisicaEMais.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAvalFisicaEMais.setIconTextGap(8);
        btnAvalFisicaEMais.setOpaque(true);
        btnAvalFisicaEMais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAvalFisicaEMaisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAvalFisicaEMaisMouseExited(evt);
            }
        });
        btnAvalFisicaEMais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvalFisicaEMaisActionPerformed(evt);
            }
        });

        btnFichaTreino1.setBackground(new java.awt.Color(250, 250, 250));
        btnFichaTreino1.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btnFichaTreino1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFichaTreino32x32.png"))); // NOI18N
        btnFichaTreino1.setText("Ficha de treino");
        btnFichaTreino1.setBorderPainted(false);
        btnFichaTreino1.setContentAreaFilled(false);
        btnFichaTreino1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFichaTreino1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFichaTreino1.setIconTextGap(8);
        btnFichaTreino1.setOpaque(true);
        btnFichaTreino1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFichaTreino1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFichaTreino1MouseExited(evt);
            }
        });
        btnFichaTreino1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFichaTreino1ActionPerformed(evt);
            }
        });

        btnTurmas.setBackground(new java.awt.Color(250, 250, 250));
        btnTurmas.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btnTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconGroup32x32.png"))); // NOI18N
        btnTurmas.setText("Turmas");
        btnTurmas.setBorderPainted(false);
        btnTurmas.setContentAreaFilled(false);
        btnTurmas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTurmas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTurmas.setIconTextGap(8);
        btnTurmas.setOpaque(true);
        btnTurmas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTurmasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTurmasMouseExited(evt);
            }
        });
        btnTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurmasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator5)
            .addComponent(btnAvalFisicaEMais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnFichaTreino1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(btnTurmas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAvalFisicaEMais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFichaTreino1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTurmas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(242, 242, 242));
        jPanel6.setToolTipText("");

        cbxStatusAluno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStatusAluno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "ATIVO", "INATIVO" }));
        cbxStatusAluno.setBorder(null);
        cbxStatusAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStatusAlunoActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(31, 158, 150));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Filtros");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbxStatusAluno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxStatusAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Alunos");

        javax.swing.GroupLayout jPanelAlunosLayout = new javax.swing.GroupLayout(jPanelAlunos);
        jPanelAlunos.setLayout(jPanelAlunosLayout);
        jPanelAlunosLayout.setHorizontalGroup(
            jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlunosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAlunosLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelAlunosLayout.createSequentialGroup()
                        .addGroup(jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4)
                            .addGroup(jPanelAlunosLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnFechar, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(35, 35, 35))))
        );
        jPanelAlunosLayout.setVerticalGroup(
            jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlunosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAlunosLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelAlunos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelAlunos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       linhaSelecionada= tblAluno.getSelectedRow();
       
       if(linhaSelecionada >=0){
           Integer id = Integer.valueOf(tblAluno.getValueAt(linhaSelecionada, 0).toString());
           aluno= (Aluno) sessao.get(Aluno.class, id);
           
            //this.setVisible(false);
                JDialogNovoAluno jdNovoAluno = new JDialogNovoAluno(new Frame(), true, sessao, aluno);
                jdNovoAluno.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdNovoAluno.setVisible(true);
            //this.setVisible(true);
            
            txtPesquisa.setText(" PESQUISAR ALUNO");
            cbxStatusAluno.setSelectedItem("TODOS");
            
       } else{
           JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro");
       }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        linhaSelecionada= tblAluno.getSelectedRow();
        
        if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblAluno.getValueAt(linhaSelecionada, 0).toString());
            aluno= (Aluno) sessao.get(Aluno.class, id);

            int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja realmente excluir "+aluno.getPessoafisica().getPessoa().getNome().toUpperCase()+"? ", "Excluir aluno!", JOptionPane.YES_NO_OPTION);

            if(opcao==0){
                
                tornarAlunoInativo();
                txtPesquisa.setText(" PESQUISAR ALUNO");
                cbxStatusAluno.setSelectedItem("INATIVO");
                //deletar();
                
            }
        } else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro");
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        aluno = null;
        //this.setVisible(false);
            JDialogNovoAluno jdNovoAluno = new JDialogNovoAluno(new Frame(), true, sessao, aluno);
            jdNovoAluno.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
            jdNovoAluno.setVisible(true);
        //this.setVisible(true);
        
        dtm.setRowCount(0);
        popularTabela();
        
        txtPesquisa.setText(" PESQUISAR ALUNO");
        cbxStatusAluno.setSelectedItem("TODOS");
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatriculaActionPerformed
               
       linhaSelecionada= tblAluno.getSelectedRow();
       
       if(linhaSelecionada >=0){
           Integer id = Integer.valueOf(tblAluno.getValueAt(linhaSelecionada, 0).toString());
           aluno= (Aluno) sessao.get(Aluno.class, id);
           
            //this.setVisible(false);
                JDialogMatricula jdMatricula = new JDialogMatricula(new Frame(), true, aluno, sessao);
                jdMatricula.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdMatricula.setVisible(true);
            //this.setVisible(true);
            
            //cbxStatusAluno.setSelectedItem("TODOS");
            
       } else{
           JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro");
       }
       
    }//GEN-LAST:event_btnMatriculaActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        
        String sql = "SELECT\n" +
"             aluno.status AS aluno_status,\n" +
"             pessoafisica.sexo AS pessoafisica_sexo,\n" +
"             pessoafisica.dataNascimento AS pessoafisica_dataNascimento,\n" +
"             pessoa.id AS pessoa_id,\n" +
"             pessoa.nome AS pessoa_nome,\n" +
"             pessoa.email AS pessoa_email\n" +
"        FROM\n" +
"             pessoafisica pessoafisica INNER JOIN aluno aluno ON pessoafisica.pessoa_id = aluno.pessoaFisica_id\n" +
"             INNER JOIN pessoa pessoa ON pessoafisica.pessoa_id = pessoa.id";
        
              
        if(jpViewer == null){
            relatorio = new GerarRelatorio();
            jpViewer = relatorio.gerarRelatorioAluno(sql, "/relatorios/rel_alunos.jasper");  
        }
        
        jpViewer.setVisible(true);
        jpViewer.setState(Frame.NORMAL);
        
        jpViewer.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt){
                jpViewer = null;
            }
        });
        
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased

        pesquisar();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void cbxStatusAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStatusAlunoActionPerformed
        
        filtrar();
        
    }//GEN-LAST:event_cbxStatusAlunoActionPerformed

    private void btnAvalFisicaEMaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvalFisicaEMaisActionPerformed

       linhaSelecionada= tblAluno.getSelectedRow();
       
       if(linhaSelecionada >=0){
           Integer id = Integer.valueOf(tblAluno.getValueAt(linhaSelecionada, 0).toString());
           aluno= (Aluno) sessao.get(Aluno.class, id);
           
           avaliacaoFisica = new IFrmAvaliacaoFisica(sessao, aluno);
           
           FrmPrincipal.btnAluno.setBackground(new Color(31,158,150));
           FrmPrincipal.btnAluno.setForeground(Color.white);
           FrmPrincipal.btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunosBranco.png")));
           dispose();
           
            FrmPrincipal.dskPrincipal.add(avaliacaoFisica);
            avaliacaoFisica.setSize(FrmPrincipal.dskPrincipal.getWidth(),FrmPrincipal.dskPrincipal.getHeight());

            avaliacaoFisica.setVisible(true);

            //FrmPrincipal.dskPrincipal.setSelectedFrame(this);
            
            
           FrmPrincipal.btnAvaliacaoFisica.setBackground(Color.white);
           FrmPrincipal.btnAvaliacaoFisica.setForeground(Color.black);
           FrmPrincipal.btnAvaliacaoFisica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAvalFisica.png")));
           
        
       
       } else{
           JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro");
       }

        
    }//GEN-LAST:event_btnAvalFisicaEMaisActionPerformed

    private void btnMatriculaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMatriculaMouseEntered
        btnMatricula.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnMatriculaMouseEntered

    private void btnMatriculaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMatriculaMouseExited
        btnMatricula.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnMatriculaMouseExited

    private void btnAvalFisicaEMaisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvalFisicaEMaisMouseEntered
        btnAvalFisicaEMais.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnAvalFisicaEMaisMouseEntered

    private void btnAvalFisicaEMaisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvalFisicaEMaisMouseExited
        btnAvalFisicaEMais.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnAvalFisicaEMaisMouseExited

    private void btnFichaTreino1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFichaTreino1ActionPerformed
        
       linhaSelecionada= tblAluno.getSelectedRow();
       
       if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblAluno.getValueAt(linhaSelecionada, 0).toString());
            aluno= (Aluno) sessao.get(Aluno.class, id);

            //  fichaTreino  = new IFrmFichaTreino(sessao, aluno);
            IFrmFichaTreino fichaTreino  = new IFrmFichaTreino(sessao, aluno);

            FrmPrincipal.btnAluno.setBackground(new Color(31,158,150));
            FrmPrincipal.btnAluno.setForeground(Color.white);
            FrmPrincipal.btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunosBranco.png")));
            dispose();

                FrmPrincipal.dskPrincipal.add(fichaTreino);
                fichaTreino.setSize(FrmPrincipal.dskPrincipal.getWidth(),FrmPrincipal.dskPrincipal.getHeight());
                fichaTreino.setVisible(true);
                //FrmPrincipal.dskPrincipal.setSelectedFrame(this);

            FrmPrincipal.btnFichaTreino.setBackground(Color.white);
            FrmPrincipal.btnFichaTreino.setForeground(Color.black);
            FrmPrincipal.btnFichaTreino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFichaTreino.png")));
        
       
       } else{
           JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro");
       }
       
        
    }//GEN-LAST:event_btnFichaTreino1ActionPerformed

    private void btnTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurmasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTurmasActionPerformed

    private void btnFichaTreino1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFichaTreino1MouseEntered
        btnFichaTreino1.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnFichaTreino1MouseEntered

    private void btnFichaTreino1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFichaTreino1MouseExited
        btnFichaTreino1.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnFichaTreino1MouseExited

    private void btnTurmasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTurmasMouseEntered
        btnTurmas.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnTurmasMouseEntered

    private void btnTurmasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTurmasMouseExited
        btnTurmas.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnTurmasMouseExited

    private void btnRelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseEntered
        btnRelatorio.setBackground(Color.gray);
    }//GEN-LAST:event_btnRelatorioMouseEntered

    private void btnRelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseExited
        btnRelatorio.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnRelatorioMouseExited

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setBackground(Color.gray);
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnNovoMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(Color.gray);
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void txtPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseClicked
        txtPesquisa.setText("");
        pesquisar();
    }//GEN-LAST:event_txtPesquisaMouseClicked

    private void txtPesquisaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseReleased
        
    }//GEN-LAST:event_txtPesquisaMouseReleased

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
         if(txtPesquisa.getText().equals("")){
            txtPesquisa.setText(" PESQUISAR ALUNO");
        } else{
             txtPesquisa.requestFocus();
         }
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        FrmPrincipal.btnAluno.setBackground(Color.white);
           FrmPrincipal.btnAluno.setForeground(Color.black);
           FrmPrincipal.btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunos.png")));
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvalFisicaEMais;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFichaTreino1;
    private javax.swing.JButton btnMatricula;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnTurmas;
    private javax.swing.JComboBox<String> cbxStatusAluno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAlunos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable tblAluno;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
