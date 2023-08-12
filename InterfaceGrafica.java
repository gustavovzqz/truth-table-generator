import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
  /*
   * Classe tela inicial tem seu método construtor que constroi a interface inicial com dois botões e partir deles o ususário é direcionado para 
   * a tela_conferir onde vai buscar as expressões que já estão salvas no arquivo .txt inserido junto ao programa ou tela_inserir onde vai colocar uma nova expressão.
   */
  
class Tela_inicial {

  // primeiramente é preciso criar a interface inicial
  Tela_inicial() {
    JFrame inicial = new JFrame("Tela inicial");
    inicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // botao de inserir nova expressão
    JButton nv_express = new JButton("INSERIR NOVA EXPRESSÃO");
    nv_express.setBounds(10, 30, 200, 20);
    nv_express.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e1) {
        // fechandoo a tela inicial
        inicial.dispose();
        // mostrar a tela de inserir nova tabela
        Tela_Inserir t1 = new Tela_Inserir();

      }
    });

    // botao de procurar expressão ja salva

    JButton cf_express = new JButton("BUSCAR EXPRESSÃO");
    cf_express.setBounds(10, 80, 200, 20);
    cf_express.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e2) {
        // fechandoo a tela inicial
        // mostrar a tela de conferir
        if (ArqConfig.arquivoNaoVazio()) {
          Tela_Conferir t2 = new Tela_Conferir();
          inicial.dispose();
        }
        else{
          JLabel aviso = new JLabel("ABRA O PROGRAMA COM UM ARQUIVO NÃO VAZIO");
          aviso.setBounds(10, 120, 380, 20);
          inicial.add(aviso);
          inicial.revalidate();          
          inicial.repaint();
        }
      }
    });

    inicial.add(cf_express);
    inicial.add(nv_express);

    inicial.setSize(500, 500);
    inicial.setLayout(null);
    inicial.setVisible(true);

  }

}
/*
   * a Classe Tela_inserir é onde o usuário terá a interface de inserção de novas expressões, ao inserir uma nova expressão ele poderá se deparar com a mensagem de erro 
   * caso coloque uma expressão fora da formatação usada nesse trabalho, caso contrário ele será automaticamente levado para a interface tela_tabela, onde verá a tabela 
   * verdade correspondente a expressão inserida.
   */
class Tela_Inserir {
  
  String expressao;

  Tela_Inserir() {
    JFrame inserir = new JFrame("INSERIR NOVA EXPRESSÃO");

    JLabel jl = new JLabel("Insira a expressão: ");
    jl.setBounds(10, 10, 120, 20);

    JTextField tf = new JTextField();
    tf.setBounds(10, 50, 300, 20);
    tf.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e3) {
        if (e3.getKeyCode() == KeyEvent.VK_ENTER) {
          String expressao1 = tf.getText();
          expressao = expressao1.toUpperCase();
          System.out.println(expressao);
          if (validar(expressao) == false) {
            System.out.println("falho");
            JLabel erro = new JLabel("Expressao falha, escreva novamente.");
            erro.setBounds(10, 140, 250, 20);
            inserir.add(erro);
            inserir.revalidate();
            inserir.repaint();
            tf.setText("");
          } else {
            
            // FECHAR TELA_INSERIR

            inserir.dispose();

            // IR PARA TELA_TABELA
            Tela_Tabela t = new Tela_Tabela(expressao);
          }
        }

      }
    });

    JButton enviar = new JButton("Enviar");
    enviar.setBounds(10, 80, 80, 40);

    enviar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e4) {
        String expressao1 = tf.getText();
        expressao = expressao1.toUpperCase();
        System.out.println(expressao);
        if (validar(expressao) == false) {
          System.out.println("falho");
          JLabel erro = new JLabel("Expressao falha, escreva novamente.");
          erro.setBounds(10, 140, 250, 20);
          inserir.add(erro);
          inserir.revalidate();
          inserir.repaint();
          tf.setText("");
        }

        else {

          // FECHAR TELA_INSERIR

          inserir.dispose();

          // IR PARA TELA_TABELA
          Tela_Tabela t = new Tela_Tabela(expressao);
        }
      }
    });

    inserir.add(jl);
    inserir.add(tf);
    inserir.add(enviar);

    inserir.setSize(500, 500);
    inserir.setLayout(null);
    inserir.setVisible(true);
  }


  public boolean validar(String expressao) {

    try {
      Tratamento.verificarformula(expressao);
    } catch (Exception exception) {
      System.out.println(exception);
      return false;
    }

    return true;
  }

}

