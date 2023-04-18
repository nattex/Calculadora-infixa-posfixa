import java.util.*;
import java.math.*;

class Main {
    // primeiro fizemos um repositorio de funçoes que serao utilizadas durante o
    // programa

    public static char[] pegaVariavel(String equaçao) { // retorna uma pilha ccom todas a variaveis como letra
                                                        // maiusccula
        int i, I = 0;
        char variaveis[] = new char[100];// variaveis podem ser de A a Z
        for (i = 0; i < equaçao.length(); i++) {
            if ((int) equaçao.charAt(i) >= 65 && (int) equaçao.charAt(i) <= 90) {// utilizamos tabela ASSCI para a
                                                                                 // identificaçao
                if (!contains(variaveis, equaçao.charAt(i))) {// se a variavel ja nao estiver registrada
                    variaveis[I] = equaçao.charAt(i);// adiciona no registro de variaveis
                    I++;
                }

            } else {// mesma funçao mas para letras minusculas PS:letras minusculas serao
                    // consideradas como letras maisculas entao variavel 'A' e 'a' sao a mesma
                    // variavel
                if ((int) equaçao.charAt(i) >= 97 && (int) equaçao.charAt(i) <= 122) {
                    if (!contains(variaveis, (char) ((int) equaçao.charAt(i) + 32))) {
                        variaveis[I] = (char) ((int) equaçao.charAt(i) + 32);
                        I++;
                    }
                }
            }

        }

        return variaveis;// retorno
    }
    ///////////////////////////////////////////////////////

    private static int precedence(char operator) {// usado para saber a ordem das operaçoes na conversao da prefixa para
                                                  // a pos
        switch (operator) {
            case '+':
            case '-':
                return 1;// menor prioridade
            case '*':
            case '/':
                return 2;// prioridade media
            case '^':
                return 3;// prioridade alta
            default:
                return -1;// vai que da pau ne
        }
    }
    //////////////////////////////////////////////////////

    public static boolean eVariavel(int n) {// usado para verificar se um elemento da operaçao é uma variavel
        if ((int) n >= 65 && (int) n <= 90) {// faz check usando tabela ASSCI se valor estiver entre 65(A) ate 90(Z)
                                             // retorna true
            return true;
        } else {
            if ((int) n >= 97 && (int) n <= 122) {// mesmo conceito mas com letra minuscula
                return true;
            }
        }
        return false;
    }
    /////////////////////////////////////////////////////

    public static String infixaPosfixa(String infix) {// funçao de conversao de equaçao da ordem infixa para posfixa

        Pilha pilha = new Pilha(100);
        StringBuilder postfix = new StringBuilder();
        for (int i = 0; i < infix.length(); i++) {
            int c = (int) infix.charAt(i);
            // Ignora espaços em branco
            if (c == 32) {
                continue;
            }
            // Se c é um dígito ou uma letra, adiciona ao resultado posfixo
            if (eVariavel(c)) {
                postfix.append((char) c);
            }

            else if (c == 40) {// retira abre parenteses
                pilha.push((int) c);
            }

            else if (c == 41) {// retira fecha parenteses
                while (!pilha.isEmpty() && pilha.peek() != 40) {
                    postfix.append((char) pilha.pop());
                }

                pilha.pop();
            }
            // Se c é um operador, desempilha todos os operadores de maior ou igual
            // precedência e adiciona ao resultado posfixo
            else {
                while (!pilha.isEmpty() && precedence((char) c) <= precedence((char) pilha.peek())) {
                    postfix.append((char) pilha.pop());
                }

                pilha.push(c);
            }
        }

        // Desempilhando todos os operadores restantes na pilha e adicionando ao
        // resultado posfixo
        while (!pilha.isEmpty()) {
            postfix.append((char) pilha.pop());
        }

        return postfix.toString();// retorno
    }
    ////////////////////////////////////////////////////

    public int[] pegaValor(Pilha variaveis) { // funçao de input do valor das variaveis por pilha
        Scanner ent = new Scanner(System.in);
        char V;
        int N = 0;

        int[] valorVariaveis = new int[100]; // cria o array que vai ser estocado os valores
        while (variaveis.isEmpty() == false) {// enquanto todas as variaveis forem designadas um valor

            V = (char) variaveis.pop();// pega uma variavel
            System.out.format("digite o valor de %c:\n", V); // print
            valorVariaveis[N] = ent.nextInt();// pega o valor e guarda
            N++; // so para dar track

        }
        return valorVariaveis;// retorno
    }
    ///////////////////////////////////////////////////

