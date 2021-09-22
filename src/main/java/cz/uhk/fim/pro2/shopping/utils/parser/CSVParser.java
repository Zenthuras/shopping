package cz.uhk.fim.pro2.shopping.utils.parser;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import cz.uhk.fim.pro2.shopping.utils.Constants;
import cz.uhk.fim.pro2.shopping.utils.FileUtils;
import javafx.scene.image.Image;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility trida pro praci s CSV soubory
 */
public class CSVParser {

    /**
     * Metoda pro vygenerovani retezce v CSV formatu
     * @param objects list deti, ktere chceme zapsat do retezce
     */
    public static void generateCsv(String filename, List<Child> objects) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        StringBuilder builder = new StringBuilder();
        for (Child child : objects) {
            builder.append(String.format("%s;%s;%.2f;%.2f;%s;%d;%s;%b;%d;%s%n",
                    child.getPersonalId(),
                    child.getDisplayName(),
                    child.getPrice(),
                    child.getWeight(),
                    dateFormat.format(child.getBirthDate()),
                    child.getAge(),
                    child.getGender(),
                    child.isRace(),
                    child.getSkinTone(),
                    child.getAvatar() == null ? "-" : child.getAvatar().getUrl()
                )
            );
        }

        FileUtils.writeToFile(builder.toString().getBytes(), filename, Constants.EXTENSION_CSV);
    }

    /**
     * Metoda pro parsovani CSV souboru se seznamem deti
     * @param filename nazev a cesta k souboru
     * @return list deti
     */
    public static List<Child> parseCsv(String filename) {
        List<Child> childList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

        String data = FileUtils.readFromFile(filename, Constants.EXTENSION_CSV);
        // rozdeleni datoveho souboru na jednotlive radky
        String[] lines = data.split("\n");
        for (String line : lines) {
            // rozdeleni radku na jednotlive segmenty
            String[] attributes = line.split(";");
            try {
                childList.add(new Child(
                        attributes[0],
                        attributes[1],
                        Double.parseDouble(attributes[2].replaceAll(",", ".")), // parsovani desetinneho cisla z retezce -
                        Double.parseDouble(attributes[3].replaceAll(",", ".")), //   nahrazeni desetinne carky, za desetinnou tecku
                        sdf.parse(attributes[4]),
                        GenderType.valueOf(attributes[6]),
                        Boolean.parseBoolean(attributes[7]),
                        Integer.parseInt(attributes[8]),
                        attributes[9].equals("-") ? null : new Image(attributes[9])
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return childList;
    }
}
