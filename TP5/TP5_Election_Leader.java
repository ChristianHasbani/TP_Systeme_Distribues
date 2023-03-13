import visidia.simulation.process.algorithm.LC1_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
// STILL NOT FUCKING WORKING 
public class TP5_Election_Leader extends LC1_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC2. \n" +
                "";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel());
        setLocalProperty("nbNeighbors",vertex.getDegree());
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){
        boolean hasNeighbor = checkNeighbors();
        if(!hasNeighbor){
            setLocalProperty("label","E");
        }
       }
    }
    
    //Method to check if a node has neighbors
    private boolean checkNeighbors(){
        boolean result = false;
        for(int i = 0; i< getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort,"label").equals("N")){
                result = true;
                if((int) getNeighborProperty(numPort,"nbNeighbors") == 1){
                    setNeighborProperty(numPort,"label", "F");
                }
            }
        }
        return result;
    }

    @Override
    public Object clone(){
        return new TP5_Election_Leader();
    }
}
