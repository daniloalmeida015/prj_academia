
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.CustomTableCellRenderer;
import model.Matricula;
import model.MatriculaId;
import model.Modalidade;
import org.hibernate.Query;
import org.hibernate.Session;

/** * * @author Danilo */
public class JDialogMatricula extends javax.swing.JDialog {

    private List<Matricula> listaMatricula;
    private List<Modalidade> listaModalidade;
    private DefaultTableModel dtmAlunoModalidade;
    private Session sessao;
    private Aluno aluno;
    private Matricula matricula;
    private Modalidade modalidade;
    private int linhaSelecionada;
    private DefaultComboBoxModel dcmModalidade;
    public CustomTableCellRenderer cellRenderer;
    
    
    public JDialogMatricula(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
     public JDialogMatricula(java.awt.Frame parent, boolean modal, Aluno aluno, Session sessao) {
        super(parent, modal);
        initComponents();
        
        this.sessao = sessao;
        this.aluno = aluno;
        
        listaMatricula = new ArrayList<>();
        
        dcmModalidade= (DefaultComboBoxModel)cbxModalidade.getModel();
        dtmAlunoModalidade = (DefaultTableModel) tblAlunoModalidade.getModel();
        
        //##ALTERANDO A TABELA
            tblAlunoModalidade.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblAlunoModalidade.setBackground(Color.white);
            tblAlunoModalidade.getParent().setBackground(jPanel2.getBackground());
            tblAlunoModalidade.setShowGrid(true);
            tblAlunoModalidade.setShowHorizontalLines(true);
            tblAlunoModalidade.setShowVerticalLines(false);
            
            cellRenderer = new CustomTableCellRenderer();
            
            tblAlunoModalidade.getColumn("ALUNO").setCellRenderer(cellRenderer);
            tblAlunoModalidade.getColumn("MODALIDADE").setCellRenderer(cellRenderer);
         //## FIM EDITANDO A TABELA ---------------
        
        
        if(this.aluno != null){
            popularTabelaAlunoModalidade();
        }
        lblAluno.setText(aluno.getPessoafisica().getPessoa().getNome());
        
        popularComboModalidade();
        
        painelMatricula.setEnabledAt(0, true);
        painelMatricula.setEnabledAt(1, false);
     }
     
     private void popularComboModalidade(){
        
        listaModalidade= sessao.createQuery("from Modalidade").list();
        
        try{
            for(int i=0; i< listaModalidade.size(); i++){
                dcmModalidade.addElement(listaModalidade.get(i).getDescricao());
            }
        } catch(Exception ex){
            
        }
        
    }
     
    private void popularTabelaAlunoModalidade(){
      
        Matricula m = new Matricula();
        
        Query consulta= sessao.createQuery("from Matricula where aluno_id = :alunoId");
        consulta.setParameter("alunoId", aluno.getPessoaFisicaId());
        
        listaMatricula = consulta.list();
        
        for(int aux=0; aux <listaMatricula.size(); aux++){
            m = listaMatricula.get(aux);

            String[] resp={m.getAluno().getPessoafisica().getPessoa().getNome(),
            m.getModalidade().getDescricao()};

            dtmAlunoModalidade.addRow(resp);   
        }
    }
    
    
    private void matricularAluno(){
        
        matricula = new Matricula(aluno, modalidade, "ativo", new Date());

        MatriculaId matriculaId = new MatriculaId(aluno.getPessoaFisicaId(), modalidade.getId());
        
        matricula.setId(matriculaId);
        
       
        try{
            
            sessao.beginTransaction();
            sessao.save(matricula);
            sessao.getTransaction().commit();
            
            JOptionPane.showMessageDialog(this,"Aluno Matriculado com sucesso!");
            
            listaMatricula.add(matricula);
            String[] resp={matricula.getAluno().getPessoafisica().getPessoa().getNome(),matricula.getModalidade().getDescricao()};
            dtmAlunoModalidade.addRow(resp);
            
         } catch(Exception erro){
          
             JOptionPane.showMessageDialog(this,"Erro ao matricular.!\n"+ erro.getMessage());
             sessao.getTransaction().rollback();
         }
    }
    
   private void deletarMatricula(){
       
       String descricao= tblAlunoModalidade.getValueAt(linhaSelecionada, 1).toString();
        
       modalidade = (Modalidade) sessao.createQuery("from Modalidade where descricao = '"+descricao+"'").list().get(0);
       
       matricula = (Matricula) sessao.createQuery("from Matricula where aluno_id = "+aluno.getPessoafisica().getPessoa().getId()+
                    "and modalidade_id = "+modalidade.getId()).list().get(0);
       
       
        try{
            sessao.beginTransaction();            
                sessao.delete(matricula);
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Registro deletado com sucesso!");
            listaMatricula.remove(matricula);
            dtmAlunoModalidade.removeRow(linhaSelecionada);
        } catch(Exception erro){

            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!"+"\n"+erro+"\n Causa: "+erro.getCause());
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
        painelMatricula = new javax.swing.JTabbedPane();
        jPanelMatricula = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAlunoModalidade = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jPanelEditarMatricula = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbxModalidade = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        txtPreco = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDiaVencimento = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtVagas = new javax.swing.JTextField();
        btnCancela = new javax.swing.JButton();
        btnMatricular = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblAluno = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Matrículas do aluno");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        painelMatricula.setBackground(new java.awt.Color(255, 255, 255));
        painelMatricula.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        painelMatricula.setOpaque(true);
        painelMatricula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                painelMatriculaMouseClicked(evt);
            }
        });

