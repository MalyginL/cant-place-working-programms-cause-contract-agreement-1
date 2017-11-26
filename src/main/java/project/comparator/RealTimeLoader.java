package project.comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.entity.ThreadControl;
import project.entity.WebRepository;

import javax.xml.crypto.Data;

@Component
@EnableScheduling
public class RealTimeLoader {

    @Autowired
    private WebRepository webRepository;

    @Autowired
    private ThreadControl threadControl;

    @Autowired
    private DataFile dataFile;

    private String savedDate;
    private long CurrUnixTimestamp;

    @Scheduled(fixedDelay = 60000)
    public void start() {
        if (threadControl.isReal() == true) {
            CurrUnixTimestamp = System.currentTimeMillis() / 1000L;
            if (!DataFile.convert((int) CurrUnixTimestamp).equals(savedDate)) {
                for (int i = 1; i <= webRepository.getChannels(); i++) {
                    dataFile.readAndSend(DataFile.convert((int) CurrUnixTimestamp), i);
                }
            } else {
                savedDate = DataFile.convert((int) CurrUnixTimestamp);
                for (int i = 1; i <= webRepository.getChannels(); i++) {
                    dataFile.readAndSend(DataFile.convert((int) CurrUnixTimestamp), i);
                }
            }
            if (!threadControl.isHistory()) webRepository.setParsingDate((int)CurrUnixTimestamp);


        }


    }

}
