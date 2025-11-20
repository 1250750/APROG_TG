import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DG_1250750_1251008 {

    static File file = new File("src//input.txt");
    static Scanner input;


    static final int MAX_MOOD = 5;
    static final int MIN_MOOD = 1;

    static final int MAX_LOW_MOOD = 3;

    static final int VAL_TERAPIA_MUSICA = 2;
    static final int VAL_TERAPIA_PSICOLOGICO = 5;

    static final int MIN_PESSOAS = 0;
    static final int MIN_DIAS = 0;

    public static void main(String[] args) throws FileNotFoundException {

        input = new Scanner(file);
        input.nextLine(); // nao ser a primeira linha

        int numeroPessoas = lerComMinimo(MIN_PESSOAS);
        int dias = lerComMinimo(MIN_DIAS);

        int[][] moods = new int[numeroPessoas][dias];

        //alinea a
        armazenarMatriz(moods);

        //alinea b
        imprimirMatriz(moods);

        //alinea c
        mediaHumorPorDia(moods);

        //alinea d
        mediaHumor(moods);

        //alinea e
        maiorHumor(moods);

        //alinea f
        percentagemHumor(moods);

        //alinea g
        emocionalDisorders(moods);

        //alinea h
        graficoHumor(moods);

        //alinea i
        terapia(moods);

        //alinea j
        humorSemelhante(moods);

    }



    private static void armazenarMatriz(int[][] matriz){

        for (int numeroPessoas = 0; numeroPessoas < matriz.length; numeroPessoas++) {
            for (int dias = 0; dias < matriz[numeroPessoas].length; dias++) {
                matriz[numeroPessoas][dias] = lerNoIntervalo(MIN_MOOD, MAX_MOOD);
            }
        }

    }


    //===========================================================================================
    private static void imprimirMatriz(int[][] matriz) {

        System.out.println("b) Mood (level/day(person)");


        // cabeçalho dos dias
        System.out.print("day       : ");
        for (int dia = 0; dia < matriz[0].length; dia++) {
            // 1 espaço + número alinhado à esquerda em 2 colunas + 1 espaço = 4 chars por dia
            System.out.printf(" %-2d ", dia);
        }
        System.out.println();

        // linha de separadores dinâmica
        System.out.print("----------");
        for (int dia = 0; dia < matriz[0].length; dia++) {
            System.out.print("|---");
        }
        System.out.println("|");

        // matriz de moods
        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            System.out.printf("Person #%d : ", pessoa);
            for (int dia = 0; dia < matriz[0].length; dia++) {
                // igual ao cabeçalho: 4 chars por coluna
                System.out.printf(" %-2d ", matriz[pessoa][dia]);
            }
            System.out.println();
        }

        System.out.println();
    }







    //================================================================================================
    private static void mediaHumorPorDia(int[][] matriz){

        System.out.println("c) Average mood each day:");

        System.out.print("day       : ");
        for (int dia = 0; dia < matriz[0].length; dia++) {
            // igual à alínea b): 1 espaço + numero em 2 colunas + 1 espaço
            System.out.printf(" %-2d ", dia);
        }
        System.out.println();

        System.out.print("----------");
        for (int dias = 0; dias < matriz[0].length; dias++) {
            System.out.print("|---");
        }
        System.out.println("|");

        System.out.print("mood      :");

        int somaPessoas = matriz.length;
        int somaValores = 0;

        for (int dia = 0; dia < matriz[0].length; dia++) {
            for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
                somaValores += matriz[pessoa][dia];
            }
            double media = (double) somaValores / somaPessoas;


            System.out.printf("%.1f ", media);

            somaValores = 0;
        }

        System.out.println();
        System.out.println();
    }


    //=====================================================================================

    private static void mediaHumor(int [][] matriz){

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
    private static void maiorHumor(int[][] matriz){

        double[] mediasHumor = new double[matriz[0].length];

        int somaPessoas = matriz.length;
        int somaValores = 0;

        for (int dia = 0; dia < matriz[0].length; dia++) {
            for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
                somaValores += matriz[pessoa][dia];
            }

            double media = (double) somaValores / somaPessoas;
            mediasHumor[dia] = media;
            somaValores = 0;
        }

        double maiorHumor = mediasHumor[0];
        String diasComMaiorMedia = " ";

        // encontrar o maior valor
        for (int dias = 0; dias < mediasHumor.length; dias++) {
            if (mediasHumor[dias] > maiorHumor) {
                maiorHumor = mediasHumor[dias];
            }
        }

        // juntar todos os dias empatados
        diasComMaiorMedia = "";
        for (int dias = 0; dias < mediasHumor.length; dias++) {
            if (Math.abs(mediasHumor[dias] - maiorHumor) < 0.00001) { //Porque ao dar um valor double vai ter várias casas decimais e nem sempre ser igual, 3,299991 e diferente de 3,299992 por isso faço essa verificação. Se a diferenca for muito pequena, sao iguais.
                diasComMaiorMedia += dias + " ";

            }
        }

        System.out.printf("e) Days with the highest average mood (%.1f) : %s", maiorHumor, diasComMaiorMedia);

        System.out.println();
        System.out.println();
    }


    //===============================================================================================
    private static void percentagemHumor(int[][] matriz){
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

    private static void emocionalDisorders(int[][] matriz){
        System.out.println("g) People with emotional disorders:");

        boolean encontradasDisorders = false;

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            int diasHumorBaixo = 0;
            int maxDiasBaixo = 0;
            int valorDiaAnterior = MAX_MOOD;

            for (int dia = 0; dia < matriz[0].length; dia++) {

                int valor = matriz[pessoa][dia];
                if (valor < MAX_LOW_MOOD) {
                    if (valorDiaAnterior < MAX_LOW_MOOD) {
                        diasHumorBaixo++;
                    }else {
                        diasHumorBaixo = 1;
                    }

                    if (diasHumorBaixo > maxDiasBaixo) {
                        maxDiasBaixo = diasHumorBaixo;

                    }
                } else{
                    diasHumorBaixo = 0;
                }

                valorDiaAnterior = valor;

            }
            if (maxDiasBaixo >= 2){ //tem de ser 2 para serem consecutivos
                System.out.printf("Person #%d : %d consecutive days%n", pessoa, maxDiasBaixo);
                encontradasDisorders = true;
            }
        }
        if (!encontradasDisorders){
            System.out.println("Ninguem");
        }

        System.out.println();


    }

    //==============================================================================

    private static void graficoHumor(int [][] matriz){

        System.out.println("h) People's Mood Level Charts:");

        System.out.println();
        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            System.out.printf("Person #%d:%n",pessoa);
            int contador2=0;
            for (int humor=MAX_MOOD; humor >=MIN_MOOD ; humor--) {
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
            int dias5=5;
            System.out.print("Mood +");
            for (int dia = 0; dia < matriz[0].length; dia++) {
                System.out.print("-");
            }
            System.out.println();
            System.out.printf("%7d",0);
            for (int dia = 1; dia < matriz.length; dia++) {
                System.out.printf("%5d",dias5);
                dias5+=5;
            }
            System.out.println();
        }
        System.out.println();
    }

    //======================================================================================
    private static void terapia(int[][] matriz){

        System.out.println("i) Recommended therapy:");

        for (int pessoa = 0; pessoa < matriz.length; pessoa++) {
            int diasLowHumor = 0;     // contador corrente
            int maxDiasBaixo = 0;       // máximo por pessoa
            int valorDiaAnterior = MAX_MOOD;  // reinicia por pessoa, MAX_MOOD nunca vai ser menor que o low mood

            for (int dia = 0; dia < matriz[0].length; dia++) {
                int valor = matriz[pessoa][dia];

                if (valor < MAX_LOW_MOOD) {                 // está em low mood
                    if (valorDiaAnterior < MAX_LOW_MOOD) {  // estava ontem?
                        diasLowHumor++;                   // continua sequência
                    } else {
                        diasLowHumor = 1;                 // começa a sequência
                    }
                    if (diasLowHumor > maxDiasBaixo) {
                        maxDiasBaixo = diasLowHumor;
                    }
                } else {
                    diasLowHumor = 0;                     // quebra a sequência
                }

                valorDiaAnterior = valor;
            }

            if (maxDiasBaixo >= VAL_TERAPIA_PSICOLOGICO ) {
                System.out.printf("Person #%d: psychological support%n", pessoa);
            }
            if (maxDiasBaixo >= VAL_TERAPIA_MUSICA && maxDiasBaixo < VAL_TERAPIA_PSICOLOGICO) {
                System.out.printf("Penson #%d: listen to music%n", pessoa);
            }
            // se maxDiasBaixo < 2, não imprime nada para essa pessoa
        }
        System.out.println();
    }

    //=======================================================================================
    private static void humorSemelhante(int [][] matriz){
        int primeiraPessoaSemelhante=0;
        int segundaPessoaSemelhante=0;
        int humorSemelhanteFinal=0;
        int pessoa2=0;
        for (int pessoa = (matriz.length)-1; pessoa >0; pessoa--) {
            for (pessoa2 = pessoa-1;pessoa2>=0 ; pessoa2--) {
                int humorSemelhante=0;
                for (int dia = 0; dia <matriz[0].length ; dia++) {
                    if (matriz[pessoa][dia]==matriz[pessoa2][dia]){
                        humorSemelhante++;
                    }
                }
                if (humorSemelhante>=humorSemelhanteFinal){
                    humorSemelhanteFinal=humorSemelhante;
                    primeiraPessoaSemelhante=pessoa;
                    segundaPessoaSemelhante=pessoa2;
                }
            }
        }
        if (humorSemelhanteFinal==0){
            System.out.print("Nenhum");
        }else{
            System.out.printf("j) People with the most similar moods: (person #%d and Person #%d have the same mood on %d days)",segundaPessoaSemelhante,primeiraPessoaSemelhante,humorSemelhanteFinal);
        }

        System.out.println();

    }



    //======================================================================================
    public static int lerNoIntervalo(int min, int max) {
        int valor;
        do {
            valor = input.nextInt();
        } while (valor < min || valor > max);
        return valor;
    }

    public static int lerComMinimo(int min) {
        int valor;
        do {
            valor = input.nextInt();
        } while (valor < min);
        return valor;
    }

}