import java.io.File;
/**
 * <p>
 * The CreateBedButton class allows the constructor of Main to create a button that creates a bed.
 * It is updated through calls by main's update().
 * @author Conrad Duddingston
 *
 */
public class CreateBedButton {
	
	// Default sizes of the bed, used for detection of mouse over it
	private static final int WIDTH = 96;
	private static final int HEIGHT = 32;
	 
	// Fields needed
	private PApplet processing;
	private float[] position;
	private String label;
	
	/**
	 * <p>
	 * Before calling rect(), you should call the fill method to set the color that
	 * this rectangle is filled with. When the mouse is over this button set
	 * fill(100) for a dark gray, and when the mouse is not over this button set
	 * fill(200) for a light gray.
	 * 
	 * @param x
	 * @param y
	 * @param processing
	 */
	public CreateBedButton(float x, float y, PApplet processing) {
		
		// 1) the x-position of the upper left corner, 2) the y-position of the upper
		// left corner, 3) the x-position of the lower right corner, 4) the y-position
		// of the lower right corner.
		
		// x and y desingate position of button
		this.position = new float[]{x, y};
		
		// sets this class' processing field to that of the other classes
		this.processing = processing;
		
		// Creates label for the button
		this.label = new String("Create Bed");
		

		
	}
	/**
	 * <p>
	 * Draws and fills the rectangle and manipulates coloring for the mouse on/off color effect
	 * @params n/a
	 */
	public void update() {
		
		// Default draws rectangle at the spot, untouched
		this.processing.fill(200);
		this.processing.rect(this.position[0] - (WIDTH / 2), this.position[1] + (HEIGHT / 2),
				this.position[0] + (WIDTH / 2), this.position[1] - (HEIGHT / 2));
		
		// If the mouse is over the button
		if(isMouseOver()) {
			
			this.processing.fill(100);
			
			this.processing.rect(this.position[0] - (WIDTH / 2), this.position[1] + (HEIGHT / 2),
					this.position[0] + (WIDTH / 2), this.position[1] - (HEIGHT / 2));
			
			
		} else {
			
			// Sets dark gray
			this.processing.fill(200);

			// Paints
			this.processing.rect(this.position[0] - (WIDTH / 2), this.position[1] + (HEIGHT / 2),
					this.position[0] + (WIDTH / 2), this.position[1] - (HEIGHT / 2));
			
		}
		
		this.processing.fill(0);
		this.processing.text(this.label, this.position[0], this.position[1]);
		
	}
	/**
	 * <p>
	 * Returns a Furniture object with type bed, instead of sofa.
	 * Is called from main, returns null if no furniture is to be made.
	 * @return Furniture
	 */
	public Furniture mouseDown() {

		// Will either be occupied or not.
		Furniture returnVal;

		// Calls helper method.
		if (isMouseOver()) {
			
			// Tests to see if bed image exists.
			File test = new File("images/bed.png");
			if(test.exists()) {
				
				// If so, instantiate.
				returnVal = new Furniture("bed", processing);
				
			} else {

				// Else, warn and return null.
				System.out.println("WARNING: Could not find an image for a furniture object of type: bed");
				returnVal = null;

			}

		} else {

			returnVal = null;

		}

		return returnVal;

	}
	/**
	 * <p>
	 * Detects if mouse is over the button and returns t/f
	 * @return boolean
	 */
	public boolean isMouseOver() {

		boolean returnVal = false;

		// Is x/y within bounds?
		if (processing.mouseX > this.position[0] - (WIDTH / 2) && processing.mouseX < this.position[0] + (WIDTH / 2)
				&& processing.mouseY > this.position[1] - (HEIGHT / 2)
				&& processing.mouseY < this.position[1] + (HEIGHT / 2)) {

			returnVal = true;

		}

		return returnVal;
	}

}
