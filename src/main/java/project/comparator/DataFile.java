package project.comparator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.SpringBootMain;
import project.entity.WebRepository;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

@Component
public class DataFile {
    final static Logger logger = Logger.getLogger(SpringBootMain.class);
    @Autowired
    private WebRepository webRepository;

    @Autowired
    DaoImpl daoImpl;

    public static String convert(int date) {
        java.util.Date time = new java.util.Date((long) date * 1000);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd");
        return (DATE_FORMAT.format(time));
    }

    public synchronized void readAndSend(String date, int channel) {
        try {
            FileChannel fileChannel = FileChannel.open
                    ((new File(webRepository.getDataPath()+File.separator + date + "_0"+ channel+".dat")).toPath(),
                            EnumSet.of(StandardOpenOption.READ),
                            new FileAttribute[0]);
            ByteBuffer time = ByteBuffer.allocate(4);
            ByteBuffer phase = ByteBuffer.allocate(8);
            while ((fileChannel.read(time)>0) & (fileChannel.read(phase)>0)) {
                time.order(ByteOrder.LITTLE_ENDIAN);
                phase.order(ByteOrder.LITTLE_ENDIAN);
            daoImpl.sendData(channel,((ByteBuffer)(time.rewind())).asIntBuffer().get(0), ((ByteBuffer)(phase.rewind())).asDoubleBuffer().get(0));
            }
        } catch (Exception ex) {

            logger.warn("File Not Found" + webRepository.getDataPath()+File.separator + date + "_0"+ channel+".dat");
        }
    }


}
