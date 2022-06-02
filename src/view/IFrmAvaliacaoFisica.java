package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.Avaliacaofisica;
import model.CustomTableCellRenderer;
import org.hibernate.Session;
import static view.FrmPrincipal.dskPrincipal;


public class IFrmAvaliacaoFisica extends javax.swing.JInternalFrame {

    private Session sessao;
    
    int idSelecionado;
    int linhaSelecionada;
    public Aluno aluno;
    private Avaliacaofisica avaliacaofisica;
    
    private List<Aluno> listaAluno;
    private List<Avaliacaofisica> listaAvaliacaoFisica;
    
    private DefaultComboBoxModel dcmAvaliacaoFisica;
    private DefaultListModel dlmAluno;
    private DefaultTableModel dtmAvaliacoes;
    
    private JDialogAvaliacaoFisica jdAvaliacaoFisica;
    private JDialogAnamnese jdAnamnese;
    private JDialogPerimetria jdPerimetria;
    private JDialogIMC imc;
    private JDialogParQ jdParQ;
    private JDialogDobraCutanea jdDobraCutanea;
    
    public CustomTableCellRenderer cellRenderer;
    
    public IFrmAvaliacaoFisica() {
        initComponents();
    }
    
    public IFrmAvaliacaoFisica(Session sessao, Aluno aluno) {
        this();
        this.sessao=sessao;
        
        this.aluno=aluno;
        listaAvaliacaoFisica= new ArrayList<>();
        
        //dcmAvaliacaoFisica = (DefaultComboBoxModel) cbxAvaliacao.getModel();
        dtmAvaliacoes = (DefaultTableModel) tblAvaliacoes.getModel();
        
        listaAluno = sessao.createQuery("from Aluno").list();
        
        idSelecionado =-1;
        
        dlmAluno= new DefaultListModel();
        
        paineDeInformacao.setVisible(false);
        
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior
        
        //## EDITANDO A TABELA ---------------
            tblAvaliacoes.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblAvaliacoes.setBackground(Color.white);         

            tblAvaliacoes.getParent().setBackground(painelDaTabela.getBackground());
            tblAvaliacoes.setShowGrid(true);
            tblAvaliacoes.setShowHorizontalLines(true);
            tblAvaliacoes.setShowVerticalLines(false);
         
            cellRenderer = new CustomTableCellRenderer();
            
            tblAvaliacoes.getColumn("AVALIACAO").setCellRenderer(cellRenderer);
            tblAvaliacoes.getColumn("ALUNO").setCellRenderer(cellRenderer);
            tblAvaliacoes.getColumn("DATA").setCellRenderer(cellRenderer);
            tblAvaliacoes.getColumn("PROFESSOR").setCellRenderer(cellRenderer);
            
         //## FIM EDITANDO A TABELA ---------------

        popularTabelaAvaliacoes();
        
        if(this.aluno!=null){
            
            txtPesquisa.setText(this.aluno.getPessoafisica().getPessoa().getNome());
            txtPesquisa.requestFocus();
            pesquisar();
        }
    }
    
    
    private void popularTabelaAvaliacoes(){
        
        listaAvaliacaoFisica.clear();
        dtmAvaliacoes.setRowCount(0);
        
        List<Avaliacaofisica> lista = sessao.createQuery("from Avaliacaofisica").list();
        
        if(checkMostrarFicha.isSelected()){
            
            for(int i= lista.size()-1; i >=0; i--){

                boolean tem = false;
                if(i == lista.size()-1){
                    listaAvaliacaoFisica.add(lista.get(i));
                }else{
                    for(int j = listaAvaliacaoFisica.size()-1; j >=0; j--){

                        if(listaAvaliacaoFisica.get(j).getAluno().getPessoafisica().getPessoa().getId().equals(
                                lista.get(i).getAluno().getPessoafisica().getPessoa().getId())){
                            
                            tem = true;
                        }
                    }

                    if(!tem){
                        listaAvaliacaoFisica.add(lista.get(i));
                    }
                }
            }
            
            Collections.reverse(listaAvaliacaoFisica);
            
        } else{
            listaAvaliacaoFisica = lista;
        }
        
        if(listaAvaliacaoFisica!=null){
            for(int aux=0; aux <listaAvaliacaoFisica.size(); aux++){
                Avaliacaofisica aval = listaAvaliacaoFisica.get(aux);
                dtmAvaliacoes.addRow(aval.montarLinha());
            }
        }
    }
    
    
    
    
    public void popularAvaliacoes(){
        
        String data="";
        
            try {
                listaAvaliacaoFisica = sessao.createQuery("from Avaliacaofisica where aluno_id = " + aluno.getPessoafisica().getPessoa().getId()).list();

                JOptionPane.showMessageDialog(this, "List size:"+listaAvaliacaoFisica.size());
                if (listaAvaliacaoFisica.size() > 0) {
                    
                    dcmAvaliacaoFisica.removeAllElements();

                    for (int i = 0; i < listaAvaliacaoFisica.size(); i++) {
                        
                        data = DateFormat.getDateInstance().format(listaAvaliacaoFisica.get(i).getData());
                        dcmAvaliacaoFisica.addElement(data);
                    }
                    dcmAvaliacaoFisica.setSelectedItem(data);
                } else{
                    dcmAvaliacaoFisica.setSelectedItem("Nenhuma avaliação");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: "+e.getMessage());
            }
    }
    
    
    
    public void recuperarAluno(Aluno a){   
        this.aluno = a;
    }
    
    
    private void pesquisar(){
                
        listaAvaliacaoFisica.clear();
        dtmAvaliacoes.setRowCount(0);        
        
        List<Avaliacaofisica> lista = sessao.createQuery("from Avaliacaofisica").list();
        
        if(checkMostrarFicha.isSelected()){
            
            for(int i= lista.size()-1; i >=0; i--){

                boolean tem = false;
                if(i == lista.size()-1){
                    listaAvaliacaoFisica.add(lista.get(i));
                }else{
                    for(int j = listaAvaliacaoFisica.size()-1; j >=0; j--){

                        if(listaAvaliacaoFisica.get(j).getAluno().getPessoafisica().getPessoa().getId().equals(
                                lista.get(i).getAluno().getPessoafisica().getPessoa().getId())){
                            
                            tem = true;
                        }
                    }

                    if(!tem){
                        listaAvaliacaoFisica.add(lista.get(i));
                    }
                }
            }
            
        } else{
            listaAvaliacaoFisica = lista;
        }
        
        
        if(listaAvaliacaoFisica!=null && listaAvaliacaoFisica.size() >0){
            for(int aux=0; aux <listaAvaliacaoFisica.size(); aux++){

                if(listaAvaliacaoFisica.get(aux).getAluno().getPessoafisica().getPessoa().getId().equals(
                        aluno.getPessoafisica().getPessoa().getId())){

                    Avaliacaofisica aval = listaAvaliacaoFisica.get(aux);
                    dtmAvaliacoes.addRow(aval.montarLinha());
                }
            }

        }
            
        txtPesquisa.setText(aluno.getPessoafisica().getPessoa().getNome());
        
        if(dtmAvaliacoes.getRowCount() ==0){
            
            paineDeInformacao.setVisible(true);
            jScrollPane1.setVisible(false);
            tblAvaliacoes.setVisible(false);

            String sexo = aluno.getPessoafisica().getSexo();

            lblInformacao.setText((sexo.equals("M")?"O aluno ":"A aluna " )+"\""+txtPesquisa.getText()+"\" não possui Avaliação");

        } else{
            paineDeInformacao.setVisible(false);
            jScrollPane1.setVisible(true);
            tblAvaliacoes.setVisible(true);
        }
   }
    
    
    
    private void limpar(){
        avaliacaofisica=null;
        
        paineDeInformacao.setVisible(false);
        jScrollPane1.setVisible(true);
        tblAvaliacoes.setVisible(true);
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
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        painelDaTabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAvaliacoes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        paineDeInformacao = new javax.swing.JPanel();
        lblInformacao = new javax.swing.JLabel();
        lblNovaFicha = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAnamnese = new javax.swing.JButton();
        btnParQ = new javax.swing.JButton();
        btnPerimetria = new javax.swing.JButton();
        btnIMC = new javax.swing.JButton();
        btnDobraCutanea = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        btnFechar = new javax.swing.JButton();
        checkMostrarFicha = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFocusable(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Avaliações Físicas");

        painelDaTabela.setBackground(new java.awt.Color(245, 245, 245));
        painelDaTabela.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane1.setBorder(null);

        tblAvaliacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID_AVALIACAO", "AVALIACAO", "ALUNO", "DATA", "PROFESSOR"
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
        tblAvaliacoes.setGridColor(new java.awt.Color(204, 204, 204));
        tblAvaliacoes.setRowHeight(40);
        tblAvaliacoes.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblAvaliacoes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblAvaliacoes);
        if (tblAvaliacoes.getColumnModel().getColumnCount() > 0) {
            tblAvaliacoes.getColumnModel().getColumn(0).setMinWidth(0);
            tblAvaliacoes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAvaliacoes.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAvaliacoes.getColumnModel().getColumn(1).setMinWidth(120);
            tblAvaliacoes.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblAvaliacoes.getColumnModel().getColumn(1).setMaxWidth(120);
            tblAvaliacoes.getColumnModel().getColumn(3).setMinWidth(120);
            tblAvaliacoes.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblAvaliacoes.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        jPanel4.setBackground(new java.awt.Color(31, 158, 150));

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

        txtPesquisa.setEditable(false);
        txtPesquisa.setBackground(new java.awt.Color(34, 170, 160));
        txtPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        txtPesquisa.setText(" PESQUISAR ALUNO");
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

        jLabel1.setBackground(new java.awt.Color(31, 158, 150));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisaBranco.png"))); // NOI18N
        jLabel1.setOpaque(true);

        btnLimpar.setBackground(new java.awt.Color(31, 158, 150));
        btnLimpar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconLimparBranco20x20.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Limpar campos");
        btnLimpar.setContentAreaFilled(false);
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setFocusable(false);
        btnLimpar.setOpaque(true);
        btnLimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimparMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimparMouseExited(evt);
            }
        });
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar)
                .addGap(74, 74, 74)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLimpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        paineDeInformacao.setBackground(new java.awt.Color(245, 245, 245));

        lblInformacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInformacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconInfo30x30.png"))); // NOI18N
        lblInformacao.setText("O aluno(a) não possui Avaliação ...");

        lblNovaFicha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNovaFicha.setForeground(new java.awt.Color(0, 102, 204));
        lblNovaFicha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNovaFicha.setText("Adicionar nova Ficha");
        lblNovaFicha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNovaFicha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNovaFichaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNovaFichaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNovaFichaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout paineDeInformacaoLayout = new javax.swing.GroupLayout(paineDeInformacao);
        paineDeInformacao.setLayout(paineDeInformacaoLayout);
        paineDeInformacaoLayout.setHorizontalGroup(
            paineDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineDeInformacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paineDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInformacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNovaFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        paineDeInformacaoLayout.setVerticalGroup(
            paineDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineDeInformacaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInformacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNovaFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelDaTabelaLayout = new javax.swing.GroupLayout(painelDaTabela);
        painelDaTabela.setLayout(painelDaTabelaLayout);
        painelDaTabelaLayout.setHorizontalGroup(
            painelDaTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(paineDeInformacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(painelDaTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        painelDaTabelaLayout.setVerticalGroup(
            painelDaTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDaTabelaLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(paineDeInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setOpaque(false);

        btnAnamnese.setBackground(new java.awt.Color(250, 250, 250));
        btnAnamnese.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        btnAnamnese.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAnamnese64x64.png"))); // NOI18N
        btnAnamnese.setText("ANAMNESE");
        btnAnamnese.setContentAreaFilled(false);
        btnAnamnese.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnamnese.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAnamnese.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAnamnese.setIconTextGap(16);
        btnAnamnese.setOpaque(true);
        btnAnamnese.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAnamneseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAnamneseMouseExited(evt);
            }
        });
        btnAnamnese.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnamneseActionPerformed(evt);
            }
        });
        btnAnamnese.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnAnamneseKeyReleased(evt);
            }
        });

        btnParQ.setBackground(new java.awt.Color(250, 250, 250));
        btnParQ.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        btnParQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconParQ64x64.png"))); // NOI18N
        btnParQ.setText("PAR-Q");
        btnParQ.setContentAreaFilled(false);
        btnParQ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnParQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnParQ.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnParQ.setIconTextGap(16);
        btnParQ.setOpaque(true);
        btnParQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnParQMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnParQMouseExited(evt);
            }
        });
        btnParQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParQActionPerformed(evt);
            }
        });

        btnPerimetria.setBackground(new java.awt.Color(250, 250, 250));
        btnPerimetria.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        btnPerimetria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPerimetria64x64.png"))); // NOI18N
        btnPerimetria.setText("PERIMETRIA");
        btnPerimetria.setContentAreaFilled(false);
        btnPerimetria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPerimetria.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPerimetria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPerimetria.setIconTextGap(16);
        btnPerimetria.setOpaque(true);
        btnPerimetria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPerimetriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPerimetriaMouseExited(evt);
            }
        });
        btnPerimetria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerimetriaActionPerformed(evt);
            }
        });

        btnIMC.setBackground(new java.awt.Color(250, 250, 250));
        btnIMC.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        btnIMC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconImc64x64.png"))); // NOI18N
        btnIMC.setText("IMC");
        btnIMC.setContentAreaFilled(false);
        btnIMC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIMC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIMC.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnIMC.setIconTextGap(16);
        btnIMC.setOpaque(true);
        btnIMC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIMCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIMCMouseExited(evt);
            }
        });
        btnIMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIMCActionPerformed(evt);
            }
        });

        btnDobraCutanea.setBackground(new java.awt.Color(250, 250, 250));
        btnDobraCutanea.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        btnDobraCutanea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconDobraCutanea64x64.png"))); // NOI18N
        btnDobraCutanea.setText("DOBRA CUTANEA");
        btnDobraCutanea.setContentAreaFilled(false);
        btnDobraCutanea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDobraCutanea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDobraCutanea.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDobraCutanea.setIconTextGap(16);
        btnDobraCutanea.setOpaque(true);
        btnDobraCutanea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDobraCutaneaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDobraCutaneaMouseExited(evt);
            }
        });
        btnDobraCutanea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDobraCutaneaActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOpaque(true);

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOpaque(true);

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator3.setOpaque(true);

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator4.setOpaque(true);

        jPanel5.setBackground(new java.awt.Color(31, 158, 150));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Avaliações Físicas");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator6.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAnamnese, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPerimetria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnIMC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnParQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDobraCutanea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAnamnese, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 35, Short.MAX_VALUE)
                .addComponent(btnPerimetria, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 38, Short.MAX_VALUE)
                .addComponent(btnIMC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 37, Short.MAX_VALUE)
                .addComponent(btnParQ, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 36, Short.MAX_VALUE)
                .addComponent(btnDobraCutanea, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        btnFechar.setBackground(new java.awt.Color(255, 51, 51));
        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFechar.setForeground(new java.awt.Color(255, 255, 255));
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco16x16.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFechar.setIconTextGap(8);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        checkMostrarFicha.setSelected(true);
        checkMostrarFicha.setText("Mostrar somente a última Avaliação Física de cada aluno");
        checkMostrarFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMostrarFichaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator5)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(checkMostrarFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFechar))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(painelDaTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelDaTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(checkMostrarFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        if(txtPesquisa.getText().equals("")){
            txtPesquisa.setText(" PESQUISAR ALUNO");
        }

    }//GEN-LAST:event_txtPesquisaFocusLost

    private void txtPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseClicked
        
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, null, this);
        pesquisarAluno.setVisible(true);
        
        if(aluno != null){
            pesquisar();
        }else{
            popularTabelaAvaliacoes();
        }
            
    }//GEN-LAST:event_txtPesquisaMouseClicked

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        pesquisar();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setBackground(Color.gray);
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnNovoMouseExited

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        aluno = null;
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, null, this);
        pesquisarAluno.setVisible(true);
        
        if(aluno != null){
            jdAvaliacaoFisica = new JDialogAvaliacaoFisica(new Frame(), true, sessao, aluno,null, listaAvaliacaoFisica, this);
            jdAvaliacaoFisica.setVisible(true);
            
            popularTabelaAvaliacoes();
        }

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);
            
           //aluno = avaliacaofisica.getAluno();

            int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar a Avaliação Física? ", "Excluir Avaliação", JOptionPane.YES_NO_OPTION);

            if(opcao==0){
                try {
                    sessao.beginTransaction();
                    sessao.delete(avaliacaofisica);
                    sessao.getTransaction().commit();

                    JOptionPane.showMessageDialog(this, "Deletado com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERRO: "+e.getMessage());
                }
                
                if(txtPesquisa.getText().equals(" PESQUISAR ALUNO")){
                    popularTabelaAvaliacoes();
                }else{
                    pesquisar();
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "Selecione um Aluno e uma Avaliação primeiro!");
        }
        
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(Color.gray);
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);
            
            aluno = avaliacaofisica.getAluno();

            jdAvaliacaoFisica = new JDialogAvaliacaoFisica(new Frame(), true,sessao, aluno,
                avaliacaofisica, listaAvaliacaoFisica, this);
            jdAvaliacaoFisica.setVisible(true);
            
            if(txtPesquisa.getText().equals(" PESQUISAR ALUNO")){
                popularTabelaAvaliacoes();
            }else{
                pesquisar();
            }
            
        } else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno e uma avaliação primeiro!");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAnamneseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnamneseMouseEntered
        btnAnamnese.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnAnamneseMouseEntered

    private void btnAnamneseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnamneseMouseExited
        btnAnamnese.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnAnamneseMouseExited

    private void btnAnamneseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnamneseActionPerformed

        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);

            //this.setVisible(false);  //quando abre jdAnamnese, para de exibir o Iframe avaliacaoFisica

            jdAnamnese = new JDialogAnamnese(new Frame(), true, sessao, avaliacaofisica);
            jdAnamnese.setLocationRelativeTo(dskPrincipal);
            jdAnamnese.setVisible(true);

            jdAnamnese.setLocationRelativeTo(this);

            // this.setVisible(true); //quando fechar o jdAnamnese, volta a exibir o Iframe avaliacaoFisica

        }else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno e uma avaliação primeiro!");
        }
    }//GEN-LAST:event_btnAnamneseActionPerformed

    private void btnAnamneseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAnamneseKeyReleased

    }//GEN-LAST:event_btnAnamneseKeyReleased

    private void btnParQMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnParQMouseEntered
        btnParQ.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnParQMouseEntered

    private void btnParQMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnParQMouseExited
        btnParQ.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnParQMouseExited

    private void btnParQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParQActionPerformed

        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);
           

            jdParQ = new JDialogParQ(new Frame(), true, sessao, avaliacaofisica);
            jdParQ.setLocationRelativeTo(dskPrincipal);
            jdParQ.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno e uma avaliação primeiro!");
        }
    }//GEN-LAST:event_btnParQActionPerformed

    private void btnPerimetriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerimetriaMouseEntered
        btnPerimetria.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnPerimetriaMouseEntered

    private void btnPerimetriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerimetriaMouseExited
        btnPerimetria.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnPerimetriaMouseExited

    private void btnPerimetriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerimetriaActionPerformed

        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);
            
            
            jdPerimetria = new JDialogPerimetria(new Frame(), true, sessao, avaliacaofisica);
            jdPerimetria.setLocationRelativeTo(dskPrincipal);
            jdPerimetria.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno e uma avaliação primeiro!");
        }
    }//GEN-LAST:event_btnPerimetriaActionPerformed

    private void btnIMCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIMCMouseEntered
        btnIMC.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnIMCMouseEntered

    private void btnIMCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIMCMouseExited
        btnIMC.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnIMCMouseExited

    private void btnIMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIMCActionPerformed

        imc = new JDialogIMC(new Frame(), true);
        imc.setLocationRelativeTo(dskPrincipal);
        imc.setVisible(true);

    }//GEN-LAST:event_btnIMCActionPerformed

    private void btnDobraCutaneaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDobraCutaneaMouseEntered
        btnDobraCutanea.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnDobraCutaneaMouseEntered

    private void btnDobraCutaneaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDobraCutaneaMouseExited
        btnDobraCutanea.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnDobraCutaneaMouseExited

    private void btnDobraCutaneaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDobraCutaneaActionPerformed

        linhaSelecionada= tblAvaliacoes.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblAvaliacoes.getValueAt(linhaSelecionada, 0).toString());
           avaliacaofisica = (Avaliacaofisica) sessao.get(Avaliacaofisica.class, id);
            
            

            jdDobraCutanea = new JDialogDobraCutanea(new Frame(), true);
            jdDobraCutanea.setLocationRelativeTo(dskPrincipal);
            jdDobraCutanea.setVisible(true);

        } else{
            JOptionPane.showMessageDialog(this, "Selecione um aluno e uma avaliação primeiro!");
        }
    }//GEN-LAST:event_btnDobraCutaneaActionPerformed

    private void checkMostrarFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMostrarFichaActionPerformed
        
        if(txtPesquisa.getText().equals(" PESQUISAR ALUNO")){
            popularTabelaAvaliacoes();
        }else{
            pesquisar();
        }
        
    }//GEN-LAST:event_checkMostrarFichaActionPerformed

    private void lblNovaFichaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseClicked

        aluno = null;
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, null, this);
        pesquisarAluno.setVisible(true);

        if(aluno != null){
            avaliacaofisica = null;
            
            jdAvaliacaoFisica = new JDialogAvaliacaoFisica(new Frame(), true, sessao, aluno,null, listaAvaliacaoFisica, this);
            jdAvaliacaoFisica.setVisible(true);
            
            popularTabelaAvaliacoes();
            
            txtPesquisa.requestFocus();

            //limpar();
            txtPesquisa.setText(" PESQUISAR ALUNO");

            paineDeInformacao.setVisible(false);
            jScrollPane1.setVisible(true);
            tblAvaliacoes.setVisible(true);
        }
    }//GEN-LAST:event_lblNovaFichaMouseClicked

    private void lblNovaFichaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseEntered
        lblNovaFicha.setFont(new Font("Tahoma", Font.BOLD, 12));
    }//GEN-LAST:event_lblNovaFichaMouseEntered

    private void lblNovaFichaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseExited
        lblNovaFicha.setFont(new Font("Tahoma", Font.PLAIN, 12));
    }//GEN-LAST:event_lblNovaFichaMouseExited

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        
        limpar();
        txtPesquisa.setText(" PESQUISAR ALUNO");
        popularTabelaAvaliacoes();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnLimparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimparMouseEntered
        btnLimpar.setBackground(Color.gray);
    }//GEN-LAST:event_btnLimparMouseEntered

    private void btnLimparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimparMouseExited
        btnLimpar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnLimparMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnamnese;
    private javax.swing.JButton btnDobraCutanea;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnIMC;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnParQ;
    private javax.swing.JButton btnPerimetria;
    private javax.swing.JCheckBox checkMostrarFicha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblInformacao;
    private javax.swing.JLabel lblNovaFicha;
    private javax.swing.JPanel paineDeInformacao;
    private javax.swing.JPanel painelDaTabela;
    private javax.swing.JTable tblAvaliacoes;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
