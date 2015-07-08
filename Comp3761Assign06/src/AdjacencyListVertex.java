import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

class AdjacencyListVertex implements Vertex
{
	public static final String name = "Name";
	public static final String visited = "Visited";
	private List edges_;
	private List adjVertices;
	
	public AdjacencyListVertex( Object n )
	{
		setAttribute( name, n );
		setAttribute( visited , false );
		edges_ = new LinkedList();
		adjVertices = new LinkedList();
	}
	
	public void addEdge( Edge e )
	{
		// instead of adding an edge to a list of edges
		// adding the other vertex to a list of vertices instead
		//edges_.add( e );
		if (!adjVertices.contains(e.otherVertex(this)))
		{
			adjVertices.add(e.otherVertex(this));
		}
	}
	
	public void removeEdge( Edge e )
	{
		for( Iterator i = edges_.iterator(); i.hasNext(); )
			if( e == (Edge)i.next() ){
				i.remove();
				break;
			}
	}

	public Iterator getEdges()
	{
		return edges_.iterator();
	}
	
	public Iterator getAdjacentVertices()
	{
		return new AdjIterator( this );
	}
	
	// modified so adjIterator returns iterator of adjVertices and not edges_
	class AdjIterator implements Iterator
	{
		private Iterator i_;
		private Vertex v_;
		
		public AdjIterator( Vertex v )
		{
			i_ = adjVertices.iterator();
			v_ = v;
		}

		public boolean hasNext()
		{
			return i_.hasNext();
		}

		public Object next()
		{
			return (Vertex )i_.next();
		}

		public void remove()
		{
			i_.remove();
		}
	}
	
	public final String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append( "{ " + getAttribute( name ) + ":  { " );
		for( Iterator i = getAdjacentVertices(); i.hasNext(); ){
			Vertex v = (Vertex)i.next();
			sb.append( v.getAttribute( name ) + " " );
		}
		sb.append( "} }" );
		return sb.toString();
	}
	
	//decorator pattern attributes and methods
	private final Map attributes_ = new HashMap();
	
	public final boolean hasAttribute( Object key )
	{
		return attributes_.containsKey( key );
	}
	
	public final Object getAttribute( Object key )
	{
		return attributes_.get( key );
	}
	
	public final void setAttribute( Object key )
	{
		attributes_.put( key, null );
	}
	
	public final void setAttribute( Object key, Object value )
	{
		attributes_.put( key, value );
	}

	public final void removeAttribute( Object key )
	{
		attributes_.remove( key );
	}
}

