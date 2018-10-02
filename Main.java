import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import java.io.*;

import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;


public class Main {

    public static void main(String Args[]) throws Exception {





        String input="c:\\Users\\simon5521\\Desktop\\input.txt";
        File inputFile=new File(input);
        Scanner scanner=new Scanner(inputFile);
        scanner.useLocale(Locale.US);
        double keyProbability=scanner.nextDouble();
        ParametricMarkovChain parametricMarkovChain=new ParametricMarkovChain(scanner);
        ParametricMarkovChainTester parametricMarkovChainTester=new ParametricMarkovChainTester(keyProbability,parametricMarkovChain);
        parametricMarkovChainTester.generateOutput();
    }
}






