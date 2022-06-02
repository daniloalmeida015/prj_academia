package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Aluno;
import model.Cidade;
import model.Endereco;
import model.Estado;
import model.Pessoa;
import model.Pessoafisica;
import model.Telefone;
import org.hibernate.Session;

/*** * @author Danilo */
public class JDialogNovoAluno extends javax.swing.JDialog {

    private Session sessao;
    private Aluno aluno;
    private Pessoa pessoa;
    private Pessoafisica pessoaFisica;    
    private Endereco endereco;
    private Estado estado;
    private Cidade cidade;
    private Telefone telefone;
    
    private DefaultComboBoxModel dcmCidades;
    
    private List<Cidade> listaCidade;
    private List<Telefone> listaTelefone;
     
    private JDialogCidade jdCidade;
    
    private File arquivo, destino;
   
    
    
    public JDialogNovoAluno(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public JDialogNovoAluno(java.awt.Frame parent, boolean modal, Session sessao, Aluno aluno) {
        super(parent, modal);
        initComponents();
                
        this.sessao = sessao;
        this.aluno = aluno;
        dcmCidades = (DefaultComboBoxModel) cbxCidadeUf.getModel();
        listaCidade= new ArrayList<>();
        listaTelefone = new ArrayList<>();
        
        popularComboCidade();
        
        if(aluno != null){
            popularCamposFormulario();
            lblNovoAluno.setText("Editar Aluno");
        }else{
            lblNovoAluno.setText("Novo Aluno");
        }
        
        jTPainelAluno.setSelectedComponent(jPanelInformacoes);
        jTPainelAluno.setEnabledAt(0, true);
        jTPainelAluno.setEnabledAt(1, false);
        jTPainelAluno.setEnabledAt(2, false);
        
    }
    
    
    public final void popularComboCidade(){
        
        cbxCidadeUf.removeAllItems();
        listaCidade.clear();
        listaCidade= sessao.createQuery("from Cidade").list();
        
        try {
            for(int i =0; i<listaCidade.size(); i++){
               dcmCidades.addElement(listaCidade.get(i).getNome());
           }
        } catch (Exception e) {
        }     
    }
    
    
    public final void popularCamposFormulario(){
        
        
        lblNome.setText(aluno.getPessoafisica().getPessoa().getNome());
        lblEmail.setText(aluno.getPessoafisica().getPessoa().getEmail());
        lblStatus.setText(aluno.getStatus());
        
        txtNome.setText(aluno.getPessoafisica().getPessoa().getNome());
        String sexo = aluno.getPessoafisica().getSexo().equals("M")?"Masculino":"Feminino";
        cbxSexo.setSelectedItem(sexo);
        
        String dataNasc = DateFormat.getDateInstance().format(aluno.getPessoafisica().getDataNascimento());
        dataNasc = dataNasc.replace("/", "-");
            
        jtxtNascimento.setValue(dataNasc);
        
        jtxtRg.setValue(aluno.getPessoafisica().getRg());
        jtxtCpf.setValue(aluno.getPessoafisica().getCpf());
        txtEmail.setText(aluno.getPessoafisica().getPessoa().getEmail());
        
        //fazendo tratamento da imagem
        ImageIcon imageIcon = new ImageIcon(aluno.getPessoafisica().getFoto());
        Image imagem = imageIcon.getImage();  
        
        //JOptionPane.showMessageDialog(this,imagem);
        lblImagem.setIcon(new ImageIcon(imagem.getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), imagem.SCALE_DEFAULT)));
        
        
        //cbxMatricular.setSelectedItem(aluno.getStatus());
        
            
        listaTelefone = sessao.createQuery("from Telefone where pessoa_id ='"+aluno.getPessoafisica().getPessoa().getId()+"'").list();
        
        if(listaTelefone.get(0)!=null){
                telefone= listaTelefone.get(0);
                jtxtNumero.setText(telefone.getNumero());
                cbxTipoTelefone.setSelectedItem(telefone.getTipo());
            } else{
                jtxtNumero.setText("");
        }
        
