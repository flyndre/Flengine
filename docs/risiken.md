# Risiken

Jedes Projekt birgt gewisse Risiken, die dessen Erfolg gefährden. Folgende Risiken können den Erfolg dieses Projekts negativ beeinträchtigen:

| **Risiko** | **Beschreibung** | **Notlösung** | **Gegenmaßnahmen** | **Priorisierung\*** |
| --- | --- | --- | --- | --- |
| Zeitplan wird nicht eingehalten | Wir haben keine große Erfahrung mit Projekten dieser Art, weswegen deie Gefahr besteht, dass wir den Aufwand falsch einschätzen. Die kann dazu führen, dass wir die Aufgabenstellung nicht in Gänze erfüllen können. Daraus resultieren würde ein Noteabzug - im schlimmsten Fall ein Nichtbestehen des Kurses. | Falls absehbar ist, dass nicht alle Anforderungen erfüllt werden, wird sich darauf konzentriert, bereits implementierte Dinge komplett lauffähig zu bekommen. Der Hauptfokus liegt auf Spielregeln und MinMax-Algorithmus. | *   Puffer von 1-2 Wochen vor 24.12.2023<br>    <br>*   Regelmäßige Analyse des Status Quo<br>    <br>*   Priorisierung der Aufgaben | 5   |
| Inkompatibilität zu UCI | Wir haben keinerlei Vorwissen über UCI, es gibt auch nur wenig Dokumentation des protokolls online. Darum besteht die Gefahr, dass wir nicht allen Anforderungen des Protokolls gerecht werden bzw, dass das Protokoll nicht alles abbilden kann, was Teil der Aufgabe ist. | Wenn UCI nicht alle nötigen Funktionen abbilden kann, nutzen wir alle Funktionen, die es hat und bilden die restlichen mit einem anderen Endpunkt ab.<br><br>Wenn wir nicht alle Anforderungen von UCI abbilden können und Frontends damit nicht arbeiten können, stellen wir ein simples Konsolenfrontend bereit. | *   Von Beginn an an UCI orientieren<br>    <br>*   An bestehenden Frontends orientieren<br>    <br>*   Früh alternativen suchen | 10  |
| Funktionalität | Schach ist ein komplexes Spiel mit einigen Sonderregeln. Es besteht die Gefahr, dass die Engine nicht alle Regeln abbilden kann oder dass zu spät auffällt, dass die Implementierung von Regeln mit Datenmodell/Struktur des Codes inkompatibel ist. | Spezialregeln werden im Notfall weggelassen und es wird sich darauf konzentriert, dass die Grundregeln (Zugmuster der Figuren) implementiert werden. | *   Früh zu implementierende Regeln sammeln<br>    <br>*   Fokus auf zentrale und häufige Regeln legen | 10  |
| Zugprüfung | Obwohl es in UCI die Aufgabe der GUI ist, die Validität der Züge sicherzustellen, könnte es vorkommen, dass das Frontend einen ungültigen Zug sendet. | Sollte der Fall auftreten, dass die Engine ungültige Züge erhält, soll mit einem Abbruch des Spiels oder einer anderen UCI-Konformen Verhaltensweise reagiert werden. | *   Aktuelles, populäres Frontend verwenden | 5   |

**\* Die Priorisierung sagt aus, wie schwerwiegend das Risiko ist, wenn es eintrifft. Skala hat Werte von 1 (weniger schlimm) zu 10 (sehr schlimm)**