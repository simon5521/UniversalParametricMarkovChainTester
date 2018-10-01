import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParametricMarkovChain {

    private ParameterSpace parameterSpace;

    public final Integer startingLocation;

    public final Integer locationNumber;

    public final Integer targetLocation;

    public int actionNumber;

    public Location [] locations;

    public ParametricMarkovChain(int startingLocation, int locationNumber, int actionNumber,int targetLocation){
        this.startingLocation = startingLocation;
        this.locationNumber = locationNumber;
        this.actionNumber = actionNumber;
        locations=new Location[locationNumber];
        if(targetLocation<=locationNumber)
            this.targetLocation = targetLocation;
        else {
            this.targetLocation = -1;
            System.out.println("there is an error with the target location");
        }
    }

    public ParametricMarkovChain(Scanner inputStream){
        startingLocation=inputStream.nextInt();
        locationNumber=inputStream.nextInt();
        locations=new Location[locationNumber];
        targetLocation=inputStream.nextInt();
        parameterSpace=new ParameterSpace(inputStream);
        for (int locationCounter=0;inputStream.hasNext(Location.note) || locationCounter<locationNumber;){
            locations[locationCounter]=new Location(parameterSpace,locationNumber,inputStream);
        }
        calculateActionNumber();
    }

    public ParametricMarkovChain copy(){
        ParametricMarkovChain parametricMarkovChain=new ParametricMarkovChain(startingLocation, locationNumber, actionNumber, targetLocation);
        parametricMarkovChain.locations=this.locations;
        parametricMarkovChain.parameterSpace=this.parameterSpace;
        return parametricMarkovChain;
    }

    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace = parameterSpace;

    }

    public ParameterSpace getParameterSpace(){
        return parameterSpace;
    }

    public void calculateActionNumber(){
        int maxActionNumber=0;
        for (int i=0;i<locationNumber;i++){
            if(locations[i].actionNumber>maxActionNumber){
                maxActionNumber=locations[i].actionNumber;
            }
        }
        actionNumber=maxActionNumber;
    }


    private void readParameters(){
        parameterSpace=new ParameterSpace();

    }

    private void readLocations(){
        List<Location> tmpLocations=new ArrayList<>();
        // #todo
        locations=tmpLocations.toArray(new Location[locationNumber]);

    }

    public MarkovDecisionProcess generateMarkovDecisionProcess() {
        MarkovDecisionProcess markovDecisionProcess=new MarkovDecisionProcess(locationNumber,startingLocation,actionNumber);
        for (int i=0;i<locationNumber;i++){
            markovDecisionProcess.partitiallySetRateMatrix(i,locations[i].generateAndGetActions());
        }
        return markovDecisionProcess;
    }
}
