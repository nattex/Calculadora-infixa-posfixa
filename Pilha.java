public class Pilha {
    // Atributos privados
    private static int TAM_DEFAULT = 100;
    private int topoPilha;
    private int e[];

    // Métodos públicos
    public Pilha(int tamanho) { // construtor 1
        this.e = new int[tamanho];
        this.topoPilha = -1;
    }

    public Pilha() {// construtor 2
        this(TAM_DEFAULT);
    }

    // Verifica se a pilha
    // está vazia
    public boolean isEmpty() {
        if (this.topoPilha == -1)
            return true;
        else
            return false;
    }

    // Verifica se a pilha está
    // cheia
    public boolean isFull() {
        if (this.topoPilha == this.e.length - 1)
            return true;
        else
            return false;
    }

    // insere um elemento e
    // no topo da pilha
    public void push(int e) {
        if (!this.isFull())
            this.e[++this.topoPilha] = e;
        else
            System.out.println("overflow - Estouro de Pilha");
    }

    // remove um elemento
    // do topo da pilha
    public int pop() {
        if (!this.isEmpty())
            return this.e[this.topoPilha--];
        else {
            System.out.println("underflow - Esvaziamento de Pilha");
            return -1;
        }
    }
    // Retorna o elemento que está
    // no topo da pilha

    public int topo() {

        if (!this.isEmpty())
            return this.e[this.topoPilha];
        else {
            System.out.println("Underlow - Esvaziamento de Pilha");
            return -1;
        }
    }

    // obtém o total de elementos
    // armazenados na Pilha
    public int sizeElements() {
        return topoPilha + 1;
    }

    public void invert() {// inverte os elementos de uma pilha
        int buffer[] = new int[100];
        int i = 0, I = 0;
        while (this.isEmpty() == false) {
            buffer[i] = this.pop();
            i++;

        }
        for (I = 0; i > 0; I++) {
            this.push(buffer[I]);
            i--;
        }
    }

    public int peek() {// retorna o topo da pilha sem apagar
        int V;
        V = this.pop();
        this.push(V);
        return V;
    }

    public void aumenta() {
        topoPilha++;
    }

}