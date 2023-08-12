import java.util.*;

/**
 * Classe que envolve o mecanismo da valoração de uma linha da tabela verdade (recebe a proposição e um mapa onde cada caractere possui um valor)
 */



public class ValueExpression{
     /**
         * A expressão começa assim: (exemplo)
         * 1. ((P * Q) * R)
         * O programa busca o parênteses mais interno, adiciona a expressão em um vetor de expressões, e põe o índice desse vetor na fórmula original
         * (0 * R)
         * Dessa maneira, o programa consegue implementar o Expressão de forma correta.
         * @param formula
         * @param mapa
         */
    public static int valor(String formula, HashMap<Character, Boolean> mapa){
        int qtd_operadores = 0;
        String temp;
        int indice_p = 0;
        int indice_q = 0;
        int i = 0;
        formula = formula.replace(" ", "");
        while (i < formula.length()){ // Acha o número de expressões
            char caractere = formula.charAt(i);
            if (caractere == '(')
                qtd_operadores++;
            i++;
        } 
        i = 0;
        int c = 0;
        Expressao[] operadores = new Expressao[qtd_operadores];
        while(formula.length() != 1){
            i =0;
            while (i < formula.length()){
                char ct = formula.charAt(i);
                if (ct == '(')
                    indice_p = i;
                if (ct == ')'){
                    indice_q = i;
                    break;
                }
                i++;
            } // Retorna o indice dos dois parenteses mais internos indice_p e indice_q
            temp = formula.substring(indice_p + 1, indice_q); // Aqui eu recebo algo do tipo valor- formula- valor ou formula-valor
            int tamanho = temp.length();
            String nova1, nova2, nova3;
            nova1 = formula.substring(0, indice_p);
            nova2 = String.valueOf(c);
            nova3 = formula.substring(indice_q + 1, formula.length());
            formula = nova1 + nova2 + nova3;
            if (tamanho == 2) {
                char first = temp.charAt(0);
                char second = temp.charAt(1);
                if (Character.isDigit(second)){
                    int indice_do_operador = Character.getNumericValue(second);
                    operadores[c] = new Operador(operadores[indice_do_operador], first);
                }
                else{
                    boolean value_of_letter = mapa.get(second);
                    operadores[c] = new Operador(new Operando(value_of_letter), first);
                }
            }
            else if (tamanho == 3){ // p ^ q ... 0 + q ... n ^ n; 0 ^ n; n ^ 0
                char first = temp.charAt(0);
                char second = temp.charAt(1); // Sempre será 'caractere' que corresponderá a formula
                char third = temp.charAt(2);
                if(Character.isDigit(first) && Character.isDigit(third)){ // numero numero
                    int first_index = Character.getNumericValue(first);
                    int second_index = Character.getNumericValue(third);
                    operadores[c] = new Operador(operadores[first_index], operadores[second_index], second);
                }
                else if (Character.isDigit(first) && Character.isLetter(third)){ // numero letra
                    int first_index = Character.getNumericValue(first);
                    boolean value_of_letter = mapa.get(third);
                    operadores[c] = new Operador(operadores[first_index], new Operando(value_of_letter), second);
                }
                else if (Character.isLetter(first) && Character.isDigit(third)){ // letra numero
                    int third_index = Character.getNumericValue(third);
                    boolean value_of_letter = mapa.get(first);
                    operadores[c] = new Operador(operadores[third_index], new Operando(value_of_letter), second);
                }
                else{
                    boolean valor1 = mapa.get(first);
                    Boolean valor2 = mapa.get(third);
                    operadores[c] = new Operador(new Operando(valor1), new Operando(valor2), second);
                }
            }
            c++;
        }

        if(operadores[c-1].calcular())
            return 1;
        return 0;
    }
}

