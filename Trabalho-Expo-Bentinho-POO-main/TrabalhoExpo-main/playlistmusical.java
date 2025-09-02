import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class playlistmusical {

    private static HashMap<String, DefaultListModel<String>> modelos = new HashMap<>();
    private static DefaultListModel<String> modeloFavoritas = new DefaultListModel<>();
    private static boolean modoEscuroAtivo = false; // modo escuridão
    private static Clip clipAtual = null;



    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }

        JFrame janela = new JFrame("Playlist Musical");
        janela.setSize(1280, 720);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JTabbedPane painelAbas = new JTabbedPane();
        painelAbas.setFont(new Font("JetBrains Mono", Font.ITALIC | Font.BOLD, 16));

        // Butão modo escuro
        JButton btnModoEscuro = new JButton("Ativar Modo Escuro");
        btnModoEscuro.setFont(new Font("Monospace", Font.BOLD, 14));
        btnModoEscuro.setFocusPainted(false);

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.add(btnModoEscuro);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(painelAbas, BorderLayout.CENTER);

        // ABas
        adicionarAba(painelAbas, "Samba",
                new String[] {
                        "Aquarela do Brasil (3:45)", "Trem das Onze (4:10)", "O Mundo é um Moinho (3:55)",
                        "Sorriso Aberto (4:02)", "A Amizade (3:48)", "Conselho (3:50)",
                        "Se Você Jurar (2:58)", "Não Deixe o Samba Morrer (3:40)"
                });
        adicionarAba(painelAbas, "Pagode",
                new String[] {
                        "Deixa Acontecer (4:15)", "Coração Deserto (3:50)", "Tá Vendo Aquela Lua (4:03)",
                        "Até Que Durou (3:57)", "Pela Última Vez (4:01)", "Apaguei Pra Todos (3:42)",
                        "P do Pecado (3:58)", "Coração Partido (4:12)"
                });
        adicionarAba(painelAbas, "Sertanejo",
                new String[] {
                        "Evidências (4:25)", "Chalana (3:45)", "Fio de Cabelo (4:05)", "Tubarões (3:50)",
                        "Caso Indefinido (3:58)", "Dois Tristes (4:10)", "Eu Menti (3:40)",
                        "Proibido Terminar (3:55)", "Cuida Bem Dela (4:00)", "Narcisista (3:48)", "50 Reais (4:12)"
                });
        adicionarAba(painelAbas, "Funk",
                new String[] {
                        "Bum Bum Tam Tam (3:25)", "Vai Malandra (3:10)", "Parado no Bailão (3:40)"
                });
        adicionarAba(painelAbas, "Internacional",
                new String[] {
                });
        adicionarAba(painelAbas, "Pop",
                new String[] {
                        "Ordinary (3:30)", "Photograph (4:19)", "Cardigan (3:59)", "Die With A Smile (3:45)",
                        "Blinding Lights (3:22)", "Company (3:40)", "Die For You (3:33)"
                });
        adicionarAba(painelAbas, "Rap",
                new String[] {
                        "Rap God (6:04)", "See You Again (3:50)", "Without Me (4:34)", "Goosebumps (4:03)",
                        "No Idea (2:54)", "Godzilla (3:30)", "Sicko Mode (5:12)", "The Box (3:16)",
                        "Lucid Dreams (3:59)", "Rockstar (3:38)"
                });
        adicionarAba(painelAbas, "Metal",
                new String[] {
                        "Enter Sandman (5:31)", "Master of Puppets (8:35)", "Paranoid (2:50)", "Holy Wars (6:37)",
                        "Fear of the Dark (7:16)", "Hallowed Be Thy Name (7:12)", "Back in Black (4:15)",
                        "Smoke on the Water (5:40)", "Crazy Train (4:52)", "Iron Man (5:56)"
                });
        adicionarAba(painelAbas, "Eletrônica",
                new String[] {
                        "Strobe (10:37)", "Titan (3:00)", "Animals (5:03)", "Wake Me Up (4:07)", "Lean On (2:56)",
                        "Get Lucky (6:09)", "Turn Down for What (3:33)", "Tremor (3:49)",
                        "Don't You Worry Child (5:24)", "Clarity (4:31)"
                });

        adicionarAbaFavoritas(painelAbas);

        janela.add(painelPrincipal);
        janela.setVisible(true);

        // Negro
        btnModoEscuro.addActionListener(e -> {
            modoEscuroAtivo = !modoEscuroAtivo;
            if (modoEscuroAtivo) {
                btnModoEscuro.setText("Desativar Modo Escuro");
            } else {
                btnModoEscuro.setText("Ativar Modo Escuro");
            }
            aplicarTema(painelPrincipal, modoEscuroAtivo);
        });

        // Claro
        aplicarTema(painelPrincipal, modoEscuroAtivo);
    }

