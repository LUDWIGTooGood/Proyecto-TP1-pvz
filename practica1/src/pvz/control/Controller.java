package pvz.control;

import pvz.logic.Game;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.Sunflower;
import pvz.utils.Position;
import pvz.view.GamePrinter;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Input/output coordinator of the game (the C in MVC).
 *
 * <p>Owns the game loop: reads a line from stdin, parses it into a
 * command, validates parameters (plant type, position), delegates
 * state changes to {@link Game}, and triggers a board reprint via
 * {@link GameView} when the cycle advances. It holds no game
 * state of its own; the source of truth is always {@link Game}.
 */
public class Controller {

    private final Game game;
    private final GameView view;

    /**
     * Crea el controlador asociado a una partida.
     *
     * @param game partida que será controlada
     */
    public Controller(Game game) {
        this.game = game;
        this.view = new GamePrinter(game);
    }

    /**
     * Ejecuta el bucle principal de la partida.
     * Lee los comandos del usuario y realiza las acciones correspondientes
     * hasta que la partida termina.
     */
    public void run() {

        this.view.showGame();

        while (!this.game.hasGameFinished()) {

            String[] words = this.view.getPrompt();

            boolean advanceCycle = false;
            boolean redraw = false;

            String command = words[0];

            if (command.isEmpty()
                    || command.equals("none")
                    || command.equals("n")) {

                advanceCycle = true;
            }

            else if (command.equals("help")
                    || command.equals("h")) {

                this.view.showMessage(Messages.HELP);
            }

            else if (command.equals("list")
                    || command.equals("l")) {

                this.view.showMessage(Messages.LIST);
            }

            else if (command.equals("reset")
                    || command.equals("r")) {

                this.game.reset();
                redraw = true;
            }

            else if (command.equals("exit")
                    || command.equals("e")) {

                this.game.quit();
            }

            else if (command.equals("add")
                    || command.equals("a")) {

                advanceCycle = executeAdd(words);
            }

            else {
                this.view.showError(Messages.UNKNOWN_COMMAND);
            }

            if (advanceCycle) {
                this.game.update();
                redraw = true;
            }

            if (redraw) {
                this.view.showGame();
            }
        }

        this.view.showEndMessage();
    }

    /**
     * Procesa el comando utilizado para añadir una planta.
     *
     * @param words palabras introducidas por el usuario
     * @return true si la planta se ha añadido correctamente
     */
    private boolean executeAdd(String[] words) {

        boolean added = false;

        if (words.length < 4) {
            this.view.showError(Messages.COMMAND_PARAMETERS_MISSING);
        }

        else {
            String plantType = words[1];

            if (!this.game.checkGameObject(plantType)) {
                this.view.showError(Messages.INVALID_GAME_OBJECT);
            }

            else {
                try {
                    int column = Integer.parseInt(words[2]);
                    int row = Integer.parseInt(words[3]);

                    Position position = new Position(row, column);

                    if (!this.game.isInsideBoard(position)
                            || !this.game.isEmpty(position)) {

                        this.view.showError(Messages.INVALID_POSITION);
                    }

                    else if (!hasEnoughCoins(plantType)) {

                        this.view.showError(Messages.NOT_ENOUGH_COINS);
                    }

                    else {
                        this.game.addGameObject(plantType, position);
                        added = true;
                    }

                } catch (NumberFormatException e) {
                    this.view.showError(Messages.INVALID_POSITION);
                }
            }
        }

        return added;
    }

    /**
     * Comprueba si el jugador dispone de suficientes soles
     * para comprar una planta.
     *
     * @param plantType tipo de planta que se quiere comprar
     * @return true si hay suficientes soles
     */
    private boolean hasEnoughCoins(String plantType) {

        boolean enoughCoins = false;

        if (plantType.equalsIgnoreCase(Sunflower.shortName())
                || plantType.equalsIgnoreCase(Sunflower.longName())) {

            enoughCoins = this.game.getCoins() >= Sunflower.COST;
        }

        else if (plantType.equalsIgnoreCase(Peashooter.shortName())
                || plantType.equalsIgnoreCase(Peashooter.longName())) {

            enoughCoins = this.game.getCoins() >= Peashooter.COST;
        }

        return enoughCoins;
    }
}