import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DG_1250750_1251008 {

    static final int MAX_MOOD = 5;
    static final int MIN_MOOD = 1;

    static final int MAX_LOW_MOOD = 3;

    static final int VAL_TERAPIA_MUSICA = 2;
    static final int VAL_TERAPIA_PSICOLOGICO = 5;

    static final int MIN_PESSOAS = 1;
    static final int MIN_DIAS = 1;

    public static void main(String[] args) throws FileNotFoundException {

        int tipoInput = escolherInput();
        Scanner input = mudarScanner(tipoInput);

        input.nextLine();
        int numeroPessoas = lerComMinimo(MIN_PESSOAS, input);
        int dias = lerComMinimo(MIN_DIAS, input);

        int[][] moods = new int[numeroPessoas][dias];

        //alínea a
        armazenarMatriz(moods, input);

        //alínea b
        mostrarMatriz(moods);

        //alínea c
        mostrarMediaHumorDia(moods);

        //alínea d
        mostrarMediaHumor(moods);

        //alínea e
        mostrarDiasComMaiorHumor(moods);

        //alínea f
        mostrarPercentagemHumor(moods);

        //alínea g
        mostrarProblemasEmocionais(moods);

        //alínea h
        mostrarGraficoHumor(moods);

        //alínea i
        sugerirTerapia(moods);

        //alínea j
        mostrarHumorSemelhante(moods);
    }

    private static int escolherInput() {
        final int QUANTIDADE_OPCOES = 2;

        Scanner sc = new Scanner(System.in);

        int tipoInput;

        System.out.println("Escolha o tipo de Input:");
        System.out.println("1) Teclado");
        System.out.println("2) Ficheiro de texto");
        System.out.print("Selecione uma opção: ");

        tipoInput = lerNoIntervalo(1, QUANTIDADE_OPCOES, sc);

        return tipoInput;


    }

    private static Scanner mudarScanner(int tipoInput) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);

        if (tipoInput == 1) {
            System.out.println("Insere os teus dados: ");
        }
        else if (tipoInput == 2) {

            System.out.print("Insira o diretório do ficheiro a ler: ");
            String diretorio = input.nextLine();
            File file = new File(diretorio);

            input = new Scanner(file);
        }

        return input;
    }





    private static void armazenarMatriz(int[][] matriz, Scanner input){

        for (int numeroPessoas = 0; numeroPessoas < matriz.length; numeroPessoas++) {
            for (int dias = 0; dias < matriz[numeroPessoas].length; dias++) {
                matriz[numeroPessoas][dias] = lerNoIntervalo(MIN_MOOD, MAX_MOOD, input);
            }
        }
    }

    // --- métodos reutilizáveis ----------------------------------------------------

    private static void mostrarCabecalhoDias(int numDias) {
        System.out.print("day       :");
        for (int dia = 0; dia < numDias; dia++) {
            System.out.printf("%3d ", dia); // cria dinamicamente o formato
        }
        System.out.println();
    }


    private static void mostrarSeparadorDias(int numDias) {
        System.out.print("----------");
        for (int dia = 0; dia < numDias; dia++) {
            System.out.print("|---");
        }
        System.out.println("|");
    }

    private static double[] calcularMediasPorDia(int[][] matriz) {
        int numDias = matriz[0].length;
        int numPessoas = matriz.length;
        double[] medias = new double[numDias];

        for (int dia = 0; dia < numDias; dia++) {
            int somaValores = 0;
            for (int pessoa = 0; pessoa < numPessoas; pessoa++) {
                somaValores += matriz[pessoa][dia];
            }
            medias[dia] = (double) somaValores / numPessoas;
        }

        return medias;
    }

    private static int calcularDiasHumorBaixo(int[] matriz) {
        int diasLowHumor = 0;
        int maxDiasBaixo = 0;
        int valorDiaAnterior = MAX_MOOD;

        for (int dia = 0; dia < matriz.length; dia++) {
            int valor = matriz[dia];

            if (valor < MAX_LOW_MOOD) {
                if (valorDiaAnterior < MAX_LOW_MOOD) {
                    diasLowHumor++;
                } else {
                    diasLowHumor = 1;
                }
                if (diasLowHumor > maxDiasBaixo) {
                    maxDiasBaixo = diasLowHumor;
                }
            } else {
                diasLowHumor = 0;
            }

            valorDiaAnterior = valor;
        }

        return maxDiasBaixo;
    }

    //===========================================================================================
    private static void mostrarMatriz(int[][] matriz) {

        System.out.println("b) Mood (level/day(person)");

        mostrarCabecalhoDias(matriz[0].length);
        mostrarSeparadorDias(matriz[0].length);

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            System.out.printf("Person #%d : ", pessoa);
            for (int dia = 0; dia < matriz[0].length; dia++) {
                System.out.printf(" %-2d ", matriz[pessoa][dia]);
            }
            System.out.println();
        }

        System.out.println();
    }

    //================================================================================================
    private static void mostrarMediaHumorDia(int[][] matriz){

        System.out.println("c) Average mood each day:");

        mostrarCabecalhoDias(matriz[0].length);
        mostrarSeparadorDias(matriz[0].length);

        System.out.print("mood      :");

        double[] medias = calcularMediasPorDia(matriz);

        for (int dia = 0; dia < medias.length; dia++) {
            System.out.printf("%.1f ", medias[dia]);
        }

        System.out.println();
        System.out.println();
    }

    //=====================================================================================

    private static void mostrarMediaHumor(int [][] matriz){

        System.out.println("d) Average of each person's mood:");

        double media;
        for (int pessoa = 0; pessoa < matriz.length ; pessoa++) {
            int soma=0;
            for (int dia = 0; dia < matriz[0].length ; dia++) {
                soma+= matriz[pessoa][dia];
            }
            media = (double) soma / matriz[0].length;
            System.out.printf("Person #%d : %.1f%n",pessoa, media);
        }

        System.out.println();
    }

    //======================================================================================
    private static void mostrarDiasComMaiorHumor(int[][] matriz){

        double[] mediasHumor = calcularMediasPorDia(matriz);

        double maiorHumor = mediasHumor[0];
        String diasComMaiorMedia = "";

        // encontrar o maior valor
        for (int dias = 1; dias < mediasHumor.length; dias++) {
            if (mediasHumor[dias] > maiorHumor) {
                maiorHumor = mediasHumor[dias];
            }
        }

        // juntar todos os dias empatados
        for (int dias = 0; dias < mediasHumor.length; dias++) {
            if (Math.abs(mediasHumor[dias] - maiorHumor) < 0.00001) { // Verificação para doubles. Se a diferença entre os doubles for muito pequena, considera-se que são iguais
                diasComMaiorMedia += dias + " ";
            }
        }

        System.out.printf("e) Days with the highest average mood (%.1f) : %s", maiorHumor, diasComMaiorMedia);

        System.out.println();
        System.out.println();
    }

    //===============================================================================================
    private static void mostrarPercentagemHumor(int[][] matriz){
        System.out.println("f) Percentage of mood levels:");

        double percentagem;
        for (int humor = MAX_MOOD; humor >=MIN_MOOD ; humor--) {
            int soma=0;
            for (int pessoa = 0; pessoa < matriz.length ; pessoa++) {
                for (int dia = 0; dia < matriz[0].length; dia++) {
                    if (matriz[pessoa][dia]==humor){
                        soma++;
                    }
                }
            }
            percentagem=((double) soma/(matriz.length*matriz[0].length))*100;
            System.out.printf("Mood #%d : %.1f%%%n",humor,percentagem);
        }

        System.out.println();
    }

    //=============================================================================================
    private static void mostrarProblemasEmocionais(int[][] matriz){
        System.out.println("g) People with emotional disorders:");

        boolean problemasEncontrados = false;

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {

            int maxDiasBaixo = calcularDiasHumorBaixo(matriz[pessoa]);

            if (maxDiasBaixo >= 2){ //tem de ser 2 para serem consecutivos
                System.out.printf("Person #%d : %d consecutive days%n", pessoa, maxDiasBaixo);
                problemasEncontrados = true;
            }
        }
        if (!problemasEncontrados){
            System.out.println("Ninguém");
        }

        System.out.println();
    }

    //==============================================================================
    private static void mostrarGraficoHumor(int [][] matriz){

        System.out.println("h) People's Mood Level Charts:");
        System.out.println();

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            System.out.printf("Person #%d:%n",pessoa);
            int contador2=0;
            for (int humor=MAX_MOOD; humor >=MIN_MOOD ; humor--) {  //verificação da existência de humor
                int contador=0;
                for (int dia = 0; dia < matriz[0].length; dia++) {
                    if (matriz[pessoa][dia]==humor){
                        contador++;
                    }
                }
                if (contador!=0 || contador2>0){
                    System.out.printf("%4d |",humor);
                    for (int dia = 0; dia < matriz[0].length; dia++) {
                        if (matriz[pessoa][dia]==humor){
                            System.out.print("*");
                        }else{
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                    contador2++;
                }
            }
//===================Parte de baixo da tabela========================================================
            System.out.print("Mood +");
            for (int dia = 0; dia < matriz[0].length; dia++) {
                System.out.print("-");
            }
            System.out.println();
            System.out.printf("%7d", 0);
            System.out.print("    ");
            for (int dia = 5; dia < matriz[0].length; dia+=5) {
                System.out.printf("%-5d",dia);
            }
            System.out.println();
        }
        System.out.println();
    }

    //======================================================================================
    private static void sugerirTerapia(int[][] matriz){

        System.out.println("i) Recommended therapy:");

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {

            int maxDiasBaixo = calcularDiasHumorBaixo(matriz[pessoa]);

            if (maxDiasBaixo >= VAL_TERAPIA_PSICOLOGICO ) {
                System.out.printf("Person #%d: psychological support%n", pessoa);
            }
            if (maxDiasBaixo >= VAL_TERAPIA_MUSICA && maxDiasBaixo < VAL_TERAPIA_PSICOLOGICO) {
                System.out.printf("Person #%d: listen to music%n", pessoa);
            }
            // se maxDiasBaixo < 2, não imprime nada para essa pessoa
        }
        System.out.println();
    }

    //=======================================================================================
    private static void mostrarHumorSemelhante(int [][] matriz){
        int primeiraPessoaSemelhante = 0;
        int segundaPessoaSemelhante = 0;
        int diasComHumorSemelhanteFinal = 0;
        int pessoa1;
        for (int pessoa2 = (matriz.length)-1; pessoa2 >0; pessoa2--) {
            for (pessoa1 = pessoa2-1;pessoa1>=0 ; pessoa1--) {
                int diasComHumorSemelhante=0;
                for (int dia = 0; dia <matriz[0].length ; dia++) {
                    if (matriz[pessoa2][dia]==matriz[pessoa1][dia]){
                        diasComHumorSemelhante++;
                    }
                }
                if (diasComHumorSemelhante >= diasComHumorSemelhanteFinal){

                    diasComHumorSemelhanteFinal = diasComHumorSemelhante;
                    primeiraPessoaSemelhante = pessoa1;
                    segundaPessoaSemelhante = pessoa2;
                }
            }
        }
        if (diasComHumorSemelhanteFinal == 0){
            System.out.print("Nenhum");
        }else{
            System.out.printf("j) People with the most similar moods: (person #%d and Person #%d have the same mood on %d days)",primeiraPessoaSemelhante,segundaPessoaSemelhante,diasComHumorSemelhanteFinal);
        }

        System.out.println();
    }

    //======================================================================================
    public static int lerNoIntervalo(int min, int max, Scanner scanner) {
        int valor;
        do {
            valor = scanner.nextInt();
        } while (valor < min || valor > max);

        return valor;
    }

    public static int lerComMinimo(int min, Scanner scanner) {
        int valor;
        do {
            valor = scanner.nextInt();
        } while (valor < min);

        return valor;
    }
}