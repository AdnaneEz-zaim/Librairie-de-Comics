package fr.tse.prinfo3.control;


import fr.tse.prinfo3.model.Model;
import fr.tse.prinfo3.view.Interface;


public class Controller {
	private Model model ;
    private Interface view = null ;
    
    public Controller(Model m) {
        model = m;
    }
    

    public void setView(Interface view) {
        this.view = view ;
    }
}
