package cz.uhk.fim.pro2.shopping.utils.parser;

import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.Marketplace;
import cz.uhk.fim.pro2.shopping.utils.Children;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class JAXBParser {
    private Marketplace marketplace;

    public static void marshal(ObservableList<Child> observableList) throws JAXBException {
        Children children = new Children();

        children.setChildren(new ArrayList<Child>(observableList));

        JAXBContext jaxbContext = JAXBContext.newInstance(Children.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(children, new File("children.xml"));
    }
}
