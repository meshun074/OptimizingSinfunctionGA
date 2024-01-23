import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm();

        LineChart.DrawChart(new ArrayList<>(ga.startGA(50,30,1,0.5,7,true)));
    }
}