package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.Avaliacaofisica;
import model.CustomTableCellRenderer;
import model.Exercicio;
import model.Fichatreino;
import model.Professor;
import model.Treino;
import org.hibernate.Session;

/** * * @author Danilo */

public class IFrmFichaTreino extends javax.swing.JInternalFrame {
    
    private Session sessao;
    int idSelecionado;
    int linhaSelecionada;
    
    public Aluno aluno;
    public Professor professor;
    private Fichatreino fichaTreino;
    private Treino treino;
    
    private List<Aluno> listaAluno;
    private List<Fichatreino> listaFichaTreino;
    private List<Treino> listaTreino;
    
    private DefaultListModel dlmAluno;
    
    private DefaultComboBoxModel dcmFichasTreino;
    private JDialogFichaTreino1 jdFichaTreino;
    
    private DefaultTableModel dtm;
    private DefaultTableModel dtmAlunos;
    
    public CustomTableCellRenderer cellRenderer;
    
    public IFrmFichaTreino() {
        initComponents();
    }
    
    public IFrmFichaTreino(Session sessao, Aluno aluno) {
        initComponents();
        this.sessao=sessao;
        this.aluno=aluno;
        
        //painelDeTreinos.setVisible(false);
        
        dtm = (DefaultTableModel) tblFichas.getModel();
        
        
        listaAluno = sessao.createQuery("from Aluno").list();
        listaTreino = sessao.createQuery("from Treino").list();
        listaFichaTreino = new ArrayList<>();
        
        dlmAluno= new DefaultListModel();
        
        paineDeInformacao.setVisible(false);
        
        popularTabela();
        
        if(this.aluno != null){
            txtPesquisa.setText(aluno.getPessoafisica().getPessoa().getNome());
            pesquisar();
        }
        
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior
         // this.setBorder(null);//retirar bordas
         //txtPesquisa.requestFocus();
         
         //##ALTERANDO A TABELA
            tblFichas.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblFichas.setBackground(Color.white);
            
            tblFichas.getParent().setBackground(painelFichas.getBackground());
            tblFichas.setShowGrid(true);
            tblFichas.setShowHorizontalLines(true);
            tblFichas.setShowVerticalLines(false);
            
            
            cellRenderer = new CustomTableCellRenderer();
            
            tblFichas.getColumn("ALUNO").setCellRenderer(cellRenderer);
            tblFichas.getColumn("FICHA").setCellRenderer(cellRenderer);
            tblFichas.getColumn("PROFESSOR").setCellRenderer(cellRenderer);
            tblFichas.getColumn("INICIO").setCellRenderer(cellRenderer);
            tblFichas.getColumn("VALIDADE").setCellRenderer(cellRenderer);
        //##FIM ALTERAÇÃO TABELA

        
    }

    
    
    private boolean temTreino(String treino){
        
        boolean temTreino = false;
        
        for(int i=0; i<listaTreino.size(); i++){
                
            if(Objects.equals(listaTreino.get(i).getFichatreino().getId(), fichaTreino.getId())
               && listaTreino.get(i).getNome().equals(treino)){
                    
                    temTreino = true;
                    break;
            }
        }
        
        return temTreino;
    }
    
    private void popularTabela(){     
        
        limpar();
        listaFichaTreino.clear();
        dtm.setRowCount(0);
        
        List<Fichatreino> lista = sessao.createQuery("from Fichatreino").list();
        
        if(checkMostrarFicha.isSelected()){
            for(int i= lista.size()-1; i >=0; i--){

                boolean tem = false;
                if(i == lista.size()-1){
                    listaFichaTreino.add(lista.get(i));
                }else{
                    for(int j = listaFichaTreino.size()-1; j >=0; j--){

                        if(listaFichaTreino.get(j).getAluno().getPessoafisica().getPessoa().getId().equals(lista.get(i).getAluno().getPessoafisica().getPessoa().getId())){
                            tem = true;
                        }
                    }

                    if(!tem){
                        listaFichaTreino.add(lista.get(i));
                    }
                }
            }
            
            Collections.reverse(listaFichaTreino);
            
        } else{
            listaFichaTreino = lista;
        }
        
        
        //listaFichaTreino = sessao.createQuery("from Fichatreino").list();
            
        if(listaFichaTreino!=null && listaFichaTreino.size() >0){
            for(int aux=0; aux <listaFichaTreino.size(); aux++){
                
                novaLinha(aux);
            }
        } else{
            paineDeInformacao.setVisible(true);
            lblInformacao.setText("Nenhuma Ficha encontrada");
            
            jScrollPane1.setVisible(false);
            tblFichas.setVisible(false);
        }
    }
    
