import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ParametricMarkovChainTester {

    public final String outputLocation="c:\\Users\\simon5521\\Desktop\\output4.txt";
    private final double keyProbability;
    private final int iteratonLimit=10;
    private Queue<ParameterSpace> fifo;
    private ParametricMarkovChain parametricMarkovChain;
    private PrizmPMCChecker prizmPMCChecker;
    BufferedWriter fileWriter=new BufferedWriter(new FileWriter(outputLocation));
    private final double JordanMeasure;
    private double actualJordanMeasure=0.0;


    public ParametricMarkovChainTester(
            double keyProbability,
            ParametricMarkovChain parametricMarkovChain
    ) throws IOException {
        this.keyProbability=keyProbability;
        this.parametricMarkovChain=parametricMarkovChain;
        fifo=new LinkedList<ParameterSpace>();
        fifo.add(parametricMarkovChain.getParameterSpace());

        prizmPMCChecker=new PrizmPMCChecker(parametricMarkovChain,keyProbability);
        JordanMeasure=parametricMarkovChain.getParameterSpace().getJordanMeasure();

    }



    private Parameter searchCutParameter(ParameterSpace parameterSpace){
        Parameter minIterationParameter=null;
        int minIteration=iteratonLimit;
        for(Map.Entry<String, Parameter> entry:parameterSpace.map.entrySet()){
            if(entry.getValue().cutted<=minIteration){
                minIteration=entry.getValue().cutted;
                minIterationParameter=entry.getValue();
            }
        }
        return minIterationParameter;
    }

    public void generateOutput() throws Exception {
        System.out.println("Generating output and test ParameterSpace of the Parametric Markov Chain");
        Integer counter=0;
        while (!fifo.isEmpty()){
            counter++;
            System.out.println("Testing and cutting; iteration:"+counter.toString());
            System.out.println("Jordan Measure rate:"+Double.toString(actualJordanMeasure/JordanMeasure));
            System.out.print("Size of fifo ");
            System.out.println(fifo.size());
            testTopParameterSpace();
        }
        fileWriter.close();
    }

    private void testTopParameterSpace() throws Exception {

        ParameterSpace parameterSpace=fifo.remove();
        System.out.println(parameterSpace.toString());
        parametricMarkovChain.setParameterSpace(parameterSpace);
        prizmPMCChecker.setParametricMarkovChain(parametricMarkovChain);


        if(keyProbability>=prizmPMCChecker.calculateMaxProbability()){
            //write out the interval
            String outputString="lower area: "+parametricMarkovChain.getParameterSpace().toString();
            actualJordanMeasure+=parameterSpace.getJordanMeasure();
            fileWriter.write(outputString+"\n");
            System.out.println(outputString);

        } else if(keyProbability<=prizmPMCChecker.calculateMinProbability()){
            //write out the interval
            String outputString="upper area: "+parametricMarkovChain.getParameterSpace().toString();
            actualJordanMeasure+=parameterSpace.getJordanMeasure();
            fileWriter.write(outputString+"\n");
            System.out.println(outputString);

        } else {
            Parameter cutParameter=searchCutParameter(parametricMarkovChain.getParameterSpace());
            if(cutParameter.cutted<iteratonLimit){
                fifo.add(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetLow(cutParameter.name));
                fifo.add(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetUp(cutParameter.name));
            }
        }

    }


}
