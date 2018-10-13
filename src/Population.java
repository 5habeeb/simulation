import java.util.ArrayList;

public class Population {

    double probabilityOfInfection;
    double probabilityOfDeath;

    private int populationMatrixMaxLength;
    int[] individualIllnessTime = new int[2];
    Individual[][] individuals;

    private int totalNumberOfIllPeople = 0;
    private int totalNumberOfDeaths =0;
    private int accumulatedInfectedIndividuals = 0;
    private int day;

    private int diedToday;
    private int recoveredToday;
    private int illToday;
    private int infectedToday;

    public Population(int populationSize) {
        this.populationMatrixMaxLength = (int) Math.sqrt(populationSize);
        individuals = new Individual[populationMatrixMaxLength][populationMatrixMaxLength];
        for(int x = 0; x< populationMatrixMaxLength ; x++){
            for(int y = 0; y< populationMatrixMaxLength; y++){
                individuals[x][y] = new Individual(x,y);
            }
        }
    }

    public int runSimulation(int initialIllNumber){
        populationMatrixMaxLength= individuals.length;
        totalNumberOfIllPeople += initialIllNumber;
        ArrayList<Individual> infectedIndividualsToday = new ArrayList<>();
        day = 0;

        while(totalNumberOfIllPeople != 0){
            diedToday = 0;
            recoveredToday = 0;
            illToday = 0;
            infectedToday = 0;
            infectedIndividualsToday.clear();
            day++;

            for (int i = 0; i < populationMatrixMaxLength; i++) {
                for (int j = 0; j < populationMatrixMaxLength; j++) {

                    // If Individual is ill
                    if(individuals[i][j].getStatus() == 'S'){
                        illToday++;

                        // individual might die
                        if(RandomGenerator.generateRandom() < this.probabilityOfDeath){
                            killIndividual(i,j);
                        }

                        else { // individual still ill
                            individuals[i][j].individualIllDays++;
                            // individual may survive
                            if(individuals[i][j].individualIllDays >= this.individualIllnessTime[1]){
                                cureIndividual(i, j);
                            }

                            // Individual spread the disease
                            Individual[] directNeighbours = this.getIndividualDirectNeighbours(i, j);
                            for (int k = 0; k < directNeighbours.length; k++) {
                                if (directNeighbours[k].getStatus() == 'H') {
                                    if (RandomGenerator.generateRandom()<this.probabilityOfInfection) {
                                        infectedIndividualsToday.add(directNeighbours[k]);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // set the neighbours to be ill after the infection
            for(int i =0; i<infectedIndividualsToday.size();i++){
                if(infectedIndividualsToday.get(i).getStatus()== 'H'){
                    infectedIndividualsToday.get(i).setStatus('S');
                    totalNumberOfIllPeople++;
                    accumulatedInfectedIndividuals++;
                    infectedToday++;
                }
            }
            totalNumberOfDeaths += diedToday;

//            System.out.println("Day: "+ day);
//            this.showPopulation();
//            System.out.println("");
//            System.out.println("The number of individuals that becomes infected today: " + infectedToday);
//            System.out.println("The number of individuals that died today: " + diedToday);
//            System.out.println("The number of individuals that have recovered today: "+ recoveredToday);
//            System.out.println("The number of ill individuals today: "+ totalNumberOfIllPeople);
//            System.out.println("The accumulated number of deaths so far: "+ totalNumberOfDeaths);
//            System.out.println("The accumulated number of infected individuals so far: "+ accumulatedInfectedIndividuals);
//            System.out.println("");
        }

        System.out.println("The simulation lasted for " + day + " days, #infected people is: " + accumulatedInfectedIndividuals );
        int populationSize = populationMatrixMaxLength*populationMatrixMaxLength/2;
        if(accumulatedInfectedIndividuals > populationSize ) {
            System.out.println("This is an epidemic, people who got infected: " + accumulatedInfectedIndividuals + " out of " + populationSize );
        }
        return accumulatedInfectedIndividuals;
    }

    public void killIndividual(int x, int y) {
        individuals[x][y].setStatus('X');
        diedToday++;
        illToday--;
        totalNumberOfIllPeople--;
    }

    public void cureIndividual(int x, int y) {
        individuals[x][y].setStatus('I');
        recoveredToday++;
        illToday--;
        totalNumberOfIllPeople--;
    }

    public void showPopulation() {
        for (int i=0; i < populationMatrixMaxLength ; i++) {
            for (int j=0; j < populationMatrixMaxLength ; j++) {
                System.out.print("   " + individuals[i][j]);
            }
            System.out.println("");
        }
    }

    private Individual[] getIndividualDirectNeighbours(int i, int j) {
        ArrayList<Individual> neighbours = new ArrayList<>();
        for(int n=i-1; n<=i+1; n++){
            for(int m=j-1;m<=j+1; m++){
                if(!(n==i && m==j)){
                    if(this.getIndividual(n,m)!=null){
                        neighbours.add(this.getIndividual(n, m));
                    }
                }
            }
        }
        return neighbours.toArray(new Individual[neighbours.size()]);
    }

    private Individual getIndividual(int x, int y) {
        if ( x >= 0 && x < populationMatrixMaxLength
                && y >= 0 && y < populationMatrixMaxLength)
                return individuals[x][y];
        return null;
    }

    public int getSimulationDays() {
        return day;
    }

}
