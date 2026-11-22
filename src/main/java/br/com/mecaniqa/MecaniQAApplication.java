package br.com.mecaniqa;

import br.com.mecaniqa.csv.PersistenciaCSV;
import br.com.mecaniqa.entidades.Gerenciador;
import br.com.mecaniqa.entidades.OrdemServico;
import br.com.mecaniqa.entidades.Peca;
import br.com.mecaniqa.entidades.Servico;
import br.com.mecaniqa.grafo.Dijkstra;
import br.com.mecaniqa.grafo.Grafo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MecaniQAApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        PersistenciaCSV.carregarTudo();

        do {
            System.out.println("\n--- MecâniQA Gestão ---");
            System.out.println("1. Adicionar Peça");
            System.out.println("2. Listar Peças");
            System.out.println("3. Atualizar Peça");
            System.out.println("4. Remover Peça");
            System.out.println("5. Buscar Peça por Código");
            System.out.println("-----------------------");
            System.out.println("6. Adicionar Serviço");
            System.out.println("7. Listar Serviços");
            System.out.println("8. Atualizar Serviço");
            System.out.println("9. Remover Serviço");
            System.out.println("10. Buscar Serviço por Código");
            System.out.println("-----------------------");
            System.out.println("11. Rota de Entrega e Ordem de Serviço");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, insira um número.");
                continue;
            }

            switch (choice) {
                case 1:
                    adicionarPeca(scanner);
                    break;
                case 2:
                    Gerenciador.listarPecas();
                    break;
                case 3:
                    atualizarPeca(scanner);
                    break;
                case 4:
                    removerPeca(scanner);
                    break;
                case 5:
                    buscarPeca(scanner);
                    break;
                case 6:
                    adicionarServico(scanner);
                    break;
                case 7:
                    Gerenciador.listarServicos();
                    break;
                case 8:
                    atualizarServico(scanner);
                    break;
                case 9:
                    removerServico(scanner);
                    break;
                case 10:
                    buscarServico(scanner);
                    break;
                case 11:
                    executarRotaComOS(scanner);
                    break;
                case 0:
                    PersistenciaCSV.salvarTudo();
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);
    }

    private static void adicionarPeca(Scanner scanner) {
        Peca p = new Peca();

        System.out.print("Nome da peça: ");
        p.nome = scanner.nextLine();

        System.out.print("Fabricante: ");
        p.fabricante = scanner.nextLine();

        System.out.print("Preço de custo: ");
        p.precoCusto = lerDouble(scanner);

        System.out.print("Preço de venda: ");
        p.precoVenda = lerDouble(scanner);

        System.out.print("Quantidade em estoque: ");
        p.quantidadeEstoque = lerInt(scanner);

        Gerenciador.inserirPeca(p);
    }

    private static void atualizarPeca(Scanner scanner) {
        System.out.print("Código da peça a atualizar: ");
        int codigo = lerInt(scanner);

        Peca existente = Gerenciador.buscarPeca(codigo);
        if (existente == null) {
            System.out.println("Peça não encontrada.");
            return;
        }

        Peca novosDados = new Peca();

        System.out.print("Novo nome: ");
        novosDados.nome = scanner.nextLine();

        System.out.print("Novo fabricante: ");
        novosDados.fabricante = scanner.nextLine();

        System.out.print("Novo preço de custo: ");
        novosDados.precoCusto = lerDouble(scanner);

        System.out.print("Novo preço de venda: ");
        novosDados.precoVenda = lerDouble(scanner);

        System.out.print("Nova quantidade em estoque: ");
        novosDados.quantidadeEstoque = lerInt(scanner);

        Gerenciador.atualizarPeca(codigo, novosDados);
    }

    private static void removerPeca(Scanner scanner) {
        System.out.print("Código da peça a remover: ");
        int codigo = lerInt(scanner);

        Gerenciador.removerPeca(codigo);
    }

    private static void buscarPeca(Scanner scanner) {
        System.out.print("Código da peça a buscar: ");
        int codigo = lerInt(scanner);

        Peca p = Gerenciador.buscarPeca(codigo);
        if (p == null) {
            System.out.println("Peça não encontrada.");
            return;
        }

        System.out.println(
                "Codigo: " + p.codigo +
                        " | Nome: " + p.nome +
                        " | Fabricante: " + p.fabricante +
                        " | Preco Custo: R$" + p.precoCusto +
                        " | Preco Venda: R$" + p.precoVenda +
                        " | Estoque: " + p.quantidadeEstoque
        );
    }

    private static void adicionarServico(Scanner scanner) {
        Servico s = new Servico();

        System.out.print("Descrição do serviço: ");
        s.descricao = scanner.nextLine();

        System.out.print("Tempo estimado (minutos): ");
        s.tempoEstimadoMinutos = lerInt(scanner);

        System.out.print("Valor da mão de obra: ");
        s.valorMaoDeObra = lerDouble(scanner);

        Gerenciador.inserirServico(s);
    }

    private static void atualizarServico(Scanner scanner) {
        System.out.print("Código do serviço a atualizar: ");
        int codigo = lerInt(scanner);

        Servico existente = Gerenciador.buscarServico(codigo);
        if (existente == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        Servico novosDados = new Servico();

        System.out.print("Nova descrição: ");
        novosDados.descricao = scanner.nextLine();

        System.out.print("Novo tempo estimado (minutos): ");
        novosDados.tempoEstimadoMinutos = lerInt(scanner);

        System.out.print("Novo valor da mão de obra: ");
        novosDados.valorMaoDeObra = lerDouble(scanner);

        Gerenciador.atualizarServico(codigo, novosDados);
    }

    private static void removerServico(Scanner scanner) {
        System.out.print("Código do serviço a remover: ");
        int codigo = lerInt(scanner);

        Gerenciador.removerServico(codigo);
    }

    private static void buscarServico(Scanner scanner) {
        System.out.print("Código do serviço a buscar: ");
        int codigo = lerInt(scanner);

        Servico s = Gerenciador.buscarServico(codigo);
        if (s == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.println(
                "Codigo: " + s.codigo +
                        " | Descricao: " + s.descricao +
                        " | Tempo estimado (min): " + s.tempoEstimadoMinutos +
                        " | Valor mao de obra: R$" + s.valorMaoDeObra
        );
    }

    private static void executarRotaComOS(Scanner scanner) {
        List<String> filiais = Arrays.asList("CD Central", "Filial A", "Filial B", "Filial C");
        Grafo mapa = new Grafo(filiais.size());
        mapa.adicionarAresta(0, 1, 10);
        mapa.adicionarAresta(0, 2, 25);
        mapa.adicionarAresta(1, 3, 15);
        mapa.adicionarAresta(2, 3, 5);

        System.out.println("\nFiliais disponíveis:");
        for (int i = 0; i < filiais.size(); i++) {
            System.out.println(i + " - " + filiais.get(i));
        }

        System.out.print("ID da origem: ");
        int origem = lerInt(scanner);

        System.out.print("ID do destino: ");
        int destino = lerInt(scanner);

        System.out.print("Código da Ordem de Serviço: ");
        int codigoOS = lerInt(scanner);

        if (origem < 0 || origem >= filiais.size() || destino < 0 || destino >= filiais.size()) {
            System.out.println("Origem ou destino fora do intervalo de filiais.");
            return;
        }

        OrdemServico os = Gerenciador.buscarOS(codigoOS);
        Dijkstra.calcularMenorCaminho(mapa, origem, destino, filiais, codigoOS, os);
    }

    private static int lerInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número inteiro: ");
            }
        }
    }

    private static double lerDouble(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número (use ponto para decimais): ");
            }
        }
    }
}