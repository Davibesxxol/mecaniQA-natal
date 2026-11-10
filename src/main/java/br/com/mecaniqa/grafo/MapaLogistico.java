package br.com.mecaniqa.grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MapaLogistico {

    // Traduz o Nome da Filial (String) para Vértice (Integer) e vice-versa
    public static Map<String, Integer> mapaFiliais = new HashMap<>();
    public static List<String> listaFiliais = new ArrayList<>();
    private static int proximoIdVertice = 0;

    public static Grafo carregarGrafoDoCsv(String caminhoArquivo) {
        System.out.println("--- Iniciando leitura do arquivo logístico: " + caminhoArquivo + " ---");

        // PASSO 1: Ler apenas para descobrir e registrar todas as filiais únicas
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    registrarFilial(dados[0].trim());
                    registrarFilial(dados[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("[Erro] Não foi possível ler o arquivo para mapeamento: " + e.getMessage());
            return null;
        }

        // Instancia o br.com.br.com.mecaniqa.csv.grafo com a quantidade exata de filiais encontradas
        Grafo mapa = new Grafo(listaFiliais.size());

        // PASSO 2: Ler de novo para popular as arestas de mão dupla
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");

                if (dados.length != 3) {
                    System.out.println("[Aviso] Linha com formatação incorreta ignorada: " + linha);
                    continue;
                }

                int origemId = mapaFiliais.get(dados[0].trim());
                int destinoId = mapaFiliais.get(dados[1].trim());

                try {
                    int distancia = Integer.parseInt(dados[2].trim());
                    mapa.adicionarAresta(origemId, destinoId, distancia);
                } catch (NumberFormatException e) {
                    System.out.println("[Erro] Distância inválida ignorada na linha: " + linha);
                }
            }
            System.out.println("--- Mapa carregado com sucesso! " + listaFiliais.size() + " filiais mapeadas. ---");
            return mapa;

        } catch (IOException e) {
            System.out.println("[Erro] Falha ao popular o br.com.br.com.mecaniqa.csv.grafo: " + e.getMessage());
            return null;
        }
    }

    private static void registrarFilial(String nome) {
        if (!mapaFiliais.containsKey(nome)) {
            mapaFiliais.put(nome, proximoIdVertice);
            listaFiliais.add(nome);
            proximoIdVertice++;
        }
    }
}