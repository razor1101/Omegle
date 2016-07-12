package omeglegui2;

import java.util.List;

public class details {
public List<String[]> events;
public String clientID;

public details(List<String[]> events, String clientID)
{
	this.events=events;
	this.clientID=clientID;
}
public List<String[]> getEvents()
{
	return events;
}
public String getID()
{
	return clientID;
}

}
