import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.SimulationConstants;

public class Order_du_graphe extends LC0_Algorithm {

    private String neighborStates [];
    private int counter = 1;

    @Override
    public String getDescription() {
        return "Spanning tree algorithm using LCO. \n" +
                "Rule 1: N--A --> A--M \n" + 
                "Rule 2: A--M --> F--A";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel()); //Set the label property for each node
        setLocalProperty("parent", -1); //Set the parent for all nodes as -1
        this.neighborStates = new String[vertex.getDegree()]; // get the number of neighbor nodes 
        for(int i = 0; i < vertex.getDegree(); i++){
            this.neighborStates[i]="N"; //Set all neighbor states to N by default
        }
        setLocalProperty("counter",counter);// Set counter for each node
    }

    @Override
    protected void onStarCenter() {
        this.neighborStates[neighborDoor] = getNeighborProperty("label").toString(); //Update the neighbor states for this node
        R1();
        R2();
        putProperty("Affichage",getNeighborProperty("counter"),SimulationConstants.PropertyStatus.DISPLAYED);
    }

    // Function for rule 1
    private void R1() {
        if (getLocalProperty("label").equals("N") && getNeighborProperty("label").equals("A")) { //Check if rule 1 applies for those 2 nodes
            setLocalProperty("label", "A");//If it applies then we change the node to status A and the neighbor to status M
            setNeighborProperty("label", "M"); 
            setLocalProperty("parent",neighborDoor); //The node that applies the rule saved the port on which its parent is located
            setDoorState(new MarkedState(true), neighborDoor); // Mark the link between those the local and neighbor node 
            this.neighborStates[neighborDoor] = getNeighborProperty("label").toString(); // Update the neighbors states array
        }
    }

    // Function for rule 2
    private void R2() {
        if(getLocalProperty("label").equals("A") && getNeighborProperty("label").equals("M")){ //Verify that rule 2 applies for this node and its neighbor
            // We check if the node that applies the rule is indeed a node with state "A" and its neighbor with "M"
            if(neighborDoor == (int) getLocalProperty("parent")){ 
                if(!neighborsExist()){ //There isn't any neighbors with the state "N"
                    setLocalProperty("label","F");
                    setNeighborProperty("label","A");
                    //Update counter
                    int localCounter = (int) getLocalProperty("counter");
                    int neighborCounter = (int) getLocalProperty("counter");
                    setNeighborProperty("counter",counter ++);
                }
            }
        }
    }

    // Function to check if the nodes still has any neighbor nodes with the state "N"
    private boolean neighborsExist(){
        boolean result = false;
        for(int i = 0; i<vertex.getDegree();i++){
            if(this.neighborStates[i].equals("N")){
                result = true;
            }
        }
        return result;
    }


    @Override
    public Object clone() {
        return new Order_du_graphe();
    }
}