    private void novaLinha(int aux){
        Fichatreino f = listaFichaTreino.get(aux);
        dtm.addRow(f.montarLinha());
    }
    
    
    
    
    
    
    
    private void popularFichasTreino(){
        
        String data="";
        listaFichaTreino.clear();
//        dcmFichasTreino.removeAllElements();
        
            try {
                
                listaFichaTreino = sessao.createQuery("from Fichatreino where aluno_id = "+aluno.getPessoafisica().getPessoa().getId()).list();
                
                if (listaFichaTreino.size() > 0) {
                    //JOptionPane.showMessageDialog(this,"Lista::"+listaFichaTreino.size());
                    for (int i = 0; i < listaFichaTreino.size(); i++) {
                        
                        data = DateFormat.getDateInstance().format(listaFichaTreino.get(i).getDataInicio());
                        //dcmFichasTreino.addElement(data);
                    }
                    //dcmFichasTreino.setSelectedItem(data);
                } else{
                    //JOptionPane.showMessageDialog(this,"Lista::"+listaFichaTreino.size());
                    //dcmFichasTreino.addElement("Nenhuma Ficha");
                    //dcmFichasTreino.setSelectedItem("Nenhuma Ficha");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: "+e.getMessage());
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
    
    
    
    public void limpar(){
        
        fichaTreino = null;
        //aluno = null;
       
        btnTreinoA.setBackground(new Color(245,245,245));
        btnTreinoB.setBackground(new Color(245,245,245));
        btnTreinoC.setBackground(new Color(245,245,245));
        btnTreinoD.setBackground(new Color(245,245,245));
        
        Color semFichaCadastrada = btnSemFichaCadastrada.getBackground();
        btnTreinoA.setForeground(semFichaCadastrada);
        btnTreinoB.setForeground(semFichaCadastrada);
        btnTreinoC.setForeground(semFichaCadastrada);
        btnTreinoD.setForeground(semFichaCadastrada);

        btnTreinoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png")));
        btnTreinoB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png")));
        btnTreinoC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png")));
        btnTreinoD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png")));
    }
    
    
    private void popularTreinos(){
        linhaSelecionada= tblFichas.getSelectedRow();
        
        if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblFichas.getValueAt(linhaSelecionada, 1).toString());
            fichaTreino = (Fichatreino) sessao.get(Fichatreino.class, id);

                Color comTreino = btnTreinoCadastrado.getBackground(),
                semTreino = btnTreinoNaoCadastrado.getBackground();
                
                if(temTreino("Treino A")){
                    btnTreinoA.setForeground(comTreino);
                    btnTreinoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSelecionadoRedondo20x20.png")));
                }else{
                    btnTreinoA.setForeground(semTreino);
                    btnTreinoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconNaoSelecionado20x20.png")));
                }

                if(temTreino("Treino B")){
                    btnTreinoB.setForeground(comTreino);
                    btnTreinoB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSelecionadoRedondo20x20.png")));
                }else{
                    btnTreinoB.setForeground(semTreino);
                    btnTreinoB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconNaoSelecionado20x20.png")));
                }
                
                if(temTreino("Treino C")){
                    btnTreinoC.setForeground(comTreino);
                    btnTreinoC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSelecionadoRedondo20x20.png")));
                }else{
                    btnTreinoC.setForeground(semTreino);
                    btnTreinoC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconNaoSelecionado20x20.png")));
                }
                
                if(temTreino("Treino D")){
                    btnTreinoD.setForeground(comTreino);
                    btnTreinoD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSelecionadoRedondo20x20.png")));
                }else{
                    btnTreinoD.setForeground(semTreino);
                    btnTreinoD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconNaoSelecionado20x20.png")));
                }            
        }
    }
    
    
    
    public void recuperarAluno(Aluno a){
        
        this.aluno = a;
    }
    
    
    private void pesquisar(){
        
        listaFichaTreino.clear();
        dtm.setRowCount(0);        
        
        List<Fichatreino> lista = sessao.createQuery("from Fichatreino").list();
        
        if(checkMostrarFicha.isSelected()){
            
            for(int i= lista.size()-1; i >=0; i--){

                boolean tem = false;
                if(i == lista.size()-1){
                    listaFichaTreino.add(lista.get(i));
                }else{
                    for(int j = listaFichaTreino.size()-1; j >=0; j--){

                        if(listaFichaTreino.get(j).getAluno().getPessoafisica().getPessoa().getId().equals(lista.get(i).getAluno().getPessoafisica().getPessoa().getId())){
                            tem = true;
                        }
                    }

                    if(!tem){
                        listaFichaTreino.add(lista.get(i));
                    }
                }
            }
            
        } else{
            listaFichaTreino = lista;
        }
        
            if(listaFichaTreino!=null && listaFichaTreino.size() >0){
                for(int aux=0; aux <listaFichaTreino.size(); aux++){

                    if(listaFichaTreino.get(aux).getAluno().getPessoafisica().getPessoa().getId().equals(
                            aluno.getPessoafisica().getPessoa().getId())){
                        
                        novaLinha(aux);
                    }
                }

            }
            txtPesquisa.setText(aluno.getPessoafisica().getPessoa().getNome());
            
            if(dtm.getRowCount() ==0){

                paineDeInformacao.setVisible(true);
                jScrollPane1.setVisible(false);
                tblFichas.setVisible(false);

                String sexo = aluno.getPessoafisica().getSexo();
                
                lblInformacao.setText((sexo.equals("M")?"O aluno ":"A aluna " )+"\""+txtPesquisa.getText()+"\" não possui Fichas");
                limpar();
                
            } else{
                paineDeInformacao.setVisible(false);
                jScrollPane1.setVisible(true);
                tblFichas.setVisible(true);
                limpar();
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
        jPanelFichaDoAluno = new javax.swing.JPanel();
        painelDeTreinos = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnTreinoA = new javax.swing.JButton();
        btnTreinoB = new javax.swing.JButton();
        btnTreinoC = new javax.swing.JButton();
        btnTreinoD = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btnSemFichaCadastrada = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btnTreinoCadastrado = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnTreinoNaoCadastrado = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        painelFichas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnRelatorio = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFichas = new javax.swing.JTable();
        paineDeInformacao = new javax.swing.JPanel();
        lblInformacao = new javax.swing.JLabel();
        lblNovaFicha = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        btnFechar = new javax.swing.JButton();
        checkMostrarFicha = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFocusable(false);

        jPanelFichaDoAluno.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFichaDoAluno.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanelFichaDoAluno.setFocusable(false);

        painelDeTreinos.setBackground(new java.awt.Color(255, 255, 255));
        painelDeTreinos.setOpaque(false);

        jLabel14.setBackground(new java.awt.Color(31, 158, 150));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("  Treinos (ABCD)");
        jLabel14.setOpaque(true);

        btnTreinoA.setBackground(new java.awt.Color(250, 250, 250));
        btnTreinoA.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        btnTreinoA.setForeground(new java.awt.Color(153, 153, 153));
        btnTreinoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png"))); // NOI18N
        btnTreinoA.setText("Treino A");
        btnTreinoA.setContentAreaFilled(false);
        btnTreinoA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTreinoA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTreinoA.setIconTextGap(8);
        btnTreinoA.setOpaque(true);
        btnTreinoA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTreinoAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTreinoAMouseExited(evt);
            }
        });
        btnTreinoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTreinoAActionPerformed(evt);
            }
        });

        btnTreinoB.setBackground(new java.awt.Color(250, 250, 250));
        btnTreinoB.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        btnTreinoB.setForeground(new java.awt.Color(153, 153, 153));
        btnTreinoB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png"))); // NOI18N
        btnTreinoB.setText("Treino B");
        btnTreinoB.setBorderPainted(false);
        btnTreinoB.setContentAreaFilled(false);
        btnTreinoB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTreinoB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTreinoB.setIconTextGap(8);
        btnTreinoB.setOpaque(true);
        btnTreinoB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTreinoBMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTreinoBMouseExited(evt);
            }
        });
        btnTreinoB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTreinoBActionPerformed(evt);
            }
        });

        btnTreinoC.setBackground(new java.awt.Color(250, 250, 250));
        btnTreinoC.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        btnTreinoC.setForeground(new java.awt.Color(153, 153, 153));
        btnTreinoC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png"))); // NOI18N
        btnTreinoC.setText("Treino C");
        btnTreinoC.setBorderPainted(false);
        btnTreinoC.setContentAreaFilled(false);
        btnTreinoC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTreinoC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTreinoC.setIconTextGap(8);
        btnTreinoC.setOpaque(true);
        btnTreinoC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTreinoCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTreinoCMouseExited(evt);
            }
        });
        btnTreinoC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTreinoCActionPerformed(evt);
            }
        });

        btnTreinoD.setBackground(new java.awt.Color(250, 250, 250));
        btnTreinoD.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        btnTreinoD.setForeground(new java.awt.Color(153, 153, 153));
        btnTreinoD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png"))); // NOI18N
        btnTreinoD.setText("Treino D");
        btnTreinoD.setBorderPainted(false);
        btnTreinoD.setContentAreaFilled(false);
        btnTreinoD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTreinoD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTreinoD.setIconTextGap(8);
        btnTreinoD.setOpaque(true);
        btnTreinoD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTreinoDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTreinoDMouseExited(evt);
            }
        });
        btnTreinoD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTreinoDActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOpaque(true);

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator3.setOpaque(true);

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator5.setOpaque(true);

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator6.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        btnSemFichaCadastrada.setBackground(new java.awt.Color(153, 153, 153));
        btnSemFichaCadastrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExclamacao20x20.png"))); // NOI18N
        btnSemFichaCadastrada.setContentAreaFilled(false);

        jLabel20.setText("Sem Ficha selecionada");

        btnTreinoCadastrado.setBackground(new java.awt.Color(31, 158, 150));
        btnTreinoCadastrado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSelecionadoRedondo20x20.png"))); // NOI18N
        btnTreinoCadastrado.setContentAreaFilled(false);

        jLabel8.setText("Treino já cadastrado");

        btnTreinoNaoCadastrado.setBackground(new java.awt.Color(255, 70, 60));
        btnTreinoNaoCadastrado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconNaoSelecionado20x20.png"))); // NOI18N
        btnTreinoNaoCadastrado.setContentAreaFilled(false);

        jLabel10.setText("Treino não cadastrado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTreinoCadastrado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTreinoNaoCadastrado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSemFichaCadastrada, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSemFichaCadastrada, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTreinoCadastrado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTreinoNaoCadastrado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelDeTreinosLayout = new javax.swing.GroupLayout(painelDeTreinos);
        painelDeTreinos.setLayout(painelDeTreinosLayout);
        painelDeTreinosLayout.setHorizontalGroup(
            painelDeTreinosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTreinoA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTreinoB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(btnTreinoC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addComponent(btnTreinoD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelDeTreinosLayout.setVerticalGroup(
            painelDeTreinosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelDeTreinosLayout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnTreinoA, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTreinoB, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTreinoC, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTreinoD, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        painelFichas.setBackground(new java.awt.Color(242, 242, 242));
        painelFichas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(31, 158, 150));

        btnRelatorio.setBackground(new java.awt.Color(31, 158, 150));
        btnRelatorio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconImprimirBranco20x20.png"))); // NOI18N
        btnRelatorio.setText("Imprimir");
        btnRelatorio.setContentAreaFilled(false);
        btnRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRelatorio.setFocusable(false);
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
        btnExcluir.setFocusable(false);
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
        btnEditar.setFocusable(false);
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

        txtPesquisa.setEditable(false);
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
        });
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(31, 158, 150));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisaBranco.png"))); // NOI18N
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnNovo)
                .addGap(6, 6, 6)
                .addComponent(btnEditar)
                .addGap(6, 6, 6)
                .addComponent(btnRelatorio)
                .addGap(6, 6, 6)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar)
                .addGap(18, 18, 18)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(245, 245, 245));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tblFichas.setAutoCreateRowSorter(true);
        tblFichas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblFichas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#COD", "#COD FICHA", "ALUNO", "FICHA", "PROFESSOR", "INICIO", "VALIDADE"
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
        tblFichas.setToolTipText("Tabela de alunos");
        tblFichas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblFichas.setGridColor(new java.awt.Color(204, 204, 204));
        tblFichas.setRowHeight(40);
        tblFichas.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblFichas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblFichas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblFichas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblFichasFocusLost(evt);
            }
        });
        tblFichas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFichasMouseClicked(evt);
            }
        });
        tblFichas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblFichasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblFichas);
        if (tblFichas.getColumnModel().getColumnCount() > 0) {
            tblFichas.getColumnModel().getColumn(0).setMinWidth(0);
            tblFichas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblFichas.getColumnModel().getColumn(0).setMaxWidth(0);
            tblFichas.getColumnModel().getColumn(1).setMinWidth(0);
            tblFichas.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblFichas.getColumnModel().getColumn(1).setMaxWidth(0);
            tblFichas.getColumnModel().getColumn(3).setMinWidth(100);
            tblFichas.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblFichas.getColumnModel().getColumn(3).setMaxWidth(100);
            tblFichas.getColumnModel().getColumn(5).setMinWidth(100);
            tblFichas.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblFichas.getColumnModel().getColumn(5).setMaxWidth(100);
            tblFichas.getColumnModel().getColumn(6).setMinWidth(100);
            tblFichas.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblFichas.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        paineDeInformacao.setBackground(new java.awt.Color(242, 242, 242));

        lblInformacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInformacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconInfo30x30.png"))); // NOI18N
        lblInformacao.setText("O aluno(a) não possui fichas ...");

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
                .addComponent(lblNovaFicha)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelFichasLayout = new javax.swing.GroupLayout(painelFichas);
        painelFichas.setLayout(painelFichasLayout);
        painelFichasLayout.setHorizontalGroup(
            painelFichasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFichasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(paineDeInformacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelFichasLayout.setVerticalGroup(
            painelFichasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFichasLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(paineDeInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Fichas de Treino");

        jPanel3.setOpaque(false);

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
        checkMostrarFicha.setText("Mostrar somente a última Ficha de cada aluno");
        checkMostrarFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMostrarFichaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(checkMostrarFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(checkMostrarFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanelFichaDoAlunoLayout = new javax.swing.GroupLayout(jPanelFichaDoAluno);
        jPanelFichaDoAluno.setLayout(jPanelFichaDoAlunoLayout);
        jPanelFichaDoAlunoLayout.setHorizontalGroup(
            jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator4)
                    .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                        .addComponent(painelFichas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(painelDeTreinos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        jPanelFichaDoAlunoLayout.setVerticalGroup(
            jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFichaDoAlunoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFichaDoAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelFichas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelDeTreinos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFichaDoAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFichaDoAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnTreinoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTreinoAActionPerformed

        int opcao;
        
        if(fichaTreino != null){

            if(temTreino("Treino A")){
               //popularTabelaTreino("Treino A");
        
                JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao, fichaTreino, "Treino A");
                jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdNovoTreino.setVisible(true);

            } else{

                opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja cadastrar um novo treino? ", "Sem treino cadastrado", JOptionPane.YES_NO_OPTION);

                if(opcao == 0 ){
                    treino = null;
        
                    JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao,fichaTreino, "Treino A");
                    jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                    jdNovoTreino.setVisible(true);
                }
            }
            
            
            listaTreino.clear();
            listaTreino = sessao.createQuery("from Treino").list();
            
            popularTreinos();
            
        } else{
            JOptionPane.showMessageDialog(this, "Primeiro selecione um Aluno e uma Ficha");
        }

    }//GEN-LAST:event_btnTreinoAActionPerformed

    private void btnTreinoBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTreinoBActionPerformed

        int opcao;

        if(fichaTreino != null){

            if(temTreino("Treino B")){
        
                JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao, fichaTreino, "Treino B");
                jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdNovoTreino.setVisible(true);

            } else{

                opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja cadastrar um novo treino? ", "Sem treino cadastrado", JOptionPane.YES_NO_OPTION);

                if(opcao == 0 ){
                    treino = null;
        
                    JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao,fichaTreino, "Treino B");
                    jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                    jdNovoTreino.setVisible(true);
                }
            }
            
            //String fichaSelecionada = (String) dcmFichasTreino.getSelectedItem();
            
            listaTreino.clear();
            listaTreino = sessao.createQuery("from Treino").list();
            popularTreinos();
            
        } else{
            JOptionPane.showMessageDialog(this, "Primeiro selecione um Aluno e uma Ficha");
        }

    }//GEN-LAST:event_btnTreinoBActionPerformed

    private void btnTreinoCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTreinoCActionPerformed

        int opcao;

        if(fichaTreino != null){

            if(temTreino("Treino C")){
        
                JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao, fichaTreino, "Treino C");
                jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdNovoTreino.setVisible(true);

            } else{

                opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja cadastrar um novo treino? ", "Sem treino cadastrado", JOptionPane.YES_NO_OPTION);

                if(opcao == 0 ){
                    treino = null;
        
                    JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao,fichaTreino, "Treino C");
                    jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                    jdNovoTreino.setVisible(true);
                }
            }
            
            //String fichaSelecionada = (String) dcmFichasTreino.getSelectedItem();
            
            listaTreino.clear();
            listaTreino = sessao.createQuery("from Treino").list();
            popularTreinos();
            
        } else{
            JOptionPane.showMessageDialog(this, "Primeiro selecione um Aluno e uma Ficha");
        }
    }//GEN-LAST:event_btnTreinoCActionPerformed

    private void btnTreinoDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTreinoDActionPerformed

        int opcao;

        if(fichaTreino != null){

            if(temTreino("Treino D")){
        
                JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao, fichaTreino, "Treino D");
                jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                jdNovoTreino.setVisible(true);

            } else{

                opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja cadastrar um novo treino? ", "Sem treino cadastrado", JOptionPane.YES_NO_OPTION);

                if(opcao == 0 ){
                    treino = null;
        
                    JDialogNovoTreino jdNovoTreino = new JDialogNovoTreino(new Frame(), true, sessao,fichaTreino, "Treino D");
                    jdNovoTreino.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
                    jdNovoTreino.setVisible(true);
                }
            }
            
            //String fichaSelecionada = (String) dcmFichasTreino.getSelectedItem();
            
            listaTreino.clear();
            listaTreino = sessao.createQuery("from Treino").list();
            popularTreinos();
            
        } else{
            JOptionPane.showMessageDialog(this, "Primeiro selecione um Aluno e uma Ficha");
        }
        
    }//GEN-LAST:event_btnTreinoDActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
        
        paineDeInformacao.setVisible(false);
        jScrollPane1.setVisible(true);
        tblFichas.setVisible(true);
        
        txtPesquisa.setText(" PESQUISAR ALUNO");
        popularTabela();
        
    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        
        if(txtPesquisa.getText().equals("")){
            txtPesquisa.setText(" PESQUISAR ALUNO");
            limpar();
        }
        
        if(dtm.getRowCount() ==0){
            limpar();
            
        }
    
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void txtPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisaMouseClicked
                
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, this, null);
        pesquisarAluno.setVisible(true);
        
        if(aluno != null){
            pesquisar();
        }else{
            popularTabela();
        }
        
        
    }//GEN-LAST:event_txtPesquisaMouseClicked

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        
    }//GEN-LAST:event_txtPesquisaActionPerformed

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

        aluno = null;
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, this, null);
        pesquisarAluno.setVisible(true);
        
        if(aluno != null){
            fichaTreino = null;
            jdFichaTreino = new JDialogFichaTreino1(new Frame(), true,sessao, aluno, fichaTreino);
            jdFichaTreino.setVisible(true);
            
            popularTabela();
            txtPesquisa.requestFocus();
            
            limpar();
            txtPesquisa.setText(" PESQUISAR ALUNO");
            
            paineDeInformacao.setVisible(false);
            jScrollPane1.setVisible(true);
            tblFichas.setVisible(true);
        }
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        linhaSelecionada= tblFichas.getSelectedRow();
        
        if(linhaSelecionada >=0){
            
           Integer id = Integer.valueOf(tblFichas.getValueAt(linhaSelecionada, 1).toString());
           fichaTreino = (Fichatreino) sessao.get(Fichatreino.class, id);
           
            int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar a Ficha de Treino do aluno? ", "Excluir Ficha", JOptionPane.YES_NO_OPTION);

            if(opcao==0){
                try {
                    sessao.beginTransaction();
                    sessao.delete(fichaTreino);
                    sessao.getTransaction().commit();

                    JOptionPane.showMessageDialog(this, "Deletado com sucesso!");
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERRO: "+e.getMessage());
                }
                
                if(aluno!=null){
                    limpar();
                    pesquisar();
                }else{
                    popularTabela();
                    limpar();
                    txtPesquisa.setText(" PESQUISAR ALUNO");
                }
                
                aluno = null;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Selecione uma Ficha na tabela ao lado primeiro!");
        }
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(Color.gray);
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        linhaSelecionada= tblFichas.getSelectedRow();
        
        if(linhaSelecionada >=0){
            Integer id = Integer.valueOf(tblFichas.getValueAt(linhaSelecionada, 1).toString());
            fichaTreino = (Fichatreino) sessao.get(Fichatreino.class, id);
            
            id = Integer.valueOf(tblFichas.getValueAt(linhaSelecionada, 0).toString());
            aluno = (Aluno) sessao.get(Aluno.class, id);

            
            jdFichaTreino = new JDialogFichaTreino1(new Frame(), true,sessao, aluno, fichaTreino);
            jdFichaTreino.setVisible(true);
            
            if(!txtPesquisa.getText().equals(" PESQUISAR ALUNO")){
                limpar();
                pesquisar();
            }else{
                popularTabela();
                limpar();
                txtPesquisa.setText(" PESQUISAR ALUNO");
            }
                
            aluno = null;            
            
        } else{
            JOptionPane.showMessageDialog(this, "Primeiro selecione um Aluno");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnLimparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimparMouseEntered
        btnLimpar.setBackground(Color.gray);
    }//GEN-LAST:event_btnLimparMouseEntered

    private void btnLimparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimparMouseExited
        btnLimpar.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnLimparMouseExited

    private void btnTreinoAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoAMouseEntered
       
        btnTreinoA.setBackground(new Color(235,235,235));
       
    }//GEN-LAST:event_btnTreinoAMouseEntered

    private void btnTreinoAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoAMouseExited
        
        btnTreinoA.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnTreinoAMouseExited

    private void btnTreinoBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoBMouseEntered
        btnTreinoB.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnTreinoBMouseEntered

    private void btnTreinoBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoBMouseExited
        btnTreinoB.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnTreinoBMouseExited

    private void btnTreinoCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoCMouseEntered
        btnTreinoC.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnTreinoCMouseEntered

    private void btnTreinoCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoCMouseExited
        btnTreinoC.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnTreinoCMouseExited

    private void btnTreinoDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoDMouseEntered
        btnTreinoD.setBackground(new Color(235,235,235));
    }//GEN-LAST:event_btnTreinoDMouseEntered

    private void btnTreinoDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreinoDMouseExited
        btnTreinoD.setBackground(new Color(250,250,250));
    }//GEN-LAST:event_btnTreinoDMouseExited

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void tblFichasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFichasMouseClicked
        
        popularTreinos();
    }//GEN-LAST:event_tblFichasMouseClicked

    private void tblFichasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblFichasKeyReleased
        popularTreinos();
    }//GEN-LAST:event_tblFichasKeyReleased

    private void tblFichasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFichasFocusLost
                    
            Color semFichaCadastrada = btnSemFichaCadastrada.getBackground();
            btnTreinoA.setForeground(semFichaCadastrada);
            btnTreinoB.setForeground(semFichaCadastrada);
            btnTreinoC.setForeground(semFichaCadastrada);
            btnTreinoD.setForeground(semFichaCadastrada);
        
    }//GEN-LAST:event_tblFichasFocusLost

    private void lblNovaFichaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseEntered
        lblNovaFicha.setFont(new Font("Tahoma", Font.BOLD, 12));
    }//GEN-LAST:event_lblNovaFichaMouseEntered

    private void lblNovaFichaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseExited
        lblNovaFicha.setFont(new Font("Tahoma", Font.PLAIN, 12));
    }//GEN-LAST:event_lblNovaFichaMouseExited

    private void lblNovaFichaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNovaFichaMouseClicked

        aluno = null;
        JDialogPesquisarAluno pesquisarAluno = new JDialogPesquisarAluno(new JFrame(), true, sessao, this, null);
        pesquisarAluno.setVisible(true);
        
        if(aluno != null){
            fichaTreino = null;
            jdFichaTreino = new JDialogFichaTreino1(new Frame(), true,sessao, aluno, fichaTreino);
            jdFichaTreino.setVisible(true);
            
            popularTabela();
            txtPesquisa.requestFocus();
            
            limpar();
            txtPesquisa.setText(" PESQUISAR ALUNO");
            
            paineDeInformacao.setVisible(false);
            jScrollPane1.setVisible(true);
            tblFichas.setVisible(true);
        }
    }//GEN-LAST:event_lblNovaFichaMouseClicked

    private void checkMostrarFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMostrarFichaActionPerformed
        if(txtPesquisa.getText().equals(" PESQUISAR ALUNO")){
            popularTabela();
            limpar();
        }else{
            pesquisar();
            limpar();
        }

    }//GEN-LAST:event_checkMostrarFichaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnSemFichaCadastrada;
    private javax.swing.JButton btnTreinoA;
    private javax.swing.JButton btnTreinoB;
    private javax.swing.JButton btnTreinoC;
    private javax.swing.JButton btnTreinoCadastrado;
    private javax.swing.JButton btnTreinoD;
    private javax.swing.JButton btnTreinoNaoCadastrado;
    private javax.swing.JCheckBox checkMostrarFicha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelFichaDoAluno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblInformacao;
    private javax.swing.JLabel lblNovaFicha;
    private javax.swing.JPanel paineDeInformacao;
    private javax.swing.JPanel painelDeTreinos;
    private javax.swing.JPanel painelFichas;
    private javax.swing.JTable tblFichas;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
