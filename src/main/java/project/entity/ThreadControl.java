package project.entity;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ThreadControl {

    private boolean real;
    private boolean history;

    public boolean isReal() {
        return real;
    }

    public void setReal(boolean real) {
        this.real = real;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }


    @PostConstruct
    private void fill(){
        setHistory(false);
        setReal(false);
    }
}
