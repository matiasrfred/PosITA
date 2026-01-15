# PosBO+ Android App

Bienvenido al repositorio de **PosBO+**, una aplicación de punto de venta (POS) nativa para Android. Esta aplicación está diseñada para gestionar operaciones comerciales como categorías de productos, ventas y más, directamente desde un dispositivo Android.

---

## 📝 Descripción del Proyecto

**PosBO+** es una aplicación Android moderna que sigue las mejores prácticas de desarrollo recomendadas por Google. Su arquitectura está pensada para ser **robusta, escalable y fácil de mantener**, utilizando un stack tecnológico basado en **Kotlin** y **Android Jetpack**.

La aplicación está orientada a entornos comerciales (retail/POS) y prioriza estabilidad, claridad arquitectónica y una experiencia de usuario consistente.

---

## 🚀 Tecnologías y Arquitectura

Este proyecto utiliza un conjunto de herramientas y librerías modernas para garantizar un desarrollo eficiente y una aplicación de alta calidad.

### 🔧 Stack Tecnológico

* **Lenguaje:** Kotlin (100%)
  Lenguaje oficial para Android. Se evidencia el uso de extensiones `-ktx` como `core-ktx` y `fragment-ktx`.

* **Interfaz de Usuario (UI):** Android Views + Material Design Components
  La UI está construida de forma nativa mediante layouts XML (por ejemplo, `dialog_category_form.xml`) y componentes de `com.google.android.material`, asegurando consistencia visual y adherencia a Material Design.

* **Binding:** ViewBinding
  Se utiliza `androidx.databinding:viewbinding` para una interacción segura y concisa entre el código Kotlin y los layouts XML, evitando el uso de `findViewById`.

---

## 🧱 Arquitectura

La aplicación sigue el patrón **MVVM (Model – View – ViewModel)**, separando claramente las responsabilidades de cada capa:

### 🖼 UI Layer (View)

* Compuesta por **Activities** y **Fragments**.
* Su responsabilidad es **mostrar datos** y **capturar interacciones del usuario**.
* Observa los cambios expuestos por el ViewModel mediante `LiveData`.

### 🧠 ViewModel Layer

* Implementada con `androidx.lifecycle.ViewModel`.
* Contiene la **lógica de la UI** y el **estado** de la vista.
* Expone datos reactivos usando `androidx.lifecycle.LiveData`.

### 🗄 Data Layer (Model)

* Gestiona toda la lógica relacionada con los datos.

#### 📦 Repositorio

* Actúa como **Single Source of Truth** de la aplicación.
* Decide si los datos provienen de una fuente local o remota.

#### 💾 Base de Datos Local

* Se recomienda el uso de **Room Persistence Library** (Jetpack).
* Proporciona una abstracción sobre SQLite y se integra de forma natural con `LiveData` y **Kotlin Coroutines**.

#### 🌐 Fuente de Datos Remota (Opcional)

* El repositorio puede conectarse a una API REST.
* Librerías sugeridas: **Retrofit** o **Ktor**.

---

### 📐 Diagrama de Arquitectura

```
┌──────────────────┐
│      Vista       │  (Activities, Fragments, XML)
│ (Observa LiveData) │
└─────────┬────────┘
          │
┌─────────▼────────┐
│    ViewModel     │  (Lógica de UI, expone LiveData)
└─────────┬────────┘
          │
┌─────────▼────────┐
│    Repositorio   │  (Única fuente de verdad)
└─────────┬────────┘
          │
┌─────────┴──────────┐
│   Fuentes de Datos │
│ ┌───────┐ ┌────────┐ │
│ │  Room │ │Retrofit│ │
│ │ (BDD) │ │ (API)  │ │
│ └───────┘ └────────┘ │
└────────────────────┘
```

---

## 🛠️ Dependencias Clave

* **Android Jetpack**

    * `androidx.core:core-ktx` – Extensiones Kotlin para código más limpio.
    * `androidx.lifecycle` – ViewModel y LiveData (base de MVVM).
    * `androidx.activity:activity-ktx` – Manejo simplificado de Activities.
    * `androidx.fragment:fragment-ktx` – Manejo simplificado de Fragments.
    * `androidx.constraintlayout` – Layouts flexibles y complejos.

* **Material Components for Android**
  Implementación oficial de Material Design.

* **ViewBinding**
  Enlace seguro entre vistas XML y código Kotlin.

---

## 🏁 Cómo Empezar

1. **Clona el repositorio**:

```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
```

2. Abre el proyecto en **Android Studio**.
3. Permite que **Gradle sincronice** y descargue las dependencias.
4. Ejecuta la aplicación en un **emulador** o **dispositivo físico**.

---

> Este `README.md` fue generado basándose en el análisis de la estructura del proyecto, las dependencias del módulo `PosBO+.app.main` y archivos de layout como `dialog_category_form.xml`.
