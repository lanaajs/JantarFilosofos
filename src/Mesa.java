class Mesa {

    // array representa o estado dos garfos, cada posição indica se o garfo está
    // sendo usado (true) ou não (false).
    private final boolean[] garfos;

    // construtor da classe Mesa, recebe o número de filósofos (e consequentemente o
    // número de garfos)
    public Mesa(int numFilosofos) {
        garfos = new boolean[numFilosofos]; // inicializa o array de garfos, todos começam como "false"
    }

    // apenas uma thread pode executar o bloco de código ou método por vez
    public synchronized void pegarGarfos(int filosofo) {

        int garfoEsquerdo = filosofo; // o garfo à esquerda é o de mesmo índice que o filósofo.
        int garfoDireito = (filosofo + 1) % garfos.length; // índice do garfo à direita nunca ultrapasse o tamanho do
                                                           // array garfos

        // enquanto um dos garfos (esquerdo ou direito) estiver ocupado, o filósofo
        // espera
        while (garfos[garfoEsquerdo] || garfos[garfoDireito]) {

            try {
                wait(); // pause sua execução até que outra thread a notifique que pode continuar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // restaurando o estado de interrupção da thread
            }
        }

        // quando ambos os garfos estão livres, o filósofo os pega (seta os valores no
        // array como "true")
        garfos[garfoEsquerdo] = true;
        garfos[garfoDireito] = true;
    }

    // método sincronizado que permite ao filósofo devolver os garfos após comer
    public synchronized void devolverGarfos(int filosofo) {

        int garfoEsquerdo = filosofo;
        int garfoDireito = (filosofo + 1) % garfos.length;

        garfos[garfoEsquerdo] = false;
        garfos[garfoDireito] = false;

        // notifica todos os outros filósofos que os garfos estão livres, acordando as
        // threads que estavam esperando
        notifyAll();
    }
}