    public static Pilha EquaçaoPilha(String equaçao) { // retorna uma pilha com todos os elementos da equaçao
        int i;
        Pilha variaveis = new Pilha(100);// gera a pilha
        for (i = 0; i < equaçao.length(); i++) {// percorre todos os elementos da string e
            variaveis.push(equaçao.charAt(i));// coloca na pilha
        }

        variaveis.invert();// desinverte a seguencia na pilha
        return variaveis;
    }
    /////////////////////////////////////////////////

    public static boolean contains(char test[], char V) {// teste para verificar se um array de char contain alguma
                                                         // variavel
        for (int i = 0; i < test.length; i++) {
            if (test[i] == V) {
                return true;
            }
        }
        return false;
    }
    ////////////////////////////////////////////////

    public static String leInfixa() {// funçao de input da equaçao infixa
        System.out.println("a equaçao infixa é:");
        String buffer;
        Scanner sc = new Scanner(System.in);
        buffer = sc.nextLine();
        buffer.replaceAll(" ", "");
        return buffer;

    }
    ////////////////////////////////////////////////

    public static int[] pegaValor(char[] variaveis) {// funçao de input do valor das variaveis por vetor
        int i = 0;
        Scanner SC = new Scanner(System.in);
        int valores[] = new int[100];

        for (i = 0; i < variaveis.length; i++) {
            if (variaveis[i] == 0) {
                i = variaveis.length;
            } else {
                System.out.format("valor da variavel %c é :", variaveis[i]);
                valores[i] = SC.nextInt();
            }
        }

        return valores;

    }
    ////////////////////////////////////////////////

