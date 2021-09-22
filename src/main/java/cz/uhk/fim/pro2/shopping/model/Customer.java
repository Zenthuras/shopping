package cz.uhk.fim.pro2.shopping.model;

/**
 * Modelova trida predstavujici Zakaznika
 */
public class Customer {
    private int customerId;
    private ShoppingCart cart;
    private String username;
    private String password;
    private String email;
    private Address address;
    private double budget;

    public Customer() {
    }

    public Customer(int customerId, ShoppingCart cart, String username, String password, String email, Address address, double budget) {
        this.customerId = customerId;
        this.cart = cart;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.budget = budget;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", cart=" + cart +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", budget=" + budget +
                '}';
    }
}
