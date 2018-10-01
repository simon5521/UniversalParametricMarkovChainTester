import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrizmModellChecker {
    private final String prizmmodellcheckerLocation="c:\\Program Files\\prism-4.4\\bin\\prism.bat";
    private final String propertyLocationHigh="c:\\Users\\simon5521\\Desktop\\propsHigh.csl";
    private final String propertyLocationLow="c:\\Users\\simon5521\\Desktop\\propsLow.csl";
    private final String modellLocation="c:\\Users\\simon5521\\Desktop\\modell.pm";
    private final String trueMessage="Result: true";
    private final String falseMessage="Result: false";

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
        fileWriter.close();
    }

    private String startCheckHigh() throws IOException {

        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationHigh);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File("c:\\Program Files\\prism-4.4\\bin\\"))
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
                .directory(new File("c:\\Program Files\\prism-4.4\\bin\\"))
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
