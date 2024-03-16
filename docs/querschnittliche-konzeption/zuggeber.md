# Zuggeber

Um das Qualitätsziel Änderbarkeit zu erreichen, verfügt Flengine wie in [Qualitätsziele](../../docs/einfhrung/qualittsziele.md) beschrieben, über austauschbare Zuggeber.

Zuggeber sind im Code Klasse, die das `MoveProvider`\-Interface implementieren. Details dazu finden sich in [Interface MoveProvider](../../docs/bausteinsicht/ebene-2/interface-moveprovider.md). Über dieses Interface wird das _Strategy-Pattern_ umgesetzt, indem es die statische Methode `getRecommendedMoves(board: Board, options: Options): List<Move>` vorschreibt.

Im [Controller](../../docs/bausteinsicht/ebene-2/subsystem-controller.md) findet sich eine Liste von `MoveProvider`n, die der Controller (wie in der [Laufzeitsicht des Controllers](../../docs/laufzeitsicht/zugermittlung/zugermittlung-controller.md) beschrieben) nutzt, um den besten Zug zu ermitteln. Um neue Zuggeber hinzuzufügen oder alte auszutauschen, kann diese Liste angepasst werden.
