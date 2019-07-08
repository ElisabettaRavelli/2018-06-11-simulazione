package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private Graph<String, DefaultEdge> grafo;
	private List<String> state;
	
	public Model() {
		this.dao = new SightingsDAO();
		this.state = new ArrayList<>();
	}
	
	public List<Anno> getAnni(){
		return this.dao.getAnni();
	}
	
	public void creaGrafo(Integer anno) {
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		this.state = this.dao.getStati(anno);
		
		Graphs.addAllVertices(this.grafo, this.state);
		System.out.println("Vertici aggiunti");
		
		for(String stato1: grafo.vertexSet()) {
			for(String stato2: grafo.vertexSet()) {
				if(!stato1.equals(stato2)) {
					if(dao.esisteArco(stato1, stato2, anno)) {
						grafo.addEdge(stato1, stato2) ;
						System.out.println("Arco aggiunto: " + stato1 + " -> " + stato2 );
					}
				}
			}
		}
		
	}

	public int getVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getStati() {
		return this.state;
	}
	
	public List<String> getPrecedenti(String state){
		List<String> precedenti = new ArrayList<>();
		precedenti = Graphs.predecessorListOf(this.grafo, state);
		return precedenti;
	}
	
	public List<String> getSuccessori(String state){
		List<String> successori = new ArrayList<>();
		successori = Graphs.successorListOf(this.grafo, state);
		return successori;
	}
	
	public List<String> getRaggiungibili(String source){
		List<String> result = new ArrayList<>();
		GraphIterator<String, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, source);
		
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

}
