package models;

public class Cliente {
    private String CPF;
    private String nome;

    public Cliente(String CPF, String nome) {
        this.CPF = CPF;
        this.nome = nome;
    }
    public Cliente(String nome){
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean validaCPF(){
        return CPF.matches("\\d{11}");
    }
    
}
