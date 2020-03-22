package com.projectfinalapi.function;

import org.springframework.stereotype.Component;

@Component
public class OtherFunc {
    public String homeAwayToAwayHome(String homeAway) {
        String[] partsTeam = homeAway.split(" - ");
        String home = partsTeam[0];
        String away = partsTeam[1];
        return away + " - " + home;
    }
}
