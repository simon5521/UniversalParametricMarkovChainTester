import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PrizmModellChecker {
    private final String prizmmodellcheckerLocation="c:\\Program Files\\prism-4.4\\bin\\prism.bat";
    private final String prizmLocation="c:\\Program Files\\prism-4.4\\bin\\";
    private final String propertyLocationHigh="c:\\Users\\simon5521\\Desktop\\propsHigh.csl";
    private final String propertyLocationLow="c:\\Users\\simon5521\\Desktop\\propsLow.csl";
    private final String propertyLocation="c:\\Users\\simon5521\\Desktop\\props.csl";
    private final String modellLocation="c:\\Users\\simon5521\\Desktop\\modell.pm";
    private final String trueMessage="Result: true";
    private final String falseMessage="Result: false";
    private final String reaultPattern="Result: [01]\\.*[0-9]*";

    public void setPropertyFileHigh(String prizmProperty) throws IOException {
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(propertyLocationHigh));
        fileWriter.write(prizmProperty);
        fileWriter.close();
    }

    public void setPropertyFileLow(String prizmProperty) throws IOException {
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(propertyLocationLow));
        fileWriter.write(prizmProperty);
        fileWriter.close();
    }



    private void generateModellFile(String prizmModell) throws IOException {
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(modellLocation));
        fileWriter.write(prizmModell);
        //System.out.println(prizmModell);
        fileWriter.close();
    }

    public double calculateMaxProbability(String prizmModell) throws IOException {
        generateModellFile(prizmModell);
        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationHigh);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();


        Scanner scanner=new Scanner(new BufferedInputStream(process.getInputStream()));
        String rawResult=scanner.findWithinHorizon(reaultPattern,20000);
        scanner=new Scanner(rawResult);
        scanner.useLocale(Locale.US);
        scanner.next();
        double max=scanner.nextDouble();
        System.out.println(max);
        return max;
    }

    public double calculateMinProbability(String prizmModell) throws IOException {

        generateModellFile(prizmModell);
        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationLow);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();


        Scanner scanner=new Scanner(new BufferedInputStream(process.getInputStream()));
        String rawResult=scanner.findWithinHorizon(reaultPattern,20000);
        scanner=new Scanner(rawResult);
        scanner.useLocale(Locale.US);
        scanner.next();
        double min=scanner.nextDouble();
        System.out.println(min);
        return min;
    }

    private String startCheckHigh() throws IOException {

        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationHigh);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();

        StringBuilder output=new StringBuilder();

        int i = 0;
        while ((i = process.getInputStream().read()) != -1) {
            output.append((char) i);
        }

        return output.toString();
    }

    private String startCheckLow() throws IOException {

        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationLow);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();

        StringBuilder output=new StringBuilder();

        int i = 0;
        while ((i = process.getInputStream().read()) != -1) {
            output.append((char) i);
        }

        return output.toString();
    }

    public boolean checkModellHigh(String prizmModell) throws Exception {

        generateModellFile(prizmModell);
        String solution=startCheckHigh();
        if(solution.contains(trueMessage)){
            return true;
        }

        if(solution.contains(falseMessage)){
            return false;
        }

        throw new Exception("The result is neither true nor false, there must be an error with the modell checking :'( \n"+solution);

    }

    public boolean checkModellLow(String prizmModell) throws Exception {

        generateModellFile(prizmModell);
        String solution=startCheckLow();
        if(solution.contains(trueMessage)){
            return true;
        }

        if(solution.contains(falseMessage)){
            return false;
        }

        throw new Exception("The result is neither true nor false, there must be an error with the modell checking :'( \n"+solution);

    }

    public PrizmModellChecker(){
    }





}
