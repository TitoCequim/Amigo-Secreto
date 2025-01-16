import java.io.*;
import java.util.*;

public class AmigoSecreto {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> resultados = new HashMap<>();
    private static final String ARQUIVO_RESULTADOS = "resultados_amigo_secreto.txt";
    private static String responsavel;

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao programa de Amigo Secreto!");

        // Configurar responsável
        configurarResponsavel();

        // Obter nomes dos participantes
        List<String> participantes = obterParticipantes();

        // Realizar sorteio
        realizarSorteio(participantes);

        // Salvar resultados
        salvarResultados();

        // Exibir opções para o responsável
        menuResponsavel();
    }

    private static void configurarResponsavel() {
        System.out.println("Digite o nome do responsável pelo sorteio:");
        responsavel = scanner.nextLine().trim();
        System.out.println("Responsável configurado como: " + responsavel);
    }

    private static List<String> obterParticipantes() {
        List<String> participantes = new ArrayList<>();
        System.out.println("Insira os nomes dos participantes. Digite 'fim' para encerrar.");
        while (true) {
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.equalsIgnoreCase("fim")) {
                break;
            }
            if (participantes.contains(nome)) {
                System.out.println("Erro: Nome duplicado. Insira outro nome.");
            } else {
                participantes.add(nome);
            }
        }
        if (participantes.size() < 2) {
            System.out.println("Erro: É necessário no mínimo dois participantes para o sorteio.");
            return obterParticipantes();
        }
        return participantes;
    }

    private static void realizarSorteio(List<String> participantes) {
        List<String> sorteados = new ArrayList<>(participantes);
        Collections.shuffle(sorteados);

        for (String participante : participantes) {
            for (Iterator<String> iterator = sorteados.iterator(); iterator.hasNext(); ) {
                String sorteado = iterator.next();
                if (!sorteado.equals(participante)) {
                    resultados.put(participante, sorteado);
                    iterator.remove();
                    break;
                }
            }
        }
        System.out.println("Sorteio realizado com sucesso!");
    }

    private static void salvarResultados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RESULTADOS))) {
            for (Map.Entry<String, String> entry : resultados.entrySet()) {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
            System.out.println("Resultados salvos no arquivo: " + ARQUIVO_RESULTADOS);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os resultados: " + e.getMessage());
        }
    }

    private static void menuResponsavel() {
        while (true) {
            System.out.println("Opções para o responsável:");
            System.out.println("1. Visualizar todos os pares");
            System.out.println("2. Consultar resultado individual");
            System.out.println("3. Encerrar programa");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    visualizarTodosPares();
                    break;
                case "2":
                    consultarResultadoIndividual();
                    break;
                case "3":
                    System.out.println("Programa encerrado.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void visualizarTodosPares() {
        System.out.print("Confirme o nome do responsável: ");
        String nome = scanner.nextLine().trim();
        if (nome.equals(responsavel)) {
            System.out.println("Pares do Amigo Secreto:");
            for (Map.Entry<String, String> entry : resultados.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        } else {
            System.out.println("Erro: Somente o responsável pode visualizar todos os pares.");
        }
    }

    private static void consultarResultadoIndividual() {
        System.out.print("Digite seu nome para consultar seu amigo secreto: ");
        String nome = scanner.nextLine().trim();
        if (resultados.containsKey(nome)) {
            System.out.println("Seu amigo secreto é: " + resultados.get(nome));
        } else {
            System.out.println("Erro: Nome não encontrado no sorteio.");
        }
    }
}
