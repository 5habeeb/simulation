public class Individual {
    /*
    * The status of individual:
    * H = healthy
    * S = sick
    * X = dead
    * I = Immune
    * */
    private char status = 'H';
    int individualIllDays = 0;
    private int posX,posY;

    public Individual(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    public String toString(){
        return this.status+"";
    }

    public char getStatus() { return this.status; }
    public void setStatus( char status) {
        this.status = status;
    }

}

