package Models;

/**
 * En klass som används för att hantera data omvandlingen från json till java objekt.
 */
public class search
{
	private String auth;
	private String type;
	private String query;

	public search(String auth, String type, String query)
	{
		this.auth = auth;
		this.type = type;
		this.query = query;
	}

	public String getAuth()
	{
		return auth;
	}

	public String getType()
	{
		return type;
	}

	public String getQuery()
	{
		return query;
	}
}
