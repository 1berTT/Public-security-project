package Models;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Nogueira Bezerra
 */
public class Cidade implements Comparable<Cidade> {

    private String nome;
    private boolean visitada;
    private boolean tem_viatura;
    private int seguranca;
    private int grau;
    private int vitimas;
    private ArrayList<Cidade> vizinhos = new ArrayList();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public boolean isTem_viatura() {
        return tem_viatura;
    }

    public void setTem_viatura(boolean tem_viatura) {
        this.tem_viatura = tem_viatura;
    }

    public int getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(int seguranca) {
        this.seguranca = seguranca;
    }

    public ArrayList<Cidade> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(ArrayList<Cidade> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public int getGrau() {
        return grau;
    }

    public void setGrau(int grau) {
        this.grau = grau;
    }

    public void atualiza() {
        if (!isVisitada()) {
            if (getSeguranca() < 10) {
                setSeguranca(getSeguranca() + 1);
            }
            if (getSeguranca() == 10) {
                setVitimas(getVitimas() + 10);
            } else {
                setVitimas(getVitimas() + getSeguranca());
            }
        } else {
            if (isTem_viatura()) {
                if ((getSeguranca() - 1) >= 0) {
                    setSeguranca(getSeguranca() - 1);
                }
            }
        }
    }

    @Override
    public int compareTo(Cidade outraCidade) {
        if (this.seguranca < outraCidade.getSeguranca()) {
            return -1;
        } else if (this.seguranca > outraCidade.getSeguranca()) {
            return 1;
        }

        return 0;
    }

    public int getVitimas() {
        return vitimas;
    }

    public void setVitimas(int vitimas) {
        this.vitimas = vitimas;
    }

}
