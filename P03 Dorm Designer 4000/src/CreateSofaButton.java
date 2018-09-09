import java.io.File;

/**
 * <p>
 * This is the class for the SofaButton. The main methods makes one object of
 * this data type.
 * 
 * @author Conrad Duddingston
 *
 */
public class CreateSofaButton {

	// Assuming both the sofa and bed have the same dimensions
	private final static int WIDTH = 92;
	private final static int HEIGHT = 32;

	// Same fields as the above bed class
	private PApplet processing;
	private float[] position;
	private String label;

	/**
	 * Constructor for the sofa button.
	 * 
	 * @param x
	 *            - x value for the button
	 * @param y
	 *            - y value for the button
	 * @param processing
	 *            - mechanisms
	 */
	public CreateSofaButton(float x, float y, PApplet processing) {

		// Sets the parameterized x and y coordinates into the instances' postions array
		this.position = new float[] { x, y };

		// sets this class' processing field to that of the other classes
		this.processing = processing;

		// Creates label for the button-- this time it's a sofa
		this.label = new String("Create Sofa");

	}

	/**
	 * <p>
	 * Called from main's update() method.
	 * 
	 * @params n/a
	 */
	public void update() {

		// Default draws rectangle at the spot, untouched
		this.processing.fill(200);
		this.processing.rect(this.position[0] - (WIDTH / 2), this.position[1] + (HEIGHT / 2),
				this.position[0] + (WIDTH / 2), this.position[1] - (HEIGHT / 2));

		// If the mouse is over the button
		if (isMouseOver()) {

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
	 * Returns a furniture if mouse has been clicked down and isMouseOver() returns
	 * true. If not, null
	 * 
	 * @return Furniture
	 */
	public Furniture mouseDown() {

		// Declare a type of Furniture to return.
		Furniture returnVal;

		if (isMouseOver()) {

			// Not a documented exception handling circumstance that the assignment told me
			// to handle,
			// but I did this anyways. Doesn't hurt.
			File test = new File("images/sofa.png");
			if (test.exists()) {

				returnVal = new Furniture("sofa", processing);
			} else {

				System.out.println("WARNING: Could not find an image for a furniture object of type: sofa");
				returnVal = null;

			}

		} else {

			// Else, returns null, effectively doing nothing.
			returnVal = null;

		}

		return returnVal;

	}

	/**
	 * <p>
	 * Returns true if the nouse is indeed over the given object
	 * 
	 * @return boolean
	 */
	public boolean isMouseOver() {

		boolean returnVal = false;

		if (processing.mouseX > this.position[0] - (WIDTH / 2) && processing.mouseX < this.position[0] + (WIDTH / 2)
				&& processing.mouseY > this.position[1] - (HEIGHT / 2)
				&& processing.mouseY < this.position[1] + (HEIGHT / 2)) {

			returnVal = true;

		}

		return returnVal;
	}

}