    public static int calcula(String posfixa, char[] variaveis, int[] valorVariaveis) {// funçao calculadora
        Pilha equaçao = EquaçaoPilha(posfixa);// joga a equaçao em pilha
        int n = 0, N, control = 0;// variaveis de controle
        int[] temp;// array de buffer
        char operador;// char para leitura de operador
        char[] operadores = { '+', '-', '*', '/', '^' }; // operadores
        char[] buffer = new char[100]; // buffer para as variaveis
        char A = ' ', B = ' ';// buffer para as ultimas 2 variaveis lidas (quais serao usadas nas equaçoes)
        while (equaçao.isEmpty() == false) {// enquanto a equaçao nao for totalmente calculada
            while (equaçao.isEmpty() == false && !contains(operadores, (char) equaçao.peek())) {// teste para ver se
                                                                                                // proximo é um operador
                if (control == 0) {// caso 1 estoca variavel em A
                    control = 1;
                    A = (char) equaçao.pop();// guarda variavel em A

                } else {
                    if (control == 1) {// caso 2 estoca variavel em B
                        control = 2;
                        B = (char) equaçao.pop();// guarda variavel em B

                    } else {
                        if (control == 2) {// se um operador nao for achado ainda

                            buffer[n] = A;// estoca valor de A em buffer
                            A = B;// trasfere valor de B para A
                            B = (char) equaçao.pop();// pega proxima variavel e estoca em B
                            n++;

                        }
                    }
                }
            }
            control = 0;// reseta os casos

            if (equaçao.isEmpty() == false) {// teste para garantir que a pilha n esta vazia
                operador = (char) equaçao.pop();// pega o operador
                if (operador == '+') {// operaçao de mais
                    // como a pilha esta estocando somente variaveis e os valores de tais variaveis
                    // estao estocadas em vetor tivemos que dar uma improvisada
                    N = substituir(A, variaveis, valorVariaveis) + substituir(B, variaveis, valorVariaveis); // substuir
                                                                                                             // muda de
                                                                                                             // Variavel
                                                                                                             // como 'A'
                                                                                                             // para seu
                                                                                                             // valor
                                                                                                             // como 12
                                                                                                             // e faz a
                                                                                                             // operaçao
                    temp = adicionar(variaveis, valorVariaveis);// para podermos jogar o valor de volta a pillha temos
                                                                // que saber que variavel ainda nao foi alocada e onde
                                                                // poder alocar isso que adicionar retorna
                    variaveis[temp[1]] = (char) temp[0];// adicionamos a nova variavel ao array de variaveis
                    valorVariaveis[temp[1]] = N;// adicionamos seu valor ao array de valores
                    equaçao.push((char) temp[0]);// empurramos a nova variavel para pilha
                }
                if (operador == '-') {// operaçao de -
                    // o mesmo conceito somente mudando a operaçao
                    N = substituir(A, variaveis, valorVariaveis) - substituir(B, variaveis, valorVariaveis);
                    temp = adicionar(variaveis, valorVariaveis);
                    variaveis[temp[1]] = (char) temp[0];
                    valorVariaveis[temp[1]] = N;
                    equaçao.push((char) temp[0]);
                }
                if (operador == '/') {// operaçao /
                    // o mesmo conceito somente mudando a operaçao
                    N = substituir(A, variaveis, valorVariaveis) / substituir(B, variaveis, valorVariaveis);
                    temp = adicionar(variaveis, valorVariaveis);
                    variaveis[temp[1]] = (char) temp[0];
                    valorVariaveis[temp[1]] = N;
                    equaçao.push((char) temp[0]);
                }
                if (operador == '*') {// operaçao de *
                    // o mesmo conceito somente mudando a operaçao
                    N = substituir(A, variaveis, valorVariaveis) * substituir(B, variaveis, valorVariaveis);
                    temp = adicionar(variaveis, valorVariaveis);
                    variaveis[temp[1]] = (char) temp[0];
                    valorVariaveis[temp[1]] = N;
                    equaçao.push((char) temp[0]);
                }
                if (operador == '^') {// operaçao de ^
                    // o mesmo conceito somente mudando a operaçao
                    N = pow(substituir(A, variaveis, valorVariaveis), substituir(B, variaveis, valorVariaveis));// tivemos
                                                                                                                // que
                                                                                                                // fazer
                                                                                                                // uma
                                                                                                                // funçao
                                                                                                                // pow
                                                                                                                // por
                                                                                                                // conta
                                                                                                                // de
                                                                                                                // estarmos
                                                                                                                // usando
                                                                                                                // int n
                                                                                                                // double
                    temp = adicionar(variaveis, valorVariaveis);
                    variaveis[temp[1]] = (char) temp[0];
                    valorVariaveis[temp[1]] = N;
                    equaçao.push((char) temp[0]);
                }
                for (; n > 0; n--) {// coloca o buffer de volta a pilha
                    equaçao.push(buffer[n - 1]);

                }
            }
        }
        int resultado = substituir(A, variaveis, valorVariaveis);// pega o valor do resultado
        return resultado;// retorna o resultado
    }
    ////////////////////////////////////////////////

    public static int substituir(char variavel, char[] variaveis, int[] valorVariaveis) {// recebe a variavel e retorna
                                                                                         // seu valor
        int i = 0;
        while (variaveis[i] != variavel) {
            i++;
        }
        return valorVariaveis[i];
    }
    ////////////////////////////////////////////////

    public static int[] adicionar(char[] variaveis, int[] valorVariaveis) {// retorna qual variavel ainda nao esta
                                                                           // alocada e onde pode ser alocada

        int i = 65, I = 0;
        while (contains(variaveis, (char) i)) {
            i++;
            if (i == 90) {
                i += 7;
            }
        }
        while ((int) variaveis[I] != 0) {
            I++;
        }
        int[] resultado = { i, I };
        return resultado;

    }
    ////////////////////////////////////////////////

