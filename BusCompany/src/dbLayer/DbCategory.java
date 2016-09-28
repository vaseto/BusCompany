/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelLayer.Category;

/**
 *
 * @author Delyan
 */
public class DbCategory extends DbClass{
    private Connection con;
    private String from = "Categories";
    
    public DbCategory(){
        con = getCon();
    }
    
    /**
     * 
     * @return List of all categories
     * @throws SQLException 
     */
      public List<Category> getAllCategories() throws SQLException {
        return missWhere("");
    }
      
      /**
       * 
       * @param results the result set to be used to build a Category object
       * @return Category object build with the result set
       * @throws SQLException 
       */
    private Category buildCategory(ResultSet results) throws SQLException {
       return new Category(results.getInt(1),results.getDouble(3),results.getString(2));
    }
    
    /**
     * Finds a single instance of Category 
     * @param wClause the clause to be used for finding 
     * @return Category object
     * @throws SQLException 
     */
    private Category singleWhere(String wClause) throws SQLException {
        ResultSet results = getResultSet(wClause,from);
        results.next();
        return buildCategory(results);
    }
    
    /**
     * 
     * @param wClause the clause to be used to find all categories
     * @return list of Categories matching the wClause
     * @throws SQLException 
     */
    private List<Category> missWhere(String wClause) throws SQLException {
        List<Category> list = new ArrayList<>();
        ResultSet results = getResultSet(wClause,from);

        while(results.next())
            list.add(buildCategory(results));  

        return list;
    }

    /**
     * Creates prepare statement for inserting category object
     * @return the newly created prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForInsert() throws SQLException {
        return con.prepareStatement("Insert into Categories (category,discount) values (?,?) Select Scope_Identity()");
    }

    /**
     * Creates prepare statement for updating categories
     * @return the newly created prepare statement
     * @throws SQLException 
     */
    private PreparedStatement getPrepStatForUpdate() throws SQLException {
        return con.prepareStatement("Update Categories set category = ?, discount = ? where id = ? ");
    }

    /**
     * Returns a category object matching the given id
     * @param id
     * @return 
     * @throws SQLException 
     */
    public Category getCategoryById(int id) throws SQLException {
        return singleWhere("id = " + id );
    }

    /**
     *  Returns a category with the name
     * @param city the name to be searched for
     * @return 
     * @throws SQLException 
     */
    public Category getCategoryByName(String city) throws SQLException {
        return singleWhere("category ='" + city + "'");
    }

    /**
     * Inserts a category object into the database
     * @param category to be insert
     * @return 1 if successful
     * @throws SQLException 
     */
    public int insert(Category category) throws SQLException {
        PreparedStatement prSt = getPrepStatForInsert();
        prSt.setString(1, category.getName());
        prSt.setDouble(2, category.getDiscount());
        return prSt.executeUpdate();
    }
    
     /**
      * Removes a category object with the given id
      * @param category to be deleted
      * @return 1 if successful
      * @throws SQLException 
      */
     public int delete(Category category) throws SQLException {
        return con.createStatement().executeUpdate("Delete from Categories where id= '" + category.getId() + "'");
    }

     /**
      * Updates a category object into database
      * @param category to be updated 
      * @return 1 if successful
      * @throws SQLException 
      */
    public int update(Category category) throws SQLException {
        PreparedStatement prSt = getPrepStatForUpdate();
         prSt.setString(1, category.getName());
        prSt.setDouble(2, category.getDiscount());
        prSt.setInt(3, category.getId());
        return prSt.executeUpdate();
    }
    
}