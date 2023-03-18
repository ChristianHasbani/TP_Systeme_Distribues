import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.SimulationConstants;


public class Election_Leader extends LC0_Algorithm {

    @Override
    public String getDescription() {
        return "Spanning tree algorithm using LCO. \n" +
                "Rule 1: N(1) --- N(x) --> F(0) x---x N(x-1) \n" + 
                "Rule 2: N(1) x---x N(1) --> E(0) x---x F(0)";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel()); // label for each node
        setLocalProperty("nbNeighbors", vertex.getDegree()); // number of neighbor nodes
        putProperty("Affichage", "Neighbor N = " +getLocalProperty("nbNeighbors"),SimulationConstants.PropertyStatus.DISPLAYED); // display
    }

    @Override
    protected void onStarCenter() {
     if(getLocalProperty("label").equals("N") && getNeighborProperty("label").equals("N")){ // Check if local and neighbor nodes are both in state "N"
            int nbNeighborsLocal = (int) getLocalProperty("nbNeighbors"); // Get the number of neighbor nodes of the local node
            int nbNeighborsNeighbor = (int) getNeighborProperty("nbNeighbors"); // Get the number of neighbor nodes of the neighbor node
            if(nbNeighborsLocal == 1 && nbNeighborsNeighbor > 1){ // Check 1st rule of our algorithm applies
                setLocalProperty("label","F"); // Change local node label to "F" and number of neighbors to 0
                setLocalProperty("nbNeighbors",0);
                setNeighborProperty("nbNeighbors",nbNeighborsNeighbor - 1); // Decrease the number of neighbors of the neighbor node by 1
            }
            else if(nbNeighborsLocal == 1 && nbNeighborsNeighbor == 1){ // Check if the 2nd rule applies
                setLocalProperty("label","E"); // Change local node label to "E" and number of neighbors to 0
                setLocalProperty("nbNeighbors",0);
                setNeighborProperty("label","F");// Change the neighbor node label to "F" and number of neihgbors to 0
                setNeighborProperty("nbNeighbors",0);
            }
            display();
        }
    }

    // Method to display on the graph
    private void display(){
        if(getLocalProperty("label").equals("F")){
                putProperty("Affichage", "", SimulationConstants.PropertyStatus.DISPLAYED);
            }
            else if(getLocalProperty("label").equals("E")){
                putProperty("Affichage", "Leader", SimulationConstants.PropertyStatus.DISPLAYED);
            }
            else{
                putProperty("Affichage","Neighbor N = " + getLocalProperty("nbNeighbors"),
                SimulationConstants.PropertyStatus.DISPLAYED);
            } 
    }
    

    @Override
    public Object clone() {
        return new Election_Leader();
    }
}

//"C:\Program Files\Java\jdk1.7.0_80\bin\javac" Profondeur.java -cp ViSiDiA.jar