        if(listaTelefone.size()>1){
            if(listaTelefone.get(1) != null){
                telefone= listaTelefone.get(1);
                jtxtNumero1.setText(telefone.getNumero());
                cbxTipoTelefone1.setSelectedItem(telefone.getTipo());
            }else{
                jtxtNumero1.setText("");
            }
        }
        
        
        txtRua.setText(aluno.getPessoafisica().getPessoa().getEndereco().getRua());
        txtBairro.setText(aluno.getPessoafisica().getPessoa().getEndereco().getBairro());
        txtNumero.setText(aluno.getPessoafisica().getPessoa().getEndereco().getNumero());
        jtxtCep.setValue(aluno.getPessoafisica().getPessoa().getEndereco().getCep());


        cbxCidadeUf.setSelectedItem(aluno.getPessoafisica().getPessoa().getEndereco().getCidade().getNome());
        cbxEstado.setSelectedItem(aluno.getPessoafisica().getPessoa().getEndereco().getCidade().getEstado().getSigla());  
    }
    
    
    
    public void exibirImagem(){
               
        JFileChooser buscadorDeArquivo = new JFileChooser();
        
        //coloca um titulo no buscador
        buscadorDeArquivo.setDialogTitle("Buscar arquivo");
        
        //para buscar apenas arquivos e não pastas
        buscadorDeArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        buscadorDeArquivo.setAcceptAllFileFilterUsed(false);
        
        Dimension  d = new Dimension(600, 400);
        buscadorDeArquivo.setPreferredSize(d);
        
        //filtros
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens (jpg, png, jpeg)", "JPG", "PNG", "JPEG");
        buscadorDeArquivo.setFileFilter(filtro);
        
        //chamando o JFileChooser
        int retorno = buscadorDeArquivo.showOpenDialog(this);
        
        if(retorno== JFileChooser.APPROVE_OPTION){ //quando ele abre um arquivo
            //pega o arquivo selecionado
            arquivo = buscadorDeArquivo.getSelectedFile();
            
            ImageIcon imageIcon = new ImageIcon(arquivo.getPath());
            Image imagem = imageIcon.getImage();
            lblImagem.setIcon(new ImageIcon(imagem.getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), Image.SCALE_DEFAULT)));
            
            
        }
        
    }
    
    
    
    
    
    public void copiar(File arquivo, File destino) {
        try {            
            FileChannel in = new FileInputStream(arquivo).getChannel();
            FileChannel out = new FileOutputStream(destino).getChannel();            
            out.transferFrom(in, 0, in.size());

            in.close();
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(IFrmAluno.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }
    
    
    private void salvar(){
        aluno = new Aluno();
        
        cidade.setEstado(estado);
        endereco.setCidade(cidade);
        pessoa.setEndereco(endereco);
        pessoaFisica.setPessoa(pessoa);
        aluno.setPessoafisica(pessoaFisica);
        aluno.setStatus("ATIVO");
        
        try{
            sessao.beginTransaction();
            sessao.save(aluno);

            telefone= new Telefone(jtxtNumero.getText(), cbxTipoTelefone.getSelectedItem().toString());
            telefone.setPessoa(pessoa);
            sessao.save(telefone);

            if(jtxtNumero1.getValue() != null && !jtxtNumero1.getText().equals("")){
               telefone= new Telefone(jtxtNumero1.getText(), cbxTipoTelefone1.getSelectedItem().toString());
               telefone.setPessoa(pessoa);
               sessao.save(telefone);   
            }
            sessao.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Aluno CADASTRADO com sucesso");
            
        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, erro);
            sessao.getTransaction().rollback();
        }
        
    }
    
    private void atualizar(){
        
        listaTelefone = sessao.createQuery("from Telefone where pessoa_id = "+aluno.getPessoafisica().getPessoa().getId()).list();
        endereco.setId(aluno.getPessoafisica().getPessoa().getEndereco().getId());
        pessoa.setId(aluno.getPessoafisica().getPessoa().getId());
        pessoaFisica.setPessoaId(aluno.getPessoafisica().getPessoa().getId());
        
        cidade.setEstado(estado);
        endereco.setCidade(cidade);
        pessoa.setEndereco(endereco);
        pessoaFisica.setPessoa(pessoa);
        aluno.setPessoafisica(pessoaFisica);
        
        try{
            
            sessao.beginTransaction();
            sessao.merge(aluno);

            if(!listaTelefone.isEmpty()){

                telefone= new Telefone(jtxtNumero.getText(), cbxTipoTelefone.getSelectedItem().toString());
                telefone.setId(listaTelefone.get(0).getId());
                telefone.setPessoa(pessoa);
                sessao.merge(telefone);
                
                if(!jtxtNumero1.getText().equals("")){
                  telefone= new Telefone(jtxtNumero1.getText(), cbxTipoTelefone1.getSelectedItem().toString());

                  if(listaTelefone.size() == 2){
                    telefone.setId(listaTelefone.get(1).getId());
                  }
                  
                  telefone.setPessoa(pessoa);   
                  sessao.merge(telefone);
                }
                
            }
            sessao.getTransaction().commit();
            JOptionPane.showMessageDialog(this, "Aluno "+aluno.getPessoafisica().getPessoa().getNome()+" ALTERADO com sucesso");
            
        } catch(Exception erro){
            //JOptionPane.showMessageDialog(this, "ERRO: "+erro.getMessage());
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

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTPainelAluno = new javax.swing.JTabbedPane();
        jPanelInformacoes = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jtxtNascimento = new javax.swing.JFormattedTextField();
        jtxtRg = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCpf = new javax.swing.JFormattedTextField();
        btnSalvar1 = new javax.swing.JButton();
        jPanelEndereco = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jtxtCep = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbxCidadeUf = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        btnEditarCidades = new javax.swing.JButton();
        btnSalvar3 = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        jPanelContato = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtxtNumero = new javax.swing.JFormattedTextField();
        cbxTipoTelefone = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jtxtNumero1 = new javax.swing.JFormattedTextField();
        cbxTipoTelefone1 = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnVoltar1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        painelLateral = new javax.swing.JPanel();
        lblImagem = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnExcluirImagem = new javax.swing.JButton();
        btnTirarFoto = new javax.swing.JButton();
        btnArquivo = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        lblNome = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblEmail = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        lblNovoAluno = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTPainelAluno.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanelInformacoes.setBackground(new java.awt.Color(245, 245, 245));
        jPanelInformacoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setText("Nome:");

        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeKeyReleased(evt);
            }
        });

        jLabel2.setText("Sexo:");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        jLabel3.setText("Data Nascimento:");

        try {
            jtxtNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jtxtRg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("RG:");

        jLabel5.setText("CPF:");

        try {
            jtxtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtxtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCpfActionPerformed(evt);
            }
        });

        btnSalvar1.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar1.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconProximoBranco16x16.png"))); // NOI18N
        btnSalvar1.setText("Próximo");
        btnSalvar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalvar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSalvar1.setIconTextGap(8);
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 483, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(52, 52, 52))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxtRg, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbxSexo, javax.swing.GroupLayout.Alignment.LEADING, 0, 231, Short.MAX_VALUE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNascimento)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 115, Short.MAX_VALUE))
                            .addComponent(jtxtCpf)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtCpf)
                    .addComponent(jtxtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelInformacoesLayout = new javax.swing.GroupLayout(jPanelInformacoes);
        jPanelInformacoes.setLayout(jPanelInformacoesLayout);
        jPanelInformacoesLayout.setHorizontalGroup(
            jPanelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelInformacoesLayout.setVerticalGroup(
            jPanelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPainelAluno.addTab("Informações", jPanelInformacoes);

        jPanelEndereco.setBackground(new java.awt.Color(250, 250, 250));

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));

        jLabel10.setText("CEP:");

        try {
            jtxtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel12.setText("UF:");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA ", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cbxEstado.setSelectedItem("SP");
        cbxEstado.setEnabled(false);

        jLabel20.setText("Cidade:");

        cbxCidadeUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCidadeUfActionPerformed(evt);
            }
        });

        jLabel7.setText("Rua:");

        txtRua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRuaKeyReleased(evt);
            }
        });

        jLabel9.setText("Numero:");

        jLabel8.setText("Bairro:");

        txtBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBairroKeyReleased(evt);
            }
        });

        btnEditarCidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconEditar.png"))); // NOI18N
        btnEditarCidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCidadesActionPerformed(evt);
            }
        });

        btnSalvar3.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar3.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconProximoBranco16x16.png"))); // NOI18N
        btnSalvar3.setText("Próximo");
        btnSalvar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalvar3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSalvar3.setIconTextGap(8);
        btnSalvar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar3ActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(31, 158, 150));
        btnVoltar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconVoltarBranco16x16.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVoltar.setIconTextGap(8);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEstado, 0, 144, Short.MAX_VALUE)
                            .addComponent(jtxtCep)
                            .addComponent(jLabel9)
                            .addComponent(txtNumero))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(cbxCidadeUf, 0, 298, Short.MAX_VALUE)
                                    .addGap(11, 11, 11)
                                    .addComponent(btnEditarCidades)
                                    .addContainerGap())
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7))
                                    .addGap(15, 15, 15)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtBairro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                                    .addComponent(txtRua, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap())))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnVoltar)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar3))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCidadeUf, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEditarCidades, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEnderecoLayout = new javax.swing.GroupLayout(jPanelEndereco);
        jPanelEndereco.setLayout(jPanelEnderecoLayout);
        jPanelEnderecoLayout.setHorizontalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelEnderecoLayout.setVerticalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPainelAluno.addTab("Endereço", jPanelEndereco);

        jPanelContato.setBackground(new java.awt.Color(250, 250, 250));

        jPanel8.setBackground(new java.awt.Color(250, 250, 250));

        jLabel14.setText("DDD + Numero:");

        try {
            jtxtNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cbxTipoTelefone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Celular", "Telefone" }));

        jLabel40.setText("DDD + Numero:");

        try {
            jtxtNumero1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cbxTipoTelefone1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Celular", "Telefone" }));

        btnSalvar.setBackground(new java.awt.Color(31, 158, 150));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSalvarBranco16x16.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalvar.setIconTextGap(8);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnVoltar1.setBackground(new java.awt.Color(31, 158, 150));
        btnVoltar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnVoltar1.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconVoltarBranco16x16.png"))); // NOI18N
        btnVoltar1.setText("Voltar");
        btnVoltar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVoltar1.setIconTextGap(8);
        btnVoltar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltar1ActionPerformed(evt);
            }
        });

        jLabel6.setText("E-mail:");

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jtxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxTipoTelefone, 0, 248, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel40)
                            .addComponent(jtxtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cbxTipoTelefone1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVoltar1)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel14))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTipoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTipoTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelContatoLayout = new javax.swing.GroupLayout(jPanelContato);
        jPanelContato.setLayout(jPanelContatoLayout);
        jPanelContatoLayout.setHorizontalGroup(
            jPanelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelContatoLayout.setVerticalGroup(
            jPanelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPainelAluno.addTab("Contato", jPanelContato);

        lblImagem.setBackground(new java.awt.Color(255, 255, 255));
        lblImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/semImagem.jpg"))); // NOI18N
        lblImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblImagem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblImagem.setIconTextGap(0);
        lblImagem.setPreferredSize(new java.awt.Dimension(220, 280));

        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Status:");
        lblStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnExcluirImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconDelete20x20.png"))); // NOI18N
        btnExcluirImagem.setContentAreaFilled(false);
        btnExcluirImagem.setOpaque(true);
        btnExcluirImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirImagemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirImagemMouseExited(evt);
            }
        });
        btnExcluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirImagemActionPerformed(evt);
            }
        });

        btnTirarFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCamera20x20.png"))); // NOI18N
        btnTirarFoto.setContentAreaFilled(false);
        btnTirarFoto.setOpaque(true);
        btnTirarFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTirarFotoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTirarFotoMouseExited(evt);
            }
        });

        btnArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPesquisa2.png"))); // NOI18N
        btnArquivo.setContentAreaFilled(false);
        btnArquivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArquivo.setIconTextGap(0);
        btnArquivo.setOpaque(true);
        btnArquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnArquivoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnArquivoMouseExited(evt);
            }
        });
        btnArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArquivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnExcluirImagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTirarFoto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnArquivo))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnExcluirImagem)
                        .addComponent(btnTirarFoto, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btnArquivo)))
        );

        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNome.setText("Nome");
        lblNome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setText("Email");
        lblEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout painelLateralLayout = new javax.swing.GroupLayout(painelLateral);
        painelLateral.setLayout(painelLateralLayout);
        painelLateralLayout.setHorizontalGroup(
            painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelLateralLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelLateralLayout.createSequentialGroup()
                        .addGroup(painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(lblNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4))
                .addGap(20, 20, 20))
        );
        painelLateralLayout.setVerticalGroup(
            painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLateralLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(painelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTPainelAluno))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPainelAluno)
            .addComponent(painelLateral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Alunos >");

        lblNovoAluno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNovoAluno.setForeground(new java.awt.Color(102, 102, 102));
        lblNovoAluno.setText("Novo Aluno");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNovoAluno)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblNovoAluno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyReleased
        txtNome.setText(txtNome.getText().toUpperCase());
        txtNome.setBorder(jtxtNumero1.getBorder());
    }//GEN-LAST:event_txtNomeKeyReleased

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        txtEmail.setText(txtEmail.getText().toUpperCase());
    }//GEN-LAST:event_txtEmailKeyReleased

    private void cbxCidadeUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCidadeUfActionPerformed
        String estadoSelecionado;
        
        try{
            estadoSelecionado = listaCidade.get(cbxCidadeUf.getSelectedIndex()).getEstado().getSigla();
            cbxEstado.setSelectedItem(estadoSelecionado);
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_cbxCidadeUfActionPerformed

    private void txtRuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaKeyReleased
        txtRua.setText(txtRua.getText().toUpperCase());
    }//GEN-LAST:event_txtRuaKeyReleased

    private void txtBairroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyReleased
        txtBairro.setText(txtBairro.getText().toUpperCase());
    }//GEN-LAST:event_txtBairroKeyReleased

    private void btnEditarCidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCidadesActionPerformed

        jdCidade = new JDialogCidade(new Frame(), true, sessao);
        jdCidade.setLocationRelativeTo(FrmPrincipal.dskPrincipal);
        jdCidade.setVisible(true);
        //jdCidade.toFront();
        
        popularComboCidade();

    }//GEN-LAST:event_btnEditarCidadesActionPerformed

    private void jtxtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCpfActionPerformed

    private void btnArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArquivoActionPerformed

        exibirImagem();
        
        
        

    }//GEN-LAST:event_btnArquivoActionPerformed

    private void btnExcluirImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirImagemActionPerformed
        arquivo = new File("./src/fotosPerfil/semImagem.jpg");

        ImageIcon imageIcon = new ImageIcon(arquivo.getPath());
        Image imagem = imageIcon.getImage();
        lblImagem.setIcon(new ImageIcon(imagem.getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), Image.SCALE_DEFAULT)));
    }//GEN-LAST:event_btnExcluirImagemActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        boolean isOk = !txtNome.getText().equals("");

        if(isOk){

            String uf = cbxEstado.getSelectedItem().toString();
            String sexo= String.valueOf(cbxSexo.getSelectedItem().toString().charAt(0));
            Date nascimento= Pessoafisica.formatarData(jtxtNascimento.getValue().toString());
            //recebe a data formatada e convertida para Date //o metodo FORMATARDATA se encontra na classe PessoaFisica

            estado = (Estado) sessao.createQuery("from Estado where sigla = '"+uf+"'").uniqueResult();
            cidade= (Cidade) sessao.createQuery("from Cidade where nome = '"+cbxCidadeUf.getSelectedItem().toString()+"'").uniqueResult();

            endereco= new Endereco(txtRua.getText(), txtBairro.getText(), txtNumero.getText(), jtxtCep.getText());
            pessoa= new Pessoa(txtNome.getText(), txtEmail.getText());

            //## tratamento da imagem de perfil

            //criar um arquivo de destino
            destino = new File("./src/fotosPerfil/"+txtNome.getText().trim().toLowerCase()+".jpg");

            if(arquivo!=null){
                //chamo uma função que faz a cópia do arq selecionado para a pasta do projeto
                copiar(arquivo,destino);
            }else{
                arquivo = new File("./src/fotosPerfil/semImagem.jpg");
                copiar(arquivo,destino);
            }

            String caminhoFoto = destino.getPath();

            pessoaFisica=new Pessoafisica(sexo,
                nascimento,
                jtxtRg.getText(),
                jtxtCpf.getText(),
                caminhoFoto);

            if(aluno==null){
                salvar();
            } else{
                atualizar();
            }
            
            dispose();

        } else{
            JOptionPane.showMessageDialog(this, "Preencha todos os campos");
            txtNome.setBorder(BorderFactory.createLineBorder(Color.red));

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        jTPainelAluno.setSelectedComponent(jPanelEndereco);
        jTPainelAluno.setEnabledAt(0, false);
        jTPainelAluno.setEnabledAt(1, true);
        jTPainelAluno.setEnabledAt(2, false);
    }//GEN-LAST:event_btnSalvar1ActionPerformed

    private void btnSalvar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar3ActionPerformed
        jTPainelAluno.setSelectedComponent(jPanelContato);
        jTPainelAluno.setEnabledAt(0, false);
        jTPainelAluno.setEnabledAt(1, false);
        jTPainelAluno.setEnabledAt(2, true);
    }//GEN-LAST:event_btnSalvar3ActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        jTPainelAluno.setSelectedComponent(jPanelInformacoes);
        jTPainelAluno.setEnabledAt(0, true);
        jTPainelAluno.setEnabledAt(1, false);
        jTPainelAluno.setEnabledAt(2, false);
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnVoltar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltar1ActionPerformed
        jTPainelAluno.setSelectedComponent(jPanelEndereco);
        jTPainelAluno.setEnabledAt(0, false);
        jTPainelAluno.setEnabledAt(1, true);
        jTPainelAluno.setEnabledAt(2, false);
    }//GEN-LAST:event_btnVoltar1ActionPerformed

    private void btnExcluirImagemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirImagemMouseEntered
        btnExcluirImagem.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnExcluirImagemMouseEntered

    private void btnExcluirImagemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirImagemMouseExited
        btnExcluirImagem.setBackground(painelLateral.getBackground());
    }//GEN-LAST:event_btnExcluirImagemMouseExited

    private void btnTirarFotoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTirarFotoMouseEntered
        btnTirarFoto.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnTirarFotoMouseEntered

    private void btnTirarFotoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTirarFotoMouseExited
        btnTirarFoto.setBackground(painelLateral.getBackground());
    }//GEN-LAST:event_btnTirarFotoMouseExited

    private void btnArquivoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnArquivoMouseEntered
        btnArquivo.setBackground(new Color(31,158,150));
    }//GEN-LAST:event_btnArquivoMouseEntered

    private void btnArquivoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnArquivoMouseExited
        btnArquivo.setBackground(painelLateral.getBackground());
    }//GEN-LAST:event_btnArquivoMouseExited

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
            java.util.logging.Logger.getLogger(JDialogNovoAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogNovoAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogNovoAluno dialog = new JDialogNovoAluno(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnArquivo;
    private javax.swing.JButton btnEditarCidades;
    private javax.swing.JButton btnExcluirImagem;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvar1;
    private javax.swing.JButton btnSalvar3;
    private javax.swing.JButton btnTirarFoto;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton btnVoltar1;
    private javax.swing.JComboBox<String> cbxCidadeUf;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<String> cbxTipoTelefone;
    private javax.swing.JComboBox<String> cbxTipoTelefone1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelContato;
    private javax.swing.JPanel jPanelEndereco;
    private javax.swing.JPanel jPanelInformacoes;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTPainelAluno;
    private javax.swing.JFormattedTextField jtxtCep;
    private javax.swing.JFormattedTextField jtxtCpf;
    private javax.swing.JFormattedTextField jtxtNascimento;
    private javax.swing.JFormattedTextField jtxtNumero;
    private javax.swing.JFormattedTextField jtxtNumero1;
    private javax.swing.JFormattedTextField jtxtRg;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNovoAluno;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel painelLateral;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRua;
    // End of variables declaration//GEN-END:variables
}
