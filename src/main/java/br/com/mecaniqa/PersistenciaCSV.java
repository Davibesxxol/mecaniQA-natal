package br.com.mecaniqa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PersistenciaCSV {

    private static final String PASTA = "dados/";
    private static final String ARQ_PECAS = PASTA + "pecas.csv";
    private static final String ARQ_SERVICOS = PASTA + "servicos.csv";
    private static final String ARQ_CLIENTES = PASTA + "clientes.csv";
    private static final String ARQ_PEDIDOS = PASTA + "pedidos.csv";
    private static final String ARQ_ITENS_PEDIDO = PASTA + "itens_pedido.csv";
    private static final String ARQ_ORDENS = PASTA + "ordens_servico.csv";
    private static final String ARQ_SERVICOS_OS = PASTA + "servicos_os.csv";

    public static boolean existemDados() {
        return new File(ARQ_PECAS).exists();
    }

    public static void salvarTudo() {
        new File(PASTA).mkdirs();

        ArrayList<String> linhas = new ArrayList<>();
        for (int i = 0; i < Gerenciador.totalPecas; i++) {
            linhas.add(Gerenciador.pecas[i].toCSV());
        }
        gravar(ARQ_PECAS, linhas);

        linhas = new ArrayList<>();
        for (int i = 0; i < Gerenciador.totalServicos; i++) {
            linhas.add(Gerenciador.servicos[i].toCSV());
        }
        gravar(ARQ_SERVICOS, linhas);

        linhas = new ArrayList<>();
        for (Cliente c : Gerenciador.clientes) {
            linhas.add(c.toCSV());
        }
        gravar(ARQ_CLIENTES, linhas);

        ArrayList<String> linhasPedidos = new ArrayList<>();
        ArrayList<String> linhasItens = new ArrayList<>();
        for (Pedido p : Gerenciador.pedidos) {
            linhasPedidos.add(p.toCSV());
            for (ItemPedido item : p.itens) {
                linhasItens.add(p.codigo + ";" + item.peca.codigo + ";" + item.quantidade);
            }
        }
        gravar(ARQ_PEDIDOS, linhasPedidos);
        gravar(ARQ_ITENS_PEDIDO, linhasItens);

        ArrayList<String> linhasOrdens = new ArrayList<>();
        ArrayList<String> linhasServicosOS = new ArrayList<>();
        for (OrdemServico os : Gerenciador.ordens) {
            linhasOrdens.add(os.toCSV());
            for (Servico s : os.servicos) {
                linhasServicosOS.add(os.codigo + ";" + s.codigo);
            }
        }
        gravar(ARQ_ORDENS, linhasOrdens);
        gravar(ARQ_SERVICOS_OS, linhasServicosOS);

        System.out.println("Dados salvos na pasta " + PASTA);
    }

    public static void carregarTudo() {
        for (String linha : ler(ARQ_PECAS)) {
            try {
                Gerenciador.carregarPeca(Peca.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_PECAS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_SERVICOS)) {
            try {
                Gerenciador.carregarServico(Servico.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_SERVICOS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_CLIENTES)) {
            try {
                Gerenciador.clientes.add(Cliente.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_CLIENTES + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_PEDIDOS)) {
            try {
                Gerenciador.pedidos.add(Pedido.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_PEDIDOS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_ITENS_PEDIDO)) {
            try {
                String[] c = linha.split(";");
                Pedido pedido = buscarPedido(Integer.parseInt(c[0]));
                int indicePeca = Gerenciador.buscarPeca(Integer.parseInt(c[1]));
                if (pedido == null || indicePeca == -1) {
                    System.out.println("Item ignorado, pedido ou peca nao encontrados: " + linha);
                    continue;
                }
                ItemPedido item = new ItemPedido();
                item.peca = Gerenciador.pecas[indicePeca];
                item.quantidade = Integer.parseInt(c[2]);
                pedido.itens.add(item);
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_ITENS_PEDIDO + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_ORDENS)) {
            try {
                Gerenciador.ordens.add(OrdemServico.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_ORDENS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_SERVICOS_OS)) {
            try {
                String[] c = linha.split(";");
                OrdemServico os = buscarOrdem(Integer.parseInt(c[0]));
                int indiceServico = Gerenciador.buscarServico(Integer.parseInt(c[1]));
                if (os == null || indiceServico == -1) {
                    System.out.println("Servico ignorado, OS ou servico nao encontrados: " + linha);
                    continue;
                }
                os.servicos.add(Gerenciador.servicos[indiceServico]);
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_SERVICOS_OS + ": " + linha);
            }
        }

        for (OrdemServico os : Gerenciador.ordens) {
            if (os.status == StatusOS.AGUARDANDO_EXECUCAO) {
                for (Servico s : os.servicos) {
                    OrdemServico.filaAtendimento.offer(s);
                }
            }
        }

        System.out.println("Dados carregados da pasta " + PASTA);
    }

    private static Pedido buscarPedido(int codigo) {
        for (Pedido p : Gerenciador.pedidos) {
            if (p.codigo == codigo) {
                return p;
            }
        }
        return null;
    }

    private static OrdemServico buscarOrdem(int codigo) {
        for (OrdemServico os : Gerenciador.ordens) {
            if (os.codigo == codigo) {
                return os;
            }
        }
        return null;
    }

    private static void gravar(String arquivo, ArrayList<String> linhas) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            for (String linha : linhas) {
                escritor.write(linha);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar " + arquivo + ": " + e.getMessage());
        }
    }

    private static ArrayList<String> ler(String arquivo) {
        ArrayList<String> linhas = new ArrayList<>();
        if (!new File(arquivo).exists()) {
            return linhas;
        }
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler " + arquivo + ": " + e.getMessage());
        }
        return linhas;
    }

}