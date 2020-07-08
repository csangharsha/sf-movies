SAGE 

---
##Description
This service provides the location of movies filmed in San Francisco with interactive maps. 
Some features of this application are: 
- List of movies and details
- Search the movie with title
- Show Movie Location on Map with Marker along with the details

Mostly this application is focus on backend with some emphasis on frontend.

##Technical choices
- Backend
   - Java, Spring boot, Hibernate, JPA
   - Liquibase (For database migration)
   - Mysql (for development profile)
   - H2 (for production profile)
   - Swagger (for API documentation)
   
- Frontend
   
   - react
   - react-leaflet(OpenStreetMap)

This application is develop with modular approach so that the frontend and backend can be 
build, clone and deploy easily in one go without having to perform these action separately.

---

- This OpenStreetMap's geo-coding API uses this application, which doesn't accurately converts 
address into latitude and longitude due to which map does not show location of most of the 
movie in San Francisco while importing the data from the [DataSF: FileLocation](https://data.sfgov.org/resource/yitu-d5am.json).
The best alternative for  OpenStreetMap's geo-coding API is Google Map's geo-coding API, which I could not afford at the moment.

- Due to the time constraint, I could not build the interface for an admin in the frontend. 
However, the authorization and authentication has been implemented in the backend using 
spring security and JWT.

## Application Link
This application has been deployed in AWS. Links to this application is:

- [FRONTEND](http://ec2-18-189-189-17.us-east-2.compute.amazonaws.com:8083/)
- [BACKEND](http://ec2-18-189-189-17.us-east-2.compute.amazonaws.com:8082/)

NOTE: Right now, swagger is visible in both profile(dev and prod) for viewing proposes only. 

##Link to Personal Profile

- [GITHUB: csangharsha](https://github.com/csangharsha)
- [LINKEDIN: s-chaulagain](https://www.linkedin.com/in/s-chaulagain/)
- [https://csangharsha.github.io/](https://csangharsha.github.io/)

---