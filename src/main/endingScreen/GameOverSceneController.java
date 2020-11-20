package main.endingScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;
import main.farm.FarmState;
import main.gameManager.GameManager;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GameOverSceneController {
    private Stage primaryStage;
    private AudioClip backgroundMusic;

    public void construct(Stage primaryStage, AudioClip backgroundMusic) {
        this.primaryStage = primaryStage;
        this.backgroundMusic = backgroundMusic;
    }

    @FXML
    public void handleRestartButton() throws IOException {
        //restartApplication();
        restart();
    }

    public void restart() {
        GameManager.getInstance().clear();
        FarmState.clearFarmStateDangerous();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../configurationScreen/configScene.fxml"
                )
        );
        try {
            Parent parent = loader.load();
            ConfigSceneController controller = loader.getController();

            controller.construct(primaryStage, backgroundMusic);
            primaryStage.setTitle("Welcome!");
            primaryStage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        primaryStage.close();
    }

    /**
     * Sun property pointing the main class and its arguments.
     * Might not be defined on non Hotspot VM implementations.
     */
    public static final String SUN_JAVA_COMMAND = "sun.java.command";

    /**
     * Restart the current Java application
     * @throws IOException jnk
     */
    public void restartApplication(/*Runnable runBeforeRestart*/) throws IOException {
        System.out.println("...");
        try {
            // java binary
            String java = System.getProperty("java.home") + "/bin/java";
            // vm arguments
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            for (String arg : vmArguments) {
                // if it's the agent argument : we ignore it otherwise the
                // address of the old application and the new one will be in conflict
                if (!arg.contains("-agentlib") && !arg.contains("-javaagent")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
            // init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer(java + " " + vmArgsOneLine);

            // program main and program arguments
            String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");

            // program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
                // if it's a jar, add -jar mainJar
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
                // else it's a .class, add the classpath and mainClass
                cmd.append("-cp " + System.getProperty("java.class.path") + " " + mainCommand[0]);
                //System.out.println(cmd);
            }
            // finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }
            // execute the command in a shutdown hook, to be sure that all the
            // resources have been disposed before restarting the application
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            // execute some custom code before restarting
            //            if (runBeforeRestart!= null) {
            //                runBeforeRestart.run();
            //            }
            // exit
            System.exit(0);
        } catch (Exception e) {
            // something went wrong
            throw new IOException("Error while trying to restart the application", e);
        }
    }
}
