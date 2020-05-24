# Blog REST API example
Blog REST API

# Build and run  
Precondition: Docker up and running  
  
At root directory:  
docker-compose up -d  
mvn clean install  
mvn spring-boot:run  (or run DemoApplication as java class)  

# Requests
URI|request|response|description
---|---|---|---
/v1/posts|GET|200, [{id:'1', title:'title', content:'content'}, {id:'2', title:'title2', content:'content2'}]| Get all posts
/posts|GET|200, [{id:'1', title:'title', content:'content'}, {id:'2', title:'title2', content:'content2'}]| Get all posts pageable (sorting, searching, filtering)
/v1/posts|POST {title:'title string', content: 'content string'} |201, no content in body, the value of HTTP response header  **Location** is the uri of the new created post| Create a new post
/v1/posts/{id}|GET|200, {id:'1', title:'title', content:'content'}| Get a posts by id
/v1/posts/{id}|PUT {title:'title string', content: 'content string'} |204, no content in body| Update a certain posts by id
/v1/posts/{id}|DELETE|204, no content| Delete a post by id 

Same structure goes for a users with base url `/users`

