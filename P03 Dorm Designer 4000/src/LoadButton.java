import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Instance of a button we use that, if clicked, creates an instance of LoadFile, an object that loads 
 * the contents of a file (RoomData.ddd(.txt)) into the game.
 * @author Conrad Duddingston
 */
public class LoadButton {
	
	private final static int WIDTH = 92;
	private final static int HEIGHT = 32;

	// Fields neccesary.
	// processing: Our "blackbox" we use. PApplet.
	// position: float array containing x and y
	// label: Label for our button.
	// in: reference to our LoadFile instance we'll instantiate later.
	private PApplet processing;
	private float[] position;
	private String label;
	private int line;
	private File testPath;
	private Scanner sc;
	private Scanner parser;
	private String type;
	private Furniture[] furniture;
	
	/**
	 * <p>
	 * Main and only constructor.
	 * @param x
	 * @param y
	 * @param processing
	 */
	public LoadButton(float x, float y, PApplet processing) {
		
		// Fields used through the process of saving.
		this.processing = processing;
		this.position = new float[] {x, y};
		this.testPath = null;
		this.line = 0;
		this.label = new String("Load Button");
		
	}
	/**
	 * <p>
	 * Called from main. No params.
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

		// Draws the text
		this.processing.fill(0);
		this.processing.text(this.label, this.position[0], this.position[1]);
	}
	
	// Helper method.
	public boolean isMouseOver() {

		boolean returnVal = false;

		if (processing.mouseX > this.position[0] - (WIDTH / 2) && processing.mouseX < this.position[0] + (WIDTH / 2)
				&& processing.mouseY > this.position[1] - (HEIGHT / 2)
				&& processing.mouseY < this.position[1] + (HEIGHT / 2)) {

			returnVal = true;

		}

		return returnVal;
	}
	/**
	 * <p>
	 * Called from main. Utilizes helper method isMouseOver()
	 * @param furniture
	 */
	public void mouseDown(Furniture[] furniture) {
		
		// If mouseIsOver() returns true-- helper method.
		if(isMouseOver()) {
			
			// Removes all existing peices of furniture
			for (int i = 0; i < furniture.length; i++) {
				
				// If exists
				if (furniture[i] != null) {
					
					// Remove
					furniture[i] = null;

				}

			}

			// Creates instance of LoadFile to load contents.
			// Arguments: furiniture array, processing PApplet
			this.loadData(furniture);

		}

	}

