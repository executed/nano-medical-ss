package service;

import configuration.ClientConfiguration;
import dao.ClientDao;
import dto.SignupDTO;
import entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.UUID;

import static dao.DaoCache.getCache;
import static utility.ClassNameUtil.getClassName;

public class ClientDBService {

    private static ClientDao clientDB = (ClientDao) getCache().
            getDao(ClientDao.class.getName());
    private static final Logger LOG = LogManager.getLogger(getClassName());

    public ClientConfiguration getClientConfigByEmail(String email){
        return clientDB.getClientConfigByEmail(email);
    }

    public ClientConfiguration getClientConfigByUsername(String username){
        return clientDB.getClientConfigByUsername(username);
    }

    public UUID saveSignupActionData(SignupDTO dto){
        UUID savedClientId = null;

        Client client = new Client.ClientBuilder().setFirstName(dto.getFirstName())
                                                  .setLastName(dto.getLastName())
                                                  .build();
        try {
            UUID clientId = clientDB.save(client);
            if (clientId == null) throw new SQLException("Client saving to database returned null id value");
            ClientConfiguration clientConfig = new ClientConfiguration(clientId,
                                                                       dto.getUsername(),
                                                                       dto.getEmail(),
                                                                       dto.getPassword(),
                                                                 false);
            if (clientDB.saveClientConfig(clientConfig) == null){
                clientDB.deleteById(clientId);
                throw new SQLException("ClientConfiguration saving to database returned null id value");
            }
            else savedClientId = clientId;
            LOG.trace("SignupDTO {} saved successful", dto);
        } catch (SQLException e) {
            LOG.info("Something went wrong while saving DTO {} to database", dto, e);
        }
        return savedClientId;
    }

    /**
     * Gets Client with set ClientConfiguration from database
     * @param id Needed client identificator
     * @return needed Client from database (null if so doesn't exist)
     */
    public Client getClientById(UUID id){
        return new Client.ClientBuilder(clientDB.getById(id))
                                                .setConfig(clientDB.getClientConfigById(id))
                                                .build();
    }
}
