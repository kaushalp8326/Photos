package view.controller;

import view.statemachine.StateMachine;

/**
 * Abstract wrapper class for all controllers. Ensures that all controllers inherit the same state machine instance.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public abstract class PhotosController {
	
	protected static StateMachine stateMachine = StateMachine.getInstance();

}
