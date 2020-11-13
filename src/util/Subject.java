package util;

/*
 * Subject interface
 */

public interface Subject {
	void ajouter(Observer o);

	void retirer(Observer o);

	void notifier();
}
