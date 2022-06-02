package view;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.util.List;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CustomTableCellRenderer;
import model.Exercicio;
import model.Fichatreino;
import model.Treino;
import org.hibernate.Session;

/** * * @author Danilo*/
public class JDialogNovoTreino extends javax.swing.JDialog {

    
    private Session sessao;
    private Treino treino;
    private Exercicio exercicio;
    private List<Treino> listaTreino;
    private DefaultTableModel dtm;
    private Fichatreino fichaTreino;
    private DefaultComboBoxModel dcmExercicio;
    private List<Exercicio> listaExercicio;
    
    public CustomTableCellRenderer cellRenderer;
    
    private int linhaSelecionada;
    
    private String nomeTreino, data;
    
    
    
    public JDialogNovoTreino(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    
    public JDialogNovoTreino(java.awt.Frame parent, boolean modal, Session sessao, Fichatreino fichaTreino, String nomeTreino) {
        super(parent, modal);
        initComponents();
        
        this.fichaTreino = fichaTreino;
        this.nomeTreino = nomeTreino;
        this.sessao = sessao;
        listaTreino = sessao.createQuery("from Treino where nome = '"+this.nomeTreino+"' and fichatreino.id = "+this.fichaTreino.getId()).list();
        
        if(listaTreino.isEmpty()){
            painelDeInformacao.setVisible(true);
            //tblTreino.setVisible(false);
            jScrollPaneTabelaTreino.setVisible(false);
        }else{
            painelDeInformacao.setVisible(false);
            jScrollPaneTabelaTreino.setVisible(true);
        }
        
        
        dtm = (DefaultTableModel)tblTreino.getModel();
        listaExercicio = sessao.createQuery("from Exercicio").list();
        
        
        //##ALTERANDO A TABELA
            tblTreino.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblTreino.setBackground(Color.white);
            
            tblTreino.getParent().setBackground(jPanelTreinos.getBackground());
            tblTreino.setShowGrid(true);
            tblTreino.setShowHorizontalLines(true);
            tblTreino.setShowVerticalLines(false);
            
            
            cellRenderer = new CustomTableCellRenderer();
            
            tblTreino.getColumn("#ID").setCellRenderer(cellRenderer);
            tblTreino.getColumn("EXERCÍCIO").setCellRenderer(cellRenderer);
            tblTreino.getColumn("SERIES").setCellRenderer(cellRenderer);
            tblTreino.getColumn("REPETIÇÕES").setCellRenderer(cellRenderer);
            tblTreino.getColumn("PESO").setCellRenderer(cellRenderer);
        //## FIM ALTERAÇÃO
        
        
        
        popularTabelaTreino();
        popularComboexercicio();
        
        //this.setTitle(nomeTreino);
        lblTreino.setText(nomeTreino);
        lblTreinoTabela.setText(nomeTreino);
        
        lblNomeAluno.setText(fichaTreino.getAluno().getPessoafisica().getPessoa().getNome());
        lblEmailAluno.setText(fichaTreino.getAluno().getPessoafisica().getPessoa().getEmail());
        lblFicha.setText("Ficha # "+fichaTreino.getId());
        
        
        data= DateFormat.getDateInstance().format(fichaTreino.getValidade());
        //data = data.replace("/", "-");
        
        lblValidade.setText("Validade: "+data);
       
        jPanelInclusaoTreino.setVisible(false);
    }
    
    
    private void popularTabelaTreino(){
        
        for(int i=0; i<listaTreino.size(); i++){
            dtm.addRow(listaTreino.get(i).montarLinha());
        }
    }
    
    
    private void popularComboexercicio(){

        cbxExercicio.removeAllItems();
        dcmExercicio = (DefaultComboBoxModel) cbxExercicio.getModel();

        for(int i=0; i< listaExercicio.size(); i++){
            dcmExercicio.addElement(listaExercicio.get(i).getNome());
        }
    }
    
    private void incluirTreino(String nomeTreino){
        
        for(int i=0; i < listaExercicio.size(); i++){
            if(listaExercicio.get(i).getNome().equals(cbxExercicio.getSelectedItem())){
                exercicio = listaExercicio.get(i);
            }
        }

        if(treino == null){
            
            treino = new Treino(exercicio,
                                fichaTreino,
                                nomeTreino,
                                new Byte(spnSeries.getValue().toString()),
                                new Byte(spnRepeticoes.getValue().toString()),
                                (Float) spnPeso.getValue());
            
            //JOptionPane.showMessageDialog(this, "NOVO treino:: ID: "+treino.getId());
            
            salvarTreino();
            
            
            listaTreino.add(treino);
            dtm.addRow(listaTreino.get(listaTreino.size()-1).montarLinha());
            
        } else{
            
            JOptionPane.showMessageDialog(this, "EDITAR treino");
            
                treino.setExercicio(exercicio);
                treino.setFichatreino(fichaTreino);
                treino.setSeries(new Byte(spnSeries.getValue().toString()));
                treino.setRepeticoes(new Byte(spnRepeticoes.getValue().toString()));
                treino.setPeso((Float) spnPeso.getValue());
            
            salvarTreino();
            
            listaTreino.set(linhaSelecionada, treino);
            dtm.removeRow(linhaSelecionada);
            dtm.insertRow(linhaSelecionada, treino.montarLinha());
        }
    }
    
    
    private void salvarTreino(){
        JOptionPane.showMessageDialog(this, "Salvando");
        try {
                sessao.beginTransaction();
                    sessao.save(treino);
                sessao.getTransaction().commit();
                
                JOptionPane.showMessageDialog(this, "Salvo com sucesso");

        } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: "+e);
                sessao.getTransaction().rollback();
        }
    }
    
