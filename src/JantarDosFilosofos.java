/* quando o thread inicia, será linhas de execução separadas, no caso 5 linhas, referente aos 5 filosofos
mas todos na mesma mesa, alternando entre comer e pensar */

import java.util.ArrayList; 
import java.util.List; 

public class JantarDosFilosofos {
    public static void main(String[] args) {
        
        int numFilosofos = 5; //número de filósofos

        //cria uma instância da classe Mesa que gerencia os filosofos (garfos)
        Mesa mesa = new Mesa(numFilosofos);

        //lista para armazenar os filósofos
        List<Filosofo> filosofos = new ArrayList<>();

        //criar os filósofos
        for (int i = 0; i < numFilosofos; i++) {

            //cria um novo filósofo, passando seu nome, a mesa e sua posição
            Filosofo filosofo = new Filosofo("Filósofo " + (i + 1), mesa, i);

            filosofos.add(filosofo); //adiciona o filósofo à lista
            filosofo.start(); //inicia a thread do filósofo
        }

        //aguardar todos os filósofos terminarem de comer e pensar
        for (Filosofo filosofo : filosofos) {
            try {
                filosofo.join(); //aguarda ate que termine sua execução
            } catch (InterruptedException e) {
                System.out.println("A execução foi interrompida."); //caso ocorra uma interrupção na espera, exibe uma mensagem
            }
        }

        System.out.println("\nTEMPO TOTAL DOS FILOSOFOS:");

        System.out.printf("%-15s %-20s %-20s %-20s %-20s%n", "NOME DO FILOSOFO", "TEMPO PENSANDO (ms)", "TEMPO PENSANDO (s)",
                "TEMPO COMENDO (ms)", "TEMPO COMENDO (s)");

        //percorre a lista de filósofos para imprimir seus tempos
        for (Filosofo filosofo : filosofos) {
            
            //tem armazenado o tempo que cada filósofo passou pensando e comendo
            long tempoPensandoMs = filosofo.getTempoPensando();
            long tempoComendoMs = filosofo.getTempoComendo();

            //imprime os dados do filósofos em milissegundos e segundos
            System.out.printf("%-15s %-20d %-20.2f %-20d %-20.2f%n",
                    filosofo.getName(),
                    tempoPensandoMs,
                    tempoPensandoMs / 1000.0, // converte para segundos
                    tempoComendoMs,
                    tempoComendoMs / 1000.0); // converte para segundos
        }
    }
}
