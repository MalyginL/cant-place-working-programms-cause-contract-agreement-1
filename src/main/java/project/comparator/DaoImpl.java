package project.comparator;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.SpringBootMain;
import project.entity.WebRepository;

import java.sql.*;

@Component
public class DaoImpl {

    @Autowired
    WebRepository webRepository;

    private Connection connection;
    private Connection connectionCheck;
    private Statement statement;
    private Statement statementCheck;
    private ResultSet resultSetCheck;

    final static Logger logger = Logger.getLogger(SpringBootMain.class);

    public void setConnection() {
        try {
            connection = DriverManager.getConnection(
                    webRepository.getDataBaseAddress(),
                    webRepository.getDataBaseUserName(),
                    webRepository.getDataBasePassword());
            connectionCheck = DriverManager.getConnection(
                    webRepository.getDataBaseAddress(),
                    webRepository.getDataBaseUserName(),
                    webRepository.getDataBasePassword());
            statement = connection.createStatement();
            statementCheck = connectionCheck.createStatement();


        }
        catch (SQLException ex){
            logger.warn("enable connection error \n" + ex);


        }
    }

    private synchronized Boolean getRequestDuplicate (int channel,int time)
    //true - есть строка, false - нет строки
    {

        try {
            resultSetCheck= statementCheck.executeQuery("SELECT `date` FROM `"+ webRepository.getDbName()+"` WHERE  `date`='"+time+"' AND `channel`='"+channel+"'");
            return resultSetCheck.first();
        }
        catch (SQLException ex) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.warn("Thread.sleep error \n" + e);
                e.printStackTrace();
            }
            logger.warn("RequestDuplicate  error \n" + ex);
            logger.warn("RESTORING CONNECTION");
            setConnection();
            getRequestDuplicate(channel,time);
            return null;
        }
    }

    public synchronized void sendData(int channel, int time,double phase){

        if (!this.getRequestDuplicate(channel,time))
        {
            try {
                statement.execute("INSERT INTO `"+webRepository.getDbName() +"` (`channel`,`date`,`phase`) VALUE ('"+channel+"','" + time + "','" + phase + "')");
            } catch (SQLException e) {
                logger.warn("sendData error \n" + e);
            }
        }
    }
}
