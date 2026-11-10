package br.com.mecaniqa.entidades;

import br.com.mecaniqa.enums.EstiloCarro;

public class Carro {

    public int codigo;
    public String modelo;
    public String placa;
    public int ano;
    public EstiloCarro estilo;

    public String toCSV() {

        return this.placa + ";" + this.modelo + ";" + this.estilo;
    }

}