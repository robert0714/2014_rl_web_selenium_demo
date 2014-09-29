package func.rl.common.internal;

import java.io.Serializable;

public class GrowlMsg implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -6163362747250060288L;
    
    private boolean giveUpOperation = false;
    
    private String message ;
    
    private String extMessage  ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String errorExtMessage) {
        this.extMessage = errorExtMessage;
    }

    public boolean isGiveUpOperation() {
        return giveUpOperation;
    }

    public void setGiveUpOperation(boolean giveUpOperation) {
        this.giveUpOperation = giveUpOperation;
    }

    
}