class Tela_Conferir {
  ArrayList<String> proposicoes1;
  String expressao;

  Tela_Conferir() {
    JFrame inserir = new JFrame("BUSCAR EXPRESSÃO");
    inserir.setLayout(new GridBagLayout());

    proposicoes1 = ArqConfig.LerArq();

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(textArea);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    inserir.add(scrollPane, gbc);

    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < proposicoes1.size(); i++) {
      stringBuilder.append("Índice: ").append(i).append(" - Proposição: ").append(proposicoes1.get(i)).append("\n");
    }

    textArea.setText(stringBuilder.toString());

    JLabel jl = new JLabel("Insira o índice da expressão: ");
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(10, 10, 0, 0);
    inserir.add(jl, gbc);

    JTextField tf = new JTextField();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 10, 0, 10);
    inserir.add(tf, gbc);

    tf.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent k1){
        if(k1.getKeyCode() == KeyEvent.VK_ENTER){
          int indice = Integer.parseInt(tf.getText());
          expressao = proposicoes1.get(indice);
          System.out.println(expressao);
          inserir.dispose();
          Tela_Tabela_2 t = new Tela_Tabela_2(expressao, indice);
          }
        }
    });

    JButton enviar = new JButton("Enviar");
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 10, 10, 0);
    inserir.add(enviar, gbc);

    inserir.setSize(500, 300);
    inserir.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    inserir.setVisible(true);

    enviar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e4) {
        int indice = Integer.parseInt(tf.getText());
        expressao = proposicoes1.get(indice);
        System.out.println(expressao);
        inserir.dispose();
        Tela_Tabela_2 t = new Tela_Tabela_2(expressao, indice);
      }
    });
  }
}
  /*
   * Em tela_tabela o usuário verá a tabela verdade da expressão inserida, ele poderá escolher se quer salvar ou não a expressão em uma arquivo .txt,
   * alem disso, caso não tenha inserido arquivo junto ao programa quando foi executá-lo, um arquivo nomeado prop.txt será criado e a expressão será
   * salva lá. Depois disso o usuário poderá retornar a tela_inicial e refazer todo o processo. 
   */
class Tela_Tabela {
  
  Tela_Tabela(String a) {
    JFrame tabela = new JFrame("TABELA");

    JLabel titulo = new JLabel(a);
    titulo.setBounds(20, 20, 200, 40);
    // criar a tabela

    String[] proposicoes = TabelaVerdade.retornar_proposicao(a);

    int[][] matriz = TabelaVerdade.criarTabela(a);

    JPanel tabelaVerdade = new JPanel();

    // mostrar a tabela

    tabelaVerdade = exibir_tabela(proposicoes, matriz, a);
    tabelaVerdade.setBounds(0, 50, (proposicoes.length) * 150, (proposicoes.length) * 120);

    // botao de salvar
    JButton salvar = new JButton("SALVAR");
    salvar.setBounds(proposicoes.length * 150 + 50, 50, 120, 40);

    // botao de nao salvar
    JButton nsalvar = new JButton("NÃO SALVAR");
    nsalvar.setBounds(proposicoes.length * 150 + 50, 100, 120, 40);

    JButton voltar = new JButton("Voltar para a tela inicial");
    voltar.setBounds(proposicoes.length * 150 + 50, 75, 200, 40);

   
    // o usuario que o a expressão foi salva e voltar para a tela inicial.
    salvar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e5) {

        tabela.remove(salvar);
        tabela.remove(nsalvar);
        tabela.revalidate();
        tabela.repaint();
        ArqConfig.adicionarLinha(a);
        JLabel exito = new JLabel("Expressão salva com sucesso");
        exito.setBounds(50, 400, 200, 40);
        tabela.add(exito);
        tabela.add(voltar);
      }
    });

    nsalvar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e7) {

        tabela.remove(salvar);
        tabela.remove(nsalvar);
        tabela.revalidate();
        tabela.repaint();
        JLabel falha = new JLabel("Expressão não salva com sucesso");
        falha.setBounds(50, 400, 200, 40);

        tabela.add(falha);
        tabela.add(voltar);

      }
    });
    // acoes do botao voltar
    voltar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e6) {
        Tela_inicial t = new Tela_inicial();
        tabela.dispose();
      }
    });

    tabela.add(titulo);
    tabela.add(tabelaVerdade);
    tabela.add(salvar);
    tabela.add(nsalvar);

    tabela.setSize(1000, 1000);
    tabela.setLayout(null);
    tabela.setVisible(true);
  }

  public JPanel exibir_tabela(String[] proposicoes, int[][] tabelaVerdade, String resultado) {
    int numColunas = proposicoes.length + 1;
    int numLinhas = tabelaVerdade.length;

    JPanel tabela = new JPanel();
    tabela.setLayout(new GridLayout(numLinhas + 1, numColunas));

    // Cabeçalho das proposições
    for (String proposicao : proposicoes) {
      JLabel label = new JLabel(proposicao);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      tabela.add(label);
    }

    // Cabeçalho do resultado
    JLabel resultadoLabel = new JLabel(resultado);
    resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
    resultadoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    tabela.add(resultadoLabel);

    // Preencher a tabela verdade
    for (int i = 0; i < numLinhas; i++) {
      for (int j = 0; j < numColunas; j++) {
        JLabel label = new JLabel(String.valueOf(tabelaVerdade[i][j]));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tabela.add(label);
      }
    }

    return tabela;
  }
}

