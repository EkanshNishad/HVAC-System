
public interface AC {
	boolean turnACOn();
	boolean turnACOff();
	void setACTemp(double temp);
	void increaseACTemp(double temp);
	void decreaseACTemp(double temp);
	boolean turboMode(boolean turbo);
	boolean ACFan(boolean acFan);
	
}
