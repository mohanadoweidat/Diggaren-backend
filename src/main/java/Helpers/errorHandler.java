package Helpers;

/**
 * Den här klassen kommer att användas för att hantera felkoder samt meddelanden när API-calls sker.
 */
public class errorHandler
{
	private int statusCode;
	private String errorMessage;

	public errorHandler(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public errorHandler(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
