package Models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Gabriel Nogueira Bezerra
 */
public class Problema {

    private List<Cidade> cidades;
    private ArrayList<Distancia> distancias;
    private ArrayList<Viatura> viaturas;
    private int dias = 0;

    // atualiza as vitimas quando o nivel de segurança baixa?
    // dar uma ideia de buscar proxima cidade caso os vizinhos já estejam vizitados
    // qual proxima cidade visita com base na distancia x segurança
    public void algoritmo() {

        int i = 0;
        // as três primeiras viaturas irão para as cidades de maior prioridade.
        // no caso a prioridade é a maior segurança
        // e as duas viaturas que restaram, irão para cidades aleatorias
        for (Viatura viatura : viaturas) {
            if (i < 3) {
                viatura.setCidade(cidades.get(i));
                cidades.get(i).setTem_viatura(true);
                cidades.get(i).setVisitada(true);
            } else {
                int numero;
                do {
                    Random r = new Random();
                    numero = r.nextInt(49);
                } while (cidades.get(numero).isVisitada());

                viatura.setCidade(cidades.get(numero));
                cidades.get(i).setTem_viatura(true);
                cidades.get(i).setVisitada(true);
            }
            i++;
        }

        while (temCidade()) {
            dias++;
            atualizaCidades();
            for (Viatura viatura : viaturas) {
                if (viatura.getCidade().getSeguranca() == 0) {
                    viatura.getCidade().setTem_viatura(false);
                    Cidade _proxima = proximaCidade(viatura.getCidade());
                    if (_proxima != null) {
                        _proxima.setVisitada(true);
                        _proxima.setTem_viatura(true);
                        viatura.setCidade(_proxima);
                    }
                }
            }
        }
        mostraResultado();
    }

    private void mostraResultado() {
        System.out.println("Total de dias: " + dias);
        for (Viatura viatura : viaturas) {
            System.out.println("Viatura: " + viatura.getId() + ", percorreu: ");
            for (Cidade cidade : viatura.getCidades()) {
                System.out.println("Cidade: "
                        + cidade.getNome()
                        + ", nivel de segurança: "
                        + cidade.getSeguranca()
                        + ", vitimas: " + cidade.getVitimas());
            }
            System.out.println("");
        }

    }

    private Cidade buscaCidade(String nome) {
        for (Cidade cidade : cidades) {
            if (cidade.getNome().equals(nome)) {
                return cidade;
            }
        }
        return null;
    }

    private boolean temCidade() {
        return cidades.stream().anyMatch((cidade) -> (!cidade.isVisitada() || (cidade.isVisitada() && cidade.getSeguranca() > 0)));
    }

    public int totalVitimas() {
        int total = 0;
        for (Cidade cidade : cidades) {
            total += cidade.getVitimas();
        }

        return total;
    }

    private Distancia buscaDistancia(Cidade cidadeA, Cidade cidadeB) {
        for (Distancia distancia : distancias) {
            if (distancia.getCidadeA().getNome().equals(cidadeA.getNome())) {
                if (distancia.getCidadeB().getNome().equals(cidadeB.getNome())) {
                    return distancia;
                }
            }
        }

        return null;

    }

