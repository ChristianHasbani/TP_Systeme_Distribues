import visidia.simulation.process.algorithm.LC0_Algorithm;
import visidia.simulation.process.edgestate.MarkedState;

public class Profondeur extends LC0_Algorithm {

    private String neighborStates [];

    @Override
    public String getDescription() {
        return "Spanning tree algorithm using LCO. \n" +
                "Rule 1: N --- A --> A x---x M \n" + 
                "Rule 2: A x---x M --> F x---x A";
    }

    @Override
    protected void beforeStart() {
        setLocalProperty("label", vertex.getLabel()); //Set the label property for each node
        setLocalProperty("parent", -1); //Set the parent port to -1 to remember later the port that is the start of all the neighbors
        this.neighborStates = new String[vertex.getDegree()]; // get the number of neighbor nodes 
        for(int i = 0; i < vertex.getDegree(); i++){
            this.neighborStates[i]="N"; //Set all neighbor states to N by default
        }
        setLocalProperty("counter",1);// Add a counter property
        putProperty("Affichage",getLocalProperty("counter").toString(), SimulationConstants.PropertyStatus.DISPLAYED); // Display the counter
    }

    @Override
    protected void onStarCenter() {
        this.neighborStates[neighborDoor] = getNeighborProperty("label").toString(); //Update the neighbor states for the node rewriting the rules
        R1();
        R2();
    }

    // Function for rule 1
    private void R1() {
         //Check if rule 1 applies for those 2 nodes
         //Check if the local node has a label "N" and the neighbor node has the label "A"
        if (getLocalProperty("label").equals("N") && getNeighborProperty("label").equals("A")) {
            setLocalProperty("label", "A");// Change the local node's label to A 
            setNeighborProperty("label", "M"); // The neighbor's to M
            setLocalProperty("parent",neighborDoor); //The node that applies the rule saved the port on which its parent is located to mark the link
            setDoorState(new MarkedState(true), neighborDoor); // Mark the link between those the local and neighbor node 
            this.neighborStates[neighborDoor] = getNeighborProperty("label").toString(); // Update the neighbors states array
        }
    }

    // Function for rule 2
    private void R2() {
         //Verify that rule 2 applies for this node and its neighbor
         // Check if the local node has the label "A" and the neighbor node has the label "M"
        if(getLocalProperty("label").equals("A") && getNeighborProperty("label").equals("M")){
            if(neighborDoor == (int) getLocalProperty("parent")){ // Check if the link is marked
                if(!neighborsExist()){ //There isn't any neighbors with the state "N"
                    setLocalProperty("label","F"); //Change the label of the local node
                    setNeighborProperty("label","A"); // Chagne the label of the neighbor node
                    setNeighborProperty("counter", Integer.parseInt(getLocalProperty("counter").toString()) +
                     Integer.parseInt(getNeighborProperty("counter").toString()));
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
        return new Profondeur();
    }
}
