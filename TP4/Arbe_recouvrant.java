import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class Arbe_recouvrant extends LC1_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC1. \n" +
                "Rule: N --- A --> A x---x A";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel()); // label for each node
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){//check if the local node has the label "N"
        int neighboorPortA = -1; // initialize the neighbor port if it has the label "A" to -1
        for(int i = 0; i < getActiveDoors().size(); i++){ // loop through the edges of the local node using the getActiveDoors method
            int numPort = getActiveDoors().get(i); // Get the number of each port
            if(getNeighborProperty(numPort,"label").equals("A")){ // Check if the port leades to a neighbor node with the label "A"
                neighboorPortA = numPort;// save the port number of the neighbor node with the label "A"
            }
        }

        if(neighboorPortA != -1){ // Check if the local node has a port number of a neighbor with the label "A"
            setLocalProperty("label","A");// Change the label of the local node to "A"
            setDoorState(new MarkedState(true), neighboorPortA); // Mark the edge between the local node and the neighbor node with the label "A"
        }
       }
    }
    
    @Override
    public Object clone(){
        return new Arbe_recouvrant();
    }
}
//"C:\Program Files\Java\jdk1.7.0_80\bin\javac" Arbe_recouvrant.java -cp ViSiDiA.jar