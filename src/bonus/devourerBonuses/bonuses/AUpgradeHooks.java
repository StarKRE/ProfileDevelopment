package bonus.devourerBonuses.bonuses;

import bonus.bonuses.ExtendedBonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public final class AUpgradeHooks extends ExtendedBonus{

    private static final int START_COUNT = 0;

    private static final int END_COUNT = 4;

    private static final double ATTACK_BOOST = 10;

    private int count;

    private Text text;

    public AUpgradeHooks(String name, int id, ImageView sprite) {
        super(name, id, sprite);
        this.count = START_COUNT;
        this.text = new Text(START_COUNT + "/" + END_COUNT);
        installContainer(text);
    }

    @Override
    public final void use() {
        if (count + 1 == END_COUNT){
            final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
            currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        } else {
            this.count++;
            text.setText(count + "/" + END_COUNT);
        }
    }
}