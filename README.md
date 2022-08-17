![key](https://user-images.githubusercontent.com/36485235/185005295-f0dd7ff1-90c3-49e6-b79c-2c73efda31c5.png)

# Digital Key App
Android app to store personal digital keys in a secure wallet with an [own REST API](https://github.com/jongwon254/Digital-Key-API).

## Technologies
- Language: Java
- Backend: 
  - REST API built with Spring Boot and MySQL Database
  - Authentication with Spring Security (HS256 Hashing) 
  - Deployed with Docker on Azure Cloud Kubernetes Cluster
- Frontend: 
  - Designed with Figma
  - Built with XML in Android Studio

## Functionality
- The Android app communicates with the REST API via GET and POST methods
- Users can register or login and access their digital keys after authentication
- The user data is stored in a MySQL database with the hashed password (HS256 algorithm)
- Users can unlock their car with NFC or UWB and control it with remote controls (planned)

## Screenshots

![screen1](https://user-images.githubusercontent.com/36485235/171295679-69ed918b-31ac-43b5-8234-c751b263200f.png)
![screen2](https://user-images.githubusercontent.com/36485235/171295697-803b4d46-5f9e-4b15-b95f-4d03776a1d71.png)


## More Info
[Visit My Website](https://jongwonlee.dev/digital-key-app)
