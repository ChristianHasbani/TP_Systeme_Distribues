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
        setLocalProperty("label",vertex.getLabel());
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("N")){
        int neighboorPortA = -1;
        for(int i = 0; i < getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort,"label").equals("A")){
                neighboorPortA = numPort;
            }
        }

        if(neighboorPortA != -1){
            setLocalProperty("label","A");
            setDoorState(new MarkedState(true), neighboorPortA);
        }

       }
    }
    
    @Override
    public Object clone(){
        return new Arbe_recouvrant();
    }
}
