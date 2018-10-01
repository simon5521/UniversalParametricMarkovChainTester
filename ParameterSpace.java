import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ParameterSpace {

    public ParameterSpaceState state=ParameterSpaceState.UNTESTED;

    public Map<String,Parameter> map = new HashMap<String,Parameter>();

    public ParameterSpace(){

    }

    public ParameterSpace(ParameterSpace parameterSpace){
        map.putAll(parameterSpace.map);
    }

    public Parameter getParameterByName(String name){
        return map.get(name);
    }

    public void addParameter(Parameter parameter){
        map.put(parameter.name,parameter);
    }

    ParameterSpace cutSpaceByParameterAndGetLow(String pameterName){
        Parameter cutParameter=map.get(pameterName);
        ParameterSpace cutLowParameterSpace=new ParameterSpace(this);
        cutLowParameterSpace.map.remove(pameterName);
        cutLowParameterSpace.map.put(cutParameter.lowHalf().name,cutParameter.lowHalf());
        return cutLowParameterSpace;
    }

    ParameterSpace cutSpaceByParameterAndGetUp(String pameterName){
        Parameter cutParameter=map.get(pameterName);
        ParameterSpace cutUpParameterSpace=new ParameterSpace(this);
        cutUpParameterSpace.map.remove(pameterName);
        cutUpParameterSpace.map.put(cutParameter.upHalf().name,cutParameter.upHalf());
        return cutUpParameterSpace;
    }

    public ParameterSpace(Scanner inputStream){
        ParameterSpace parameterSpace=new ParameterSpace();
        while (inputStream.hasNext(Parameter.note)){
            Parameter parameter=new Parameter(inputStream);
            this.map.put(parameter.name,parameter);
        }
    }

}
