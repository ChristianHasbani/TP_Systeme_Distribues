import visidia.simulation.process.algorithm.LC2_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class TP5_Arbre_recouvrant extends LC2_Algorithm {

    @Override
   public String getDescription(){
        return "Spanning tree algorithm using LC2. \n" +
                "Rule: N --- A --> A x---x A";
    }

    @Override
    protected void beforeStart(){
        setLocalProperty("label",vertex.getLabel());
    }

    @Override
    protected void onStarCenter(){
       if(getLocalProperty("label").equals("A")){
        for(int i=0; i<getActiveDoors().size(); i++){
            int numPort = getActiveDoors().get(i);
            if(getNeighborProperty(numPort,"label").equals("N")){
                setNeighborProperty(numPort,"label", "A");
                setDoorState(new MarkedState(true),numPort);
            }
        }
       }
    }
    
    @Override
    public Object clone(){
        return new TP5_Arbre_recouvrant();
    }
}
