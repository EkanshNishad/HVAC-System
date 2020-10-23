
public interface AC {
	void turnACOn();
	void turnACOff();
	void turnCompressorOn();
	void turnCompressorOff();
	void setACTemp(double temp);
	void increaseACTemp(double temp);
	void decreaseACTemp(double temp);
	boolean turboMode(boolean turbo);
	boolean ACFan(boolean acFan);
	
}
