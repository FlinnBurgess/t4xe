package fvs.taxe.replay;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import gameLogic.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReplayStage extends Stage {

    private final List<ClickEvent> clickEvents = new ArrayList<ClickEvent>();
    private boolean replaying = false;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!replaying)
            clickEvents.add(new ClickEvent(screenX, screenY, pointer, button, System.currentTimeMillis()));

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    public void replay() {
        Game.getInstance().resetGameState();
        replaying = true;
        // Don't replay the final replay click as we could end up in a loop.
        for (ClickEvent c : clickEvents.subList(0, clickEvents.size() - 1)) {
            // We can get away with reusing the same ClickEvent as we can assume
            // that a click up and down occur at the same location.
            touchDown(c.screenX, c.screenY, c.pointer, c.button);
            touchUp(c.screenX, c.screenY, c.pointer, c.button);
            System.out.println(c);
        }
        replaying = false;
    }

    public void saveReplay() {
        Replay rep = new Replay(Game.seed, clickEvents);
        Json json = new Json();
        // TODO: Consider a better place to put this.
        String filename = new SimpleDateFormat("HH:mm:ss.SSS yyyy-MM-dd").format(new Date()) + ".rep";
        File file = new File(filename);

        try {
            FileWriter writer = new FileWriter(file, true);
            PrintWriter output = new PrintWriter(writer);
            output.print(json.prettyPrint(rep));
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadReplay(String filepath) {
        Json json = new Json();
        Path path = Paths.get(filepath);

        try {
            String text = new String(Files.readAllBytes(path));
            Replay rep = json.fromJson(Replay.class, text);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