    // verifica se está correto
    // distancia ao quadrado verificar
    private Cidade proximaCidade(Cidade cidade) {

        if (cidade.getVizinhos() != null) {

            if (cidade.getVizinhos().size() > 0) {

                Cidade _cidade = null;

                for (int i = 0; i < cidade.getVizinhos().size(); i++) {
                    if (!cidade.getVizinhos().get(i).isVisitada()) {
                        _cidade = cidade.getVizinhos().get(i);
                        Cidade _proximo = null;
                        for (int j = 0; j < cidade.getVizinhos().size(); j++) {
                            if (!cidade.getVizinhos().get(j).isVisitada()) {
                                _proximo = cidade.getVizinhos().get(j);
                                if (!_proximo.getNome().equals(_cidade.getNome())) {
                                    Distancia distanciaA = buscaDistancia(cidade, _cidade);
                                    Distancia distanciaB = buscaDistancia(cidade, _proximo);

                                    // essa é a metrica selecionada para descobrir qual cidade o algoritmo vai escolher
                                    double pesoA = (distanciaA.getDistancia() * distanciaA.getDistancia()) / (_cidade.getSeguranca());
                                    double pesoB = (distanciaB.getDistancia() * distanciaB.getDistancia()) / (_proximo.getSeguranca());

                                    if (pesoB > pesoA) {
                                        _cidade = _proximo;
                                    }
                                }
                            }
                        }
                    }
                }

                return _cidade;
            } else {
                return null;
            }
        }

        return null;
    }

    private void preencheViaturas(int numeroViaturas) {
        viaturas = new ArrayList<>();

        for (int i = 0; i < numeroViaturas; i++) {
            Viatura viatura = new Viatura();
            viatura.setCidade(null);
            viatura.setId(i + 1);
            viaturas.add(viatura);
        }
    }

    private void preencheCidades(String caminho) throws FileNotFoundException, IOException {

        // coloco o arquivo na memoria
        BufferedReader arquivo = new BufferedReader(new FileReader(caminho));
        cidades = new ArrayList<>();
        distancias = new ArrayList<>();

        String linha = "";
        while (true) {

            if (linha == null) {
                break;
            }

            linha = linha.trim();
            if (!(linha.equals(""))) {
                // pego as informações de cada linha do arquivo
                String informacoes[] = linha.split(" ");
                // busco se já existe a cidade na memoria
                Cidade cidadeA = buscaCidade(informacoes[0]);
                // se não estiver na memoria eu crio e coloco na memoria
                if (cidadeA == null) {
                    cidadeA = new Cidade();
                    cidadeA.setNome(informacoes[0]);
                    cidadeA.setSeguranca(0);
                    cidadeA.setTem_viatura(false);
                    cidadeA.setVisitada(false);
                    cidadeA.setSeguranca(0);
                    cidades.add(cidadeA);
                }
                // busco se já existe a cidade na memoria
                Cidade cidadeB = buscaCidade(informacoes[1]);
                // se não estiver na memoria eu crio e coloco na memoria
                if (cidadeB == null) {
                    cidadeB = new Cidade();
                    cidadeB.setNome(informacoes[1]);
                    cidadeB.setSeguranca(0);
                    cidadeB.setTem_viatura(false);
                    cidadeB.setVisitada(false);
                    cidadeB.setSeguranca(0);
                    cidades.add(cidadeB);
                }

                cidadeA.getVizinhos().add(cidadeB);
                cidadeA.setGrau(cidadeA.getGrau() + 1);

                Distancia distancia = new Distancia();
                distancia.setCidadeA(cidadeA);
                distancia.setCidadeB(cidadeB);
                distancia.setDistancia(Float.parseFloat(informacoes[2].replaceAll("km", "")));
                distancias.add(distancia);
            }

            linha = arquivo.readLine();
        }
    }

    private void ordenaCidades() {
        Collections.sort(cidades);
    }

    public void preencheProblema(String caminho, int numeroViaturas) throws FileNotFoundException, IOException {
        preencheViaturas(numeroViaturas);
        preencheCidades(caminho);
        ordenaCidades();
    }

    private void atualizaCidades() {
        cidades.forEach((cidade) -> {
            cidade.atualiza();
        });
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Cidade> cidades) {
        this.cidades = cidades;
    }

    public ArrayList<Distancia> getDistancias() {
        return distancias;
    }

    public void setDistancias(ArrayList<Distancia> distancias) {
        this.distancias = distancias;
    }

    public ArrayList<Viatura> getViaturas() {
        return viaturas;
    }

    public void setViaturas(ArrayList<Viatura> viaturas) {
        this.viaturas = viaturas;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

}
