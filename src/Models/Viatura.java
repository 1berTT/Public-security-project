package Models;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Nogueira Bezerra
 */
public class Viatura {

    private int id;
    private Cidade cidade;
    private ArrayList<Cidade> cidades = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
        if (cidade != null) {
            this.cidades.add(cidade);
        }
    }

    public ArrayList<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
    }

}
