import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * A classe ArqConfig é responsável por ler, adicionar, deletar linhas e verificar se um arquivo não está vazio.
 */
public class ArqConfig {
    /**
     * Lê o conteúdo de um arquivo linha por linha e retorna como uma lista de strings.
     *
     * @return Uma lista de strings contendo as linhas do arquivo.
     */
     public static ArrayList<String> LerArq() {
        ArrayList<String> proposicoes = new ArrayList<>();

        String file1Name = "prop.txt";
        File f = new File(file1Name);
        try {
            if (f.createNewFile()) {
                System.out.println("Arquivo prop.txt criado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
        }

        try (FileReader file1 = new FileReader(file1Name);
             BufferedReader reader1 = new BufferedReader(file1)) {
            String line;
            while ((line = reader1.readLine()) != null) {
                proposicoes.add(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return proposicoes;
    }
    /**
     * Lê o conteúdo de um arquivo linha por linha e adiciona uma nova
     */
    public static void adicionarLinha(String novaLinha) {
        File arquivo = new File("prop.txt");
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                System.out.println("Arquivo prop.txt criado.");
            }
    
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            StringBuilder conteudo = new StringBuilder();
    
            String linha;
    
            // Lê o arquivo linha por linha e adiciona ao conteúdo
            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
    
            leitor.close();
    
            // Adiciona a nova linha ao conteúdo
            conteudo.append(novaLinha).append("\n");
    
            // Sobrescreve o arquivo com o conteúdo atualizado
            FileWriter escritor = new FileWriter(arquivo);
            escritor.write(conteudo.toString());
            escritor.close();
    
            System.out.println("Nova linha adicionada com sucesso.");
    
        } catch (IOException e) {
            System.out.println("Erro ao adicionar nova linha ao arquivo: " + e.getMessage());
        }
    }
    
    /**
     * Lê o conteúdo de um arquivo linha por linha e remove uma em específico.
     * @param linhaParaDeletar
     */
    public static void deletarLinha(int linhaParaDeletar) {
        File arquivo = new File("prop.txt");
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            StringBuilder conteudo = new StringBuilder();

            String linha;
            int numeroLinha = 1;

            // Lê o arquivo linha por linha
            while ((linha = leitor.readLine()) != null) {
                if (numeroLinha != linhaParaDeletar) {
                    // Se não for a linha para deletar, adiciona ao conteúdo
                    conteudo.append(linha).append("\n");
                }
                numeroLinha++;
            }

            leitor.close();

            // Sobrescreve o arquivo com o conteúdo atualizado
            FileWriter escritor = new FileWriter(arquivo);
            escritor.write(conteudo.toString());
            escritor.close();

            System.out.println("Linha deletada com sucesso.");

        } catch (IOException e) {
            System.out.println("Erro ao deletar a linha do arquivo: " + e.getMessage());
        }
    }    
    /**
     * Verifica se o arquivo não está vazio
     */
    public static boolean arquivoNaoVazio() {
        String nomeArquivo = "prop.txt";
        Path arquivo = Paths.get(nomeArquivo);

        if (Files.exists(arquivo) && Files.isRegularFile(arquivo)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo.toFile()))) {
                String linha = reader.readLine();
                return linha != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}