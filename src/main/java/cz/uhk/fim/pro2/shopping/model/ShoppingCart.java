package cz.uhk.fim.pro2.shopping.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelova trida predstavujici nakupni kosik
 * - obsahuje:
 *      - referenci na seznam deti (artiklu) vlozenych v kosiku
 *      - metody pro praci se stavem a obsahem kosiku
 *
 */
public class ShoppingCart {
    private int cartId; // id kosiku
    private List<Child> childList; // seznam vlozenych deti (artiklu)
    private double vat; // DPH
    private double subtotal; // mezisoucet (bez zapocteneho DPH)
    private double total; // celkovy soucet

    public ShoppingCart() {
        this.childList = new ArrayList<>();
    }

    public ShoppingCart(int cartId, List<Child> childList, double vat, double subtotal, double total) {
        this.cartId = cartId;
        this.childList = childList;
        this.vat = vat;
        this.subtotal = subtotal;
        this.total = total;
    }

    /**
     * Metoda pro pridani vybraneho ditete do kosiku
     * @param child konkretni dite
     */
    public void addChild(Child child) {
        this.childList.add(child);
    }

    /**
     * Metoda pro odebrani vybraneho ditete z kosiku
     * @param child konkretni dite
     */
    public void removeChild(Child child) {
        this.childList.remove(child);
    }

    /**
     * Metoda pro odebrani vybraneho ditete z kosiku
     * @param index dite na konkretnim indexu
     */
    public void removeChild(int index) {
        this.childList.remove(index);
    }

    /**
     * Metoda pro vymazani celeho kosiku
     */
    public void clearCart() {
        this.childList.clear();
    }

    /**
     * Metoda pro vraceni aktualniho poctu deti v kosiku
     * @return pocet deti v kosiku
     */
    public int getChildCount() {
        return childList.size();
    }

    /**
     * Metoda pro vypocet celkove hodnoty kosiku vcetne DPH
     * @return celkova cena kosiku vcetne DPH
     */
    public double calculateTotal() {
        return this.calculateSubtotal() * (1 + vat);
    }

    /**
     * Metoda pro vypocet mezisouctu hodnoty kosiku (pouze cena deti bez DPH)
     * @return mezisoucet kosiku
     */
    public double calculateSubtotal() {
        double sum = 0.0;
        for (Child child : this.childList) {
            sum += child.getPrice();
        }
        return sum;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartId=" + cartId +
                ", childList=" + childList +
                ", vat=" + vat +
                ", subtotal=" + subtotal +
                ", total=" + total +
                '}';
    }
}
