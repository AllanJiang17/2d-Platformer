package Persistence;

import GameLogic.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: creates reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads reservation from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Player> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return playerStatus(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s));
        }

        return content.toString();
    }

    // EFFECTS: read past player status from JSON object and returns it
    private List<Player> playerStatus(JSONObject jsonObject) {
        List<Player> players = new ArrayList<>();
        JSONArray playerArray = jsonObject.getJSONArray("players status");
        for (Object json : playerArray) {
            JSONObject player = (JSONObject) json;
            addPlayer(players, player);
        }
        return players;
    }

    private void addPlayer(List<Player> players, JSONObject player) {
        int xLoc = player.getInt("xLoc");
        int yLoc = player.getInt("yLoc");
        double xVel = player.getDouble("xVel");
        double yVel = player.getDouble("yVel");
        int hp = player.getInt("hp");
        int ammo = player.getInt("ammo");
        Player oldPlayer = new Player(xLoc,yLoc,20,40);
        oldPlayer.setxVel(xVel);
        oldPlayer.setyVel(yVel);
        oldPlayer.setHP(hp);
        oldPlayer.setAmmo(ammo);
        players.add(oldPlayer);
    }

}
