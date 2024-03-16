![Flengine Logo](docs/attachments/flengine.png)

# Flengine

> A UCI chess engine.

# Motto

-   Liberté
-   Agilité
-   Schachenginé

# Docs

> [!NOTE]  
> Since this project has been conducted in German, the docs were created in German.

-   [Einführung](./docs/einfhrung.md)
    -   [Aufgabenstellung](./docs/einfhrung/aufgabenstellung.md)
    -   [Qualitätsziele](./docs/einfhrung/qualittsziele.md)
    -   [Stakeholder](./docs/einfhrung/stakeholder.md)
-   [Randbedingungen](./docs/randbedingungen.md)
    -   [Technisch](./docs/randbedingungen/technisch.md)
    -   [Organisatorisch](./docs/randbedingungen/organisatorisch.md)
    -   [Konventionen](./docs/randbedingungen/konventionen.md)
-   [Kontextabgrenzung](./docs/kontextabgrenzung.md)
    -   [Fachlicher Kontext](./docs/kontextabgrenzung/fachlicher-kontext.md)
    -   [Technischer Kontext](./docs/kontextabgrenzung/technischer-kontext.md)
-   [Lösungsstrategie](./docs/lsungsstrategie.md)
    -   [Übersicht](./docs/lsungsstrategie/bersicht.md)
    -   [Aufbau](./docs/lsungsstrategie/aufbau.md)
    -   [Spielstrategien](./docs/lsungsstrategie/spielstrategien.md)
    -   [Anbindung](./docs/lsungsstrategie/anbindung.md)
-   [Bausteinsicht](./docs/bausteinsicht.md)
    -   [Ebene 1](./docs/bausteinsicht/ebene-1.md)
    -   [Ebene 2](./docs/bausteinsicht/ebene-2.md)
        -   [Subsystem Converter](./docs/bausteinsicht/ebene-2/subsystem-converter.md)
        -   [Subsystem Controller](./docs/bausteinsicht/ebene-2/subsystem-controller.md)
        -   [Interface MoveProvider](./docs/bausteinsicht/ebene-2/interface-moveprovider.md)
        -   [Subsystem Openings/Endgame](./docs/bausteinsicht/ebene-2/subsystem-openingsendgame.md)
        -   [Subsystem MinMax](./docs/bausteinsicht/ebene-2/subsystem-minmax.md)
        -   [Subsystem Rules](./docs/bausteinsicht/ebene-2/subsystem-rules.md)
-   [Laufzeitsicht](./docs/laufzeitsicht.md)
    -   [UCI-Ablauf](./docs/laufzeitsicht/uci-ablauf.md)
    -   [RequestHandler](./docs/laufzeitsicht/requesthandler.md)
    -   [Zugermittlung](./docs/laufzeitsicht/zugermittlung.md)
        -   [Zugermittlung Converter](./docs/laufzeitsicht/zugermittlung/zugermittlung-converter.md)
        -   [Zugermittlung Controller](./docs/laufzeitsicht/zugermittlung/zugermittlung-controller.md)
        -   [Zuermittlung MinMax](./docs/laufzeitsicht/zugermittlung/zuermittlung-minmax.md)
    -   [Rules](./docs/laufzeitsicht/rules.md)
-   [Verteilungssicht](./docs/verteilungssicht.md)
    -   [Deployment](./docs/verteilungssicht/deployment.md)
-   [Querschnittliche Konzeption](./docs/querschnittliche-konzeption.md)
    -   [Domänenmodell](./docs/querschnittliche-konzeption/domnenmodell.md)
    -   [Logging](./docs/querschnittliche-konzeption/logging.md)
    -   [Optionen](./docs/querschnittliche-konzeption/optionen.md)
    -   [Testkonzept](./docs/querschnittliche-konzeption/testkonzept.md)
    -   [Zuggeber](./docs/querschnittliche-konzeption/zuggeber.md)
-   [Entscheidungen](./docs/entscheidungen.md)
    -   [DE1: Datentyp für Figurentyp](./docs/entscheidungen/de1-datentyp-fr-figurentyp.md)
    -   [DE2: UCI Abbruchbefehl wird ignoriert](./docs/entscheidungen/de2-uci-abbruchbefehl-wird-ignoriert.md)
    -   [DE3: Auswahl des UI](./docs/entscheidungen/de3-auswahl-des-ui.md)
    -   [DE4: Deployment via Batch](./docs/entscheidungen/de4-deployment-via-batch.md)
    -   [DE5: Programmatische Loggingkonfiguration](./docs/entscheidungen/de5-programmatische-loggingkonfiguration.md)
    -   [DE6: Nutzung von Virtual Threads](./docs/entscheidungen/de6-nutzung-von-virtual-threads.md)
    -   [DE7: Umgang mit Patt](./docs/entscheidungen/de7-umgang-mit-patt.md)
-   [Qualitätsanforderungen](./docs/qualittsanforderungen.md)
-   [Risiken](./docs/risiken.md)
-   [Glossar](./docs/glossar.md)
