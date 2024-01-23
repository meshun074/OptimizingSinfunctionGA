import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    private static boolean maximize;
    private final Random r = new Random();
    private static ArrayList<Individual> newPopulation;
    public ArrayList<Double> startGA(int pop_size, int no_Gen, double cross_rate, double mut_rate, int decimal_place, boolean max){
        ArrayList<Double> genBest = new ArrayList<>();
        maximize=max;
        ArrayList<Individual> population = new ArrayList<>();
        Individual chromosome;
        //Initialize Population
        for (String binary: Population.Initialize(pop_size,decimal_place)){
            chromosome =new Individual(binary,EvaluationFunction.FitnessFunction(binary));
            population.add(chromosome);
        }
        //Sort Population
        sortPopulation(population);
        //Output best in the current generation
        System.out.println("Generation 1 "+EvaluationFunction.getDecimal(population.get(0).alleles()) +"---"+ population.get(0).fittness());
        genBest.add(population.get(0).fittness());
        //Start generations
        for (int gen = 2; gen<=no_Gen; gen++){
//            population.forEach(x-> System.out.print(x.alleles()+"-"+x.fittness()+" "));
//            System.out.println();
            //elitism
            newPopulation=new ArrayList<>(elitism(population));
//            System.out.println("After elitism " + newPopulation.size()+"+++"+population.size());
            //tournament selection
            ArrayList<Individual> tempPopulation = new ArrayList<>(tournamentSelect(population));
            //Uniform Crossover and Mutation and Fitness Evaluation
            uniforCrossoverMutation(tempPopulation, cross_rate, mut_rate);
            //update population
//            for (int i=0; i<population.size(); i++){
//                population.set(i,newPopulation.get(i));
//            }
            population = new ArrayList<>(newPopulation);
            //Sort Population
            sortPopulation(population);
//            population.forEach(x-> System.out.print(x.alleles()+"-"+x.fittness()+" "));
//            System.out.println();
//            newPopulation.forEach(x-> System.out.print(x.alleles()+"-"+x.fittness()+" "));
//            System.out.println();
            //Output best in the current generation
            System.out.println("Generation "+gen+" "+EvaluationFunction.getDecimal(population.get(0).alleles()) +"---"+ population.get(0).fittness());
            genBest.add(population.get(0).fittness());
        }
        return genBest;
    }

    private void sortPopulation(ArrayList<Individual> population) {
        if (maximize) {
            population.sort((o1, o2) -> {
                if (o1.fittness() == o2.fittness()) return 0;
                else if (o1.fittness() < o2.fittness()) return 1;
                return -1;
            });
        }else {
            population.sort((o1, o2) -> {
                if (o1.fittness() == o2.fittness()) return 0;
                else if (o1.fittness() > o2.fittness()) return 1;
                return -1;
            });
        }
    }
    private  ArrayList<Individual> elitism(ArrayList<Individual> population) {
        int rate = population.size ()>100?(int) (population.size() * 0.01): population.size()/10;
        System.out.println("Elistims "+rate);
        int counter = 0;
        ArrayList<Individual> elitismPopulation = new ArrayList<>();
        for (Individual x : population) {
            elitismPopulation.add(new Individual(x.alleles(), x.fittness()));
            counter++;
            if (counter == rate) break;
        }
        return elitismPopulation;
    }

    private ArrayList<Individual> tournamentSelect(List<Individual> population) {
        ArrayList<Individual> TSpop = new ArrayList<>();
        ArrayList<Individual> temp;
        for (int i = 0; i < population.size(); i++) {
            temp = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                temp.add(population.get(r.nextInt(population.size())));
            }
            sortPopulation(temp);
            TSpop.add(new Individual(temp.get(0).alleles(), temp.get(0).fittness()));
        }
        return TSpop;
    }

    private void uniforCrossoverMutation(ArrayList<Individual> population, double rate, double mutationRate) {
        StringBuilder c1;
        StringBuilder c2;
        for(int i=0; i<population.size()-1; i+=2){
            c1=new StringBuilder(population.get(i).alleles());
            c2=new StringBuilder(population.get(i+1).alleles());
            int check =0;
            //check if same chromosome are used for crossover
            while (sameIndividual(c1,c2)){
                c2=new StringBuilder(population.get(r.nextInt(population.size())).alleles());
                if (check > population.size() / 10)
                    break;
                check++;
            }
//            perfroms crossover
//            System.out.println("First: "+c1+"----"+c2);
            char c;
            if(Math.random()<rate){
                for(int bit=0; bit<c1.length(); bit++)
                {
                    if(Math.random()<0.5){
                        c=c1.charAt(bit);
                        c1.setCharAt(bit,c2.charAt(bit));
//                        System.out.println("Pre c2 "+c2);
                        c2.setCharAt(bit,c);
//                        System.out.println("Post c2 "+c2+"---> "+c1.charAt(bit)+" position "+ bit);
                    }
                }
//                System.out.println("Second: "+c1+"----"+c2);
//                System.exit(0);
            }
            //performs mutation
            Mutation(c1,mutationRate);

            if(newPopulation.size()== population.size())
                break;
            else
                newPopulation.add(new Individual(String.valueOf(c1),EvaluationFunction.FitnessFunction(String.valueOf(c1))));

            Mutation(c2,mutationRate);
            if(newPopulation.size()<population.size())
                newPopulation.add(new Individual(String.valueOf(c2),EvaluationFunction.FitnessFunction(String.valueOf(c2))));
            else
                break;
        }

    }

        private boolean sameIndividual(StringBuilder c1, StringBuilder c2) {
        for (int ch = 0; ch < c1.length(); ch++) {
            if (c1.charAt(ch)!=c2.charAt(ch))
                return false;
        }
        return true;
    }
    private void Mutation(StringBuilder c, double rate) {
        if(Math.random()<rate){
            for(int bit =0; bit<c.length(); bit++)
            {
                if(Math.random()<0.5)
                {
                    if(c.charAt(bit)=='0')
                        c.setCharAt(bit, '1');
                    else
                        c.setCharAt(bit,'0');
                }
            }
        }
    }

}