    private void deletarTreino(){
        try{
            sessao.beginTransaction();
                sessao.delete(treino);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "EXCLUIDO com sucesso!");
            listaTreino.remove(treino);
            dtm.removeRow(linhaSelecionada);

        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!"+"\n"+erro+"\n Causa: "+erro.getCause());
            sessao.getTransaction().rollback();
        }
    }
    
    private void adicionarNovoTreino(){
        treino = null;
        lblAdicionarOuEditarTreino.setText("Adicionar Treino");
        btnIncluirTreino.setText("Adicionar");
        
        spnSeries.setValue(0);
        spnRepeticoes.setValue(0);
        spnPeso.setValue(0);
        cbxExercicio.setSelectedIndex(0);

        jPanelInclusaoTreino.setVisible(true);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanelTreinos = new javax.swing.JPanel();
        jScrollPaneTabelaTreino = new javax.swing.JScrollPane();
        tblTreino = new javax.swing.JTable();
        painelDeInformacao = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblAdicionarNovoTreino = new javax.swing.JLabel();
        painelDeInformacao1 = new javax.swing.JPanel();
        lblTreinoTabela = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblNomeAluno = new javax.swing.JLabel();
        lblEmailAluno = new javax.swing.JLabel();
        lblFicha = new javax.swing.JLabel();
        lblValidade = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelInclusaoTreino = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbxExercicio = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        spnSeries = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        spnRepeticoes = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        spnPeso = new javax.swing.JSpinner();
        btncancelarInclusao = new javax.swing.JButton();
        btnIncluirTreino = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblAdicionarOuEditarTreino = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnRelatorio = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        lblTreino = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(242, 242, 242));
        setIconImage(null);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

        jPanel2.setBackground(new java.awt.Color(242, 242, 242));

        jPanel4.setBackground(new java.awt.Color(242, 242, 242));

        jPanelTreinos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTreinos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelTreinos.setFocusable(false);

        jScrollPaneTabelaTreino.setBorder(null);

        tblTreino.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblTreino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID", "ID TREINO", "ID EXERCÍCIO", "EXERCÍCIO", "SERIES", "REPETIÇÕES", "PESO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTreino.setGridColor(new java.awt.Color(204, 204, 204));
        tblTreino.setRowHeight(40);
        tblTreino.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblTreino.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblTreino.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneTabelaTreino.setViewportView(tblTreino);
        if (tblTreino.getColumnModel().getColumnCount() > 0) {
            tblTreino.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblTreino.getColumnModel().getColumn(1).setMinWidth(0);
            tblTreino.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblTreino.getColumnModel().getColumn(1).setMaxWidth(0);
            tblTreino.getColumnModel().getColumn(2).setMinWidth(0);
            tblTreino.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblTreino.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        painelDeInformacao.setBackground(new java.awt.Color(250, 250, 250));
        painelDeInformacao.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconInfo30x30.png"))); // NOI18N
        jLabel1.setText("Nenhum registro encontrado!");

        lblAdicionarNovoTreino.setForeground(new java.awt.Color(0, 102, 204));
        lblAdicionarNovoTreino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdicionarNovoTreino.setText("Adicionar novo treino");
        lblAdicionarNovoTreino.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAdicionarNovoTreino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAdicionarNovoTreinoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAdicionarNovoTreinoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAdicionarNovoTreinoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout painelDeInformacaoLayout = new javax.swing.GroupLayout(painelDeInformacao);
        painelDeInformacao.setLayout(painelDeInformacaoLayout);
        painelDeInformacaoLayout.setHorizontalGroup(
            painelDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeInformacaoLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(painelDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAdicionarNovoTreino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        painelDeInformacaoLayout.setVerticalGroup(
            painelDeInformacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeInformacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblAdicionarNovoTreino)
                .addContainerGap())
        );

        painelDeInformacao1.setBackground(new java.awt.Color(242, 242, 242));

        lblTreinoTabela.setBackground(new java.awt.Color(204, 204, 204));
        lblTreinoTabela.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTreinoTabela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTreinoTabela.setText("Treino");

        javax.swing.GroupLayout painelDeInformacao1Layout = new javax.swing.GroupLayout(painelDeInformacao1);
        painelDeInformacao1.setLayout(painelDeInformacao1Layout);
        painelDeInformacao1Layout.setHorizontalGroup(
            painelDeInformacao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTreinoTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelDeInformacao1Layout.setVerticalGroup(
            painelDeInformacao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeInformacao1Layout.createSequentialGroup()
                .addComponent(lblTreinoTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lblNomeAluno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNomeAluno.setText("Nome");

        lblEmailAluno.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblEmailAluno.setForeground(new java.awt.Color(102, 102, 102));
        lblEmailAluno.setText("Email");

        lblFicha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFicha.setText("Ficha #");

        lblValidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblValidade.setText("Validade:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblEmailAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeAluno)
                    .addComponent(lblFicha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailAluno)
                    .addComponent(lblValidade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelTreinosLayout = new javax.swing.GroupLayout(jPanelTreinos);
        jPanelTreinos.setLayout(jPanelTreinosLayout);
        jPanelTreinosLayout.setHorizontalGroup(
            jPanelTreinosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTreinosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTreinosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTabelaTreino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addComponent(painelDeInformacao1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(jPanelTreinosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(painelDeInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTreinosLayout.setVerticalGroup(
            jPanelTreinosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTreinosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelDeInformacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTabelaTreino, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(painelDeInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jPanelInclusaoTreino.setBackground(new java.awt.Color(250, 250, 250));
        jPanelInclusaoTreino.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Exercício:");

        jLabel7.setText("Séries:");

        spnSeries.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel9.setText("Repetições:");

        spnRepeticoes.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel13.setText("Peso (Kg):");

        spnPeso.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 5.0f));

        btncancelarInclusao.setBackground(new java.awt.Color(255, 51, 51));
        btncancelarInclusao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncancelarInclusao.setForeground(new java.awt.Color(255, 255, 255));
        btncancelarInclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco16x16.png"))); // NOI18N
        btncancelarInclusao.setText("Cancelar");
        btncancelarInclusao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncancelarInclusao.setIconTextGap(8);
        btncancelarInclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarInclusaoActionPerformed(evt);
            }
        });

        btnIncluirTreino.setBackground(new java.awt.Color(31, 158, 150));
        btnIncluirTreino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIncluirTreino.setForeground(new java.awt.Color(255, 255, 255));
        btnIncluirTreino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconProximoBranco16x16.png"))); // NOI18N
        btnIncluirTreino.setText("Adicionar");
        btnIncluirTreino.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluirTreino.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnIncluirTreino.setIconTextGap(8);
        btnIncluirTreino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirTreinoActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(31, 158, 150));

        lblAdicionarOuEditarTreino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdicionarOuEditarTreino.setForeground(new java.awt.Color(255, 255, 255));
        lblAdicionarOuEditarTreino.setText("Adicionar Treino");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdicionarOuEditarTreino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdicionarOuEditarTreino, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelInclusaoTreinoLayout = new javax.swing.GroupLayout(jPanelInclusaoTreino);
        jPanelInclusaoTreino.setLayout(jPanelInclusaoTreinoLayout);
        jPanelInclusaoTreinoLayout.setHorizontalGroup(
            jPanelInclusaoTreinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelInclusaoTreinoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInclusaoTreinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxExercicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnSeries)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnRepeticoes)
                    .addComponent(spnPeso)
                    .addComponent(btncancelarInclusao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncluirTreino, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelInclusaoTreinoLayout.setVerticalGroup(
            jPanelInclusaoTreinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInclusaoTreinoLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxExercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnRepeticoes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btncancelarInclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIncluirTreino, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(242, 242, 242));

        btnRelatorio.setBackground(new java.awt.Color(31, 158, 150));
        btnRelatorio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconImprimirBranco20x20.png"))); // NOI18N
        btnRelatorio.setToolTipText("Imprimir Treino");
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
        btnNovo.setToolTipText("Adicionar novo Treino");
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
        btnExcluir.setToolTipText("Excluir Treino selecionado");
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
        btnEditar.setToolTipText("Editar Treino selecionado");
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRelatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTreino.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTreino.setForeground(new java.awt.Color(102, 102, 102));
        lblTreino.setText("Treino");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Ficha de treino >");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTreino)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanelInclusaoTreino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTreinos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTreino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelInclusaoTreino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTreinos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIncluirTreinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirTreinoActionPerformed
        
        boolean isOk = true;
        
        isOk = !spnSeries.getValue().equals(0) &&
                !spnRepeticoes.getValue().equals(0) &&
                !spnPeso.getValue().equals(0);
        
        if(isOk){
            incluirTreino(nomeTreino);
            btncancelarInclusaoActionPerformed(evt);
            jScrollPaneTabelaTreino.setVisible(true);
            painelDeInformacao.setVisible(false);
        } else{
            JOptionPane.showMessageDialog(this, "Preencha todos os campos");
        }
        
    }//GEN-LAST:event_btnIncluirTreinoActionPerformed

    private void btncancelarInclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarInclusaoActionPerformed
        treino = null;
        spnSeries.setValue(0);
        spnRepeticoes.setValue(0);
        spnPeso.setValue(0);
        cbxExercicio.setSelectedIndex(0);
        jPanelInclusaoTreino.setVisible(false);
    }//GEN-LAST:event_btncancelarInclusaoActionPerformed

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setBackground(Color.gray);
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnNovoMouseExited

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        adicionarNovoTreino();
        

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        linhaSelecionada= tblTreino.getSelectedRow();

        if(linhaSelecionada >=0){

            Integer id = Integer.valueOf(tblTreino.getValueAt(linhaSelecionada, 0).toString());
            treino = (Treino) sessao.get(Treino.class, id);

            int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o Treino ?", "Excluir treino!", JOptionPane.YES_NO_OPTION);

            if(opcao==0){
                deletarTreino();
                
                if(listaTreino.isEmpty()){
                    painelDeInformacao.setVisible(true);
                    jScrollPaneTabelaTreino.setVisible(false);
                }
            }

        } else{
            JOptionPane.showMessageDialog(this, "Selecione um treino primeiro");
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(Color.gray);
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        linhaSelecionada= tblTreino.getSelectedRow();

        if(linhaSelecionada >=0){

            lblAdicionarOuEditarTreino.setText("Editar Treino");
            jPanelInclusaoTreino.setVisible(true);
            btnIncluirTreino.setText("Salvar");

            Integer id = Integer.valueOf(tblTreino.getValueAt(linhaSelecionada, 0).toString());
            treino = (Treino) sessao.get(Treino.class, id);

            spnSeries.setValue(treino.getSeries());
            spnRepeticoes.setValue(treino.getRepeticoes());
            spnPeso.setValue(treino.getPeso());
            dcmExercicio.setSelectedItem(treino.getExercicio().getNome());

        } else{
            JOptionPane.showMessageDialog(this, "Selecione um Treino primeiro");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed

    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnRelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseExited
        btnRelatorio.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnRelatorioMouseExited

    private void btnRelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseEntered
        btnRelatorio.setBackground(Color.gray);
    }//GEN-LAST:event_btnRelatorioMouseEntered

    private void lblAdicionarNovoTreinoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAdicionarNovoTreinoMouseEntered
        lblAdicionarNovoTreino.setFont(new Font("Tahoma", Font.BOLD, 11));
    }//GEN-LAST:event_lblAdicionarNovoTreinoMouseEntered

    private void lblAdicionarNovoTreinoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAdicionarNovoTreinoMouseExited
        lblAdicionarNovoTreino.setFont(new Font("Tahoma", Font.PLAIN, 11));
    }//GEN-LAST:event_lblAdicionarNovoTreinoMouseExited

    private void lblAdicionarNovoTreinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAdicionarNovoTreinoMouseClicked
        adicionarNovoTreino();
    }//GEN-LAST:event_lblAdicionarNovoTreinoMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogNovoTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogNovoTreino dialog = new JDialogNovoTreino(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluirTreino;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btncancelarInclusao;
    private javax.swing.JComboBox<String> cbxExercicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelInclusaoTreino;
    private javax.swing.JPanel jPanelTreinos;
    private javax.swing.JScrollPane jScrollPaneTabelaTreino;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAdicionarNovoTreino;
    private javax.swing.JLabel lblAdicionarOuEditarTreino;
    private javax.swing.JLabel lblEmailAluno;
    private javax.swing.JLabel lblFicha;
    private javax.swing.JLabel lblNomeAluno;
    private javax.swing.JLabel lblTreino;
    private javax.swing.JLabel lblTreinoTabela;
    private javax.swing.JLabel lblValidade;
    private javax.swing.JPanel painelDeInformacao;
    private javax.swing.JPanel painelDeInformacao1;
    private javax.swing.JSpinner spnPeso;
    private javax.swing.JSpinner spnRepeticoes;
    private javax.swing.JSpinner spnSeries;
    private javax.swing.JTable tblTreino;
    // End of variables declaration//GEN-END:variables
}
