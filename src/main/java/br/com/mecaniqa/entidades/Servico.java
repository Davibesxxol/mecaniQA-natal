package br.com.mecaniqa.entidades;

public class Servico {

    public int codigo;
    public String descricao;
    public double valorMaoDeObra;
    public int tempoEstimadoMinutos;

    public String toCSV() {
        return codigo + ";" + descricao.replace(";", ",") + ";" + valorMaoDeObra + ";" + tempoEstimadoMinutos;
    }

    public static Servico fromCSV(String linha) {
        String[] c = linha.split(";");
        Servico s = new Servico();
        s.codigo = Integer.parseInt(c[0]);
        s.descricao = c[1];
        s.valorMaoDeObra = Double.parseDouble(c[2]);
        s.tempoEstimadoMinutos = Integer.parseInt(c[3]);
        return s;
    }

}