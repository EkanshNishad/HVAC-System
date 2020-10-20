
public interface Heater {

	boolean turnHeaterOn();
	boolean turnHeaterOff();
	void increaseHeaterTemp(double temp);
	void decreaseHeaterTemp(double temp);
	void setHeaterTemp(double temp);
}
