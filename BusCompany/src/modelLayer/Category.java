/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelLayer;

/**
 *
 * @author viva
 */
public class Category {
    private int id;
    private double discount;
    private String name;

    public Category(int id, double discount, String name) {
        this.id = id;
        this.discount = discount;
        this.name = name;
    }

    public Category(double discount, String name) {
        this.discount = discount;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", discount=" + discount + ", name=" + name + '}';
    }
    
}
