package it.polito.tdp.ufo.model;

public class Anno implements Comparable<Anno>{
	
	private Integer anno;
	private Integer avvistamenti;
	public Anno(Integer anno, Integer avvistamenti) {
		super();
		this.anno = anno;
		this.avvistamenti = avvistamenti;
	}
	public Integer getAnno() {
		return anno;
	}
	public Integer getAvvistamenti() {
		return avvistamenti;
	}
	
	
	
	@Override
	public String toString() {
		return String.format("Anno=%s - Avvistamenti=%s ", anno, avvistamenti);
	}
	@Override
	public int compareTo(Anno o) {
		return anno.compareTo(o.anno);
	}
	
	

}
