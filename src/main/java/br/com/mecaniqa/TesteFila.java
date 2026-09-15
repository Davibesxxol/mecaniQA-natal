package br.com.mecaniqa;

public class TesteFila {

    public static void main(String[] args) {

        System.out.println("=== QA-01: Politica FIFO da Fila de Atendimento ===");

        Servico servicoA = new Servico();
        servicoA.codigo = 1;
        servicoA.descricao = "Servico A";
        servicoA.tempoEstimadoMinutos = 30;
        servicoA.valorMaoDeObra = 50.0;

        Servico servicoB = new Servico();
        servicoB.codigo = 2;
        servicoB.descricao = "Servico B";
        servicoB.tempoEstimadoMinutos = 45;
        servicoB.valorMaoDeObra = 70.0;

        OrdemServico os = new OrdemServico();
        os.codigo = 1;
        os.adicionarServico(servicoA);
        os.adicionarServico(servicoB);

        System.out.println("Status antes: " + os.status);
        os.mudarStatus(StatusOS.AGUARDANDO_EXECUCAO);
        System.out.println("Status depois: " + os.status);

        System.out.println();
        System.out.println("Requisitando o proximo trabalho...");
        Servico proximo = OrdemServico.atenderProximoServico();
        System.out.println("Servico despachado: " + proximo.descricao);

        System.out.println();
        System.out.println("Requisitando o proximo trabalho novamente...");
        Servico segundo = OrdemServico.atenderProximoServico();
        System.out.println("Servico despachado: " + segundo.descricao);

        System.out.println();
        System.out.println("Requisitando um terceiro (fila deve estar vazia)...");
        OrdemServico.atenderProximoServico();

    }

}