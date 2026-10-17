package br.com.mecaniqa;

public class Peca {

    public int codigo;
    public String nome;
    public String fabricante;
    public double precoCusto;
    public double precoVenda;
    public int quantidadeEstoque;

    public String toCSV() {
        return codigo + ";" + nome.replace(";", ",") + ";" + fabricante.replace(";", ",") + ";"
                + precoCusto + ";" + precoVenda + ";" + quantidadeEstoque;
    }

    public static Peca fromCSV(String linha) {
        String[] c = linha.split(";");
        Peca p = new Peca();
        p.codigo = Integer.parseInt(c[0]);
        p.nome = c[1];
        p.fabricante = c[2];
        p.precoCusto = Double.parseDouble(c[3]);
        p.precoVenda = Double.parseDouble(c[4]);
        p.quantidadeEstoque = Integer.parseInt(c[5]);
        return p;
    }

}