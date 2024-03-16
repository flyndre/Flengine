# Optionen

UCI unterstützt das konfigurieren von Engines über Optionen. Alle unterstützten Einstellungsoptionen welche das Verhalten der Engine anpassen oder verändern müssen innerhalb der Engine von verschiedenen Stellen aus einsehbar sein und im verändert werden könnnen.

Flengine stellt hierfür die Klasse `Options` bereit, welche alle Einstellungsmöglichkeiten verwaltet. Innerhalb der Klasse `RequestHandler` wird hierfür ein Objekt erstellt, welches durch den Defaultkonstruktor sämtliche Standardwerte beinhaltet.

Diese Werte können über entsprechende Methoden des Objekts geändert werden. In der Praxis geschieht dies innerhalb des `RequestHandler`\-Objektes durch entsprechende UCI-Befehle der GUI.

Die unterstützten Einstellungen können in der folgenden Tabelle eingesehen werden:

| **Option** | **mögliche Werte** | **Standardwert** | **Beschreibung** |
| --- | --- | --- | --- |
| Difficulty | Easy, Normal, Hard | Normal | Legt die Schwierigkeit der Engine auf einen Ganzzahl-Wert größer 0 fest. |
| RecursiveDepth | 1-10 | 3   | Legt die Tiefe des Rekursionsbaums des MiniMax-Algorithmus fest. Höhere Werte führen zu besseren Ergebnissen und deutlich längeren Berechnungszeiten. |

Wenn eine neue Berechnung gestartet wird übergibt das `RequestHandler`\-Objekt dem `Controller` einen Verweis auf das `Options`\-Objekt. Die Implementierung des Algorithmus für die Zugberechnung greift hierüber auf die aktuellen Einstellungen zu.

Die Debug-Einstellung der Engine kann ebenfalls von der GUI über UCI geändert werden. Dies ist separat unter [Logging](../querschnittliche-konzeption/logging.md) dokumentiert.

Wie die Optionen im Schach-UI konfiguriert werden können, ist in [https://flengine.atlassian.net/wiki/spaces/SD/pages/9240577/Deployment#Konfiguration](https://flengine.atlassian.net/wiki/spaces/SD/pages/9240577/Deployment#Konfiguration) erklärt.