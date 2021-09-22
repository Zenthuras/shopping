package cz.uhk.fim.pro2.shopping.utils;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import cz.uhk.fim.pro2.shopping.utils.parser.XPathParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XPathGenerator {

    public static ObservableList<Child> generate() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, ParseException {
        NodeList personalIds = XPathParser.execute("//children/child/personalId");
        NodeList displayNames = XPathParser.execute("//children/child/displayName");
        NodeList prices = XPathParser.execute("//children/child/price");
        NodeList weights = XPathParser.execute("//children/child/weight");
        NodeList birthDates = XPathParser.execute("//children/child/birthDate");
        NodeList genders = XPathParser.execute("//children/child/gender");
        NodeList races = XPathParser.execute("//children/child/race");
        NodeList skinTones = XPathParser.execute("//children/child/skinTone");

        ObservableList<Child> children = FXCollections.observableArrayList();

        for (int i = 0; i < personalIds.getLength(); i++) {
            String gender = genders.item(i).getTextContent();
            GenderType genderType = null;
            if (gender.equals("MALE")) genderType = GenderType.MALE;
            if (gender.equals("FEMALE")) genderType = GenderType.FEMALE;

            String date = birthDates.item(i).getTextContent();
            String[] dateX = date.split("T");
            date = dateX[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(date);

            Child child = new Child(personalIds.item(i).getTextContent(), displayNames.item(i).getTextContent(), Double.parseDouble(prices.item(i).getTextContent()), Double.parseDouble(weights.item(i).getTextContent()), birthDate, genderType, Boolean.parseBoolean(races.item(i).getTextContent()), Integer.parseInt(skinTones.item(i).getTextContent()), null);
            children.add(child);
        }
        return children;
    }
}
