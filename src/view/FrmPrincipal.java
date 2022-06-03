package view;

import dao.DAO;
import dao.GerarRelatorio;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.RIGHT;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.Session;

/**
 * @author Danilo Almeida da Silva
 */

public class FrmPrincipal extends javax.swing.JFrame {

    
    private final Session sessao;
    private JDialogTelaLogin telaLogin;

    private IFrmAluno ifrmAluno;
    private IFrmModalidade ifrmModalidade;
    private IFrmProfessor ifrmProfessor;
    private IFrmLembrete ifrmLembrete;
    private IFrmAvaliacaoFisica ifrmAvaliacaoFisica;
    private IFrmFichaTreino ifrmFichaTreino;
    private IFrmExercicios ifrmExercicio;
    private IFrmCaixa ifrmCaixa;
    
    private JDialogCidade jdCidade;

    private GerarRelatorio relatorio;
    private JasperViewer jpViewer;

    private final Color corBotaoSelecionado;
    private final Color corBotaoOriginal;

    Icon iconeMenuOpen;
    Icon iconeMenuClose;
    Icon planoDeFundoFosco, planoDeFundoOriginal;

    //private FundoDeTela fundo;
    public FrmPrincipal() {
        initComponents();
        
        //seta o icone no Frame
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSoutFit50x50.png")).getImage());

        //abre a sessao com o BD
        sessao = DAO.getSessionFactory().openSession();

        //chama o Frame de Login
        //fazerLogin();
        iconeMenuClose = new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco.png"));
        iconeMenuOpen = new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAbrirBranco.png"));

        planoDeFundoFosco = new javax.swing.ImageIcon(getClass().getResource("/imagens/background9.jpg"));
        planoDeFundoOriginal = new javax.swing.ImageIcon(getClass().getResource("/imagens/SoutFit_logotipo4.jpg"));

        corBotaoSelecionado = new Color(255, 255, 255);
        corBotaoOriginal = jPanelMenuLateral.getBackground();
        
        btnHome.doClick();
        
    }

    //não está sendo usada no momento, mas está implmentada
    private void fazerLogin() {
        telaLogin = new JDialogTelaLogin(this, true, sessao);
        telaLogin.setVisible(true);
    }

    

    private void redimensionarJanela() {
        if (dskPrincipal.getComponentCount() <= 2) {
            if (menu.getName().equals("menuOpen")) {
                btnHome.setText("Início");
                btnAluno.setText("Alunos");
                btnAvaliacaoFisica.setText("Aval. Física");
                btnCaixa.setText("Caixa");
                btnExercicio.setText("Exercícios");
                btnLembrete.setText("Lembretes");
                btnModalidade.setText("Modalidades");
                btnProfessor.setText("Professores");
                btnRelatorio.setText("Relatórios");
                btnSair.setText("Sair");
                btnFichaTreino.setText("Fichas de treino");

                menu.setIcon(iconeMenuClose);
                menu.setName("menuClosed");
                menu.setHorizontalAlignment(RIGHT);

            } else {
                btnHome.setText("");
                btnAluno.setText("");
                btnAvaliacaoFisica.setText("");
                btnCaixa.setText("");
                btnExercicio.setText("");
                btnLembrete.setText("");
                btnModalidade.setText("");
                btnProfessor.setText("");
                btnRelatorio.setText("");
                btnSair.setText("");
                btnFichaTreino.setText("");

                menu.setIcon(iconeMenuOpen);
                menu.setName("menuOpen");
                menu.setHorizontalAlignment((int) CENTER_ALIGNMENT);
            }
        }
    }

    //se for mexido no tamando, ele volta
    public void componentMoved(ComponentEvent e) {
        this.setLocation(0, 0);
    }

