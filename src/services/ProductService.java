package services;

import entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;

/**
 *
 * @author Adam baba
 */
public class ProductService implements IService<Product> {

    Connection cnx;

    public ProductService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Product t) throws SQLException {
        String req = "INSERT INTO product(price, quantite, likes, panier_id, name, image, datefabrication) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getPrice());
        ps.setInt(2, t.getQuantite());
        ps.setInt(3, t.getLikes());
        ps.setInt(4, t.getPanier_id());
        ps.setString(5, t.getName());
        ps.setString(6, t.getImage());
        ps.setDate(7, t.getDatefabrication());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Product t) throws SQLException {
        String req = "UPDATE product SET price = ?, quantite = ?, likes = ?, panier_id = ?, name = ?, image = ?, datefabrication = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getPrice());
        ps.setInt(2, t.getQuantite());
        ps.setInt(3, t.getLikes());
        ps.setInt(4, t.getPanier_id());
        ps.setString(5, t.getName());
        ps.setString(6, t.getImage());
        ps.setDate(7, t.getDatefabrication());
        ps.setInt(8, t.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Product t) throws SQLException {
        String req = "DELETE FROM product WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Product> recuperer() throws SQLException {
        List<Product> products = new ArrayList<>();
        String req = "SELECT * FROM product";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("id"),
                    rs.getInt("price"),
                    rs.getInt("quantite"),
                    rs.getInt("likes"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getDate("datefabrication")
            );
            products.add(product);
        }
        return products;
    }
}