package com.lutlab.pmca;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * The palettes: that a stored Theme choice resolves the way the menu says it does and steps through every look, that
 * every slot is written from the row it belongs to — a slot left out of a row would only show up as a colour that never
 * moved — that the cassette-futurism look is the one the app shipped with, value by value, that only the tube draws the
 * scanline veil, and that each look can actually be read: the body text on its ground, the lettering on a lit key and a
 * readout on the panel all carry enough contrast. The tag on a lit recipe row is the case that catches a palette copied
 * by scaling: on the tube it is the amber plate that stands out, on the pale window the row under it is the dark one.
 */
class ThemeTest {
    /** the slots in the order a palette row is written, under the names Theme writes them to: a new slot goes here too */
    private static final String[] SLOTS = {
        "GROUND", "MODAL", "CHASSIS", "EDGE",
        "ACCENT", "ACCENT_DIM", "ACCENT_SOFT", "INK", "INK_SOFT",
        "TEXT", "TEXT_DIM", "TEXT_FAINT", "RULE",
        "AMBER", "AMBER_DIM", "BEIGE",
        "BADGE_OK", "BADGE_EDITED", "BADGE_WARN", "WARN_TEXT",
        "TAG_BG", "TAG_BG_ON", "TRACK", "THUMB",
        "SCAN", "STAR", "HEART",
    };

    @AfterEach void theTube() { Theme.apply(Theme.CRT); Lang.use(Lang.EN); }

    // ---- the menu choice
    @Test void aChoiceIsStoredByCodeAndAnythingElseIsTheDefault() {
        for (int c : Theme.CHOICES) assertEquals(c, Theme.parseChoice(Theme.choiceCode(c)), Theme.choiceCode(c));
        assertEquals(Theme.DEFAULT, Theme.parseChoice(null), "nothing stored yet");
        assertEquals(Theme.DEFAULT, Theme.parseChoice("luna"), "a theme this build does not have");
        assertEquals(Theme.choiceCode(Theme.DEFAULT), Theme.choiceCode(-1));
        assertEquals(Theme.choiceCode(Theme.DEFAULT), Theme.choiceCode(Theme.COUNT));
    }

    @Test void theChoiceStepsThroughEveryThemeAndWraps() {
        assertEquals(Theme.XP_LIGHT, Theme.nextChoice(Theme.CRT, +1));
        assertEquals(Theme.XP_DARK, Theme.nextChoice(Theme.XP_LIGHT, +1));
        assertEquals(Theme.CRT, Theme.nextChoice(Theme.XP_DARK, +1));
        assertEquals(Theme.XP_DARK, Theme.nextChoice(Theme.CRT, -1));
        for (int dir : new int[] { -1, +1 }) {                    // a full turn comes back to where it started
            int c = Theme.CRT;
            for (int i = 1; i <= Theme.CHOICES.length; i++) c = Theme.nextChoice(c, dir);
            assertEquals(Theme.CRT, c);
        }
    }

    @Test void theThemeRowNamesThemInTheDisplayLanguage() {
        assertEquals("Cassette futurism", Theme.choiceLabel(Theme.CRT));
        assertEquals("Windows XP (light)", Theme.choiceLabel(Theme.XP_LIGHT));
        assertEquals("Windows XP (dark)", Theme.choiceLabel(Theme.XP_DARK));
        Lang.use(Lang.ZH_HANS);
        assertEquals("卡带未来主义", Theme.choiceLabel(Theme.CRT));
        Lang.use(Lang.ZH_HANT);
        assertEquals("卡帶未來主義", Theme.choiceLabel(Theme.CRT));
        assertEquals("Windows XP（淺色）", Theme.choiceLabel(Theme.XP_LIGHT));
        assertEquals("卡帶未來主義", Theme.choiceLabel(9), "a choice off the end shows the default");
    }

    // ---- the palettes
    @Test void everySlotIsWrittenFromItsOwnRow() throws Exception {
        int[][] rows = rows();
        for (int id = 0; id < Theme.COUNT; id++) {
            Theme.apply(id);
            assertEquals(id, Theme.current());
            assertEquals(SLOTS.length, rows[id].length, Theme.choiceCode(id) + " has a value per slot, or a slot has no name here");
            for (int s = 0; s < SLOTS.length; s++)
                assertEquals(rows[id][s], slot(SLOTS[s]), Theme.choiceCode(id) + " leaves " + SLOTS[s] + " where it was");
        }
    }

