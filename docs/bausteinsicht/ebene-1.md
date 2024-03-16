# Ebene 1

Auf oberster Ebene ist die Engine im Zentrum des Systems. Sie wird vom Umwandler, der Mittler zwischen Engine und API ist, bedient. Sie selbst verwendet Zuggeber, die ihr den besten Zug ermitteln. Dazu gehören Eröffnungs- und Endspielzuggeber sowie ein MinMax-Zuggeber, der im Midgame seinen Einsatz findet.

| **Subkomponente** | **Funktion** |
| --- | --- |
| [Converter](../bausteinsicht/ebene-2/subsystem-converter.md) | Das System Converter (Umwandler) dient als Verbindungsstück zur Außenwelt. Es wandelt die API-Anfragen in Java-Objekte um und umgekehrt. |
| [Controller](../bausteinsicht/ebene-2/subsystem-controller.md) | Die vom Controller orchestrierte Engine ist das Herzstück des Systems und ermittelt unter Zuhilfenahme von Zuggebern den aktuell besten (bzw. passendsten) Zug. |
| [Openings](../bausteinsicht/ebene-2/subsystem-openingsendgame.md) | Das Subsystem Openings bedient sich der Eröffnungsdatenbank, um in einem Eröffnungsszenario den besten Zug zu finden. |
| [Endgame](../bausteinsicht/ebene-2/subsystem-openingsendgame.md) | Das Subsystem Endgame bedient sich der Endspieldatenbank, um in einem Endpielszenario den besten Zug zu finden. |
| [MinMax](../bausteinsicht/ebene-2/subsystem-minmax.md) | Unter Zuhilfenahme des Subsystems Rules ermittelt dieser Zuggeber im Midgame den besten bzw. passendsten Zug. |
| [Rules](../bausteinsicht/ebene-2/subsystem-rules.md) | Das Subsystem Rules ermittelt ist für die EInhaltung der Schach-Regeln zuständig - generiert beispielsweise zu einer Position alle legalen Züge. |