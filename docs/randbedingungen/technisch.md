# Technisch

| **Randbedingung** | **Erläuterung** |
| --- | --- |
| Implementierung in Java | Die Engine soll in Java 21 implementiert werden. |
| UCI-Protokoll | Das UCI-Protokoll (Universal Chess Interface) wird zur Datenübertragung verwendet. Es handelt sich hierbei um ein standardisiertes, zustandloses Protokoll welches für Schach verwendet wird. |
| REST-Anfragen | Die Engine wird über REST auf Eröffnungs- und Endspieldatenbank zugreifen. Hierfür werden die Dependencies OkHttp ([https://square.github.io/okhttp/](https://square.github.io/okhttp/) ) sowie Json-B ([https://javaee.github.io/jsonb-spec/](https://javaee.github.io/jsonb-spec/) ) über Maven eingebunden und verwendet. |
| Frontend | Als Frontend wurde sich für CuteChess ([https://cutechess.com/](https://cutechess.com/) ) entschieden. Im Gegensatz zu vielen anderen UCI-Frontends wird dieses noch aktiv entwickelt und unterstützt viele Spielmodi sowie Schachversionen.<br><br>Durch die Nutzung von UCI sollten alle Frontends funktionieren, die dieses Protokoll unterstützen, jedoch wurden die Funktionen vor allem mit CuteChess getestet. |
| Zugprüfung | In UCI wird davon ausgegangen, dass die Züge, die vom Frontend gesendet werden, bereits validiert wurden. |