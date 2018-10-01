import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Parameter {

    public final Double lowerLimit;
    public final Double  upperLimit;

    public final Integer cutted;

    public final String name;

    public static final String note="#";

    public ParameterDirection limit=ParameterDirection.LOW;


    public Parameter(String name,Double lowerLimit,Double upperLimit){
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
        this.name=name;
        cutted=0;
    }

    public Parameter(Scanner inputStream){
        String parameterPattern="[a-zA-Z]+[a-zA-Z0-9]*";
        inputStream.next(note);
        this.name=inputStream.next(parameterPattern);
        this.lowerLimit=inputStream.nextDouble();
        this.upperLimit=inputStream.nextDouble();
        this.cutted=0;
        inputStream.hasNext("\n");

    }

    public boolean checkName(){

        for(char c:name.toCharArray()){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }



    public Parameter(String name,Double lowerLimit,Double upperLimit,Integer cutted){
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
        this.name=name;
        this.cutted=cutted;
    }

    public Double getValue(){
        if(limit==ParameterDirection.LOW){
            return lowerLimit;
        }else{
            return upperLimit;
        }
    }

    Parameter lowHalf(){
        return new Parameter(name, lowerLimit,(upperLimit+lowerLimit)/2,cutted+1);
    }


    Parameter upHalf(){
        return new Parameter(name,(upperLimit+lowerLimit)/2,upperLimit,cutted+1);
    }



}

