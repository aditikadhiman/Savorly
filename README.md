# Savorly App
# 🍽️ Savorly - Personal Recipe Manager App

Savorly is an intuitive Android application built to help users manage, organize, and explore their favorite recipes with ease. With features like custom categories, ingredient-based search, image support, and local storage, Savorly ensures your culinary ideas are always within reach.

---

## 📱 Features

- ✅ **Add / Edit / Delete Recipes**
- 🗂️ **Categorize Recipes** – Breakfast, Lunch, Dinner, Desserts
- 🔍 **Search Recipes** – by title or ingredients
- ⭐ **Mark Recipes as Favorites**
- 🗓️ **Save Recipe Creation Date**
- 🖼️ **Attach Optional Images**
- 📦 **Offline-first Experience** – backed by Room Database
- 🧠 **Modern MVVM Architecture** – Clean and maintainable
- 🧪 **Jetpack Compose UI** – Smooth and responsive design

---

<h2>🧑‍🍳 Screenshots</h2>

<table>
  <tr>
    <td align="center">
      <img src="screenshots/welcome.png.jpg" alt="Welcome Screen" width="250"/><br/>
      <b>Welcome Screen</b>
    </td>
    <td align="center">
      <img src="screenshots/homeSavorly.png.jpg" alt="Home Screen" width="250"/><br/>
      <b>Home Screen</b>
    </td>
    <td align="center">
      <img src="screenshots/add.png.jpg" alt="Add Recipe" width="250"/><br/>
      <b>Add New Recipe</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="screenshots/details.png.jpg" alt="Recipe Detail" width="250"/><br/>
      <b>Recipe Detail View</b>
    </td>
    <td align="center">
      <img src="screenshots/edit.png.jpg" alt="Edit Recipe" width="250"/><br/>
      <b>Edit Screen</b>
    </td>
    <td align="center">
      <img src="screenshots/favorite.png.jpg" alt="Favorites Screen" width="250"/><br/>
      <b>Favorites Screen</b>
    </td>
  </tr>
</table>



---

## 🧱 Tech Stack

| Layer       | Library / Tool              |
|-------------|-----------------------------|
| Language    | Kotlin                      |
| UI          | Jetpack Compose             |
| Architecture| MVVM                        |
| Database    | Room (SQLite)               |
| Dependency Injection | Hilt               |
| Image Loading | Coil                      |
| Navigation | Jetpack Navigation Compose  |

---

## 🗂️ Project Structure

```bash
com.savorly
├── data
│   ├── local (Room entities, DAO)
│   └── repository
├── domain (models, use cases)
├── ui
│   ├── screens (Home, AddRecipe, Details)
│   └── components
├── viewmodel
├── utils
└── di (Hilt modules)
