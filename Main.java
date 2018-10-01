import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import java.io.*;

import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;


public class Main {

    public static void main(String Args[]) throws Exception {

        String input="\\input.txt";
        double keyProbability=0.5;
        Scanner scanner=new Scanner(new File(input));
        ParametricMarkovChain parametricMarkovChain=new ParametricMarkovChain(scanner);
        ParametricMarkovChainTester parametricMarkovChainTester=new ParametricMarkovChainTester(keyProbability,parametricMarkovChain);
        parametricMarkovChainTester.generateOutput();
    }
}






