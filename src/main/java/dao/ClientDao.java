package dao;

import entity.Client;
import entity.Client.ClientBuilder;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;

public class ClientDao {

    private Dao dao;
    private Connection connection;

    private static final Logger LOGGER = getLogger(getClassName());

    private final String insertQuery = "INSERT INTO client (full_name) VALUES (?);";
    private final String updateQuery = "UPDATE client SET full_name = ? WHERE id = ?;";
    private final String selectQuery = "SELECT * FROM client WHERE id = ?;";
    private final String selectAllQuery = "SELECT * FROM client;";
    private final String deleteQuery = "DELETE FROM client WHERE id = ?;";

    public ClientDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }

    public void save(Client client){
        if (client.getId() != null){ update(client); return; }
        LOGGER.debug("Started saving "+client+" in database.");
        try {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, client.getFullName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Client " + client + " wasn't saved to database", e);
        }
        LOGGER.debug("Client "+client+" saved successfully.");
    }

    public void update(Client client){
        if (client.getId() == null){ save(client); return; }
        LOGGER.debug("Started updating "+client+" in database.");
        try {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, client.getFullName());
            statement.setObject(2, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Client " + client + " wasn't updated in database", e);
        }
        LOGGER.debug("Client "+client+" updated successfully.");
    }

    public Client getById(UUID id){
        Client client = null;
        LOGGER.debug("Started getting client by id "+id+" from database.");
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()){
                client = new ClientBuilder().setId(id)
                                            .setFullName(resultSet.getString("full_name"))
                                            .build();
            }
        } catch (SQLException e) {
            LOGGER.info("Client wiht id " + id + " wasn't found in database", e);
        }
        LOGGER.debug("Client with id "+id+" get successfully.");
        return client;
    }

    public HashSet<Client> getAll(){
        HashSet<Client> clients = new HashSet<>();
        LOGGER.debug("Started getting all clients from database.");
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(selectAllQuery);
            while(resultSet.next()){
                clients.add(new ClientBuilder().setId((UUID) resultSet.getObject("id"))
                                               .setFullName(resultSet.getString("full_name"))
                                               .build());
            }
        } catch (SQLException e) {
            LOGGER.info("Process of getting all clients crashed.", e);
        }
        LOGGER.debug("All clients get successfully");
        return clients;
    }

    public void deleteById(UUID id){
        LOGGER.debug("Started deleting client with id"+id+" from database.");
        try {
            int status = connection.createStatement().executeUpdate(deleteQuery);
            if (status == 0) LOGGER.debug("No clients were affected while deleting");
        } catch (SQLException e) {
            LOGGER.info("Process of deleting client crashed.", e);
        }
        LOGGER.debug("Client with id "+id+" deleted successfully");
    }
}
