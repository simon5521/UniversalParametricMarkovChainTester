import java.util.ArrayList;
import java.util.List;

public class MarkovDecisionProcess {
    double [][][] rateMatrix;
    public final int locationNumber;
    public final int stratingLocation;
    public final int actionNumber;
    private List<DecisionTransition> transitionList;

    public MarkovDecisionProcess(int locationNumber,int stratingLocation,int actionNumber){


        this.locationNumber = locationNumber;
        this.stratingLocation = stratingLocation;
        this.actionNumber = actionNumber;

        transitionList=new ArrayList<DecisionTransition>();

    }


    public void addTransition(DecisionTransition decisionTransition){
        transitionList.add(decisionTransition);
    }


    //deprecated
    public void partitiallySetRateMatrix(int locationID,double [] [] partitialRateMatrix){
        rateMatrix[locationID]=partitialRateMatrix;
    }
}
