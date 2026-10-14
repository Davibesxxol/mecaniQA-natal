package br.com.mecaniqa;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class OrdemServico {

    public int codigo;
    public ArrayList<Servico> servicos = new ArrayList<>();
    public StatusOS status = StatusOS.EM_ABERTO;

    public static Queue<Servico> filaAtendimento = new LinkedList<>();

    public void adicionarServico(Servico servico) {
        if (status != StatusOS.EM_ABERTO) {
            System.out.println("Erro: OS nao esta mais aberta, nao e possivel adicionar servicos.");
            return;
        }
        servicos.add(servico);
    }

    public void removerServico(Servico servico) {
        if (status != StatusOS.EM_ABERTO) {
            System.out.println("Erro: OS nao esta mais aberta, nao e possivel remover servicos.");
            return;
        }
        servicos.remove(servico);
    }

    public void mudarStatus(StatusOS novoStatus) {
        status = novoStatus;

        if (novoStatus == StatusOS.AGUARDANDO_EXECUCAO) {
            for (Servico s : servicos) {
                filaAtendimento.offer(s);
            }
            System.out.println("Servicos da OS " + codigo + " despachados para a fila de atendimento.");
        }
    }

    public static Servico atenderProximoServico() {
        Servico proximo = filaAtendimento.poll();
        if (proximo == null) {
            System.out.println("Erro: fila de atendimento vazia.");
            return null;
        }
        return proximo;
    }

    public int quantidadeServicos() {
        return servicos.size();
    }

    public double valorTotal() {
        double total = 0.0;
        for (Servico s : servicos) {
            total += s.valorMaoDeObra;
        }
        return total;
    }

    public void tabelaServicos() {
        System.out.println("Ordem de Servico " + codigo + " - Status: " + status);
        for (Servico s : servicos) {
            System.out.println(
                    s.descricao + " | Tempo estimado (min): " + s.tempoEstimadoMinutos +
                            " | Valor mao de obra: R$" + s.valorMaoDeObra
            );
        }
        System.out.println("Quantidade de servicos: " + quantidadeServicos());
        System.out.println("Valor total: R$" + valorTotal());
    }

    public String toCSV() {

        return this.codigo + ";" + this.status;
    }
}