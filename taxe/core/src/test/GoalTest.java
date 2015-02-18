package test;

import gameLogic.goal.Goal;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.ResourceManager;
import gameLogic.resource.Train;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import test.LibGdxTest;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class GoalTest extends LibGdxTest {

    Station origin;
    Station destination;
    Station station3;
    Station station4;
    Station station5;
    Station station6;
    Station station7;
    Station station8;
    Station intermediary;
    Train train;
    Goal goal;
    Goal goal2;


    @Before
    public void setup() {
        origin = new Station("station1", new Position(5, 5));
        destination = new Station("station2", new Position(2, 2));

        station3= new Station("station3", new Position(3, 5));
        station4 = new Station("station4", new Position(4, 2));
        station5= new Station("station5", new Position(5, 1));
        station6 = new Station("station6", new Position(6, 2));
        station7= new Station("station7", new Position(7, 5));
        station8 = new Station("station8", new Position(8, 2));


        intermediary = new Station("stationi", new Position(5, 5));
        train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);
        goal = new Goal(origin, destination, intermediary, 0, 4, 50, 20, train);
        goal2 = new Goal(origin, destination, intermediary, 0, 4, 50, 20, null);

    }

    @Test
    public void testIsComplete() throws Exception {
        train.addHistory(origin, 0);
        train.addHistory(station3, 1);
        train.addHistory(station4, 4);
        train.addHistory(station5, 6);
        train.addHistory(station6, 10);
        train.addHistory(station7, 11);
        train.addHistory(station8, 16);

        assertEquals(false, goal.isComplete(train));
        train.setFinalDestination(destination);
        train.addHistory(destination, 18);
        assertEquals(true, goal.isComplete(train));

    }

    @Test
    public void testWentThroughStation() throws Exception {
        train.addHistory(origin, 0);
        //train.addHistory(station3, 1);
        train.addHistory(station4, 4);
        train.addHistory(station6, 10);
        train.addHistory(station7, 11);
        train.addHistory(station8, 16);

        assertEquals(false, goal2.wentThroughStation(train));
        train.addHistory(intermediary, 8);
        train.setFinalDestination(destination);
        train.addHistory(destination, 18);
        assertEquals(true, goal2.wentThroughStation(train));
    }

    @Test
    public void testCompletedWithinMaxTurns() throws Exception {

        Goal anotherGoal= new Goal (origin, destination, intermediary, 20, 0, 20, 50, train);
        assertEquals(false, anotherGoal.completedWithinMaxTurns(train));

        Goal yetAnotherGoal = new Goal(destination,origin,intermediary, 20, 10, 20, 50,train);
        train.addHistory(origin, 20);
        train.addHistory(station4, 22);
        train.addHistory(station6, 23);
        train.addHistory(station7, 40);
        train.addHistory(station8, 56);
        train.addHistory(destination,60);
        assertEquals(false, yetAnotherGoal.completedWithinMaxTurns(train));

    }

    @Test
    public void testCompletedWithTrain() throws Exception {
        assertEquals(true, goal.getTrain().getName()==train.getName());
        Train timeOfMyLife = new Train("I just love testing", "RedTrain.png", "RedTrainRight.png", 250);
        assertEquals(false, goal.getTrain().getName()==timeOfMyLife.getName());

    }


}