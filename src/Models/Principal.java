package Models;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Nogueira Bezerra
 */
public class Principal {

    public static void main(String[] args) {
        Problema p = new Problema();

        try {
            System.out.println("Digite o total de iterações: ");
            Scanner in = new Scanner(System.in);
            int numero = in.nextInt();

            int numeros[] = new int[numero];

            for (int i = 0; i < numero; i++) {
                System.out.println("-----------------------------------------------------------------------------------------------------");
                System.out.println((i+1) + " Iteração");
                String caminho = "C:/Users/gabri/OneDrive/Documentos/Engenharia de Software/7º Semestre - 2019.2/Inteligencia artificial/Seguranca publica/cidades.txt";
                p.preencheProblema(caminho, 5);
                p.algoritmo();
                numeros[i] = p.totalVitimas();
                System.out.println("-----------------------------------------------------------------------------------------------------");
            }
            int menor = numeros[0];
            System.out.println("Total de Vitimas na iteração 1: " + menor);
            for (int i = 1; i < numero; i++) {
                if (numeros[i] < menor) {
                    menor = numeros[i];
                }
                System.out.println("Total de Vitimas na iteração " + (i+1) + ", " + numeros[i]);
            }

            System.out.println("Menor total de vitimas: " + menor);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
