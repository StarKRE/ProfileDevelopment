package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

public final class HSoulPiercing extends Bonus {

    private static final double DESTROY_TREATMENT_COEFFICIENT = 0.95;

    public HSoulPiercing(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer()
                .getHero();
        final Hero opponentHero = playerManager.getOpponentATeam().getCurrentPlayer()
                .getHero();
        final double treatmentDecreasing = currentHero.getAttack()
                * DESTROY_TREATMENT_COEFFICIENT;
        opponentHero.setTreatment(treatmentDecreasing);
    }
}
