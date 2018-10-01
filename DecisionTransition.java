public class DecisionTransition {

    public final int fromLocation;
    public final int toLocation;
    private double rate;

    public DecisionTransition(int fromLocation,int toLocation,double rate) {
        this.fromLocation=fromLocation;
        this.toLocation=toLocation;
        this.rate=rate;
    }

    public void setRate(double rate){
        this.rate=rate;
    }

}
