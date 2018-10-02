import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ParametricMarkovChainTester {

    public final String outputLocation="c:\\Users\\simon5521\\Desktop\\output4.txt";
    private final double keyProbability;
    private final int iteratonLimit=10;
    private Queue<ParametricMarkovChain> fifo;
    private PrintWriter printWriter;
    private PrizmPMCChecker prizmPMCChecker;

    public ParametricMarkovChainTester(
            double keyProbability,
            ParametricMarkovChain parametricMarkovChain
    ) throws IOException {
        this.keyProbability=keyProbability;
        fifo=new LinkedList<ParametricMarkovChain>();
        fifo.add(parametricMarkovChain);

        prizmPMCChecker=new PrizmPMCChecker(parametricMarkovChain,keyProbability);

        printWriter=new PrintWriter(outputLocation,"UTF-8");
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
            System.out.print("Size of fifo ");
            System.out.println(fifo.size());
            testTopParametricMarkovChain();
        }
    }

    private void testTopParametricMarkovChain() throws Exception {

        ParametricMarkovChain parametricMarkovChain=fifo.remove();

        if(prizmPMCChecker.checkHigh()){
            //write out the interval
            String outputString="upper area: "+parametricMarkovChain.getParameterSpace().toString();
            printWriter.print(outputString);
            System.out.println(outputString);

        } else if(prizmPMCChecker.checkLow()){
            //write out the interval
            String outputString="downer area: "+parametricMarkovChain.getParameterSpace().toString();
            printWriter.println(outputString);
            System.out.println(outputString);

        } else {
            Parameter cutParameter=searchCutParameter(parametricMarkovChain.getParameterSpace());
            ParametricMarkovChain parametricMarkovChainLow=parametricMarkovChain.copy();
            parametricMarkovChainLow.setParameterSpace(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetLow(cutParameter.name));
            ParametricMarkovChain parametricMarkovChainHigh=parametricMarkovChain.copy();
            parametricMarkovChainHigh.setParameterSpace(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetUp(cutParameter.name));
            if(cutParameter.cutted<=iteratonLimit){
                fifo.add(parametricMarkovChainLow);
                fifo.add(parametricMarkovChainHigh);
            }
        }

    }

}
