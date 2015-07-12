import java.util.Iterator;

interface Vertex extends Decorator
{
	public void addEdge( Edge e );
	public void removeEdge( Edge e );
	public Iterator getEdges();
	public Iterator getAdjacentVertices();
}