// MODO ESCURO E CLARO!!
    private static void aplicarTema(Component comp, boolean escuro) {
        Color fundoClaro = new Color(240, 240, 240);
        Color fundoEscuro = new Color(34, 34, 34);
        Color textoClaro = Color.BLACK;
        Color textoEscuro = Color.WHITE;

        if (comp instanceof JPanel) {
            JPanel painel = (JPanel) comp;
            if (escuro) {
                painel.setBackground(fundoEscuro);
            } else {
                painel.setBackground(fundoClaro);
            }
            for (Component c : painel.getComponents()) {
                aplicarTema(c, escuro);
            }
        } else if (comp instanceof JTabbedPane) {
            JTabbedPane tabs = (JTabbedPane) comp;
            if (escuro) {
                tabs.setBackground(fundoEscuro);
                tabs.setForeground(textoEscuro);
            } else {
                tabs.setBackground(fundoClaro);
                tabs.setForeground(textoClaro);
            }
            for (int i = 0; i < tabs.getTabCount(); i++) {
                Component c = tabs.getComponentAt(i);
                aplicarTema(c, escuro);
            }
        } else if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            if (escuro) {
                if (btn.getText().equals("Adicionar")) {
                    btn.setBackground(new Color(0, 0, 0)); // Modo Escuro
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Editar")) {
                    btn.setBackground(new Color(0, 0, 0)); // Modo Escuro
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Remover")) {
                    btn.setBackground(new Color(0, 0, 0)); // Modo Escuro
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Favoritar")) {
                    btn.setBackground(new Color(0, 0, 0)); // Modo Escuro
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Pausar")) {
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                    btn.setBackground(new Color(0, 0, 0)); // Modo Escuro
                } else {
                    btn.setBackground(new Color(0, 0, 0));
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                }
            } else {
                if (btn.getText().equals("Adicionar")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Editar")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Remover")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                } else if (btn.getText().equals("Favoritar")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));

                } else if (btn.getText().equals("Reproduzir")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));

                } else if (btn.getText().equals("Pausar")) {
                    btn.setBackground(new Color(175, 175, 175)); // Modo Claro
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));

                } else {
                    btn.setBackground(null);
                    btn.setForeground(Color.BLACK);
                    btn.setFont(new Font("Monospace", Font.BOLD, 15));
                }
            }
        } else if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            if (escuro) {
                label.setForeground(textoEscuro);
            } else {
                label.setForeground(textoClaro);
            }
        } else if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            if (escuro) {
                scroll.getViewport().setBackground(fundoEscuro);
                scroll.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2, true));
            } else {
                scroll.getViewport().setBackground(fundoClaro);
                scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2, true));
            }
            aplicarTema(scroll.getViewport().getView(), escuro);
        } else if (comp instanceof JList) {
            JList<?> lista = (JList<?>) comp;
            if (escuro) {
                lista.setBackground(new Color(55, 55, 55));
                lista.setForeground(textoEscuro);
                lista.setSelectionBackground(new Color(100, 100, 100));
                lista.setSelectionForeground(textoEscuro);
            } else {
                lista.setBackground(Color.WHITE);
                lista.setForeground(textoClaro);
                lista.setSelectionBackground(new Color(180, 180, 255));
                lista.setSelectionForeground(Color.BLACK);
            }
        }
    }

    // ABAS DE GÊNEROS MUSICAIS
