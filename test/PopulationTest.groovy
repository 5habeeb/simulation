import org.junit.Assert
import org.junit.Before;
import org.junit.Test;

class PopulationTest extends GroovyTestCase {
    Population testPopulation = new Population(500, false);
    char X = 'X';
    char I = 'I';
    char S = 'S';

    @Test
    void test_killIndividual() {
        testPopulation.killIndividual(10,10);
        Assert.assertEquals("The sick person did not die!!!", X, testPopulation.individuals[10][10].getStatus());
        System.out.println("Unit test_killIndividual finished without error.");
    }

    @Test
    void test_cureIndividual() {
        testPopulation.cureIndividual(10,10);
        Assert.assertEquals("The sick person did not die!!!", I, testPopulation.individuals[10][10].getStatus());
        System.out.println("Unit test_cureIndividual finished without error.");
    }

    @Test
    void test_getIndividualDirectNeighbours_centerLocated() {
        Assert.assertEquals('Should check when individual has 8 direct neighbours', 8, testPopulation.getIndividualDirectNeighbours(10,10).size());
        System.out.println("Unit test_getIndividualDirectNeighbours_centerLocated finished without error.");
    }

    @Test
    void test_getIndividualDirectNeighbours_cornerLocated() {
        Assert.assertEquals('Should check when individual has 8 direct neighbours', 3, testPopulation.getIndividualDirectNeighbours(0,0).size());
        System.out.println("Unit test_getIndividualDirectNeighbours_cornerLocated finished without error.");
    }

    @Test
    void test_getIndividualDirectNeighbours_sideLocated() {
        Assert.assertEquals('Should check when individual has 8 direct neighbours', 5, testPopulation.getIndividualDirectNeighbours(21,11).size());
        System.out.println("Unit test_getIndividualDirectNeighbours_sideLocated finished without error.");
    }

    @Test
    void test_runSimulation_noSickPeople() {
        Assert.assertEquals('Should run for no day', 0, testPopulation.getSimulationDays())
        System.out.println("Unit test_runSimulation_noSickPeople finished without error.");
    }

    @Test
    void test_runSimulation_probabilityOfInfection0() {
        testPopulation.individuals[10][10].setStatus(S);
        testPopulation.probabilityOfInfection = 0.0;
        testPopulation.individualIllnessTime[0] = 5;
        testPopulation.individualIllnessTime[1] = 10;
        testPopulation.runSimulation(1);

        Assert.assertEquals('Should run for 10 days', 10, testPopulation.getSimulationDays())
        System.out.println("Unit test_runSimulation_noSickPeople finished without error.");
    }

    @Test
    void test_runSimulation_probabilityOfInfection100() {
        testPopulation.individuals[10][10].setStatus(S);
        testPopulation.individualIllnessTime[0] = 5;
        testPopulation.individualIllnessTime[1] = 10;
        testPopulation.probabilityOfInfection = 1;
        testPopulation.runSimulation(1);

        Assert.assertEquals('Should all individuals get infected', 483, testPopulation.accumulatedInfectedIndividuals)
        System.out.println("Unit test_runSimulation_noSickPeople finished without error.");
    }
}
