package br.com.mecaniqa;

import java.util.ArrayList;

public class Cliente {

    public int codigo;
    public String nome;
    public String telefone;
    public String email;
    public ArrayList<Carro> carros = new ArrayList<>();

    public String toCSV() {
        return codigo + ";" + nome.replace(";", ",") + ";" + telefone.replace(";", ",") + ";" + email.replace(";", ",");
    }

    public static Cliente fromCSV(String linha) {
        String[] c = linha.split(";");
        Cliente cliente = new Cliente();
        cliente.codigo = Integer.parseInt(c[0]);
        cliente.nome = c[1];
        cliente.telefone = c[2];
        cliente.email = c[3];
        return cliente;
    }

}