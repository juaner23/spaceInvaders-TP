package view;


import java.util.List;

public class RankingView {
    private final List<String> topPuntajes;

    public RankingView(List<String> top) {
        this.topPuntajes = top;
    }

    public List<String> getTopPuntajes() {
        return topPuntajes;
    }
}
