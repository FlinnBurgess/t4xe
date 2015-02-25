package gameLogic.map;

public class Connection {
    private Station station1;
    private Station station2;
    //Added this variable to the Connection class which indicates how long the connection shall be blocked for
    //We could have used a boolean to indicate whether it was blocked but this implementation makes it very easy to set the connections to be blocked for a certain number of turns
    private int blocked;

    public Connection(Station station1, Station station2) {
        this.station1 = station1;
        this.station2 = station2;
        //Blocked set to 0 initially, indicating that it is not blocked
        this.blocked = 0;
    }

    public Station getStation1() {
        return this.station1;
    }

    public Station getStation2() {
        return this.station2;
    }

    public boolean isBlocked() {
        return this.blocked > 0;
    }

    public void decrementBlocked() {
        //This is run every turn to decrement the turns blocked on the connection if it is greater than 0
        if (this.blocked > 0) {
            this.blocked--;
        }
    }

    public int getTurnsBlocked() {
        return blocked;
    }

    public void setBlocked(int turns) {
        this.blocked = turns;
    }

    public IPositionable getMidpoint() {
        //This returns the midPoint of the connection, which is useful for drawing the obstacle indicators on to the connection
        return new IPositionable() {
            @Override
            public int getX() {
                return (station1.getLocation().getX() + station2.getLocation().getX()) / 2;
            }

            @Override
            public int getY() {
                return (station1.getLocation().getY() + station2.getLocation().getY()) / 2;
            }

            @Override
            public void setX(int x) {

            }

            @Override
            public void setY(int y) {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }
        };
    }
}