import cz.uhk.fim.pro2.shopping.model.Child;
import cz.uhk.fim.pro2.shopping.model.GenderType;
import cz.uhk.fim.pro2.shopping.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Testovaci trida pro nakupni kosik
 */
public class ShoppingCartTest {

    // reference na testovanou tridu
    private ShoppingCart cart;

    /**
     * Metoda se provadi vzdy pred kazdym testem
     */
    @BeforeEach
    public void setup() {
        // nova inicializace instance (vzdy stejny stav pred kazdym testem)
        this.cart = new ShoppingCart();
    }

    @Test
    @DisplayName("Test na ziskani poctu deti v listu")
    public void test_getChildCount() {
        // test pro prazdny list
        Assertions.assertEquals(0, cart.getChildCount());

        // test pro 2 deti v listu
        List<Child> childList2Childs = new ArrayList<>();
        childList2Childs.add(new Child());
        childList2Childs.add(new Child());
        cart.setChildList(childList2Childs);
        Assertions.assertEquals(2, cart.getChildCount(), "ocekavam 2 deti");
    }

    @Test
    @DisplayName("Test pro overeni pridani deti do listu")
    public void test_addChild() {
        this.cart.addChild(new Child());
        Assertions.assertEquals(1, this.cart.getChildCount());
    }

    @Test
    @DisplayName("Test pro smazani ditete z listu s konkretnim indexem")
    public void test_removeChildOnIndex() {
        Child child1 = new Child("4573829122", "Karel", 98799.90, 45.4, new Date(), GenderType.MALE, true, 0xd1a3a4, null);
        Child child2 = new Child("6667778881", "Shanice", 10000.0, 89.2, new Date(), GenderType.FEMALE, false, 0x8d5524, null);
        List<Child> childList = new ArrayList<>();
        childList.add(child1);
        childList.add(child2);
        this.cart.setChildList(childList);

        this.cart.removeChild(0);

        Assertions.assertNotEquals(child1, this.cart.getChildList().get(0));
    }

    @Test
    @DisplayName("Test pro smazani ditete z listu pomoci reference")
    public void test_removeChildByObject() {
        Child child1 = new Child("4573829122", "Karel", 98799.90, 45.4, new Date(), GenderType.MALE, true, 0xd1a3a4, null);
        Child child2 = new Child("6667778881", "Shanice", 10000.0, 89.2, new Date(), GenderType.FEMALE, false, 0x8d5524, null);
        List<Child> childList = new ArrayList<>();
        childList.add(child1);
        childList.add(child2);
        this.cart.setChildList(childList);

        this.cart.removeChild(child1);

        Assertions.assertNotEquals(child1, this.cart.getChildList().get(0));
    }

    @Test
    @DisplayName("Test pro vycisteni celeho kosiku")
    public void test_clearCart() {
        List<Child> childList = new ArrayList<>();
        childList.add(new Child());
        childList.add(new Child());
        childList.add(new Child());
        childList.add(new Child());

        this.cart.setChildList(childList);
        this.cart.clearCart();

        Assertions.assertEquals(0, this.cart.getChildCount());
    }

    @Test
    @DisplayName("Test pro vypocet mezisouctu ceny kosiku")
    public void test_calculateSubtotal() {
        List<Child> childList = new ArrayList<>();
        Child child1 = new Child();
        child1.setPrice(1000.0);
        Child child2 = new Child();
        child2.setPrice(2000.0);
        childList.add(child1);
        childList.add(child2);

        this.cart.setChildList(childList);

        Assertions.assertEquals(3000.0, this.cart.calculateSubtotal());
    }

    @Test
    @DisplayName("Test pro vypocet celkove ceny kosiku vcetne DPH")
    public void test_calculateTotal() {
        List<Child> childList = new ArrayList<>();
        Child child1 = new Child();
        child1.setPrice(1000.0);
        Child child2 = new Child();
        child2.setPrice(2000.0);
        childList.add(child1);
        childList.add(child2);

        this.cart.setChildList(childList);
        this.cart.setVat(0.21);

        Assertions.assertEquals(3630.0, this.cart.calculateTotal());
    }
}
