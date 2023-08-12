import java.util.*;
/**
 * A classe abaixo varia todos os valores possíveis, usando a classe ValueExpression, e assim, formando a tabela verdade.
 */
public class TabelaVerdade {
  /**
   * Recebe a proposição e usa o ValueExpression para determinar o valor de cada linha da tabela verdade, retorna a matriz para a interface usar.
   */
  public static int[][] criarTabela(String proposicao) {
    HashMap<Character, Boolean> mapa = new HashMap<>();
    Set<Character> letras = new HashSet<>();
    char[] letras_ = new char[5];
    int matriz[][] = null;
    proposicao = proposicao.replace(" ", "");
    // Quais são as letras da proposição? -> Percorrer a string, e tudo o que não
    // for '(' ')' '+' '~' '>' '*' adicionamos ao mapa
    for (int i = 0; i < proposicao.length(); i++) {
      char c = proposicao.charAt(i);
      if (c != '(' && c != ')' && c != '+' && c != '>' && c != '*' && c != '~') {
        letras.add(c); // Conjunto de letras do set
      }
    }
    Iterator<Character> iterator = letras.iterator();
    // Extraindo as letras do set
    int k = 0;
    int linha = 0;
    while (iterator.hasNext()) {
      letras_[k] = iterator.next();
      iterator.remove();
      k++; // Número de elementos (-1)
    }
  
    if (k == 1) { // Uma proposição
      matriz = new int[2][2];
      for (int i = 0; i < 2; i++) {
        boolean a = (i == 1);
        matriz[linha][0] = i;
        mapa.put(letras_[0], a);
        matriz[linha][1] = ValueExpression.valor(proposicao, mapa);
        linha++;
      }
    } else if (k == 2) {
      matriz = new int[4][3];
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
          boolean a = (i == 1);
          boolean b = (j == 1);
          matriz[linha][0] = i;
          matriz[linha][1] = j;
          mapa.put(letras_[0], a);
          mapa.put(letras_[1], b);
          matriz[linha][2] = ValueExpression.valor(proposicao, mapa);
          linha++;
        }
      }
    } else if (k == 3) {
      matriz = new int[8][4];
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
          for (int l = 0; l < 2; l++) {
            boolean a = (i == 1);
            boolean b = (j == 1);
            boolean c = (l == 1);
            matriz[linha][0] = i;
            matriz[linha][1] = j;
            matriz[linha][2] = l;
            mapa.put(letras_[0], a);
            mapa.put(letras_[1], b);
            mapa.put(letras_[2], c);
            matriz[linha][3] = ValueExpression.valor(proposicao, mapa);
            linha++;
          }
        }
      }
    } else if (k == 4) {
      matriz = new int[16][5];
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
          for (int l = 0; l < 2; l++) {
            for (int h = 0; h < 2; h++) {
              boolean a = (i == 1);
              boolean b = (j == 1);
              boolean c = (l == 1);
              boolean d = (h == 1);
              matriz[linha][0] = i;
              matriz[linha][1] = j;
              matriz[linha][2] = l;
              matriz[linha][3] = h;
              mapa.put(letras_[0], a);
              mapa.put(letras_[1], b);
              mapa.put(letras_[2], c);
              mapa.put(letras_[3], d);
              matriz[linha][4] = ValueExpression.valor(proposicao, mapa);
              linha++;
            }
          }
        }
      }
    } else if (k == 5) {
      matriz = new int[32][6];
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
          for (int l = 0; l < 2; l++) {
            for (int h = 0; h < 2; h++) {
              for (int g = 0; g < 2; g++) {
                boolean a = (i == 1);
                boolean b = (j == 1);
                boolean c = (l == 1);
                boolean d = (h == 1);
                boolean e = (g == 1);
                matriz[linha][0] = i;
                matriz[linha][1] = j;
                matriz[linha][2] = l;
                matriz[linha][3] = h;
                matriz[linha][4] = g;
                mapa.put(letras_[0], a);
                mapa.put(letras_[1], b);
                mapa.put(letras_[2], c);
                mapa.put(letras_[3], d);
                mapa.put(letras_[4], e);
                matriz[linha][5] = ValueExpression.valor(proposicao, mapa);
                linha++;
              }
            }
          }
        }
      }
    }
    return matriz;
  }
  /**
   * Retorna um vetor de proposições (das letras da proposição) para facilitar o trabalho da interface gráfica
   */
  public static String[] retornar_proposicao(String proposicao) {
    HashMap<Character, Boolean> mapa = new HashMap<>();
    Set<Character> letras = new HashSet<>();
    String[] letras_ = new String[5];
    int matriz[][] = null;
    proposicao = proposicao.replace(" ", "");

    // Quais são as letras da proposição?
    // Percorrer a string, e tudo o que não for '(' ')' '+' '~' '>' '*' adicionamos ao mapa
    proposicao = proposicao.replace(" ", "");
    for (int i = 0; i < proposicao.length(); i++) {
        char c = proposicao.charAt(i);
        if (c != '(' && c != ')' && c != '+' && c != '>' && c != '*' && c != '~') {
            letras.add(c); // Conjunto de letras do set
        }
    }

    Iterator<Character> iterator = letras.iterator();
    int k = 0;
    int linha = 0;
    while (iterator.hasNext()) {
        letras_[k] = String.valueOf(iterator.next());
        iterator.remove();
        k++;
    }
    String[] letras1 = new String[k];
    for (int i = 0; i < k; i++){
      letras1[i] = letras_[i];
    }


    return letras1;
 }

}
