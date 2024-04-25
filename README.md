# Getir Bootcamp App
This documentation includes used features, Navigation graph for screens, images from app.

## Features

- Single Activity 
- MVVM (Model-View-ViewModel) Architecture
- Retrofit
- Navigation Component
- Hilt Dependency Injection
- Kotlin Flow Coroutines
- Glide for image uploading

## Navigation Graph
In app navigation graph, start screen is product screen. We can easily navigate to cart screen if we have any products in cart. If not we should add some products to go to Cart screen. If user click on any product, Detail screen appears. In detail screen user can add or remove that product to cart and also can navigate to cart if any product is in the cart.\
\
There is no direct navigation from Cart and Detail fragment to Product fragment. In app, there is a navigation handling mechanism by using backstack management. So if any action needed to navigate to Product Fragment, app itself handles to implement that navigation action.

\
![image](https://github.com/sengule/Chat-App-kotlin/assets/93080523/ecdb0038-1c7f-44a4-ac58-44efa8891907)

## ScreenShoots

### Product Screen
![image](https://github.com/sengule/Chat-App-kotlin/assets/93080523/fafc055c-9ebc-4df8-82ce-5bef69a36dff)

### Detail Screen
![image](https://github.com/sengule/Chat-App-kotlin/assets/93080523/896dbfd9-df78-4514-88b4-89b4567a8fc8)

### Cart Screen
![image](https://github.com/sengule/Chat-App-kotlin/assets/93080523/f53c4f6d-fd65-4e20-a663-7fa3ea30f05b)
