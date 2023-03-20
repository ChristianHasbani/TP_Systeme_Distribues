import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class TP5_Arbre_recouvrant extends LC2_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC2. \n" +
                "Rule: A --- N --> A x---x A";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel());// label for each node
    }

    @Override
    protected void onStarCenter(){
        int countNeighborsN = 0;
       if(getLocalProperty("label").equals("A")){// Check if the local node has label = "A"
        for(int i=0; i<getActiveDoors().size(); i++){ // Get all the edges using the getActiveDoors method
            int numPort = getActiveDoors().get(i); // Get the port number of each edge
            if(getNeighborProperty(numPort,"label").equals("N")){ // Check if the neighbor node has the state "N"
                countNeighborsN++;
                setNeighborProperty(numPort,"label", "A"); //if the condition is verified then change the neighbor's label to "A"
                setDoorState(new MarkedState(true),numPort); // Mark the edge between the local node and the neighbor node
            }
        }
        if(countNeighborsN == 0){
            localTermination();
        }
        }
    }
    
    @Override
    public Object clone(){
        return new TP5_Arbre_recouvrant();
    }
}
//"C:\Program Files\Java\jdk1.7.0_80\bin\javac" TP5_Arbre_recouvrant.java -cp ViSiDiA.jar