class Tela_Tabela_2 {
  Tela_Tabela_2(String a, int indice) {
    JFrame tabela = new JFrame("TABELA");

    JLabel titulo = new JLabel(a);
    titulo.setBounds(20, 20, 200, 40);
    // criar a tabela

    String[] proposicoes = TabelaVerdade.retornar_proposicao(a);

    int[][] matriz = TabelaVerdade.criarTabela(a);

    JPanel tabelaVerdade = new JPanel();

    // mostrar a tabela

    tabelaVerdade = exibir_tabela(proposicoes, matriz, a);
    tabelaVerdade.setBounds(0, 50, (proposicoes.length) * 150, (proposicoes.length) * 120);

    // botao de salvar
    JButton salvar = new JButton("REMOVER");
    salvar.setBounds(proposicoes.length * 150 + 50, 50, 120, 40);

    // botao de nao salvar
    JButton nsalvar = new JButton("VOLTAR");
    nsalvar.setBounds(proposicoes.length * 150 + 50, 100, 120, 40);

    JButton voltar = new JButton("Voltar para a tela inicial");
    voltar.setBounds(proposicoes.length * 150 + 50, 75, 200, 40);

    // como ja vai ser salvo no tela_inserir o que faremos aqui vai ser só notificar
    // o usuario que o a expressão foi salva e voltar para a tela inicial.
    salvar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e8) {

        tabela.remove(salvar);
        tabela.remove(nsalvar);
        tabela.revalidate();
        tabela.repaint();
        JLabel exito = new JLabel("Expressão removida com sucesso");
        ArqConfig.deletarLinha(indice + 1);
        exito.setBounds(50, 400, 200, 40);
        tabela.add(exito);
        tabela.add(voltar);
      }
    });

    // nao salvar significa excluir a ultima linha do arquivo txt(a ultima expressao
    // salva), notificar o usuario e voltar para a tela inicial
    nsalvar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e7) {

        tabela.remove(salvar);
        tabela.remove(nsalvar);
        tabela.revalidate();
        tabela.repaint();
        JLabel falha = new JLabel("Obrigado por usar o programa!");
        falha.setBounds(50, 400, 200, 40);

        tabela.add(falha);
        tabela.add(voltar);

      }
    });
    // acoes do botao voltar
    voltar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e6) {
        Tela_inicial t = new Tela_inicial();
        tabela.dispose();
      }
    });

    tabela.add(titulo);
    tabela.add(tabelaVerdade);
    tabela.add(salvar);
    tabela.add(nsalvar);

    tabela.setSize(1000, 1000);
    tabela.setLayout(null);
    tabela.setVisible(true);
  }

  public JPanel exibir_tabela(String[] proposicoes, int[][] tabelaVerdade, String resultado) {
    int numColunas = proposicoes.length + 1;
    int numLinhas = tabelaVerdade.length;

    JPanel tabela = new JPanel();
    tabela.setLayout(new GridLayout(numLinhas + 1, numColunas));

    // Cabeçalho das proposições
    for (String proposicao : proposicoes) {
      JLabel label = new JLabel(proposicao);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      tabela.add(label);
    }

    // Cabeçalho do resultado
    JLabel resultadoLabel = new JLabel(resultado);
    resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
    resultadoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    tabela.add(resultadoLabel);

    // Preencher a tabela verdade
    for (int i = 0; i < numLinhas; i++) {
      for (int j = 0; j < numColunas; j++) {
        JLabel label = new JLabel(String.valueOf(tabelaVerdade[i][j]));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tabela.add(label);
      }
    }

    return tabela;
  }
}
