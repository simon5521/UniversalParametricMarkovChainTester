import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ParametricMarkovChainTester {

    public final String outputLocation="/output.txt";
    private final double keyProbability;
    private int iteratonLimit=10;
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
            testTopParametricMarkovChain();
            System.out.println("Testing and cutting; iteration:"+counter.toString());
        }
    }

    private void testTopParametricMarkovChain() throws Exception {

        ParametricMarkovChain parametricMarkovChain=fifo.remove();

        if(prizmPMCChecker.checkHigh()){
            //write out the interval
            printWriter.print("upper area: "+parametricMarkovChain.getParameterSpace().toString());
            System.out.println("upper area found");

        } else if(prizmPMCChecker.checkLow()){
            //write out the interval
            printWriter.print("downer area: "+parametricMarkovChain.getParameterSpace().toString());
            System.out.println("downer area found");

        } else {
            Parameter cutParameter=searchCutParameter(parametricMarkovChain.getParameterSpace());
            ParametricMarkovChain parametricMarkovChainLow=parametricMarkovChain.copy();
            parametricMarkovChainLow.setParameterSpace(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetLow(cutParameter.name));
            ParametricMarkovChain parametricMarkovChainHigh=parametricMarkovChain.copy();
            parametricMarkovChainHigh.setParameterSpace(parametricMarkovChain.getParameterSpace().cutSpaceByParameterAndGetLow(cutParameter.name));
            fifo.add(parametricMarkovChainLow);
            fifo.add(parametricMarkovChainHigh);
        }

    }

}
