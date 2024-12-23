package jp.nlaocs.skriptcatchup.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.MainHand;

@Name("Left Handed")
@Description({
        "Checks if living entities or players are left or right-handed. Armor stands are neither right nor left-handed.",
        "Paper 1.17.1+ is required for non-player entities."
})
@Examples({
        "on damage of player:",
        "\tif victim is left handed:",
        "\t\tcancel event"
})
@Since("2.8.0")
@RequiredPlugins("Paper 1.17.1+ (entities)")
public class CondIsLeftHanded extends PropertyCondition<LivingEntity> {


    static {
        register(CondIsLeftHanded.class, PropertyType.BE, "[catch[ ]up] (:left|right)( |-)handed", "livingentities");
    }

    private MainHand hand;

    @Override
    public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        hand = parseResult.hasTag("left") ? MainHand.LEFT : MainHand.RIGHT;
        if (!HumanEntity.class.isAssignableFrom(exprs[0].getReturnType())) {
            Skript.warning("Non-player entities' main hand is not supported in this version.\n" +
                    "(Always false is returned)");
        }
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }

    @Override
    public boolean check(LivingEntity livingEntity) {
        // check if entity is a player
        if (livingEntity instanceof HumanEntity)
            return ((HumanEntity) livingEntity).getMainHand() == hand;

        // invalid entity
        return false;
    }

    @Override
    protected String getPropertyName() {
        return (hand == MainHand.LEFT ? "left" : "right") + " handed";
    }

}
