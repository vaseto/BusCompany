/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrlLayer;

import dbLayer.DbCategory;
import java.sql.SQLException;
import java.util.List;
import modelLayer.Category;

/**
 *
 * @author viva
 */
public class CategoryCtrl {
    private DbCategory dbCategory;
    
    public CategoryCtrl(){
        dbCategory = new DbCategory();
    }
    
    /**
     * Creates and inserts a new category
     * @param discount
     * @param name
     * @return
     * @throws SQLException 
     */
    public int createCategory(double discount,String name) throws SQLException{
        return dbCategory.insert(new Category(discount,name));
    }
    
    /**
     * Updates a category 
     * @param category
     * @return
     * @throws SQLException 
     */
    public int updateCategory(Category category) throws SQLException{
        return dbCategory.update(category);
    }
    
    /**
     * Deletes a category
     * @param category
     * @return
     * @throws SQLException 
     */
    public int deleteCategory(Category category) throws SQLException{
        return dbCategory.delete(category);
    }
    
    /**
     * Returns a category with the given id
     * @param id
     * @return
     * @throws SQLException 
     */
    public Category getCategoryById(int id) throws SQLException{
        return dbCategory.getCategoryById(id);
    }
    
    /**
     * Returns a category with the given name
     * @param name
     * @return
     * @throws SQLException 
     */
    public Category getCategoryByName(String name) throws SQLException{
        return dbCategory.getCategoryByName(name);
    }
    
    /**
     * Returns all categories
     * @return
     * @throws SQLException 
     */
    public List<Category> getAllCategories() throws SQLException{
        return dbCategory.getAllCategories();
    }
}
