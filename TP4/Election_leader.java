import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class Election_leader extends LC1_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC1. \n" +
                "Rule: A---N ---> A---A";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel());
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){
        int nbNeighbors = countNbNeighbors();

        if(nbNeighbors == 1){
            setLocalProperty("label","F");
        }

        else if(nbNeighbors == 0){
            setLocalProperty("label","E");
        }
        
       }
    }
    
    //Method to count number of neighbors of each node
    private int countNbNeighbors(){
        int result = 0;
        for(int i = 0; i< getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort,"label").equals("N")){
                result++;
            }
        }
        return result;
    }

    @Override
    public Object clone(){
        return new Election_leader();
    }
}