private static void adicionarAba(JTabbedPane painelAbas, String genero, String[] musicasIniciais) {
    JPanel painel = new JPanel(new BorderLayout(10, 10));
    painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel titulo = new JLabel("Playlist de " + genero, SwingConstants.CENTER);
    titulo.setFont(new Font("Monospace", Font.BOLD, 22));
    titulo.setForeground(new Color(0, 0, 0));
    painel.add(titulo, BorderLayout.NORTH);

    DefaultListModel<String> modeloLista = new DefaultListModel<>();
    for (String musica : musicasIniciais) {
        modeloLista.addElement(musica);
    }
    modelos.put(genero, modeloLista);

    JList<String> listaMusicas = new JList<>(modeloLista);
    listaMusicas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listaMusicas.setFont(new Font("Segoe UI", Font.BOLD, 16));
    listaMusicas.setFixedCellHeight(32);

    JScrollPane scrollPane = new JScrollPane(listaMusicas);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2, true));
    painel.add(scrollPane, BorderLayout.CENTER);

    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 30));
    painelBotoes.setBackground(new Color(150, 150, 150));

    // ADICIONANDO TODOS OS BOTÕES

    JButton btnAdicionar = new JButton("Adicionar");
    JButton btnEditar = new JButton("Editar");
    JButton btnRemover = new JButton("Remover");
    JButton btnFavoritar = new JButton("Favoritar");
    JButton btnReproduzir = new JButton("Reproduzir");
    JButton btnPausar = new JButton("Pausar");

    btnAdicionar.setPreferredSize(new Dimension(120, 45));
    btnEditar.setPreferredSize(new Dimension(120, 45));
    btnRemover.setPreferredSize(new Dimension(120, 45));
    btnFavoritar.setPreferredSize(new Dimension(120, 45));
    btnReproduzir.setPreferredSize(new Dimension(120, 45));
    btnPausar.setPreferredSize(new Dimension(120, 45)); 

    painelBotoes.add(btnAdicionar);
    painelBotoes.add(btnEditar);
    painelBotoes.add(btnRemover);
    painelBotoes.add(btnFavoritar);
    painelBotoes.add(btnReproduzir);
    painelBotoes.add(btnPausar);

    painel.add(painelBotoes, BorderLayout.SOUTH);

    painelAbas.addTab(genero, painel);

    // Ações dos botões

    btnAdicionar.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo de música (.wav)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos WAV", "wav"));
        int resultado = fileChooser.showOpenDialog(painel);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String nomeArquivo = arquivoSelecionado.getName();
            if (!nomeArquivo.toLowerCase().endsWith(".wav")) {
                JOptionPane.showMessageDialog(painel, "Selecione apenas arquivos .wav!");
                return;
            }
            String nomeMusica = nomeArquivo.substring(0, nomeArquivo.length() - 4);
            modeloLista.addElement(nomeMusica);

            File pastaAudios = new File("audios");
            if (!pastaAudios.exists()) {
                pastaAudios.mkdir();
            }
            File destino = new File(pastaAudios, nomeArquivo);
            try {
                java.nio.file.Files.copy(arquivoSelecionado.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao copiar o arquivo para a pasta audios.");
            }
        }
    });

    btnEditar.addActionListener(e -> {
        int idx = listaMusicas.getSelectedIndex();
        if (idx != -1) {
            String atual = modeloLista.getElementAt(idx);
            String novoNome = JOptionPane.showInputDialog(painel, "Editar música:", atual);
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                modeloLista.setElementAt(novoNome.trim(), idx);
            }
        } else {
            JOptionPane.showMessageDialog(painel, "Selecione uma música para editar.");
        }
    });

    btnRemover.addActionListener(e -> {
        int index = listaMusicas.getSelectedIndex();
        if (index != -1) {
            int confirm = JOptionPane.showConfirmDialog(painel,
                    "Você tem certeza que quer remover essa música?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modeloLista.removeElementAt(index);
            }
        } else {
            JOptionPane.showMessageDialog(painel, "Selecione uma música para remover.");
        }
    });

    btnFavoritar.addActionListener(e -> {
        int idx = listaMusicas.getSelectedIndex();
        if (idx != -1) {
            String musica = modeloLista.getElementAt(idx);
            JOptionPane.showMessageDialog(painel, "Adicionando à lista de favoritas" + musica);
            if (!modeloFavoritas.contains(musica)) {
                modeloFavoritas.addElement(musica);
            }
        } else {
            JOptionPane.showMessageDialog(painel, "Selecione uma música para favoritar.");
        }
    });

    btnReproduzir.addActionListener(e -> {
        int idx = listaMusicas.getSelectedIndex();
        if (idx != -1) {
            String musica = modeloLista.getElementAt(idx);
            String nomeArquivo = musica.split("\\(")[0].trim();
            String caminho = "audios/" + nomeArquivo + ".wav";
            if (clipAtual != null && clipAtual.isRunning()) {
                clipAtual.stop();
                clipAtual.close();
            }
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(caminho));
                clipAtual = AudioSystem.getClip();
                clipAtual.open(audioStream);
                clipAtual.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Arquivo de áudio não encontrado: " + caminho);
            }
        } else {
            JOptionPane.showMessageDialog(painel, "Selecione uma música para reproduzir.");
        }
    });

    btnPausar.addActionListener(e -> {
        if (clipAtual != null && clipAtual.isRunning()) {
            clipAtual.stop();
        }
    });
}

