package br.com.mecaniqa;

public class Servico {

    public int codigo;
    public String descricao;
    public double tempoEstimadoMinutos;
    public double valorMaoDeObra;

    public String toCSV() {

        return this.codigo + ";" + this.descricao + ";" + this.valorMaoDeObra + ";" + this.tempoEstimadoMinutos;
    }

}
    