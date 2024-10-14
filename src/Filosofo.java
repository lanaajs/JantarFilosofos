public class Filosofo extends Thread { // a classe Filosofo herda a funcionalidade da classe Thread

    // constante que define o tempo máx. que um filósofo pode gastar pensando ou comendo (em milissegundos)
    final static int TEMPO_MAXIMO = 1000; // 1 segundo

    // constante que define o tempo limite total que o filósofo vai executar o ciclo de pensar e comer
    final static int TEMPO_LIMITE_EXECUCAO = 60000; // 1 minuto

    Mesa mesa; // instância da classe Mesa gerencia o uso dos garfos compartilhados pelos filósofos
    int idFilosofo; // identificador do filósofo (para diferenciar os filósofos)
    private long totalPensando = 0; // armazenar o tempo total pensando
    private long totalComendo = 0; // armazenar o tempo total comendo

    // construtor da classe Filosofo
    public Filosofo(String nome, Mesa mesaJantar, int numFil) {
        super(nome); // chama o construtor da classe Thread, passando o nome do filósofo
        mesa = mesaJantar; 
        idFilosofo = numFil; 
    }

    @Override // sobrescrevendo o método run() da classe Thread para definir o comportamento da thread
    // este método define o que o filósofo faz durante sua execução (alternando entre pensar e comer)
    public void run() {
        long inicio = System.currentTimeMillis(); // armazena o momento em que a execução do filósofo começou

        // o loop continua enquanto o tempo total de execução não ultrapassar o
        // TEMPO_LIMITE_EXECUCAO
        while (System.currentTimeMillis() - inicio < TEMPO_LIMITE_EXECUCAO) {

            // gera um tempo aleatório (sempre em milissegundos) para o filósofo pensar
            int tempo = (int) (Math.random() * TEMPO_MAXIMO);
            totalPensando += tempo; // Acumula o tempo pensando
            pensar(tempo); // chama o método pensar com o tempo gerado

            // o filósofo tenta pegar os garfos para começar a comer
            mesa.pegarGarfos(idFilosofo);

            // gera um tempo aleatório para o filósofo comer
            tempo = (int) (Math.random() * TEMPO_MAXIMO);
            totalComendo += tempo; // Acumula o tempo comendo
            comer(tempo); // chama o método comer com o tempo gerado

            // o filósofo devolve os garfos após terminar de comer
            mesa.devolverGarfos(idFilosofo);
        }

        // quando o tempo limite termina, o filósofo para de executar e exibe uma
        // mensagem
        System.out.println(getName() + " terminou de executar após atingir o tempo limite.");
    }

    // método para simular o filósofo pensando por um tempo aleatório
    public void pensar(int tempo) {
        System.out.println(getName() + " está pensando por " + tempo + "ms."); // mensagem informando que o filósofo
                                                                               // está pensando

        try {
            Thread.sleep(tempo); // o filósofo "dorme" por um tempo aleatório, simulando o ato de pensar
        } catch (InterruptedException e) {
            System.out.println(getName() + " foi interrompido enquanto pensava."); // eerro caso a thread seja
                                                                                   // interrompida
        }
    }

    // método para simular o filósofo comendo por um tempo aleatório
    public void comer(int tempo) {
        System.out.println(getName() + " está comendo por " + tempo + "ms."); // mensagem informando que o filósofo está
                                                                              // comendo

        try {
            Thread.sleep(tempo); // o filósofo "dorme" por um tempo aleatório, simulando o ato de comer
        } catch (InterruptedException e) {
            System.out.println(getName() + " foi interrompido enquanto comia."); // erro caso a thread seja interrompida
        }
    }

    // métodos para obter os tempos
    public long getTempoPensando() {
        return totalPensando;
    }

    public long getTempoComendo() {
        return totalComendo;
    }
}

/*
 * Um bloco try é chamado de bloco protegido porque, caso ocorra algum problema
 * com os comandos dentro do bloco, a execução desviará para os blocos catch
 * correspondentes.
 * 
 * try >> permite que você defina um bloco de código a ser testado quanto a
 * erros enquanto ele está sendo executado.
 * catch >> permite que você defina um bloco de código a ser executado, caso
 * ocorra um erro no bloco try.
 * InterruptedException >> lançada quando uma thread é interrompida enquanto
 * está esperando (dormindo)
 */