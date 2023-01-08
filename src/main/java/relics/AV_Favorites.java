package relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
public class AV_Favorites extends CustomRelic{
    public static final String ID = "AV_Favorites";
    private static final String IMG = "img/relics_IronMan/AV_Favorites.png";
    private static final String IMG_OTL = "img/relics_IronMan/outline/AV_Favorites.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public AV_Favorites() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        if (card.type == AbstractCard.CardType.SKILL) {
            this.counter++;
            if (this.counter % 2 == 0) {
                //如果是2的倍数，counter=0和获得5点格挡
                this.counter = 0;
                flash();
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, 6));
            }
        }
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new AV_Favorites();
    }
}
