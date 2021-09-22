package cz.uhk.fim.pro2.shopping.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

/**
 * Modelova trida predstavujici Dite (prodavany/kupovany subjekt)
 */

@XmlRootElement(name = "child")
@XmlAccessorType(XmlAccessType.FIELD)
public class Child {
    private String personalId;
    private String displayName;
    private double price;
    private double weight;
    private Date birthDate;
    private int age;
    private GenderType gender;
    private boolean race;
    private int skinTone;
    // transient - znaci, ze tato promenna nema byt serializovana
    private transient Image avatar;

    public Child() {
    }

    public Child(String personalId, String displayName, double price, double weight, Date birthDate, GenderType gender, boolean race, int skinTone, Image avatar) {
        this.personalId = personalId;
        this.displayName = displayName;
        this.price = price;
        this.weight = weight;
        this.birthDate = birthDate;
        this.age = getAge();
        this.gender = gender;
        this.race = race;
        this.skinTone = skinTone;
        this.avatar = avatar;
    }

    public String getPersonalId() {
        return personalId;
    }

    public StringProperty getPersonalIdProperty() {
        return new SimpleStringProperty(personalId);
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public StringProperty getDisplayNameProperty() {
        return new SimpleStringProperty(displayName);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public StringProperty getPriceProperty() {
        return new SimpleStringProperty(String.format("%.2f", price));
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.birthDate);
        return Year.now().getValue() - calendar.get(Calendar.YEAR);
    }

    public IntegerProperty getAgeProperty() {
        return new SimpleIntegerProperty(getAge());
    }

    public GenderType getGender() {
        return gender;
    }

    public ObjectProperty<GenderType> getGenderProperty() {
        return new SimpleObjectProperty<GenderType>(gender);
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public boolean isRace() {
        return race;
    }

    public void setRace(boolean race) {
        this.race = race;
    }

    public int getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(int skinTone) {
        this.skinTone = skinTone;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Child{" +
                "personalId='" + personalId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", gender=" + gender +
                ", race=" + race +
                ", skinTone=" + skinTone +
                '}';
    }
}
