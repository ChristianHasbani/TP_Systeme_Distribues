import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class TP5_Election_Leader extends LC2_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC2. \n" +
                "Rule1: N --- N (only 1 neighbor) --> F --- N \n"+
                "Rule2: N --- F (no neighbor) --> E --- F";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel()); // Give a label for each node
        setLocalProperty("nbNeighbors",vertex.getDegree()); // Get the number of neighbors for each node
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){ // Check if the local node has the label "N"
        int nbNeighbors = checkNeighbors(); 
        if(nbNeighbors == 0){ // Check if the local node has any local neighbors with the label "N"
            setLocalProperty("label","E"); // If no neighbors with label N then this node is the Leader
        }
       }else if (getLocalProperty("label").equals("F") || getLocalProperty("label").equals("E")){
        localTermination();
       }
    }
    
    //Method to check if a node has neighbors
    private int checkNeighbors(){
        int nbNeighbors = 0;
        for(int i = 0; i< getActiveDoors().size(); i++){ // Loop through the edges using the getActiveDoors method
            int numPort = getActiveDoors().get(i); // Get the port number of each edge
            if(getNeighborProperty(numPort,"label").equals("N")){ // Check if the neighbor node has the label "N"
                nbNeighbors++;
                if((int) getNeighborProperty(numPort,"nbNeighbors") == 1){ // Check if the local node has 1 neighbor
                    setNeighborProperty(numPort,"label", "F"); // Rewrite this node's label to "F" so it's not the leader
                }
            }
            setLocalProperty("nbNeighbors", nbNeighbors);// Set the number of neighbors after adding the new value
        }
        return nbNeighbors; // Return the number of neighbors
    }

    @Override
    public Object clone(){
        return new TP5_Election_Leader();
    }
}
//"C:\Program Files\Java\jdk1.7.0_80\bin\javac" TP5_Election_Leader.java -cp ViSiDiA.jar
