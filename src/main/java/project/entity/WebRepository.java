package project.entity;


import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;

@Repository

public class WebRepository {
    private String dataBaseAddress;
    private String dataBaseUserName;
    private String dataBasePassword;
    private Integer parsingDate;
    private String DataPath;
    private Integer Channels;
    private String dbName;

    public String getDataBaseAddress() {
        return dataBaseAddress;
    }

    public void setDataBaseAddress(String dataBaseAddress) {
        this.dataBaseAddress = dataBaseAddress;
    }

    public String getDataBaseUserName() {
        return dataBaseUserName;
    }

    public void setDataBaseUserName(String dataBaseUserName) {
        this.dataBaseUserName = dataBaseUserName;
    }

    public String getDataBasePassword() {
        return dataBasePassword;
    }

    public void setDataBasePassword(String dataBasePassword) {
        this.dataBasePassword = dataBasePassword;
    }

    public Integer getParsingDate() {
        return parsingDate;
    }

    public void setParsingDate(Integer parsingDate) {
        this.parsingDate = parsingDate;
    }

    public String getDataPath() {
        return DataPath;
    }

    public void setDataPath(String dataPath) {
        DataPath = dataPath;
    }

    public Integer getChannels() {
        return Channels;
    }

    public void setChannels(Integer channels) {
        Channels = channels;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @PostConstruct
    public void setDefault() {
        setDbName("Etalon");
        setDataBaseAddress("jdbc:mysql://192.168.101.93/ComparatorBase");
        setDataBaseUserName("comparator");
        setDataBasePassword("22222222");
        setChannels(8);
        setDataPath("C:\\Program Files\\VCH-315\\samples");
        setParsingDate(1506729600); //01-01-2017
    }

    @Override
    public String toString() {
        return "dataBaseAdress = " + getDataBaseAddress() +
                " DataBaseUserName = " + getDataBaseUserName() +
                " DataBasePassword = " + getDataBasePassword() +
                " Channels = " + getChannels() +
                " DataPath = " + getDataPath() +
                " ParsingDate = " + getParsingDate();
    }


}
