# Mensch Ärgere dich nicht!

![Spielbrett](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Mensch_%C3%A4rgere_dich_nicht_4.svg/220px-Mensch_%C3%A4rgere_dich_nicht_4.svg.png)

![Komischen Spielbrett](https://hd.z0py.de/uploads/7c3aa462-0bdb-43f6-98e8-669f265d6175.png)

## Entwurfsskizze

- Für jeden Screen eine kleine Skizze zeichnen (z.b Figma)
- Mit Pfeilen verbinden, wie man wo hinkommt
- Pro Bild: wie kommuniziert es miteinander + Erklärung
- mvc strucktur?

## Klassen

- Backend sollte so gut wie möglich von Frontend abgekapselt werden
-

### Spielklasse
- game.Game Startet
- Anzahl der Spieler initialisieren
- Würfeln
- Managed Logik (Spieler springen etc.)
-

### game.PlayingField
- game.PlayingField ist 40 Felder groß (array mir modulo)
- Wenn field beseutzt, speichert es farbe/spieler
- (Felder haben Prioritäten)

#### game.Field
- hat Attribute: Besetzt von Farbe und Nummer

### Spielsteine
- Eigenes Array pro team
- Hat attribute: Farbe (enum/COLOR.RED), Nummer des Feldes auf game.PlayingField
- Ist zu Hause / auf dem Spielbrett / im Ziel
- Nummer im Zielfeld

### Teams
- Spielfarben Enums/<COLOR.>
- Liste der Farben
- 4 Teams
- Jedes game.Team hat ein Startfeld (Auf game.PlayingField) und endbereich bzw. was das letzte game.Field vor dem Ziel ist
- Pro game.Team: 2 interne Arrays

### Menschlicher Spieler
- Hat manchmal die Wahl, ob neuer game.Piece raus oder weiter ziehen
- Auswahl des Steines, welcher bewegt werden soll

### Bot
- Prio:
    1. Ziel
    2. Vom Spawn des Gegners runter -> muss irgendwie implementiert werden (game.Field hat )
    3. Gegner Schlagen (Wenn ein Spieler nahe des Bot-Spawns steht, eher mit ner 6 versuchen Gegner zu behindern)
- Je nach Schwierigkeit, ?Priorität abschwächen (Fehlerwahrscheinlichkeit)

## UI

- JavaFX ist ok, wenn wir die Bib mit includen bzw. alles in eine Zip packen können (das es einfach zu installieren ist)
- Veränderbare Größe

### Spielsteine
- Kleinere kreise als Spielfeldkreis
- Wenn angeklickt -> game.Field wo stein hingeschoben ?Blinkt? werden könnte, kann aber wieder zurück (nicht finale entscheidung)

### Settings
- Lautstärke
- Farbenblinder Modus
- KI Schwierigkeit

### Ideen
- Würfel Sound
- Spieler über das game.Field bewegen (Animation)
- Stein schieben/setzen sound


## Multiplayer
- 


## 3D
- [YT Tutorial](https://www.youtube.com/playlist?list=PLsRmsZm0xMNogPyRn6gNWq4OM5j22FkAU)
- unterschiedliche Spielsteine

## Extra
- Aktionsfelder
- Mehr als 4 Spieler

# Code Einigungen

# AOP Beschreibung

## Aufgabe
ErstellenSie ein Programm, welches das game.Game "Mensch ärgere Dich nicht" simuliert.
Hierbei können maximal vier menschliche Spieler teilnehmen. Nehmen weniger als vier
Spieler teil sollen die restlichen vom Computer simuliert werden. Der Computer sollte ein
möglichst sinnvolles Verhalten zeigen.
Sehen Sie einen Testmodus vor, bei dem alle Features überprüft werden können,
insbesonders: Vorgabe von Würfelergebnissen, Setzen von Steinen usw.
### Mögliche optionale Aufgaben
- Verbesserungen der Spieloberfläche:
  Hierzu sind denkbar: Siegesfanfare und/oder Video, 3D-Spielbrett, Würfelgeräusche
  usw.
- Verfeinerung der Strategie:
  Es sollte der Versuch unternommen werden, die Gewinnstrategie des Computer zu
  verbessern. Hierzu gehören z.B.: Wann werden Steine eigesetzt (bei einer 6) oder
  eventuell Steine gezogen. Soll ein Gegner geschlagen werden oder besser ein anderer
  Stein gezogen werden. Aufräumen im Zielbereich (Haus) bei kleinen Würfen etc:
- Client-Server-Anwendung (nur für Spezialisten):
  Jeder Spieler sitzt an einem eigenen Rechner (Client). Die Verwaltung der Spieldaten
  wird vom Server übernommen. Jeder Spieler muss zu jedem Zeitpunkt einen
  vollständigen Überblick über den Spielstand haben. Hierfür ist der Aufbau einer
  WLAN-Verbindung notwendig


# Allgemeine Hinweise

Allgemeine Hinweise für die Prüfungsprojekte
- Zulassungsvoraussetzung
  Studierende, welche nicht alle zwei Belege bestanden haben, sind nicht zum Prüfungs-
  projekt zugelassen.
- Projektentscheidung
  Die Projekte haben unterschiedliche Schwerpunkte und eventuell unterschiedliche
  Schwierigkeitsgrade. Die Entscheidung für ein Projekt ist endgültig und kann nicht
  rückgängig gemacht werden. Lesen Sie daher die Aufgabenstellung für alle möglichen
  Projekte durch und entscheiden Sie sich für dasjenige, welches Ihnen am meisten
  zusagt.
- Projektumfang
  Alle Projekte sind so konzipiert, dass sie von drei Studierenden bewältigt werden
  können. Allerdings erfordert dies eine Aufgabenverteilung innerhalb der Gruppe.
- Abgabe Deckblatt
  Deadline: 30.06.2023 23:59
  Zum Projekt ist vorab das Deckblatt einzureichen.
  Laden Sie sich die Vorlage aus dem OPAL-Ordner zur Prüfung. Tragen Sie das
  gewählte Projekt-Thema und alle Beteiligten in das Deckblatt ein und bestätigen Sie
  mit Ihren Unterschriften, dass Sie diese "Allgemeinen Hinweise für die Prüfungsprojekte"
  zur Kenntnis genommen haben.
  Scannen Sie das unterschriebene Dokument wieder ein und benennen Sie es mit Ihren
  Nachnamen (z.B. Mueller_Schmitz_Kolze.pdf).
  Anschließend ist das Deckblatt von einem der Projekt-Beteiligten in den Abgabe-
  Ordner der Prüfung im OPAL-Kurs hochzuladen.
  Mit dem Upload sind Thema und Beteiligte verbindlich festgelegt und für die
  Prüfung vorgemerkt.
- Abgabe Projekt
  Deadline: 20.08.2023 23:59
  Die Abgabe des Programmier-Projekts erfolgt ebenfalls über OPAL. Hierbei sind
  folgende Dokumente abzugeben:
    - eine Entwurfsskizze
    - eine kurze Bedienungsanleitung (PDF), ggf. mit einer Anleitung zur Installation
    - das Eclipse-Projekt als ZIP-komprimierter Projektordner und
    - Testbeispiele (PDF)
      Das Projekt muss unter Eclipse 2023-03 mit Java 11 lauffähig sein (bitte vor der Abgabe prüfen). Wenn Sie mit JavaFX arbeiten müssen, stellen Sie sicher, dass es mit Java 11 funktioniert. Empfohlen wird initial die Programmierung mit dem Framework Swing.
      Falls nicht standardmäßige Bibliotheken benutzt wurden, müssen diese mitgeliefertwerden, zusammen mit einer genauen Installationsanleitung.
      Abgabemodalitäten:
        - Fassen Sie den gesamten Eclipse-Projektordner zu einem ZIP-Archiv zusammen.
        - Packen Sie anschließend das komprimierte Eclipse-Projekt und die zusätzlich
          angeforderten Dokumente (Bedienungsanleitung, Testbeispiele etc.) wiederum in ein
          ZIP-Archiv. Versehen Sie dieses Archiv mit den Nachnamen der Projekt-Beteiligten
          (z.B. Mueller_Schmitz_Kolze.zip) und laden Sie dieses in den Abgabeorder der
          Prüfung im OPAL-Kurs.
        - Das Projekt sollte nur ein Mal und von demjenigen Projektbeteiligten abgegeben werden, der bereits das Deckblatt hochgeladen hat, so dass alle Dokumente in ein und demselben Abgabeordner liegen.
          Bewertungskriterien
1. Selbständigkeit
   Das wichtigste Kriterium zur Beurteilung einer Arbeit ist, dass das Programm von den
   Beteiligten selbst erstellt wurde. Ein nicht selbständig erstelltes Programm gilt als
   nicht bestanden. Falls Quellen (z. B. Internet) benutzt werden, sind diese anzugeben.
   Die Verwendung von nicht originären Teilen sollte ein gewisses Maß nicht
   überschreiten.
2. Funktionalität
   Für das Bestehen ist ein Programm, welches die Mindestanforderungen erfüllt,
   ausreichend. Ein umfangreicheres Programm wird besser bewertet als ein einfaches.
   Hierbei spielt es natürlich eine Rolle welche Teile tatsächlich funktionieren.
3. Aufbau und Programmierstil
   Ein Ziel dieser Vorlesung ist die Vermittlung von Kenntnissen der Programmier-
   sprache Java. Daher wird ein klar strukturiertes Programm besser bewertet als ein
   ungeschickt Formuliertes. Das Gleiche gilt auch für Aufbau und Handhabbarkeit der
   GUI.
4. Test
   Weisen Sie nach, dass die einzelnen Teile Ihres Programms getestet wurden. Die Tests
   sollten durch geeignete Testbeispiele dokumentiert werden.
5. Dokumentation
   Die angeforderten Dokumente müssen vollständig sein.

