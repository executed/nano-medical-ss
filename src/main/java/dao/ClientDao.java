package dao;

import configuration.ClientConfiguration;
import entity.Client;
import entity.Client.ClientBuilder;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;
import static utility.SqlQueryUtil.getQuery;

public class ClientDao implements IDao{

    private DaoSpreader daoSpreader;
    private Connection connection;

    private static final Logger LOGGER = getLogger(getClassName());

    public ClientDao(DaoSpreader daoSpreader){
        this.daoSpreader = daoSpreader;
        this.connection = daoSpreader.getConnection();
    }

    public UUID save(Client client) throws SQLException{
        if (client.getId() != null){ update(client); return client.getId(); }
        LOGGER.trace("Started saving client {} in database.", client);

        PreparedStatement statement = connection.prepareStatement(getQuery("client.insert"));
        statement.setString(1, client.getFirstName());
        statement.setString(2, client.getLastName());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            LOGGER.trace("Client {} saved successfully.", client);
            return (UUID) resultSet.getObject("id");
        }
        return null;
    }

    public void update(Client client) throws SQLException{
        if (client.getId() == null){ save(client); return; }
        LOGGER.trace("Started updating client {} in database.", client);

        PreparedStatement statement = connection.prepareStatement(getQuery("client.update"));
        statement.setString(1, client.getFirstName());
        statement.setString(2, client.getLastName());
        statement.setObject(3, client.getId());
        statement.executeUpdate();
        LOGGER.trace("Client {} updated successfully.", client);
    }

    public Client getById(UUID id){
        Client client = null;
        LOGGER.trace("Started getting client by id {} from database.", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("client.select"));
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                client = new ClientBuilder().setId(id)
                                            .setFirstName(resultSet.getString("first_name"))
                                            .setLastName(resultSet.getString("last_name"))
                                            .build();
                LOGGER.trace("Client with id {} was get successfully.", id);
            }
        } catch (SQLException e) {
            LOGGER.debug("Client wiht id {} wasn't found in database", id, e);
        }
        return client;
    }

    public HashSet<Client> getAll(){
        HashSet<Client> clients = new HashSet<>();
        LOGGER.trace("Started getting all clients from database.");
        try {
            ResultSet resultSet =
                    connection.createStatement().executeQuery(getQuery("client.selectAll"));
            while(resultSet.next()){
                clients.add(new ClientBuilder().setId((UUID) resultSet.getObject("id"))
                                               .setFirstName(resultSet.getString("first_name"))
                                               .setLastName(resultSet.getString("last_name"))
                                               .build());
            }
            LOGGER.trace("All clients were get successfully");
        } catch (SQLException e) {
            LOGGER.warn("Process of getting all clients crashed.", e);
        }
        return clients;
    }

    public void deleteById(UUID id){
        LOGGER.trace("Started deleting client with id {} from database.", id);
        try {
            int status = connection.createStatement().executeUpdate(getQuery("client.delete"));
            if (status == 0) LOGGER.info("No clients were affected while deleting");
            else LOGGER.debug("Client with id {} deleted successfully", id);
        } catch (SQLException e) {
            LOGGER.debug("Process of deleting client crashed.", e);
        }
    }

    public UUID saveClientConfig(ClientConfiguration clientConfig) throws SQLException{
        LOGGER.trace("Started saving ClientConfiguration {} in database.", clientConfig);

        PreparedStatement statement = connection.prepareStatement(getQuery("clientConfig.insert"));
        statement.setObject(1, clientConfig.getId());
        statement.setString(2, clientConfig.getEmail());
        statement.setString(3, clientConfig.getPassword());
        statement.setBoolean(4, clientConfig.isAdmin());
        statement.setString(5, clientConfig.getUsername());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            LOGGER.trace("ClientConfiguration with {} saved successfully.", clientConfig);
            return (UUID) resultSet.getObject("id");
        }
        return null;
    }

    public ClientConfiguration getClientConfigById(UUID id){
        ClientConfiguration result = null;
        LOGGER.trace("Started getting ClientConfiguration instance by id {} from database.", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("clientConfig.select"));
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = new ClientConfiguration(id,
                                                 resultSet.getString("username"),
                                                 resultSet.getString("email"),
                                                 resultSet.getString("password"),
                                                 resultSet.getBoolean("admin"));
                LOGGER.trace("ClientConfiguration instance with id {} was get successfully.", id);
            }
        } catch (SQLException e) {
            LOGGER.debug("ClientConfiguration instance wiht id {} wasn't found in database", id, e);
        }
        return result;
    }

    public ClientConfiguration getClientConfigByUsername(String username){
        ClientConfiguration result = null;
        LOGGER.trace("Started getting ClientConfiguration instance by username {} from database.", username);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("clientConfig.selectByUsername"));
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = new ClientConfiguration((UUID) resultSet.getObject("id"),
                                                 username,
                                                 resultSet.getString("email"),
                                                 resultSet.getString("password"),
                                                 resultSet.getBoolean("admin"));
                LOGGER.trace("ClientConfiguration instance with username {} was get successfully.", username);
            }
        } catch (SQLException e) {
            LOGGER.debug("ClientConfiguration instance wiht username {} wasn't found in database", username, e);
        }
        return result;
    }

    public ClientConfiguration getClientConfigByEmail(String email){
        ClientConfiguration result = null;
        LOGGER.trace("Started getting ClientConfiguration instance by email {} from database.", email);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("clientConfig.selectByEmail"));
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = new ClientConfiguration((UUID) resultSet.getObject("id"),
                                                        resultSet.getString("username"),
                                                        email,
                                                        resultSet.getString("password"),
                                                        resultSet.getBoolean("admin"));
                LOGGER.trace("ClientConfiguration instance with email {} was get successfully.", email);
            }
        } catch (SQLException e) {
            LOGGER.debug("ClientConfiguration instance wiht email {} wasn't found in database", email, e);
        }
        return result;
    }

    public ArrayList<ClientConfiguration> getAllClientConfigs(){
        ArrayList<ClientConfiguration> resultList = new ArrayList<>();
        LOGGER.trace("Started getting all ClientConfiguration instances from database.");
        try {
            ResultSet resultSet =
                    connection.createStatement().executeQuery(getQuery("clientConfig.selectAll"));
            while (resultSet.next()){
                resultList.add(new ClientConfiguration((UUID) resultSet.getObject("id"),
                                                       resultSet.getString("username"),
                                                       resultSet.getString("email"),
                                                       resultSet.getString("password"),
                                                       resultSet.getBoolean("admin")));
            }
            LOGGER.trace("{} ClientConfiguration instances were get successfully", resultList.size());
        } catch (SQLException e) {
            LOGGER.warn("Process of getting all clients crashed.", e);
        }

        return resultList;
    }
}
