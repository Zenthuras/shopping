package cz.uhk.fim.pro2.shopping.model;

import cz.uhk.fim.pro2.shopping.utils.DataGenerator;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * Modelova trida predstavujici trh se zbozim
 */
public class Marketplace {
    private ObservableList<Child> offerList;
    private DataGenerator generator;

    public Marketplace() throws IOException {

        generator = new DataGenerator();
        this.offerList = generator.generateOffers(30);
    }


    /**
     * Metoda pridat nabidku zpet do listu po odebrani z kosiku
     *
     * @param child dite
     */
    public void addOffer(Child child) {
        offerList.add(child);
    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle indexu
     *
     * @param index index nabidky
     */
    public void removeOffer(int index) {
        offerList.remove(index);
    }

    /**
     * Metoda odebere nabidku z listu po pridani do kosiku podle reference
     *
     * @param child reference na konkretni nabidku
     */
    public void removeOffer(Child child) {
        offerList.remove(child);
    }

    /**
     * Metoda pro vraceni konkretni nabidky
     *
     * @param index index v listu
     * @return nabidka/dite
     */
    public Child getOfferDetail(int index) {
        return offerList.get(index);
    }

    public ObservableList<Child> getOfferList() {
        return offerList;
    }

    public void setOfferList(ObservableList<Child> offerList) {
        this.offerList = offerList;
    }
}
