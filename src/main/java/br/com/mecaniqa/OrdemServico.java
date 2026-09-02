package br.com.mecaniqa;

import java.util.ArrayList;

public class OrdemServico {

    public int codigo;
    public ArrayList<Servico> servicos = new ArrayList<>();
    public StatusOS status = StatusOS.EM_ABERTO;

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

}