    /** the app's first look, pinned: a palette edited by hand has to leave these alone */
    @Test void theGreenTubeIsTheOneTheAppShippedWith() {
        Theme.apply(Theme.CRT);
        assertTrue(Theme.crt());
        assertEquals(0xF0080E09, Theme.GROUND);
        assertEquals(0xFF050906, Theme.MODAL);
        assertEquals(0xFF20261C, Theme.CHASSIS);
        assertEquals(0x99FFB23E, Theme.EDGE);
        assertEquals(0xFF74FF9A, Theme.ACCENT);
        assertEquals(0xFF06120A, Theme.INK);
        assertEquals(0xFFD0FFDA, Theme.TEXT);
        assertEquals(0xCC8FE6A6, Theme.TEXT_DIM);
        assertEquals(0xFFFFB23E, Theme.AMBER);
        assertEquals(0x55FFB23E, Theme.TAG_BG);
        assertEquals(0x33061208, Theme.TAG_BG_ON);
        assertEquals(0x26000000, Theme.SCAN);
    }

    @Test void aSwitchPutsThePaletteBackAndCountsAVersion() {
        Theme.apply(Theme.CRT);
        int version = Theme.version(), modal = Theme.MODAL;
        Theme.apply(Theme.XP_LIGHT);
        assertTrue(Theme.version() > version, "a view restyles on the version alone");
        assertNotEquals(modal, Theme.MODAL);
        assertFalse(Theme.crt());
        Theme.apply(Theme.CRT);
        assertEquals(modal, Theme.MODAL);
        assertTrue(Theme.crt());
    }

    @Test void aThemeThisBuildDoesNotHaveFallsBackToTheTube() {
        Theme.apply(Theme.XP_DARK);
        for (int id : new int[] { 9, -1 }) {
            Theme.apply(id);
            assertEquals(Theme.DEFAULT, Theme.current());
            assertTrue(Theme.crt());
        }
    }

    @Test void theScanlineVeilOnlyRunsOnTheTube() {
        for (int id : Theme.CHOICES) {
            Theme.apply(id);
            assertEquals(id == Theme.CRT, Theme.crt());
            if (!Theme.crt()) assertEquals(0, Theme.SCAN >>> 24, Theme.choiceCode(id) + " would still draw a veil");
        }
    }

    // ---- what a look has to be able to do
    @Test void everyLookIsReadable() {
        for (int id : Theme.CHOICES) {
            Theme.apply(id);
            String code = Theme.choiceCode(id);
            assertTrue(contrast(Theme.TEXT, Theme.MODAL) >= 4.5, code + " body text on the modal ground: " + round(contrast(Theme.TEXT, Theme.MODAL)));
            assertTrue(contrast(Theme.TEXT, Theme.GROUND) >= 4.5, code + " body text on the panel ground: " + round(contrast(Theme.TEXT, Theme.GROUND)));
            assertTrue(contrast(Theme.TEXT_DIM, Theme.GROUND) >= 4.5, code + " a detail line on the panel ground: " + round(contrast(Theme.TEXT_DIM, Theme.GROUND)));
            assertTrue(contrast(Theme.INK, Theme.ACCENT) >= 4.5, code + " the lettering on a lit key: " + round(contrast(Theme.INK, Theme.ACCENT)));
            assertTrue(contrast(Theme.AMBER, Theme.MODAL) >= 4.5, code + " a readout on the panel: " + round(contrast(Theme.AMBER, Theme.MODAL)));
        }
    }

    @Test void theTagOnALitRowGoesTheWayItsGroundDemands() {
        Theme.apply(Theme.CRT);
        assertTrue(lum(Theme.TAG_BG_ON) < lum(Theme.TAG_BG), "on the green row the tag takes the darkness off, so the amber plate still stands out");
        Theme.apply(Theme.XP_LIGHT);
        assertTrue(lum(Theme.TAG_BG_ON) > lum(Theme.TAG_BG), "on the pale window the lit row is the dark one, so the tag over it has to lighten");
    }

    // ---- the tables, and the brightness the checks read
    private static int[][] rows() throws Exception {
        Field f = Theme.class.getDeclaredField("PALETTES");
        f.setAccessible(true);
        return (int[][]) f.get(null);
    }

    private static int slot(String name) throws Exception {
        Field f = Theme.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.getInt(null);
    }

    /** relative luminance of a colour's channels, alpha ignored: the palettes are read for their colour, not their strength */
    private static double lum(int argb) {
        return 0.2126 * channel((argb >> 16) & 0xff) + 0.7152 * channel((argb >> 8) & 0xff) + 0.0722 * channel(argb & 0xff);
    }

    private static double channel(int c) {
        double s = c / 255.0;
        return s <= 0.03928 ? s / 12.92 : Math.pow((s + 0.055) / 1.055, 2.4);
    }

    /** WCAG contrast: 4.5 is the ratio body text needs */
    private static double contrast(int fg, int bg) {
        double a = lum(fg), b = lum(bg);
        return (Math.max(a, b) + 0.05) / (Math.min(a, b) + 0.05);
    }

    private static String round(double v) { return String.format("%.2f", v); }
}
