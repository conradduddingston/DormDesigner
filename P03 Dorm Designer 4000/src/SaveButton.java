import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveButton {

	// Universal button dimensions
	private final static int WIDTH = 92;
	private final static int HEIGHT = 32;

	// Fields
	private PApplet processing;
	private float[] position;
	private String label;
	private Furniture[] furniture;
	private PrintWriter write;
	private File pathway;

	/**
	 * Only constructor within SaveButton.
	 * Only initializes three of the seven fields.
	 * @param x
	 * @param y
	 * @param processing
	 */
	public SaveButton(float x, float y, PApplet processing) {

		// Main constructor called once from main
		this.processing = processing;
		this.position = new float[] { x, y };
		this.label = new String("Save Button");

	}

	/**
	 * <p>
	 * Update method called repeatedbly from the update() method of
	 * main.
	 * No params.
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

		// Draws the text
		this.processing.fill(0);
		this.processing.text(this.label, this.position[0], this.position[1]);

	}

	/**
	 * <p>
	 * Called from mouseDown() member method here in SaveButton.
	 * @return
	 */
	public boolean isMouseOver() {

		boolean returnVal = false;

		// Bounds of mouse in relation to button.
		if (processing.mouseX > this.position[0] - (WIDTH / 2) && processing.mouseX < this.position[0] + (WIDTH / 2)
				&& processing.mouseY > this.position[1] - (HEIGHT / 2)
				&& processing.mouseY < this.position[1] + (HEIGHT / 2)) {

			returnVal = true;

		}

		return returnVal;
	}

	// Mouse down. Called from main.
	// params: Furniture[] furniture
	public void mouseDown(Furniture[] furniture) {

		// Calls helper method is isMouseOver()
		if (isMouseOver()) {

			// Calls private method to do the saving.
			this.saveFile(furniture);

		}

	}
	/**
	 * <p>
	 * Private method called from the consturctor that creates an instance of PrintWriter
	 * and utilizes file to print exact data of all furniture objects.
	 * @param furniture
	 */
	private void saveFile(Furniture[] furniture) {

		this.furniture = furniture;

		try {

			// Creates pathway to RoomData.ddd
			pathway = new File("RoomData.ddd");
			write = new PrintWriter(pathway);

			// If it exists and is writable, this won't execute. Else, throw an exception.
			// Note: It is actually more work to make use of exceptions here as opposed to a
			// simple if statement
			// but I'm given the option so I might as well have some fun.
			if (!pathway.exists() || !pathway.canWrite()) {

				throw new FileNotFoundException();

			}

			// Cycles through array of furniture.
			for (int i = 0; i < this.furniture.length; i++) {

				if (this.furniture[i] != null) {

					// Writes using .print function. Uses accessor methods from furniture class.
					write.print(this.furniture[i].getType() + ":");
					write.print(this.furniture[i].getX() + "," + this.furniture[i].getY() + ",");
					write.println(this.furniture[i].getRotations());

				}

			}

			// Exception mandated by the assignment for when RoomData.ddd isn't writable or
			// cannot be created.
		} catch (FileNotFoundException e) {

			System.out.println("WARNING: Could not save room contents to file RoomData.ddd.");

			// Extra exception I added in case of some extraneous exception.
		} catch (Exception e2) {

			System.out.println("WARNING: Unspecified, generic exception thrown from SaveFile.");

			// Always executes. If not null, close. Stops memory leaks.
		} finally {

			if (write != null) {

				write.close();

			}

		}

	}

}
