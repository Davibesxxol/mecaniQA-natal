package br.com.mecaniqa.csv;

import br.com.mecaniqa.entidades.Gerenciador;
import br.com.mecaniqa.entidades.OrdemServico;
import br.com.mecaniqa.entidades.Peca;
import br.com.mecaniqa.entidades.Servico;
import br.com.mecaniqa.enums.StatusOS;

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
    private static final String ARQ_ORDENS = PASTA + "ordens_servico.csv";
    private static final String ARQ_SERVICOS_OS = PASTA + "servicos_os.csv";

    public static boolean existemDados() {
        return new File(ARQ_PECAS).exists();
    }

    public static void salvarTudo() {
        new File(PASTA).mkdirs();

        ArrayList<String> linhasPecas = new ArrayList<>();
        Gerenciador.percorrerEmOrdem(Gerenciador.raizPecas, reg -> {
            if (reg instanceof Peca p) {
                linhasPecas.add(p.toCSV());
            }
        });
        gravar(ARQ_PECAS, linhasPecas);

        ArrayList<String> linhasServicos = new ArrayList<>();
        Gerenciador.percorrerEmOrdem(Gerenciador.raizServicos, reg -> {
            if (reg instanceof Servico s) {
                linhasServicos.add(s.toCSV());
            }
        });
        gravar(ARQ_SERVICOS, linhasServicos);

        ArrayList<String> linhasOrdens = new ArrayList<>();
        ArrayList<String> linhasServicosOS = new ArrayList<>();
        Gerenciador.percorrerEmOrdem(Gerenciador.raizOS, reg -> {
            if (reg instanceof OrdemServico os) {
                linhasOrdens.add(os.toCSV());
                for (Servico s : os.servicos) {
                    linhasServicosOS.add(os.codigo + ";" + s.codigo);
                }
            }
        });
        gravar(ARQ_ORDENS, linhasOrdens);
        gravar(ARQ_SERVICOS_OS, linhasServicosOS);

        System.out.println("Dados salvos na pasta " + PASTA);
    }

    public static void carregarTudo() {
        for (String linha : ler(ARQ_PECAS)) {
            try {
                Gerenciador.inserirPeca(Peca.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_PECAS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_SERVICOS)) {
            try {
                Gerenciador.inserirServico(Servico.fromCSV(linha));
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_SERVICOS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_ORDENS)) {
            try {
                OrdemServico os = OrdemServico.fromCSV(linha);
                Gerenciador.inserirOS(os);
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_ORDENS + ": " + linha);
            }
        }

        for (String linha : ler(ARQ_SERVICOS_OS)) {
            try {
                String[] c = linha.split(";");
                int codigoOS = Integer.parseInt(c[0]);
                int codigoServico = Integer.parseInt(c[1]);

                OrdemServico os = Gerenciador.buscarOS(codigoOS);
                Servico servico = Gerenciador.buscarServico(codigoServico);

                if (os == null || servico == null) {
                    System.out.println("Servico ignorado, OS ou servico nao encontrados: " + linha);
                    continue;
                }
                os.servicos.add(servico);
            } catch (RuntimeException e) {
                System.out.println("Linha invalida em " + ARQ_SERVICOS_OS + ": " + linha);
            }
        }

        Gerenciador.percorrerEmOrdem(Gerenciador.raizOS, reg -> {
            if (reg instanceof OrdemServico os && os.status == StatusOS.AGUARDANDO_EXECUCAO) {
                for (Servico s : os.servicos) {
                    OrdemServico.filaAtendimento.offer(s);
                }
            }
        });

        System.out.println("Dados carregados da pasta " + PASTA);
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