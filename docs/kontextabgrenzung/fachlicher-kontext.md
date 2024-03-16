# Fachlicher Kontext

![](./attachments/Fachlicher%20Kontext.png)

## Menschlicher Spieler

Schach wird von zwei Gegnern gespielt, die nacheinander mit ihren Figuren ziehen. Der menschliche Spieler tritt dazu gegen die Flengine an, die selbstständig Züge berechnet und je nach Zug des menschlichen Spielers den nächsten Schachzug zieht. Der menschliche Spieler greift für die Durchführung seines Zuges auf die GUI des Frontends zu.

## Computergegner

Darüber hinaus kann die Flengine auch gegen weitere Schach-Engines antreten, die an das Cute Chess GUI angebunden sind. CuteChess kommuniziert auch dann mit der Flengine über UCI.

## CuteChess (Fremdsystem)

Für die Benutzeroberfläche der Flengine wurde die GUI von CuteChess ([https://cutechess.com/](https://cutechess.com/) ) ausgewählt. CuteChess bildet dadurch das Frontend, über die der menschliche Spieler mit der Flengine interagieren kann.

## UCI-Schnittstelle

Die Flengine kommuniziert mit dem Cute Chess Frontend über das Universal Chess Interface (UCI) Protokoll. Die Kommunikation erfolgt über Plain Text, die über die von der Flengine bereitgestellte Schnittstelle übertragen werden.

## Eröffnungsdatenbank (Fremdsystem)

Zu Beginn eines Schachspiels nutzt die Flengine eine Auswahl an Eröffnungsspielzügen, die im frühen Stadium des Schachspiels genutzt werden können. Dazu wird eine externe Datenbank angebunden, die eine Vielzahl an Kombinationen anfänglicher Spielzügen beinhaltet, die für die verschiedenen Eröffnungsspielzüge genutzt wird.

## Endspieldatenbank (Fremdsystem)

Zum Ende eines Schachspiels nutzt die Flengine eine Auswahl an Endspielzügen, die im späten Stadium des Schachspiels genutzt werden können. Dafür wird ebenfalls eine extern angebundene Datenbank genutzt, die verschiedene Kombinationen an möglichen Spielzügen beinhaltet, um bei wenigen Figuren auf dem Schachbrett den nächsten, bestmöglichen Zug auszuwählen.