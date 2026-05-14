package com.facens.ac2melissagabriel;

public class Treino {

    private String id;
    private String nomeTreino;
    private String data;
    private String duracao;
    private String Atividade;
    private String Intensidade;

    public Treino(String id,String nomeTreino, String data, String duracao, String Atividade, String Intensidade){

        this.id = id;
        this.nomeTreino = nomeTreino;
        this.data = data;
        this.duracao = duracao;
        this.Atividade = Atividade;
        this.Intensidade = Intensidade;

    }

    public String getId(){
        return id;
    }

    public void setId( String id){
        this.id = id;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setnomeTreino(String nomeTreino ) {
        this.nomeTreino = nomeTreino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data ) {
        this.data = data;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao ) {
        this.duracao = duracao;
    }

    public String getIntensidade() {
        return Intensidade;
    }

    public void setIntensidade(String Intensidade ) {
        this.Intensidade = Intensidade;
    }

    public String getAtividade() {
        return Atividade;
    }

    public void setAtividade( String Atividade ) {
        this.Atividade = Atividade;
    }



}