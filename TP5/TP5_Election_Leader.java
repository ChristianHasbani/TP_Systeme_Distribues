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
        int nbNeighbors = 0;
        for(int j = 0; j<getActiveDoors().size(); j++){
            int numPort = getActiveDoors().get(j);
            if(getNeighborProperty(numPort, "label").equals("N")){
                nbNeighbors++;
            }
            setLocalProperty("nbNeighbors",nbNeighbors);
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
