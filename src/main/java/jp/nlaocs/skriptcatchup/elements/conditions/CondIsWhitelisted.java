package jp.nlaocs.skriptcatchup.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Is Whitelisted")
@Description("Whether or not the server or a player is whitelisted, or the server is whitelist enforced.")
@Examples({
        "if the player is whitelisted:",
        "if the server is whitelisted:",
        "if the server whitelist is enforced:"
})
@Since("2.5.2, 2.9.0 (enforce, offline players)")
@RequiredPlugins("MC 1.17+ (enforce)")
public class CondIsWhitelisted extends Condition {

    static {
        String[] patterns = new String[3];
        patterns[0] = "[the] server (is|not:(isn't|is not)) [catch[ ]up] (in white[ ]list mode|white[ ]listed)";
        patterns[1] = "%offlineplayers% (is|are|not:(isn't|is not|aren't|are not)) [catch[ ]up] white[ ]listed";
        patterns[2] = "[the] server white[ ]list (is|not:(isn't|is not)) [catch[ ]up] enforced";
        Skript.registerCondition(CondIsWhitelisted.class, patterns);
    }

    @Nullable
    private Expression<OfflinePlayer> players;

    private boolean isServer;
    private boolean isEnforce;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        setNegated(parseResult.hasTag("not"));
        isServer = matchedPattern != 1;
        isEnforce = matchedPattern == 2;
        if (isEnforce) {
            Skript.error("Enforce whitelist is not supported in this version.");
            return false;
        }
        if (matchedPattern == 1)
            players = (Expression<OfflinePlayer>) exprs[0];
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (isServer)
            return Bukkit.hasWhitelist() ^ isNegated();
        return players.check(event, OfflinePlayer::isWhitelisted, isNegated());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String negation = isNegated() ? "not" : "";
        if (isServer) {
            if (isEnforce) {
                return "the server whitelist is " + negation + " enforced";
            }
            return "the server is " + negation + " whitelisted";
        }
        return players.toString(event, debug) + " is " + negation + " whitelisted";
    }

}
