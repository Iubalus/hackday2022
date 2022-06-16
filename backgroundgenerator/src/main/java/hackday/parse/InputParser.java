package hackday.parse;

import java.awt.*;

public class InputParser {

    public Parsed parse(String raw) {
        Parsed result = new Parsed();
        if(raw == null || raw.isEmpty()){
            return result;
        }
        String[] statements = raw.split(";");
        for (String statement : statements) {
            String[] parts = statement.split(":");
            if(parts[0].equalsIgnoreCase("height")){
                result.setHeight(Integer.parseInt(parts[1]));
            }
            if(parts[0].equalsIgnoreCase("width")){
                result.setWidth(Integer.parseInt(parts[1]));
            }
            if(parts[0].equalsIgnoreCase("background_color")){
                result.setBackgroundColor(Color.decode(parts[1]));
            }
        }
        return result;
    }

}
