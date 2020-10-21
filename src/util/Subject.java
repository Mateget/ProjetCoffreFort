package util;

public interface Subject {
	void ajouter(Observer o);

	void retirer(Observer o);

	void notifier();
}
