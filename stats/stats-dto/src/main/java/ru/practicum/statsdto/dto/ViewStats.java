package ru.practicum.statsdto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStats implements Comparable {
    private String app;
    private String uri;
    private Integer hits;

    @Override
    public int compareTo(Object obj1) {
        ViewStats that = (ViewStats)obj1;
        Integer p1 = this.getHits();
        Integer p2 = that.getHits();

        if (p1 > p2) {
            return -1;
        } else if (p1 < p2) {
            return 1;
        } else
            return 0;
    }
}
