package project.comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import project.entity.ThreadControl;
import project.entity.WebRepository;

@Component
@EnableAsync
public class HistoryLoader {

    @Autowired
    WebRepository webRepository;

    @Autowired
    ThreadControl threadControl;

    @Autowired
    DataFile dataFile;

    @Async
    public void start() {

        if (threadControl.isHistory()) {

            long CurrUnixTimestamp = System.currentTimeMillis() / 1000L;

            while (webRepository.getParsingDate() < CurrUnixTimestamp) {
                for (int i = 1; i <= webRepository.getChannels(); i++) {
                    dataFile.readAndSend(DataFile.convert(webRepository.getParsingDate()), i);
                }
                webRepository.setParsingDate(webRepository.getParsingDate() + 86400);
            }
            threadControl.setHistory(false);
        }
    }
}
