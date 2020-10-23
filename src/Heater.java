
public interface Heater {

	void turnHeaterOn();
	void turnHeaterOff();
	void increaseHeaterTemp(double temp);
	void decreaseHeaterTemp(double temp);
	void setHeaterTemp(double temp);
}
