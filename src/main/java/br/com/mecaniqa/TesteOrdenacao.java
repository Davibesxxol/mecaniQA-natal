package br.com.mecaniqa;

public class TesteOrdenacao {

    public static void main(String[] args) {

        Peca p1 = new Peca();
        p1.nome = "Ziper";
        p1.fabricante = "Generica";
        p1.precoCusto = 5.0;
        p1.precoVenda = 10.0;
        p1.quantidadeEstoque = 3;

        Peca p2 = new Peca();
        p2.nome = "Amortecedor";
        p2.fabricante = "Monroe";
        p2.precoCusto = 180.0;
        p2.precoVenda = 260.0;
        p2.quantidadeEstoque = 6;

        Peca p3 = new Peca();
        p3.nome = "Filtro";
        p3.fabricante = "Bosch";
        p3.precoCusto = 20.0;
        p3.precoVenda = 35.0;
        p3.quantidadeEstoque = 12;

        Gerenciador.inserirPeca(p1);
        Gerenciador.inserirPeca(p2);
        Gerenciador.inserirPeca(p3);

        System.out.println();
        System.out.println("=== Antes de ordenar ===");
        Gerenciador.listarPecas();

        System.out.println();
        System.out.println("=== QA-06: ordenando por NOME ===");
        Gerenciador.ordenarPecas(ChaveOrdenacao.NOME);
        Gerenciador.listarPecas();

        System.out.println();
        System.out.println("=== QA-06: ordenando por CODIGO_IDENTIFICADOR ===");
        Gerenciador.ordenarPecas(ChaveOrdenacao.CODIGO_IDENTIFICADOR);
        Gerenciador.listarPecas();

        Servico s1 = new Servico();
        s1.descricao = "Troca de oleo";
        s1.tempoEstimadoMinutos = 40;
        s1.valorMaoDeObra = 80.0;

        Servico s2 = new Servico();
        s2.descricao = "Alinhamento";
        s2.tempoEstimadoMinutos = 60;
        s2.valorMaoDeObra = 120.0;

        Gerenciador.inserirServico(s1);
        Gerenciador.inserirServico(s2);

        System.out.println();
        System.out.println("=== Servicos ordenados por NOME ===");
        Gerenciador.ordenarServicos(ChaveOrdenacao.NOME);
        Gerenciador.listarServicos();

    }


}