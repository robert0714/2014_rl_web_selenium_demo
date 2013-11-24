package func.rl.common;

public enum PagePartialURL {
    householdMaintain("/rl00001/householdMaintain.xhtml");
    
    PagePartialURL(final String url){
	this.url=url;
    }
    private String url;
    public String toString(){
	return url;
    }
}