    public static boolean validaçao(String afixa) { // funçao de validaçao
        int variavel = 0; // conta quantidade de variaveis
        int operador = 0;// conta quantidade de operadores
        int parenteses = 0; // controla se todos os parenteses abertos
        char buffer;// buffer de analise
        int valido = 0;// controla se o elemento é valido
        char[] operadores = { '+', '-', '*', '/', '^' };// lista de operandos
        Pilha equaçao = EquaçaoPilha(afixa);// joga a equaçao em uma pilha
        while (equaçao.isEmpty() == false) {// emquanto pilha nao estiver vazia
            buffer = (char) equaçao.pop();// pega elemento
            valido = 0;// reseta valides
            if (eVariavel(buffer) == true) {// se for uma variavel adiciona as variaveis
                valido = 1;
                variavel++;
                continue;
            }
            if (contains(operadores, buffer)) {// se for um operador adiciona aos operadores
                valido = 1;
                operador++;
                continue;
            }
            if (buffer == '(') { // se um parenteses abre
                valido = 1;
                parenteses++;
                continue;
            }
            if (buffer == ')') {// ele tem que fechar
                valido = 1;
                parenteses--;
                if (parenteses < 0) {
                    return false;
                }
                continue;
            }
            if (buffer == ' ') {// se for espaço so ignora
                valido = 1;
                continue;
            }
            if (valido == 0) {// se o elemento nao for uma variavel/elemento/parenteses ele nao é valido
                return false;
            }

        }
        if (parenteses == 0 && variavel == operador + 1) {// teste de valides
            return true;
        } else {// se parenteses nao forem fechados/abertos e se nao ouver a quantidade certa de
                // operandos para variaveis
            return false;
        }
    }
    ////////////////////////////////////////////////

    static int pow(int num, int power) {
        int answer = 1;
        if (num > 0 && power == 0) {
            return answer;
        } else if (num == 0 && power >= 1) {
            return 0;
        } else {
            for (int i = 1; i <= power; i++) {
                answer = answer * num;
            }
            return answer;
        }
    }

    ////////////////////////////////////////////////

    public static void main(String args[]) { // funçao main onde a magica acontece
        String opcoes = "\nMenu de Opções\n\n1 - Leitura infixa\n2 - Entrada Variáveis\n3 - Converte pósfixa\n4 - Calcula Resultado\n5 - Encerra\n\n Opção: ";
        Scanner ent = new Scanner(System.in);
        String infixa = "", posfixa = "", teste = "";
        int opcao = 0;
        int[] valorVariaveis = new int[100];// array de valor que é utilizado pelo programa todo
        char[] variaveis = new char[100];// array de variaveis que é utilizado pelo progrma todo

        do {
            System.out.print(opcoes);
            opcao = ent.nextInt();
            switch (opcao) {
                case 1:// le a expressao
                    teste = leInfixa();// coloca a expressao lida em um buffer
                    if (validaçao(teste)) {// valida se a espressao lida é valida
                        infixa = teste;
                    } else {// se nao for
                        System.out.println("digite uma equaçao valida!!");// printa erro
                        break;
                    }
                    System.out.println("infixa: " + infixa);
                    break;

                case 2:// entrada de variaveis
                    System.out.println("Entrada de Variáveis");
                    if (infixa == "") {// simples check para verificar se há uma equaçao
                        System.out.println("primeiro digite uma equaçao!!(opçao 1)");
                        break;
                    } else {
                        variaveis = pegaVariavel(infixa);// pega variaveis
                        valorVariaveis = pegaValor(variaveis);// pede o valor de cada para o usuario
                    }
                    break;

                case 3:// conversao para posfixa
                    System.out.println("Converte Pósfixa");
                    posfixa = infixaPosfixa(infixa);// chama funçao de conversao
                    System.out.format("equaçao posfixa:%s\n", posfixa);
                    break;

                case 4:// calcula resultado
                    System.out.println("Calcula Resultado");
                    if (infixa == "") {// verifica se opçao 1 foi chamada
                        System.out.println("primeiro digite uma equaçao!!(opçao 1)");// erro 1
                        break;
                    }

                    if (variaveis[0] == 0) {// verifica se opçao 2 foi chamada
                        System.out.println("primeiro digite os valores da equaçao!!(opçao 2)");// erro 2
                        break;
                    }
                    if (posfixa == "") {// verifica see opçao 3 foi chamada
                        posfixa = infixaPosfixa(infixa);// se nao, chama
                    }

                    int resultado = calcula(posfixa, variaveis, valorVariaveis);// chama calculadora
                    System.out.println("resultado =" + resultado);// print resultado

                    break;

                case 5:// sair
                    System.out.println("Encerra o programa");
                    break;

            }
        } while (opcao != 5); // encerra o programa
    }
}
