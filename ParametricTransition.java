import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ParametricTransition {

    private ParameterSpace parameterSpace;

    public final String valueExpression;

    Set<Parameter> localParameters;

    public ParametricTransition(ParameterSpace parameterSpace,String valueExpression){
        this.parameterSpace = parameterSpace;
        localParameters = new HashSet<>();
        this.valueExpression=valueExpression;
        searchParameters();
    }

    public ParametricTransition(ParameterSpace parameterSpace,Scanner inputStream){
        this.parameterSpace = parameterSpace;
        localParameters=new HashSet<>();
        this.valueExpression=inputStream.next("[\\p{Graph}]+");
        searchParameters();
    }

    private void searchParameters(){
        localParameters.clear();
        for (Map.Entry<String,Parameter> entry:
         parameterSpace.map.entrySet()    ) {
            if(valueExpression.contains(entry.getKey())){
                localParameters.add(entry.getValue());
            }
        }
    }

    public Set<Parameter> getLocalParameters(){
        return localParameters;
    }

    private double getLocalValue(StringBuilder localValue){
        double value;
        if (('9'>localValue.charAt(0) && '0'<localValue.charAt(0)) || localValue.charAt(0)=='-'){
            value=Double.parseDouble(localValue.toString());
        }else {
            value=parameterSpace.map.get(localValue.toString()).getValue();
        }
        localValue.setLength(0);
        return value;
    }

    public double getRate(){
        char [] c_valueExpression=valueExpression.toCharArray();
        StringBuilder accumulator=new StringBuilder();
        Double rate=0.0;
        Double multipleAccumulator=1.0;
        boolean state=true;
        for(int i=0; i<c_valueExpression.length;i++){
            char c=c_valueExpression[i];
            if(state){
                if(c=='+'){
                    rate+=getLocalValue(accumulator);
                } else if(c=='*'){
                    state=false;
                    multipleAccumulator=getLocalValue(accumulator);
                } else{
                    accumulator.append(c);
                }
            } else {
                if(c=='+'){
                    state=true;
                    rate+=multipleAccumulator*getLocalValue(accumulator);
                    multipleAccumulator=1.0;
                } else if(c=='*'){
                    multipleAccumulator*=getLocalValue(accumulator);
                } else{
                    accumulator.append(c);
                }
            }

        }
        if(!state){
            rate+=multipleAccumulator*getLocalValue(accumulator);
        }else {
            rate+=getLocalValue(accumulator);
        }
        return rate;
    }

    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace=parameterSpace;
        searchParameters();
    }


}
