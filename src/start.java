import java.util.Scanner;

public class start {

    public static void main (String[] args) {
        boolean invalidInputs = false;
        Population population = null;
        int populationSize = 0;
        int numberOfInitiallyInfectedPeople = 0;
        while(invalidInputs){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the size of the population: ");
            populationSize = sc.nextInt();
            population = new Population(populationSize);
            System.out.println("Enter the min number of days an individual can be ill: ");
            population.individualIllnessTime[0] = sc.nextInt();
            System.out.println("Enter the max number of days an individual can be ill: ");
            population.individualIllnessTime[1] = sc.nextInt();

            if(population.individualIllnessTime[0] > population.individualIllnessTime[1]){
                System.out.println("Wrong inputs: max days are smaller than the min days. Start again");
            } else {
                System.out.println("Enter probability of infection as % :");
                population.probabilityOfInfection = sc.nextDouble()/100;
                System.out.println("Enter probability of death as %:");
                population.probabilityOfDeath = sc.nextDouble()/100;
                System.out.println("Enter Number of initially people infected:");
                numberOfInitiallyInfectedPeople = sc.nextInt();

                System.out.println("Locating the initial ill people within the population:");
                int populationMatrixSideLength = (int)Math.sqrt(populationSize);
                for(int i=0; i< numberOfInitiallyInfectedPeople; i++) {
                    System.out.println("set the position x for ill person " + (i+1) + ", the value should be at max "
                            + populationMatrixSideLength );
                    int x = sc.nextInt();
                    System.out.println("set the position y for ill person "+ (i+1) +", the value should be at max "
                            + populationMatrixSideLength );
                    int y = sc.nextInt();
                    population.individuals[x-1][y-1].setStatus('S');

                }
                population.showPopulation();
                System.out.println("   ------------------------------------   ");
                invalidInputs = false;
            }
        }
        // choose seed between 0 - 99
        RandomGenerator.setSeed(0);
        //population.runSimulation(numberOfInitiallyInfectedPeople);

        // getting results
        System.out.println("Enter Probability of Infection as %");
        Scanner sc = new Scanner(System.in);
        double probabilityOfInfection = sc.nextDouble()/100;
        population = new Population(1600);

        int results = 0;
        for (int i=0; i<100; i++) {
            population.probabilityOfInfection = probabilityOfInfection;
            population.individualIllnessTime[0] = 6;
            population.individualIllnessTime[1] = 9;
            population.individuals[20][20].setStatus('S');
            RandomGenerator.setSeed(i);
            System.out.println("seed #" + i);
            results+= population.runSimulation(1);
        }
        int median = results/100;
        System.out.println("The average number of infected people for probabaility of infection "+ probabilityOfInfection*100 +"% is: " + median);
    }
}
