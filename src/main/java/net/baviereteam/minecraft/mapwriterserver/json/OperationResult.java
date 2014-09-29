package net.baviereteam.minecraft.mapwriterserver.json;

public class OperationResult {
	private boolean result = false;
	private String errorMessage = "";
	private Object resultingObject = null;
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Object getResultingObject() {
		return resultingObject;
	}
	public void setResultingObject(Object resultingObject) {
		this.resultingObject = resultingObject;
	}
}
