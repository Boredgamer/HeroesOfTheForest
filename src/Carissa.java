
public class Carissa extends Ally {

	private int stage = LARVA;
	private static int LARVA = 0;
	private static int COCOON = 1;
	private static int ADULT = 2;
	
	public Carissa(){
		
	}
	
	public void transform(){
		stage++;
	}
}
