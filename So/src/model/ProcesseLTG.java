package model;

public class ProcesseLTG {
	
	private String name;
	private int time;
	private int dead;
	
	
	
	public ProcesseLTG(String name, int time, int dead) {
		super();
		this.name = name;
		this.time = time;
		this.dead = dead;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getTime() {
		return time;
	}



	public void setTime(int time) {
		this.time = time;
	}



	public int getDead() {
		return dead;
	}



	public void setDead(int dead) {
		this.dead = dead;
	}
	
	
	
	
	
	
	
	

}
