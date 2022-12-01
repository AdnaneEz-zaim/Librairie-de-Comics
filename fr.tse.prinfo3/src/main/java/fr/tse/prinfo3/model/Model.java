package fr.tse.prinfo3.model;


import java.beans.PropertyChangeSupport;  // Java8+
import java.beans.PropertyChangeListener; // Java8+

public class Model {
	 private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);     // Java8+
	 public void addPropertyChangeListener(PropertyChangeListener listener) {       // Java8+
	         this.pcs.addPropertyChangeListener(listener);
	     }
	 
	 
}
