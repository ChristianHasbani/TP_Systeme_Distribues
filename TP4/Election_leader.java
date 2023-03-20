import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class Election_leader extends LC1_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC1. \n" +
                "Rule1: N(1) --- N(x) (only 1 neighbor) --> F(0) --- N(x-1) \n"+
                "Rule2: N(1) --- N(1) (no neighbor) --> E(0) --- F(0)";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel());// label for each node
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){ // Check if the local node has the label "N"
        int nbNeighbors = countNbNeighbors(); // Get the number of neighbors

        if(nbNeighbors == 1){ // Check if the number of neighbors with the label "N" is 1 
            setLocalProperty("label","F"); // This node failed to be a leader its a follower node
        }

        else if(nbNeighbors == 0){ // Check if the number of neighbors with tthe label "N" is 0 
            setLocalProperty("label","E"); // This node is the Leader
        }
        
       }
    }
    
    //Method to count number of neighbors of each node
    private int countNbNeighbors(){
        int result = 0;
        for(int i = 0; i< getActiveDoors().size(); i++){ // loop through all the edges of the local node using the getActiveDoors method
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort,"label").equals("N")){
                result++; // for each neighbor of with the label "N" increment the number of neighbors
            }
        }
        return result; // return the result containing the number of neigbors of the local node with the label "N"
    }

    @Override
    public Object clone(){
        return new Election_leader();
    }
}

//"C:\Program Files\Java\jdk1.7.0_80\bin\javac" Election_Leader.java -cp ViSiDiA.jar