# Testkonzept

Um die ordnungsgemäßen Funktionen der Flengine sicherstellen zu können, wurden einige Testklassen erstellt, die verschiedene Testfälle eines Schachspiels abdecken.

## Testumfang

Ziel der Tests ist das Sicherstellen des ordnungsgemäßen Ablaufs aller Kernfunktionen der Flengine. Die dafür erstelten Unit-Tests beschränken sich dabei auf die Hauptkomponenten der Flengine. Zu finden sind diese im Projektverzeichnis unter `flengine/src/test`.

### Engine-Controller-Tests

Hierbei werden die grundlegenden Funktionen des Engine-Controllers getestet. Anhand von unterschiedlichen Figurstellungen eines Schachbretts wird geprüft, ob die Engine für die jeweilige Stellung einen möglichen Zug findet. Außerdem wird der Zuggeber getestet und ob dieser den Zug mittels des MinMax-Algorithmus berechnet oder aus der Eröffnungs- bzw. Endspiel-Datenbank bezieht.

### Tests für die Schachregeln

Die Tests der Schachregeln sind aufgeteilt in zwei Testklassen: `PieceRuleTest` und `RuleTest`

Die `PieceRuleTests` prüfen die korrekten Zugmöglichkeiten aller verschiedenen Schachfiguren sowie alle weiteren besonderen Züge eines Schachspiels (Rochade, En Passant, Bauernumwandlung). Außerdem wird die korrekte Funktionsweise der Hilfsmethode `isFieldCovered` geprüft.

Die `RuleTests` testen die übergreifenden Regeln bei der Rückgabe der möglichen Spielzüge. Dabei werden bestimmte Situationen geprüft, die die individuellen Zugmöglichkeiten einer Schachfigur einschränken (zB. König im Schach oder Fesselung/Pin einer Figur).

### MinMax-Algorithmus-Tests

Diese Tests prüfen, ob der MinMax-Algorithmus für eine bestimmte Stellung eines Schachbretts einen möglichen Zug findet.

## Testablauf

Um alle Testklassen eines Maven-Projekts auszuführen, kann der Befehl `mvn test` in der Commandline ausgeführt werden. Neben einem manuellen Ausführen der Tests werden außerdem alle Tests bei Änderungen des GitHub-Repositories ausgeführt. Die automatisierten Workflows des Repositories ermöglicht das automatische Testen und Builden der Application bei Änderungen, nativ auf GitHub.

Um für die verschiedenen Positionen und Kombinationen eines Schachbretts eine möglichst umfangreiche Auswahl an zu überprüfenden Schachbrettern bieten zu können, wurden für die Tests beispielhafte Schachpositionen mittels der FEN-Notation vorbereitet. Der Converter wandelt diese in das entsprechende Schachbrett-Datenobjekt um, das im Anschluss als Grundlage für die individuellen Testfälle dient. Zur visuellen Darstellung eines Schachbretts in der FEN-Notation kann die externe Website [https://www.dailychess.com/chess/chess-fen-viewer.php](https://www.dailychess.com/chess/chess-fen-viewer.php) verwendet werden.

## Testumgebung

Für die Ausführung der Tests nutzt die Flengine die externe Bibliothek `JUnit Jupiter`, welches als Maven-Abhängigkeit im Projekt eingebunden ist. Dadurch ist es möglich, schnell Testklassen zu definieren, den Testablauf zu steuern und Ergebnisse mit ihrem Soll-Wert zu vergleichen.