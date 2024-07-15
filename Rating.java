import java.util.ArrayList;
import java.util.List;

public class Rating {
    private List<Observer> observers = new ArrayList<>();
    private int rating;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setRating(int rating) {
        this.rating = rating;
        notifyObservers();
    }

    public int getRating() {
        return rating;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(rating);
        }
    }
}
