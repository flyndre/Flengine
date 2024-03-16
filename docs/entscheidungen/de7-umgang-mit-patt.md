# DE7: Umgang mit Patt

## Fragestellung: Soll die Engine überprüfen, ob ein Patt eine gute Alternative zu einem Zug ist.

## Einflussfaktoren:

Entscheidung hat Einfluss auf Komplexität des Enginecode und den nötigen Entwicklungsaufwand.

Patt schwer in MinMax zu bewerten.

## Alternativen:

### Engine beachtet die Möglichkeit des Patt nicht

Wenn die Engine alle möglichen Züge durchgeht und bewertet wird die Möglichkeit einen Patt vorzuschlagen nicht berücksichtigt und die Engine versucht nur durch Züge zu gewinnen.

### Engine beachtet die Möglichkeit des Patt

Wenn die Engine alle Züge zur Bewertung duchgeht wird auch jedes mal ein Patt in die Bewertung miteinbezogen und dieser wird ausgeführt, wenn er ein besseres Egebniss liefert als alle anderen Möglichkeiten.

## Entscheidung:

Patt wird ignoriert, da er nicht gut im eingesetzten MinMax-Algorithmus bewertet werden kann.