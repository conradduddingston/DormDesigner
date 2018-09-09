//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Dorm Designer 300
// Files:           Main.java, DormDesigner.jar
// Course:          CS300
//
// Author:          Conrad Duddingston
// Email:           duddingston@wisc.edu
// Lecturer's Name: M. Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Lecturer's Name: (name of your partner's lecturer)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This is the main method that executes the application. An instance is created
 * upon execution of the application via the main method. This instance
 * manipulates the Furniture class and the button classes.
 * 
 * @author Conrad Duddingston
 */
public class Main {

	// Fields
	private PApplet processing;
	private PImage backgroundImage;
	private Furniture[] furniture;
	private CreateBedButton button1;
	private CreateSofaButton button2;
	private LoadButton button3;
	private SaveButton button4;

	/**
	 * Main constructor that initalizes the array of furnitures and sets the
	 * background
	 * 
	 * @param processing
	 */
	public Main(PApplet processing) {

		this.processing = processing;
		processing.background(100, 150, 250);
		PImage backgroundImage = processing.loadImage("images/background.png");
		this.backgroundImage = backgroundImage;

		// Centering
		// Loading bed image
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);

		// Replace the bedImage, bedPositions, and dragBedIndex fields in Main with a
		// single array of Bed references that begins with 6 null values
		furniture = new Furniture[6];

		// Initialization of bed button to specific point
		button1 = new CreateBedButton((float)50, (float)24, processing);
		button2 = new CreateSofaButton((float)150, (float)24, processing);
		button3 = new LoadButton((float)750, (float)24, processing);
		button4 = new SaveButton((float)650, (float)24, processing);

	}

	/**
	 * <p>
	 * Update callback method that gets called throughout the application. Updates
	 * the positioning of the furnitures.
	 * Calls update() methods of other classes.
	 * @param data
	 */
	public void update() {

		// Updates background and placement of the background
		processing.background(100, 150, 250);
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);

		// Updates all of the buttons (in order to have the "clicking" darkening effect)
		button1.update();
		button2.update();
		button3.update();
		button4.update();

		// One-by-one, updates all non-null peices of information.
		for (int i = 0; i < furniture.length; i++) {

			if (furniture[i] != null) {

				furniture[i].update();

			}

		}

	}

	/**
	 * <p>
	 * If the mouse is down, prompts other mouseDown member methods.
	 * Attempts to call or calls all mosueDown methods for other objects that
	 * are not null. button1 and button2 return type furniture[] that are null should
	 * a specific peice of furniture be not created.
	 * @params none
	 */
	public void mouseDown() {

		for (int i = 0; i < furniture.length; i++) {

			if (furniture[i] != null && furniture[i].isMouseOver()) {

				furniture[i].mouseDown();
				break;

			}

		}

		// Should mouse be down, calls it on the above button instances.
		// Note: temp and temp2 return something, as they create objects.
		Furniture temp = button1.mouseDown();
		Furniture temp2 = button2.mouseDown();
		button3.mouseDown(furniture);
		button4.mouseDown(furniture);

		// If a bed
		if (temp != null && temp2 == null) {

			// Counts amount of furniture currently
			int counter = 0;
			for (int i = 0; i < furniture.length; i++) {

				if (furniture[i] != null) {

					counter++;
				}

			}
			// If the amount of furniture is not 6, meaning we still have space
			if (counter != 6) {

				for (int i = 0; i < furniture.length; i++) {

					if (furniture[i] == null) {

						furniture[i] = temp;
						break;

					}

				}

			}

			// A sofa
		} else if (temp == null && temp2 != null) {

			int counter = 0;
			for (int i = 0; i < furniture.length; i++) {

				if (furniture[i] != null) {

					counter++;

				}

			}
			// Same schema as above^^
			if (counter != 6) {

				for (int i = 0; i < furniture.length; i++) {

					if (furniture[i] == null) {

						// Temp 2 because this branch is for if temp is null and temp2 has been returned
						// (aka a sofa)
						furniture[i] = temp2;
						break;

					}

				}

			}

		}

	}

	/**
	 * <p>
	 * If mouse is up, prompts other methods.
	 * @params n/a
	 * 
	 */
	public void mouseUp() {

		for (int i = 0; i < furniture.length; i++) {

			if (furniture[i] != null) {

				furniture[i].mouseUp();

			}

		}
	}

	/**
	 * <p>
	 * Should a key be pressed, preforms actions on objects
	 * @params n/a
	 */
	public void keyPressed() {

		// When the ‘B’ key is pressed, a new Bed object should be created, and its
		// reference should replace a null reference within this Bed array. When the
		// array becomes full, no more new Bed object references will be able to be
		// added to it. Also make sure that your Main class calls each Bed object’s
		// update(), mouseDown(), and mouseUp() methods at the appropriate times.

		// Tests to see if full or not

		if (processing.key == 'd' || processing.key == 'D') {

			for (int i = 0; i < furniture.length; i++) {

				if (furniture[i] != null) {

					if (furniture[i].isMouseOver()) {

						furniture[i] = null;
						break;

					}
				}

			}

			// If r is pressed. This signals a rotation of the furniture it's currently over.
		} else if (processing.key == 'r' || processing.key == 'R') {

			// Loops through all furniture.
			for (int i = 0; i < furniture.length; i++) {

				// If the specific peice is null.
				if (furniture[i] != null) {

					// And is over...
					if (furniture[i].isMouseOver()) {

						// Calls this furnitures rotatae member method.
						// Then breaks.
						furniture[i].rotate();
						break;

					}

				}

			}

		}

	}

	/**
	 * <p>
	 * The main method that starts the application.
	 * @param args
	 */
	public static void main(String[] args) {

		// Starts the application.
		Utility.startApplication();

	}

}
