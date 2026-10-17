package br.com.mecaniqa;

import java.util.ArrayList;

public class Pedido {

    public int codigo;
    public ArrayList<ItemPedido> itens = new ArrayList<>();
    public double desconto;
    public boolean finalizado = false;

    public void adicionarItem(ItemPedido item) {
        if (finalizado) {
            System.out.println("Erro: pedido ja finalizado, nao e possivel adicionar itens.");
            return;
        }
        itens.add(item);
    }

    public void removerItem(ItemPedido item) {
        if (finalizado) {
            System.out.println("Erro: pedido ja finalizado, nao e possivel remover itens.");
            return;
        }
        itens.remove(item);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.peca.precoVenda * item.quantidade;
        }
        return total - desconto;
    }

    public void imprimirRelatorio() {
        System.out.println("Pedido " + codigo + ":");
        for (ItemPedido item : itens) {
            System.out.println(
                    item.peca.nome + " | Quantidade: " + item.quantidade +
                            " | Preco unitario: R$" + item.peca.precoVenda
            );
        }
        System.out.println("Desconto: R$" + desconto);
        System.out.println("Total: R$" + calcularTotal());
    }

    public void finalizar() {
        finalizado = true;
        System.out.println("Pedido " + codigo + " finalizado com sucesso!");
    }

    public String toCSV() {
        return codigo + ";" + desconto + ";" + finalizado;
    }

    public static Pedido fromCSV(String linha) {
        String[] c = linha.split(";");
        Pedido pedido = new Pedido();
        pedido.codigo = Integer.parseInt(c[0]);
        pedido.desconto = Double.parseDouble(c[1]);
        pedido.finalizado = Boolean.parseBoolean(c[2]);
        return pedido;
    }

}