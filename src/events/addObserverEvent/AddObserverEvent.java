package events.addObserverEvent;

public class AddObserverEvent extends java.util.EventObject{

    private String class_name;

    public AddObserverEvent(Object source, String class_name){
        super(source);
        this.class_name = class_name;
    }

    public String getClass_name(){
        return this.class_name;
    }

}
