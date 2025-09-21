# TerraCambio 🌍⚡️

A **Kotlin Compose + Spring Boot API** for TerraCambio.
This repository contains a minimal, clean architecture-style setup (ports and adapters/hexagonal) for a **Listing** feature, plus **Springdoc OpenAPI** for interactive API docs 📑🔍

---

## 🎯 Targets

This is a **Kotlin Multiplatform** project targeting:
📱 Android · 🍏 iOS · 🌐 Web · 💻 Desktop · 🖥️ Server (JVM)

* **[/composeApp](./composeApp/src)** 🧩 — Shared UI code across Compose Multiplatform apps.

  * **[commonMain](./composeApp/src/commonMain/kotlin)** 🔄 — Code shared by all platforms.
  * Platform-specific folders (e.g. \[iosMain], \[jvmMain]) handle native integrations.

* **[/iosApp](./iosApp/iosApp)** 🍏 — Entry point for iOS app (needed even with Compose Multiplatform). Can also include SwiftUI code.

* **[/shared](./shared/src)** 📦 — Business logic shared across all targets.

  * **[commonMain](./shared/src/commonMain/kotlin)** — Core shared code.

* **[/listing](./listing/src/commonMain/kotlin/com/github/braiso_22/listing)** 📋 — KMP module for listing domain logic, used in backend + frontend.

* **[/server](./server/src/main/kotlin/com/braiso_22/terracambio)** ⚙️ — Spring Boot API layer.

---

## 📖 API Documentation (Swagger UI)

Once the server is running, open:
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Provided by **springdoc-openapi** for exploring available controllers interactively 🎛️

---

## 🛠️ Build commands

### ▶️ Run Server

* macOS/Linux 🐧

  ```sh
  ./gradlew :server:run
  ```
* Windows 🪟

  ```sh
  .\gradlew.bat :server:run
  ```

---

### 📱 Run Android App

* macOS/Linux 🐧

  ```sh
  ./gradlew :composeApp:assembleDebug
  ```
* Windows 🪟

  ```sh
  .\gradlew.bat :composeApp:assembleDebug
  ```

---

### 💻 Run Desktop (JVM) App

* macOS/Linux 🐧

  ```sh
  ./gradlew :composeApp:run
  ```
* Windows 🪟

  ```sh
  .\gradlew.bat :composeApp:run
  ```

---

### 🌐 Run Web App

1. Install [Node.js](https://nodejs.org/en/download) 📦
2. Build Kotlin/JS shared code

   * macOS/Linux 🐧

     ```sh
     ./gradlew :shared:jsBrowserDevelopmentLibraryDistribution
     ```
   * Windows 🪟

     ```sh
     .\gradlew.bat :shared:jsBrowserDevelopmentLibraryDistribution
     ```
3. Run the web app 🚀

   ```sh
   npm install
   npm run start
   ```

---

### 🍏 Run iOS App

Open **[/iosApp](./iosApp)** in **Xcode** and run it directly ▶️

---

✨ Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

