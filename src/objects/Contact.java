package objects;

import javafx.beans.property.SimpleStringProperty;

public class Contact {

    private final SimpleStringProperty status;
    public SimpleStringProperty id;
    public SimpleStringProperty display,full;

    public Contact(String status, String id, String display, String full) {
        this.status = new SimpleStringProperty(status);
        this.id = new SimpleStringProperty(id);
        this.display = new SimpleStringProperty(display);
        this.full = new SimpleStringProperty(full);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDisplay() {
        return display.get();
    }

    public SimpleStringProperty displayProperty() {
        return display;
    }

    public void setDisplay(String display) {
        this.display.set(display);
    }

    public String getFull() {
        return full.get();
    }

    public SimpleStringProperty fullProperty() {
        return full;
    }

    public void setFull(String full) {
        this.full.set(full);
    }
}