package org.accounting.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.security.InvalidKeyException;
import java.util.Map;

/*
    Class for retrieving database configuration.
    Set environment variable ACCOUNTING_ENVIRONMENT to match desired configuration type in database.xml
 */

public class DbConfigReader {
    private static DbConfig dbConfig = null;

    public static DbConfig getDbConfig() {
        if (dbConfig != null) { return dbConfig; }

        Map<String, String> env = System.getenv();
        String environment = env.getOrDefault("ACCOUNTING_ENVIRONMENT", "production");

        try {
            File fXmlFile = new File(DbConfig.class.getClassLoader().getResource("database.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("environment");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (!nNode.getAttributes().getNamedItem("type").getTextContent().equals(environment)) { continue; }

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    String host = element.getElementsByTagName("host").item(0).getTextContent();
                    String database = element.getElementsByTagName("database").item(0).getTextContent();
                    int port = Integer.parseInt(element.getElementsByTagName("port").item(0).getTextContent());
                    String user = element.getElementsByTagName("user").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    return dbConfig = new DbConfig(host, database, port, user, password);
                }
            }

            throw new InvalidKeyException(String.format("No database configuration for environment: %s", environment));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static class DbConfig {
        private String host, database, user, password;
        private int port;

        public String getHost() {
            return host;
        }

        public String getDatabase() {
            return database;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public int getPort() {
            return port;
        }

        DbConfig(String host, String database, int port, String user, String password) {
            this.host = host;
            this.database = database;
            this.port = port;
            this.user = user;
            this.password = password;
        }
    }
}
