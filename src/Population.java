import java.util.ArrayList;

public class Population {
    public static ArrayList<String> Initialize(int size, int decimalplace){
        ArrayList<String> population = new ArrayList<>();
        int length=decimalplace*2;
        while (Math.pow(2,length)<3*Math.pow(10,decimalplace))
            length++;
        StringBuilder individual;
        for (int i=0; i<size; i++){
            individual = new StringBuilder();
            for (int k=1; k<=length; k++){
                individual.append(Math.random()<0.5?"0":"1");
            }
            population.add(String.valueOf(individual));
        }
        return population;
    }
}