        jPanelMatricula.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(242, 242, 242));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane2.setBorder(null);

        tblAlunoModalidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ALUNO", "MODALIDADE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlunoModalidade.setGridColor(new java.awt.Color(204, 204, 204));
        tblAlunoModalidade.setRowHeight(40);
        tblAlunoModalidade.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblAlunoModalidade.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblAlunoModalidade);

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelMatriculaLayout = new javax.swing.GroupLayout(jPanelMatricula);
        jPanelMatricula.setLayout(jPanelMatriculaLayout);
        jPanelMatriculaLayout.setHorizontalGroup(
            jPanelMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMatriculaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMatriculaLayout.setVerticalGroup(
            jPanelMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMatriculaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelMatricula.addTab("Matriculas", jPanelMatricula);

        jPanelEditarMatricula.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("Modalidade:");

        cbxModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxModalidadeActionPerformed(evt);
            }
        });

        jLabel21.setText("Preço:");

        txtPreco.setEditable(false);
        txtPreco.setEnabled(false);

        jLabel17.setText("Plano:");

        txtPlano.setEditable(false);
        txtPlano.setEnabled(false);

        jLabel18.setText("Dia vencimento:");

        txtDiaVencimento.setEditable(false);
        txtDiaVencimento.setEnabled(false);

        jLabel22.setText("Vagas:");

        txtVagas.setEditable(false);
        txtVagas.setEnabled(false);

        btnCancela.setBackground(new java.awt.Color(31, 158, 150));
        btnCancela.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancela.setForeground(new java.awt.Color(255, 255, 255));
        btnCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconVoltarBranco16x16.png"))); // NOI18N
        btnCancela.setText("Voltar");
        btnCancela.setToolTipText("");
        btnCancela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancela.setIconTextGap(8);
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        btnMatricular.setBackground(new java.awt.Color(31, 158, 150));
        btnMatricular.setForeground(new java.awt.Color(255, 255, 255));
        btnMatricular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnMatricular.setText("Matricular");
        btnMatricular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMatricular.setIconTextGap(8);
        btnMatricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatricularActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(31, 158, 150));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dados da Matrícula");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(288, 288, 288))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxModalidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(txtPreco))
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtDiaVencimento)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVagas)
                                    .addComponent(txtPlano)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17))
                                        .addGap(0, 190, Short.MAX_VALUE)))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancela)
                .addGap(18, 18, 18)
                .addComponent(btnMatricular)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbxModalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addGap(2, 2, 2)
                        .addComponent(txtDiaVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(2, 2, 2)
                        .addComponent(txtVagas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancela, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMatricular, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEditarMatriculaLayout = new javax.swing.GroupLayout(jPanelEditarMatricula);
        jPanelEditarMatricula.setLayout(jPanelEditarMatriculaLayout);
        jPanelEditarMatriculaLayout.setHorizontalGroup(
            jPanelEditarMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarMatriculaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelEditarMatriculaLayout.setVerticalGroup(
            jPanelEditarMatriculaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarMatriculaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelMatricula.addTab("Nova Matrícula", jPanelEditarMatricula);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Matrículas >");

        lblAluno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAluno.setForeground(new java.awt.Color(102, 102, 102));
        lblAluno.setText("Aluno");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelMatricula)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAluno)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator5))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblAluno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
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
    }// </editor-fold>//GEN-END:initComponents

    private void cbxModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxModalidadeActionPerformed
        int selecionado=cbxModalidade.getSelectedIndex();
        modalidade=listaModalidade.get(selecionado);
        
        txtPreco.setText(String.valueOf(listaModalidade.get(selecionado).getPreco()));
        txtPlano.setText(listaModalidade.get(selecionado).getPlano());
        txtDiaVencimento.setText(String.valueOf(listaModalidade.get(selecionado).getDiaDoVencimento()));
        txtVagas.setText(String.valueOf(listaModalidade.get(selecionado).getVagas()));
    }//GEN-LAST:event_cbxModalidadeActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
                
        painelMatricula.setSelectedComponent(jPanelMatricula);
        painelMatricula.setEnabledAt(0, true);
        painelMatricula.setEnabledAt(1, false);
    }//GEN-LAST:event_btnCancelaActionPerformed

    private void btnMatricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatricularActionPerformed
        matricularAluno();
        painelMatricula.setSelectedComponent(jPanelMatricula);
        painelMatricula.setEnabledAt(0, true);
        painelMatricula.setEnabledAt(1, false);
    }//GEN-LAST:event_btnMatricularActionPerformed

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setBackground(Color.gray);
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnNovoMouseExited

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        painelMatricula.setSelectedComponent(jPanelEditarMatricula);
        painelMatricula.setEnabledAt(0, false);
        painelMatricula.setEnabledAt(1, true);
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(Color.gray);
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        linhaSelecionada= tblAlunoModalidade.getSelectedRow();
       
        if(linhaSelecionada==-1){
            JOptionPane.showMessageDialog(this, "Selecione um registro!");
        } else{
            //Integer id = Integer.valueOf(tblModalidade.getValueAt(linhaSelecionada, 0).toString());
            //modalidade= (Modalidade) sessao.get(Modalidade.class, id);
            deletarMatricula();
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void painelMatriculaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelMatriculaMouseClicked
    }//GEN-LAST:event_painelMatriculaMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogMatricula dialog = new JDialogMatricula(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnMatricular;
    private javax.swing.JButton btnNovo;
    private javax.swing.JComboBox<String> cbxModalidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelEditarMatricula;
    private javax.swing.JPanel jPanelMatricula;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblAluno;
    private javax.swing.JTabbedPane painelMatricula;
    private javax.swing.JTable tblAlunoModalidade;
    private javax.swing.JTextField txtDiaVencimento;
    private javax.swing.JTextField txtPlano;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JTextField txtVagas;
    // End of variables declaration//GEN-END:variables
}
