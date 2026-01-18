┌────────────────────────────┐
│        MainActivity        │
│  (Startpunkt der App)      │
└────────────┬───────────────┘
│
▼
┌────────────────────────────┐
│      BabyalterScreen       │
│   (Einziger Screen)        │
└────────────┬───────────────┘
│
┌───────┴────────┐
▼                ▼
┌──────────────┐  ┌───────────────┐
│ BirthDate    │  │ AgeCalculator │
│ Section      │  │ (Logik)       │
└──────┬───────┘  └──────┬────────┘
│                │
▼                ▼
┌──────────────┐  ┌───────────────┐
│ DataStore    │  │ AgeResult     │
│ (Persistenz) │  │ (Datenmodell) │
└──────────────┘  └───────────────┘

MainActivity.kt

Startet die App und verbindet Android mit Compose.
Hier wird der Hauptscreen geladen – sonst keine Logik.

🔹 ui/screens/BabyalterScreen.kt

Der einzige Screen der App.
Steuert, ob DatePicker oder Altersanzeige sichtbar ist.

🔹 ui/components/BirthDateSection.kt

UI-Block für Datum auswählen, ändern oder löschen.
Kümmert sich nur um Benutzerinteraktion, nicht um Berechnung.

🔹 data/AgeCalculator.kt

Reine Rechenlogik.
Wandelt zwei Daten in Tage, Wochen und Monate um.

🔹 data/AgeResult.kt

Einfaches Datenobjekt für das Ergebnis der Altersberechnung.
Keine Logik, nur Struktur.

🔹 data/PreferencesKeys.kt

Enthält alle DataStore-Keys an einer Stelle.
Verhindert Tippfehler und Chaos.

🎨 ui/theme/Color.kt

Definiert die Farben der App.

🎨 ui/theme/Theme.kt

Zentrale Theme-Definition für Material 3.

🎨 ui/theme/Type.kt

Schriftarten & Textstile.

🧠 Das 2-Satz-Mentalmodell (wichtiger Teil)

UI zeigt State.
State kommt aus DataStore oder Berechnung.