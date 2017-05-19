package tm;

import dao.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by María del Rosario on 11/04/2017.
 */
public abstract class TransactionManager {
    /**
     * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
     */
    protected static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

    /**
     * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
     */
    protected String connectionDataPath;

    /**
     * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
     */
    protected String user;

    /**
     * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
     */
    protected String password;

    /**
     * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
     */
    protected String url;

    /**
     * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
     */
    protected String driver;

    /**
     * Conexión a la base de datos
     */
    protected Connection connection;

    protected TransactionManager(String contextPathP) {
        connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
        initConnectionData();
    }

    /*
    * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
    * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
    */
    protected void initConnectionData() {
        try {
            File arch = new File(this.connectionDataPath);
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(arch);
            prop.load(in);
            in.close();
            this.url = prop.getProperty("url");
            this.user = prop.getProperty("usuario");
            this.password = prop.getProperty("clave");
            this.driver = prop.getProperty("driver");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que  retorna la conexión a la base de datos
     *
     * @return Connection - la conexión a la base de datos
     * @throws java.sql.SQLException - Cualquier error que se genere durante la conexión a la base de datos
     */
    protected Connection getConnection() throws SQLException {
        System.out.println("Connecting to: " + url + " With user: " + user);
        return DriverManager.getConnection(url, user, password);
    }

    protected void closeDAO(DAO dao) throws SQLException {
        try {
            dao.cerrarResource();
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException exception) {
            System.err.println("SQLException closing resources:" + exception.getMessage());
            exception.printStackTrace();
            throw exception;
        }
    }
}

