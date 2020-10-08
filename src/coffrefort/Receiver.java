package coffrefort;

public interface Receiver {
	void add(Observer o);
	void remove(Observer o);
	void notification();
}
