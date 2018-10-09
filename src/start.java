import java.util.Scanner;

public class start {

    public static void main (String[] args) {
        boolean invalidInputs = true;
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
                System.out.println("Enter probability of infection :");
                population.probOfInfection = sc.nextDouble();
                System.out.println("Enter probability of death:");
                population.probOfDeath = sc.nextDouble();
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
        // create a loop 100 times and collect data.
        population.runSimulation(numberOfInitiallyInfectedPeople);
    }
}
