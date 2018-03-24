package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.playerManagement.Player;
import managment.processors.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AFromFireIntoTheFire extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(AFromFireIntoTheFire.class);

    public AFromFireIntoTheFire(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    private final Processor treatmentProcessor = () -> {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getHero();
        final Hero opponentHero = opponentPlayer.getHero();
        final double damage = currentHero.getTreatment();

        if (opponentHero.getDamage(damage)) {
            actionManager.getEventEngine().handle();
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(playerManager.getCurrentTeam());
        }
    };

    @Override
    public final void use() {
        installCustomTreatment();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomTreatment() {
        actionManager.setStandardTreatment(false);
        actionManager.setProcessor(treatmentProcessor);
        log.info("INSTALLED CUSTOM TREATMENT PROCESSOR");
    }

    private void installDefaultTreatment() {
        actionManager.setDefaultProcessor();
        actionManager.setStandardAttack(true);
        log.info("INSTALLED DEFAULT TREATMENT PROCESSOR");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private boolean isWorking = true;

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultTreatment();
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "AFromFireIntoTheFire";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}