    public void zerarBotoesMenu() {

        btnHome.setBackground(corBotaoOriginal);
        btnHome.setForeground(Color.white);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconHomeBranco.png")));

        btnAluno.setBackground(corBotaoOriginal);
        btnAluno.setForeground(Color.white);
        btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunosBranco.png")));

        btnProfessor.setBackground(corBotaoOriginal);
        btnProfessor.setForeground(Color.white);
        btnProfessor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPersonalBranco.png")));

        btnRelatorio.setBackground(corBotaoOriginal);
        btnRelatorio.setForeground(Color.white);
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconRelatorioBranco.png")));

        btnAvaliacaoFisica.setBackground(corBotaoOriginal);
        btnAvaliacaoFisica.setForeground(Color.white);
        btnAvaliacaoFisica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAvaliacaoFisicaBranco.png")));

        btnModalidade.setBackground(corBotaoOriginal);
        btnModalidade.setForeground(Color.white);
        btnModalidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconModalidadeBranco.png")));

        btnCaixa.setBackground(corBotaoOriginal);
        btnCaixa.setForeground(Color.white);
        btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCaixaBranco.png")));

        btnLembrete.setBackground(corBotaoOriginal);
        btnLembrete.setForeground(Color.white);
        btnLembrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconLembreteBranco.png")));

        btnExercicio.setBackground(corBotaoOriginal);
        btnExercicio.setForeground(Color.white);
        btnExercicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExercicioBranco.png")));

        btnFichaTreino.setBackground(corBotaoOriginal);
        btnFichaTreino.setForeground(Color.white);
        btnFichaTreino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFichaTreinoBranco.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jPanelMenuLateral = new javax.swing.JPanel();
        menu = new javax.swing.JLabel();
        btnAluno = new javax.swing.JButton();
        btnExercicio = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnLembrete = new javax.swing.JButton();
        btnCaixa = new javax.swing.JButton();
        btnModalidade = new javax.swing.JButton();
        btnAvaliacaoFisica = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        btnProfessor = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnFichaTreino = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        btnHome = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        dskPrincipal = new javax.swing.JDesktopPane();
        logotipo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("soutFit - Sistema de gestão de academia");
        setExtendedState(6);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1024, 768));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelMenuLateral.setBackground(new java.awt.Color(31, 158, 150));

        menu.setBackground(new java.awt.Color(31, 158, 150));
        menu.setForeground(new java.awt.Color(255, 255, 255));
        menu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFecharBranco.png"))); // NOI18N
        menu.setAlignmentX(0.5F);
        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu.setName("menuClosed"); // NOI18N
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuMousePressed(evt);
            }
        });

        btnAluno.setBackground(new java.awt.Color(31, 158, 150));
        btnAluno.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnAluno.setForeground(new java.awt.Color(255, 255, 255));
        btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunosBranco.png"))); // NOI18N
        btnAluno.setText("Alunos");
        btnAluno.setToolTipText("Alunos");
        btnAluno.setBorderPainted(false);
        btnAluno.setContentAreaFilled(false);
        btnAluno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAluno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAluno.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAluno.setIconTextGap(25);
        btnAluno.setOpaque(true);
        btnAluno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAlunoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlunoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlunoMouseExited(evt);
            }
        });
        btnAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlunoActionPerformed(evt);
            }
        });

        btnExercicio.setBackground(new java.awt.Color(31, 158, 150));
        btnExercicio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnExercicio.setForeground(new java.awt.Color(255, 255, 255));
        btnExercicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExercicioBranco.png"))); // NOI18N
        btnExercicio.setText("Exercícios");
        btnExercicio.setToolTipText("Exercícios");
        btnExercicio.setContentAreaFilled(false);
        btnExercicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExercicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExercicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnExercicio.setIconTextGap(25);
        btnExercicio.setOpaque(true);
        btnExercicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExercicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExercicioMouseExited(evt);
            }
        });
        btnExercicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExercicioActionPerformed(evt);
            }
        });

        btnSair.setBackground(new java.awt.Color(31, 158, 150));
        btnSair.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnSair.setForeground(new java.awt.Color(255, 255, 255));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconSairBranco.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setToolTipText("Sair do sistema");
        btnSair.setContentAreaFilled(false);
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSair.setIconTextGap(25);
        btnSair.setOpaque(true);
        btnSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSairMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSairMouseExited(evt);
            }
        });
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnLembrete.setBackground(new java.awt.Color(31, 158, 150));
        btnLembrete.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnLembrete.setForeground(new java.awt.Color(255, 255, 255));
        btnLembrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconLembreteBranco.png"))); // NOI18N
        btnLembrete.setText("Lembretes");
        btnLembrete.setToolTipText("Lembretes");
        btnLembrete.setContentAreaFilled(false);
        btnLembrete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLembrete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLembrete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLembrete.setIconTextGap(25);
        btnLembrete.setOpaque(true);
        btnLembrete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLembreteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLembreteMouseExited(evt);
            }
        });
        btnLembrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLembreteActionPerformed(evt);
            }
        });

        btnCaixa.setBackground(new java.awt.Color(31, 158, 150));
        btnCaixa.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCaixaBranco.png"))); // NOI18N
        btnCaixa.setText("Caixa");
        btnCaixa.setToolTipText("Caixa");
        btnCaixa.setContentAreaFilled(false);
        btnCaixa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCaixa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCaixa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCaixa.setIconTextGap(25);
        btnCaixa.setOpaque(true);
        btnCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCaixaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCaixaMouseExited(evt);
            }
        });
        btnCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaixaActionPerformed(evt);
            }
        });

        btnModalidade.setBackground(new java.awt.Color(31, 158, 150));
        btnModalidade.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnModalidade.setForeground(new java.awt.Color(255, 255, 255));
        btnModalidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconModalidadeBranco.png"))); // NOI18N
        btnModalidade.setText("Modalidades");
        btnModalidade.setToolTipText("Modalidades");
        btnModalidade.setContentAreaFilled(false);
        btnModalidade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModalidade.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModalidade.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnModalidade.setIconTextGap(25);
        btnModalidade.setOpaque(true);
        btnModalidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModalidadeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModalidadeMouseExited(evt);
            }
        });
        btnModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModalidadeActionPerformed(evt);
            }
        });

        btnAvaliacaoFisica.setBackground(new java.awt.Color(31, 158, 150));
        btnAvaliacaoFisica.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnAvaliacaoFisica.setForeground(new java.awt.Color(255, 255, 255));
        btnAvaliacaoFisica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAvaliacaoFisicaBranco.png"))); // NOI18N
        btnAvaliacaoFisica.setText("Aval. Físicas");
        btnAvaliacaoFisica.setToolTipText("Avaliações Físicas");
        btnAvaliacaoFisica.setContentAreaFilled(false);
        btnAvaliacaoFisica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAvaliacaoFisica.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAvaliacaoFisica.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAvaliacaoFisica.setIconTextGap(25);
        btnAvaliacaoFisica.setOpaque(true);
        btnAvaliacaoFisica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAvaliacaoFisicaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAvaliacaoFisicaMouseExited(evt);
            }
        });
        btnAvaliacaoFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvaliacaoFisicaActionPerformed(evt);
            }
        });

        btnRelatorio.setBackground(new java.awt.Color(31, 158, 150));
        btnRelatorio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconRelatorioBranco.png"))); // NOI18N
        btnRelatorio.setText("Relatórios");
        btnRelatorio.setToolTipText("Relatórios");
        btnRelatorio.setContentAreaFilled(false);
        btnRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRelatorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRelatorio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRelatorio.setIconTextGap(25);
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

        btnProfessor.setBackground(new java.awt.Color(31, 158, 150));
        btnProfessor.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnProfessor.setForeground(new java.awt.Color(255, 255, 255));
        btnProfessor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPersonalBranco.png"))); // NOI18N
        btnProfessor.setText("Professores");
        btnProfessor.setToolTipText("Professores");
        btnProfessor.setBorderPainted(false);
        btnProfessor.setContentAreaFilled(false);
        btnProfessor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfessor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProfessor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnProfessor.setIconTextGap(25);
        btnProfessor.setOpaque(true);
        btnProfessor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProfessorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProfessorMouseExited(evt);
            }
        });
        btnProfessor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfessorActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        btnFichaTreino.setBackground(new java.awt.Color(31, 158, 150));
        btnFichaTreino.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnFichaTreino.setForeground(new java.awt.Color(255, 255, 255));
        btnFichaTreino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFichaTreinoBranco.png"))); // NOI18N
        btnFichaTreino.setText("Fichas de treino");
        btnFichaTreino.setToolTipText("Fichas de treino");
        btnFichaTreino.setContentAreaFilled(false);
        btnFichaTreino.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFichaTreino.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFichaTreino.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFichaTreino.setIconTextGap(25);
        btnFichaTreino.setOpaque(true);
        btnFichaTreino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFichaTreinoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFichaTreinoMouseExited(evt);
            }
        });
        btnFichaTreino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFichaTreinoActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        btnHome.setBackground(new java.awt.Color(31, 158, 150));
        btnHome.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconHomeBranco.png"))); // NOI18N
        btnHome.setText("Página Inicial");
        btnHome.setToolTipText("Alunos");
        btnHome.setBorderPainted(false);
        btnHome.setContentAreaFilled(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnHome.setIconTextGap(25);
        btnHome.setOpaque(true);
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelMenuLateralLayout = new javax.swing.GroupLayout(jPanelMenuLateral);
        jPanelMenuLateral.setLayout(jPanelMenuLateralLayout);
        jPanelMenuLateralLayout.setHorizontalGroup(
            jPanelMenuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMenuLateralLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelMenuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(btnExercicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLembrete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCaixa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModalidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAvaliacaoFisica, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRelatorio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfessor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFichaTreino, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator12)))
        );
        jPanelMenuLateralLayout.setVerticalGroup(
            jPanelMenuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLateralLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnAvaliacaoFisica, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnModalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnExercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnFichaTreino, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dskPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        dskPrincipal.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dskPrincipalComponentAdded(evt);
            }
        });
        dskPrincipal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dskPrincipalFocusGained(evt);
            }
        });
        dskPrincipal.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                dskPrincipalComponentHidden(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                dskPrincipalComponentResized(evt);
            }
        });

        logotipo.setBackground(new java.awt.Color(255, 255, 255));
        logotipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/SoutFit_logotipo4.jpg"))); // NOI18N
        logotipo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logotipo.setMinimumSize(new java.awt.Dimension(400, 300));

        dskPrincipal.setLayer(logotipo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dskPrincipalLayout = new javax.swing.GroupLayout(dskPrincipal);
        dskPrincipal.setLayout(dskPrincipalLayout);
        dskPrincipalLayout.setHorizontalGroup(
            dskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logotipo, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
        );
        dskPrincipalLayout.setVerticalGroup(
            dskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logotipo, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addComponent(jPanelMenuLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dskPrincipal))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskPrincipal)
            .addComponent(jPanelMenuLateral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1191, 673));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        //fecha a sessão com o BD quando o programa for fechado
        sessao.getSessionFactory().close();
    }//GEN-LAST:event_formWindowClosing

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

        //se o dskPrincipal for redimensionado ... o IFrame tbm muda
        setExtendedState(6);
    }//GEN-LAST:event_formComponentResized


    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked

        redimensionarJanela();

    }//GEN-LAST:event_menuMouseClicked

    private void menuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMousePressed

    }//GEN-LAST:event_menuMousePressed

    private void btnAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlunoActionPerformed
        
        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmAluno)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {
            ifrmAluno = new IFrmAluno(sessao);
            dskPrincipal.add(ifrmAluno);
            ifrmAluno.setSize(dskPrincipal.getSize());
            ifrmAluno.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnAluno.setBackground(corBotaoSelecionado);
        btnAluno.setForeground(Color.black);
        btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAlunos.png")));

        


    }//GEN-LAST:event_btnAlunoActionPerformed

    private void btnExercicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExercicioActionPerformed

        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmExercicio)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {

            IFrmExercicios ifrmExercicio = new IFrmExercicios(sessao);
            dskPrincipal.add(ifrmExercicio);
            ifrmExercicio.setSize(dskPrincipal.getSize());
            ifrmExercicio.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnExercicio.setBackground(corBotaoSelecionado);
        btnExercicio.setForeground(Color.black);
        btnExercicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconExercicios.png")));

    }//GEN-LAST:event_btnExercicioActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja realmente sair do sistema? ", "SAIR", JOptionPane.YES_NO_OPTION);

        if (opcao == 0) {
            //sair
            System.exit(1);
        }

    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLembreteActionPerformed
       
        
        
        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmLembrete)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {
            ifrmLembrete = new IFrmLembrete();
            dskPrincipal.add(ifrmLembrete);

            ifrmLembrete.setSize(dskPrincipal.getSize());
            ifrmLembrete.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnLembrete.setBackground(corBotaoSelecionado);
        btnLembrete.setForeground(Color.black);
        btnLembrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconLembrete.png")));


    }//GEN-LAST:event_btnLembreteActionPerformed

    private void btnCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaixaActionPerformed

        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmCaixa)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {
            ifrmCaixa = new IFrmCaixa();
            dskPrincipal.add(ifrmCaixa);
            ifrmCaixa.setSize(dskPrincipal.getSize());
            ifrmCaixa.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnCaixa.setBackground(corBotaoSelecionado);
        btnCaixa.setForeground(Color.black);
        btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconCaixa.png")));
        
    }//GEN-LAST:event_btnCaixaActionPerformed

    private void btnModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModalidadeActionPerformed

        if (dskPrincipal.getComponentCount() > 1&& !dskPrincipal.getSelectedFrame().equals(ifrmModalidade)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {
            ifrmModalidade = new IFrmModalidade(sessao);

            //ifrmModalidade.setLocation((dskPrincipal.getWidth()-ifrmModalidade.getWidth())/2,
            //          (dskPrincipal.getHeight()-ifrmModalidade.getHeight())/2);;
            ifrmModalidade.setSize(dskPrincipal.getWidth(), //deixa o IFrame de cadAluno do mesmo tamanho do DskPrincipal
                    dskPrincipal.getHeight());

            dskPrincipal.add(ifrmModalidade);
            ifrmModalidade.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnModalidade.setBackground(corBotaoSelecionado);
        btnModalidade.setForeground(Color.black);
        btnModalidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconModalidade.png")));
    }//GEN-LAST:event_btnModalidadeActionPerformed

    private void btnAvaliacaoFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliacaoFisicaActionPerformed

        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmAvaliacaoFisica)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        IFrmAvaliacaoFisica ifrmAvaliacaoFisica;
        
        if (dskPrincipal.getComponentCount() == 1) {

            ifrmAvaliacaoFisica = new IFrmAvaliacaoFisica(sessao, null);

            dskPrincipal.add(ifrmAvaliacaoFisica);
            //adicona o Frame de cad de alunos ao desktopPrincipal
            ifrmAvaliacaoFisica.setSize(dskPrincipal.getSize());
            //deixa o IFrame de cadAluno do mesmo tamanho do DskPrincipal

            ifrmAvaliacaoFisica.setVisible(true);
        }
        
        
        zerarBotoesMenu();

        btnAvaliacaoFisica.setBackground(corBotaoSelecionado);
        btnAvaliacaoFisica.setForeground(Color.black);
        btnAvaliacaoFisica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconAvalFisica.png")));
        
    }//GEN-LAST:event_btnAvaliacaoFisicaActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        String sql = "SELECT\n"
                + "             aluno.status AS aluno_status,\n"
                + "             pessoafisica.sexo AS pessoafisica_sexo,\n"
                + "             pessoafisica.dataNascimento AS pessoafisica_dataNascimento,\n"
                + "             pessoa.id AS pessoa_id,\n"
                + "             pessoa.nome AS pessoa_nome,\n"
                + "             pessoa.email AS pessoa_email\n"
                + "        FROM\n"
                + "             pessoafisica pessoafisica INNER JOIN aluno aluno ON pessoafisica.pessoa_id = aluno.pessoaFisica_id\n"
                + "             INNER JOIN pessoa pessoa ON pessoafisica.pessoa_id = pessoa.id";

        if (dskPrincipal.getComponentCount() > 1) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }

        if (jpViewer == null) {
            relatorio = new GerarRelatorio();
            jpViewer = relatorio.gerarRelatorioAluno(sql, "/relatorios/rel_alunos.jasper");
        }

        jpViewer.setVisible(true);
        jpViewer.setState(Frame.NORMAL);

        jpViewer.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                jpViewer = null;
            }
        });
        
        
        zerarBotoesMenu();

        btnRelatorio.setBackground(corBotaoSelecionado);
        btnRelatorio.setForeground(Color.black);
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconRelatorio.png")));

    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnProfessorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfessorActionPerformed

        if (dskPrincipal.getComponentCount() > 1&& !dskPrincipal.getSelectedFrame().equals(ifrmProfessor)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {

            ifrmProfessor = new IFrmProfessor(sessao);

            dskPrincipal.add(ifrmProfessor);
            //adicona o Frame de cad de alunos ao desktopPrincipal
            ifrmProfessor.setSize(dskPrincipal.getWidth(), dskPrincipal.getHeight());
            //deixa o IFrame de cadAluno do mesmo tamanho do DskPrincipal
            ifrmProfessor.setVisible(true);
        }

        zerarBotoesMenu();
        btnProfessor.setBackground(corBotaoSelecionado);
        btnProfessor.setForeground(Color.black);
        btnProfessor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconPersonal.png")));

    }//GEN-LAST:event_btnProfessorActionPerformed

    private void btnFichaTreinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFichaTreinoActionPerformed

        if (dskPrincipal.getComponentCount() > 1 && !dskPrincipal.getSelectedFrame().equals(ifrmFichaTreino)) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }
        
        if (dskPrincipal.getComponentCount() == 1) {

            ifrmFichaTreino = new IFrmFichaTreino(sessao, null);
            dskPrincipal.add(ifrmFichaTreino);
            ifrmFichaTreino.setSize(dskPrincipal.getSize());
            ifrmFichaTreino.setVisible(true);
        }
        
        zerarBotoesMenu();
        btnFichaTreino.setBackground(corBotaoSelecionado);
        btnFichaTreino.setForeground(Color.black);
        btnFichaTreino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconFichaTreino.png")));
        
        
        
    }//GEN-LAST:event_btnFichaTreinoActionPerformed

    private void btnProfessorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfessorMouseEntered
        if (!btnProfessor.getBackground().equals(corBotaoSelecionado)) {
            btnProfessor.setBackground(Color.gray);
        }


    }//GEN-LAST:event_btnProfessorMouseEntered

    private void btnRelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseEntered
        if (!btnRelatorio.getBackground().equals(corBotaoSelecionado)) {
            btnRelatorio.setBackground(Color.gray);
        }
    }//GEN-LAST:event_btnRelatorioMouseEntered

    private void btnProfessorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfessorMouseExited
        if (!btnProfessor.getBackground().equals(corBotaoSelecionado)) {
            btnProfessor.setBackground(jPanelMenuLateral.getBackground());
        }

    }//GEN-LAST:event_btnProfessorMouseExited

    private void btnAlunoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlunoMouseEntered
        if (btnAluno.getBackground().equals(corBotaoOriginal)) {
            btnAluno.setBackground(Color.gray);
        }

        //btnAluno.setBackground(Color.gray);
    }//GEN-LAST:event_btnAlunoMouseEntered

    private void btnAlunoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlunoMouseExited

        if (!btnAluno.getBackground().equals(corBotaoSelecionado)) {
            btnAluno.setBackground(jPanelMenuLateral.getBackground());
        }


    }//GEN-LAST:event_btnAlunoMouseExited

    private void btnRelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRelatorioMouseExited
        
        if (!btnRelatorio.getBackground().equals(corBotaoSelecionado)) {
            btnRelatorio.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnRelatorioMouseExited

    private void btnAvaliacaoFisicaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvaliacaoFisicaMouseEntered
        if (!btnAvaliacaoFisica.getBackground().equals(corBotaoSelecionado)) {
            btnAvaliacaoFisica.setBackground(Color.gray);
        }
       
    }//GEN-LAST:event_btnAvaliacaoFisicaMouseEntered

    private void btnModalidadeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModalidadeMouseEntered
        if (!btnModalidade.getBackground().equals(corBotaoSelecionado)) {
            btnModalidade.setBackground(Color.gray);
        }
        
    }//GEN-LAST:event_btnModalidadeMouseEntered

    private void btnCaixaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCaixaMouseEntered
        if (!btnCaixa.getBackground().equals(corBotaoSelecionado)) {
            btnCaixa.setBackground(Color.gray);
        }
    }//GEN-LAST:event_btnCaixaMouseEntered

    private void btnLembreteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLembreteMouseEntered
        if (!btnLembrete.getBackground().equals(corBotaoSelecionado)) {
            btnLembrete.setBackground(Color.gray);
        }
    }//GEN-LAST:event_btnLembreteMouseEntered

    private void btnExercicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExercicioMouseEntered
        
        if (!btnExercicio.getBackground().equals(corBotaoSelecionado)) {
            btnExercicio.setBackground(Color.gray);
        }
        
    }//GEN-LAST:event_btnExercicioMouseEntered

    private void btnFichaTreinoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFichaTreinoMouseEntered
        if (!btnFichaTreino.getBackground().equals(corBotaoSelecionado)) {
            btnFichaTreino.setBackground(Color.gray);
        }
        
    }//GEN-LAST:event_btnFichaTreinoMouseEntered

    private void btnSairMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSairMouseEntered
        btnSair.setBackground(Color.gray);
    }//GEN-LAST:event_btnSairMouseEntered

    private void btnAvaliacaoFisicaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvaliacaoFisicaMouseExited
        if (!btnAvaliacaoFisica.getBackground().equals(corBotaoSelecionado)) {
            btnAvaliacaoFisica.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnAvaliacaoFisicaMouseExited

    private void btnModalidadeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModalidadeMouseExited
        
        if (!btnModalidade.getBackground().equals(corBotaoSelecionado)) {
            btnModalidade.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnModalidadeMouseExited

    private void btnCaixaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCaixaMouseExited
        if (!btnCaixa.getBackground().equals(corBotaoSelecionado)) {
            btnCaixa.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnCaixaMouseExited

    private void btnLembreteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLembreteMouseExited
        if (!btnLembrete.getBackground().equals(corBotaoSelecionado)) {
            btnLembrete.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnLembreteMouseExited

    private void btnExercicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExercicioMouseExited

        if (!btnExercicio.getBackground().equals(corBotaoSelecionado)) {
            btnExercicio.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnExercicioMouseExited

    private void btnFichaTreinoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFichaTreinoMouseExited
        if (!btnFichaTreino.getBackground().equals(corBotaoSelecionado)) {
            btnFichaTreino.setBackground(jPanelMenuLateral.getBackground());
        }
        
    }//GEN-LAST:event_btnFichaTreinoMouseExited

    private void btnSairMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSairMouseExited
        btnSair.setBackground(jPanelMenuLateral.getBackground());
    }//GEN-LAST:event_btnSairMouseExited

    private void dskPrincipalComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dskPrincipalComponentResized

        if (dskPrincipal.getComponentCount() > 1) {
            dskPrincipal.getSelectedFrame().setSize(dskPrincipal.getSize());
        }
    }//GEN-LAST:event_dskPrincipalComponentResized

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved

        setExtendedState(6);
    }//GEN-LAST:event_formComponentMoved

    private void dskPrincipalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dskPrincipalFocusGained

    }//GEN-LAST:event_dskPrincipalFocusGained

    private void dskPrincipalComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dskPrincipalComponentHidden
    }//GEN-LAST:event_dskPrincipalComponentHidden

    private void dskPrincipalComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dskPrincipalComponentAdded

        if (dskPrincipal.getComponentCount() > 1) {

            //logotipo.setIcon(planoDeFundoFosco);
        }
    }//GEN-LAST:event_dskPrincipalComponentAdded

    private void btnAlunoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlunoMouseClicked


    }//GEN-LAST:event_btnAlunoMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked

    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered

        if (!btnHome.getBackground().equals(corBotaoSelecionado)) {
            btnHome.setBackground(Color.gray);
        }

        //Border borda = BorderFactory.createLineBorder(Color.black);
        //btnHome.setBorder(borda);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        if (!btnHome.getBackground().equals(corBotaoSelecionado)) {
            btnHome.setBackground(new Color(31, 158, 150));
        }

    }//GEN-LAST:event_btnHomeMouseExited

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        
        if (dskPrincipal.getComponentCount() > 1) {
            dskPrincipal.getSelectedFrame().setVisible(false);
            dskPrincipal.remove(dskPrincipal.getSelectedFrame());
        }

        zerarBotoesMenu();

        btnHome.setBackground(corBotaoSelecionado);
        btnHome.setForeground(Color.black);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/iconHome.png")));

    }//GEN-LAST:event_btnHomeActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });

    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAluno;
    public static javax.swing.JButton btnAvaliacaoFisica;
    private javax.swing.JButton btnCaixa;
    private javax.swing.JButton btnExercicio;
    public static javax.swing.JButton btnFichaTreino;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLembrete;
    private javax.swing.JButton btnModalidade;
    private javax.swing.JButton btnProfessor;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnSair;
    public static javax.swing.JDesktopPane dskPrincipal;
    private javax.swing.JPanel jPanelMenuLateral;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel logotipo;
    private javax.swing.JLabel menu;
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
}
