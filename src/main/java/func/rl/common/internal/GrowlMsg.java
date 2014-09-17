package func.rl.common.internal;

import java.io.Serializable;

public class GrowlMsg implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -6163362747250060288L;
    
    private boolean giveUpOperation = false;
    
    private String errorMessage ;
    
    private String errorExtMessage  ;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorExtMessage() {
        return errorExtMessage;
    }

    public void setErrorExtMessage(String errorExtMessage) {
        this.errorExtMessage = errorExtMessage;
    }

    public boolean isGiveUpOperation() {
        return giveUpOperation;
    }

    public void setGiveUpOperation(boolean giveUpOperation) {
        this.giveUpOperation = giveUpOperation;
    }

    
}
