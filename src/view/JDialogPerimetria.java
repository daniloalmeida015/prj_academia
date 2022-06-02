package view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Avaliacaofisica;
import model.Perimetria;
import org.hibernate.Session;

/** ** @author Danilo*/
public class JDialogPerimetria extends javax.swing.JDialog {

   private Session sessao;
   private Avaliacaofisica avaliacaoFisica;
   private Perimetria perimetria;
   private List<Perimetria> listaPerimetria;
   
    public JDialogPerimetria(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public JDialogPerimetria(java.awt.Frame parent, boolean modal, Session sessao, Avaliacaofisica avaliacaoFisica) {
        super(parent, modal);
        initComponents();
        
        this.sessao=sessao;
        this.avaliacaoFisica=avaliacaoFisica;
        listaPerimetria= new ArrayList<>();
        popularCampos();
    }

    
    
    private void popularCampos(){
        
        listaPerimetria =  sessao.createQuery("from Perimetria where id = '"+avaliacaoFisica.getId()+"'").list();
        if(listaPerimetria.size()>0){
            perimetria= listaPerimetria.get(0);
            
            spnPescoco.setValue(perimetria.getPescoco());
            spnBracoDir.setValue(perimetria.getBracoDireito());
            spnBracoEsq.setValue(perimetria.getBracoEsquerdo());
            spnTorax.setValue(perimetria.getTorax());
            spnAbdomem.setValue(perimetria.getAbdomem());
            spnQuadril.setValue(perimetria.getQuadril());
            spnCoxaSupDir.setValue(perimetria.getCoxaSuperiorDireita());
            spnCoxaSupEsq.setValue(perimetria.getCoxaSuperiorEsquerda());
            spnCoxaInfDir.setValue(perimetria.getCoxaInferiorDireita());
            spnCoxaInfEsq.setValue(perimetria.getCoxaInferiorEsquerda());
            spnPernaDir.setValue(perimetria.getPernaDireita());
            spnPernaEsq.setValue(perimetria.getPernaEsquerda());
            spnAntebracoDir.setValue(perimetria.getAntebracoDireito());
            spnAntebracoEsq.setValue(perimetria.getAntebracoEsquerdo());
            
        }
    }
    
    public void atualizar(){
        
        perimetria.setAvaliacaofisica(avaliacaoFisica);
        perimetria.setPescoco(new BigDecimal(spnPescoco.getValue().toString()));
        perimetria.setBracoDireito(new BigDecimal(spnBracoDir.getValue().toString()));
        perimetria.setBracoEsquerdo(new BigDecimal(spnBracoEsq.getValue().toString()));
        perimetria.setTorax(new BigDecimal(spnTorax.getValue().toString()));
        perimetria.setAbdomem(new BigDecimal(spnAbdomem.getValue().toString()));
        perimetria.setQuadril(new BigDecimal(spnQuadril.getValue().toString()));
        perimetria.setCoxaSuperiorDireita(new BigDecimal(spnCoxaSupDir.getValue().toString()));
        perimetria.setCoxaSuperiorEsquerda(new BigDecimal(spnCoxaSupEsq.getValue().toString()));
        perimetria.setCoxaInferiorDireita(new BigDecimal(spnCoxaInfDir.getValue().toString()));
        perimetria.setCoxaInferiorEsquerda(new BigDecimal(spnCoxaInfEsq.getValue().toString()));
        perimetria.setPernaDireita(new BigDecimal(spnPernaDir.getValue().toString()));
        perimetria.setPernaEsquerda(new BigDecimal(spnPernaEsq.getValue().toString()));
        perimetria.setAntebracoDireito(new BigDecimal(spnAntebracoDir.getValue().toString()));
        perimetria.setAntebracoEsquerdo(new BigDecimal(spnAntebracoEsq.getValue().toString()));
        
        try {
                sessao.beginTransaction();
                    sessao.update(perimetria);
                    JOptionPane.showMessageDialog(this,"Alterado com sucesso!");
                sessao.getTransaction().commit();
                
                
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar!"+"\n\nERRO: "+e.getMessage()+"\n\nCAUSA: "+e.getCause());
                //sessao.getTransaction().rollback();
        }
    }
    
    public void salvar(){
        
        perimetria.setAvaliacaofisica(avaliacaoFisica);
        perimetria.setPescoco(new BigDecimal(spnPescoco.getValue().toString()));
        perimetria.setBracoDireito(new BigDecimal(spnBracoDir.getValue().toString()));
        perimetria.setBracoEsquerdo(new BigDecimal(spnBracoEsq.getValue().toString()));
        perimetria.setTorax(new BigDecimal(spnTorax.getValue().toString()));
        perimetria.setAbdomem(new BigDecimal(spnAbdomem.getValue().toString()));
        perimetria.setQuadril(new BigDecimal(spnQuadril.getValue().toString()));
        perimetria.setCoxaSuperiorDireita(new BigDecimal(spnCoxaSupDir.getValue().toString()));
        perimetria.setCoxaSuperiorEsquerda(new BigDecimal(spnCoxaSupEsq.getValue().toString()));
        perimetria.setCoxaInferiorDireita(new BigDecimal(spnCoxaInfDir.getValue().toString()));
        perimetria.setCoxaInferiorEsquerda(new BigDecimal(spnCoxaInfEsq.getValue().toString()));
        perimetria.setPernaDireita(new BigDecimal(spnPernaDir.getValue().toString()));
        perimetria.setPernaEsquerda(new BigDecimal(spnPernaEsq.getValue().toString()));
        perimetria.setAntebracoDireito(new BigDecimal(spnAntebracoDir.getValue().toString()));
        perimetria.setAntebracoEsquerdo(new BigDecimal(spnAntebracoEsq.getValue().toString()));
        
        try {
                sessao.beginTransaction();
                    sessao.save(perimetria);
                    JOptionPane.showMessageDialog(this,"Salvo com sucesso!");
                sessao.getTransaction().commit();
                
                dispose();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(this, "Erro ao salvar!"+"\n\nERRO: "+e.getMessage()+"\n\nCAUSA: "+e.getCause());
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

        jPanel11 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        lblImagemPerimetria = new javax.swing.JLabel();
        spnPescoco = new javax.swing.JSpinner();
        spnTorax = new javax.swing.JSpinner();
        spnCoxaSupDir = new javax.swing.JSpinner();
        spnCoxaInfEsq = new javax.swing.JSpinner();
        spnAntebracoDir = new javax.swing.JSpinner();
        spnAbdomem = new javax.swing.JSpinner();
        spnCoxaSupEsq = new javax.swing.JSpinner();
        spnPernaDir = new javax.swing.JSpinner();
        spnAntebracoEsq = new javax.swing.JSpinner();
        spnBracoEsq = new javax.swing.JSpinner();
        spnQuadril = new javax.swing.JSpinner();
        spnCoxaInfDir = new javax.swing.JSpinner();
        spnPernaEsq = new javax.swing.JSpinner();
        spnBracoDir = new javax.swing.JSpinner();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblDuvidaPerimetria = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNovaModalidade = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Perimetria");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel11.setBackground(new java.awt.Color(245, 245, 245));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel36.setText("Pescoço");

        jLabel39.setText("Braço direito");

        jLabel40.setText("Braço esquerdo");

        jLabel41.setText("Tórax");

        jLabel42.setText("Abdomem");

        jLabel43.setText("Quadril");

        jLabel44.setText("Coxa sup. direita");

        jLabel45.setText("Coxa sup. esquerda");

        jLabel61.setText("Coxa inf. direita");

        jLabel67.setText("Coxa if. esquerda");

        jLabel68.setText("Perna direita");

        jLabel69.setText("Perna esquerda");

        jLabel71.setText("Antebraço direito");

        jLabel72.setText("Antebraço esquerdo");

        lblImagemPerimetria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagemPerimetria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/perimetria corporal.jpg"))); // NOI18N

        spnPescoco.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnTorax.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnCoxaSupDir.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnCoxaInfEsq.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnAntebracoDir.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnAbdomem.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnCoxaSupEsq.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnPernaDir.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnAntebracoEsq.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnBracoEsq.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnQuadril.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnCoxaInfDir.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnPernaEsq.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        spnBracoDir.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 0.5d));

        btnCancelar.setBackground(new java.awt.Color(255, 51, 51));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCancelarBranco16x16.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setIconTextGap(8);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setIconTextGap(8);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblDuvidaPerimetria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPergunta24x24.png"))); // NOI18N
        lblDuvidaPerimetria.setToolTipText("O que é?");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Avaliações Físicas >");

        lblNovaModalidade.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovaModalidade.setForeground(new java.awt.Color(102, 102, 102));
        lblNovaModalidade.setText("Perimetria");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNovaModalidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDuvidaPerimetria))
                    .addComponent(jSeparator4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel44)
                                        .addComponent(spnCoxaSupDir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel45)
                                        .addComponent(spnCoxaSupEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnCoxaInfDir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel61)))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnCoxaInfEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel67))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnPernaDir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel68))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnPernaEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel69)))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnAntebracoDir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnAntebracoEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnPescoco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel36))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel39)
                                        .addComponent(spnBracoDir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnBracoEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel40)))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnTorax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel41))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnAbdomem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel42))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spnQuadril, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel43))))
                            .addGap(43, 43, 43)
                            .addComponent(lblImagemPerimetria, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lblNovaModalidade))
                    .addComponent(lblDuvidaPerimetria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnPescoco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel39)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnBracoDir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnBracoEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel11Layout.createSequentialGroup()
                                                    .addComponent(jLabel41)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(spnTorax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                    .addComponent(jLabel43)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(spnQuadril, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel42)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnAbdomem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel44)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnCoxaSupDir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel61)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnCoxaInfDir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel45)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnCoxaSupEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spnPernaDir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel67)
                                            .addComponent(jLabel68))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnCoxaInfEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnPernaEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnAntebracoDir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnAntebracoEsq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblImagemPerimetria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(listaPerimetria.size()>0){
            perimetria= listaPerimetria.get(0);
            JOptionPane.showMessageDialog(this, "EDITAR!");
            atualizar();
        } else{
            perimetria = new Perimetria();
            JOptionPane.showMessageDialog(this, "SALVAR!");
            salvar();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogPerimetria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogPerimetria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogPerimetria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogPerimetria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogPerimetria dialog = new JDialogPerimetria(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblDuvidaPerimetria;
    private javax.swing.JLabel lblImagemPerimetria;
    private javax.swing.JLabel lblNovaModalidade;
    private javax.swing.JSpinner spnAbdomem;
    private javax.swing.JSpinner spnAntebracoDir;
    private javax.swing.JSpinner spnAntebracoEsq;
    private javax.swing.JSpinner spnBracoDir;
    private javax.swing.JSpinner spnBracoEsq;
    private javax.swing.JSpinner spnCoxaInfDir;
    private javax.swing.JSpinner spnCoxaInfEsq;
    private javax.swing.JSpinner spnCoxaSupDir;
    private javax.swing.JSpinner spnCoxaSupEsq;
    private javax.swing.JSpinner spnPernaDir;
    private javax.swing.JSpinner spnPernaEsq;
    private javax.swing.JSpinner spnPescoco;
    private javax.swing.JSpinner spnQuadril;
    private javax.swing.JSpinner spnTorax;
    // End of variables declaration//GEN-END:variables
}