	/**
	 * <p>
	 * Private method called to load the file data. Uses exception handling.
	 * @param pathway
	 */
	private void loadData(Furniture[] furniture) {

		// Assigns furniture to the field.
		this.furniture = furniture;

		/**
		 * When loading a file: if a “RoomData.ddd” file does not exist or cannot be
		 * read from, your program should print the following message to System.out:
		 * “WARNING: Could not load room contents from file RoomData.ddd.” Your program
		 * should do this instead of making any changes to the furniture in the room.
		 */
		try {

			// First check: To see if pathway exists.
			// Throws: FileNotFoundException if pathway does not exist.
			File pathway = new File("RoomData.ddd");
			if (!pathway.exists()) {

				throw new FileNotFoundException();

			} else {

					sc = new Scanner(pathway);
					
				}
				
				// Starts main loop. Each iteration signals a new line attempting to be read.
				// Condition: hasNextLine, meaning it iterates until all lines have been read and/or found illegal.
				while (sc.hasNextLine()) {
					
					line++;
				if (!(line > 6)) {

					// Tries to read a line.
					// Line iterates from 0 to 1, signifying first line being read.
					try {

						// Takes in-- scans-- new line. Trims it.
						String temp = sc.nextLine().trim();

						// If it's but a newline, next.
						if (temp.length() == 0) {

							continue;

						}

						// Calls helper method checkSyntax. Check syntax checks to make sure the
						// prescence of a
						// type, checks to see if colon and commas are in correct positions. Also checks
						// number of
						// commas. ArrayIndexOutOfBounds exceptions are through for any syntactical
						// issues.
						if (checkSyntax(new String(temp))) {

							// Explicitly throwing this exception.
							throw new ArrayIndexOutOfBoundsException();

						}

						// Type at this point contains the name of the furniture in [0] and the rest of
						// the string in [1].
						String[] splitContents = temp.split(":");
						splitContents[0] = splitContents[0].trim();

						// A second temporary String[] is created to hold all the numbers.
						// Trims and cleans up spacing that may occur between numbers.
						splitContents[1] = splitContents[1].trim();
						String[] splitContents2 = splitContents[1].split(",");
						
						for (int i = 0; i < splitContents2.length; i++) {
							
							splitContents2[i] = splitContents2[i].trim();

						}

						// Creates test path to check to see if a corresponding image of a peice of
						// furniture exists.
						// Reminder: At this point, we've already checked the syntax, therefore the
						// contents of splitContents[0]
						// cannot be something that would cause in exception whilist checking existance
						// down below
						this.testPath = new File("images/" + splitContents[0] + ".png");
						
						if (!testPath.exists()) {

							this.type = splitContents[0];
							throw new FileNotFoundException();

						}

						// If passed all tests, set type to it's type.
						// Assign the floats and number of rotations to more legible variables.
						// Also parse the ints/floats.
						this.type = splitContents[0];
						float x = Float.parseFloat(splitContents2[0]);
						float y = Float.parseFloat(splitContents2[1]);
						int rot = Integer.parseInt(splitContents2[2]);

						// Starting bottom up, find the lowest possible null spot in the furniture
						// array.
						// Create a new instance of furniture.
						// Note: This will call a different, unique constructor within Furniture.
						for (int i = 0; i < this.furniture.length; i++) {

							if (this.furniture[i] == null) {

								this.furniture[i] = new Furniture(this.type, this.processing, x, y, rot);
								break;

							}

						}

						// Catches all exceptions because of syntax. Can be thrown implicitly through
						// the splitting of the temp string
						// or explicitly through use of the checkSyntax helper method.
					} catch (ArrayIndexOutOfBoundsException e) {

						System.out.println("WARNING: Found incorrectly formatted line in file: " + this.line);
						continue;

						// This catch clause is used for cases where the type in the beginning of a line
						// is illegal.
					} catch (FileNotFoundException e) {

						System.out.println(
								"WARNING: Could not find an image for a furniture object of type: " + this.type);
						continue;
					}

				} else {

					// More than 6 lines.
					System.out.println("WARNING: Unable to load more furniture.");
					break;

				}

			}

			// When the RoomData.ddd(.txt) file cannot be found.
		} catch (FileNotFoundException e) {

			System.out.println("WARNING: Could not load room contents from file RoomData.ddd.");

			// Closes scanner if needed.
		} finally {

			if (sc != null) {
				sc.close();

			}

		}
	}

	/**
	 * <p>
	 * Helper method to help check the syntax.
	 * @param line
	 * @return
	 */
	private boolean checkSyntax(String line) {
		
		boolean returnVal = false;

		// Counts all the commas (yeah), counts all the commas (yeah), counts all the commas yeaah
		int commaCount = 0;
		for (int i = 0; i < line.length(); i++) {

			if (line.charAt(i) == ',') {

				commaCount++;
			}

		}

		// Checks to see if given string meets all requirements as stated within the
		// program assignment.
		if (!Character.isLetter(line.charAt(0))
				|| !(line.indexOf("bed") < line.indexOf(":") && line.indexOf("sofa") < line.indexOf(":"))
				|| !(commaCount == 2) || !Character.isDigit(line.charAt(line.length() - 1))) {

			returnVal = true;

		}

		// Returns t/f
		// t: Faulty syntax
		// f: Good syntax
		return returnVal;

	}

}
