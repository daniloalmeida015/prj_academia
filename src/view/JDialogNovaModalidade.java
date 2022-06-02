/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CustomTableCellRenderer;
import model.Horario;
import model.Modalidade;
import org.hibernate.Session;

/**
 *
 * @author Danilo
 */
public class JDialogNovaModalidade extends javax.swing.JDialog {

    private Session sessao;
    
    private Modalidade modalidade;
    private Horario horario;
    private List<Modalidade> listaModalidade;
    private List<Horario> listaHorario;
    private DefaultTableModel dtm;
    private DefaultTableModel dtmHorario;
    
    public CustomTableCellRenderer cellRenderer;
    
    private int linhaSelecionada;
    
    public JDialogNovaModalidade(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public JDialogNovaModalidade(java.awt.Frame parent, boolean modal, Session sessao, Modalidade modalidade) {
        super(parent, modal);
        initComponents();
        
        this.sessao = sessao;
        this.modalidade = modalidade;
        
        dtmHorario = (DefaultTableModel)tblHorario.getModel();
        listaHorario = new ArrayList<>();
        
        //##ALTERANDO A TABELA
            tblHorario.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
            tblHorario.setBackground(Color.white);
            tblHorario.getParent().setBackground(painelHorarios.getBackground());
            tblHorario.setShowGrid(true);
            tblHorario.setShowHorizontalLines(true);
            tblHorario.setShowVerticalLines(false);
            
            cellRenderer = new CustomTableCellRenderer();
            
            tblHorario.getColumn("DIA DA SEMANA").setCellRenderer(cellRenderer);
            tblHorario.getColumn("ENTRADA").setCellRenderer(cellRenderer);
            tblHorario.getColumn("SAIDA").setCellRenderer(cellRenderer);
         //## FIM EDITANDO A TABELA ---------------
            
        //##FIM ALTERAÇÃO TABELA
        
        if(modalidade != null){
            setarCamposFormulario();
            lblNovaModalidade.setText("Editar modalidade");
        }else{
            lblNovaModalidade.setText("Nova modalidade");
        }
        
        
    }
    
    private void setarCamposFormulario(){
        
        //SETA OS CAMPOS DO FORMULARIO
            txtPlano.setText(modalidade.getPlano());
            txtModalidade.setText(modalidade.getDescricao());
            spnDiaVencimento.setValue(modalidade.getDiaDoVencimento());
            spnPreco.setValue(modalidade.getPreco());
            spnVagas.setValue(modalidade.getVagas());

            horario = new Horario();

            listaHorario = sessao.createQuery("from Horario where modalidade_id = '"+modalidade.getId()+"'").list();
            
            dtmHorario.setRowCount(0);
            for(int i=0; i<listaHorario.size(); i++){
                horario= listaHorario.get(i);
                dtmHorario.addRow(horario.montarLinha());
            }
    }
    
    
    public void adicionarHorario(){
        
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            horario = new Horario();
            Date h = new Date();

            try {
                 h = sdf.parse(jtxtHoraEntrada.getText());
                 horario.setHoraInicio(h);

                 h = sdf.parse(jtxtHoraSaida.getText());
                 horario.setHoraFim(h);

                 horario.setDiaDaSemana(cbxDiaSemana.getSelectedItem().toString().substring(0, 3));

                 listaHorario.add(horario);
                 dtmHorario.addRow(horario.montarLinha());

            } catch (ParseException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    private void cadastrar(){
        
        modalidade=new Modalidade(txtModalidade.getText(),
                                  BigDecimal.valueOf((Double)spnPreco.getValue()),
                                  txtPlano.getText(),
                                  (byte)spnDiaVencimento.getValue(),
                                  (Short) spnVagas.getValue());
        
        try{
            
            sessao.beginTransaction();
          
                sessao.save(modalidade);
                for(int i =0; i < listaHorario.size(); i++){
                    horario= listaHorario.get(i);
                    horario.setModalidade(modalidade);
                    sessao.save(horario);
                    //JOptionPane.showMessageDialog(this, "Salvou "+i);
                }
                
            sessao.getTransaction().commit();
            
            JOptionPane.showMessageDialog(this, "Registro salvo com sucesso!");
            
        } catch(Exception erro){
            JOptionPane.showMessageDialog(this,"ERRO AO SALVAR: "+ erro.getMessage());
            sessao.getTransaction().rollback();
        }
    }
    
    public void atualizar(){
        
        try {
            sessao.beginTransaction();
            
                for(int i =0; i < listaHorario.size(); i++){
                        horario= listaHorario.get(i);
                        horario.setModalidade(modalidade);
                        sessao.merge(horario);
                }
                
               BigDecimal preco = new BigDecimal(spnPreco.getValue().toString());

               modalidade.setDescricao(txtModalidade.getText());
               modalidade.setPreco(preco);
               modalidade.setDiaDoVencimento((byte)spnDiaVencimento.getValue());
               modalidade.setPlano(txtPlano.getText());
               modalidade.setVagas((Short) spnVagas.getValue());

               sessao.merge(modalidade); 
           
           sessao.getTransaction().commit();
           
            JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
             
        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "ERRO AO SALVAR!\n"+ erro.getMessage());
            sessao.getTransaction().rollback();
        }
     
    }
    
    private void deletarHorario(){
        try{
            
            sessao.beginTransaction();
            sessao.delete(horario);

            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Registro deletado com sucesso!");
            listaHorario.remove(horario);
            dtmHorario.removeRow(linhaSelecionada);
            
        } catch(Exception erro){

            JOptionPane.showMessageDialog(this, "Erro ao EXCLUIR as informações!"+"\n"+erro+"\n Causa: "+erro.getCause());
            sessao.getTransaction().rollback();
        }
    }
    
    
    private void atualizarTabela(){
        int aux = listaModalidade.size()-1;
        novaLinha(aux);
    }
    
    private void novaLinha(int aux){
        Modalidade m = listaModalidade.get(aux);
        dtm.addRow(m.montarLinha());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCadModalidade = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblNovaModalidade = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtModalidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        spnPreco = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        spnDiaVencimento = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spnVagas = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        painelHorarios = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblHorario = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        cbxDiaSemana = new javax.swing.JComboBox<>();
        jtxtHoraEntrada = new javax.swing.JFormattedTextField();
        jtxtHoraSaida = new javax.swing.JFormattedTextField();
        btnDeletarHorario = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btncancelar = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanelCadModalidade.setBackground(new java.awt.Color(250, 250, 250));

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Modalidades >");

        lblNovaModalidade.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaModalidade.setForeground(new java.awt.Color(102, 102, 102));
        lblNovaModalidade.setText("Nova modalidade");

        jPanel3.setBackground(new java.awt.Color(242, 242, 242));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Descrição:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Plano:");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Preço:");

        spnPreco.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Vencimento:");

        spnDiaVencimento.setModel(new javax.swing.SpinnerNumberModel(Byte.valueOf((byte)1), Byte.valueOf((byte)1), Byte.valueOf((byte)30), Byte.valueOf((byte)1)));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Vagas:");

        spnVagas.setModel(new javax.swing.SpinnerNumberModel((short)1, (short)1, null, (short)1));

        jPanel1.setBackground(new java.awt.Color(31, 158, 150));

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Modalidade:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtModalidade, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPlano, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(spnPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(spnDiaVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnVagas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)))))
                        .addContainerGap())))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtModalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnDiaVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnVagas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        painelHorarios.setBackground(new java.awt.Color(242, 242, 242));
        painelHorarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jSeparator1.setBackground(new java.awt.Color(250, 250, 250));

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel22.setText("Dia da Semana:");

        jLabel26.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel26.setText("Entrada:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAdicionar2.png"))); // NOI18N
        btnAdicionar.setText("adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        jScrollPane8.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane8.setBorder(null);

        tblHorario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DIA DA SEMANA", "ENTRADA", "SAIDA"
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
        tblHorario.setGridColor(new java.awt.Color(204, 204, 204));
        tblHorario.setRowHeight(30);
        tblHorario.setSelectionBackground(new java.awt.Color(233, 236, 239));
        tblHorario.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane8.setViewportView(tblHorario);

        jLabel27.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel27.setText("Saída:");

        cbxDiaSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado" }));

        try {
            jtxtHoraEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jtxtHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnDeletarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconDelete20x20.png"))); // NOI18N
        btnDeletarHorario.setText("remover");
        btnDeletarHorario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarHorarioActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(31, 158, 150));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Horários de aula:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelHorariosLayout = new javax.swing.GroupLayout(painelHorarios);
        painelHorarios.setLayout(painelHorariosLayout);
        painelHorariosLayout.setHorizontalGroup(
            painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelHorariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(painelHorariosLayout.createSequentialGroup()
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(cbxDiaSemana, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addComponent(jtxtHoraEntrada))
                        .addGap(18, 18, 18)
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtHoraSaida)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDeletarHorario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelHorariosLayout.setVerticalGroup(
            painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelHorariosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelHorariosLayout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelHorariosLayout.createSequentialGroup()
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxDiaSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelHorariosLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtHoraSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btncancelar.setBackground(new java.awt.Color(255, 51, 51));
        btncancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncancelar.setForeground(new java.awt.Color(255, 255, 255));
        btncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCancelarBranco16x16.png"))); // NOI18N
        btncancelar.setText("Cancelar");
        btncancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        btnCadastrar.setBackground(new java.awt.Color(31, 158, 150));
        btnCadastrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnCadastrar.setText("Salvar");
        btnCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNovaModalidade)
                        .addGap(734, 734, 734))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btncancelar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCadastrar))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(painelHorarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(25, 25, 25))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNovaModalidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelHorarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCadModalidadeLayout = new javax.swing.GroupLayout(jPanelCadModalidade);
        jPanelCadModalidade.setLayout(jPanelCadModalidadeLayout);
        jPanelCadModalidadeLayout.setHorizontalGroup(
            jPanelCadModalidadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelCadModalidadeLayout.setVerticalGroup(
            jPanelCadModalidadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 971, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanelCadModalidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanelCadModalidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed

        if(modalidade == null){
            cadastrar();
        } else{
            atualizar();
        }

        dispose();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        //ADICIONAR HORARIO A MODALIDADE
        adicionarHorario();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnDeletarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarHorarioActionPerformed

        linhaSelecionada = tblHorario.getSelectedRow();

        if(linhaSelecionada==-1){
            JOptionPane.showMessageDialog(this, "Selecione um Horário primeiro!");

        } else{
            JOptionPane.showMessageDialog(this, "Linha Selecionada:: "+linhaSelecionada);

            horario = new Horario();
            horario = listaHorario.get(linhaSelecionada);

            JOptionPane.showMessageDialog(this, "ID: "+horario.getId());

            if(horario.getId()!=null){
                deletarHorario();
            }
        }

    }//GEN-LAST:event_btnDeletarHorarioActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        dispose();
       
    }//GEN-LAST:event_btncancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogNovaModalidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogNovaModalidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogNovaModalidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogNovaModalidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogNovaModalidade dialog = new JDialogNovaModalidade(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDeletarHorario;
    private javax.swing.JButton btncancelar;
    private javax.swing.JComboBox<String> cbxDiaSemana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCadModalidade;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JFormattedTextField jtxtHoraEntrada;
    private javax.swing.JFormattedTextField jtxtHoraSaida;
    private javax.swing.JLabel lblNovaModalidade;
    private javax.swing.JPanel painelHorarios;
    private javax.swing.JSpinner spnDiaVencimento;
    private javax.swing.JSpinner spnPreco;
    private javax.swing.JSpinner spnVagas;
    private javax.swing.JTable tblHorario;
    private javax.swing.JTextField txtModalidade;
    private javax.swing.JTextField txtPlano;
    // End of variables declaration//GEN-END:variables
}
