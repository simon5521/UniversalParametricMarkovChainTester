import java.util.*;

public class Location {

    private Set<Parameter> localParameters=new HashSet<Parameter>();

    private ParameterSpace parameterSpace;

    private ParametricTransition [] transitions;
//--------------------------------------------
    public int actionNumber;

    private double [] [] actions; //elso dim a lokációk, második dim az akciók
//------------------------------------------
    public int locationNumber;

    public int parameterNumber;

    public static final String note="Location";

//do not use
    public Location(int locationNumber){
        this.locationNumber=locationNumber;
        readTransitions();
        transitions=new ParametricTransition[locationNumber];
        collectLocalParameters();
        parameterNumber=localParameters.size();
        actionNumber =pow2(parameterNumber);
        actions=new double[locationNumber][actionNumber];
    }


    public Location(ParameterSpace parameterSpace, int locationNumber, Scanner inputStream){
        this.parameterSpace = parameterSpace;
        this.locationNumber=locationNumber;
        transitions=new ParametricTransition[locationNumber];
        inputStream.next(Location.note);
        while (inputStream.hasNextInt()){
            int targetLocattion=inputStream.nextInt();
            transitions[targetLocattion]=new ParametricTransition(parameterSpace,inputStream);
        }
        collectLocalParameters();
        parameterNumber=localParameters.size();
        actionNumber =pow2(parameterNumber);
        actions=new double[locationNumber][actionNumber];

    }

    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace = parameterSpace;

        for(ParametricTransition parametricTransition:transitions){
            if(parametricTransition!=null) {
                parametricTransition.setParameterSpace(parameterSpace);
            }
        }

    }

    private void collectLocalParameters(){
        localParameters.clear();
        for(ParametricTransition transition:transitions) {
            if (transition != null) {
                localParameters.addAll(transition.localParameters);

            }
        }
    }

    public void generateActions(){
        collectLocalParameters();
        Parameter  [] parameterArray = localParameters.toArray(new Parameter[localParameters.size()]);
        for(int i=0;i<actionNumber;i++){
            for (int j=0;j<parameterNumber;j++){ //beállítom az összes paramétert az alsó vagy felső határára
                int mask=1<<j;
                boolean actualBit=(mask&i)==1;
                if(actualBit){
                    parameterArray[j].limit=ParameterDirection.UP;
                }else {
                    parameterArray[j].limit=ParameterDirection.LOW;
                }
            }
            for (int j=0;j<locationNumber;j++){
                actions[j][i]=transitions[j].getRate();
            }
        }
    }

    public double[][] generateAndGetActions(){
        collectLocalParameters();
        Parameter  [] parameterArray = localParameters.toArray(new Parameter[localParameters.size()]);
        for(int actionBits=0;actionBits<actionNumber;actionBits++){
            for (int parameterCounter=0;parameterCounter<parameterNumber;parameterCounter++){ //beállítom az összes paramétert az alsó vagy felső határára
                int mask=1<<parameterCounter;
                int maskedBits=(mask&actionBits)>>parameterCounter;
                boolean actualBit=maskedBits==1;
                if(actualBit){
                    parameterArray[parameterCounter].limit=ParameterDirection.UP;
                }else {
                    parameterArray[parameterCounter].limit=ParameterDirection.LOW;
                }
            }
            for (int targetLocation=0;targetLocation<locationNumber;targetLocation++){
                if(transitions[targetLocation]!=null){
                    actions[targetLocation][actionBits]=transitions[targetLocation].getRate();
                }
            }
        }
        return actions;
    }


    private void readTransitions(){
        transitions=new ParametricTransition[locationNumber];
        for(ParametricTransition transition:transitions){
            transition=null;
        }

    }

    private int pow2(int n){
        int r=1;
        for (int i=0;i<n;i++){
            r*=2;
        }
        return r;
    }

}
