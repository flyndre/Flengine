# Technischer Kontext

![](./attachments/Technischer%20Kontext.png)

## UCI-Schnittstelle

Die Flengine kommuniziert mit dem Frontend über das Universal Chess Interface (UCI) Protokoll. Die Kommunikation erfolgt über Plain Text, die über die von der Flengine bereitgestellte Schnittstelle übertragen werden.

## UCI-kompatibles Frontend (Fremdsystem)

Da Flengine über das UCI-Protokoll kommuniziert, kann als Benutzeroberfläche ein beliebiges UCI-kompatibles Frontend gewählt werden, dass die GUI der Flengine darstellt.

## Lichess-Datenbank (Fremdsystem)

Für eine Auswahl an möglichen Spielzügen zu Beginn oder zu Ende eines Spiels, nutzt die Flengine eine externe Datenbank von Lichess, die vordefinierte Schachzüge enthält. Dadurch kann die Flengine während des Eröffnung- oder Endspiels auf Datenbankeinträge zurückgreifen, die bei Beginn oder Ende des Schachspiels ausgewählt werden können.

Der Zugriff auf die Datenbank von Lichess geschieht via einer REST-Schnittstelle über HTTP.

## Abbildung technischer Kontext auf fachlichen Kontext

| **Technischer Kontext** | **Fachlicher Kontext** |
| --- | --- |
| UCI-kompatibles Frontend | CuteChess |
| Lichess-Datenbank | Eröffnungsdatenbank, Endspieldatenbank |