// ABA DOS FAVORITOS
    private static void adicionarAbaFavoritas(JTabbedPane painelAbas) {
        JPanel painelFavoritas = new JPanel(new BorderLayout(10, 10));
        painelFavoritas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel tituloFavoritas = new JLabel("Playlist Favoritas", SwingConstants.CENTER);
        tituloFavoritas.setFont(new Font("Monospace", Font.BOLD, 22));
        tituloFavoritas.setForeground(new Color(0, 0, 0));
        painelFavoritas.add(tituloFavoritas, BorderLayout.NORTH);

        JList<String> listaFavoritas = new JList<>(modeloFavoritas);
        listaFavoritas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFavoritas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        listaFavoritas.setFixedCellHeight(32);

        JScrollPane scrollFavoritas = new JScrollPane(listaFavoritas);
        scrollFavoritas.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2, true));
        painelFavoritas.add(scrollFavoritas, BorderLayout.CENTER);

        JButton btnRemoverFav = new JButton("Remover das Favoritas");
        btnRemoverFav.setFont(new Font("Monospace", Font.BOLD, 20));
        btnRemoverFav.setPreferredSize(new Dimension(250, 50));
        btnRemoverFav.setBackground(new Color(0, 0, 0));
        btnRemoverFav.setFocusPainted(false);
        JButton btnReproduzir = new JButton("Reproduzir");
        btnReproduzir.setFont(new Font("Monospace", Font.BOLD, 20));
        btnReproduzir.setPreferredSize(new Dimension(120, 45));
        btnReproduzir.setBackground(new Color(255, 105, 180));
        btnReproduzir.setFocusPainted(false);
        JButton btnPausar = new JButton("Pausar");
        btnPausar.setFont(new Font("Monospace", Font.BOLD, 20));
        btnPausar.setPreferredSize(new Dimension(120, 45));
        btnPausar.setBackground(new Color(255, 105, 180));
        btnPausar.setFocusPainted(false);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 30));
        painelBotoes.setBackground(new Color(230, 230, 250));
        painelBotoes.add(btnRemoverFav);
        painelBotoes.add(btnReproduzir);
        painelBotoes.add(btnPausar);

        painelFavoritas.add(painelBotoes, BorderLayout.SOUTH);

        btnRemoverFav.addActionListener(e -> {
            int index = listaFavoritas.getSelectedIndex();
            if (index != -1) {
                int confirm = JOptionPane.showConfirmDialog(painelFavoritas,
                        "Você tem certeza que quer remover das favoritas?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    modeloFavoritas.removeElementAt(index);
                }
            } else {
                JOptionPane.showMessageDialog(painelFavoritas, "Selecione uma música para remover das favoritas.", null,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnReproduzir.addActionListener(e -> {
            int idx = listaFavoritas.getSelectedIndex();
            if (idx != -1) {
                String musica = modeloFavoritas.getElementAt(idx);
                String nomeArquivo = musica.split("\\(")[0].trim();
                String caminho = "audios/" + nomeArquivo + ".wav";
                if (clipAtual != null && clipAtual.isRunning()) {
                    clipAtual.stop();
                    clipAtual.close();
                }
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(caminho));
                    clipAtual = AudioSystem.getClip();
                    clipAtual.open(audioStream);
                    clipAtual.start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(painelFavoritas, "Arquivo de áudio não encontrado: " + caminho);
                }
            } else {
                JOptionPane.showMessageDialog(painelFavoritas, "Selecione uma música para reproduzir.", null,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Função para pausar a músiquinha
        btnPausar.addActionListener(e -> {
            if (clipAtual != null && clipAtual.isRunning()) {
                clipAtual.stop();
            }
        });

        painelAbas.addTab("Favoritas", painelFavoritas);
    }
}
