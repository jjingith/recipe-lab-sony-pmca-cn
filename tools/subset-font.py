#!/usr/bin/env python3
"""Rebuild the fonts the APK bundles, from the text tables that use them.

The camera's firmware font has no Chinese, Japanese or Hangul glyphs, so the app carries its own: Droid Sans Fallback —
the AOSP font Droid Sans pairs with, so a CJK line sits in the same family as the camera's own UI — cut down to the
characters their tables (src/.../TextZhHans.java, TextZhHant.java, TextJa.java, TextKo.java) actually use, plus printable
ASCII and the English table's characters, which still appear on a translated screen (recipe names, codes, error text). One
file per script, so each gets its own glyph forms. English and Vietnamese are drawn in Droid Sans Mono, the terminal face
the green-screen look is written in — Vietnamese is Latin with diacritics, which Droid Sans Mono carries — cut down the
same way. Run it after any change to a translation; LangTest fails a character a font lacks.

    python3 tools/subset-font.py              # the sources are fetched once into out/font-source/
    python3 tools/subset-font.py --source-dir <dir>

Needs fonttools (python3 -m pip install fonttools). The sources are pinned by tag and SHA-256, and are not committed.
"""

from argparse import ArgumentParser
from hashlib import sha256
from pathlib import Path
from urllib.request import urlretrieve
import re

try:
    from fontTools import subset
except ImportError as exc:
    raise SystemExit("fonttools is required: python3 -m pip install fonttools") from exc

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "src" / "com" / "lutlab" / "pmca"
OUTPUT = ROOT / "assets" / "fonts"
# AOSP's Droid Sans Fallback (Apache-2.0), the CJK companion of the camera's Droid Sans, and Droid Sans Mono, the
# terminal face the English interface is drawn in. Both are pinned by tag and SHA-256.
SOURCES = {
    "cjk": ("DroidSansFallback.ttf",
            "https://raw.githubusercontent.com/aosp-mirror/platform_frameworks_base/android-4.4_r1/data/fonts/DroidSansFallback.ttf",
            "05d71b179ef97b82cf1bb91cef290c600a510f77f39b4964359e3ef88378c79d"),
    "mono": ("DroidSansMono.ttf",
             "https://raw.githubusercontent.com/aosp-mirror/platform_frameworks_base/android-4.4_r1/data/fonts/DroidSansMono.ttf",
             "12b552de765dc1265d64f9f5566649930dde4dba07da0251d9f92801e70a1047"),
}
FONTS = [
    # tables, output file, family name, source
    (["TextZhHans.java"], "RecipeLabCJKsc-Regular.ttf", "Recipe Lab CJK SC", "cjk"),
    (["TextZhHant.java"], "RecipeLabCJKtc-Regular.ttf", "Recipe Lab CJK TC", "cjk"),
    (["TextJa.java"], "RecipeLabCJKjp-Regular.ttf", "Recipe Lab CJK JP", "cjk"),
    (["TextKo.java"], "RecipeLabCJKkr-Regular.ttf", "Recipe Lab CJK KR", "cjk"),
    (["TextEn.java", "TextVi.java"], "RecipeLabMono-Regular.ttf", "Recipe Lab Mono", "mono"),
]
# The camera's own font, Droid Sans (2048 units per em): line ascent / descent and the glyph bounding box. A TextView
# sizes each line by these, so a font that reports more makes every line taller than it is in English.
CAMERA_UPM, CAMERA_ASCENT, CAMERA_DESCENT = 2048, 1900, -500
ROW = re.compile(r'\{\s*"([^"]+)",\s*"((?:[^"\\]|\\.)*)"\s*\}')


def digest(path):
    h = sha256()
    with path.open("rb") as f:
        for block in iter(lambda: f.read(1 << 20), b""):
            h.update(block)
    return h.hexdigest()


def source(source_dir, name, url, want):
    path = source_dir / name
    if not path.is_file():
        source_dir.mkdir(parents=True, exist_ok=True)
        print("fetching %s" % url)
        urlretrieve(url, path)
    if digest(path) != want:
        raise SystemExit("%s does not match its pinned SHA-256" % path)
    return path


def table_chars(java):
    """every character of every text in a table: the values of its { "key", "text" } rows"""
    rows = ROW.findall((SRC / java).read_text(encoding="utf-8"))
    if not rows:
        raise SystemExit("no rows found in %s" % java)
    chars = set()
    for _, text in rows:
        chars.update(text.replace('\\"', '"').replace("\\\\", "\\"))
    return chars


def build(src, output, family, chars):
    options = subset.Options()
    # no OpenType layout: Android 2.3 shapes nothing, and the alternates the features pull in (a 2.9 em tall bracket
    # among them) only inflate the bounding box every line is padded to
    options.layout_features = []
    options.name_IDs = ["*"]
    options.name_legacy = True
    options.name_languages = ["*"]
    options.notdef_glyph = True
    options.notdef_outline = True
    options.recommended_glyphs = True
    options.hinting = True
    options.recalc_bounds = True   # the bounding box of what is left, not of the whole source

    font = subset.load_font(str(src), options)
    worker = subset.Subsetter(options=options)
    worker.populate(text="".join(sorted(chars)))
    worker.subset(font)

    # line metrics in the camera font's proportions, so a CJK line is as tall as an English one
    upm = font["head"].unitsPerEm
    ascent, descent = round(CAMERA_ASCENT * upm / CAMERA_UPM), round(CAMERA_DESCENT * upm / CAMERA_UPM)
    font["hhea"].ascent, font["hhea"].descent, font["hhea"].lineGap = ascent, descent, 0
    os2 = font["OS/2"]
    os2.sTypoAscender, os2.sTypoDescender, os2.sTypoLineGap = ascent, descent, 0
    os2.usWinAscent, os2.usWinDescent = ascent, -descent

    # a subset of Droid Sans Fallback must not claim the upstream family name
    postscript = family.replace(" ", "") + "-Regular"
    for name_id, value in {1: family, 2: "Regular", 3: family + " Regular", 4: family + " Regular", 6: postscript, 16: family, 17: "Regular"}.items():
        font["name"].setName(value, name_id, 3, 1, 0x409)
        font["name"].setName(value, name_id, 1, 0, 0)

    cmap = font.getBestCmap()
    missing = sorted(ord(c) for c in chars if ord(c) not in cmap)
    if missing:
        raise SystemExit("%s lacks: %s" % (src.name, " ".join("U+%04X" % c for c in missing)))
    out = OUTPUT / output
    font.save(str(out), reorderTables=True)
    print("%s  %d glyphs requested  %d bytes  SHA-256 %s" % (out.relative_to(ROOT), len(chars), out.stat().st_size, digest(out)))


def main():
    parser = ArgumentParser()
    parser.add_argument("--source-dir", type=Path, default=ROOT / "out" / "font-source")
    args = parser.parse_args()
    OUTPUT.mkdir(parents=True, exist_ok=True)
    common = set(chr(c) for c in range(0x20, 0x7f)) | table_chars("TextEn.java")
    sources = {}
    for tables, output, family, key in FONTS:
        if key not in sources:
            sources[key] = source(args.source_dir, *SOURCES[key])
        chars = set(common)
        for java in tables:
            chars |= table_chars(java)
        build(sources[key], output, family, chars)


if __name__ == "__main__":
    main()
