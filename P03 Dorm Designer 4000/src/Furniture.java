import java.io.File;
import java.io.*;

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
 * <p>
 * This is the furniture class. It's member methods are directly accessed via
 * the main method's callback methods. It controls the properties and movements
 * (repositioning) of the beds.
 * 
 * @author Conrad Duddingston
 *
 */
public class Furniture {

	// Fields neccessary for the Bed class.
	// image: Image needed for specific furniture
	// position: x/y cords
	// isDragging: boolean variable, true is is being dragged
	// rotations: # of rotations of a specific object
	// type: string containing name of furniture
	private PApplet processing;
	private PImage image;
	private float[] position;
	private boolean isDragging;
	private int rotations;
	private String type;

	/**
	 * <p>
	 * Constructor called for individual creation from the sofa/bed buttons.
	 * @param type
	 * @param processing
	 */
	public Furniture(String type, PApplet processing) {

		// Fields used by furniture:
		this.rotations = 0;
		this.processing = processing;
		this.isDragging = false;
		this.type = type;
		this.image = processing.loadImage("images/" + this.type + ".png");
		this.position = new float[] { processing.width / 2, processing.height / 2 };
		processing.image(this.image, position[0], position[1]);

	}

	/**
	 * Constructor calls exclusively from the loadFile object.
	 * Has specific positions for x and y, rotations.
	 * @param type
	 * @param processing
	 * @param x
	 * @param y
	 * @param rotations
	 */
	public Furniture(String type, PApplet processing, float x, float y, int rotations) {

		this.rotations = rotations;
		this.processing = processing;
		this.isDragging = false;
		this.type = type;

		this.image = processing.loadImage("images/" + type + ".png");
		this.position = new float[] { x, y };
		processing.image(this.image, position[0], position[1]);

	}

	/**
	 * Updated via repeated calling from the Main callback method: update()
	 */
	public void update() {

		// draws this bed at its current position
		if (this.isDragging) {

			position[0] = processing.mouseX;
			position[1] = processing.mouseY;

		}

		// Draws image. Even if not being moved, must draw to place over the
		// ever-changing background

		processing.image(image, position[0], position[1], rotations * PApplet.PI / 2);

		// processing.image(this.image, position[0], position[1]);

	}

	/**
	 * <p>
	 * Called from main. Calls helper method isMouseOver() to check to see if mouseDown()
	 * applies to this instance of furniture.
	 * @params n/a
	 */
	public void mouseDown() {

		if (isMouseOver()) {

			this.isDragging = true;

		}

	}

	/**
	 * <p>
	 * Is called from main. If mouse is up, stops dragging this specific instance 
	 * of furniture.
	 */
	public void mouseUp() {

		this.isDragging = false;

	}

	/**
	 * <p>
	 * Helper method that detects if mouseX and mouseY
	 * of PApplet processing is on top of an instance of
	 * furniture.
	 * @return
	 */
	public boolean isMouseOver() {

		boolean isOver;

		// If the mouse is over the dimensions for the mouse
		// If even (bed is positioned horizontally like normal)
		if(this.rotations % 2 == 0) {
				
			if (processing.mouseX > position[0] - this.image.width / 2
					&& processing.mouseX < position[0] + this.image.width / 2
					&& processing.mouseY > position[1] - this.image.height / 2
					&& processing.mouseY < position[1] + this.image.height / 2) {
	
				isOver = true;

			} else {

				isOver = false;

			}

			// If positioned upwards
		} else {

			// Is mouseX/mouseY within boundaries?
			if (processing.mouseX < this.position[0] + this.image.height / 2
					&& processing.mouseX > this.position[0] - this.image.height / 2
					&& processing.mouseY < this.position[1] + this.image.width / 2
					&& processing.mouseY > this.position[1] - this.image.width / 2) {

				isOver = true;

			} else {

				isOver = false;

			}

		}

		return isOver;

	}

	/**
	 * <p>
	 * Increment rotations. Odd and even numbers of rotations affect how the mouse
	 * is able to recognize a bed
	 * @reutns n/a
	 */
	public void rotate() {

		this.rotations++;

	}

	// Accessor for type.
	public String getType() {

		return this.type;

	}

	// Accessor for X position.
	public float getX() {

		return this.position[0];

	}

	// Accessor for Y position.
	public float getY() {

		return this.position[1];

	}

	// Accessor for rotation #.
	public int getRotations() {

		return this.rotations;

	}
}
