import java.io.IOException;

public class PrizmPMCChecker {
    private PrizmModellChecker prizmModellChecker;
    private ParametricMarkovChain parametricMarkovChain;
    private Double keyProperty=null;



    public PrizmPMCChecker(
            ParametricMarkovChain parametricMarkovChain,
            Double keyProperty
    ) throws IOException {
        prizmModellChecker=new PrizmModellChecker();
        this.parametricMarkovChain=parametricMarkovChain;
        setKeyProperty(keyProperty);
    }

    public void setKeyProperty(Double keyProperty) throws IOException {
        if(keyProperty<=1.0 && keyProperty>=0.0)
            this.keyProperty = keyProperty;
        setPropertyFile();
    }

    private void setPropertyFile() throws IOException {
        prizmModellChecker.setPropertyFileHigh(" P > "+keyProperty.toString()+"[ F = "+parametricMarkovChain.targetLocation.toString()+"]");
        prizmModellChecker.setPropertyFileLow(" P < "+keyProperty.toString()+"[ F = "+parametricMarkovChain.targetLocation.toString()+"]");
    }

    //relaxation
    private String generatePrizmModell(){
        StringBuilder prizmModell=new StringBuilder();
        prizmModell.append("mdp\n");
        prizmModell.append("module m1\n");
        prizmModell.append(" l : [0.."+parametricMarkovChain.locationNumber.toString()+"] init "+parametricMarkovChain.startingLocation.toString()+" ; \n");

        for(Integer sourceLocation=0; sourceLocation<parametricMarkovChain.locationNumber; sourceLocation++){
            double [] [] actions=parametricMarkovChain.locations[sourceLocation].generateAndGetActions();
            for(Integer action=0;action<parametricMarkovChain.actionNumber;action++){
                prizmModell.append("[] s= "+sourceLocation.toString()+" -> ");
                for (Integer targetLocation=0; targetLocation<parametricMarkovChain.locationNumber; targetLocation++){
                    if(actions[targetLocation][action]!=0){
                        prizmModell.append(Double.toString(actions[targetLocation][action])+"(s'="+targetLocation.toString()+") + ");
                    }
                }
                prizmModell.append(" 0:(l'=0);\n");
            }
        }
        prizmModell.append("endmodule\n");
        return prizmModell.toString();
    }

    public boolean checkHigh() throws Exception {
        return prizmModellChecker.checkModellHigh(generatePrizmModell());
    }

    public boolean checkLow() throws Exception {
        return prizmModellChecker.checkModellHigh(generatePrizmModell());
    }

}


