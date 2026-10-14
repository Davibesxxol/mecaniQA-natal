package br.com.mecaniqa;

import java.util.ArrayList;

public class Cliente {

    public int codigo;
    public String nome;
    public String telefone;
    public String email;
    public ArrayList<Carro> carros = new ArrayList<>();

    public String toCSV() {

        return this.nome + ";" + this.telefone + ";" + this.email;
    }

}