Recipe Lab bundled fonts
========================

Five cut-down fonts: English and Vietnamese are drawn in Droid Sans Mono, the terminal face
the green-screen UI is set in, and the Chinese, Japanese and Korean tables in Droid Sans
Fallback, the CJK companion of Droid Sans, the font the camera's own interface uses. Each holds
only the glyphs its table uses (src/com/lutlab/pmca/TextEn.java, TextVi.java, TextZhHans.java,
TextZhHant.java, TextJa.java, TextKo.java), plus printable ASCII and the other tables'
characters, which still appear alongside it. A CJK line sits in the same family as the Latin
around it.

Upstream:  https://github.com/aosp-mirror/platform_frameworks_base (tag android-4.4_r1)
  data/fonts/DroidSansFallback.ttf   (the Chinese, Japanese and Korean fonts)
    SHA-256: 05d71b179ef97b82cf1bb91cef290c600a510f77f39b4964359e3ef88378c79d
  data/fonts/DroidSansMono.ttf       (the English and Vietnamese font)
    SHA-256: 12b552de765dc1265d64f9f5566649930dde4dba07da0251d9f92801e70a1047
License:   Apache License 2.0 (LICENSE.txt)

Generated (static TrueType for Android 2.3):
  RecipeLabMono-Regular.ttf    family "Recipe Lab Mono"    (English, Vietnamese)
  RecipeLabCJKsc-Regular.ttf   family "Recipe Lab CJK SC"  (Simplified Chinese)
  RecipeLabCJKtc-Regular.ttf   family "Recipe Lab CJK TC"  (Traditional Chinese)
  RecipeLabCJKjp-Regular.ttf   family "Recipe Lab CJK JP"  (Japanese)
  RecipeLabCJKkr-Regular.ttf   family "Recipe Lab CJK KR"  (Korean)

No OpenType layout tables are kept (Android 2.3 shapes nothing), and the line metrics are set
to the camera font's proportions (Droid Sans: ascent 1900, descent 500 per 2048), so no line
is taller than an English one in the camera's own font.

Rebuild after any change to the text tables:

    python3 tools/subset-font.py

It prints each output's size and SHA-256. The files are not byte-for-byte reproducible across
fonttools versions; LangTest checks what matters, that each font maps every character its table
uses and reports no more line height than the camera's font.
