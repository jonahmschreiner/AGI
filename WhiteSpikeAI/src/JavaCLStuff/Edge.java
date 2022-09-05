package JavaCLStuff;

final class Edge {

static final int JUMP = 0;


static final int EXCEPTION = 0x7FFFFFFF;

final int info;

/** The successor block of this control flow graph edge. */
final Label successor;

/**
* The next edge in the list of outgoing edges of a basic block. See {@link Label#outgoingEdges}.
*/
Edge nextEdge;

Edge(final int info, final Label successor, final Edge nextEdge) {
 this.info = info;
 this.successor = successor;
 this.nextEdge = nextEdge;
}
}
