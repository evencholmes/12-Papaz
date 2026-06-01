# PAPAZ — The ExamOS
> *You have an exam coming. You know what's on it. You're not sure how much of it you actually know. That's what PAPAZ is for.*

PAPAZ is a free, local-first academic command center for Android. No account. No subscription. No cloud. Your data stays on your device — period. Build your full syllabus as a collapsible tree, rate your confidence on every topic, log your study hours, track your mock test scores, add your exam dates, and let PAPAZ tell you exactly where you're behind.

**Syllabus tree. Confidence tracking. Study heatmap. Mock test log. Vault notes. Exam countdowns. Report card. Panic Mode. Zero cloud.**

It started because no study app actually thinks the way a student does. They want you to make flashcards, join streaks, and tap through gamified nonsense. PAPAZ just maps your entire curriculum, shows you your weak spots in red, and tells you what to study next.

Most people will download it, use it for one exam cycle, and never go back to highlighters and sticky notes.

The ones who don't try it will find out on results day.
That's also a choice.

---

═══════════════════════════════════════════════════════════

---

## What PAPAZ Actually Does

**Dashboard — where you stand, all at once**
Coverage percentage, total study hours, streak, and a progress bar across the top so you know in one glance whether you're on track or pretending to be. Next exam countdown in amber. A "What should I study now?" card that picks your weakest untouched topic and tells you to open the book. A tap-to-log hour tile — tap once for 1h, hold for a custom amount. A 90-day study heatmap so the gaps are visible. A "Needs Attention" section that flags any topic you rated low and haven't touched recently. PAPAZ also greets you every time with a line that matches your situation. Not cheerful. Honest.

**Plan — your full curriculum as a tree**
Build your syllabus as a collapsible, nested tree. Parent topics. Subtopics. Sub-subtopics. As deep as your curriculum goes. Each node has a confidence score from 1 to 5 — you set it, PAPAZ doesn't guess. Low-confidence nodes are highlighted so the weak spots are impossible to ignore. Tap any topic to open its detail: set confidence, add a note directly, see all vault notes tagged to it, log hours against it, or delete it. Collapse and expand the whole tree with one button.

Paste your syllabus directly — PAPAZ parses Outline Decimal Notation automatically. Don't have it formatted? Ask any AI to convert it first, then paste. The app tells you exactly how to do that.

Export your tree as a `.papaz` file. Import it back on any device. Expand all. Collapse all. And when things get bad — **Panic Mode** turns the whole interface red and surfaces only your weakest topics. Not a gimmick. A mode you will actually use the night before.

**Vault — notes that know where they belong**
Add notes and tag each one to a specific topic in your tree. Title, body, type — Definition, Formula, Case Study, or Watch Out. Search by keyword. Sort by syllabus order or note type. Filter by type with one tap. Long-press any note for full detail. Every vault note also appears inside the relevant topic's detail view, so nothing is ever disconnected from the curriculum.

**Ledger — performance, tracked honestly**
Add exams with name, date, and optional topic tag. PAPAZ counts down to each one and color-codes by urgency — green means breathing room, red means you're running out of time. Set custom notification days per exam. Older exams fold away cleanly.

Log every mock test you sit — name, date, score, total, and exactly which topics were covered (or the full curriculum if it was comprehensive). PAPAZ calculates your average score and tracks trend across attempts. Run it on a time range — past month, 3 months, full year, whole session — and PAPAZ compiles a full **Report Card**: hours logged, coverage, average mock score, topics still below your confidence threshold, and a written pace analysis that tells you plainly whether you're going to be ready.

**Reminders**
Set notification alerts per exam — 7 days out, 3 days out, or any custom number of days you want. Reminders fire at reasonable times. Not at 2am. Not mid-lecture.

**Data & Export**
Full backup as a `.papaz` file. Import to restore with no duplicates. Export your tree for sharing or archiving. Everything lives on your device. Nothing leaves it.

**Appearance & Settings**
Dark mode / Light mode. Accent colors — Silver, Gold, Blue, Fluffy. Font size S / M / L / XL. App lock. The whole phone UI matches the app's skin — nav bar, status bar, everything.

