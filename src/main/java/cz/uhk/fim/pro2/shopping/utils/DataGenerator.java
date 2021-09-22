package cz.uhk.fim.pro2.shopping.utils;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Utils trida, ktera bude slouzit pro generovani dat
 */
public class DataGenerator {
    private ObservableList<Child> offerList;

    /**
     * Metoda pro generovani nahodneho data narozeni
     *
     * @return nahodny datum narozeni mezi 0 a 15 lety
     */
    public static Date randomBirthdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Random r = new Random();
        int day = r.nextInt(31) + 1;
        int month = r.nextInt(12) + 1;
        int age = r.nextInt(15) + 1;
        Date date = null;
        try {
            date = sdf.parse(
                    String.format("%s.%s.%s",
                            day < 10 ? "0" + day : String.valueOf(day),
                            month < 10 ? "0" + month : String.valueOf(month),
                            (Year.now().getValue() - age)
                    )
            );
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return date;
    }

    /**
     * Metoda pro vygenerovani ditete
     *
     * @return dite
     */
    public Child generateChild() throws IOException {
        String[] boyNames = {"Charles", "John", "Noah", "Adolf", "Paul", "Mark", "Brad", "Phil", "Jack", "Dario", "Joey", "Leonard", "Sheldon", "Howard", "Rajesh"};
        String[] girlNames = {"Emma", "Kate", "Shanique", "Eva", "Jennifer", "Lara", "Jane", "Natalie", "Zora", "Monica", "Phoebe", "Samantha", "Janice", "Francesca", "Nora"};
        String[] lastNames = {"Johnson", "Parkinson", "Bing", "Anniston", "Reed", "Fibracio", "Fox", "Cox", "Shearer", "Cooper", "Tribbiani", "Geller", "Gonzalez", "Armstrong", "Griffith"};
        String name;
        Random random = new Random();

        int nameIndex = random.nextInt(15);
        int lastNameIndex = random.nextInt(15);
        int gender = random.nextInt(2);

        Date birthDate = randomBirthdate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        int age = Year.now().getValue() - calendar.get(Calendar.YEAR);

        int minRange = 0, maxRange = 0;

        if (age < 3) {              // nastavení rozmezí váhy pro následné generování
            minRange = 2;
            maxRange = 5;
        }
        if (age < 5 & age >= 3) {
            minRange = 5;
            maxRange = 15;
        }
        if (age < 7 & age >= 5) {
            minRange = 15;
            maxRange = 25;
        }
        if (age < 10 & age >= 7) {
            minRange = 20;
            maxRange = 30;
        }
        if (age < 12 & age >= 10) {
            minRange = 25;
            maxRange = 40;
        }
        if (age <= 15 & age >= 12) {
            minRange = 35;
            maxRange = 50;
        }

        double weight = Math.random() * (maxRange - minRange + 1) + minRange;


        if (gender == 1) {
            name = boyNames[nameIndex] + " " + lastNames[lastNameIndex];
            int avatarNumber = random.nextInt(23) + 1;
            String avatarName = "avatar/boy-" + avatarNumber + ".png";
            Image avatar = FileUtils.loadImage(avatarName);
            return new Child(
                    String.valueOf(name.hashCode()),
                    name,
                    Math.random() * age * 1000,
                    weight,
                    birthDate,
                    GenderType.MALE,
                    random.nextBoolean(),
                    0x88aef9,
                    avatar
            );
        } else {
            name = girlNames[nameIndex] + " " + lastNames[lastNameIndex];
            int avatarNumber = random.nextInt(27) + 1;
            String avatarName = "avatar/girl-" + avatarNumber + ".png";
            Image avatar = FileUtils.loadImage(avatarName);
            return new Child(
                    String.valueOf(name.hashCode()),
                    name,
                    Math.random() * age * 900,
                    weight,
                    birthDate,
                    GenderType.FEMALE,
                    random.nextBoolean(),
                    0x88aef9,
                    avatar
            );

        }
    }

    /**
     * Metoda pro vygenerovani seznamu nabidek
     *
     * @param n pocet generovanych nabidek
     * @return seznam nabidek
     */
    public ObservableList<Child> generateOffers(int n) throws IOException {
        ObservableList<Child> offerList = FXCollections.observableArrayList();
        for (int i = 0; i < n; i++) {
            offerList.add(generateChild());
        }
        return offerList;
    }
}
