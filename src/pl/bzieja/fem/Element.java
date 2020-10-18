package pl.bzieja.fem;

public class Element {
    private Node[] ID; // Nodes included in finite Element

    /*
     *   ID4         ID3
     *   -------------
     *   |           |
     *   |           |
     *   -------------
     *   ID1          ID2
     * */

    public Element(Node ID1, Node ID2, Node ID3, Node ID4) {
        this.ID = new Node[] {ID1, ID2, ID3, ID4};
    }

    public Node[] getID() {
        return ID;
    }
}