---

## Permissions — What They Are and Why

| Permission | Why |
|---|---|
| POST_NOTIFICATIONS | Exam countdown alerts and study reminders |
| WAKE_LOCK | Wakes device to deliver scheduled notifications |
| SCHEDULE_EXACT_ALARM | Fires reminders at the exact time you set |
| RECEIVE_BOOT_COMPLETED | Restores scheduled alarms after a phone restart |
| INTERNET | Loads Google Fonts only — no data is sent anywhere |
| READ_MEDIA_IMAGES | Required by Capacitor on Android 13+ for file operations |
| READ / WRITE EXTERNAL_STORAGE | Export and import `.papaz` backup files (Android 12 and below) |

No network calls to any server. No analytics. No crash reporting. No tracking. Install it with WiFi off — it works completely.

---

## Why Your Antivirus Might Flag This

PAPAZ is not on the Google Play Store. It is a direct APK install.

Google and most antivirus tools flag sideloaded apps by default — not because they found something wrong, but because they have not seen this app before. Unknown = suspicious. That is how the system works. It does not mean dangerous.

What actually happens when you install PAPAZ:
- No outbound connections to any server are made
- No data leaves your device
- The only network call is loading a Google Font from Google's own CDN
- You can install it with WiFi off and it runs completely

If you want to verify — install [NetGuard](https://netguard.me/) and watch PAPAZ's traffic. You will see nothing.

The irony: most apps that sail through Play Store review are the ones quietly tracking your study habits, your schedule, and your academic anxieties in ways the automated scan was never designed to catch. PAPAZ fails the suspicion check because it has no company behind it, no Play Console account, and no history. That is the price of being independent and free.

---

## Honest Notes

Built with hand-written code and AI-assisted engineering (Claude by Anthropic), packaged into an APK using Android Studio and Capacitor. Dozens of hours of building, debugging, and testing went into getting it stable.

Not a polished commercial product. No support team. No guarantees. Tested on MIUI / Android 13 — other devices should be fine but haven't all been tested individually.

If something breaks — open an issue. If it helps you pass — tell someone who needs it.

---

## Stack

- Single `index.html` — all HTML, CSS, and JS in one file
- [Capacitor 6](https://capacitorjs.com/) — Android wrapper
- [Android Studio](https://developer.android.com/studio) — APK build
- No frameworks. No build step for the web layer.

---

═══════════════════════════════════════════════════════════

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/2499bfa7-7019-412d-9c67-567629c33eb7" width="180"/></td>
    <td><img src="https://github.com/user-attachments/assets/4740344a-1473-4cba-b575-1de6ee0eeed3" width="180"/></td>
    <td><img src="https://github.com/user-attachments/assets/91b953d3-535f-4fe9-b672-10a5a9587834" width="180"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/d04383cc-80ba-4f80-8431-12f7331c54ab" width="180"/></td>
    <td><img src="https://github.com/user-attachments/assets/09d3a1e7-5c8d-48e8-a744-50ad28afe206" width="180"/></td>
    <td><img src="https://github.com/user-attachments/assets/ea2a0edf-0a76-4b21-a787-7b2e0386a61e" width="180"/></td>
  </tr>
</table>

---

**[Support on Patreon](https://www.patreon.com/jibunshidai81)**
PAPAZ is free and will stay free. If it helped you stay on top of your work — a dollar goes a long way toward keeping independent tools like this alive. A few other free apps live on that page too. Same deal, same spirit.

If money is tight, sharing costs nothing and means just as much.

---

*Built by [@Jibunshidai81](https://x.com/Jibunshidai81) — engineered with Claude by Anthropic, emotionally supervised by John A. Sherlock*

---

## Get PAPAZ

You got this far. Exam's not going to study itself.

**[Download APK](https://github.com/evencholmes/12-Papaz/releases/tag/APK)**
Direct install. No Play Store. No account. No nonsense.
**[Direct Download](https://github.com/evencholmes/12-Papaz/releases/download/APK/12.Papaz.apk)**

**[Test the webapp here](https://12-papaz-app.netlify.app)** 

---

*Do whatever you want with this. Credit appreciated, not required.*
