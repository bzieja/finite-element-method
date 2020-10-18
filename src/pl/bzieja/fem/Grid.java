package pl.bzieja.fem;

public class Grid {
    int numberOfNodes;
    int numberOfElements;
    private Node[] Nodes;
    private Element[] Elements;

    public Grid(GlobalData globalData) {
        this.numberOfNodes = globalData.getNumberOfNodesAtHeight() * globalData.getNumberOfNodesAtWidth();
        this.numberOfElements = (globalData.getNumberOfNodesAtHeight() - 1) * (globalData.getNumberOfNodesAtWidth() - 1);
        this.Nodes = new Node[numberOfNodes];
        this.Elements = new Element[numberOfElements];
    }

    public void fillGrid(GlobalData globalData) {
        double dx = globalData.getWidth() / (globalData.getNumberOfNodesAtWidth() - 1);
        double dy = globalData.getHeight() / (globalData.getNumberOfNodesAtHeight() - 1);

        //fill Nodes
        for (int i = 0; i < globalData.getNumberOfNodesAtWidth(); i++) {
            for (int j = 0; j < globalData.getNumberOfNodesAtHeight(); j++) {
                this.Nodes[i * globalData.getNumberOfNodesAtHeight() + j] =
                        new Node(i * dx, j * dy);
            }
        }

        //fill Elements
        for (int i = 0, j = 0; i < globalData.getNumberOfElements(); i++, j++) {

            if (i > 0 && i % (globalData.getNumberOfNodesAtHeight() - 1) == 0) {
                j++;
            }

            int ID1 = j;
            int ID2 = ID1 + globalData.getNumberOfNodesAtHeight();
            int ID3 = ID2 + 1;
            int ID4 = ID1 + 1;

            Elements[i] = new Element(Nodes[ID1], Nodes[ID2], Nodes[ID3], Nodes[ID4]);
        }
    }

    public void validateElement (int elementID) {
        System.out.println("Element's ID: " + elementID);

        for (Node node : this.Elements[elementID - 1].getID()) {
            System.out.printf("         %d                  ", node.getNodeID());
        }
        System.out.printf("\n");

        for (Node node : this.Elements[elementID - 1].getID()) {
            System.out.printf("%f   %f          ", node.getX(), node.getY());
        }
        System.out.printf("\n");
    }

}
