package br.com.mecaniqa;

public class Peca {

    public int codigo;
    public String nome;
    public String fabricante;
    public double precoCusto;
    public double precoVenda;
    public int quantidadeEstoque;

    public String toCSV() {

        return this.codigo + ";" + this.nome + ";" + this.fabricante + ";" + this.precoVenda;
    }

}
