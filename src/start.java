import java.util.Scanner;

public class start {

    public static void main (String[] args) {
        boolean invalidInputs = true;
        Population population = null;
        int populationSize = 0;
        int numberOfInitiallyInfectedPeople = 0;

        System.out.println("Welcome! \n " +
                            "1. Set input manually \n " +
                            "2. Run for default data");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        if(choice == 1) {
            while(invalidInputs){
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the size of the population: ");
                populationSize = sc.nextInt();
                population = new Population(populationSize, true);
                System.out.println("Enter the min number of days an individual can be ill: ");
                population.individualIllnessTime[0] = sc.nextInt();
                System.out.println("Enter the max number of days an individual can be ill: ");
                population.individualIllnessTime[1] = sc.nextInt();

                if(population.individualIllnessTime[0] > population.individualIllnessTime[1]){
                    System.out.println("Invalid inputs: max days are smaller than the min days. Start again");
                } else {
                    System.out.println("Enter probability of infection as % :");
                    population.probabilityOfInfection = sc.nextDouble()/100;
                    System.out.println("Enter probability of death as %:");
                    population.probabilityOfDeath = sc.nextDouble()/100;
                    System.out.println("Enter Number of initially people infected:");
                    numberOfInitiallyInfectedPeople = sc.nextInt();

                    System.out.println("Locating the initial ill people within the population:");
                    int populationMatrixSideLength = (int)Math.sqrt(populationSize);

                    int counter = 0;
                    while((counter < numberOfInitiallyInfectedPeople) && invalidInputs){
                        try{
                            System.out.print("set the position x for ill person " + (counter+1) + ", the value should be at max "
                                    + populationMatrixSideLength + ": " );
                            int x = sc.nextInt();
                            System.out.print("set the position y for ill person "+ (counter+1) +", the value should be at max "
                                    + populationMatrixSideLength + ": " );
                            int y = sc.nextInt();
                            population.individuals[x-1][y-1].setStatus('S');
                            counter++;
                        } catch (Exception e) {
                            System.out.println("Invalid inputs! Try Again");
                        }

                    }

                    invalidInputs = false;
                }
            }
            // choose seed between 0 - 99
            RandomGenerator.setSeed(0);
            population.showPopulation();
            System.out.println("   ------------------------------------   ");
            population.runSimulation(numberOfInitiallyInfectedPeople);


        } else if( choice == 2) {
            // Collect results
            System.out.println("Enter Probability of Infection as %");
            Scanner sc = new Scanner(System.in);
            double probabilityOfInfection = sc.nextDouble()/100;
            population = new Population(1600, false);

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
            int mean = results/100;
            System.out.println("The average number of infected people for probabaility of infection "+ probabilityOfInfection*100 +"% is: " + mean);
        }
    }
}
